package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.settings.DataProviderSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataProvider {

    private Connection connection;
    private DataProviderSettings settings;

    public DataProvider(DataProviderSettings settings) throws ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.settings = settings;
        if (settings.isMySqlEnabled()) {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();

            connection = DriverManager.getConnection("jdbc:mysql://" + settings.getIp() + "/" + settings.getDbName() + "?autoReconnect=true&useSSL=false", settings.getUserName(), settings.getPassword());
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

    }


    public Clan[] loadAllClans() throws SQLException {
        ArrayList<Clan> clans = new ArrayList<>();
        String sql = "SELECT * FROM Clans;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Clan clan = parseClan(resultSet);
            clans.add(clan);
        }
        return clans.toArray(new Clan[]{});
    }

    public void saveClan(Clan clan) throws SQLException {
        String sql = """              
                INSERT INTO `Clans`
                (`ID`,
                `Owner`,
                `Members`,
                `Moderators`,
                `WelcomeMessage`,
                `Balance`,
                `Exp`,
                `Date`,
                `Color`,
                `Home`,
                `OpenHome`,
                `Level`,
                `Pvp`,
                `Alliances`)
                VALUES
                (<{ID}>,
                <{Owner}>,
                <{Members}>,
                <{Moderators}>,
                <{WelcomeMessage}>,
                <{Balance}>,
                <{Exp}>,
                <{Date}>,
                <{Color}>,
                <{Home}>,
                <{OpenHome}>,
                <{Level}>,
                <{Pvp}>,
                <{Alliances}>);        
                """;
        sql = sql.replace("<{ID}>", "\"" + clan.getClanID() + "\"")
                .replace("<{Owner}>", "\"" + clan.getOwner() + "\"")
                .replace("<{Members}>", clan.getMembers() == null ? "NULL" : "'" + String.join(" ", clan.getMembers()) + "'")
                .replace("<{Moderators}>", clan.getModerators() == null ? "NULL" : "'" + String.join(" ", clan.getModerators()) + "'")
                .replace("<{WelcomeMessage}>", clan.getWelcomeMessage() == null ? "NULL" : "'" + clan.getWelcomeMessage() + "'")
                .replace("<{Balance}>", clan.getBalance() + "")
                .replace("<{Exp}>", clan.getExp() + "")
                .replace("<{Date}>", clan.getDate() == null ? "NULL" : "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clan.getDate()) + "'")
                .replace("<{Color}>", clan.getColor() == null ? "NULL" : "'" + clan.getColor().toString() + "'")
                .replace("<{Home}>", clan.getClanHome() == null ? "NULL" : "'" +
                        clan.getClanHome().getWorld().getName() + " " +
                        clan.getClanHome().getX() + " " +
                        clan.getClanHome().getY() + " " +
                        clan.getClanHome().getZ() + "'")
                .replace("<{OpenHome}>", clan.isOpenCH() + "")
                .replace("<{Level}>", clan.getLevel() + "")
                .replace("<{Pvp}>", clan.isPvp() + "")
                .replace("<{Alliances}>", clan.getAlliances() == null ? "NULL" : "'" + String.join(" ", clan.getAlliances()) + "'");

        var statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    public Clan loadClanByID(String id) throws SQLException {
        String sql = "SELECT * FROM Clans WHERE ID = \"" + id + "\"";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            return parseClan(resultSet);
        }
        statement.close();
        return null;
    }

    public void deleteClan(String id) throws SQLException {
        String sql = "DELETE FROM `Clans` WHERE ID = \"" + id + "\"";
        Statement statement = statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
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
            clan.setColor(ChatColor.getByChar(color));

//        String homeString = resultSet.getString(10);
//        if (homeString != null) {
//            String[] homeArr = homeString.split(" ");
//            Location location = new Location(Bukkit.getWorld(homeArr[0]), Double.parseDouble(homeArr[1]), Double.parseDouble(homeArr[2]), Double.parseDouble(homeArr[3]));
//            clan.setClanHome(location);
//        }

        clan.setOpenCH(resultSet.getBoolean(11));
        clan.setLevel(resultSet.getInt(12));
        clan.setPvp(resultSet.getBoolean(13));
        var alliancesString = resultSet.getString(14);
        if (alliancesString != null)
            clan.setAlliances(new HashSet<>(Set.of(alliancesString.split(" "))));
        return clan;
    }

}
