package de.sbg.unity.iconomy.Exeptions;

/**
 *
 * @hidden  
 */
public class NotPlayerAccountException extends Exception{
    public NotPlayerAccountException() {
        super("The BankAccount is not a PlayerAccount!");
    }
}
