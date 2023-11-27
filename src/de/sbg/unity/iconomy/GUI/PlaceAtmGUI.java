package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;

public class PlaceAtmGUI {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    private final UIElement panel;
    private final String lang;
    private float amount;
    private Modus modus;

    private final UILabel lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, lab9, lab10;

    public PlaceAtmGUI(Player player, iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.format = new TextFormat();
        this.panel = new UIElement();
        this.lang = player.getLanguage();
        this.amount = 1.0f;

        panel.setPosition(5, 1, false);
        panel.setPivot(Pivot.LowerLeft);
        panel.setSize(100, 380, false);
        panel.setBackgroundColor(0, 0, 102, 50);

        lab1 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_UpDown_Pos(lang));
        lab2 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_LeftRight_Pos(lang));
        lab3 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_PageUpDown(lang));
        lab4 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Plus_Pos(lang));
        lab5 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Minus_Pos(lang));
        lab6 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Enter(lang));
        lab7 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Multiply(lang));
        lab8 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Escape(lang));
        lab9 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Mode(lang) + " Position");
        lab10 = new UILabel(plugin.Language.getGui().getPlaceAtmGui_Amount(lang) + " " + amount);
        
        lab1.setSize(100, 40, false);
        lab1.setFontSize(18f);
        lab1.setPivot(Pivot.UpperLeft);
        lab1.setPosition(0, 2, false);
        lab1.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab1);
        
        lab2.setSize(100, 40, false);
        lab2.setFontSize(18f);
        lab2.setPivot(Pivot.UpperLeft);
        lab2.setPosition(40, 2, false);
        lab2.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab2);
        
        lab3.setSize(100, 40, false);
        lab3.setFontSize(18f);
        lab3.setPivot(Pivot.UpperLeft);
        lab3.setPosition(80, 2, false);
        lab3.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab3);
        
        lab4.setSize(100, 40, false);
        lab4.setFontSize(18f);
        lab4.setPivot(Pivot.UpperLeft);
        lab4.setPosition(120, 2, false);
        lab4.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab4);
        
        lab5.setSize(100, 40, false);
        lab5.setFontSize(18f);
        lab5.setPivot(Pivot.UpperLeft);
        lab5.setPosition(160, 2, false);
        lab5.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab5);
        
        lab6.setSize(100, 40, false);
        lab6.setFontSize(18f);
        lab6.setPivot(Pivot.UpperLeft);
        lab6.setPosition(200, 2, false);
        lab6.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab6);
        
        lab7.setSize(100, 40, false);
        lab7.setFontSize(18f);
        lab7.setPivot(Pivot.UpperLeft);
        lab7.setPosition(240, 2, false);
        lab7.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab7);
        
        lab8.setSize(100, 40, false);
        lab8.setFontSize(18f);
        lab8.setPivot(Pivot.UpperLeft);
        lab8.setPosition(280, 2, false);
        lab8.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab8);
        
        lab9.setSize(100, 40, false);
        lab9.setFontSize(18f);
        lab9.setPivot(Pivot.UpperLeft);
        lab9.setPosition(320, 2, false);
        lab9.setBackgroundColor(0, 0, 0, 100);
        panel.addChild(lab9);
        
        modus = Modus.Position;
        
        player.addUIElement(panel);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
        if (modus == Modus.Position) {
            lab10.setText("Menge: " + amount);
        } else {
            lab9.setText("Menge: " + amount);
        }
    }    

    public void setModus(Modus m) {
        
        if (m == Modus.Rotation) {
            lab1.setText(plugin.Language.getGui().getPlaceAtmGui_UpDown_Rot(lang));
            lab2.setText(plugin.Language.getGui().getPlaceAtmGui_LeftRight_Rot(lang));
            lab3.setText(plugin.Language.getGui().getPlaceAtmGui_Plus_Rot(lang));
            lab4.setText(plugin.Language.getGui().getPlaceAtmGui_Minus_Rot(lang));
            lab5.setText(plugin.Language.getGui().getPlaceAtmGui_Enter(lang));
            lab6.setText(plugin.Language.getGui().getPlaceAtmGui_Multiply(lang));
            lab7.setText(plugin.Language.getGui().getPlaceAtmGui_Escape(lang));
            lab8.setText(plugin.Language.getGui().getPlaceAtmGui_Mode(lang) + " Rotation");
            lab9.setText(plugin.Language.getGui().getPlaceAtmGui_Amount(lang) + " " + amount);
            lab10.setText("");
        } else {
            lab1.setText(plugin.Language.getGui().getPlaceAtmGui_UpDown_Pos(lang));
            lab2.setText(plugin.Language.getGui().getPlaceAtmGui_LeftRight_Pos(lang));
            lab3.setText(plugin.Language.getGui().getPlaceAtmGui_PageUpDown(lang));
            lab4.setText(plugin.Language.getGui().getPlaceAtmGui_Plus_Pos(lang));
            lab5.setText(plugin.Language.getGui().getPlaceAtmGui_Minus_Pos(lang));
            lab6.setText(plugin.Language.getGui().getPlaceAtmGui_Enter(lang));
            lab7.setText(plugin.Language.getGui().getPlaceAtmGui_Multiply(lang));
            lab8.setText(plugin.Language.getGui().getPlaceAtmGui_Escape(lang));
            lab9.setText(plugin.Language.getGui().getPlaceAtmGui_Mode(lang) + " Rotation");
            lab10.setText(plugin.Language.getGui().getPlaceAtmGui_Amount(lang) + " " + amount);
        }
        modus = m;
    }

    public UIElement getPanel() {
        return panel;
    }

    public enum Modus {
        Position,
        Rotation;
    }

}
