package de.sbg.unity.iconomy.GUI.Banksystem;

import de.chaoswg.gui.StyleUI;
import de.chaoswg.gui.StyleUI.Frame;
import de.chaoswg.gui.UIFrame;
import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.GUI.List.AccountList;
import de.sbg.unity.iconomy.GUI.List.AccountList.UIAccountLabel;
import de.sbg.unity.iconomy.Utils.BusinessAccountPermission;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.risingworld.api.assets.TextureAsset;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Justify;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.ScaleMode;
import net.risingworld.api.ui.style.StyleKeyword;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class MainGUI implements Listener {

    private final UIFrame window;
    private final TextFormat format;
    private final iConomy plugin;
    //private final UIScrollView menu;
    private final HashMap<UIElement, MenuElement> menuData;
    private final Player user;
    private final UIElement menuElement, groupMenu;
    private UIElement selectedMenuElement;
    private final AccountInfo accountInfo;
    private final BusinessInfo factoryInfo;
    private final BusinessPermissions factoryPermissions;
    private final MemberUI memberUI;
    private final Money money;
    private final PlayerPermissions playerPermissions;
    private final Statement statement;
    private final AccountList accountList;
    private final icConsole Console;
    private final String lang;
    

    public MainGUI(Player player, boolean all, iConomy plugin) {

        lang = player.getLanguage();
        this.format = new TextFormat();
        this.plugin = plugin;
        this.menuData = new LinkedHashMap<>();
        this.user = player;
        this.Console = new icConsole(plugin);
        
        Console.sendDebug("MainGUI", "Erstelle alle Tab-Inhalte neu");
        this.accountInfo = new AccountInfo(player, plugin);
        this.factoryInfo = new BusinessInfo(plugin);
        this.factoryPermissions = new BusinessPermissions(player, plugin);
        this.memberUI = new MemberUI(player, plugin);
        this.playerPermissions = new PlayerPermissions(player, plugin);
        this.statement = new Statement(player, plugin);
        this.money = new Money(player, plugin);

        float sizeFont = 20f, roundStep = 1.25f;
        StyleUI.Border borderFrame = new StyleUI().new Border(ColorRGBA.Red, ColorRGBA.Green, 2.5f, new StyleUI().new Radius(20.0f, 20.0f, 7.5f, 7.5f));
        StyleUI.Border borderTitle = new StyleUI().new Border(ColorRGBA.Red, ColorRGBA.Green, 2.5f, new StyleUI().new Radius((sizeFont * roundStep) / 2f, (sizeFont * roundStep) / 2f, (sizeFont * roundStep) / 2f, (sizeFont * roundStep) / 2f));
        //StyleUI.BorderStyle borderExit = new StyleUI().new BorderStyle(ColorRGBA.Red, ColorRGBA.Green, 2.5f, new StyleUI().new RadiusStyle((sizeFontroundStep)/3f, (sizeFontroundStep)/2f, (sizeFontroundStep)/3f, (sizeFontroundStep)/2f));
        StyleUI.Border borderExit = new StyleUI().new Border(ColorRGBA.Red, ColorRGBA.Green, 0f, new StyleUI().new Radius((sizeFont * roundStep) / 3f, (sizeFont * roundStep) / 2f, (sizeFont * roundStep) / 3f, (sizeFont * roundStep) / 2f));
        StyleUI.Image image = new StyleUI().new Image(TextureAsset.loadFromGame("Interface/icons/flaticon/x.png"), ColorRGBA.White, ScaleMode.ScaleToFit);
        StyleUI.Image imageHover = new StyleUI().new Image(TextureAsset.loadFromGame("Interface/icons/flaticon/error1.png"), ColorRGBA.White, ScaleMode.ScaleToFit);
        StyleUI.Button bntExit = new StyleUI().new Button(image, borderExit, new ColorRGBA(0.25f, 0.25f, 0.25f, 1), imageHover, borderExit, new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        StyleUI.Frame frameStyle = new StyleUI().new Frame(StyleUI.Frame.TitlePivot.Mitte, borderFrame, 2.0f, 0.0f, sizeFont, Font.Mono, borderTitle, /* ColorRGBA.Clear, borderExit */ bntExit);

        window = new UIFrame(player, ColorRGBA.Blue, frameStyle);
        window.setPosition(50, 50, true);
        window.setPivot(Pivot.MiddleCenter);
        window.setSize(90, 90, true);
        if (all) {
            window.setTitleText("Banksystem (Admin)");
        } else {
            window.setTitleText("Banksystem");
        }
        window.setOnExit((playerOnExit, uif) -> {
            //SaveAll();
            //Evtl. löschen anderer GUIs
            plugin.GUI.Bankystem.MainGui.hideGUI(player);
        });
        StyleUI.setBorder(window.getUiContent().style, borderFrame);
        window.getUiContent().style.flexDirection.set(FlexDirection.Row);

        UIElement grun = new UIElement();
        grun.style.flexDirection.set(FlexDirection.Column);
        grun.style.width.set(30, Unit.Percent);
        grun.style.height.set(StyleKeyword.Auto);
        //grun.style.flexGrow.set(1);

        UILabel accounts = new UILabel(plugin.Language.getGui().getAccounts(lang));
        accounts.setFontSize(22);
        accounts.setTextAlign(TextAnchor.MiddleCenter);
        accounts.setFont(Font.DefaultBold);
        accounts.style.width.set(StyleKeyword.Auto);
        accounts.style.height.set(StyleKeyword.Auto);

        UIElement listTitel = new UIElement();
        listTitel.style.width.set(StyleKeyword.Auto);
        listTitel.style.height.set(StyleKeyword.Auto);

        listTitel.style.flexDirection.set(FlexDirection.Row);
        listTitel.style.justifyContent.set(Justify.SpaceAround);

        UILabel labName = new UILabel("Name");
        labName.setFontSize(16);
        labName.style.width.set(StyleKeyword.Auto);
        labName.style.height.set(StyleKeyword.Auto);

        UILabel labID = new UILabel("ID");
        labID.setFontSize(16);
        labID.style.width.set(StyleKeyword.Auto);
        labID.style.height.set(StyleKeyword.Auto);

        accountList = new AccountList(player, all, plugin, (selector, select) -> {
            if (select != getAccountList().getSelectedAccountLabel()) {
                select.setBackgroundColor(1, 1, 0, 1);
                select.getAccountID().setFontColor(ColorRGBA.Black.toIntRGBA());
                select.getAccountName().setFontColor(ColorRGBA.Black.toIntRGBA());
                getAccountList().getSelectedAccountLabel().setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
                getAccountList().getSelectedAccountLabel().getAccountID().setFontColor(ColorRGBA.White.toIntRGBA());
                getAccountList().getSelectedAccountLabel().getAccountName().setFontColor(ColorRGBA.White.toIntRGBA());
                setSelectedAccountLabel(select);
                createMenuListe(player, select.getBankAccount());
            }
        });

        UIElement rot = new UIElement();
        rot.style.flexDirection.set(FlexDirection.Column);
        rot.style.width.set(StyleKeyword.Auto);
        rot.style.height.set(StyleKeyword.Auto);
        rot.style.flexGrow.set(1);
        rot.style.marginLeft.set(5);

        groupMenu = new UIElement();
        groupMenu.style.width.set(StyleKeyword.Auto);
        groupMenu.style.height.set(35, Unit.Pixel);
        groupMenu.style.flexDirection.set(FlexDirection.Row);
        //menu.style.flexGrow.set(1);
        groupMenu.style.justifyContent.set(Justify.SpaceAround);
        groupMenu.style.borderTopColor.set(ColorRGBA.Clear);
        groupMenu.style.borderLeftColor.set(ColorRGBA.Clear);
        groupMenu.style.borderRightColor.set(ColorRGBA.Clear);
        groupMenu.style.borderBottomColor.set(ColorRGBA.White);

        menuElement = new UIElement();
        menuElement.style.width.set(StyleKeyword.Auto);
        menuElement.style.height.set(StyleKeyword.Auto);
        menuElement.style.flexGrow.set(1);
        menuElement.style.borderTopColor.set(ColorRGBA.Clear);
        menuElement.style.borderLeftColor.set(ColorRGBA.White);
        menuElement.style.borderRightColor.set(ColorRGBA.Clear);
        menuElement.style.borderBottomColor.set(ColorRGBA.Clear);

        window.addChild(grun);
        window.addChild(rot);
        grun.addChild(accounts);
        grun.addChild(listTitel);
        grun.addChild(accountList.getPanel());
        listTitel.addChild(labName);
        listTitel.addChild(labID);
        rot.addChild(groupMenu);
        rot.addChild(menuElement);
        
        plugin.registerEventListener(playerPermissions);

        if (plugin.Config.Debug > 0) {
            menuElement.setBorder(5); //DEBUG
            menuElement.setBorderColor(0, 1, 1, 1); //DEBUG
            groupMenu.setBorder(2); //DEBUG
            groupMenu.setBorderColor(1, 1, 0, 1); //DEBUG
            groupMenu.style.marginBottom.set(5); //DEBUG ? 
            rot.setBorder(1); //DEBUG
            rot.setBorderColor(ColorRGBA.Red.toIntRGBA()); //DEBUG
            rot.setBackgroundColor(ColorRGBA.Red.toIntRGBA()); //DEBUG
            labID.setBorder(3); //DEBUG
            labID.setBorderColor(ColorRGBA.White.toIntRGBA()); //DEBUG
            labName.setBorder(3); //DEBUG
            labName.setBorderColor(ColorRGBA.White.toIntRGBA()); //DEBUG
            listTitel.setBorderColor(ColorRGBA.Black.toIntRGBA()); //DEBUG
            listTitel.setBorder(3); //DEBUG
            accounts.setBorder(3); //DEBUG
            accounts.setBorderColor(ColorRGBA.White.toIntRGBA()); //DEBUG
            grun.setBackgroundColor(ColorRGBA.Green.toIntRGBA()); //DEBUG
        }

        //menu = new UIScrollView(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
        if (!plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
            player.showErrorMessageBox("iConomy", plugin.Language.getGui().getNoAccount(lang));
            window.setVisible(false);
        } else {

//            menu.setVerticalScrollerVisibility(UIScrollView.ScrollerVisibility.AlwaysVisible);
//            menu.setHorizontalScrollerVisibility(UIScrollView.ScrollerVisibility.AlwaysVisible);
//            menu.setSize(100, 100, true);
//            menu.style.justifyContent.set(Justify.FlexStart);
//            menu.style.flexDirection.set(FlexDirection.Column);
//            menu.style.alignItems.set(Align.FlexStart);
//            menu.style.alignSelf.set(Align.FlexStart);
//            menu.style.alignContent.set(Align.FlexStart);
//            menu.setBorder(8);
//            menu.setBorderColor(ColorRGBA.Grey.toIntRGBA());
            //gMenu.addChild(menu);
            createMenuListe(player, plugin.Bankystem.PlayerSystem.getPlayerAccount(player));

            player.addUIElement(window);
            plugin.registerEventListener(window);
            plugin.registerEventListener(money);

            //player.setMouseCursorVisible(true);
        }

    }

    public UIElement getGroupMenu() {
        return groupMenu;
    }
    
    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public BusinessInfo getBusinessInfo() {
        return factoryInfo;
    }

    public BusinessPermissions getBusinessPermissions() {
        return factoryPermissions;
    }

    public MemberUI getMemberUI() {
        return memberUI;
    }

    public Money getMoney() {
        return money;
    }

    public PlayerPermissions getPlayerPermissions() {
        return playerPermissions;
    }

    public Statement getStatement() {
        return statement;
    }

    public AccountList getAccountList() {
        return accountList;
    }

    public Player getUser() {
        return user;
    }

    public HashMap<UIElement, MenuElement> getMenuData() {
        return menuData;
    }

    public UIElement getMenuElement() {
        return menuElement;
    }
    
    private void setSelectedAccountLabel(UIAccountLabel account) {
        accountList.setSelectedAccountLabel(account);
    }

    public void createMenuListe(Player player, BankAccount ba) {
        //Console.sendDebug("MainGUI", "createMenuListe");
        groupMenu.removeAllChilds();
        menuData.clear();
        
        

        UIElement tabAccountInfo = new UIElement();
        tabAccountInfo.style.height.set(100, Unit.Percent);
        tabAccountInfo.style.width.set(150, Unit.Pixel);
        tabAccountInfo.setBorder(2);
        tabAccountInfo.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabAccountInfo.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labAccountInfo = new UILabel("Account Info");
        labAccountInfo.setFontSize(20);
        labAccountInfo.setFont(Font.DefaultBold);
        labAccountInfo.setFontColor(ColorRGBA.White.toIntRGBA());
        labAccountInfo.setPosition(50, 50, true);
        labAccountInfo.setPivot(Pivot.MiddleCenter);
        tabAccountInfo.addChild(labAccountInfo);

        UIElement tabPermission = new UIElement();
        tabPermission.style.height.set(100, Unit.Percent);
        tabPermission.style.width.set(150, Unit.Pixel);
        tabPermission.setBorder(2);
        tabPermission.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabPermission.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labPermission = new UILabel(plugin.Language.getGui().getPermissions(lang));
        labPermission.setFontSize(20);
        labPermission.setFont(Font.DefaultBold);
        labPermission.setFontColor(ColorRGBA.White.toIntRGBA());
        labPermission.setPosition(50, 50, true);
        labPermission.setPivot(Pivot.MiddleCenter);
        tabPermission.addChild(labPermission);

        UIElement tabBusinessAccount = new UIElement();
        tabBusinessAccount.style.height.set(100, Unit.Percent);
        tabBusinessAccount.style.width.set(150, Unit.Pixel);
        tabBusinessAccount.setBorder(2);
        tabBusinessAccount.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabBusinessAccount.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labBusinessAccount = new UILabel("Business"); //TODO Lang - Business
        labBusinessAccount.setFontSize(20);
        labBusinessAccount.setFont(Font.DefaultBold);
        labBusinessAccount.setFontColor(ColorRGBA.White.toIntRGBA());
        labBusinessAccount.setPosition(50, 50, true);
        labBusinessAccount.setPivot(Pivot.MiddleCenter);
        tabBusinessAccount.addChild(labBusinessAccount);

        UIElement tabMoney = new UIElement();
        tabMoney.style.height.set(100, Unit.Percent);
        tabMoney.style.width.set(150, Unit.Pixel);
        tabMoney.setBorder(2);
        tabMoney.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabMoney.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labMoney = new UILabel(plugin.Language.getGui().getMoney(lang));
        labMoney.setFontSize(20);
        labMoney.setFont(Font.DefaultBold);
        labMoney.setFontColor(ColorRGBA.White.toIntRGBA());
        labMoney.setPosition(50, 50, true);
        labMoney.setPivot(Pivot.MiddleCenter);
        tabMoney.addChild(labMoney);

        UIElement tabStatement = new UIElement();
        tabStatement.style.height.set(100, Unit.Percent);
        tabStatement.style.width.set(150, Unit.Pixel);
        tabStatement.setBorder(2);
        tabStatement.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabStatement.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labStatement = new UILabel(plugin.Language.getGui().getStatements(lang));
        labStatement.setFontSize(20);
        labStatement.setFont(Font.DefaultBold);
        labStatement.setFontColor(ColorRGBA.White.toIntRGBA());
        labStatement.setPosition(50, 50, true);
        labStatement.setPivot(Pivot.MiddleCenter);
        tabStatement.addChild(labStatement);

        UIElement tabMember = new UIElement();
        tabMember.style.height.set(100, Unit.Percent);
        tabMember.style.width.set(150, Unit.Pixel);
        tabMember.setBorder(2);
        tabMember.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        tabMember.setBorderColor(ColorRGBA.White.toIntRGBA());

        UILabel labMember = new UILabel(plugin.Language.getGui().getMembers(lang));
        labMember.setFontSize(20);
        labMember.setFont(Font.DefaultBold);
        labMember.setFontColor(ColorRGBA.White.toIntRGBA());
        labMember.setPosition(50, 50, true);
        labMember.setPivot(Pivot.MiddleCenter);
        tabMember.addChild(labMember);

        if (ba instanceof PlayerAccount pa) {
            menuData.put(tabAccountInfo, accountInfo);
        } else if (ba instanceof BusinessAccount fa) {
            menuData.put(tabBusinessAccount, factoryInfo);
        }
        menuData.put(tabMoney, money);
        menuData.put(tabStatement, statement);
        

        if (ba instanceof PlayerAccount pa) {
            boolean result = pa.getOwnerUID().equals(player.getUID()) || (pa.isMember(player) && pa.getMember(player).hasPermission(PlayerAccountPermission.CHANGE_PERMISSIONS));
            if (result) {
                menuData.put(tabMember, memberUI);
                menuData.put(tabPermission, playerPermissions);
            }
        } else if (ba instanceof BusinessAccount fa) {
            boolean result = fa.isOwner(player) || (fa.isMember(player) && fa.getMember(player).hasPermission(BusinessAccountPermission.CHANGE_PERMISSIONS));
            if (result) {
                menuData.put(tabMember, memberUI);
                menuData.put(tabPermission, factoryPermissions);
            }
        }

        AtomicInteger z = new AtomicInteger(0);

        menuData.forEach((icon, ui) -> {
            icon.setClickable(true);
            groupMenu.addChild(icon);

            if (z.get() == 0) {
                icon.setBackgroundColor(1, 1, 0, 1);
                ((UILabel) icon.getChilds().get(0)).setFontColor(ColorRGBA.Black.toIntRGBA());
                ui.setBankAccount(ba);
                setSelectedMenuElement(icon);
                z.set(1);
                menuElement.removeAllChilds();
                menuElement.addChild(ui);
            }
        });
        //Console.sendDebug("MainGUI", "createMenuListe ... Done!");
    }

    public UIFrame getWindow() {
        return window;
    }

    private void setSelectedMenuElement(UIElement selectedMenuElement) {
        this.selectedMenuElement = selectedMenuElement;
    }

    public UIElement getSelectedMenuElement() {
        return selectedMenuElement;
    }

    @EventMethod
    public void onPlayerSelectMenuEvent(PlayerUIElementClickEvent event) {
        Player p = event.getPlayer();
        UIElement e = event.getUIElement();

        if (user.getUID().equals(p.getUID())) {
            if (menuData.containsKey(e)) {
                if (getSelectedMenuElement() != e) {
                    menuElement.removeAllChilds();
                    MenuElement gui = menuData.get(e);
                    gui.setBankAccount(accountList.getSelectedAccountLabel().getBankAccount());
                    menuElement.addChild(gui);

                    e.setBackgroundColor(1, 1, 0, 1);
                    ((UILabel) e.getChilds().get(0)).setFontColor(ColorRGBA.Black.toIntRGBA());
                    selectedMenuElement.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
                    ((UILabel) selectedMenuElement.getChilds().get(0)).setFontColor(ColorRGBA.White.toIntRGBA());

                    setSelectedMenuElement(e);
                }
            }
        }

    }
}
