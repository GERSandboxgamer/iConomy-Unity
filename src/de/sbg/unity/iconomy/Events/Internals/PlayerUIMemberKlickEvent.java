package de.sbg.unity.iconomy.Events.Internals;

import de.sbg.unity.iconomy.GUI.List.PlayerList.UIPlayerLabel;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class PlayerUIMemberKlickEvent extends Event{
    
    private final Player player;
    private final UIPlayerLabel label;
    
    public PlayerUIMemberKlickEvent(Player player, UIPlayerLabel label) {
        this.player = player;
        this.label = label;
    }

    public Player getPlayer() {
        return player;
    }

    public UIPlayerLabel getLabel() {
        return label;
    }
}
