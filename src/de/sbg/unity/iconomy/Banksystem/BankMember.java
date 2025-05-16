package de.sbg.unity.iconomy.Banksystem;

import java.util.EnumSet;
import java.util.Set;
import net.risingworld.api.Server;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;

public class BankMember {

    private static final long serialVersionUID = 1L;
    
    private final String UID;
    private final Set<PlayerAccountPermission> permissions; 

    public BankMember(String uid) {
        this.UID = uid;
        // Du könntest hier ein EnumSet nehmen – das ist effizienter für Enums
        this.permissions = EnumSet.noneOf(PlayerAccountPermission.class);
    }
    
    public void setDefaultPermission(){
        addPermission(PlayerAccountPermission.SHOW_ACCOUNT);
        addPermission(PlayerAccountPermission.SHOW_STATEMENTS);
    }

    public String getUID() {
        return UID;
    }

    public String getLastKnownMemberName() {
        return Server.getLastKnownPlayerName(UID);
    }

    // Getter für das Set selbst
    public Set<PlayerAccountPermission> getPermissions() {
        return permissions;
    }

    // Methode zum Hinzufügen einer Permission
    public void addPermission(PlayerAccountPermission p) {
        permissions.add(p);
    }

    // Methode zum Entfernen einer Permission
    public void removePermission(PlayerAccountPermission p) {
        permissions.remove(p);
    }

    // Abfrage
    public boolean hasPermission(PlayerAccountPermission p) {
        return permissions.contains(p);
    }
    
}
