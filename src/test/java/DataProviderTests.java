import com.lairon.xclans.clan.Clan;
import com.lairon.xclans.data.DataProvider;
import com.lairon.xclans.settings.DataProviderSettings;
import fake.FakeWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;

public class DataProviderTests {

    private DataProviderSettings settings;
    private DataProvider dataProvider;
    private Clan fullClan;
    private Clan emptyClan = new Clan("KAS", "misPis");

    {
        settings = new DataProviderSettings() {
            @Override
            public boolean isMySqlEnabled() {
                return true;
            }

            @Override
            public String getUserName() {
                return "evseev2";
            }

            @Override
            public String getDbName() {
                return "evseev2";
            }

            @Override
            public String getIp() {
                return "kolei.ru";
            }

            @Override
            public String getPassword() {
                return "123456";
            }

            @Override
            public long getUpdateTime() {
                return 1200;
            }

            @Override
            public void reload() {

            }
        };

        try {
            dataProvider = new DataProvider(settings);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fullClan = new Clan("ZTWO", "Lairon1");
        fullClan.setMembers(new HashSet<>(){{
            add("Izichka");
            add("Lolshik");
        }});

        fullClan.setModerators(new HashSet<>(){{
            add("Fanerka");
            add("KotenokMoy");
        }});

        fullClan.setWelcomeMessage("Привет бойчик");
        fullClan.setBalance(1337);
        fullClan.setExp(1200);
        fullClan.setColor(ChatColor.BLUE);
        fullClan.setClanHome(new Location(new FakeWorld(), 12, 100, 233));
        fullClan.setOpenCH(true);
        fullClan.setLevel(3);
        fullClan.setPvp(true);

        fullClan.setAlliances(new HashSet<>(){{
            add("ZTWO");
            add("PERSIK");
        }});


    }

    @Test
    public void fullClanTest() throws SQLException {
        dataProvider.saveClan(fullClan);
        Assert.assertNotNull(dataProvider.loadClanByID(fullClan.getClanID()));
        dataProvider.deleteClan(fullClan.getClanID());
    }

    @Test
    public void emptyClanTest() throws SQLException {
        dataProvider.saveClan(emptyClan);
        Assert.assertNotNull(dataProvider.loadClanByID(emptyClan.getClanID()));
        dataProvider.deleteClan(emptyClan.getClanID());
    }

    @Test
    public void loadAllClansTest() throws SQLException {
        dataProvider.loadAllClans();
    }
}
