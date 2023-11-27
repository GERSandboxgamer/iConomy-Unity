
package de.sbg.unity.iconomy.Utils;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.risingworld.api.objects.Player;


public class MoneyFormate {
    
    private final iConomy plugin;
    private final icConsole Console;
    
    public MoneyFormate(iConomy plugin, icConsole Console)  {
        this.plugin = plugin;
        this.Console = Console;
    }
    
    /**
     * Get the currency as <code>String</code>
     * @return
     */
    public String getCurrency(){
        return plugin.Config.Currency;
    }
    
    /**
     * Change money <b>long</b> to money <b>String</b>
     * @param money
     * @return
     */
    public String getMoneyAsString(long money) {
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat);
        double d = money;
        double d2 = d / 100;
        String st = df.format(d2);
        return st;
    }
    
    /**
     * Change money <b>long</b> to money <b>String</b>
     * @param money
     * @param player
     * @return
     */
    public String getMoneyAsString(long money, Player player) {
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat, DecimalFormatSymbols.getInstance(Locale.of(player.getLanguage())));
        double d = money;
        double d2 = d / 100;
        String st = df.format(d2);
        return st;
    }
    
    public String getMoneyAsFormatedString(Player player, long money) {
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat, DecimalFormatSymbols.getInstance(Locale.of(player.getLanguage())));
        double d = money;
        double d2 = d / 100;
        String st = df.format(d2) + " " + getCurrency();
        return st;
    }
    
    public String getMoneyAsDefaultFormatedString(long money){
        double d = money;
        double d2 = d / 100;
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat);
        String st = df.format(d2) + " " + getCurrency();
        return st;
    }

    public double getMoneyAsFloat(long money) {
        double d = money;
        double d2 = d / 100;
        return d2;
    }
    
    public long getMoneyAsLong(String money) throws NumberFormatException{
        String m2;
        if (money.contains(",")) {
            m2 = money.replace(",", ".");
        } else {
            m2 = money;
        }
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("getMoneyAsLong", "m2 = " + m2);
        }
        BigDecimal bd = new BigDecimal(m2);
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("getMoneyAsLong", "bd = " + bd);
        }
        BigDecimal bd2 = bd.multiply(new BigDecimal(100));
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("getMoneyAsLong", "bd (neu) = " + bd2);
        }
        
        return bd2.longValue();
        
    }
    
    public long getMoneyAsLong(BigDecimal money) {
        BigDecimal bd2 = money.multiply(new BigDecimal(100));
        return bd2.longValue();
    }
    
}
