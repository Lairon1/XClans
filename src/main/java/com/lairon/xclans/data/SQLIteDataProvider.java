package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;

public class SQLIteDataProvider implements DataProvider{



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
