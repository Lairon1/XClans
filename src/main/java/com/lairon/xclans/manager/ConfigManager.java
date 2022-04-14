package com.lairon.xclans.manager;

import com.lairon.xclans.XClans;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private XClans main;
    private File configFile;
    private FileConfiguration config;

    public ConfigManager(XClans main) {
        this.main = main;
        configFile = new File(main.getDataFolder() + File.separator + "config.yml");
        initConfig();
    }

    private void initConfig(){
        if(!configFile.exists()) main.saveDefaultConfig();
        config = main.getConfig();
    }

    public void reloadConfig() throws IOException, InvalidConfigurationException {
        config.load(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
