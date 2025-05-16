package de.sbg.unity.iconomy.Listeners.Player;

import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Events.Objects.ATM.PlayerAtmInteractionEvent;
import de.sbg.unity.iconomy.Events.Objects.Suitcase.PlayerSuitcaseRemoveEvent;
import de.sbg.unity.iconomy.GUI.Banksystem.CashInOutGUI;
import de.sbg.unity.iconomy.Objects.AtmObject;
import de.sbg.unity.iconomy.Objects.SuitcaseObject;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.definitions.Npcs;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerDeathEvent;
import net.risingworld.api.events.player.PlayerDisconnectEvent;
import net.risingworld.api.events.player.PlayerGameObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerNpcInteractionEvent;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.worldelements.GameObject;

public class icPlayerListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    public icPlayerListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.format = new TextFormat();
    }

    @EventMethod
    public void onPlayerSpawnEvent(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        plugin.Attribute.player.setSelectNpc(player, null);
        plugin.Attribute.player.setDummyMode(player, false);
        plugin.Attribute.player.setNpcSelectMode(player, false);
        plugin.Bankystem.npcSystem.addPlayer(player);
        plugin.Attribute.player.setBusinessPlotSelection(player, false);

        try {
            if (plugin.CashSystem.addPlayer(player)) {
                player.sendTextMessage(format.Color("orange", "Hello new Player! You become for start: " + plugin.CashSystem.getCashAsFormatedString(player)));
            }
        } catch (SQLException ex) {
            Console.sendErr("DB-SQL", "========= iConomy-Exception =========");
            Console.sendErr("DB-SQL", "Can not save player to Database!");
            Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
            Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
            for (StackTraceElement st : ex.getStackTrace()) {
                Console.sendErr("DB-SQL", st.toString());
            }
            Console.sendErr("DB-SQL", "=====================================");
            Console.sendErr("SERVER", "STOP SERVER!");
            Server.shutdown();
        }

        if (!plugin.GameObject.atm.getAtmList().isEmpty()) {
            plugin.GameObject.atm.getAtmList().forEach(atm -> {
                player.addGameObject(atm);
            });
        }

        if (!plugin.GameObject.suitcase.getList().isEmpty()) {
            plugin.GameObject.suitcase.getList().values().forEach(so -> {
                player.addGameObject(so);
            });
        }
    }

    @EventMethod
    public void onPlayerHelpCommand(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");

        if (cmd.length == 2) {
            if (cmd[0].toLowerCase().equals("/money") || cmd[0].toLowerCase().equals("/eco") || cmd[0].toLowerCase().equals("/$") || cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy")) {
                if (cmd[1].toLowerCase().equals("help")) {
                    player.sendTextMessage(format.Color("orange", "======== Help ========"));
                    player.sendTextMessage(format.Color("orange", "Info: | = Or; <> = must; () = Optional"));
                    player.sendTextMessage(format.Color("orange", "/balance - Show the balance"));
                    player.sendTextMessage(format.Color("orange", cmd[0]) + "Show the balance");
                    if (plugin.Config.CreateAccountViaCommand) {
                        player.sendTextMessage(format.Color("orange", cmd[0] + " [createbank|cb] - Create a bank account"));
                    }
                    player.sendTextMessage(format.Color("orange", cmd[0] + " help - Show the help"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " send - Send Money to other player"));
                    //player.sendTextMessage(format.Color("orange", cmd[0] + " factroy help")); //TODO Business
                    if (player.isAdmin()) {
                        player.sendTextMessage(format.Color("red", "===== Only Admin ====="));
                        player.sendTextMessage(format.Color("red", cmd[0] + " info - Show info about the plugin"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givecash|gc> <Player> <Amount> - Give a player cash"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takecash|tc> <Player> <Amount> - Take cash from player"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setcash|sc> <Player> <Amount> - Set the cash of a player"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givebank|gb> <Player> <Amount> - Give a player money to his bank account"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takebank|tb> <Player> <Amount> - Remove money from players bank account"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setbank|sb> <Player> <Amount> - Set the money of the player bank account"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <createbank|cb> <Player> - Create a bank account for a player"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " bs (true|false) - Show the banksystem (true = Admin-Mode)"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " bank addatm - Add a new ATM to the world"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " npc create <name> - Create a 'bank npc'"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " npc select - Select a 'bank npc'"));
                    }
                    player.sendTextMessage(format.Color("orange", "======================"));
                }
            }
            if (cmd[1].toLowerCase().equals("getid")) {
                player.sendTextMessage("[iConomy] " + player.getEquippedItem().getDefinition().id);

            }
        }
        /*
        if (cmd.length == 3) {
            if (cmd[1].toLowerCase().equals("factory") || cmd[1].toLowerCase().equals("fa")) {
                if (cmd[2].toLowerCase().equals("help")) {
                    player.sendTextMessage("Facotry Help");
                    player.sendTextMessage(format.Color("orange", cmd[0] + " " + cmd[1] + " *"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " " + cmd[1] + " " + "plot hide *"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " " + cmd[1] + " " + "plot name <name> *"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " " + cmd[1] + " " + "plot price <price> *"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " " + cmd[1] + " " + "plot show *"));
                    if (player.isAdmin()) {
                        player.sendTextMessage("Facotry Help (ADMIN)");
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot <add|a>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot <cselect|csel|cs>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot hideall"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot name <name> *"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot price <price> *"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot <select|sel|s>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " " + cmd[1] + " " + "plot showall"));

                    }
                    player.sendTextMessage("Info: * = You musst be in a plot!");
                }
            }
        }
        
        if (cmd.length == 4) {
            if (cmd[0].toLowerCase().equals("/sc")) {
                float x, y, z;
                try {
                    x = Float.parseFloat(cmd[1]);
                    y = Float.parseFloat(cmd[2]);
                    z = Float.parseFloat(cmd[3]);
                    if (plugin.GameObject.suitcase.getByPlayer(player) != null) {
                        plugin.GameObject.suitcase.getByPlayer(player).setCollider(new BoxCollider(x, y, z));
                    }
                } catch (NumberFormatException ex) {
                    player.sendTextMessage(ex.getMessage());
                }
            }
        }*/
    }

    @EventMethod
    public void onPlayerGameObjectInteractionEvent(PlayerGameObjectInteractionEvent event) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("onPlayerGameObjectInteractionEvent", "Run event!");
        }
        GameObject go = event.getGameObject();
        Player player = event.getPlayer();
        String lang = player.getLanguage();

        if (go instanceof SuitcaseObject so) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("onPlayerGameObjectInteractionEvent", "go instanceof SuitcaseObject");
            }
            plugin.GameObject.suitcase.remove(player, so, PlayerSuitcaseRemoveEvent.Cause.PlayerInteraktion);
        }
        if (go instanceof AtmObject atm) {
            PlayerAtmInteractionEvent evt = new PlayerAtmInteractionEvent(player, atm);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                    switch (atm.getType()) {
                        case Standart -> {
                            plugin.GUI.Bankystem.MainGui.showGUI(player);
                        }
                        case In -> {
                            plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.In);
                        }
                        case Out -> {
                            plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.Out);
                        }
//                    case Information -> {
//                        //TODO GUI Information (2.1)
//                    
                    }
                } else {
                    player.showErrorMessageBox("iConomy", plugin.Language.getStatus().getPlayerHasNoAccount(lang));
                }
            } else {
                if (evt.getCancelledReason() != null) {
                    player.showStatusMessage(evt.getCancelledReason(), 8);
                } else {
                    player.sendTextMessage(plugin.Language.getGameObject().getInteractAtm_Fail(lang));
                }
            }
        }
    }

    @EventMethod
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        plugin.Attribute.player.setDummyMode(player, false);

        if (event.getCause() == PlayerDeathEvent.Cause.KilledByPlayer) {
            if (plugin.Config.KillerGetMoney) {
                Player killer = (Player) event.getKiller();
                if (killer != null) {
                    long c = plugin.CashSystem.getCash(player);
                    if (c > 0) {
                        plugin.CashSystem.addCash(killer, c, AddCashEvent.Reason.Killing);
                        plugin.CashSystem.removeCash(player, c, RemoveCashEvent.Reason.Lost);
                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getLostMoney(lang)));

                    }
                }
            } else if (plugin.Config.LostMoneyByDeath) {
                long c = plugin.CashSystem.getCash(player);
                if (c > 0) {
                    plugin.GameObject.suitcase.spawn(player, c, event.getDeathPosition());
                    plugin.CashSystem.removeCash(player, c, RemoveCashEvent.Reason.Lost);
                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getLostMoney(lang)));
                }
            }
        } else if (plugin.Config.LostMoneyByDeath) {
            long c = plugin.CashSystem.getCash(player);
            if (c > 0) {
                plugin.GameObject.suitcase.spawn(player, c, event.getDeathPosition());
                plugin.CashSystem.removeCash(player, c, RemoveCashEvent.Reason.Lost);
                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getLostMoney(lang)));
            }
        }

    }

    @EventMethod
    public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
        Player player = event.getPlayer();
        plugin.Bankystem.npcSystem.removePlayer(player);
        if (plugin.Config.SaveAllByPlayerDisconnect) {
            try {
                plugin.Databases.saveAll();
            } catch (SQLException ex) {
                Console.sendErr("DB-SQL", "========= iConomy-Exception =========");
                Console.sendErr("DB-SQL", "Can not save all to Database!");
                Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr("DB-SQL", st.toString());
                }
                plugin.StopPluginByDB = true;
                Console.sendErr("SERVER", "STOP SERVER!");
                Console.sendErr("DB-SQL", "=====================================");
                Server.shutdown();
            } catch (IOException ex) {
                Console.sendErr("DB-IO", "========= iConomy-Exception =========");
                Console.sendErr("DB-IO", "Can not save all to Database!");
                Console.sendErr("DB-IO", "Ex-Msg: " + ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr("DB-IO", st.toString());
                }
                plugin.StopPluginByDB = true;
                Console.sendErr("SERVER", "STOP SERVER!");
                Console.sendErr("DB-IO", "=====================================");
                Server.shutdown();
            }
        }
    }

    @EventMethod
    public void onPlayerNpcInteraktionEvent(PlayerNpcInteractionEvent event) {
        Player player = event.getPlayer();
        Npc npc = event.getNpc();

        if (npc.getDefinition().type == Npcs.Type.Human) {
            if (!plugin.Bankystem.npcSystem.isICNpc(npc)) {
                if (plugin.Attribute.player.isDummyMode(player)) {
                    try {
                        plugin.Bankystem.npcSystem.addNpc(npc, 1);
                    } catch (SQLException ex) {
                        player.sendTextMessage(format.Color("red", "ERR: Can not save npc to database!"));
                        Console.sendErr("Command (npc create)", "Can not save npc to database!");
                        Console.sendErr("Command (npc create)", ex.getMessage());
                        for (StackTraceElement ste : ex.getStackTrace()) {
                            Console.sendErr("Command (npc create)", ste.toString());
                        }
                    }
                    npc.setLocked(true);
                }
            } else {
                if (plugin.Attribute.player.getNpcSelectMode(player)) {
                    plugin.Attribute.player.setNpcSelectMode(player, false);
                    plugin.Attribute.player.setSelectNpc(player, npc);
                    //TODO MSG
                }
                plugin.GUI.speakGuiSystem.show(player, npc);
            }
        }

    }

}
