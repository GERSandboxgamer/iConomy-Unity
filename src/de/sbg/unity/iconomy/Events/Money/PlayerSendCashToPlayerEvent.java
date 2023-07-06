package de.sbg.unity.iconomy.Events.Money;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class PlayerSendCashToPlayerEvent extends Event implements Cancellable{
    
    private final Player player, targetPlayer;
    private long amounth;
    
    public PlayerSendCashToPlayerEvent(Player from, Player to, long amounth) {
        this.player = from;
        this.targetPlayer = to;
        this.amounth = amounth;
    }

    public long getAmounth() {
        return amounth;
    }

    public void setAmounth(long amounth) {
        this.amounth = amounth;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
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
