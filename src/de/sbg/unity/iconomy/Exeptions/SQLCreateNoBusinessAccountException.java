/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Exeptions;

import de.sbg.unity.iconomy.Business.Business;

/**
 *
 * @author pott
 */
public class SQLCreateNoBusinessAccountException extends Exception{

    public SQLCreateNoBusinessAccountException(String msg) {
        super(msg);
    }

    public SQLCreateNoBusinessAccountException(Business b) {
        super("Can not add business account for '" + b.getName() + "' ID: " + b.getID() +"!");
    }
    
}
