package de.sbg.unity.iconomy.Events.Objects.Suitcase;

import de.sbg.unity.iconomy.Objects.icGameObject;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class PlayerLostMoneyEvent extends Event{
    
    private final Reason reason;
    private final Player player;
    private final Player targetPlayer;
    private final icGameObject suitcase;
    
    public PlayerLostMoneyEvent(Player player, icGameObject suitcase) {
        this.player = player;
        this.reason = Reason.Suitcase;
        this.suitcase = suitcase;
        this.targetPlayer = null;
    }
    
    public PlayerLostMoneyEvent(Player player, Player targetPlayer) {
        this.player = player;
        this.reason = Reason.Player;
        this.suitcase = null;
        this.targetPlayer = targetPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public Reason getReason() {
        return reason;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public icGameObject getSuitcase() {
        return suitcase;
    }
    
    public enum Reason {
        Suitcase,
        Player;
    }
    
}
