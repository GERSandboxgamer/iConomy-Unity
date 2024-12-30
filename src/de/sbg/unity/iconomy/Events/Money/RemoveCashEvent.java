package de.sbg.unity.iconomy.Events.Money;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class RemoveCashEvent extends Event implements Cancellable{
    
    private long amount;
    private final Player player;
    private final Reason reason;
    
    public RemoveCashEvent(Player player, long amount, Reason r) {
        this.reason = r;
        this.amount = amount;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Reason getReason() {
        return reason;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
        Player, CashToBank, Lost, Buy;
    }
}
