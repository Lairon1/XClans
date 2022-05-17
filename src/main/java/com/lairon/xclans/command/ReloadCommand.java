package com.lairon.xclans.command;

import com.lairon.xclans.api.XClansAPI;
import com.lairon.xclans.api.commandapi.ClanCommand;
import com.lairon.xclans.messages.ClanMessage;
import com.lairon.xclans.settings.ClanSettings;
import com.lairon.xclans.settings.DataProviderSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public class ReloadCommand extends ClanCommand {

    private ClanSettings clanSettings = XClansAPI.getClanSettings();
    private DataProviderSettings dataProviderSettings = XClansAPI.getDataProviderSettings();

    public ReloadCommand() {
        super("reload", "XClans.reload", ClanMessage.RELOAD_DESCRIPTION.toMessageRaw());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        clanSettings.reload();
        dataProviderSettings.reload();
        try {
            ClanMessage.reload();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        sender.sendMessage(ClanMessage.RELOAD.toMessage());
        return false;
    }

}
