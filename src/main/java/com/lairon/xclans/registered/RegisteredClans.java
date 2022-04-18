package com.lairon.xclans.registered;

import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.data.DataProvider;
import com.lairon.xclans.data.ProviderCallBack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RegisteredClans {

    private final Set<Clan> registeredClans = new HashSet<>();
    private DataProvider dataProvider;


    public RegisteredClans(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
        dataProvider.loadAllClansAsync(answer -> {
            for (Clan clan : dataProvider.loadAllClans()) registerClan(clan);
            Bukkit.getLogger().info("Loaded " + registeredClans.size() + " clans");
        });

//        Clan clan = new Clan("KOT", "Lairon1");
//        clan.setColor(ChatColor.BLUE);
//        clan.setBalance(1337);
//        clan.setClanHome(new Location(Bukkit.getWorld("world"), 12, 100, 233));
//        clan.setAlliances(new HashSet<>(){{
//            add("ZTWO");
//            add("PERSIK");
//        }});
//
//        clan.setModerators(new HashSet<>(){{
//            add("Fanerka");
//            add("KotenokMoy");
//        }});
//
//        clan.setMembers(new HashSet<>(){{
//            add("Izichka");
//            add("Lolshik");
//        }});
//        clan.setPvp(true);
//        clan.setOpenCH(true);
//        clan.setExp(1200);
//        clan.setLevel(3);
//        clan.setWelcomeMessage("Привет бойчик");
//        dataProvider.saveClanAsync(clan);
    }

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
                .filter(clan ->{
                    if(clan.getOwner().equalsIgnoreCase(player)) return true;
                    if(clan.getMembers() != null && clan.getMembers().contains(player)) return true;
                    if(clan.getModerators() != null && clan.getModerators().contains(player)) return true;
                    return false;
                })
                .findFirst();
        if (first.isEmpty()) return null;
        return first.get();
    }

    public void saveClan(Clan clan){
        dataProvider.saveClanAsync(clan);
    }

    public void reloadClanDataAsync(Clan clan, ProviderCallBack<Clan> callBack){
       dataProvider.loadClanByIDAsync(clan.getClanID(), (clan1)->{
           clan.of(clan1);
           callBack.callBack(clan);
       });
    }

    public void reloadClanData(Clan clan){
        var nowClan = dataProvider.loadClanByID(clan.getClanID());
        clan.of(clan);
    }

}
