package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.Timer;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;

public final class MoneyInfoGUI {

    private final UIElement panel;
    private final icConsole Console;
    private final Player player;
    private final iConomy plugin;
    private final UILabel Line1, Line2;
    private final int lineAmount;
    private Timer CloseTimer;

    public MoneyInfoGUI(iConomy plugin, icConsole Console, Player player, String line1Text) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("MoneyInfoGUI Line 1");
        }
        panel = new UIElement();
        this.Console = Console;
        this.player = player;
        this.plugin = plugin;

        panel.setPosition(50, 0, true);  // <- true indicates we're using relative coordinates (0-100%)
        panel.setPivot(Pivot.UpperCenter);
        panel.setSize(500, 50, false);
        panel.setBackgroundColor(0, 0, 100, 1);  // black background

        Line1 = new UILabel(line1Text);
        Line1.setPosition(50, 50, true);
        Line1.setPivot(Pivot.MiddleCenter);
        Line1.setFontSize(25);
        panel.addChild(Line1);  // <- label becomes a child of panel
        player.addUIElement(panel);
        
        Line2 = null;
        
        lineAmount = 1;
        
        startCloseTimer();
    }

    public MoneyInfoGUI(iConomy plugin, icConsole Console, Player player, String line1text, String line2text) {
        panel = new UIElement();
        this.Console = Console;
        this.player = player;
        this.plugin = plugin;
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("MoneyInfoGUI", "Line 1: " + line1text);
            Console.sendDebug("MoneyInfoGUI", "Line 2: " + line2text);
        }

        panel.setPosition(50, 0, true);  // <- true indicates we're using relative coordinates (0-100%)
        panel.setPivot(Pivot.UpperCenter);
        panel.setSize(500, 100, false);
        panel.setBackgroundColor(0, 0, 0, 50);  // black background

        Line1 = new UILabel(line1text);
        Line1.setPosition(50, 0, true);
        Line1.setPivot(Pivot.UpperCenter);
        Line1.setFontSize(25);
        panel.addChild(Line1);  // <- label becomes a child of panel
        
        Line2 = new UILabel(line2text);
        Line2.setPosition(50, 100, true);
        Line2.setPivot(Pivot.LowerCenter);
        Line2.setFontSize(25);
        panel.addChild(Line2);  // <- label becomes a child of panel
        
        lineAmount = 2;
        
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("MoneyInfoGUI", "Line 1 (l1): " + Line1);
            Console.sendDebug("MoneyInfoGUI", "Line 1 (l2):" + Line2);
        }
        
        player.addUIElement(panel);
        startCloseTimer();
    }

    public UIElement getPanel() {
        return panel;
    }

    public UILabel getLine1() {
        return Line1;
    }

    public UILabel getLine2() {
        return Line2;
    }

    public int getLineAmount() {
        return lineAmount;
    }

    public void startCloseTimer() {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("MoneyInfoGUI", "Timer: Start");
        }
        CloseTimer = new Timer(plugin.Config.MoneyInfoTime, 0, 0, () -> {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("MoneyInfoGUI", "Timer: Tick");
            }
            plugin.GUI.MoneyInfoGui.hideGUI(player);
            stopCloseTimer();
        });
        CloseTimer.start();
    }

    public void stopCloseTimer() {
        if (CloseTimer != null) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("MoneyInfoGUI", "Timer: Stop");
            }
            CloseTimer.kill();
        }
    }

    public Timer getCloseTimer() {
        return CloseTimer;
    }
    
    

}
