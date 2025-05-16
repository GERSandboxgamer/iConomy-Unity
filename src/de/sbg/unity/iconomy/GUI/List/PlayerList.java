package de.sbg.unity.iconomy.GUI.List;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Objects.OfflinePlayer;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.risingworld.api.ui.style.StyleKeyword;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class PlayerList implements Listener {

    private final Player player;
    private final TextFormat format;
    private final String lang;
    private final UIElement panel, lockWindow;
    private final UIPlayerList playerListElements;
    private final UILabel buttonCancel;
    private final iConomy plugin;
    private UIPlayerLabel selectedPlayer;
    private final List<String> uidList;
    private final icConsole Console;
    private final boolean titel;

    public interface SelectCallback {

        public void onSelection(Player selector, UIPlayerLabel select, UIPlayerLabel lastSelected);
    }

    public PlayerList(Player player, iConomy plugin, SelectCallback cb) {
        this.plugin = plugin;
        this.player = player;
        this.format = new TextFormat();
        this.lang = player.getLanguage();
        this.selectedPlayer = null;
        this.uidList = new ArrayList<>();
        this.Console = new icConsole(plugin);
        this.titel = false;
        this.lockWindow = new UIElement();

        Console.sendDebug("PlayerList", "new PlayerList");
        Console.sendDebug("PlayerList", "this.toString() = " + this.toString());

        panel = new UIElement();
        panel.style.flexDirection.set(FlexDirection.Column);
        playerListElements = new UIPlayerList(player, plugin, cb);
        if (plugin.Config.Debug > 0) {
            playerListElements.setBackgroundColor(ColorRGBA.White.toIntRGBA());
        }
        buttonCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        Body(false, null);

    }

    public PlayerList(Player player, boolean titel, String titeltext, iConomy plugin, SelectCallback cb) {
        this.plugin = plugin;
        this.player = player;
        this.format = new TextFormat();
        this.lang = player.getLanguage();
        this.selectedPlayer = null;
        this.uidList = new ArrayList<>();
        this.Console = new icConsole(plugin);
        this.titel = titel;
        this.lockWindow = new UIElement();

        Console.sendDebug("PlayerList", "new PlayerList");
        Console.sendDebug("PlayerList", "this.toString() = " + this.toString());

        panel = new UIElement();
        panel.style.flexDirection.set(FlexDirection.Column);
        playerListElements = new UIPlayerList(player, plugin, cb);
        if (plugin.Config.Debug > 0) {
            playerListElements.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
        }
        buttonCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        Body(titel, titeltext);

    }

    private void TitelBar(String titeltext) {
        UIElement titelBar = new UIElement();
        titelBar.setPosition(50, 0, true);
        titelBar.setPivot(Pivot.LowerCenter);
        titelBar.setSize(500, 50, false);
        titelBar.setBackgroundColor(255, 153, 51, 1);
        if (plugin.Config.Debug > 0) {
            titelBar.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
        }

        UILabel titelText = new UILabel(titeltext);
        titelText.setPosition(50, 50, true);
        titelText.setPivot(Pivot.MiddleCenter);
        titelText.setFontSize(25);
        titelText.setFontColor(255, 255, 255, 1);
        titelText.setBackgroundColor(255, 153, 51, 1);
        titelBar.addChild(titelText);
        panel.addChild(titelBar);
    }

    private void Body(boolean titel, String titeltext) {
//        panel.setPosition(50, 50, true);

        panel.setBackgroundColor(0, 0, 102, 1);

        if (titel) {
            lockWindow.setSize(100, 100, titel);
            lockWindow.setBackgroundColor(ColorRGBA.Black.r, ColorRGBA.Black.g, ColorRGBA.Black.b, 0.5f);

            TitelBar(titeltext);
            panel.setSize(500, 600, false);
            panel.setPosition(50, 50, true);
            panel.setPivot(Pivot.MiddleCenter);
            lockWindow.addChild(panel);
        }
//        } else {
//            panel.setPivot(Pivot.UpperLeft);
//            panel.setSize(100, 100, true);
//        }

        plugin.registerEventListener(this);
        plugin.registerEventListener(playerListElements);

        // Tabelle Überschrift
        UIElement rot = new UIElement();
        rot.style.flexDirection.set(FlexDirection.Row);
        rot.style.justifyContent.set(Justify.SpaceBetween);
        rot.style.height.set(StyleKeyword.Auto);
        if (plugin.Config.Debug > 0) {
            rot.setBackgroundColor(ColorRGBA.Red.toIntRGBA());
        }
        panel.addChild(rot);

        UILabel labBenutzername = new UILabel(format.Bold(plugin.Language.getGui().getUsername(lang)));
        labBenutzername.style.marginLeft.set(30, Unit.Pixel);
        labBenutzername.setPivot(Pivot.UpperLeft);
        labBenutzername.setFontSize(20);
        if (plugin.Config.Debug > 0) {
            labBenutzername.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
        }
        rot.addChild(labBenutzername);

        UILabel labUID = new UILabel(format.Bold("UID"));
        labUID.setPivot(Pivot.UpperLeft);
        labUID.style.marginRight.set(60, Unit.Pixel);
        labUID.setFontSize(20);
        if (plugin.Config.Debug > 0) {
            labUID.setBackgroundColor(ColorRGBA.Green.toIntRGBA());
        }
        rot.addChild(labUID);

        // Tabelle Überschrift
        panel.addChild(playerListElements);

        buttonCancel.setFontSize(25);
        buttonCancel.setClickable(true);
        panel.addChild(buttonCancel);

        if (titel) {
            player.addUIElement(lockWindow);
        } else {
            player.addUIElement(panel);
        }
    }

    public void removeButtonCancel() {
        panel.removeChild(buttonCancel);
    }

    public UIElement getPanel() {
        if (titel) {
            return lockWindow;
        }
        return panel;
    }

    public UIPlayerLabel getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(UIPlayerLabel selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public List<String> getUidList() {
        return uidList;
    }

    public UIPlayerList getPlayerListElements() {
        return playerListElements;
    }

    public UILabel getButtonCancel() {
        return buttonCancel;
    }

    @EventMethod
    public void onPlayerClickCancelEvent(PlayerUIElementClickEvent event) {
        Console.sendDebug("PlayerList", "onPlayerClickCancelEvent");
        Player p = event.getPlayer();
        if (player.equals(p)) {
            if (event.getUIElement() == buttonCancel) {
                plugin.GUI.SelectOnlinePlayerGui.hideGui(player);
            }
        }
    }

    public class UIPlayerList extends UIScrollView implements Listener {

        private final Player selector;
        private final SelectCallback cb;
        private final List<UIPlayerLabel> playerLabels;

        public UIPlayerList(Player player, iConomy plugin) {
            super(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
            Console.sendDebug("PlayerList", "new UIPlayerList (ID-UIPlayerList: " + this.getID() + ")");
            this.selector = player;
            this.cb = null;
            this.playerLabels = new ArrayList<>();
            this.setSize(100, 100, true);
            setListStyle();
        }

        public UIPlayerList(Player player, iConomy plugin, SelectCallback cb) {
            super(UIScrollView.ScrollViewMode.VerticalAndHorizontal);
            Console.sendDebug("PlayerList", "new UIPlayerList (ID-UIPlayerList: " + this.getID() + ")");
            this.selector = player;
            this.cb = cb;
            this.playerLabels = new ArrayList<>();
            this.setSize(100, 100, true);
            setListStyle();
        }

        public void addElements() {
            addElements(new ArrayList<>(), true);
        }

        public void addElements(List<String> uids) {
            addElements(uids, false);
        }

        public void addElements(List<String> offlinePlayersUID, boolean server) {
            Console.sendDebug("PlayerList", "Add new Element!");
            if (server && Server.getAllPlayers().length >= 2) {
                UIElement titelBox1 = new UIElement();
                titelBox1.style.width.set(95, Unit.Percent);
                titelBox1.style.height.set(50, Unit.Pixel);
                titelBox1.style.marginLeft.set(2, Unit.Percent);
                titelBox1.style.marginRight.set(2, Unit.Percent);
                titelBox1.style.marginTop.set(2, Unit.Percent);

                UILabel onlineTitel = new UILabel("== Online ==");
                onlineTitel.setFont(Font.DefaultBold);
                onlineTitel.setFontSize(20);
                onlineTitel.setFontColor(ColorRGBA.White.toIntRGBA());
                onlineTitel.setPivot(Pivot.MiddleCenter);
                onlineTitel.setPosition(50, 50, true);
                titelBox1.addChild(onlineTitel);
                this.addChild(titelBox1);

                if (plugin.Config.Debug > 0) {
                    onlineTitel.setFontColor(ColorRGBA.Black.toIntRGBA());
                }

                for (Player p : Server.getAllPlayers()) {
                    if (!p.getUID().equals(player.getUID())) {
                        uidList.add(p.getUID());
                        UIPlayerLabel pLabel = new UIPlayerLabel(p, player);
                        Console.sendDebug("addElements", "Add Element: " + pLabel.getID());
                        this.playerLabels.add(pLabel);
                        this.addChild(pLabel);
                    }
                }
            }
            if (!offlinePlayersUID.isEmpty()) {
                UIElement titelBox2 = new UIElement();
                titelBox2.style.width.set(95, Unit.Percent);
                titelBox2.style.height.set(50, Unit.Pixel);
                titelBox2.style.marginLeft.set(2, Unit.Percent);
                titelBox2.style.marginRight.set(2, Unit.Percent);
                titelBox2.style.marginTop.set(2, Unit.Percent);

                UILabel offlineTitel = new UILabel("== Offline ==");
                offlineTitel.setFont(Font.DefaultBold);
                offlineTitel.setFontSize(20);
                offlineTitel.setFontColor(ColorRGBA.White.toIntRGBA());
                offlineTitel.setPivot(Pivot.MiddleCenter);
                offlineTitel.setPosition(50, 50, true);
                titelBox2.addChild(offlineTitel);

                if (plugin.Config.Debug > 0) {
                    offlineTitel.setFontColor(ColorRGBA.Black.toIntRGBA());
                }
            }
            if (plugin.Config.Debug > 0) {

                int f = 1;
                while (f <= 15) {
                    UIPlayerLabel pLabel = new UIPlayerLabel("Debug " + f, String.valueOf(f), selector);
                    this.addChild(pLabel);
                    f = f + 1;
                }
            }
            Console.sendDebug("PlayerList", "Add new Element! ... Done!");
        }

        public void addBankMembers(BankAccount ba) {
            Console.sendDebug("PlayerList-addBankMembers", "addBankMembers");
            List<OfflinePlayer> list = new ArrayList<>();

            if (ba instanceof PlayerAccount pa) {
                Console.sendDebug("PlayerList-addBankMembers", "is PlayerAccount");
                pa.getMembers().forEach((t) -> {
                    list.add(new OfflinePlayer(t.getUID(), t.getLastKnownMemberName()));
                    Console.sendDebug("PlayerList-addBankMembers", "add " + t.getLastKnownMemberName() + "(" + t.getUID() + ")");
                });
            }
            if (ba instanceof BusinessAccount fa) {
                fa.getMembers().forEach((t) -> {
                    list.add(new OfflinePlayer(t.getUID(), t.getLastKnownMemberName()));
                });
            }
            if (!list.isEmpty()) {
                Console.sendDebug("PlayerList-addBankMembers", "List is NOT Empty!");
                UIElement titelBox2 = new UIElement();
                titelBox2.style.width.set(95, Unit.Percent);
                titelBox2.style.height.set(50, Unit.Pixel);
                titelBox2.style.marginLeft.set(2, Unit.Percent);
                titelBox2.style.marginRight.set(2, Unit.Percent);
                titelBox2.style.marginTop.set(2, Unit.Percent);

                UILabel offlineTitel = new UILabel("== " + plugin.Language.getGui().getMembers(lang) + " ==");
                offlineTitel.setFont(Font.DefaultBold);
                offlineTitel.setFontSize(20);
                offlineTitel.setFontColor(ColorRGBA.White.toIntRGBA());
                offlineTitel.setPivot(Pivot.MiddleCenter);
                offlineTitel.setPosition(50, 50, true);
                titelBox2.addChild(offlineTitel);
                this.addChild(titelBox2);

                for (OfflinePlayer op : list) {
                    if (!op.getUID().equals(player.getUID())) {
                        Console.sendDebug("PlayerList-addBankMembers", "add OflinePlayer-Object " + op.getName());
                        uidList.add(op.getUID());
                        UIPlayerLabel pLabel = new UIPlayerLabel(op.getName(), op.getUID(), player);
                        Console.sendDebug("addBankMembers", "Add Element: " + pLabel.getID());
                        this.playerLabels.add(pLabel);
                        this.addChild(pLabel);

                    }
                }
            } else {
                Console.sendDebug("PlayerList-addBankMembers", "List is Empty!");
            }

        }

        public void addOfflineMembers(BankAccount ba) {
            Console.sendDebug("PlayerList", "Add Offline Members!");
            List<OfflinePlayer> list = new ArrayList<>();
            List<String> server = new ArrayList<>();

            for (Player p : Server.getAllPlayers()) {
                server.add(p.getUID());
            }
            if (ba instanceof PlayerAccount pa) {
                pa.getMembers().forEach((t) -> {
                    if (!server.contains(t.getUID())) {
                        list.add(new OfflinePlayer(t.getUID(), t.getLastKnownMemberName()));
                    }
                });
            }
            if (ba instanceof BusinessAccount fa) {
                fa.getMembers().forEach((t) -> {
                    if (!server.contains(t.getUID())) {
                        list.add(new OfflinePlayer(t.getUID(), t.getLastKnownMemberName()));
                    }
                });
            }
            if (!list.isEmpty()) {
                UIElement titelBox2 = new UIElement();
                titelBox2.style.width.set(95, Unit.Percent);
                titelBox2.style.height.set(50, Unit.Pixel);
                titelBox2.style.marginLeft.set(2, Unit.Percent);
                titelBox2.style.marginRight.set(2, Unit.Percent);
                titelBox2.style.marginTop.set(2, Unit.Percent);

                UILabel offlineTitel = new UILabel("== Offline " + plugin.Language.getGui().getMembers(lang) + " ==");
                offlineTitel.setFont(Font.DefaultBold);
                offlineTitel.setFontSize(20);
                offlineTitel.setFontColor(ColorRGBA.White.toIntRGBA());
                offlineTitel.setPivot(Pivot.MiddleCenter);
                offlineTitel.setPosition(50, 50, true);
                titelBox2.addChild(offlineTitel);
                this.addChild(titelBox2);

                for (OfflinePlayer op : list) {
                    if (!op.getUID().equals(player.getUID())) {
                        uidList.add(op.getUID());
                        UIPlayerLabel pLabel = new UIPlayerLabel(op.getName(), op.getUID(), player);
                        Console.sendDebug("addOfflineMembers", "Add Element: " + pLabel.getID());
                        this.playerLabels.add(pLabel);
                        this.addChild(pLabel);

                    }
                }
            }
            Console.sendDebug("PlayerList", "Add Offline Members! ... Done!");
        }

        public void clearElements() {
            Console.sendDebug("PlayerList", "Clear PlayerList-Elements");
            this.playerLabels.clear();
            uidList.clear();
            this.removeAllChilds();
        }

        private void setListStyle() {
            this.setHorizontalScrollerVisibility(UIScrollView.ScrollerVisibility.Hidden);
            this.setVerticalScrollerVisibility(UIScrollView.ScrollerVisibility.Auto);
        }

        public List<UIPlayerLabel> getPlayerLabels() {
            return playerLabels;
        }

        private UIElement child;

        @EventMethod
        public void onPlayerSelectPlayerEvent(PlayerUIElementClickEvent event) {
            child = null;
            if (event.getUIElement() != null) {
                this.getChilds().forEach((t) -> {
                    boolean b = event.getUIElement().equals(t);
                    if (b) {
                        child = t;
                    }
                });
                if (plugin.Config.Debug > 0) {
                    if (event.getUIElement() != null) {
                        Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "EL ID AUSSEN = " + event.getUIElement().getID());
                    } else {
                        Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "UIElement is null");
                    }
                    
                    Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "Klick");

                }
                Player p = event.getPlayer();
                if (child != null) {
                    Console.sendDebug("PlayerList", "Child != null");
                    if (selector.equals(p)) {
                        if (child instanceof UIPlayerLabel theSelectedPlayer) {
                            Console.sendDebug("PlayerList", "is UIPlayerLabel (ID: " + theSelectedPlayer.getID() + ")");
                            if (selectedPlayer != null) {
                                cb.onSelection(selector, theSelectedPlayer, selectedPlayer);
                            } else {
                                cb.onSelection(selector, theSelectedPlayer, null);
                            }
                            setSelectedPlayer(theSelectedPlayer);
                        }
                    }
                } else {
                    Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "Child = null");
                }

            } else {
                Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "UIElement is null");
            }
            Console.sendDebug("PlayerList-onPlayerSelectPlayerEvent", "Event onPlayerSelectPlayerEvent ... Done!");
        }
    }

    public class UIPlayerLabel extends UIElement {

        private final UILabel playernameLabel;
        private final UILabel uidLabel;
        private final HashMap<String, Object> attribute;
        private final Player selector;
        private final OfflinePlayer playerObject;

        public UIPlayerLabel(Player player, Player selector) {
            this.selector = selector;
            this.playerObject = new OfflinePlayer(player.getUID(), player.getName());
            this.playernameLabel = new UILabel(player.getName());
            this.uidLabel = new UILabel(player.getUID());
            this.attribute = new HashMap<>();
            Console.sendDebug("PlayerList", "new UIPlayerLabel (ID: " + this.getID() + ")");
            setElement();
        }

        public UIPlayerLabel(String playername, String uid, Player selector) {
            this.selector = selector;
            this.playerObject = new OfflinePlayer(uid, playername);
            this.playernameLabel = new UILabel(playername);
            this.uidLabel = new UILabel(uid);
            this.attribute = new HashMap<>();
            Console.sendDebug("PlayerList", "new UIPlayerLabel (ID: " + this.getID() + ")");
            setElement();
        }

        private void setElement() {
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

            this.playernameLabel.setPivot(Pivot.MiddleLeft);
            this.playernameLabel.setFontSize(20);
            this.playernameLabel.setFont(Font.DefaultBold);
            this.playernameLabel.setPosition(2, 50, true);

            this.uidLabel.setPivot(Pivot.MiddleRight);
            this.uidLabel.setFontSize(20);
            this.playernameLabel.setFont(Font.DefaultBold);
            this.uidLabel.setPosition(98, 50, true);

            this.addChild(this.playernameLabel);
            this.addChild(this.uidLabel);
        }

        public Player getSelector() {
            return selector;
        }

        public OfflinePlayer getPlayerObject() {
            return playerObject;
        }

        public UILabel getPlayernameLabel() {
            return playernameLabel;
        }

        public UILabel getUidLabel() {
            return uidLabel;
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
