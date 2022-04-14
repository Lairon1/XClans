package com.lairon.xclans.registered;

import com.lairon.xclans.clan.Clan;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class RegisteredClans {

    private Set<Clan> registeredClans = new HashSet<>();

    public void registerClan(Clan clan) {
        if (getClanByID(clan.getClanID()) != null)
            throw new IllegalArgumentException("This clan is already registered");
        if (getPlayerClan(clan.getOwner()) != null)
            throw new IllegalArgumentException("The owner of the clan is already a member of the clan");
        registeredClans.add(clan);
    }

    public Clan getClanByID(String id) {
        if (registeredClans.isEmpty()) return null;
        var first = registeredClans
                .stream()
                .filter(clan ->
                        clan.getClanID().equals(id))
                .findFirst();
        if(first.isEmpty()) return null;
        return first.get();
    }

    public Clan getPlayerClan(Player player) {
        return getPlayerClan(player.getName());
    }

    public Clan getPlayerClan(String player) {
        if (registeredClans.isEmpty()) return null;
        var first = registeredClans
                .stream()
                .filter(clan ->
                        clan.getOwner().equalsIgnoreCase(player)
                                || clan.getModerators().contains(player)
                                || clan.getMembers().contains(player))
                .findFirst();
        if (first.isEmpty()) return null;
        return first.get();
    }

}
