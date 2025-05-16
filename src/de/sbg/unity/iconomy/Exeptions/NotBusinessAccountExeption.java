package de.sbg.unity.iconomy.Exeptions;

/**
 *
 * @hidden  
 */
public class NotBusinessAccountExeption extends Exception{
    public NotBusinessAccountExeption() {
        super("The BankAccount is not a FactoryAccount!");
    }
}
