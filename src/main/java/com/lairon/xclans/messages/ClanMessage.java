package com.lairon.xclans.messages;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public enum ClanMessage {

    PREFIX, DONT_HAVE_PERMISSION, RELOAD, RELOAD_DESCRIPTION, AVAILABLE_COMMANDS, COMMAND_NOT_FOUND, AVAILABLE_COMMANDS_STYLE;

    private static FileConfiguration config;
    private static File file;
    private static final String MESSAGES_PATH = "Messages";

    public String toMessage(){
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', toMessageRaw());
    }

    public String getPrefix(){
        String message = config.getString(MESSAGES_PATH + "." + PREFIX.name());
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String toMessageRaw(){
        return config.getString(MESSAGES_PATH + "." + this.name());
    }

    public static void set(FileConfiguration configg, File filee){
        config = configg;
        file = filee;
    }

    public static void reload() throws IOException, InvalidConfigurationException {
        config.load(file);
    }
}
