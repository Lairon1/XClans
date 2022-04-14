package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.settings.DataProviderSettings;

public class MySqlDataProvider implements DataProvider{

    private DataProviderSettings settings;

    public MySqlDataProvider(DataProviderSettings settings) {
        this.settings = settings;
    }


    @Override
    public Clan[] loadAllClans() {
        return new Clan[0];
    }

    @Override
    public void saveClan(Clan clan) {

    }

    @Override
    public Clan loadClanByID(String id) {
        return null;
    }
}
