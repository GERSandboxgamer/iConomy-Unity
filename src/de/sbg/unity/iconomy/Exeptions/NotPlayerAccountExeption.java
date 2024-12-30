package de.sbg.unity.iconomy.Exeptions;

/**
 *
 * @hidden  
 */
public class NotPlayerAccountExeption extends Exception{
    public NotPlayerAccountExeption() {
        super("The BankAccount is not a PlayerAccount!");
    }
}
