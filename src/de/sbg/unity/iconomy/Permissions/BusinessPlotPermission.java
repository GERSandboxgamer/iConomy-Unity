package de.sbg.unity.iconomy.Permissions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum BusinessPlotPermission {
    OPEN_ALL_DOORS,
    ENTER,
    LEAVE,
    TELEPORT_IN_ALL,
    TELEPORT_OUT_ALL,
    ADD_MEMBER,
    REMOVE_MEMBER,
    RENAME_FACTORY,
    CHANGE_OWNER,
    CHANGE_PLOTS,
    EDIT_ACTION_ALL,
    BUILD_ALL,
    TRADE_ALL,
    WORK_ALL;
    
    
    public static List<String> getAllPermisionAsString(){
        return Arrays.stream(BusinessPlotPermission.values()).map(Enum::name).collect(Collectors.toList());
    }
}
