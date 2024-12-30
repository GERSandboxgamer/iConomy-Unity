package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.assets.TextureAsset;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;

public class SelectCashInOutGUI {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    private final UIElement panel;
    private final UILabel butIn, butOut, butCancel;
    private final String lang;

    public SelectCashInOutGUI(iConomy plugin, icConsole Console, Player player) {
        this.plugin = plugin;
        this.Console = Console;
        this.format = new TextFormat();
        this.lang = player.getLanguage();

        this.panel = new UIElement();
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(350, 300, false);

        TextureAsset image = TextureAsset.loadFromPlugin(plugin, "/resources/guiBackground1_Viereck.png");
        panel.style.backgroundImage.set(image);

        TitelBar();

        UILabel message;
        message = new UILabel(format.Bold(format.Color("black", plugin.Language.getGui().getSelectCashInOutGUI_Message(lang))));
        message.setPosition(50, 20, true);
        message.setPivot(Pivot.UpperCenter);
        message.setSize(800, 50, false);
        message.setFontSize(25);
        panel.addChild(message);

        this.butIn = new UILabel(format.Bold("[Cash > Bank]"));
        butIn.setPosition(50, 45, true);
        butIn.setPivot(Pivot.MiddleCenter);
        butIn.setFontSize(25);
        butIn.setClickable(true);
        butIn.setBackgroundColor(255, 153, 51, 1);
        panel.addChild(butIn);

        this.butOut = new UILabel(format.Bold("[Bank > Cash]"));
        butOut.setPosition(50, 65, true);
        butOut.setPivot(Pivot.MiddleCenter);
        butOut.setFontSize(25);
        butOut.setClickable(true);
        butOut.setBackgroundColor(255, 153, 51, 1);
        panel.addChild(butOut);

        this.butCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        butCancel.setPosition(50, 90, true);
        butCancel.setPivot(Pivot.MiddleCenter);
        butCancel.setFontSize(25);
        butCancel.setClickable(true);
        panel.addChild(butCancel);

        plugin.registerEventListener(new SelectCashInOutGUIListener());
        player.addUIElement(panel);

    }

    public UIElement getPanel() {
        return panel;
    }

    private void TitelBar() {

        UIElement tielBar = new UIElement();
        tielBar.setPosition(50, 0, true);
        tielBar.setPivot(Pivot.UpperCenter);
        tielBar.setSize(250, 50, false);
        tielBar.setBackgroundColor(255, 153, 51, 1);

        UILabel labTitel = new UILabel(format.Bold(format.Underline(plugin.Language.getGui().getSelectCashInOutGUI_Titel(lang))));

        labTitel.setPosition(50, 50, true);
        labTitel.setPivot(Pivot.MiddleCenter);
        labTitel.setFontSize(25);
        labTitel.setFontColor(255, 255, 255, 1);
        labTitel.setBackgroundColor(255, 153, 51, 1);
        tielBar.addChild(labTitel);
        panel.addChild(tielBar);
    }

    public class SelectCashInOutGUIListener implements Listener {

        @EventMethod
        public void onPlayerUIKlickEvent(PlayerUIElementClickEvent event) {
            UIElement e = event.getUIElement();
            Player player = event.getPlayer();

            if (e == butIn) {
                plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.In);
                plugin.GUI.Bankystem.SelectCashInOutGui.hideGUI(player, false);
            }

            if (e == butOut) {
                plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.Out);
                plugin.GUI.Bankystem.SelectCashInOutGui.hideGUI(player, false);
            }

            if (e == butCancel) {
                plugin.GUI.Bankystem.SelectCashInOutGui.hideGUI(player);
            }
        }
    }

}
