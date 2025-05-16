package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Exeptions.NotPlayerAccountExeption;
import de.sbg.unity.iconomy.GUI.List.PlayerList;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UIScrollView;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Justify;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class PlayerPermissions extends MenuElement implements Listener {

    private PlayerAccount playerAccount;
    private final Player player;
    private final iConomy plugin;
    private final icConsole Console;

    private final PlayerList playerList;
    private final UIScrollView uiRight;
    private final String lang;

    public PlayerPermissions(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.player = player;
        this.lang = player.getLanguage();
        this.Console = new icConsole(plugin);
        Console.sendDebug("PlayerPermissions", "new PlayerPermissions");
        this.style.flexDirection.set(FlexDirection.Column);
        this.style.justifyContent.set(Justify.SpaceBetween);

        this.setSize(100, 100, true);

        UIElement titelBox = new UIElement();
        titelBox.style.flexDirection.set(FlexDirection.Column);
        titelBox.style.width.set(100, Unit.Percent);
        //titelBox.style.height.set(120, Unit.Pixel);
        titelBox.style.marginBottom.set(5, Unit.Pixel);

        UILabel titel = new UILabel(plugin.Language.getGui().getPermissions(lang));
        titel.setFont(Font.DefaultBold);
        titel.setFontSize(28);
        titel.setTextAlign(TextAnchor.MiddleCenter);
        titel.setBorder(3);
        titel.setBorderColor(ColorRGBA.White.toIntRGBA());
        titelBox.addChild(titel);

        UIElement uiBig = new UIElement();
        uiBig.setSize(100, 100, true);
        uiBig.style.flexDirection.set(FlexDirection.Row);

        playerList = new PlayerList(player, plugin, (selector, select, lastSelected) -> {
            //Console.sendDebug("PlayerPermissions", "playerList-Collback");

            //Console.sendDebug("PlayerPermissions", "this = " + this.getClass().getName());
            addAllPermissions(select.getPlayerObject().getUID());
            //Console.sendDebug("PlayerPermissions", "addAllPermissions()");

        });
        playerList.getPanel().setSize(50, 100, true);
        playerList.removeButtonCancel();
        uiBig.addChild(playerList.getPanel());

        uiRight = new UIScrollView(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
        uiRight.setHorizontalScrollerVisibility(UIScrollView.ScrollerVisibility.Hidden);
        uiRight.setVerticalScrollerVisibility(UIScrollView.ScrollerVisibility.Auto);
        uiRight.style.flexDirection.set(FlexDirection.Column);
        uiRight.setSize(50, 100, true);
        uiRight.setBorder(1);
        uiRight.setBorderColor(ColorRGBA.White.toIntRGBA());
        uiBig.addChild(uiRight);

        

        this.addChild(titelBox);
        this.addChild(uiBig);

        if (plugin.Config.Debug > 0) {
            
            uiBig.setBorder(5);
            uiBig.setBorderColor(ColorRGBA.Green.toIntRGBA());
            playerList.getPanel().setBorder(1);
            playerList.getPanel().setBorderColor(ColorRGBA.Red.toIntRGBA());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }

    public UIScrollView getUiRight() {
        return uiRight;
    }

    private void addAllPermissions(String uid) {
        uiRight.removeAllChilds();
        BankMember bm = playerAccount.getMember(uid);
        for (PlayerAccountPermission per : PlayerAccountPermission.values()) {
            uiRight.addChild(new PermissionLabel(playerAccount, uid, per, bm.hasPermission(per)));
        }
    }

    @EventMethod
    public void onPlayerKickPermissionEvent(PlayerUIElementClickEvent event) {
        //Console.sendDebug("onPlayerKickPermissionEvent", "Event!");

        Player p = event.getPlayer();
        UIElement el = event.getUIElement();
        if (p.equals(player)) {
            if (el instanceof PermissionLabel lab) {
                //Console.sendDebug("onPlayerKickPermissionEvent", "Is PermissionLabel");
                BankMember member = lab.getAccount().getMember(lab.getPlayerUID());
                if (lab.getPermissionValue()) {
                    member.removePermission(lab.getPermission());
                    lab.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
                    lab.setPermissionValue(false);
                } else {
                    member.addPermission(lab.getPermission());
                    lab.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
                    lab.setPermissionValue(true);
                }
                
                switch(lab.getPermission()) {
                    
                    case SHOW_ACCOUNT -> {
                        if (plugin.isPlayerConneted(lab.getPlayerUID())) {
                            Player m = Server.getPlayerByUID(lab.getPlayerUID());
                            MainGUI gui = plugin.GUI.Bankystem.MainGui.getGui(m);
                            if (gui != null) {
                                gui.getAccountList().updateAccounts(gui.getAccountList().isOwn(), gui.getAccountList().isAll());
                            }
                        }
                    }
                    
                }

            }
        }

    }

    public class PermissionLabel extends UIElement {

        private final PlayerAccountPermission permission;
        private boolean permissionValue;
        private final String playerUID;
        private final PlayerAccount account;

        public PermissionLabel(PlayerAccount pa, String uid, PlayerAccountPermission permission, boolean wert) {
            this.playerUID = uid;
            this.permission = permission;
            this.permissionValue = wert;
            this.account = pa;

            //this.style.flexGrow.set(0);
            //this.style.flexShrink.set(0);
            this.style.width.set(95, Unit.Percent);
            this.style.height.set(50, Unit.Pixel);
            this.style.marginTop.set(5, Unit.Pixel);
            this.style.marginLeft.set(2, Unit.Percent);
            this.style.marginRight.set(2, Unit.Percent);
//            this.style.marginTop.set(2, Unit.Percent);
//            this.style.marginLeft.set(2, Unit.Percent);
//            this.style.marginRight.set(2, Unit.Percent);

            if (wert) {
                this.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
            } else {
                this.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
            }
            this.setBorder(1);
            this.setBorderColor(ColorRGBA.White.toIntRGBA());

            this.style.borderBottomLeftRadius.set(12, Unit.Pixel);
            this.style.borderBottomRightRadius.set(12, Unit.Pixel);
            this.style.borderTopLeftRadius.set(12, Unit.Pixel);
            this.style.borderTopRightRadius.set(12, Unit.Pixel);

            this.style.borderBottomWidth.set(1);
            this.style.borderTopWidth.set(1);
            this.style.borderLeftWidth.set(1);
            this.style.borderRightWidth.set(1);

            this.setClickable(true);

            UILabel lab = new UILabel(permission.name());
            lab.setPivot(Pivot.MiddleCenter);
            lab.setFontSize(20);
            lab.setFont(Font.DefaultBold);
            lab.setPosition(50, 50, true);

            this.addChild(lab);
        }

        public PlayerAccountPermission getPermission() {
            return permission;
        }

        public String getPlayerUID() {
            return playerUID;
        }

        public boolean getPermissionValue() {
            return permissionValue;
        }

        public void setPermissionValue(boolean permissionValue) {
            this.permissionValue = permissionValue;
        }

        public PlayerAccount getAccount() {
            return account;
        }
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        playerList.getPlayerListElements().clearElements();
        playerList.getPlayerListElements().addBankMembers(bankAccount);
        if (bankAccount instanceof PlayerAccount pa) {
            this.playerAccount = pa;
            //Console.sendDebug("PlayerPermissions", "set BankAccount");

        } else {
            try {
                throw new NotPlayerAccountExeption();
            } catch (NotPlayerAccountExeption ex) {
                icConsole Console = new icConsole(plugin);
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }

}
