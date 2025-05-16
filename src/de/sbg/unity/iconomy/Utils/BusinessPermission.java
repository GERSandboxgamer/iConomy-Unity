/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author pott
 */
public enum BusinessPermission {
    OPEN_ALL_DOORS,
    ENTER_ALL_PLOTS,
    LEAVE_ALL_PLOTS,
    TELEPORT_IN_ALL,
    TELEPORT_OUT_ALL,
    ADD_MEMBER,
    REMOVE_MEMBER,
    RENAME_FACTORY,
    CHANGE_OWNER,
    CHANGE_PLOTS,
    EDIT_ACTION,
    BUILD,
    TRADE,
    WORK;
    
    public static List<String> getAllPermisionAsString(){
        return Arrays.stream(BusinessPermission.values()).map(Enum::name).collect(Collectors.toList());
    }
}
