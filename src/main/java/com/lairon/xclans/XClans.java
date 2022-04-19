package com.lairon.xclans;

import com.lairon.xclans.api.XClansAPI;
import com.lairon.xclans.command.ClanCommandExecutor;
import com.lairon.xclans.command.ReloadCommand;
import com.lairon.xclans.data.DataProvider;
import com.lairon.xclans.loader.SettingsLoader;
import com.lairon.xclans.manager.ConfigManager;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class XClans extends JavaPlugin {

    private Logger logger = getLogger();

    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager(this);

        SettingsLoader settingsLoader = new SettingsLoader(configManager);

        DataProvider dataProvider = null;
        try {
            dataProvider = new DataProvider(settingsLoader.loadDataProviderSettings());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RegisteredClans registeredClans = new RegisteredClans(dataProvider, this);

        RegisteredCommands registeredCommands = new RegisteredCommands();

        ClanCommandExecutor clanCommandExecutor = new ClanCommandExecutor(registeredCommands);

        Bukkit.getPluginCommand("clan").setExecutor(clanCommandExecutor);

        XClansAPI.set(registeredClans, registeredCommands, settingsLoader, configManager);
    }

    private void initDefaultCommands(RegisteredCommands registeredCommands){
        registeredCommands.registerCommand(ReloadCommand.class);
    }

    @Override
    public void onDisable() {
    }

}
