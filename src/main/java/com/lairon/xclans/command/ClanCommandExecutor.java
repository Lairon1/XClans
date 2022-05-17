package com.lairon.xclans.command;

import com.lairon.xclans.api.commandapi.ClanCommand;
import com.lairon.xclans.messages.ClanMessage;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ClanCommandExecutor implements CommandExecutor, TabCompleter {

    private RegisteredCommands registeredCommands;

    public ClanCommandExecutor(RegisteredCommands registeredCommands) {
        this.registeredCommands = registeredCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ClanMessage.AVAILABLE_COMMANDS.toMessage());
            ArrayList<String> commands = new ArrayList<>();
            for (ClanCommand reg : registeredCommands.getAllRegisteredCommands()) {
                if(!sender.hasPermission(reg.getPermission())) continue;
                commands.add(ChatColor.translateAlternateColorCodes('&', "    " + ClanMessage.AVAILABLE_COMMANDS_STYLE.toMessageRaw()
                        .replace("{COMMAND}", "/" + reg.getArgument())
                        .replace("{DESCRIPTION}", reg.getDescription())
                ));
            }
            sender.sendMessage(String.join("\n", commands));
            return false;
        }
        ClanCommand clanCommand = registeredCommands.getCommandInstanceByArgument(args[0]);
        if (clanCommand == null) {
            sender.sendMessage(ClanMessage.COMMAND_NOT_FOUND.toMessage());
            return false;
        }
        if (!sender.hasPermission(clanCommand.getPermission())) {
            sender.sendMessage(ClanMessage.DONT_HAVE_PERMISSION.toMessage());
            return false;
        }
        return clanCommand.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return null;
        if(args.length == 1){
            Collection<String> registeredArgs = new ArrayList<>();
            registeredCommands.getAllRegisteredCommands().forEach(c -> {
                if(!sender.hasPermission(c.getPermission())) return;
                registeredArgs.add(c.getArgument());
            });
            return (List<String>) sortArguments(registeredArgs, args[0]);
        }else{
            ClanCommand clanCommand = registeredCommands.getCommandInstanceByArgument(args[0]);
            if(clanCommand == null) return null;
            if(!sender.hasPermission(clanCommand.getPermission())) return null;
            return (List<String>) sortArguments(clanCommand.onTabComplete(sender, command, alias, args), args[args.length-1]);
        }
    }

    @NotNull
    private Collection<String> sortArguments(Collection<String> existingArgs, String input) {
        List<String> collect = existingArgs.stream().sorted((o1, o2) -> {
            String u1 = o1.toUpperCase(Locale.ROOT), u2 = o2.toUpperCase(Locale.ROOT), inputU = input.toUpperCase(Locale.ROOT);
            if (u1.startsWith(inputU)) return -1;
            if (u2.startsWith(inputU)) return 1;
            return 0;
        }).collect(Collectors.toList());
        return collect;
    }
}
