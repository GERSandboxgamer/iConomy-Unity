package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Events.Suitcase.SuitcaseRemoveEvent;
import de.sbg.unity.iconomy.Objects.SuitcaseObject;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
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
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givecash|gc> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takecash|tc> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setcash|sc> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <givebank|gb> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <takebank|tb> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <setbank|sb> <Player> <Amounth>"));
                        player.sendTextMessage(format.Color("red", cmd[0] + " <createbank|cb> <Player>"));
                    }
                    player.sendTextMessage(format.Color("orange", "======================"));
                }
            }
        }
    }

    @EventMethod
    public void onPlayerGameObjectInteractionEvent(PlayerGameObjectInteractionEvent event) {
        GameObject go = event.getGameObject();
        Player player = event.getPlayer();
        if (go instanceof SuitcaseObject suitcase) {
            SuitcaseRemoveEvent sre = new SuitcaseRemoveEvent(player, suitcase, SuitcaseRemoveEvent.Cause.Time);
            plugin.triggerEvent(sre);
            if (!sre.isCancelled()) {
                plugin.CashSystem.addCash(player, suitcase.getAmounth(), AddCashEvent.Reason.Pickup);
            } else {
                player.showStatusMessage("You can not pickup the suitcase", 5);
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
