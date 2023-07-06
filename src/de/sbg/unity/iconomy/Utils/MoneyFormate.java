
package de.sbg.unity.iconomy.Utils;

import de.sbg.unity.iconomy.iConomy;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.risingworld.api.objects.Player;


public class MoneyFormate {
    
    private final iConomy plugin;
    
    public MoneyFormate(iConomy plugin)  {
        this.plugin = plugin;
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
        return String.valueOf(money / 100);
    }
    
    public String getMoneyAsFormatedString(Player player, long money) {
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat, DecimalFormatSymbols.getInstance(Locale.of(player.getLanguage())));
        String st = df.format(money / 100) + " " + getCurrency();
        return st;
    }   

    public float getMoneyAsFloat(long money) {
        return money / 100;
    }
    
    public long getMoneyAsLong(String money) throws NumberFormatException{
        String m2;
        if (money.contains(",")) {
            m2 = money.replace(",", ".");
        } else {
            m2 = money;
        }
        float fm = Float.parseFloat(m2);
        return (long)(fm * 100);
    }
    
    public long getMoneyAsLong(float money) {
        return (long)(money * 100);
    }
    
}
