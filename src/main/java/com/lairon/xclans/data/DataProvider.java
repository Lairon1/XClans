package com.lairon.xclans.data;

import com.lairon.xclans.clan.Clan;

public interface DataProvider {

    Clan[] loadAllClans();
    void saveClan(Clan clan);
    Clan loadClanByID(String id);

}
