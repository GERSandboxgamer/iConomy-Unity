package de.sbg.unity.iconomy;

import com.sun.java.accessibility.util.Translator;
import de.chaoswg.ClassPluginJSONManager;
import de.chaoswg.ToolsAPI;
import de.sbg.unity.configmanager.ConfigData;
import de.sbg.unity.configmanager.ConfigManager;
import de.sbg.unity.iconomy.Banksystem.Banksystem;
import de.sbg.unity.iconomy.CashSystem.CashSystem;
import de.sbg.unity.iconomy.Database.icDatabases;
import de.sbg.unity.iconomy.Business.BusinessSystem;
import de.sbg.unity.iconomy.Listeners.Player.icPlayerListener;
import de.sbg.unity.iconomy.GUI.GUIs;
import de.sbg.unity.iconomy.Listeners.Commands.AdminBusinessCommandListener;
import de.sbg.unity.iconomy.Listeners.Commands.AdminMoneyCommandListener;
import de.sbg.unity.iconomy.Listeners.Commands.PlayerBusinessCommandsListener;
import de.sbg.unity.iconomy.Listeners.Commands.PlayerMoneyCommandListener;
import de.sbg.unity.iconomy.Listeners.Business.BusinessListener;
import de.sbg.unity.iconomy.Listeners.Player.PlayerAtmListener;
import de.sbg.unity.iconomy.Listeners.icInputListener;
import de.sbg.unity.iconomy.Objects.icGameObject;
import de.sbg.unity.iconomy.Objects.icSign;
import de.sbg.unity.iconomy.Utils.Attribute;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.PrefabVorlage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Plugin;
import net.risingworld.api.Server;
import net.risingworld.api.assets.AssetBundle;
import net.risingworld.api.assets.PrefabAsset;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;

/**
 * The iConomy class is the main plugin class responsible for managing the
 * economy system in a Rising World server. It handles the initialization and
 * management of various systems such as the bank, cash, factories, game
 * objects, and plugin configurations.
 */
public class iConomy extends Plugin {

    public icSign Sign;

    /**
     * Database handler for the plugin
     */
    public icDatabases Databases;

    /**
     * The banking system responsible for handling player and business bank
     * accounts.
     */
    public Banksystem Bankystem;

    /**
     * The cash system responsible for managing in-game currency and cash
     * transactions.
     */
    public CashSystem CashSystem;

    /**
     * The business system responsible for managing factories, their members, and
     * their financials.
     */
    public BusinessSystem Business;

    /**
     * The game object system responsible for managing custom game objects such
     * as ATMs or other assets.
     */
    public icGameObject GameObject;

    /**
     * The GUI system responsible for displaying user interfaces such as money
     * info and other custom UIs.
     */
    public GUIs GUI;

    /**
     * Attribute handler for various game objects.
     *
     * @hidden
     */
    public icAttribute Attribute;
    
    public icLanguage Language;
    

    private icConsole Console;
    private Attribute att;
    private ToolsAPI tools;
    private List<Listener> Events;
    private Update update;

    /**
     * Configuration settings for iConomy.
     */
    public Config Config;

    /**
     * Flag to stop the plugin due to database issues.
     *
     * @hidden
     */
    public boolean StopPluginByDB;

    /**
     * Formatter for money handling.
     */
    public MoneyFormate moneyFormat;

    /**
     * Called when the plugin is enabled. Initializes all the systems,
     * configurations, assets, databases, and event listeners.
     */
    @Override
    public void onEnable() {
        Console = new icConsole(this);
        Console.sendInfo("Enabled");
        this.moneyFormat = new MoneyFormate(this, Console);
        this.tools = (ToolsAPI) getPluginByName("ToolsAPI");
        if (tools != null) {
            Console.sendInfo("ini", "Load Config...");
            this.Config = new Config(this, Console);
            try {
                Config.iniConfig();
            } catch (IOException ex) {
                Console.sendErr("Config", "Can not load Config");
            }
            Console.sendInfo("ini", "Load Config...Done!");
            Console.sendInfo("Debug", "Debug = " + Config.Debug);
            Console.sendInfo("ini", "Load Class...");
            
            Console.sendInfo("ini", "Load Class...Attribute");
            this.att = new Attribute(this);
            Console.sendInfo("ini", "Load Class...CashSystem");
            CashSystem = new CashSystem(this, Console);
            Console.sendInfo("ini", "Load Class...Bankystem");
            Bankystem = new Banksystem(this, Console);
            Console.sendInfo("ini", "Load Class...Business");
            Business = new BusinessSystem(this, att);
            Console.sendInfo("ini", "Load Class...GameObject");
            this.GameObject = new icGameObject(this, Console);

            Attribute = new icAttribute();

            List<String> BundleNameList = new ArrayList<>();
            BundleNameList.add("ATM");
            BundleNameList.add("Geldkoffer");

            for (String s : BundleNameList) {
                byte[] bAsset = tools.getResourceFromClass(getClass(), "resources/" + s.toLowerCase() + ".bundle");
                String fileAsset = getPath() + System.getProperty("file.separator") + "Asset" + System.getProperty("file.separator") + Config.AssetVersion + System.getProperty("file.separator") + s.toLowerCase() + ".bundle";
                File file = new File(fileAsset);
                if (!file.exists()) {
                    Console.sendInfo("ini", "Create Bundle: '" + fileAsset + "'");
                    ToolsAPI.writeData(bAsset, fileAsset, this);
                }
                Console.sendInfo("ini", "Load Bundle to plugin: '" + fileAsset + "'");
                AssetBundle bundle = AssetBundle.loadFromFile(fileAsset);
                PrefabAsset asset = PrefabAsset.loadFromAssetBundle(bundle, "pref" + s + ".prefab");
                PrefabVorlage vorlage = new PrefabVorlage(fileAsset, asset);
                GameObject.add(s, vorlage);

            }

            Console.sendInfo("ini", "Load Class...Done!");

            Console.sendInfo("ini-DB", "Load Database...");
            Databases = new icDatabases(this, Console);
            Console.sendInfo("ini-DB", "Load Database...Done");

            Console.sendInfo("ini-DB", "Load all from Database...");
            Databases.loadAll();
            Console.sendInfo("ini-DB", "Load all from Database...Done!");
            
            Console.sendInfo("ini-DB", "Load Sign...");
            Sign = new icSign(this, Console);
            Console.sendInfo("ini-DB", "Load Sign...Done!");
            
            Console.sendInfo("ini-DB", "Load GUIs...");
            GUI = new GUIs(this, Console);
            Console.sendInfo("ini-DB", "Load GUIs...Done!");

            Console.sendInfo("ini", "Load Languages...");
            this.Language = new icLanguage();
            File fileCongigPhat = new File(getPath() + System.getProperty("file.separator") + "Languages");
            if (fileCongigPhat.mkdirs()) {
                Console.sendInfo("ini", "Erstelle: " + fileCongigPhat.getAbsolutePath());
            }
            ClassPluginJSONManager jm = new ClassPluginJSONManager();

            jm.getBanList().add("defaultLanguage");
            String configFile = getPath() + System.getProperty("file.separator") + "Languages" + System.getProperty("file.separator") + "Language v" + this.getDescription("version") + ".json";
            
            Language = (icLanguage) jm.update(Language, configFile);
            Console.sendInfo("ini", "Load Languages...Done!");

            registerEventListener(new PlayerMoneyCommandListener(this, Console));
            registerEventListener(new icPlayerListener(this, Console));
            registerEventListener(new AdminMoneyCommandListener(this, Console));
            registerEventListener(new icInputListener(this, Console));
            registerEventListener(new PlayerAtmListener(this, Console));
            registerEventListener(new AdminBusinessCommandListener(this, Console));
            registerEventListener(new PlayerBusinessCommandsListener(this));
            registerEventListener(new BusinessListener(this));

            Console.sendInfo("Check for Updates...");
            try {
                update = new Update(this, "https://sbgserver.de/downloads/Plugins/risingworld/unity/iConomy/version.txt");
            } catch (IOException | URISyntaxException ioex) {
                Console.sendWarning("Update", ioex.getMessage());
                Console.sendWarning("Update", "Can not connect to Update-Server!");
            }
        }

    }
    

    /**
     * Called when the plugin is disabled. Saves all data to the database and
     * stops the plugin safely.
     */
    @Override
    public void onDisable() {
        Databases.stopSaveTimer();
        if (!StopPluginByDB) {
            Console.sendInfo("DB", "Save all to Database...");
            try {
                Databases.saveAtm();
                Databases.saveAll(true);
                //Databases.Business.getDatabase().close();
                //Databases.Money.getDatabase().close();
                Console.sendInfo("DB", "Save all to Database...Done!");
            } catch (SQLException ex) {
                Console.sendErr("DB", "Can not save all to Database!");
                Console.sendErr("DB", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB", "Ex-SQLState: " + ex.getSQLState());
                ex.printStackTrace();
            } catch (IOException ex) {
                Console.sendErr("DB", "Can not save all to Database!");
                Console.sendErr("DB", "Ex-Msg: " + ex.getMessage());
                ex.printStackTrace();
            }

        }
        Console.sendInfo("Disabled");

    }

    /**
     * Checks whether an update for the plugin is available.
     *
     * @return True if an update is available, false otherwise.
     */
    public boolean hasUpdate() {
        return update.hasUpdate();
    }

    /**
     * Displays the player's cash and optionally bank balance in the GUI.
     *
     * @param player The player for whom the balance is being displayed.
     * @param bank Whether to display both cash and bank balance (true) or only
     * cash (false).
     */
    public void showPlayerMoney(Player player, boolean bank) {
        if (bank) {
            GUI.MoneyInfoGui.showGUI(player, "Cash: " + CashSystem.getCashAsFormatedString(player), "Bank: " + Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
        } else {
            GUI.MoneyInfoGui.showGUI(player, "Cash: " + CashSystem.getCashAsFormatedString(player));
        }
    }
    
    public boolean isPlayerConneted(String uid) {
        for (Player p : Server.getAllPlayers()) {
            if (p.getUID().equals(uid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The Config class handles the configuration settings of the iConomy
     * plugin. It loads the configuration file and sets default values.
     */
    public class Config {

        private final iConomy PLUGIN;
        private final icConsole Console;
        private final ConfigManager Manager;
        private final MoneyFormate mf;
        
        public final String AssetVersion;

        public long PlayerCashStartAmount, PlayerBankStartAmount, PlayerBankAccountCost,
                BusinessCashStartAmount, BusinessBankStartAmount, BusinessCost;
        public String Currency, MoneyFormat, BusinessCreateGroups;
        public float MoneyInfoTime, SaveTimer, SuitcaseTime;
        public int Debug;
        public boolean ShowBalanceAtStart, KillerGetMoney, LostMoneyByDeath, CreateAccountViaCommand,
                Command_Bank_OnlyAdmin, SaveAllByPlayerDisconnect, BusinessPlotByAdmin;
        
        
        /**
         * Constructs the configuration handler.
         *
         * @param plugin The main plugin instance.
         * @param Console The console for logging and output.
         */
        public Config(iConomy plugin, icConsole Console) {
            this.PLUGIN = plugin;
            this.Console = Console;
            this.Manager = (ConfigManager) plugin.getPluginByName("ConfigManager");
            this.mf = new MoneyFormate(plugin, Console);
            this.AssetVersion = "1.0"; //TODO Asset Version
        }

        /**
         * Initializes and loads the configuration settings.
         *
         * @throws IOException If the configuration cannot be loaded.
         */
        public void iniConfig() throws IOException {
            if (Manager != null) {

                ConfigData Data = Manager.newConfig(PLUGIN.getName(), PLUGIN.getPath());
                Data.addCommend("#--------------------------#");
                Data.addCommend("#       iConomy-Config     #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Turn Debug on or off (1 = on)");
                Data.addSetting("Debug", "0");
                Data.addEmptyLine();
                Data.addCommend("#--------------------------#");
                Data.addCommend("#           Main           #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# The Currency");
                Data.addSetting("Currency", "$");
                Data.addEmptyLine();
                Data.addCommend("# Format of the money (Standart: #,##0.00");
                Data.addSetting("MoneyFormat", "#,##0.00");
                Data.addEmptyLine();
                Data.addCommend("# The time of the 'MoneyInfo-GUI' to close in seconds");
                Data.addSetting("MoneyInfoTime", 5);
                Data.addEmptyLine();
                Data.addCommend("# Show the balance in the chat, if player connected to the server");
                Data.addSetting("ShowBalanceAtStart", true);
                Data.addEmptyLine();
                Data.addCommend("# Database save timer in minutes");
                Data.addSetting("SaveTimer", 5);
                Data.addEmptyLine();
                Data.addCommend("# Save all to database, if a player disconnect");
                Data.addSetting("SaveAllByPlayerDisconnect", true);
                Data.addEmptyLine();
                Data.addCommend("#--------------------------#");
                Data.addCommend("#         Commands         #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Open bank gui via command only by admin");
                Data.addSetting("Command_Bank_OnlyAdmin", true);
                Data.addEmptyLine();
                Data.addCommend("# Create a bank account via command");
                Data.addSetting("CreateAccountViaCommand", false);
                Data.addEmptyLine();
                Data.addCommend("#--------------------------#");
                Data.addCommend("#       Player System      #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Cash start amounth for new players");
                Data.addSetting("PlayerCashStartAmount", 0);
                Data.addEmptyLine();
                Data.addCommend("# Bank start amounth for new players");
                Data.addSetting("PlayerBankStartAmount", 0);
                Data.addEmptyLine();
                Data.addCommend("# Indicates whether a normal bank account costs to create. Player must pay with cash. (0 = free)");
                Data.addSetting("PlayerBankAccountCost", 0);
                Data.addEmptyLine();
//                Data.addCommend("#--------------------------#");
//                Data.addCommend("#      Business System      #");
//                Data.addCommend("#--------------------------#");
//                Data.addEmptyLine();
//                Data.addCommend("# Cash start amounth for new factories");
//                Data.addSetting("BusinessCashStartAmount", 0);
//                Data.addEmptyLine();
//                Data.addCommend("# Bank start amounth for new factories");
//                Data.addSetting("BusinessBankStartAmount", 0);
//                Data.addEmptyLine();
//                Data.addCommend("# Add new business plot only by admin");
//                Data.addSetting("BusinessPlotByAdmin", true);
//                Data.addEmptyLine();
//                Data.addCommend("# Groups to create a Business (Please add a space between the groups");
//                Data.addSetting("BusinessCreateGroups", "");
//                Data.addEmptyLine();
//                Data.addCommend("# Groups to create a Business (Please add a space between the groups");
//                Data.addSetting("BusinessCost", "0");
//                Data.addEmptyLine();
                Data.addCommend("#--------------------------#");
                Data.addCommend("#         Suitcase         #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Killer gets the money automatically (PVP must be allowed)");
                Data.addSetting("KillerGetMoney", false);
                Data.addEmptyLine();
                Data.addCommend("# Player loses money when dying (drops suitcase)");
                Data.addSetting("LostMoneyByDeath", false);
                Data.addEmptyLine();
                Data.addCommend("# The time until the suitcase despawn in seconds (Default: 600 sek = 10 Min)");
                Data.addSetting("SuitcaseTime", 600);
                Data.addEmptyLine();
                Console.sendInfo("Config", "Create / Load Config!");
                Data.CreateConfig();
                Console.sendInfo("Config", "Done!");

                
                PlayerBankAccountCost = mf.getMoneyAsLong(Data.getSetting("PlayerBankAccountCost"));
                PlayerBankStartAmount = mf.getMoneyAsLong(Data.getSetting("PlayerBankStartAmount"));
                PlayerCashStartAmount = mf.getMoneyAsLong(Data.getSetting("PlayerCashStartAmount"));
                Currency = Data.getSetting("Currency");
                MoneyFormat = Data.getSetting("MoneyFormat");
                MoneyInfoTime = Float.parseFloat(Data.getSetting("MoneyInfoTime"));
                SaveTimer = Float.parseFloat(Data.getSetting("SaveTimer"));
                Debug = Integer.parseInt(Data.getSetting("Debug"));
                ShowBalanceAtStart = Boolean.parseBoolean(Data.getSetting("ShowBalanceAtStart"));
                KillerGetMoney = Boolean.parseBoolean(Data.getSetting("KillerGetMoney"));
                LostMoneyByDeath = Boolean.parseBoolean(Data.getSetting("LostMoneyByDeath"));
                SuitcaseTime = Float.parseFloat(Data.getSetting("SuitcaseTime"));
                CreateAccountViaCommand = Boolean.parseBoolean(Data.getSetting("CreateAccountViaCommand"));
                Command_Bank_OnlyAdmin = Boolean.parseBoolean(Data.getSetting("Command_Bank_OnlyAdmin"));
                SaveAllByPlayerDisconnect = Boolean.parseBoolean(Data.getSetting("SaveAllByPlayerDisconnect"));
                
//                BusinessCreateGroups = Data.getSetting("BusinessCreateGroups");
//                BusinessPlotByAdmin = Boolean.parseBoolean(Data.getSetting("BusinessPlotByAdmin"));
//                BusinessCost = mf.getMoneyAsLong(Data.getSetting("BusinessCost"));
//                BusinessBankStartAmount = mf.getMoneyAsLong(Data.getSetting("BusinessBankStartAmount"));
//                BusinessCashStartAmount = mf.getMoneyAsLong(Data.getSetting("BusinessCashStartAmount"));
                
                BusinessCreateGroups = "";
                BusinessPlotByAdmin = true;
                BusinessCost = 0;
                BusinessBankStartAmount = 0;
                BusinessCashStartAmount = 0;

                if (Debug > 0) {
//                    Console.sendDebug("Config", "BusinessBankStartAmount = " + BusinessBankStartAmount);
//                    Console.sendDebug("Config", "BusinessCashStartAmount = " + BusinessCashStartAmount);
                    Console.sendDebug("Config", "  PlayerBankAccountCost = " + PlayerBankAccountCost);
                    Console.sendDebug("Config", " PlayerBankStartAmount = " + PlayerBankStartAmount);
                    Console.sendDebug("Config", " PlayerCashStartAmount = " + PlayerCashStartAmount);
                    Console.sendDebug("Config", "               Currency = " + Currency);
                    Console.sendDebug("Config", "            MoneyFormat = " + MoneyFormat);
                    Console.sendDebug("Config", "          MoneyInfoTime = " + MoneyInfoTime);
                    Console.sendDebug("Config", "              SaveTimer = " + SaveTimer);
                    Console.sendDebug("Config", "                  Debug = " + Debug);
                    Console.sendDebug("Config", "      ShowBalaceAtStart = " + ShowBalanceAtStart);
                    Console.sendDebug("Config", "         KillerGetMoney = " + KillerGetMoney);
                    Console.sendDebug("Config", "       LostMoneyByDeath = " + LostMoneyByDeath);
                    Console.sendDebug("Config", "           SuitcaseTime = " + SuitcaseTime);
                }

            }
        }

    }

}
