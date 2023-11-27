package de.sbg.unity.iconomy;

import de.chaoswg.ClassPluginJSONManager;
import de.chaoswg.ToolsAPI;
import de.sbg.unity.configmanager.ConfigData;
import de.sbg.unity.configmanager.ConfigManager;
import de.sbg.unity.iconomy.Banksystem.Banksystem;
import de.sbg.unity.iconomy.CashSystem.CashSystem;
import de.sbg.unity.iconomy.Database.icDatabases;
import de.sbg.unity.iconomy.Listeners.Player.icPlayerListener;
import de.sbg.unity.iconomy.GUI.GUIs;
import de.sbg.unity.iconomy.Listeners.Commands.AdminMoneyCommandListener;
import de.sbg.unity.iconomy.Listeners.Commands.PlayerMoneyCommandListener;
import de.sbg.unity.iconomy.Listeners.Player.PlayerAtmListener;
import de.sbg.unity.iconomy.Listeners.icInputListener;
import de.sbg.unity.iconomy.Objects.icGameObject;
import de.sbg.unity.iconomy.Objects.icSign;
import de.sbg.unity.iconomy.Utils.Attribute;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.PrefabVorlage;
import java.io.File;
//import de.sbg.unity.iconomy.Objects.icSign;
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

public class iConomy extends Plugin {

    public icSign Sign;
    public icDatabases Databases;
    public Banksystem Bankystem;
    public CashSystem CashSystem;

    /**
     * @hidden
     */
    //public FactorySystem Factory; //TODO Factory
    public icGameObject GameObject;
    public GUIs GUI;

    /**
     * @hidden
     */
    public icLanguage Language;

    private icConsole Console;
    private Attribute att;
    private ToolsAPI tools;
    private List<Listener> Events;
    private Update update;

    /**
     * Config of iConomy
     */
    public Config Config;

    /**
     * @hidden
     */
    public boolean StopPluginByDB;

    /**
     * Class for formating money
     */
    public MoneyFormate moneyFormat;

    @Override
    public void onEnable() {
        Console = new icConsole(this);
        Console.sendInfo("Enabled");
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
            this.moneyFormat = new MoneyFormate(this, Console);
            Console.sendInfo("ini", "Load Class...Attribute");
            this.att = new Attribute(this);
            Console.sendInfo("ini", "Load Class...CashSystem");
            CashSystem = new CashSystem(this, Console);
            Console.sendInfo("ini", "Load Class...Bankystem");
            Bankystem = new Banksystem(this, Console);
            Console.sendInfo("ini", "Load Class...Factory");
            //Factory = new FactorySystem(this, att); //TODO Factory
            Console.sendInfo("ini", "Load Class...GameObject");
            this.GameObject = new icGameObject(this, Console);

            
            List<String> BundleNameList = new ArrayList<>();
            BundleNameList.add("ATM");
            BundleNameList.add("Geldkoffer");

            for (String s : BundleNameList) {
                byte[] bAsset = tools.getResourceFromClass(getClass(), "resources/" + s.toLowerCase() + ".bundle");
                String fileAsset = getPath() + System.getProperty("file.separator") + "Asset" + System.getProperty("file.separator") + s.toLowerCase() + ".bundle";
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
            Databases.Factory.createDatabse();
            Databases.Money.createDatabse();
            Console.sendInfo("ini-DB", "Load Database...Done");

            Console.sendInfo("ini-DB", "Load all from Database...");
            try {
                Databases.Money.Cash.loadAllFromDatabase(CashSystem.getCashList());
                Databases.Money.Bank.loadAllFromDatabase(Bankystem.PlayerSystem.getPlayerAccounts());
                Databases.Money.ATM.loadAllFromDatabase(GameObject.atm.getAtmList());
                //Databases.Factory.TabFactory.loadAllFromDatabase(Factory.getHashFactories()); //TODO Factory
                //Databases.Factory.TabBank.loadAllFromDatabase(Bankystem.FactoryBankSystem.getHashFactoryAccounts()); //TODO Factory
                Databases.startSaveTimer();
            } catch (SQLException ex) {
                Console.sendErr("DB", "Cant load all from Database!");
                Console.sendErr("DB", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB", "Ex-SQLState: " + ex.getSQLState());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
            } catch (IOException ex) {
                Console.sendErr("DB", "Cant load all from Database!");
                Console.sendErr("DB", "IOException (Blob > Object)");
                Console.sendErr("DB", "Ex-Msg: " + ex.getMessage());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
            } catch (ClassNotFoundException ex) {
                Console.sendErr("DB", "Cant load all from Database!");
                Console.sendErr("DB", "ClassNotFoundException: Class can not found (Blob > Object)");
                Console.sendErr("DB", "Ex-Msg: " + ex.getMessage());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
            }
            Console.sendInfo("ini-DB", "Load all from Database...Done!");

            Sign = new icSign(this, Console);

            GUI = new GUIs(this, Console);

            Console.sendInfo("ini", "Load Languages...");
            this.Language = new icLanguage();
            File fileCongigPhat = new File(getPath() + System.getProperty("file.separator") + "Languages");
            if (fileCongigPhat.mkdirs()) {
                Console.sendInfo("ini", "Erstelle: " + fileCongigPhat.getAbsolutePath());
            }
            ClassPluginJSONManager jm = new ClassPluginJSONManager();

            jm.getBanList().add("defaultLanguage");
            String configFile = getPath() + System.getProperty("file.separator") + "Languages" + System.getProperty("file.separator") + "Language v" + this.getDescription("version") + ".json";
            Console.sendInfo("ini", "Load Languages...Done!");
            Language = (icLanguage) jm.update(Language, configFile);
            Console.sendInfo("ini", "Load Languages...Done!");

            registerEventListener(new PlayerMoneyCommandListener(this, Console));
            registerEventListener(new icPlayerListener(this, Console));
            registerEventListener(new AdminMoneyCommandListener(this, Console));
            registerEventListener(new icInputListener(this, Console));
            registerEventListener(new PlayerAtmListener(this, Console));

            Console.sendInfo("Check for Updates...");
            try {
                update = new Update(this, "http://gs.sandboxgamer.de/downloads/Plugins/risingworld/unity/iConomy/version.txt");
            } catch (IOException | URISyntaxException ioex) {
                Console.sendErr("Load", ioex.getMessage());
            }
        }

    }

    @Override
    public void onDisable() {
        Databases.stopSaveTimer();
        if (!StopPluginByDB) {
            Console.sendInfo("DB", "Save all to Database...");
            try {
                Databases.saveAtm();
                Databases.saveAll();
                Databases.Factory.getDatabase().close();
                Databases.Money.getDatabase().close();
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

    public boolean hasUpdate() {
        return update.hasUpdate();
    }

    public void showPlayerMoney(Player player, boolean bank) {
        if (bank) {
            GUI.MoneyInfoGui.showGUI(player, "Cash: " + CashSystem.getCashAsFormatedString(player), "Bank: " + Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
        } else {
            GUI.MoneyInfoGui.showGUI(player, "Cash: " + CashSystem.getCashAsFormatedString(player));
        }
    }

    public class Config {

        private final iConomy plugin;
        private final icConsole Console;
        private final ConfigManager Manager;
        private final MoneyFormate mf;

        public long PlayerCashStartAmount, PlayerBankStartAmount, PlayerBankAccountCost,
                FactoryCashStartAmount, FactoryBankStartAmount;
        public String Currency, MoneyFormat;
        public float MoneyInfoTime, SaveTimer, SuitcaseTime;
        public int Debug;
        public boolean ShowBalanceAtStart, KillerGetMoney, LostMoneyByDeath, CreateAccountViaCommand,
                Command_Bank_OnlyAdmin, SaveAllByPlayerDisconnect;
        //public boolean AlwaysShowMoneyInfo;

        public Config(iConomy plugin, icConsole Console) {
            this.plugin = plugin;
            this.Console = Console;
            this.Manager = (ConfigManager) plugin.getPluginByName("ConfigManager");
            this.mf = new MoneyFormate(plugin, Console);
        }

        public void iniConfig() throws IOException {
            if (Manager != null) {

                ConfigData Data = Manager.newConfig(plugin.getName(), plugin.getPath());
                Data.addCommend("#--------------------------#");
                Data.addCommend("#       iConomy-Config     #");
                Data.addCommend("#--------------------------#");
                Data.addEmptyLine();
                Data.addCommend("# Turn Debug on or off (1 = on)");
                Data.addSetting("Debug", "0");
                Data.addEmptyLine();
                Data.addCommend("# ==== Main ====");
                Data.addEmptyLine();
                Data.addCommend("# The Currency");
                Data.addSetting("Currency", "$");
                Data.addEmptyLine();
                Data.addCommend("# Format of the money (Standart: #,##0.00");
                Data.addSetting("MoneyFormat", "#,##0.00");
                Data.addEmptyLine();
                Data.addCommend("# The time of the 'MoneyInfo-GUI' to close in seconds");
                Data.addSetting("MoneyInfoTime", "5");
                Data.addEmptyLine();
                Data.addCommend("# Show the balance in the chat, if player connected to the server");
                Data.addSetting("ShowBalanceAtStart", "true");
                Data.addEmptyLine();
                Data.addCommend("# Database save timer in minutes");
                Data.addSetting("SaveTimer", "5");
                Data.addEmptyLine();
                Data.addCommend("# Save all to database, if a player disconnect");
                Data.addSetting("SaveAllByPlayerDisconnect", "true");
                Data.addEmptyLine();
                Data.addCommend("# ==== Commands ====");
                Data.addEmptyLine();
                Data.addCommend("# Open bank gui via command only admin");
                Data.addSetting("Command_Bank_OnlyAdmin", "true");
                Data.addEmptyLine();
                Data.addCommend("# Create a bank account via command");
                Data.addSetting("CreateAccountViaCommand", "false");
                Data.addEmptyLine();
                Data.addCommend("# ==== Player System ====");
                Data.addEmptyLine();
                Data.addCommend("# Cash start amounth for new players");
                Data.addSetting("PlayerCashStartAmount", "0");
                Data.addEmptyLine();
                Data.addCommend("# Bank start amounth for new players");
                Data.addSetting("PlayerBankStartAmount", "0");
                Data.addEmptyLine();
                Data.addCommend("# Indicates whether a normal bank account costs to create. Player must pay with cash. (0 = free)");
                Data.addSetting("PlayerBankAccountCost", "0");
                Data.addEmptyLine();
                Data.addCommend("# ==== Factory System ====");
                Data.addEmptyLine();
                Data.addCommend("# Cash start amounth for new factories");
                Data.addSetting("FactoryCashStartAmount", "0");
                Data.addEmptyLine();
                Data.addCommend("# Bank start amounth for new factories");
                Data.addSetting("FactoryBankStartAmount", "0");
                Data.addEmptyLine();
                Data.addCommend("# ==== Suitcase ====");
                Data.addEmptyLine();
                Data.addCommend("# Killer gets the money automatically (PVP must be allowed)");
                Data.addSetting("KillerGetMoney", "false");
                Data.addEmptyLine();
                Data.addCommend("# Player loses money when dying (drops suitcase)");
                Data.addSetting("LostMoneyByDeath", "false");
                Data.addEmptyLine();
                Data.addCommend("# The time until the suitcase despawn in seconds (Default: 600 sek = 10 Min)");
                Data.addSetting("SuitcaseTime", "600");
                Data.addEmptyLine();
                Console.sendInfo("Config", "Create / Load Config!");
                Data.CreateConfig();
                Console.sendInfo("Config", "Done!");

                FactoryBankStartAmount = mf.getMoneyAsLong(Data.getSetting("FactoryBankStartAmount"));
                FactoryCashStartAmount = mf.getMoneyAsLong(Data.getSetting("FactoryCashStartAmount"));
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

                if (Debug > 0) {
                    Console.sendDebug("Config", "FactoryBankStartAmount = " + FactoryBankStartAmount);
                    Console.sendDebug("Config", "FactoryCashStartAmount = " + FactoryCashStartAmount);
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
