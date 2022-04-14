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

    public SettingsLoader(ConfigManager config) {
        this.config = config;
    }

    public DataProviderSettings loadDataProviderSettings(){
        DataProviderSettingsImpl settings = new DataProviderSettingsImpl();
        settings.isMySqlEnabled = config.getConfig().getBoolean(MYSQL_ENABLE);
        settings.userName = config.getConfig().getString(MYSQL_USERNAME);
        settings.dbName = config.getConfig().getString(MYSQL_DBNAME);
        settings.ip = config.getConfig().getString(MYSQL_IP);
        settings.password = config.getConfig().getString(MYSQL_PASSWORD);
        settings.updateTime = config.getConfig().getInt(MYSQL_UPDATE_TIME);
        return settings;
    }

    public ClanSettings loadClanSettings(){
        ClanSettingsImpl settings = new ClanSettingsImpl();
        settings.idPattern = config.getConfig().getString(CLAN_REGEX);
        settings.createMoney = config.getConfig().getInt(CLAN_CREATE);
        settings.homeMoney = config.getConfig().getInt(CLAN_HOME);
        settings.colorMoney = config.getConfig().getInt(CLAN_COLOR);
        return settings;
    }

    private class ClanSettingsImpl implements ClanSettings {

        private String idPattern;
        private int createMoney;
        private int colorMoney;
        private int homeMoney;

        public String getIdPattern() {
            return idPattern;
        }

        public int getCreateMoney() {
            return createMoney;
        }

        public int getColorMoney() {
            return colorMoney;
        }

        public int getHomeMoney() {
            return homeMoney;
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

        public boolean isMySqlEnabled() {
            return isMySqlEnabled;
        }

        public String getUserName() {
            return userName;
        }

        public String getDbName() {
            return dbName;
        }

        public String getIp() {
            return ip;
        }

        public String getPassword() {
            return password;
        }

        public long getUpdateTime() {
            return updateTime;
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
