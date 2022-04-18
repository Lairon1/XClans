package com.lairon.xclans.api;

import com.lairon.xclans.loader.SettingsLoader;
import com.lairon.xclans.manager.ConfigManager;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;

public class XClansAPI {

    private static RegisteredClans registeredClans;
    private static RegisteredCommands registeredCommands;
    private static SettingsLoader settingsLoader;
    private static ConfigManager configManager;

    public static RegisteredClans getRegisteredClans() {
        return registeredClans;
    }

    public static RegisteredCommands getRegisteredCommands() {
        return registeredCommands;
    }

    public static SettingsLoader getSettingsLoader() {
        return settingsLoader;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static void set(RegisteredClans clans, RegisteredCommands cmds, SettingsLoader loader, ConfigManager config) {
        if (registeredClans != null
                || registeredCommands != null
                || settingsLoader != null
                || configManager != null)
            throw new RuntimeException("Already set");
        registeredClans = clans;
        registeredCommands = cmds;
        settingsLoader = loader;
        configManager = config;
    }
}
