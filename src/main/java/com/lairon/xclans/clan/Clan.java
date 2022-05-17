package com.lairon.xclans.clan;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Date;
import java.util.HashSet;

public class Clan implements Listener {

    private String clanID;
    private String owner;
    private HashSet<String> members;
    private HashSet<String> moderators;
    private String welcomeMessage;
    private int balance;
    private int exp;
    private Date date;
    private ChatColor color;
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
        date = new Date();
    }

    public void of(Clan clan){
        clanID = clan.getClanID();
        owner = clan.getOwner();
        members = clan.getMembers();
        moderators = clan.getModerators();
        welcomeMessage = clan.getWelcomeMessage();
        balance = clan.getBalance();
        exp = clan.getExp();
        date = clan.getDate();
        color = clan.getColor();
        clanHome = clan.getClanHome();
        openCH = clan.isOpenCH();
        level = clan.getLevel();
        pvp = clan.isPvp();
        alliances = clan.getAlliances();
    }

    public void sendMessageForMembers(String message) {
        HashSet<Player> allOnlineMembers = onlineMembers();
        allOnlineMembers.forEach(member -> member.sendMessage(message));
    }

    public void sendMessageForMembers(BaseComponent message) {
        HashSet<Player> allOnlineMembers = onlineMembers();
        allOnlineMembers.forEach(member -> member.sendMessage(message));
    }

    public HashSet<Player> onlineMembers() {
        HashSet<Player> allOnlineMembers = new HashSet<>();

        Player ownerPlayer = Bukkit.getPlayerExact(owner);
        if (ownerPlayer != null) allOnlineMembers.add(ownerPlayer);

        for (String moderator : moderators) {
            Player moderatorPlayer = Bukkit.getPlayerExact(moderator);
            if (moderatorPlayer != null) allOnlineMembers.add(moderatorPlayer);
        }

        for (String member : members) {
            Player memberPlayer = Bukkit.getPlayerExact(member);
            if (memberPlayer != null) allOnlineMembers.add(memberPlayer);
        }

        return allOnlineMembers;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Clan)) return false;
        Clan clan = (Clan) obj;
        return clan.getClanID().equalsIgnoreCase(clanID);
    }

    public String getClanID() {
        return clanID;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Clan{" +
                "clanID='" + clanID + '\'' +
                ", owner='" + owner + '\'' +
                ", members=" + members +
                ", moderators=" + moderators +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", balance=" + balance +
                ", exp=" + exp +
                ", date=" + date +
                ", color=" + color +
                ", clanHome=" + clanHome +
                ", openCH=" + openCH +
                ", level=" + level +
                ", pvp=" + pvp +
                ", alliances=" + alliances +
                '}';
    }
}
