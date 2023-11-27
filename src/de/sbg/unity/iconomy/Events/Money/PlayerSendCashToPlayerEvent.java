package de.sbg.unity.iconomy.Events.Money;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class PlayerSendCashToPlayerEvent extends Event implements Cancellable{
    
    private final Player player, targetPlayer;
    private long amount;
    
    public PlayerSendCashToPlayerEvent(Player from, Player to, long amount) {
        this.player = from;
        this.targetPlayer = to;
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
