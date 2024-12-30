package de.sbg.unity.iconomy.GUI.Banksystem;

import de.chaoswg.gui.UIButton;
import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.iConomy;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UIScrollView;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.StyleKeyword;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class Statement extends MenuElement implements Listener {

    private final UIButton clearAll;

    private final iConomy plugin;
    private final String lang;
    private final Player player;
    private final MainGUI gui;
    private final UIScrollView list;
    private BankAccount bankAccount;

    public Statement(Player player, iConomy plugin) {

        this.plugin = plugin;
        this.lang = player.getLanguage();
        this.player = player;
        this.gui = plugin.GUI.Bankystem.MainGui.getGui(player);

        this.setSize(100, 100, true);
        this.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        this.style.flexDirection.set(FlexDirection.Column);

        UIElement gButten = new UIElement();
        gButten.style.height.set(10, Unit.Percent);
        gButten.style.width.set(StyleKeyword.Auto);
        gButten.setBorder(5);
        gButten.setBorderColor(ColorRGBA.Green.toIntRGBA());

        clearAll = new UIButton("Clear all"); //TODO Lang
        clearAll.setSize(160, 60, false);
        clearAll.setPosition(50, 50, true);
        clearAll.setPivot(Pivot.MiddleCenter);
        clearAll.setFontSize(25);
        clearAll.setFont(Font.DefaultBold);
        gButten.addChild(clearAll);

        list = new UIScrollView(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
        list.setSize(100, 90, true);
        list.setBorder(1);
        list.setBorderColor(ColorRGBA.White.toIntRGBA());

        this.addChild(list);
        this.addChild(gButten);

    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        list.removeAllChilds();
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy HH:mm:ss", Locale.GERMAN);
        DateTimeFormatter englishFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm:ss a", Locale.ENGLISH);
        
        for (BankStatement st : bankAccount.getStatements()) {
                String stStatement;
                if (player.getLanguage().equals("de")) {
                    String germanTime = st.getTime().format(germanFormatter) + " - ";
                    stStatement = germanTime + st.getStatement();
                } else {
                    String otherTime = st.getTime().format(englishFormatter) + " - ";
                    stStatement = otherTime + st.getStatement();
                }
            UILabel lab = new UILabel(stStatement);
            lab.setFontSize(20);
            lab.setFontColor(ColorRGBA.White.toIntRGBA());
            list.addChild(lab);
        }
        if (plugin.Config.Debug > 0) {
            if (bankAccount.getStatements().size() < 2) {
                int f = 1;
                while (f <= 10) {
                    UILabel pLabel = new UILabel("Debug " + f);
                    list.addChild(pLabel);
                    f = f + 1;
                }
            }
        }
        this.bankAccount = bankAccount;
    }

    public UIScrollView getList() {
        return list;
    }

    public UIButton getClearAll() {
        return clearAll;
    }

    @EventMethod
    public void onPlayerClearButtonEvent(PlayerUIElementClickEvent event) {

    }

}
