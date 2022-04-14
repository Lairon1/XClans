package com.lairon.xclans.command;

import com.lairon.xclans.XClans;
import com.lairon.xclans.commandapi.ClanCommand;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public class ReloadCommand extends ClanCommand {

    public ReloadCommand(XClans xClans, RegisteredClans registeredClans, RegisteredCommands registeredCommands) {
        super(xClans, registeredClans, registeredCommands);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            getXClans().getConfigManager().reloadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        sender.sendMessage("Конфиг успешно перезагружен");
        return false;
    }


    @Override
    public String argument() {
        return "reload";
    }

    @Override
    public String permission() {
        return "xclans.reload";
    }

    @Override
    public String description() {
        return "Перезагрузить конфиги";
    }
}
