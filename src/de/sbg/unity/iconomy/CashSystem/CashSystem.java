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
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

/**
 * The CashSystem class manages in-game cash for players in the iConomy system.
 * It allows adding, removing, setting, and transferring cash between players.
 */
public class CashSystem {

    private final HashMap<String, Long> CashList;
    private final iConomy plugin;
    private final icConsole Console;
    private final MoneyFormate MoneyFormate;
    private final TextFormat TextFormat;

    /**
     * Constructor for the CashSystem class.
     *
     * @param plugin The iConomy plugin instance.
     * @param Console The console for logging and output.
     */
    public CashSystem(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.CashList = new HashMap<>();
        this.MoneyFormate = new MoneyFormate(plugin, Console);
        this.TextFormat = new TextFormat();
    }

    /**
     * Retrieves the names of all players who have a cash entry in the system.
     *
     * @return A list of player names.
     */
    public List<String> getPlayerNames() {
        List<String> l = new ArrayList<>();
        for (String s : getUIDs()) {
            l.add(Server.getLastKnownPlayerName(s));
        }
        return l;
    }

    /**
     * Returns the list of all players' cash amounts as a HashMap.
     *
     * @return The HashMap containing the UIDs of players and their cash
     * amounts.
     */
    public HashMap<String, Long> getCashList() {
        return CashList;
    }

    /**
     * Retrieves the UIDs of all players who have a cash entry in the system.
     *
     * @return A list of UIDs.
     */
    public List<String> getUIDs() {
        List<String> result = new ArrayList<>();
        for (String s : CashList.keySet()) {
            result.add(s);
        }
        return result;
    }

    /**
     * Adds a player to the cash system with a starting amount defined in the
     * configuration.
     *
     * @param player The player to add.
     * @return True if the player was successfully added, false if they already
     * exist.
     * @throws SQLException If there is an error accessing the database.
     */
    public boolean addPlayer(Player player) throws SQLException {
        return addPlayer(player.getUID());
    }

    /**
     * Adds a player to the cash system with a starting amount defined in the
     * configuration.
     *
     * @param uid The unique identifier (UID) of the player.
     * @return True if the player was successfully added, false if the player
     * already exists.
     * @throws SQLException If an error occurs while accessing the database.
     */
    public boolean addPlayer(String uid) throws SQLException {
        // Check if the player is already in the system
        if (CashList.containsKey(uid)) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("Cash-addPlayer", "Player already exists: " + uid);
            }
            return false;  // Player already exists, no need to add again
        }

        // Add the player with the starting amount
        long startAmount = plugin.Config.PlayerCashStartAmount;
        CashList.put(uid, startAmount);
        plugin.Databases.Money.Cash.add(uid, startAmount);

        if (plugin.Config.Debug > 0) {
            Console.sendDebug("Cash-addPlayer", "Player added with starting cash: " + startAmount);
        }

        return true;  // Player successfully added
    }

    /**
     * Retrieves the cash amount for a specific player.
     *
     * @param player The player whose cash amount to retrieve.
     * @return The player's cash amount.
     */
    public Long getCash(Player player) {
        return getCash(player.getUID());
    }

    /**
     * Retrieves the cash amount for a player by UID or name.
     *
     * @param st The UID or name of the player.
     * @return The player's cash amount, or null if the player does not exist.
     */
    public Long getCash(String st) {
        if (getPlayerNames().contains(st)) {
            for (String s : plugin.CashSystem.getUIDs()) {
                if (Server.getLastKnownPlayerName(s).equals(st)) {
                    return CashList.get(s);
                }
            }
        } else {
            return CashList.get(st);
        }
        return null;
    }

    /**
     * Retrieves the cash amount for a player in a formatted string.
     *
     * @param player The player whose cash amount to format.
     * @return The player's formatted cash amount as a string.
     */
    public String getCashAsFormatedString(Player player) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("getCashAsFormatedString", "getCash: " + getCash(player));
            Console.sendDebug("getCashAsFormatedString", "MoneyFormat: " + MoneyFormate);
            Console.sendDebug("getCashAsFormatedString", "Currency: " + MoneyFormate.getCurrency());
        }
        return plugin.moneyFormat.getMoneyAsString(getCash(player), player) + " " + plugin.Config.Currency;
    }

    /**
     * Retrieves the cash amount for a player by UID in a formatted string.
     *
     * @param uid The UID of the player.
     * @return The player's formatted cash amount as a string.
     */
    public String getCashAsFormatedString(String uid) {
        return MoneyFormate.getMoneyAsDefaultFormatedString(getCash(uid));
    }

    /**
     * Adds cash to a player's account.
     *
     * @param player The player to add cash to.
     * @param amounth The amount of cash to add.
     * @param reason The reason for the cash addition.
     * @return The result of the cash addition operation.
     */
    public TransferResult addCash(Player player, long amounth, AddCashEvent.Reason reason) {
        if (player != null && player.isConnected()) {

            AddCashEvent evt = new AddCashEvent(player, amounth, reason);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                long newCash = getCash(player) + evt.getAmount();
                CashList.put(player.getUID(), newCash);
                plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + MoneyFormate.getMoneyAsFormatedString(player, newCash), TextFormat.Color("green", "+ " + MoneyFormate.getMoneyAsFormatedString(player, evt.getAmount())));
                return TransferResult.Successful;
            } else {
                return TransferResult.EventCancel;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
    }

    /**
     * Removes cash from a player's account.
     *
     * @param player The player to remove cash from.
     * @param amounth The amount of cash to remove.
     * @param reason The reason for the cash removal.
     * @return The result of the cash removal operation.
     */
    public TransferResult removeCash(Player player, long amounth, RemoveCashEvent.Reason reason) {
        if (player != null && player.isConnected()) {
            if ((getCash(player) - amounth) >= 0) {
                RemoveCashEvent evt = new RemoveCashEvent(player, amounth, reason);
                plugin.triggerEvent(evt);
                if (!evt.isCancelled()) {
                    long newCash = getCash(player) - evt.getAmount();
                    if (newCash >= 0) {
                        CashList.put(player.getUID(), newCash);
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + MoneyFormate.getMoneyAsFormatedString(player, newCash), TextFormat.Color("red", "- " + MoneyFormate.getMoneyAsFormatedString(player, evt.getAmount())));
                        return TransferResult.Successful;
                    } else {
                        return TransferResult.NotEnoughMoney;
                    }
                }
            } else {
                return TransferResult.NotEnoughMoney;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Sets the cash amount for a specific player.
     *
     * @param player The player whose cash amount to set.
     * @param cash The new cash amount.
     * @return The result of the operation.
     * @throws CashFormatExeption If the cash amount is negative.
     */
    public TransferResult setCash(Player player, long cash) throws CashFormatExeption {
        return setCash(player.getUID(), cash);
    }

    /**
     * Sets the cash amount for a player by UID.
     *
     * @param uid The UID of the player.
     * @param cash The new cash amount.
     * @return The result of the operation.
     * @throws CashFormatExeption If the cash amount is negative.
     */
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

    /**
     * Sends cash from one player to another.
     *
     * @param from The player sending the cash.
     * @param to The player receiving the cash.
     * @param amounth The amount of cash to send as a string.
     * @return The result of the operation.
     * @throws NumberFormatException If the amount cannot be parsed.
     */
    public TransferResult sendCash(Player from, Player to, String amounth) throws NumberFormatException {
        return sendCash(from, to, MoneyFormate.getMoneyAsLong(amounth));
    }

    /**
     * Sends cash from one player to another.
     *
     * @param from The player sending the cash.
     * @param to The player receiving the cash.
     * @param amounth The amount of cash to send as a long.
     * @return The result of the operation.
     */
    public TransferResult sendCash(Player from, Player to, long amounth) {
        if ((from != null && from.isConnected()) && (to != null && to.isConnected())) {
            if ((getCash(from) - amounth) >= 0) {
                PlayerSendCashToPlayerEvent evt = new PlayerSendCashToPlayerEvent(from, to, amounth);
                plugin.triggerEvent(evt);
                if (!evt.isCancelled()) {
                    long newCash1 = getCash(from) - evt.getAmount();
                    if (newCash1 >= 0) {
                        long newCash2 = getCash(to) + evt.getAmount();
                        CashList.put(from.getUID(), newCash1);
                        CashList.put(to.getUID(), newCash2);
                        plugin.GUI.MoneyInfoGui.showGUI(from, "Cash: " + MoneyFormate.getMoneyAsFormatedString(from, newCash1), TextFormat.Color("red", "- " + MoneyFormate.getMoneyAsFormatedString(from, evt.getAmount())));
                        plugin.GUI.MoneyInfoGui.showGUI(to, "Cash: " + MoneyFormate.getMoneyAsFormatedString(to, newCash2), TextFormat.Color("green", "+ " + MoneyFormate.getMoneyAsFormatedString(to, evt.getAmount())));
                        return TransferResult.Successful;
                    } else {
                        return TransferResult.NotEnoughMoney;
                    }
                }
            } else {
                return TransferResult.NotEnoughMoney;
            }
        } else {
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.EventCancel;
    }

}
