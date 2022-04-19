package com.lairon.xclans.command;

import com.lairon.xclans.api.commandapi.ClanCommand;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClanCommandExecutor implements CommandExecutor, TabCompleter {

    private RegisteredCommands registeredCommands;

    public ClanCommandExecutor(RegisteredCommands registeredCommands) {
        this.registeredCommands = registeredCommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return null;
        ClanCommand clanCommand = registeredCommands.getCommandInstanceByArgument(args[0]);
        if (clanCommand == null) return null;
        if (!sender.hasPermission(clanCommand.permission())) return null;

        if (args.length == 1)
            return new ArrayList<>(sortArguments(registeredCommands.getAllRegisteredCommands(), args[0]));
        else
            return new ArrayList<>(sortArguments(clanCommand.onTabComplete(sender, command, alias, args), args[args.length]));

    }

    @NotNull
    private Collection<String> sortArguments(Collection<String> existingArgs, String input) {
        var sorted = existingArgs.stream().filter(s -> s.equalsIgnoreCase(input)).collect(Collectors.toList());
        return sorted;
    }
}
