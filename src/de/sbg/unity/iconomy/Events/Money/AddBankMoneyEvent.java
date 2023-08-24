package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddBankMoneyEvent extends Event implements Cancellable {

    private long amounth;
    private final Reason reason;
    private final Object sender;
    private final BankAccount bankAccount;

    public AddBankMoneyEvent(Player sender, BankAccount account, long amounth, Reason r) {
        this.amounth = amounth;
        this.bankAccount = account;
        this.sender = sender;
        this.reason = r;
    }

    public AddBankMoneyEvent(BankAccount sender, BankAccount account, long amounth) {
        this.reason = Reason.BankAccount;
        this.amounth = amounth;
        this.sender = sender;
        this.bankAccount = account;

    }

    public AddBankMoneyEvent(BankAccount account, long amounth) {
        this.reason = Reason.API;
        this.amounth = amounth;
        this.bankAccount = account;
        this.sender = null;
    }
    
    public AddBankMoneyEvent(BankAccount account, long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
        this.bankAccount = account;
        this.sender = null;
    }

    public Object getSender() {
        return sender;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public long getAmounth() {
        return amounth;
    }

    public void setAmounth(long amounth) {
        this.amounth = amounth;
    }

    public Reason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }

    public enum Reason {
        BankAccount,
        Sell,
        CashIn,
        Command,
        API;
       
    }
}
