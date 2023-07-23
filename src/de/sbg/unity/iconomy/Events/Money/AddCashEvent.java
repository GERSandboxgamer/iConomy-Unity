package de.sbg.unity.iconomy.Events.Money;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class AddCashEvent extends Event implements Cancellable{
    
    private long amounth;
    private final Player Player;
    private final Reason reason;
    
    public AddCashEvent(Player player, long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
        this.Player = player;
    }

    public Player getPlayer() {
        return Player;
    }

    public Reason getReason() {
        return reason;
    }

    public long getAmounth() {
        return amounth;
    }

    public void setAmounth(long amounth) {
        this.amounth = amounth;
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
        Player, BankToCash, Pickup, Sell, Factory, Killing;
    }
}
