package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Utils.BusinessAccountPermission;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import net.risingworld.api.Server;

/**
 * The FactoryBankMember class represents a member of a factory's bank account
 * within the iConomy system.
 */
public class BusinessBankMember implements Serializable {

    private final String UID;
    private final Set<BusinessAccountPermission> Permissions;

    public BusinessBankMember(String uid) {
        this.UID = uid;
        this.Permissions = new HashSet<>();
        setDefaultPermissions();
    }

    private void setDefaultPermissions() {
        Permissions.clear();
        Permissions.add(BusinessAccountPermission.USE_ACCOUNT); // Standard: Account benutzen
    }

    public String getUID() {
        return UID;
    }

    public Set<BusinessAccountPermission> getPermissionSet() {
        return Permissions;
    }

    public void addPermission(BusinessAccountPermission permission) {
        Permissions.add(permission);
    }

    public void removePermission(BusinessAccountPermission permission) {
        Permissions.remove(permission);
    }

    public boolean hasPermission(BusinessAccountPermission permission) {
        return Permissions.contains(permission);
    }

    public String getLastKnownMemberName() {
        return Server.getLastKnownPlayerName(UID);
    }

    /**
     * Gibt alle Permissions als String-Liste zurück (für GUI, Debugging etc.).
     */
    public Set<String> getPermissionsAsStringList() {
        Set<String> list = new HashSet<>();
        for (BusinessAccountPermission perm : Permissions) {
            list.add(perm.name());
        }
        return list;
    }
}
