package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryAccount;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
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
            return PlayerAccounts.put(uid, pa);
        }

        public HashMap<String, PlayerAccount> getPlayerAccounts() {
            return PlayerAccounts;
        }
        
        public boolean hasPlayerAccount(Player player) {
            return hasPlayerAccount(player.getUID());
        }
        
        public boolean hasPlayerAccount(String uid) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("Banksystem", "hasPlayerAccount = " + PlayerAccounts.containsKey(uid));
            }
            return PlayerAccounts.containsKey(uid);
        }
        
        public PlayerAccount getPlayerAccount(Player player){
            return getPlayerAccount(player.getUID());
        }
        
        public PlayerAccount getPlayerAccount(String uid){
            return PlayerAccounts.get(uid);
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
        
        public FactoryAccount addFactoryAccount(Player player, Factory factory) throws SQLException, IOException{
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
