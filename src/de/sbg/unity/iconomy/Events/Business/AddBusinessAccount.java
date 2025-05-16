package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Business.Business;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddBusinessAccount extends Event{
    
    private final Player player;
    private final Business factory;
    
    public AddBusinessAccount(Player player, Business factory) {
        this.player = player;
        this.factory = factory;
    }

    public Business getFactory() {
        return factory;
    }

    public Player getPlayer() {
        return player;
    }    
    
}
