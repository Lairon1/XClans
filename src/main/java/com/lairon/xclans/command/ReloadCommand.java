package com.lairon.xclans.command;

import com.lairon.xclans.api.XClansAPI;
import com.lairon.xclans.api.commandapi.ClanCommand;
import com.lairon.xclans.loader.SettingsLoader;
import com.lairon.xclans.manager.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends ClanCommand {

    private SettingsLoader settingsLoader = XClansAPI.getSettingsLoader();
    private ConfigManager configManager = XClansAPI.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        configManager.reloadConfig();
        settingsLoader.loadDataProviderSettings();
        settingsLoader.loadClanSettings();
        sender.sendMessage("Конфиг успешно перезагружен!");
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
