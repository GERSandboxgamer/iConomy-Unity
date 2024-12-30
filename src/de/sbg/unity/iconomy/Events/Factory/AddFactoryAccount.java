package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Factory.Factory;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddFactoryAccount extends Event{
    
    private final Player player;
    private final Factory factory;
    
    public AddFactoryAccount(Player player, Factory factory) {
        this.player = player;
        this.factory = factory;
    }

    public Factory getFactory() {
        return factory;
    }

    public Player getPlayer() {
        return player;
    }    
    
}
