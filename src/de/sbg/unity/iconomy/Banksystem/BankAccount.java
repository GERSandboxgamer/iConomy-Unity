package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Utils.AccountTyp;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

public class BankAccount {

    private final List<String> Statements;
    private long Money, Min;
    private final AccountTyp typ;
    private final MoneyFormate MoneyFormat;

    public BankAccount(iConomy plugin, AccountTyp typ) {
        this.Statements = new ArrayList<>();
        this.typ = typ;
        this.MoneyFormat = new MoneyFormate(plugin);
    }

    public AccountTyp getTyp() {
        return typ;
    }

    public List<String> getStatements() {
        return Statements;
    }

    public List<String> getStatements(int limit) throws NumberFormatException{
        List<String> l = new ArrayList<>();
        if (limit > 0) {
            for (int i = 0; i <= limit; i++) {
                l.add(Statements.get(i));
            }
            return l;
        } else {
            throw new NumberFormatException("Limit must be bigger then 0");
        }
    }

    public void addStatement(String s) {
        Statements.add(0, s);
    }

    public void clearStatement() {
        Statements.clear();
    }

    public long getMoney() {
        return Money;
    }

    public void setMoney(long Money) {
        this.Money = Money;
    }

    public long getMin() {
        return Min;
    }

    public void setMin(long Min) {
        this.Min = Min;
    }
    
    public TransferResult addMoney(Player sender, long amounth) {
        //TODO
        return null;
    }
    public TransferResult addMoney(BankAccount sender, long amounth) {
        //TODO
        return null;
    }
    
    public TransferResult addMoney(long amounth) {
        //TODO
        return null;
    }
    
    public TransferResult removeMoney(Player sender, long amounth) {
        //TODO
        return null;
    }
    public TransferResult removeMoney(BankAccount sender, long amounth) {
        //TODO
        return null;
    }
    
    public TransferResult removeMoney(long amounth) {
        //TODO
        return null;
    }
    
    public String getMoneyAsFormatedString() {
        return MoneyFormat.getMoneyAsString(Money) + " " + MoneyFormat.getCurrency();
    }
    
    public TransferResult cashOut(Player player, long amounth) {
        //TODO
        return null;
    }
    
    public TransferResult cashIn(Player player, long amounth) {
        //TODO
        return null;
    }

}
