package com.lairon.xclans;

import com.lairon.xclans.api.XClansAPI;
import com.lairon.xclans.command.ClanCommandExecutor;
import com.lairon.xclans.command.ReloadCommand;
import com.lairon.xclans.data.DataProvider;
import com.lairon.xclans.manager.SettingsManager;
import com.lairon.xclans.manager.ConfigManager;
import com.lairon.xclans.messages.ClanMessage;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class XClans extends JavaPlugin {

    private Logger logger = getLogger();
    private File langFile = new File(getDataFolder() + File.separator + "lang.yml");
    private FileConfiguration landConfig = YamlConfiguration.loadConfiguration(langFile);

    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager(this);

        SettingsManager settingsManager = new SettingsManager(configManager);

        DataProvider dataProvider = null;
        try {
            dataProvider = new DataProvider(settingsManager.loadDataProviderSettings());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RegisteredClans registeredClans = new RegisteredClans(dataProvider, this);

        RegisteredCommands registeredCommands = new RegisteredCommands();

        ClanCommandExecutor clanCommandExecutor = new ClanCommandExecutor(registeredCommands);

        Bukkit.getPluginCommand("clan").setExecutor(clanCommandExecutor);
        Bukkit.getPluginCommand("clan").setTabCompleter(clanCommandExecutor);

        if (!langFile.exists()) saveResource(langFile.getName(), false);


        ClanMessage.set(landConfig, langFile);
        try {
            ClanMessage.reload();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        new XClansAPI(registeredClans, registeredCommands, settingsManager);

        initDefaultCommands(registeredCommands);
    }

    private void initDefaultCommands(RegisteredCommands registeredCommands) {
        registeredCommands.registerCommand(ReloadCommand.class);
    }

    @Override
    public void onDisable() {
    }

}
