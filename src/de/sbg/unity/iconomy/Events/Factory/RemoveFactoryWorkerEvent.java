package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Factory.FactoryWorker;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class RemoveFactoryWorkerEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Factory factory;
    private final FactoryWorker factoryWorker;
    
    public RemoveFactoryWorkerEvent(Player p, Factory f, FactoryWorker w) {
        this.player = p;
        this.factory = f;
        this.factoryWorker = w;
    }

    public FactoryWorker getFactoryWorker() {
        return factoryWorker;
    }
    
    public Factory getFactory() {
        return factory;
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
