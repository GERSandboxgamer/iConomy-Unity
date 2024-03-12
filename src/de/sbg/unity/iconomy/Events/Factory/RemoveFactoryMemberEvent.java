package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Factory.FactoryMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class RemoveFactoryMemberEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Factory factory;
    private final FactoryMember factoryMember;
    
    public RemoveFactoryMemberEvent(Player p, Factory f, FactoryMember m) {
        this.player = p;
        this.factory = f;
        this.factoryMember = m;
    }

    public Factory getFactory() {
        return factory;
    }

    public FactoryMember getFactoryMember() {
        return factoryMember;
    }

    public Player getPlayer() {
        return player;
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
