package com.lairon.xclans.clan;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class Clan {

    private String clanID;
    private String owner;
    private HashSet<String> members;
    private HashSet<String> moderators;
    private String welcomeMessage;
    private int bank;
    private int exp;
    private String date;
    private String clanTag;
    private Location clanHome;
    private boolean openCH;
    private int level;
    private boolean pvp;
    private HashSet<String> alliances;

    public Clan(String clanID, Player owner) {
        this(clanID, owner.getName());
    }

    public Clan(String clanID, String owner) {
        this.clanID = clanID;
        this.owner = owner;
    }

    public void sendMessageForMembers(String message){
        HashSet<Player> allOnlineMembers = onlineMembers();
        allOnlineMembers.forEach(member -> member.sendMessage(message));
    }

    public void sendMessageForMembers(BaseComponent message){
        HashSet<Player> allOnlineMembers = onlineMembers();
        allOnlineMembers.forEach(member -> member.sendMessage(message));
    }

    public HashSet<Player> onlineMembers(){
        HashSet<Player> allOnlineMembers = new HashSet<>();

        Player ownerPlayer = Bukkit.getPlayerExact(owner);
        if(ownerPlayer != null) allOnlineMembers.add(ownerPlayer);

        for (String moderator : moderators) {
            Player moderatorPlayer = Bukkit.getPlayerExact(moderator);
            if(moderatorPlayer != null) allOnlineMembers.add(moderatorPlayer);
        }

        for (String member : members) {
            Player memberPlayer = Bukkit.getPlayerExact(member);
            if(memberPlayer != null) allOnlineMembers.add(memberPlayer);
        }

        return allOnlineMembers;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Clan clan)) return false;
        return clan.getClanID().equals(clanID);
    }

    public String getClanID() {
        return clanID;
    }

    public void setClanID(String clanID) {
        this.clanID = clanID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashSet<String> getMembers() {
        return members;
    }

    public void setMembers(HashSet<String> members) {
        this.members = members;
    }

    public HashSet<String> getModerators() {
        return moderators;
    }

    public void setModerators(HashSet<String> moderators) {
        this.moderators = moderators;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClanTag() {
        return clanTag;
    }

    public void setClanTag(String clanTag) {
        this.clanTag = clanTag;
    }

    public Location getClanHome() {
        return clanHome;
    }

    public void setClanHome(Location clanHome) {
        this.clanHome = clanHome;
    }

    public boolean isOpenCH() {
        return openCH;
    }

    public void setOpenCH(boolean openCH) {
        this.openCH = openCH;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public HashSet<String> getAlliances() {
        return alliances;
    }

    public void setAlliances(HashSet<String> alliances) {
        this.alliances = alliances;
    }
}
