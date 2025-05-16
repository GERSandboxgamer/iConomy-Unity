package de.sbg.unity.iconomy.GUI.List;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import java.util.HashMap;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UIScrollView;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.StyleKeyword;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

//UIScrollElement
public class AccountList implements Listener {

    private final Player player;
    private final TextFormat format;
    private final String lang;
    public final UIAccountList accountList;
    //public final UILabel buttonCancel;
    private final iConomy plugin;
    private final UIElement panel;
    private UIAccountLabel selectedAccountLabel;
    private final boolean all, own;

    public interface SelectCallback {

        public void onSelection(Player selector, UIAccountLabel select);
    }

    public AccountList(Player player, boolean all, iConomy plugin, SelectCallback cb) {
        this.plugin = plugin;
        this.player = player;
        this.format = new TextFormat();
        this.lang = player.getLanguage();
        this.all = all;
        this.own = true;

        panel = new UIElement();

        accountList = new UIAccountList(player, all, true, plugin, cb);
        updateAccounts(own, all);
        //buttonCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        Body();
        
    }

    public AccountList(Player player, boolean all, boolean own, iConomy plugin, SelectCallback cb) {
        this.plugin = plugin;
        this.player = player;
        this.format = new TextFormat();
        this.lang = player.getLanguage();
        this.all = all;
        this.own = own;

        panel = new UIElement();

        accountList = new UIAccountList(player, all, own, plugin, cb);
        updateAccounts(own, all);
        //buttonCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        Body();
    }

    public UIAccountLabel getSelectedAccountLabel() {
        return selectedAccountLabel;
    }

    public void setSelectedAccountLabel(UIAccountLabel selectedAccountLabel) {
        this.selectedAccountLabel = selectedAccountLabel;
    }

    private void Body() {
        //this.setPosition(50, 50, true);
        //panel.setPivot(Pivot.MiddleCenter);
        panel.setBackgroundColor(0, 0, 102, 1);
        panel.style.height.set(StyleKeyword.Auto);
        panel.style.width.set(StyleKeyword.Auto);
        panel.style.flexGrow.set(1);
        //panel.setBorder(1);
        //panel.setBorderColor(ColorRGBA.White.toIntRGBA());

        accountList.setSize(100, 100, true);
        //accountList.setBorder(3);
        //accountList.setBorderColor(ColorRGBA.Black.toIntRGBA());
        panel.addChild(accountList);
        plugin.registerEventListener(accountList);

//        buttonCancel.setPosition(50, 98, true);
//        buttonCancel.setPivot(Pivot.LowerCenter);
//        buttonCancel.setFontSize(25);
//        buttonCancel.setClickable(true);
//        panel.addChild(buttonCancel);
    }

    public UIElement getPanel() {
        return panel;
    }

    public boolean isAll() {
        return all;
    }

    public boolean isOwn() {
        return own;
    }
    
    @EventMethod
    public void onPlayerClickCancelEvent(PlayerUIElementClickEvent event) {
        Player p = event.getPlayer();
        if (player.equals(p)) {
//            if (event.getUIElement() == buttonCancel) {
//                plugin.GUI.SelectOnlinePlayerGui.hideGui(player);
//            }
        }
    }
    
     public void updateAccounts(boolean own, boolean all){
            accountList.removeAllChilds();
            PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(player);
            if (own) {
                UIElement titelBox1 = new UIElement();
                titelBox1.style.width.set(95, Unit.Percent);
                titelBox1.style.height.set(50, Unit.Pixel);
                titelBox1.style.marginLeft.set(2, Unit.Percent);
                titelBox1.style.marginRight.set(2, Unit.Percent);
                titelBox1.style.marginTop.set(2, Unit.Percent);
                
                UILabel ownAccount = new UILabel("== Own Account ==");
                ownAccount.setFont(Font.DefaultBold);
                ownAccount.setFontSize(20);
                ownAccount.setFontColor(ColorRGBA.White.toIntRGBA());
                ownAccount.setPivot(Pivot.MiddleCenter);
                ownAccount.setPosition(50, 50, true);
                titelBox1.addChild(ownAccount);
                accountList.addChild(titelBox1);

                UIAccountLabel lab = new UIAccountLabel(pa.getLastKnownOwnerName(), pa, player);
                lab.setBackgroundColor(1, 1, 0, 1);
                for (UIElement el : lab.getChilds()) {
                    if (el instanceof UILabel l) {
                        l.setFontColor(ColorRGBA.Black.toIntRGBA());
                    }
                }

                setSelectedAccountLabel(lab);
                accountList.addChild(lab);
            }

            if (all) {
                if (plugin.Bankystem.PlayerSystem.getPlayerAccountsAmounth() >= 2) {
                    UIElement titelBox2 = new UIElement();
                    titelBox2.style.width.set(95, Unit.Percent);
                    titelBox2.style.height.set(50, Unit.Pixel);
                    titelBox2.style.marginLeft.set(2, Unit.Percent);
                    titelBox2.style.marginRight.set(2, Unit.Percent);
                    titelBox2.style.marginTop.set(2, Unit.Percent);

                    UILabel otherAccount = new UILabel("== Other Account ==");
                    otherAccount.setFont(Font.DefaultBold);
                    otherAccount.setFontSize(20);
                    otherAccount.setFontColor(ColorRGBA.White.toIntRGBA());
                    otherAccount.setPivot(Pivot.MiddleCenter);
                    otherAccount.setPosition(50, 50, true);
                    titelBox2.addChild(otherAccount);
                    accountList.addChild(titelBox2);
                    plugin.Bankystem.getAllBankAccounts().forEach(ba -> {
                        if (ba instanceof PlayerAccount pa2) {
                            if (pa2 != pa) {
                                UIAccountLabel lab2 = new UIAccountLabel(pa2.getLastKnownOwnerName(), ba, player);
                                accountList.addChild(lab2);
                            }
                        }
                    });
                }
                if (plugin.Bankystem.BusinessBankSystem.getBusinessAccountsAmounth() >= 1) {
                    UIElement titelBox2 = new UIElement();
                    titelBox2.style.width.set(95, Unit.Percent);
                    titelBox2.style.height.set(50, Unit.Pixel);
                    titelBox2.style.marginLeft.set(2, Unit.Percent);
                    titelBox2.style.marginRight.set(2, Unit.Percent);
                    titelBox2.style.marginTop.set(2, Unit.Percent);

                    UILabel factoryAccount = new UILabel("== Business ==");
                    factoryAccount.setFont(Font.DefaultBold);
                    factoryAccount.setFontSize(20);
                    factoryAccount.setFontColor(ColorRGBA.White.toIntRGBA());
                    factoryAccount.setPivot(Pivot.MiddleCenter);
                    factoryAccount.setPosition(50, 50, true);
                    titelBox2.addChild(factoryAccount);
                    accountList.addChild(titelBox2);
                    plugin.Bankystem.getAllBankAccounts().forEach(ba -> {
                        if (ba instanceof BusinessAccount fa2) {
                            UIAccountLabel lab2 = new UIAccountLabel(fa2.getBusiness().getName(), ba, player);
                            accountList.addChild(lab2);
                        }
                    });
                }

            } else {
                if (plugin.Bankystem.PlayerSystem.getPlayerAccountsAmounth(player) >= 2) {
                    UIElement titelBox2 = new UIElement();
                    titelBox2.style.width.set(95, Unit.Percent);
                    titelBox2.style.height.set(50, Unit.Pixel);
                    titelBox2.style.marginLeft.set(2, Unit.Percent);
                    titelBox2.style.marginRight.set(2, Unit.Percent);
                    titelBox2.style.marginTop.set(2, Unit.Percent);

                    UILabel otherAccount = new UILabel("== Other Account ==");
                    otherAccount.setFont(Font.DefaultBold);
                    otherAccount.setFontSize(20);
                    otherAccount.setFontColor(ColorRGBA.White.toIntRGBA());
                    otherAccount.setPivot(Pivot.MiddleCenter);
                    otherAccount.setPosition(50, 50, true);
                    titelBox2.addChild(otherAccount);
                    accountList.addChild(titelBox2);
                    plugin.Bankystem.PlayerSystem.getPlayerAccounts(player).forEach(pa2 -> {
                        if (pa2 != pa) {
                            UIAccountLabel lab2 = new UIAccountLabel(pa2.getLastKnownOwnerName(), pa2, player);
                            accountList.addChild(lab2);
                        }
                    });
                }
                if (plugin.Bankystem.BusinessBankSystem.getBusinessAccountsAmounth(player) >= 1) {
                    UIElement titelBox2 = new UIElement();
                    titelBox2.style.width.set(95, Unit.Percent);
                    titelBox2.style.height.set(50, Unit.Pixel);
                    titelBox2.style.marginLeft.set(2, Unit.Percent);
                    titelBox2.style.marginRight.set(2, Unit.Percent);
                    titelBox2.style.marginTop.set(2, Unit.Percent);

                    UILabel otherAccount = new UILabel("== Business ==");
                    otherAccount.setFont(Font.DefaultBold);
                    otherAccount.setFontSize(20);
                    otherAccount.setFontColor(ColorRGBA.White.toIntRGBA());
                    otherAccount.setPivot(Pivot.MiddleCenter);
                    otherAccount.setPosition(50, 50, true);
                    titelBox2.addChild(otherAccount);
                    accountList.addChild(titelBox2);
                    plugin.Bankystem.BusinessBankSystem.getBusinessAccounts(player).forEach(fa2 -> {
                        UIAccountLabel lab2 = new UIAccountLabel(fa2.getBusiness().getName(), fa2, player);
                        accountList.addChild(lab2);

                    });
                }
            }
            if (plugin.Config.Debug > 0) {
                PlayerAccount paD = plugin.Bankystem.PlayerSystem.getPlayerAccount(player);
                int z = 1;
                while (z <= 20) {
                    UIAccountLabel labD = new UIAccountLabel("Debug " + z, paD, player);
                    accountList.addChild(labD);
                    z = z + 1;
                }
            }
        }

    public class AccountResult {

        private final BankAccount bankAccount;

        public AccountResult(BankAccount ba) {
            this.bankAccount = ba;
        }

        public BankAccount getBankAccount() {
            return bankAccount;
        }

    }

    public class UIAccountList extends UIScrollView implements Listener {

        private final SelectCallback cb;

        public UIAccountList(Player player, boolean all, boolean own, iConomy plugin, SelectCallback cb) {
            super(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
            this.cb = cb;
            this.setHorizontalScrollerVisibility(UIScrollView.ScrollerVisibility.Hidden); //TODO Ändern
            this.setVerticalScrollerVisibility(UIScrollView.ScrollerVisibility.Auto);
        }
        
       

        @EventMethod
        public void onPlayerSelectAccountEvent(PlayerUIElementClickEvent event) {
            Player p = event.getPlayer();
            if (player.equals(p)) {
                if (event.getUIElement() instanceof UIAccountLabel label) {
                    cb.onSelection(player, label);
                }
            }
        }
    }

    public class UIAccountLabel extends UIElement {

        private final UILabel accountName;
        private final UILabel accountID;
        private final HashMap<String, Object> attribute;
        private final Player selector;
        private final BankAccount bankAccount;

        public UIAccountLabel(String accountName, BankAccount bankAccount, Player selector) {
            this.selector = selector;
            this.bankAccount = bankAccount;
            if (bankAccount instanceof PlayerAccount pa) {
                this.accountName = new UILabel(accountName);
                this.accountID = new UILabel(pa.getOwnerUID());
            } else if (bankAccount instanceof BusinessAccount fa) {
                this.accountName = new UILabel(accountName);
                this.accountID = new UILabel(String.valueOf(fa.getAccountID()));
            } else {
                this.accountName = new UILabel("Unknown");
                this.accountID = new UILabel("0");
            }
            this.attribute = new HashMap<>();
            setElement();
        }

        private void setElement() {
            //this.style.flexGrow.set(0);
            //this.style.flexShrink.set(0);
            this.style.width.set(95, Unit.Percent);
            this.style.height.set(50, Unit.Pixel);
            this.style.marginLeft.set(2, Unit.Percent);
            this.style.marginRight.set(2, Unit.Percent);
            this.style.marginTop.set(2, Unit.Percent);

            this.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
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

            this.accountName.setPivot(Pivot.MiddleLeft);
            this.accountName.setFontSize(20);
            this.accountName.setFont(Font.DefaultBold);
            this.accountName.setPosition(2, 50, true);

            this.accountID.setPivot(Pivot.MiddleRight);
            this.accountID.setFontSize(20);
            this.accountID.setFont(Font.DefaultBold);
            this.accountID.setPosition(98, 50, true);

            this.addChild(this.accountName);
            this.addChild(this.accountID);
        }

        public Player getSelector() {
            return selector;
        }

        public BankAccount getBankAccount() {
            return bankAccount;
        }

        public UILabel getAccountName() {
            return accountName;
        }

        public UILabel getAccountID() {
            return accountID;
        }

        public Object setAttribute(String key, Object value) {
            return attribute.put(key, value);
        }

        public Object getAttribute(String key) {
            return attribute.get(key);
        }

        public boolean hasAttribute(String key) {
            return attribute.containsKey(key);
        }

        public Object removeAttribute(String key) {
            if (hasAttribute(key)) {
                return attribute.remove(key);
            }
            return null;
        }

        public HashMap<String, Object> getAllAttributes() {
            return attribute;
        }
    }

}
