package com.lairon.xclans.data;

import com.lairon.xclans.XClans;
import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.settings.DataProviderSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataProvider {

    private Connection connection;
    private DataProviderSettings settings;
    private XClans xClans;

    public DataProvider(DataProviderSettings settings, XClans XClans) {
        this.settings = settings;
        this.xClans = XClans;
        try {
            if (settings.isMySqlEnabled()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + settings.getIp() + "/" + settings.getDbName(), settings.getUserName(), settings.getPassword());
            } else {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:plugins/XClans/ClansData.db");
            }
            Statement statement = connection.createStatement();
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `Clans` (
                    `ID` VARCHAR(43) NOT NULL,
                    `Owner` VARCHAR(45) NOT NULL,
                    `Members` LONGTEXT NULL DEFAULT NULL,
                    `Moderators` LONGTEXT NULL DEFAULT NULL,
                    `WelcomeMessage` LONGTEXT NULL DEFAULT NULL,
                    `Balance` INT NULL DEFAULT 0,
                    `Exp` INT NULL DEFAULT 0,
                    `Date` DATETIME NULL DEFAULT NULL,
                    `Color` VARCHAR(2) NULL DEFAULT NULL,
                    `Home` VARCHAR(45) NULL DEFAULT NULL,
                    `OpenHome` TINYINT NULL DEFAULT 0,
                    `Level` INT NULL DEFAULT 0,
                    `Pvp` TINYINT NULL DEFAULT 0,
                    `Alliances` VARCHAR(45) NULL DEFAULT NULL,
                    PRIMARY KEY (`ID`))               
                    """);
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllClansAsync(ProviderCallBack<Clan[]> callBack) {
        Bukkit.getScheduler().runTaskAsynchronously(xClans, () -> {
            var clans = loadAllClans();
            Bukkit.getScheduler().runTask(xClans, () -> callBack.callBack(clans));
        });
    }

    public Clan[] loadAllClans() {
        ArrayList<Clan> clans = new ArrayList<>();
        String sql = "SELECT * FROM Clans;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Clan clan = parseClan(resultSet);
                clans.add(clan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clans.toArray(new Clan[]{});
    }

    public void saveClanAsync(Clan clan) {
        Bukkit.getScheduler().runTaskAsynchronously(xClans, () -> saveClan(clan));
    }

    public void saveClan(Clan clan) {
        String sql = """
                INSERT INTO `Clans`
                (`ID`,
                `Owner`)
                VALUES
                (<{ID}>,
                <{Owner}>);
                """;
        sql = sql.replace("<{ID}>", "\"" + clan.getClanID() + "\"")
                .replace("<{Owner}>", "\"" + clan.getOwner() + "\"");
        try {
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadClanByIDAsync(String id, ProviderCallBack<Clan> callBack) {
        Bukkit.getScheduler().runTaskAsynchronously(xClans, () -> {
            var clan = loadClanByID(id);
            Bukkit.getScheduler().runTask(xClans, () -> callBack.callBack(clan));
        });
    }

    public Clan loadClanByID(String id) {
        String sql = "SELECT * FROM Clans WHERE ID = \"" + id + "\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return parseClan(resultSet);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteClanAsync(Clan clan){
        Bukkit.getScheduler().runTask(xClans, ()-> deleteClan(clan));
    }

    public void deleteClan(Clan clan){
        String sql = "DELETE FROM `Clans` WHERE ID = \"" + clan.getClanID() + "\"";
        try {
            Statement statement = statement = connection.createStatement();
            statement.executeQuery(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Clan parseClan(ResultSet resultSet) throws SQLException {
        Clan clan = new Clan(resultSet.getString(1), resultSet.getString(2));
        var membersString = resultSet.getString(3);
        if (membersString != null)
            clan.setMembers(new HashSet<>(Set.of(membersString.split(" "))));

        var moderatorsString = resultSet.getString(4);
        if (moderatorsString != null)
            clan.setModerators(new HashSet<>(Set.of(moderatorsString.split(" "))));

        clan.setWelcomeMessage(resultSet.getString(5));
        clan.setBalance(resultSet.getInt(6));
        clan.setExp(resultSet.getInt(7));
        clan.setDate(resultSet.getDate(8));
        var color = resultSet.getString(9);
        if (color != null)
            clan.setColor(ChatColor.valueOf(color));

        String homeString = resultSet.getString(10);
        if (homeString != null) {
            String[] homeArr = homeString.split(" ");
            Location location = new Location(Bukkit.getWorld(homeArr[0]), Double.parseDouble(homeArr[1]), Double.parseDouble(homeArr[2]), Double.parseDouble(homeArr[3]));
            clan.setClanHome(location);
        }

        clan.setOpenCH(resultSet.getBoolean(11));
        clan.setLevel(resultSet.getInt(12));
        clan.setPvp(resultSet.getBoolean(13));
        var alliancesString = resultSet.getString(14);
        if (alliancesString != null)
            clan.setAlliances(new HashSet<>(Set.of(alliancesString.split(" "))));
        return clan;
    }

}
