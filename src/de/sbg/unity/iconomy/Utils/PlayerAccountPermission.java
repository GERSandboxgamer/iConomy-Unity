package de.sbg.unity.iconomy.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayerAccountPermission {
    ADD_MEMBER,
    REMOVE_MEMBER,
    SEND_MONEY,
    ADD_CASH,
    REMOVE_CASH,
    SHOW_ACCOUNT,
    SHOW_STATEMENTS,
    CHANGE_PERMISSIONS;
    // usw. falls du mehr brauchst
    
    public static List<String> getAllPermisionAsString(){
        return Arrays.stream(PlayerAccountPermission.values()).map(Enum::name).collect(Collectors.toList());
    }
}
