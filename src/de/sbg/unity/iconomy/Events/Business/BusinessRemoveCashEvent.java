package de.sbg.unity.iconomy.Events.Business;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

/**
 * Called if the something remove cash from the factory
 */
public class BusinessRemoveCashEvent extends Event implements Cancellable{
    
    private long amounth;
    private Player player;
    private final Reason reason;
    
    public BusinessRemoveCashEvent(long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
    }
    
    public BusinessRemoveCashEvent(Player player, long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
        this.player = player;
    }

    /**
     * Get the player
     * @return The player or null
     */
    public Player getPlayer() {
        return player;
    }

    public Reason getReason() {
        return reason;
    }

    public long getAmount() {
        return amounth;
    }

    public void setAmount(long amounth) {
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
    
    /**
     * The reason for the event
     */
    public enum Reason {
        API, Player, CashToBank, Buy, Other;
    }
}
