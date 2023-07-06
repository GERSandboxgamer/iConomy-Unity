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
        panel.setBackgroundColor(0, 0, 0, 1);  // black background

        UILabel label = new UILabel(line1Text);
        label.setPosition(50, 50, true);
        label.setPivot(Pivot.MiddleCenter);
        label.setFontSize(25);
        panel.addChild(label);  // <- label becomes a child of panel
        player.addUIElement(panel);
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
        panel.setBackgroundColor(0, 0, 0, 1);  // black background

        UILabel l1 = new UILabel(line1text);
        l1.setPosition(50, 0, true);
        l1.setPivot(Pivot.UpperCenter);
        l1.setFontSize(25);
        panel.addChild(l1);  // <- label becomes a child of panel
        
        UILabel l2 = new UILabel(line2text);
        l2.setPosition(50, 100, true);
        l2.setPivot(Pivot.LowerCenter);
        l2.setFontSize(25);
        panel.addChild(l2);  // <- label becomes a child of panel
        
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("MoneyInfoGUI", "Line 1 (l1): " + l1);
            Console.sendDebug("MoneyInfoGUI", "Line 1 (l2):" + l2);
        }
        
        player.addUIElement(panel);
        startCloseTimer();
    }

    public UIElement getPanel() {
        return panel;
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

}
