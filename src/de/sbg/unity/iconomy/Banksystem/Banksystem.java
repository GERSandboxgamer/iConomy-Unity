package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryAccount;
import de.sbg.unity.iconomy.Events.Money.PlayerAddBankEvent;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

public class Banksystem {

    public final PlayerSystem PlayerSystem;
    public final FactoryBankSystem FactoryBankSystem;
    private final icConsole Console;

    public Banksystem(iConomy plugin, icConsole Console) {
        this.Console = Console;
        this.PlayerSystem = new PlayerSystem(plugin, Console);
        this.FactoryBankSystem = new FactoryBankSystem(plugin, Console);
    }

    public class PlayerSystem {

        private final iConomy plugin;
        private final icConsole Console;

        private final HashMap<String, PlayerAccount> PlayerAccounts;

        public PlayerSystem(iConomy plugin, icConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            this.PlayerAccounts = new HashMap<>();
        }

        public PlayerAccount addPlayerAccount(Player player, long startamouth) {
            return addPlayerAccount(player.getUID());
        }

        public PlayerAccount addPlayerAccount(String uid) {
            PlayerAccount pa = new PlayerAccount(plugin, uid);
            Player p = Server.getPlayerByUID(uid);
            PlayerAddBankEvent event = new PlayerAddBankEvent(p, pa);
            plugin.triggerEvent(event);
            if (!event.isCancelled()) {
                try{
                    plugin.Databases.Money.Bank.add(pa);
                } catch (SQLException ex) {
                    Console.sendErr("addPlayerAccount", "========== iConomy-Exception ==========");
                    Console.sendErr("addPlayerAccount", "Msg: Can not add PlayerAccount");
                    Console.sendErr("addPlayerAccount", "EX-Msg: " + ex.getMessage());
                    Console.sendErr("addPlayerAccount", "EX-SQL: " + ex.getSQLState());
                    for (StackTraceElement st : ex.getStackTrace()) {
                        Console.sendErr("addPlayerAccount", st.toString());
                    }
                    Console.sendErr("addPlayerAccount", "=======================================");
                    return null;
                }
                return PlayerAccounts.put(uid, pa);
            }
            return null;
        }

        public HashMap<String, PlayerAccount> getPlayerAccounts() {
            return PlayerAccounts;
        }

        public boolean hasPlayerAccount(Player player) {
            if (player != null) {
                return hasPlayerAccount(player.getUID());
            }
            return false;
        }

        public boolean hasPlayerAccount(String uid) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("Banksystem", "hasPlayerAccount = " + PlayerAccounts.containsKey(uid));
            }
            return PlayerAccounts.containsKey(uid);
        }

        public PlayerAccount getPlayerAccount(Player player) {
            if (player != null) {
                return getPlayerAccount(player.getUID());
            }
            return null;
        }

        public PlayerAccount getPlayerAccount(String st) {
            if (PlayerAccounts.get(st) == null) {
                for (PlayerAccount pa2 : PlayerAccounts.values()) {
                    if (pa2.getLastKnownOwnerName().equals(st)) {
                        return pa2;
                    }
                }
            }
            return  PlayerAccounts.get(st);
        }

    }

    public class FactoryBankSystem {

        private final iConomy plugin;
        private final icConsole Console;
        private final HashMap<Factory, FactoryAccount> FactoryAccounts;

        public FactoryBankSystem(iConomy plugin, icConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            this.FactoryAccounts = new HashMap<>();
        }

        public boolean hasFactoryAccount(Factory factory) {
            return FactoryAccounts.containsKey(factory);
        }

        public FactoryAccount addFactoryAccount(Player player, Factory factory) throws SQLException, IOException {
            if (!hasFactoryAccount(factory)) {
                int id = plugin.Databases.Factory.TabBank.add(factory);
                AddFactoryAccount evt = new AddFactoryAccount(player, factory);
                plugin.triggerEvent(evt);
                FactoryAccount fa = new FactoryAccount(plugin, factory, id);
                return FactoryAccounts.put(factory, fa);
            }
            return null;
        }

        public HashMap<Factory, FactoryAccount> getHashFactoryAccounts() {
            return FactoryAccounts;
        }

        public Collection<FactoryAccount> getFactoryAccounts() {
            return FactoryAccounts.values();
        }

        public FactoryAccount getFactoryAccount(Factory factory) {
            return FactoryAccounts.get(factory);
        }

        public FactoryAccount getFactoryAccount(int factoryID) {
            for (FactoryAccount fa : getFactoryAccounts()) {
                if (fa.getFactory().getID() == factoryID) {
                    return fa;
                }
            }
            return null;
        }

    }

}
