package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.GUI.List.PlayerList;
import de.sbg.unity.iconomy.GUI.List.PlayerList.UIPlayerLabel;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Justify;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class MemberUI extends MenuElement {

    private BankAccount bankAccount;
    private final Player player;
    private final icConsole Console;
    private final PlayerList playerList;
    private final List<String> allUIDS;

    public MemberUI(Player player, iConomy plugin) {
        this.player = player;
        this.Console = new icConsole(plugin);
        this.allUIDS = new ArrayList<>();

        this.style.flexDirection.set(FlexDirection.Column);
        this.style.justifyContent.set(Justify.SpaceBetween);

        this.setSize(100, 100, true);

        UIElement titelBox = new UIElement();
        titelBox.style.flexDirection.set(FlexDirection.Column);
        titelBox.style.width.set(100, Unit.Percent);
        titelBox.style.height.set(120, Unit.Pixel);
        titelBox.style.marginBottom.set(5, Unit.Pixel);

        UILabel titel = new UILabel("Members");
        titel.setFont(Font.DefaultBold);
        titel.setFontSize(28);
        titel.setTextAlign(TextAnchor.MiddleCenter);
        titel.setBorder(3);
        titel.setBorderColor(ColorRGBA.White.toIntRGBA());
        //titel.setPivot(Pivot.UpperCenter);

        UILabel info1 = new UILabel("Hier werden die Mitglieder dieses Accounts angezeigt.");
        info1.setFontSize(20);
        info1.setTextAlign(TextAnchor.MiddleCenter);
        //info.setPivot(Pivot.LowerCenter);

        UILabel info2 = new UILabel("grün = Mitglied; rot = Kein Mitglied");
        info2.setFontSize(20);
        info2.setTextAlign(TextAnchor.MiddleCenter);
        //info.setPivot(Pivot.LowerCenter);

        titelBox.addChild(titel);
        titelBox.addChild(info1);
        titelBox.addChild(info2);

//        UIElement blau = new UIElement();
//        blau.style.flexDirection.set(FlexDirection.Column);
//        blau.style.flexGrow.set(1);
//        blau.style.width.set(100, Unit.Percent);
//        blau.style.height.set(StyleKeyword.Auto);

        playerList = new PlayerList(player, plugin, (selector, select, lastSelected) -> {
            Console.sendDebug("PlayerList-Click", "Es wurde auf die Liste geklickt!");
            if (bankAccount instanceof PlayerAccount pa) {
                if (pa.isMember(select.getPlayerObject().getUID())) {
                    pa.removeMember(player, select.getPlayerObject().getUID());
                    select.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
                    select.getPlayernameLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                    select.getUidLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                } else {
                    pa.addMember(player, select.getPlayerObject().getUID());
                    select.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
                    select.getPlayernameLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                    select.getUidLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                }
            }
            if (bankAccount instanceof FactoryAccount fa) {
                if (fa.isMember(select.getPlayerObject().getUID())) {
                    fa.removeMember(player, select.getPlayerObject().getUID());
                    select.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
                    select.getPlayernameLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                    select.getUidLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                } else {
                    fa.addMember(player, select.getPlayerObject().getUID());
                    select.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
                    select.getPlayernameLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                    select.getUidLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                }
            }

        });
        playerList.removeButtonCancel();
        playerList.getPanel().setPivot(Pivot.UpperLeft);
        playerList.getPanel().setSize(100, 100, true);
        //playerList.getPanel().setSize(500, 600, false);
        //playerList.getPanel().style.width.set(100, Unit.Pixel);
        

        this.addChild(titelBox);
        this.addChild(playerList.getPanel());

        if (plugin.Config.Debug > 0) {
            this.setBorder(4);
            this.setBorderColor(ColorRGBA.Green.toIntRGBA());
            
            titel.setBorder(3);
            titel.setBorderColor(ColorRGBA.Black.toIntRGBA());

            titelBox.setBorder(3);
            titelBox.setBorderColor(ColorRGBA.Red.toIntRGBA());

            info1.setBorder(3);
            info1.setBorderColor(ColorRGBA.Black.toIntRGBA());

            info2.setBorder(3);
            info2.setBorderColor(ColorRGBA.Black.toIntRGBA());

            playerList.getPanel().setBorder(10);
            playerList.getPanel().setBorderColor(1, 1, 0, 1);
        }

    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        playerList.getPlayerListElements().clearElements();
        playerList.getPlayerListElements().addElements();
        playerList.getPlayerListElements().addOfflineMembers(bankAccount);

        playerList.getPlayerListElements().getChilds().forEach((t) -> {
            if (t instanceof UIPlayerLabel label) {
                if (bankAccount instanceof PlayerAccount pa) {
                    if (pa.isMember(label.getPlayerObject().getUID())) {
                        label.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
                        label.getPlayernameLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                        label.getUidLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                    } else {
                        label.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
                        label.getPlayernameLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                        label.getUidLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                    }
                }
                if (bankAccount instanceof FactoryAccount fa) {
                    if (fa.isMember(label.getPlayerObject().getUID())) {
                        label.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
                        label.getPlayernameLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                        label.getUidLabel().setFontColor(ColorRGBA.Black.toIntRGBA());
                    } else {
                        label.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
                        label.getPlayernameLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                        label.getUidLabel().setFontColor(ColorRGBA.White.toIntRGBA());
                    }
                }
            }
//            t.getChilds().get(1).setPosition(50, 50, true);
//            t.getChilds().get(1).setPivot(Pivot.MiddleCenter);
//            UICheckButton chBut = new UICheckButton(plugin);
//            chBut.setSize(20, 20, false);
//            chBut.setPosition(100, 50, true);
//            chBut.setPivot(Pivot.MiddleRight);
//            chBut.setClickable(false);
//            t.addChild(chBut);
        });

    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

}
