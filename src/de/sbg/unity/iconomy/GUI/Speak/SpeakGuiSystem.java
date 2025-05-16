package de.sbg.unity.iconomy.GUI.Speak;

import de.sbg.unity.iconomy.Npc.SpeakSystem;
import de.sbg.unity.iconomy.Npc.SpeakSystem.SpeakObject;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;

public class SpeakGuiSystem {

    private final String guiAtt;
    private final iConomy plugin;

    public SpeakGuiSystem(iConomy plugin) {
        this.plugin = plugin;
        this.guiAtt = "sbg-iConomy-SpeakGuiAtt";
    }

    public void show(Player player, Npc npc) {
        show(player, npc, plugin.Bankystem.npcSystem.getSpeakSystem(player).id_00());
    }

    public SpeakMainGui show(Player player, Npc npc, SpeakObject so) {
        if (!player.hasAttribute(guiAtt)) {
            SpeakMainGui gui = new SpeakMainGui(player, plugin);
            player.setAttribute(guiAtt, gui);
            SpeakSystem ss = plugin.Bankystem.npcSystem.getSpeakSystem(player);
            ss.setNpc(npc);
            setNewText(player, so);
            
            player.setMouseCursorVisible(true);
            player.addUIElement(gui.getPanel());
            plugin.registerEventListener(gui);
            return gui;
        }
        return (SpeakMainGui) player.getAttribute(guiAtt);
    }

    public SpeakMainGui getGui(Player player) {
        if (player.hasAttribute(guiAtt)) {
            return (SpeakMainGui) player.getAttribute(guiAtt);
        }
        return null;
    }

    public void hide(Player player) {
        hide(player, true);
    }

    public void hide(Player player, boolean hidemouse) {
        if (player.hasAttribute(guiAtt)) {
            SpeakMainGui gui = (SpeakMainGui)player.getAttribute(guiAtt);
            player.removeUIElement(gui.getPanel());
            player.deleteAttribute(guiAtt);
            plugin.unregisterEventListener(gui);
            if (hidemouse) {
                player.setMouseCursorVisible(false);
            }
            plugin.GUI.MoneyInfoMiniGui.hideGui(player);
        }
    }

    public void setNewText(Player player, SpeakObject so) {
        if (player.hasAttribute(guiAtt)) {
            SpeakMainGui gui = (SpeakMainGui) player.getAttribute(guiAtt);
            gui.clearAll();
            System.out.println("next = " + so);
            gui.answers.setNewText(so.getAnswers());
            gui.speakText.setNewText(so.getSpeakText());
            if (so.isShowMoneyGui()) {
                plugin.GUI.MoneyInfoMiniGui.showGUI(player);
            } else {
                plugin.GUI.MoneyInfoMiniGui.hideGui(player);
            }
        }
    }
}
