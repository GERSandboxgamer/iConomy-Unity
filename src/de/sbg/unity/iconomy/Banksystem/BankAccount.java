package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveBankMoneyEvent;
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

    public TransferResult removeMoney(long amounth) {
        //TODO
        RemoveBankMoneyEvent event = new RemoveBankMoneyEvent(this, amounth);
        plugin.triggerEvent(event);
        return null;
    }

    public String getMoneyAsFormatedString() {
        return MoneyFormat.getMoneyAsString(Money) + " " + MoneyFormat.getCurrency();
    }

    public TransferResult cashOut(Player player, long amounth) {
        //TODO
        return null;
    }

    public TransferResult cashIn(Player player, long amounth) {
        //TODO
        return null;
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
