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
import net.risingworld.api.collider.BoxCollider;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerDeathEvent;
import net.risingworld.api.events.player.PlayerDisconnectEvent;
import net.risingworld.api.events.player.PlayerGameObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerSpawnEvent;
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
                    player.sendTextMessage(format.Color("orange", "/balance"));
                    player.sendTextMessage(format.Color("orange", cmd[0]));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " [createbank|cb]"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " help"));
                    player.sendTextMessage(format.Color("orange", cmd[0] + " send"));
                    if (player.isAdmin()) {
                        player.sendTextMessage(format.Color("red", "===== Only Admin ====="));
                        player.sendTextMessage(format.Color("red", cmd[0] + " info"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givecash|gc> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takecash|tc> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setcash|sc> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givebank|gb> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takebank|tb> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setbank|sb> <Player> <Amount>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <createbank|cb> <Player>"));
                    }
                    player.sendTextMessage(format.Color("orange", "======================"));
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
        }
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
                            plugin.GUI.Bankystem.SelectCashInOutGui.showGUI(player);
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

}
