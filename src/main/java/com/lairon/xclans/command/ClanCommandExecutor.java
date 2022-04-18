package com.lairon.xclans.command;

import com.lairon.xclans.api.commandapi.ClanCommand;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClanCommandExecutor implements CommandExecutor {

    private RegisteredCommands registeredCommands;

    public ClanCommandExecutor(RegisteredCommands registeredCommands) {
        this.registeredCommands = registeredCommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage("Доступные команды");
            return false;
        }

        ClanCommand clanCommand = registeredCommands.getCommandInstanceByArgument(args[0]);
        if (clanCommand == null) {
            sender.sendMessage("Команда не найдена.");
            return false;
        }

        if (!sender.hasPermission(clanCommand.permission())) {
            sender.sendMessage("У вас нет прав");
            return false;
        }

        return clanCommand.onCommand(sender, command, label, args);
    }
}
