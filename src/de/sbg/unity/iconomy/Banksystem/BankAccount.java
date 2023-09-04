package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Utils.AccountTyp;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

public class BankAccount {

    private final List<String> Statements;
    private long Money, Min;
    private final AccountTyp typ;
    private final MoneyFormate MoneyFormat;
    private final iConomy plugin;

    public BankAccount(iConomy plugin, AccountTyp typ) {
        this.Statements = new ArrayList<>();
        this.typ = typ;
        this.MoneyFormat = new MoneyFormate(plugin);
        this.plugin = plugin;
    }

    public AccountTyp getTyp() {
        return typ;
    }

    public List<String> getStatements() {
        return Statements;
    }

    public List<String> getStatements(int limit) throws NumberFormatException {
        List<String> l = new ArrayList<>();
        if (limit > 0) {
            for (int i = 0; i <= limit; i++) {
                l.add(Statements.get(i));
            }
            return l;
        } else {
            throw new NumberFormatException("Limit must be bigger then 0");
        }
    }

    public void addStatement(String s) {
        Statements.add(0, s);
    }
    
    public void addAllStatements(List<String> st) {
        st.forEach(s -> {
            addStatement(s);
        });
    }

    public void clearStatement() {
        Statements.clear();
    }

    public long getMoney() {
        return Money;
    }

    public void setMoney(long Money) {
        this.Money = Money;
    }

    public long getMin() {
        return Min;
    }

    public void setMin(long Min) {
        this.Min = Min;
    }

    public TransferResult addMoney(Player sender, long amounth, AddBankMoneyEvent.Reason r) {
        if (sender != null) {
            if (sender.isConnected()) {
                AddBankMoneyEvent event = new AddBankMoneyEvent(sender, this, amounth, r);
                plugin.triggerEvent(event);
                if (!event.isCancelled()) {
                    Money += amounth;
                    addStatement(" - " + sender.getName() + " +" + plugin.moneyFormat.getMoneyAsFormatedString(sender, Money));
                    return TransferResult.Successful;
                }
                return TransferResult.EventCancel;
            }
            return TransferResult.PlayerNotConnected;
        }
        return TransferResult.PlayerNotExist;
    }

    public TransferResult addMoney(BankAccount sender, long amounth) {
        AddBankMoneyEvent event = new AddBankMoneyEvent(sender, this, amounth);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            Money += amounth;
            addStatement(" - " + getAccountName(sender) + "-Bank +" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    public TransferResult addMoney(long amounth, String statement) {
        AddBankMoneyEvent event = new AddBankMoneyEvent(this, amounth);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            Money += amounth;
            addStatement(" - " + statement + " +" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    public TransferResult removeMoney(Player sender, long amounth, RemoveBankMoneyEvent.Reason r) {
        if (sender != null) {
            if (sender.isConnected()) {
                RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(sender, this, amounth, r);
                plugin.triggerEvent(event);
                if (!event.isCancelled()) {
                    long newMoney = Money - amounth;
                    if (newMoney >= getMin()) {
                        Money -= amounth;
                        addStatement(" - " + sender.getName() + " -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
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

    public TransferResult removeMoney(BankAccount sender, long amounth) {
        RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(sender, amounth);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            long newMoney = Money - amounth;
            if (newMoney >= getMin()) {
                Money -= amounth;
                addStatement(" - " + getAccountName(sender) + " -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
                return TransferResult.Successful;
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    public TransferResult removeMoney(long amounth, String statement) {
        RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(this, amounth);
        plugin.triggerEvent(event);
        if (!event.isCancelled()) {
            long newMoney = Money - amounth;
            if (newMoney >= getMin()) {
                Money -= amounth;
                addStatement(" - " + statement + " -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
                return TransferResult.Successful;
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    public String getMoneyAsFormatedString() {
        return MoneyFormat.getMoneyAsString(Money) + " " + plugin.Config.Currency;
    }

    public TransferResult cashOut(Player player, long amounth) {
        if (player != null) {
            if (player.isConnected()) {
                RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(player, this, amounth, RemoveBankMoneyEvent.Reason.CashOut);
                AddCashEvent event2 = new AddCashEvent(player, amounth, AddCashEvent.Reason.BankToCash);
                plugin.triggerEvent(event);
                plugin.triggerEvent(event);
                if (!event.isCancelled() && !event2.isCancelled()) {
                    long newMoney = Money - amounth;
                    if (newMoney >= getMin()) {
                        long c = plugin.CashSystem.getCash(player);
                        c += amounth;
                        try {
                            plugin.CashSystem.setCash(player, c);
                            Money -= amounth;
                        } catch (CashFormatExeption ex) {
                            return TransferResult.MoneyFormat;
                        }
                        addStatement(" - Cash out -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
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

    public TransferResult cashIn(Player player, long amounth) {
        RemoveCashEvent event = new RemoveCashEvent(player, amounth, RemoveCashEvent.Reason.CashToBank);
        AddBankMoneyEvent event2 = new AddBankMoneyEvent(player, this, amounth, AddBankMoneyEvent.Reason.CashIn);
        plugin.triggerEvent(event);
        plugin.triggerEvent(event2);
        if (!event.isCancelled() && !event2.isCancelled()) {
            long c = plugin.CashSystem.getCash(player);
            long newC = c - amounth;
            if (newC >= 0) {
                try {
                    plugin.CashSystem.setCash(player, newC);
                    Money += amounth;
                    addStatement(" - Cash in -" + plugin.moneyFormat.getMoneyAsDefaultFormatedString(Money));
                    return TransferResult.Successful;
                } catch (CashFormatExeption ex) {
                    return TransferResult.MoneyFormat;
                }
            }
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    private String getAccountName(BankAccount sender) {
        if (sender instanceof PlayerAccount pa) {
            return pa.getLastKnownOwnerName();
        }
        if (sender instanceof FactoryAccount fa) {
            return fa.getFactory().getName();
        }
        return null;
    }

}
