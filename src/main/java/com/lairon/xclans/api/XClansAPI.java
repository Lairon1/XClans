package com.lairon.xclans.api;

import com.lairon.xclans.manager.SettingsManager;
import com.lairon.xclans.registered.RegisteredClans;
import com.lairon.xclans.registered.RegisteredCommands;
import com.lairon.xclans.settings.ClanSettings;
import com.lairon.xclans.settings.DataProviderSettings;

public class XClansAPI {

    private static RegisteredClans registeredClans;
    private static RegisteredCommands registeredCommands;
    private static ClanSettings clanSettings;
    private static DataProviderSettings dataProviderSettings;
    private static boolean isInit = false;


    public XClansAPI(RegisteredClans clans, RegisteredCommands cmd, SettingsManager settings) {
        if (isInit)
            throw new RuntimeException("Already set");
        isInit = true;
        registeredClans = clans;
        registeredCommands = cmd;
        dataProviderSettings = settings.loadDataProviderSettings();
        clanSettings = settings.loadClanSettings();
    }

    public static RegisteredClans getRegisteredClans() {
        return registeredClans;
    }

    public static RegisteredCommands getRegisteredCommands() {
        return registeredCommands;
    }

    public static ClanSettings getClanSettings() {
        return clanSettings;
    }

    public static DataProviderSettings getDataProviderSettings() {
        return dataProviderSettings;
    }
}
