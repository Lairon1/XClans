package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

public class ClanFactory {

    public Clan createClanInstanceByResultSet(ResultSet resultSet) throws SQLException {
        Clan clan = new Clan(resultSet.getString(1), resultSet.getString(2));
        String membersString = resultSet.getString(3);
        if (membersString != null)
            clan.setMembers(new HashSet<>(Arrays.asList(membersString.split(" "))));
        String moderatorsString = resultSet.getString(4);
        if (moderatorsString != null)
            clan.setModerators(new HashSet<String>(Arrays.asList(moderatorsString.split(" "))));
        clan.setWelcomeMessage(resultSet.getString(5));
        clan.setBalance(resultSet.getInt(6));
        clan.setExp(resultSet.getInt(7));
        clan.setDate(resultSet.getDate(8));
        String color = resultSet.getString(9);
        if (color != null)
            clan.setColor(ChatColor.getByChar(color));
        String homeString = resultSet.getString(10);

        boolean isBukkitEnable = false;

        if (homeString != null && isBukkitEnable) {
            String[] homeArr = homeString.split(" ");
            World world = Bukkit.getWorld(homeArr[0]);

            Location location = null;
            if (world != null)
                location = new Location(world, Double.parseDouble(homeArr[1]), Double.parseDouble(homeArr[2]), Double.parseDouble(homeArr[3]));
            clan.setClanHome(location);

        }
        clan.setOpenCH(resultSet.getBoolean(11));
        clan.setLevel(resultSet.getInt(12));
        clan.setPvp(resultSet.getBoolean(13));
        String alliancesString = resultSet.getString(14);
        if (alliancesString != null)
            clan.setAlliances(new HashSet<String>(Arrays.asList(alliancesString.split(" "))));
        return clan;
    }

}
