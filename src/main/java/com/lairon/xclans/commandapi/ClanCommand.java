package com.lairon.xclans.commandapi;

import com.lairon.xclans.XClans;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ClanCommand {

    private XClans xClans;
    private RegisteredClans registeredClans;
    private RegisteredCommands registeredCommands;

    public ClanCommand(XClans xClans, RegisteredClans registeredClans, RegisteredCommands registeredCommands) {
        this.xClans = xClans;
        this.registeredClans = registeredClans;
        this.registeredCommands = registeredCommands;
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public abstract String argument();
    public abstract String permission();
    public abstract String description();

    public RegisteredClans getRegisteredClans() {
        return registeredClans;
    }

    public XClans getXClans() {
        return xClans;
    }

    public RegisteredCommands getRegisteredCommands() {
        return registeredCommands;
    }
}
