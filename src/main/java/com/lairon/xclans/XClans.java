package com.lairon.xclans;

import com.lairon.xclans.command.ReloadCommand;
import com.lairon.xclans.commandapi.ClanCommandExecutor;
import com.lairon.xclans.loader.SettingsLoader;
import com.lairon.xclans.manager.ConfigManager;
import com.lairon.xclans.manager.DataProviderManager;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class XClans extends JavaPlugin {

    private ConfigManager configManager;
    private SettingsLoader settingsLoader;
    private DataProviderManager dataProviderManager;
    private RegisteredClans registeredClans;
    private RegisteredCommands registeredCommands;
    private ClanCommandExecutor clanCommandExecutor;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        settingsLoader = new SettingsLoader(configManager);
        dataProviderManager = new DataProviderManager(settingsLoader.loadDataProviderSettings());
        registeredClans = new RegisteredClans();
        registeredCommands = new RegisteredCommands(this, registeredClans);
        clanCommandExecutor = new ClanCommandExecutor(registeredCommands);

        Bukkit.getPluginCommand("clan").setExecutor(clanCommandExecutor);


    }

    private void initDefaultCommands(){
        registeredCommands.registerCommand(ReloadCommand.class);
    }

    @Override
    public void onDisable() {
    }

    public RegisteredClans getRegisteredClans() {
        return registeredClans;
    }

    public RegisteredCommands getRegisteredCommands() {
        return registeredCommands;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
