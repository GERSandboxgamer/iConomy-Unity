
package de.sbg.unity.iconomy.Utils;

import net.risingworld.api.objects.Player;


public class icPlayer {
    
    private final String UID;
    private final String Name, ID, Language, PermissionGroup;
    
    public icPlayer(Player player) {
        this.UID = player.getUID();
        this.Name = player.getName();
        this.ID = player.getIP();
        this.Language = player.getLanguage();
        this.PermissionGroup = player.getPermissionGroup();
    }

    public String getID() {
        return ID;
    }

    public String getLanguage() {
        return Language;
    }

    public String getName() {
        return Name;
    }

    public String getPermissionGroup() {
        return PermissionGroup;
    }

    public String getUID() {
        return UID;
    }
    
}
