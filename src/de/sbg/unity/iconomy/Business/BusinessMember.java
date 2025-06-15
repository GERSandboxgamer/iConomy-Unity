package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.Permissions.BusinessPermission;
import de.sbg.unity.iconomy.Permissions.BusinessPlotPermission;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class BusinessMember implements Serializable {

    private final String UID;
    private final Set<BusinessPermission> permissions;
    private final Set<BusinessPlotPermission> plotPermission;
    

    public BusinessMember(String uid) {
        this.UID = uid;
        this.permissions = new HashSet<>();
        this.plotPermission = new HashSet<>();
        
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

    public Set<BusinessPlotPermission> getPlotPermission() {
        return plotPermission;
    }
    
    public void clearPlotPermissions() {
        plotPermission.clear();
    }
    
    public void addPermission(BusinessPlotPermission permission) {
        plotPermission.add(permission);
    }
    
    public void removePermission(BusinessPlotPermission permission) {
        plotPermission.remove(permission);
    }
    
    public boolean hasPermission(BusinessPlotPermission permission) {
        return plotPermission.contains(permission);
    }
    
    
    public class MemberAction {
        
        //TODO Business MemberAction
        
    }
    
}
