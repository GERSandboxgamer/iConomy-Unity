package de.sbg.unity.iconomy.GUI.Speak;

import de.sbg.unity.iconomy.Npc.SpeakSystem.SpeakObject;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class SpeakMainGui implements Listener {

    private final UIElement panel;
    private final Player player;
    private final iConomy plugin;
    public final UIAnswerList answers;
    public final UISpeakText speakText;

    public SpeakMainGui(Player player, iConomy plugin) {
        this.answers = new UIAnswerList();
        this.speakText = new UISpeakText();
        this.player = player;
        this.plugin = plugin;

        this.panel = new UIElement();
        panel.setSize(90, 90, true);
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.style.flexDirection.set(FlexDirection.ColumnReverse);
        
        speakText.style.height.set(100, Unit.Pixel);
        
        panel.addChild(speakText);
        
        UIElement panelOben = new UIElement();
        panelOben.setSize(100, 15, true);
        panelOben.style.flexDirection.set(FlexDirection.Row);
        panel.addChild(panelOben);
        
        UIElement panelObenLinks = new UIElement();
        panelObenLinks.setSize(60, 100, true);
        panelOben.addChild(panelObenLinks);
        
        UIElement panelObenRechts = new UIElement();
        panelObenRechts.setSize(50, 100, true);
        panelObenRechts.setBackgroundColor(ColorRGBA.Blue.r, ColorRGBA.Blue.g, ColorRGBA.Blue.b,0.75f);
        panelObenRechts.setBorder(2);
        panelObenRechts.setBorderColor(ColorRGBA.White.toIntRGBA());
        panelOben.addChild(panelObenRechts);
        
        
        answers.setSize(100, 100, true);
        panelObenRechts.addChild(answers);
    }

    public UIElement getPanel() {
        return panel;
    }

    
    public void clearAll() {
        answers.removeAllChilds();
        speakText.removeAllChilds();
    }

    @EventMethod
    public void onPlayerClickAnswerEvent(PlayerUIElementClickEvent event) {
        UIElement el = event.getUIElement();
        Player p = event.getPlayer();

        if (p == player) {
            if (el instanceof AnswerObject ao) {
                switch(ao.getAnswer().getAction()) {
                    case Code -> ao.getAnswer().getCode().onRunCode();
                    case GoTo -> {
                        clearAll();
                        SpeakObject so = plugin.Bankystem.npcSystem.getSpeakSystem(player).getSpeak(ao.getAnswer().getNextID());
                        speakText.setNewText(so.getSpeakText());
                        answers.setNewText(so.getAnswers());
                    }
                    case Stop -> {
                        plugin.GUI.speakGuiSystem.hide(player);
                    }
                }
            }
        }
    }

}
