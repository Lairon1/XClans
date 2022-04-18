package com.lairon.xclans.loader;

import com.lairon.xclans.manager.ConfigManager;
import com.lairon.xclans.settings.ClanSettings;
import com.lairon.xclans.settings.DataProviderSettings;

public class SettingsLoader {

    private ConfigManager config;

    private static final String PROVIDER_PATH = "DataProviderSettings.MySql";

    private static final String CLAN_PATH = "ClanSettings";

    private static final String CLAN_REGEX = CLAN_PATH + "." + "IdRegex";
    private static final String CLAN_CREATE = CLAN_PATH + "." + "CreateMoney";
    private static final String CLAN_COLOR = CLAN_PATH + "." + "ColorMoney";
    private static final String CLAN_HOME = CLAN_PATH + "." + "HomeMoney";

    private static final String MYSQL_ENABLE = PROVIDER_PATH + "." + "Enable";
    private static final String MYSQL_USERNAME = PROVIDER_PATH + "." + "UserName";
    private static final String MYSQL_DBNAME = PROVIDER_PATH + "." + "DataBaseName";
    private static final String MYSQL_IP = PROVIDER_PATH + "." + "IP";
    private static final String MYSQL_PASSWORD = PROVIDER_PATH + "." + "Password";
    private static final String MYSQL_UPDATE_TIME = PROVIDER_PATH + "." + "UpdateTime";

    private DataProviderSettingsImpl dataProviderSettings;
    private ClanSettingsImpl clanSettings;

    public SettingsLoader(ConfigManager config) {
        this.config = config;
    }

    public DataProviderSettings loadDataProviderSettings(){
        return loadDataProviderSettings(dataProviderSettings == null ? new DataProviderSettingsImpl() : dataProviderSettings);

    }

    private DataProviderSettings loadDataProviderSettings(DataProviderSettingsImpl settings){
        settings.isMySqlEnabled = config.getConfig().getBoolean(MYSQL_ENABLE);
        settings.userName = config.getConfig().getString(MYSQL_USERNAME);
        settings.dbName = config.getConfig().getString(MYSQL_DBNAME);
        settings.ip = config.getConfig().getString(MYSQL_IP);
        settings.password = config.getConfig().getString(MYSQL_PASSWORD);
        settings.updateTime = config.getConfig().getInt(MYSQL_UPDATE_TIME);
        dataProviderSettings = settings;
        return settings;
    }

    public ClanSettings loadClanSettings(){
        return loadClanSettings(clanSettings == null ? new ClanSettingsImpl() : clanSettings);
    }


    private ClanSettings loadClanSettings(ClanSettingsImpl settings){
        settings.idPattern = config.getConfig().getString(CLAN_REGEX);
        settings.createMoney = config.getConfig().getInt(CLAN_CREATE);
        settings.homeMoney = config.getConfig().getInt(CLAN_HOME);
        settings.colorMoney = config.getConfig().getInt(CLAN_COLOR);
        clanSettings = settings;
        return settings;
    }

    private class ClanSettingsImpl implements ClanSettings {

        private String idPattern;
        private int createMoney;
        private int colorMoney;
        private int homeMoney;
        @Override
        public String getIdPattern() {
            return idPattern;
        }
        @Override
        public int getCreateMoney() {
            return createMoney;
        }
        @Override
        public int getColorMoney() {
            return colorMoney;
        }
        @Override
        public int getHomeMoney() {
            return homeMoney;
        }

        @Override
        public void reload() {
            loadClanSettings(this);
        }

        @Override
        public String toString() {
            return "ClanSettingsImpl{" +
                    "idPattern='" + idPattern + '\'' +
                    ", createMoney=" + createMoney +
                    ", colorMoney=" + colorMoney +
                    ", homeMoney=" + homeMoney +
                    '}';
        }
    }

    private class DataProviderSettingsImpl implements DataProviderSettings {

        private boolean isMySqlEnabled;

        private String userName;
        private String dbName;
        private String ip;
        private String password;
        private long updateTime;
        @Override
        public boolean isMySqlEnabled() {
            return isMySqlEnabled;
        }
        @Override
        public String getUserName() {
            return userName;
        }
        @Override
        public String getDbName() {
            return dbName;
        }
        @Override
        public String getIp() {
            return ip;
        }
        @Override
        public String getPassword() {
            return password;
        }
        @Override
        public long getUpdateTime() {
            return updateTime;
        }

        @Override
        public void reload() {
            loadDataProviderSettings(this);
        }

        @Override
        public String toString() {
            return "DataProviderSettingsImpl{" +
                    "isMySqlEnabled=" + isMySqlEnabled +
                    ", userName='" + userName + '\'' +
                    ", dbName='" + dbName + '\'' +
                    ", ip='" + ip + '\'' +
                    ", password='" + password + '\'' +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }


}
