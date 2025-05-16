package de.sbg.unity.iconomy.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BusinessAccountPermission {
    ADD_MEMBER,
    REMOVE_MEMBER,
    CREATE_ACCOUNT,
    SEND_MONEY,
    CHANGE_OWNER,
    DELETE_ACCOUNT,
    USE_ACCOUNT,
    CHANGE_PERMISSIONS;
    // usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst// usw. falls du mehr brauchst
    
    public static List<String> getAllPermisionAsString(){
        return Arrays.stream(BusinessAccountPermission.values()).map(Enum::name).collect(Collectors.toList());
    }
}
