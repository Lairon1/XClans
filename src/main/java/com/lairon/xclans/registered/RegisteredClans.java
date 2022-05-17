package com.lairon.xclans.registered;

import com.lairon.xclans.XClans;
import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.data.DataProvider;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class RegisteredClans {

    private final Set<Clan> registeredClans = new HashSet<>();
    private DataProvider dataProvider;
    private XClans xClans;

    public RegisteredClans(DataProvider dataProvider, XClans xClans) {
        this.dataProvider = dataProvider;
        this.xClans = xClans;
    }

    public void registerClan(@NotNull Clan clan) {
        Validate.notNull(clan, "Clan cannot be null");
        if (getClanByID(clan.getClanID()) != null)
            throw new IllegalArgumentException("This clan is already registered");
        if (getPlayerClan(clan.getOwner()) != null)
            throw new IllegalArgumentException("The owner of the clan is already a member of the clan");
        registeredClans.add(clan);
    }

    @Nullable
    public Clan getClanByID(@NotNull String id) {
        Validate.notNull(id, "Clan id cannot be null");
        Validate.notEmpty(id, "Clan id cannot be empty");
        if (registeredClans.isEmpty()) return null;
        Optional<Clan> first = registeredClans
                .stream()
                .filter(clan ->
                        clan.getClanID().equals(id))
                .findFirst();
        return first.get();
    }

    @Nullable
    public Clan getPlayerClan(Player player) {
        return getPlayerClan(player.getName());
    }

    @Nullable
    public Clan getPlayerClan(@NotNull String player) {
        Validate.notNull(player);
        if (registeredClans.isEmpty()) return null;
        Optional<Clan> first = registeredClans
                .stream()
                .filter(clan ->{
                    if(clan.getOwner().equalsIgnoreCase(player)) return true;
                    if(clan.getMembers() != null && clan.getMembers().contains(player)) return true;
                    if(clan.getModerators() != null && clan.getModerators().contains(player)) return true;
                    return false;
                })
                .findFirst();
        return first.get();
    }

    public void saveClan(@NotNull Clan clan){
        Validate.notNull(clan, "Clan id cannot be null");
        Bukkit.getScheduler().runTaskAsynchronously(xClans, ()-> {
            try {
                dataProvider.saveClan(clan);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void reloadClanData(@NotNull Clan clan, @Nullable Consumer<Clan> callBack) {
        Validate.notNull(clan, "Clan cannot be null");
        Bukkit.getScheduler().runTaskAsynchronously(xClans, ()->{
            try {
                Clan nowClan = dataProvider.loadClanByID(clan.getClanID());
                if(nowClan == null){
                    if(callBack != null)
                        callBack.accept(null);
                    return;
                }

                clan.of(nowClan);
                if(callBack != null)
                callBack.accept(nowClan);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @NotNull
    public HashSet<Clan> getAllClans(){
        return new HashSet<>(registeredClans);
    }

}
