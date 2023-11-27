/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Listeners.Commands;

import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Player;

/**
 *
 * @author pbronke
 */
public class PlayerFactoryCommandsListener implements Listener{
    
    
    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        String[] cmd = event.getCommand().split(" ");
        
        
    }
    
}
