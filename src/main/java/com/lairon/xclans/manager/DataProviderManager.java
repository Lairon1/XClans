package com.lairon.xclans.manager;

import com.lairon.xclans.data.DataProvider;
import com.lairon.xclans.data.MySqlDataProvider;
import com.lairon.xclans.data.SQLIteDataProvider;
import com.lairon.xclans.settings.DataProviderSettings;

public class DataProviderManager {

    private DataProviderSettings settings;
    private DataProvider dataProvider;

    public DataProviderManager(DataProviderSettings settings) {
        this.settings = settings;
    }

    public DataProvider getDataProvider() {
        if (dataProvider != null) return dataProvider;

        if (settings.isMySqlEnabled()) dataProvider = new MySqlDataProvider(settings);

        else dataProvider = new SQLIteDataProvider();

        return dataProvider;
    }
}
