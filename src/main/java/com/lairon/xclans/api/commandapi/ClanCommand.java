package com.lairon.xclans.api.commandapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ClanCommand {


    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public abstract String argument();
    public abstract String permission();
    public abstract String description();

}
