package de.sbg.unity.iconomy.CashSystem;

import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.PlayerSendCashToPlayerEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Player;

public class CashSystem {

    private final HashMap<String, Long> CashList;
    private final iConomy plugin;
    private final icConsole Console;
    private final MoneyFormate MoneyFormate;
    private final TextFormat TextFormat;

    public CashSystem(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.CashList = new HashMap<>();
        this.MoneyFormate = new MoneyFormate(plugin);
        this.TextFormat = new TextFormat();
    }

    public HashMap<String, Long> getCashList() {
        return CashList;
    }

    public List<String> getUIDs() {
        List<String> result = new ArrayList<>();
        for (String s : CashList.keySet()) {
            result.add(s);
        }
        return result;
    }

    public boolean addPlayer(Player player) throws SQLException {
        return addPlayer(player.getUID());
    }

    public boolean addPlayer(String uid) throws SQLException {
        boolean b = !CashList.containsKey(uid);

        if (b) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("Cash-addPlayer", "PlayerCashStartAmounth: " + plugin.Config.PlayerCashStartAmounth);
            }
            CashList.put(uid, plugin.Config.PlayerCashStartAmounth);
            plugin.Databases.Money.Cash.add(uid, plugin.Config.PlayerCashStartAmounth);
        }

        if (plugin.Config.Debug > 0) {
            Console.sendDebug("Cash-addPlayer", b);
        }
        return b;
    }

    public Long getCash(Player player) {
        return getCash(player.getUID());
    }

    public Long getCash(String uid) {
        return CashList.get(uid);
    }

    public String getCashAsFormatedString(Player player) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("getCashAsFormatedString", "getCash: " + getCash(player));
            Console.sendDebug("getCashAsFormatedString", "MoneyFormat: " + MoneyFormate);
            Console.sendDebug("getCashAsFormatedString", "Currency: " + MoneyFormate.getCurrency());
        }
        return MoneyFormate.getMoneyAsString(getCash(player)) + " " + MoneyFormate.getCurrency();
    }

    public String getCashAsFormatedString(String uid) {
        return MoneyFormate.getMoneyAsString(getCash(uid)) + " " + MoneyFormate.getCurrency();
    }
    
    public TransferResult addCash(Player player, long amounth, AddCashEvent.Reason reason) {
        if (player != null && player.isConnected()) {

            AddCashEvent evt = new AddCashEvent(player, amounth, reason);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                long newCash = getCash(player) + evt.getAmounth();
                CashList.put(player.getUID(), newCash);
                plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + MoneyFormate.getMoneyAsFormatedString(player, newCash), TextFormat.Color("green", "+ " + MoneyFormate.getMoneyAsFormatedString(player, evt.getAmounth())));
                return TransferResult.Successful;
            } else {
                return TransferResult.EventCancel;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
    }

    public TransferResult removeCash(Player player, long amounth, RemoveCashEvent.Reason reason) {
        if (player != null && player.isConnected()) {
            if ((getCash(player) - amounth) >= 0) {
                RemoveCashEvent evt = new RemoveCashEvent(player, amounth, reason);
                plugin.triggerEvent(evt);
                if (!evt.isCancelled()) {
                    long newCash = getCash(player) - evt.getAmounth();
                    if (newCash >= 0) {
                        CashList.put(player.getUID(), newCash);
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + MoneyFormate.getMoneyAsFormatedString(player, newCash), TextFormat.Color("red", "- " + MoneyFormate.getMoneyAsFormatedString(player, evt.getAmounth())));
                        return TransferResult.Successful;
                    } else {
                        return TransferResult.NotAnouthMoney;
                    }
                }
            } else {
                return TransferResult.NotAnouthMoney;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.EventCancel;
    }

    public TransferResult setCash(Player player, long cash) throws CashFormatExeption {
        return setCash(player.getUID(), cash);
    }

    public TransferResult setCash(String uid, long cash) throws CashFormatExeption {
        if (CashList.containsKey(uid)) {
            if (cash >= 0) {
                CashList.put(uid, cash);
                return TransferResult.Successful;
            } else {
                throw new CashFormatExeption("Cash is less than zero!");
            }
        } else {
            return TransferResult.PlayerNotExist;
        }
    }

    public TransferResult sendCash(Player from, Player to, String amounth) throws NumberFormatException {
        return sendCash(from, to, MoneyFormate.getMoneyAsLong(amounth));
    }

    public TransferResult sendCash(Player from, Player to, long amounth) {
        if ((from != null && from.isConnected()) && (to != null && to.isConnected())) {
            if ((getCash(from) - amounth) >= 0) {
                PlayerSendCashToPlayerEvent evt = new PlayerSendCashToPlayerEvent(from, to, amounth);
                plugin.triggerEvent(evt);
                if (!evt.isCancelled()) {
                    long newCash1 = getCash(from) - evt.getAmounth();
                    if (newCash1 >= 0) {
                        long newCash2 = getCash(to) + evt.getAmounth();
                        CashList.put(from.getUID(), newCash1);
                        CashList.put(to.getUID(), newCash2);
                        plugin.GUI.MoneyInfoGui.showGUI(from, "Cash: " + MoneyFormate.getMoneyAsFormatedString(from, newCash1), TextFormat.Color("red", "- " + MoneyFormate.getMoneyAsFormatedString(from, evt.getAmounth())));
                        plugin.GUI.MoneyInfoGui.showGUI(to, "Cash: " + MoneyFormate.getMoneyAsFormatedString(to, newCash2), TextFormat.Color("green", "+ " + MoneyFormate.getMoneyAsFormatedString(to, evt.getAmounth())));
                        return TransferResult.Successful;
                    } else {
                        return TransferResult.NotAnouthMoney;
                    }
                }
            } else {
                return TransferResult.NotAnouthMoney;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.EventCancel;
    }

}
