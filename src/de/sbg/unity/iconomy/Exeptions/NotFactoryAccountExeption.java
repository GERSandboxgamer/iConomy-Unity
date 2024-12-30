package de.sbg.unity.iconomy.Exeptions;

/**
 *
 * @hidden  
 */
public class NotFactoryAccountExeption extends Exception{
    public NotFactoryAccountExeption() {
        super("The BankAccount is not a FactoryAccount!");
    }
}
