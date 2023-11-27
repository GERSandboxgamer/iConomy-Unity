package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Utils.AccountType;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class CashToBankEvent extends Event implements Cancellable{
    
    private final Player player;
    private long amount;
    private final AccountType accountTyp;
    
    public CashToBankEvent(Player p, long a, AccountType at){
        this.amount = a;
        this.player = p;
        this.accountTyp = at;
    }

    public Player getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public AccountType getAccountTyp() {
        return accountTyp;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }
    
    
}
