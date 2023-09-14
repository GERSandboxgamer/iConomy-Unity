package de.sbg.unity.iconomy.Events.Factory;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


/**
 *
 * @hidden  
 */
public class FactoryAddCashEvent extends Event implements Cancellable{
    
    private long amounth;
    private Player player;
    private final Reason reason;
    
    public FactoryAddCashEvent(long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
    }
    
    public FactoryAddCashEvent(Player player, long amounth, Reason r) {
        this.reason = r;
        this.amounth = amounth;
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
        API, Player, BankToCash, Sell, Factory;
    }
}
