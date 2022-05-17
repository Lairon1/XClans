package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.settings.DataProviderSettings;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataProvider {

    private Connection connection;
    private ClanFactory clanFactory = new ClanFactory();

    public DataProvider(DataProviderSettings settings) throws ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (settings.isMySqlEnabled()) {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();

            connection = DriverManager.getConnection("jdbc:mysql://" + settings.getIp() + "/" + settings.getDbName() + "?autoReconnect=true&useSSL=false", settings.getUserName(), settings.getPassword());
        } else {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/XClans/ClansData.db");
        }
        Statement statement = connection.createStatement();
        statement.execute(" CREATE TABLE IF NOT EXISTS `Clans` (`ID` VARCHAR(43) NOT NULL,`Owner` VARCHAR(45) NOT NULL,`Members` LONGTEXT NULL DEFAULT NULL,`Moderators` LONGTEXT NULL DEFAULT NULL,`WelcomeMessage` LONGTEXT NULL DEFAULT NULL,`Balance` INT NULL DEFAULT 0,`Exp` INT NULL DEFAULT 0,`Date` DATETIME NULL DEFAULT NULL,`Color` VARCHAR(2) NULL DEFAULT NULL,`Home` VARCHAR(45) NULL DEFAULT NULL,`OpenHome` TINYINT NULL DEFAULT 0,`Level` INT NULL DEFAULT 0,`Pvp` TINYINT NULL DEFAULT 0,`Alliances` VARCHAR(45) NULL DEFAULT NULL, PRIMARY KEY (`ID`));");
        statement.close();
    }

    public Clan[] loadAllClans() throws SQLException {
        ArrayList<Clan> clans = new ArrayList<>();
        String sql = "SELECT * FROM Clans;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Clan clan = clanFactory.createClanInstanceByResultSet(resultSet);
            clans.add(clan);
        }
        statement.close();
        return clans.toArray(new Clan[]{});
    }

    public void saveClan(Clan clan) throws SQLException {
        String sql = " INSERT INTO `Clans` (`ID`, `Owner`, `Members`, `Moderators`, `WelcomeMessage`, `Balance`, `Exp`, `Date`, `Color`, `Home`, `OpenHome`, `Level`, `Pvp`, `Alliances`) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, clan.getClanID());
        statement.setString(2, clan.getOwner());
        statement.setString(3, clan.getMembers() == null ? null : String.join(" ", clan.getMembers()));
        statement.setString(4, clan.getModerators() == null ? null : String.join(" ", clan.getModerators()));
        statement.setString(5, clan.getWelcomeMessage());
        statement.setInt(6, clan.getBalance());
        statement.setInt(7, clan.getExp());
        statement.setString(8, clan.getDate() == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(clan.getDate()));
        statement.setString(9, clan.getColor() == null ? null : clan.getColor().toString());
        statement.setString(10, clan.getClanHome() == null ? null :
                clan.getClanHome().getWorld().getName() + " " +
                        clan.getClanHome().getX() + " " +
                        clan.getClanHome().getY() + " " +
                        clan.getClanHome().getZ());
        statement.setBoolean(11, clan.isOpenCH());
        statement.setInt(12, clan.getLevel());
        statement.setBoolean(13, clan.isPvp());
        statement.setString(14, clan.getAlliances() == null ? null : String.join(" ", clan.getAlliances()));
        statement.execute();
        statement.close();
    }

    public Clan loadClanByID(String id) throws SQLException {
        String sql = "SELECT * FROM Clans WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Clan clan = clanFactory.createClanInstanceByResultSet(resultSet);
        statement.close();
        return clan;
    }

    public void deleteClan(String id) throws SQLException {
        String sql = "DELETE FROM `Clans` WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        statement.execute();
        statement.close();
    }


}
