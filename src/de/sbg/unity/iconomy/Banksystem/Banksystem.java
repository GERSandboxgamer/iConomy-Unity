package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Npc.NpcSystem;
import de.sbg.unity.iconomy.Events.Business.AddBusinessAccount;
import de.sbg.unity.iconomy.Events.Money.PlayerAddBankEvent;
import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.Utils.BusinessAccountPermission;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

/**
 * The Banksystem class handles the banking system within the iConomy plugin. It manages both player accounts
 * and factory accounts through separate systems. It provides methods to retrieve, add, and check the existence
 * of bank accounts, both for players and factories.
 */
public class Banksystem {

    public final PlayerSystem PlayerSystem;
    public final BusinessBankSystem BusinessBankSystem;
    private final icConsole Console;
    public NpcSystem npcSystem;

    /**
     * Constructor for the Banksystem class.
     * 
     * @param plugin The iConomy plugin instance.
     * @param Console The console used for logging and output.
     */
    public Banksystem(iConomy plugin, icConsole Console) {
        this.Console = Console;
        this.PlayerSystem = new PlayerSystem(plugin, Console);
        this.BusinessBankSystem = new BusinessBankSystem(plugin, Console);
        this.npcSystem = new NpcSystem(plugin);
    }

    /**
     * Retrieves all bank accounts, including player and factory accounts.
     * 
     * @return A List of all BankAccount objects.
     */
    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> list = new ArrayList<>();
        PlayerSystem.getPlayerAccounts().values().forEach(pa -> {
            list.add(pa);
        });

        BusinessBankSystem.getBusinessAccounts().forEach(fa -> {
            list.add(fa);
        });
        return list;
    }

    /**
     * Gets the total number of bank accounts, including both player and factory accounts.
     * 
     * @return The number of bank accounts.
     */
    public int getBankAccountAmounth() {
        return getAllBankAccounts().size();
    }

    /**
     * The PlayerSystem class manages all player bank accounts within the iConomy plugin.
     * It handles adding, retrieving, and checking player accounts, and integrates with the event system.
     */
    public class PlayerSystem {

        private final iConomy plugin;
        private final icConsole Console;
        private final HashMap<String, PlayerAccount> PlayerAccounts;

        /**
         * Constructor for the PlayerSystem class.
         * 
         * @param plugin The iConomy plugin instance.
         * @param Console The console used for logging and output.
         */
        public PlayerSystem(iConomy plugin, icConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            this.PlayerAccounts = new HashMap<>();
        }

        /**
         * Adds a new bank account for a player.
         * 
         * @param player The player for whom the account is being created.
         * @return The created PlayerAccount, or null if the account could not be created.
         */
        public PlayerAccount addPlayerAccount(Player player) {
            return addPlayerAccount(player.getUID());
        }

        /**
         * Adds a new bank account for a player, specified by UID.
         * 
         * @param uid The UID of the player.
         * @return The created PlayerAccount, or null if the account could not be created.
         */
        public PlayerAccount addPlayerAccount(String uid) {
            PlayerAccount pa = new PlayerAccount(plugin, Console, uid);
            Player p = Server.getPlayerByUID(uid);
            PlayerAddBankEvent event = new PlayerAddBankEvent(p, pa);
            plugin.triggerEvent(event);
            if (!event.isCancelled()) {
                try {
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
                } catch (IOException ex) {
                    
                }
                return PlayerAccounts.put(uid, pa);
            }
            return null;
        }

        /**
         * Retrieves all player accounts in the system.
         * 
         * @return A HashMap containing all PlayerAccount objects, keyed by player UID.
         */
        public HashMap<String, PlayerAccount> getPlayerAccounts() {
            return PlayerAccounts;
        }

        /**
         * Gets the total number of player accounts.
         * 
         * @return The number of player accounts.
         */
        public int getPlayerAccountsAmounth() {
            return PlayerAccounts.values().size();
        }

        /**
         * Checks if a specific player has a bank account.
         * 
         * @param player The player to check.
         * @return True if the player has a bank account, false otherwise.
         */
        public boolean hasPlayerAccount(Player player) {
            if (player != null) {
                return hasPlayerAccount(player.getUID());
            }
            return false;
        }

        /**
         * Checks if a specific player, identified by UID, has a bank account.
         * 
         * @param uid The UID of the player.
         * @return True if the player has a bank account, false otherwise.
         */
        public boolean hasPlayerAccount(String uid) {
            boolean b = getPlayerAccount(uid) != null;
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("Banksystem", "hasPlayerAccount = " + b);
            }
            return b;
        }

        /**
         * Retrieves a player account for a specific player.
         * 
         * @param player The player whose account is being retrieved.
         * @return The PlayerAccount object, or null if no account is found.
         */
        public PlayerAccount getPlayerAccount(Player player) {
            if (player != null) {
                return getPlayerAccount(player.getUID());
            }
            return null;
        }

        /**
         * Retrieves all player accounts that a specific player has access to.
         * 
         * @param player The player whose accounts are being retrieved.
         * @return A List of PlayerAccount objects the player can access.
         */
        public List<PlayerAccount> getPlayerAccounts(Player player) {
            List<PlayerAccount> accounts = new ArrayList<>();
            if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                accounts.add(plugin.Bankystem.PlayerSystem.getPlayerAccount(player));
            }
            for (PlayerAccount pa : PlayerAccounts.values()) {
                if (pa.isMember(player)) {
                    if (pa.getMember(player).hasPermission(PlayerAccountPermission.SHOW_ACCOUNT)) {
                        accounts.add(pa);
                    }
                }
            }
            return accounts;
        }

        /**
         * Gets the number of player accounts a specific player has access to.
         * 
         * @param player The player whose accounts are being counted.
         * @return The number of accounts the player can access.
         */
        public int getPlayerAccountsAmounth(Player player) {
            return getPlayerAccounts(player).size();
        }

        /**
         * Retrieves a player account by UID or name.
         * 
         * @param uid The UID or name of the player account.
         * @return The PlayerAccount object, or null if no account is found.
         */
        public PlayerAccount getPlayerAccount(String uid) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("getPlayerAccount", "Show: " + uid);
            }
            if (PlayerAccounts.get(uid) == null) {
                for (PlayerAccount pa : PlayerAccounts.values()) {
                    if (pa.getLastKnownOwnerName().equals(uid)) {
                        if (plugin.Config.Debug > 0) {
                            Console.sendDebug("getPlayerAccount", "Account-Name: " + pa.getLastKnownOwnerName());
                        }
                        return pa;
                    }
                }
            } else {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("getPlayerAccount", "Account-Name: " + PlayerAccounts.get(uid).getLastKnownOwnerName());
                }
                return PlayerAccounts.get(uid);
            }
            return null;
        }
    }

    /**
     * The BusinessBankSystem class manages all factory bank accounts within the iConomy plugin.
     * It handles adding, retrieving, and checking factory accounts, and integrates with the event system.
     */
    public class BusinessBankSystem {

        private final iConomy plugin;
        private final icConsole Console;
        private final HashMap<Business, BusinessAccount> BusinessAccounts;

        /**
         * Constructor for the BusinessBankSystem class.
         * 
         * @param plugin The iConomy plugin instance.
         * @param Console The console used for logging and output.
         */
        public BusinessBankSystem(iConomy plugin, icConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            this.BusinessAccounts = new HashMap<>();
        }

        /**
         * Checks if a factory has a bank account.
         * 
         * @param factory The factory to check.
         * @return True if the factory has a bank account, false otherwise.
         */
        public boolean hasBusinessAccount(Business factory) {
            return BusinessAccounts.containsKey(factory);
        }

        /**
         * Adds a new bank account for a factory.
         * 
         * @param player The player creating the account.
         * @param factory The factory for which the account is being created.
         * @return The created BusinessAccount, or null if the account could not be created.
         * @throws SQLException If a database error occurs.
         * @throws IOException If an I/O error occurs.
         */
        public BusinessAccount addBusinessAccount(Player player, Business factory) throws SQLException, IOException {
            if (!hasBusinessAccount(factory)) {
                int id = plugin.Databases.Business.TabBank.add(factory);
                AddBusinessAccount evt = new AddBusinessAccount(player, factory);
                plugin.triggerEvent(evt);
                BusinessAccount fa = new BusinessAccount(plugin, Console, factory, id);
                return BusinessAccounts.put(factory, fa);
            }
            return null;
        }

        /**
         * Retrieves all factory accounts in the system as a HashMap.
         * 
         * @return A HashMap containing all BusinessAccount objects, keyed by Business.
         */
        public HashMap<Business, BusinessAccount> getHashBusinessAccounts() {
            return BusinessAccounts;
        }

        /**
         * Retrieves all factory accounts in the system as a Collection.
         * 
         * @return A Collection of all BusinessAccount objects.
         */
        public Collection<BusinessAccount> getBusinessAccounts() {
            return BusinessAccounts.values();
        }

        /**
         * Gets the total number of factory accounts.
         * 
         * @return The number of factory accounts.
         */
        public int getBusinessAccountsAmounth() {
            return getBusinessAccounts().size();
        }

        /**
         * Retrieves all factory accounts that a specific player has access to.
         * 
         * @param player The player whose accounts are being retrieved.
         * @return A Collection of BusinessAccount objects the player can access.
         */
        public Collection<BusinessAccount> getBusinessAccounts(Player player) {
            Collection<BusinessAccount> accounts = new ArrayList<>();
            for (BusinessAccount fa : getBusinessAccounts()) {
                if (fa.isMember(player)) {
                    if (fa.getMember(player).hasPermission(BusinessAccountPermission.USE_ACCOUNT)) {
                        accounts.add(fa);
                    }
                } else if (fa.isOwner(player)) {
                    accounts.add(fa);
                }
            }
            return accounts;
        }

        /**
         * Gets the number of factory accounts a specific player has access to.
         * 
         * @param player The player whose accounts are being counted.
         * @return The number of accounts the player can access.
         */
        public int getBusinessAccountsAmounth(Player player) {
            return getBusinessAccounts(player).size();
        }

        /**
         * Retrieves a factory account for a specific factory.
         * 
         * @param factory The factory whose account is being retrieved.
         * @return The BusinessAccount object, or null if no account is found.
         */
        public BusinessAccount getBusinessAccount(Business factory) {
            return BusinessAccounts.get(factory);
        }

        /**
         * Retrieves a factory account by the factory's ID.
         * 
         * @param factoryID The ID of the factory.
         * @return The BusinessAccount object, or null if no account is found.
         */
        public BusinessAccount getBusinessAccount(int factoryID) {
            for (BusinessAccount fa : getBusinessAccounts()) {
                if (fa.getBusiness().getID() == factoryID) {
                    return fa;
                }
            }
            return null;
        }
    }
}
