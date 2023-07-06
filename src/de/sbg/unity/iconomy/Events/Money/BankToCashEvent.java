package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Utils.AccountTyp;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class BankToCashEvent extends Event implements Cancellable{
    
    private final Player player;
    private long amounth;
    private final AccountTyp accountTyp;
    
    public BankToCashEvent(Player p, long a, AccountTyp at){
        this.amounth = a;
        this.player = p;
        this.accountTyp = at;
    }

    public Player getPlayer() {
        return player;
    }

    public long getAmounth() {
        return amounth;
    }

    public void setAmounth(long amounth) {
        this.amounth = amounth;
    }

    public AccountTyp getAccountTyp() {
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
