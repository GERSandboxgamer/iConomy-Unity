package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Utils.AccountType;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

/**
 * The BankAccount class represents a bank account in the iConomy system. It
 * provides methods for adding, removing, and managing money, as well as
 * handling statements and integrating with the event system. It supports cash
 * in/out functionality and allows interaction between different accounts and
 * players.
 */
public class BankAccount {

    private final List<BankStatement> Statements;
    private long Money, Min;
    private final AccountType typ;
    private final MoneyFormate MoneyFormat;
    private final iConomy plugin;
    private final icConsole Console;

    /**
     * Constructor for creating a new BankAccount.
     *
     * @param plugin The iConomy plugin instance.
     * @param Console The console used for logging and output.
     * @param typ The type of the account (e.g., Player or Factory account).
     */
    public BankAccount(iConomy plugin, icConsole Console, AccountType typ) {
        this.Statements = new ArrayList<>();
        this.typ = typ;
        this.MoneyFormat = new MoneyFormate(plugin, Console);
        this.plugin = plugin;
        this.Console = Console;
    }

    /**
     * Gets the account type of the bank account.
     *
     * @return The account type.
     */
    public AccountType getTyp() {
        return typ;
    }

    /**
     * Retrieves the list of statements for the bank account.
     *
     * @return A List of statement strings.
     */
    public List<BankStatement> getStatements() {
        return Statements;
    }

    /**
     * Retrieves a limited number of statements for the bank account.
     *
     * @param limit The maximum number of statements to retrieve.
     * @return A List of statement strings.
     * @throws NumberFormatException If the limit is less than or equal to 0.
     */
    public List<BankStatement> getStatements(int limit) throws IllegalArgumentException {
        List<BankStatement> l = new ArrayList<>();
        if (limit > 0 && limit <= Statements.size()) {
            for (int i = 0; i <= (limit - 1); i++) {
                l.add(Statements.get(i));
            }
            return l;
        } else {
            throw new IllegalArgumentException("Limit must be greater than 0 and within the size of statements");
        }

    }

    /**
     * Adds a new statement to the bank account.
     *
     * @param s The statement to add.
     * @param sender The sender who sent the money (e.g. Player, BankAccount, FactoryAccount, etc.)
     */
    public void addStatement(String s, String sender) {       
        Statements.add(new BankStatement(LocalDateTime.now(), sender, s));
    }
    
    public void addStatement(BankStatement bs) {
        Statements.add(bs);
    }

    /**
     * Adds multiple statements to the bank account.
     *
     * @param st A List of statements to add.
     */
    public void addAllStatements(List<BankStatement> st) {
        st.forEach(s -> addStatement(s));
    }

    /**
     * Clears all statements from the bank account.
     */
    public void clearStatement() {
        Statements.clear();
    }

    /**
     * Gets the current balance of the bank account.
     *
     * @return The balance of the bank account.
     */
    public long getMoney() {
        return Money;
    }

    /**
     * Sets the balance of the bank account using a formatted string.
     *
     * @param Money The balance as a formatted string.
     */
    public void setMoney(String Money) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("setMoney", "Set money to: " + Money);
        }
        setMoney(MoneyFormat.getMoneyAsLong(Money));
    }

    /**
     * Sets the balance of the bank account.
     *
     * @param Money The balance as a long value.
     */
    public void setMoney(long Money) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("setMoney", "Set money to: " + Money);
        }
        this.Money = Money;
    }

    /**
     * Gets the minimum allowed balance for the bank account.
     *
     * @return The minimum balance.
     */
    public long getMin() {
        return Min;
    }

    /**
     * Sets the minimum allowed balance for the bank account.
     *
     * @param Min The minimum balance.
     */
    public void setMin(long Min) {
        this.Min = Min;
    }

    /**
     * Adds money to the bank account from a player.
     *
     * @param sender The player sending the money.
     * @param amount The amount of money to add.
     * @param reason The reason for the money addition.
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult addMoney(Player sender, long amount, AddBankMoneyEvent.Reason reason) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("addMoney", "Adding money from Player");
        }
        if (sender != null) {
            if (sender.isConnected()) {
                AddBankMoneyEvent event = new AddBankMoneyEvent(sender, this, amount, reason);
                plugin.triggerEvent(event);
                if (!event.isCancelled()) {
                    Money += amount;
                    addStatement(sender.getName() + " +" + plugin.moneyFormat.getMoneyAsFormatedString(sender, Money), sender.getName());
                    return TransferResult.Successful;
                }
                return TransferResult.EventCancel;
            }
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.PlayerNotExist;
    }
    
    public TransferResult addMoney(Player sender, long amount, AddBankMoneyEvent.Reason reason, String statement) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("addMoney", "Adding money from Player");
        }
        if (sender != null) {
            if (sender.isConnected()) {
                AddBankMoneyEvent event = new AddBankMoneyEvent(sender, this, amount, reason);
                plugin.triggerEvent(event);
                if (!event.isCancelled()) {
                    Money += amount;
                    addStatement(statement + " +" + plugin.moneyFormat.getMoneyAsFormatedString(sender, Money), sender.getName());
                    return TransferResult.Successful;
                }
                return TransferResult.EventCancel;
            }
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.PlayerNotExist;
    }

    /**
     * Adds money to the bank account from another bank account.
     *
     * @param sender The bank account sending the money.
     * @param amount The amount of money to add.
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult addMoney(BankAccount sender, long amount) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("addMoney", "Adding money from BankAccount");
        }
        AddBankMoneyEvent event = new AddBankMoneyEvent(sender, this, amount);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            Money += amount;
            addStatement("Send to Bank: +" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), sender.getAccountName());
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Adds money to the bank account with a custom statement.
     *
     * @param amount The amount of money to add.
     * @param statement The custom statement for the transaction.
     * @param sender The sender who sent the money (e.g. Player, BankAccount, FactoryAccount, etc.)
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult addMoney(long amount, String statement, String sender) {
        if (plugin.Config.Debug > 0) {
            Console.sendInfo("addMoney", "Adding money with custom statement");
        }
        AddBankMoneyEvent event = new AddBankMoneyEvent(this, amount);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            Money += amount;
            addStatement(statement + " +" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), sender);
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Removes money from the bank account for a player.
     *
     * @param sender The player requesting the withdrawal.
     * @param amount The amount of money to remove.
     * @param reason The reason for the withdrawal.
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult removeMoney(Player sender, long amount, RemoveBankMoneyEvent.Reason reason) {
        if (sender != null) {
            if (sender.isConnected()) {
                RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(sender, this, amount, reason);
                plugin.triggerEvent(event);
                if (!event.isCancelled()) {
                    long newMoney = Money - amount;
                    if (newMoney >= getMin()) {
                        Money -= amount;
                        addStatement(sender.getName() + " -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), sender.getName());
                        return TransferResult.Successful;
                    } else {
                        return TransferResult.NotEnoughMoney;
                    }
                }
                return TransferResult.EventCancel;
            }
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.PlayerNotExist;
    }

    /**
     * Removes money from the bank account for another bank account.
     *
     * @param sender The bank account requesting the withdrawal.
     * @param amount The amount of money to remove.
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult removeMoney(BankAccount sender, long amount) {
        RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(sender, amount);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            long newMoney = Money - amount;
            if (newMoney >= getMin()) {
                Money -= amount;
                addStatement("Send money -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), sender.getAccountName());
                return TransferResult.Successful;
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Removes money from the bank account with a custom statement.
     *
     * @param amount The amount of money to remove.
     * @param statement The custom statement for the transaction.
     * @param sender The sender who sent the money (e.g. Player, BankAccount, FactoryAccount, etc.)
     * @return TransferResult indicating the result of the transfer.
     */
    public TransferResult removeMoney(long amount, String statement, String sender) {
        RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(this, amount);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            long newMoney = Money - amount;
            if (newMoney >= getMin()) {
                Money -= amount;
                addStatement(statement + " -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), sender);
                return TransferResult.Successful;
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Retrieves the balance of the bank account as a formatted string.
     *
     * @return The formatted balance of the bank account.
     */
    public String getMoneyAsFormatedString() {
        return MoneyFormat.getMoneyAsString(Money) + " " + plugin.Config.Currency;
    }

    /**
     * Handles cashing out money from the bank account to a player's cash
     * balance.
     *
     * @param player The player receiving the cash.
     * @param amount The amount of money to cash out.
     * @return TransferResult indicating the result of the cash out operation.
     */
    public TransferResult cashOut(Player player, long amount) {
        if (player != null) {
            if (player.isConnected()) {
                RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(player, this, amount, RemoveBankMoneyEvent.Reason.CashOut);
                AddCashEvent event2 = new AddCashEvent(player, amount, AddCashEvent.Reason.BankToCash);
                plugin.triggerEvent(event);
                plugin.triggerEvent(event2);
                if (!event.isCancelled() && !event2.isCancelled()) {
                    long newMoney = Money - amount;
                    if (newMoney >= getMin()) {
                        long c = plugin.CashSystem.getCash(player);
                        c += amount;
                        try {
                            plugin.CashSystem.setCash(player, c);
                            Money -= amount;
                        } catch (CashFormatExeption ex) {
                            return TransferResult.MoneyFormat;
                        }
                        addStatement("Cash out -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), player.getName());
                        return TransferResult.Successful;
                    }
                    return TransferResult.NotEnoughMoney;
                }
                return TransferResult.EventCancel;
            }
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.PlayerNotExist;
    }

    /**
     * Handles cashing in money from a player's cash balance to the bank
     * account.
     *
     * @param player The player providing the cash.
     * @param amount The amount of money to cash in.
     * @return TransferResult indicating the result of the cash in operation.
     */
    public TransferResult cashIn(Player player, long amount) {
        RemoveCashEvent event = new RemoveCashEvent(player, amount, RemoveCashEvent.Reason.CashToBank);
        AddBankMoneyEvent event2 = new AddBankMoneyEvent(player, this, amount, AddBankMoneyEvent.Reason.CashIn);
        plugin.triggerEvent(event);
        plugin.triggerEvent(event2);
        if (!event.isCancelled() && !event2.isCancelled()) {
            long c = plugin.CashSystem.getCash(player);
            long newC = c - amount;
            if (newC >= 0) {
                try {
                    plugin.CashSystem.setCash(player, newC);
                    Money += amount;
                    addStatement("Cash in +" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money), player.getName());
                    return TransferResult.Successful;
                } catch (CashFormatExeption ex) {
                    return TransferResult.MoneyFormat;
                }
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Retrieves the account name based on the type of account (PlayerAccount or
     * FactoryAccount).
     *
     * @param sender The bank account.
     * @return The name of the account owner (player or factory).
     */
    private String getAccountName() {
        if (this instanceof PlayerAccount pa) {
            return pa.getLastKnownOwnerName();
        }
        if (this instanceof FactoryAccount fa) {
            return fa.getFactory().getName();
        }
        return null;
    }
}
