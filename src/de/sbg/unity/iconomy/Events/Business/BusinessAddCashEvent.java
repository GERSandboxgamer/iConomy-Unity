package de.sbg.unity.iconomy.Events.Business;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class BusinessAddCashEvent extends Event implements Cancellable{
    
    private long amount;
    private Player player;
    private final Reason reason;
    
    public BusinessAddCashEvent(long amount, Reason r) {
        this.reason = r;
        this.amount = amount;
    }
    
    public BusinessAddCashEvent(Player player, long amount, Reason r) {
        this.reason = r;
        this.amount = amount;
        this.player = player;
    }

    /**
     * Get the Player.
     * @return The player or null
     */
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
        API, Player, BankToCash, Sell, Factory;
    }
}
