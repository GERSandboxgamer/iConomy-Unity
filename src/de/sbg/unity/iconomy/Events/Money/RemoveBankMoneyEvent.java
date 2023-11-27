package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class RemoveBankMoneyEvent extends Event implements Cancellable{
    
    private long amount;
    private final Reason reason;
    private final Object sender;
    private final BankAccount bankAccount;

    public RemoveBankMoneyEvent(Player sender, BankAccount account, long amount, Reason r) {
        this.amount = amount;
        this.bankAccount = account;
        this.sender = sender;
        this.reason = r;
    }

    public RemoveBankMoneyEvent(BankAccount sender, BankAccount account, long amount) {
        this.reason = Reason.BankAccount;
        this.amount = amount;
        this.sender = sender;
        this.bankAccount = account;

    }

    public RemoveBankMoneyEvent(BankAccount account, long amount) {
        this.reason = Reason.API;
        this.amount = amount;
        this.bankAccount = account;
        this.sender = null;
    }

    public Object getSender() {
        return sender;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
        CashOut,
        Command,
        API;
       
    }
    
    
    
}
