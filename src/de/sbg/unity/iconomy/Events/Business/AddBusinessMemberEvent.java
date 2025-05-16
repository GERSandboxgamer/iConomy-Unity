package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.Business.BusinessMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddBusinessMemberEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Business factory;
    private final BusinessMember factoryMember;
    
    public AddBusinessMemberEvent(Player p, Business f, BusinessMember m) {
        this.player = p;
        this.factory = f;
        this.factoryMember = m;
    }
    
    public Business getFactory() {
        return factory;
    }

    public BusinessMember getFactoryMember() {
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
