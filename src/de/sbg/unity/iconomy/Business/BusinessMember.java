package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.Utils.BusinessPermission;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BusinessMember implements Serializable {

    private final String UID;
    private final Set<BusinessPermission> permissions;

    public BusinessMember(String uid) {
        this.UID = uid;
        this.permissions = new HashSet<>();
        
    }

    public String getUID() {
        return UID;
    }

    public boolean hasPermission(BusinessPermission permission) {
        return permissions.contains(permission);
    }

    public void addPermission(BusinessPermission permission) {
        permissions.add(permission);
    }

    public void removePermission(BusinessPermission permission) {
        permissions.remove(permission);
    }

    public Set<BusinessPermission> getPermissions() {
        return permissions;
    }

    public void clearPermissions() {
        permissions.clear();
    }
    
    
    public class MemberAction {
        
        
        
    }
    
}
