package de.sbg.unity.iconomy.Exeptions;

/**
 *
 * @hidden  
 */
public class NotBusinessAccountException extends Exception{
    public NotBusinessAccountException() {
        super("The BankAccount is not a FactoryAccount!");
    }
}
