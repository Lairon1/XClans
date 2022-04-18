package com.lairon.xclans.settings;

public interface ClanSettings {

    String getIdPattern();

    int getCreateMoney();

    int getColorMoney();

    int getHomeMoney();

    void reload();
}
