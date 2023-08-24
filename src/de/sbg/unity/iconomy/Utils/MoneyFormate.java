
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
        float f = money / 100;
        return String.valueOf(f);
    }
    
    public String getMoneyAsFormatedString(Player player, long money) {
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat, DecimalFormatSymbols.getInstance(Locale.of(player.getLanguage())));
        double d = money / 100;
        String st = df.format(d) + " " + getCurrency();
        return st;
    }
    
    public String getMoneyAsDefaultFormatedString(long money){
        double d = money /100;
        DecimalFormat df = new DecimalFormat(plugin.Config.MoneyFormat);
        String st = df.format(d);
        return st;
    }

    public float getMoneyAsFloat(long money) {
        float f = money / 100;
        return f;
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
