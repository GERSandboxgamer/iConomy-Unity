package de.sbg.unity.iconomy.Npc;

import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.FactoryAlreadyExistsExeption;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;

public class SpeakSystem {

    private final iConomy plugin;
    private final HashMap<Integer, SpeakObject> speakList;
    private final Player player;
    private Factory savedFactory;
    private final icConsole Console;
    private boolean nameOK;
    private String savedID;

    public SpeakSystem(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.speakList = new HashMap<>();
        this.player = player;
        this.Console = new icConsole(plugin);
    }

    public Factory getSavedFactory() {
        return savedFactory;
    }

    public void setSavedFactory(Factory savedFactory) {
        this.savedFactory = savedFactory;
    }

    public interface SpeakActionCode {

        public void onRunCode();
    }

    public void init(Npc npc) {
        speakList.clear();
        try {
        String[] sp0Text = {plugin.Translator.translate("Herzlich willkommen in unserer Filiale.", "de", player.getSystemLanguage()), plugin.Translator.translate("Mein Name ist " + npc.getName() + ".", "de", player.getSystemLanguage())};
        SpeakObject sp0 = new SpeakObject(sp0Text);
        sp0.addAnswer("[Weiter]", 1);
        speakList.put(0, sp0);

        String[] sp1Text = {plugin.Translator.translate("Wie kann ich Ihnen helfen?", "de", player.getSystemLanguage())};
        SpeakObject sp1 = new SpeakObject(sp1Text);
        if (!plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
            sp1.addAnswer(plugin.Translator.translate("Hallo, ich möchte ein Bankkonto erstellen.", "de", player.getSystemLanguage()), 2);
        } else {
            sp1.addAnswer(plugin.Translator.translate("Hallo, ich möchte ein Bankkonto erstellen.", "de", player.getSystemLanguage()), 3);
        }
        //sp1.addAnswer("Hallo, ich möchte meine Schuldengrenze erhöhen.", 4);
        if (plugin.Config.FactroyCreateGroups.contains(player.getPermissionGroup())) {
            sp1.addAnswer(plugin.Translator.translate("Hallo, ich möchte eine Firma gründen.", "de", player.getSystemLanguage()), 7);
        }
        sp1.addAnswer("Auf Wiedersehen");
        speakList.put(1, sp1);

        String[] sp2Text = {"Sie wollen also ein Bankkonto erstellen.", "Das macht dann " + plugin.moneyFormat.getMoneyAsString(plugin.Config.PlayerBankAccountCost, player) + "."};
        SpeakObject sp2 = new SpeakObject(sp2Text);
        sp2.addAnswer("[Bezahlen]", () -> {

        });
        sp2.addAnswer("Ich habe es mir anders überlegt. Auf Wiedersehen.");
        speakList.put(2, sp2);

        String[] sp3Text = {"Wie ich sehe, haben Sie bereits ein Bankkonto.", "Wollen Sie noch ein Konto eröffnen?"};
        SpeakObject sp3 = new SpeakObject(sp3Text);
        sp3.addAnswer("Ja, ein Firmenkonto.", 4);
        sp3.addAnswer("Nein. Ich hätte da noch eine andere Bitte.", 1);
        sp3.addAnswer("Nein, nicht nötig. Auf Wiedersehen.");
        speakList.put(3, sp3);

        String[] sp4Text = {"Sie wollen also ihr Schuldengrenze erhöhen.", "Kein Problem. Aber übertreiben Sie es nicht."};
        SpeakObject sp4 = new SpeakObject(sp4Text);
        sp4.addAnswer("Ich doch nicht.", () -> {
            //TODO EDIT
            long min = plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMin();
            plugin.Bankystem.PlayerSystem.getPlayerAccount(player).setMin(min + 5);
        });
        speakList.put(4, sp4);

        String[] sp5Text = {"Ich habe Ihre Schuldengrenze auf " + plugin.moneyFormat.getMoneyAsFormatedString(player, plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMin()) + " erhöht."};
        SpeakObject sp5 = new SpeakObject(sp5Text);
        sp5.addAnswer("Vielen Dank. Ich hätte da noch eine Bitte.", 1);
        sp5.addAnswer("Vielen Dank. Auf Wiedersehen.");
        speakList.put(5, sp5);

        String[] sp6Text = {"Sie wollen ein Firmenkonto anlagen? Dafür brauch ich die Firmen-Bank-ID.", "Diese finden Sie im Firmen-GUI im Bereich 'Info'."};
        SpeakObject sp6 = new SpeakObject(sp6Text);
        sp6.addAnswer("[ID mitteilen]", () -> {
            plugin.GUI.speakGuiSystem.hide(player, false);
            player.showInputMessageBox("Firmenkonto anlegen", "Geben Sie die Firmen-Bank-ID ein:", null, (t) -> {
                Factory f = plugin.Factory.getFactoryByBankID(t);
                if (f != null) {
                    savedFactory = f;
                    savedID = t;
                    plugin.GUI.speakGuiSystem.show(player, npc, 17);
                } else {
                    plugin.GUI.speakGuiSystem.show(player, npc, 15);
                }
            });
        });
        sp6.addAnswer("Vielen Dank für die Info. Ich komme nochmal auf Sie zu.");
        speakList.put(6, sp6);

        String[] sp7Text = {"Sie wollen eine Firma gründen? Dass kostet Sie " + plugin.moneyFormat.getMoneyAsFormatedString(player, plugin.Config.FactoryCost) + " (Cash)."}; //TODO Preis
        SpeakObject sp7 = new SpeakObject(sp7Text);
        sp7.addAnswer("[Bezahlen]", () -> {
            if (plugin.CashSystem.removeCash(player, plugin.Config.FactoryCost, RemoveCashEvent.Reason.Buy) == TransferResult.Successful) {
                plugin.GUI.speakGuiSystem.setNewText(player, 11);
            }

        });
        sp7.addAnswer("Nein, danke. Ich hätte da noch eine andere Bitte.", 1);
        sp7.addAnswer("Ich habe es mir anders überlegt. Auf Wiedersehen.");
        speakList.put(7, sp7);

        String[] sp8Text = {"Herzlichen Glückwunsch. Sie sind nun Eigentümer der Firma " + savedFactory.getName() + ".", "Kann ich Ihnen sonst noch helfen?"};
        SpeakObject sp8 = new SpeakObject(sp8Text);
        sp8.addAnswer("Ja.", 1);
        sp8.addAnswer("Nein. Auf Wiedersehen.");
        speakList.put(8, sp8);

        String[] sp10Text = {"Einen Moment bitte … Sie besitzen leiter keine Firma.", "Sie müssen erst eine Gründen."};
        SpeakObject sp10 = new SpeakObject(sp10Text);
        sp10.addAnswer("Dann möchte ich eine Firma gründen.", 7);
        sp10.addAnswer("OK. Anderes Thema.", 1);
        sp10.addAnswer("OK. Auf Wiedersehen.");
        speakList.put(10, sp10);

        String[] sp11Text = {"Vielen Dank. Wie wollen Sie Ihre neue Firma nennen?"};
        SpeakObject sp11 = new SpeakObject(sp11Text);
        sp11.addAnswer("[Name vergeben]", () -> {
            nameOK = false;

            while (!nameOK) {
                player.showInputMessageBox("Factory-Name", "Vergebe der Firma einen Namen", "", (t) -> {
                    try {
                        savedFactory = plugin.Factory.addNewFactory(player, t);
                        if (savedFactory != null) {
                            plugin.GUI.speakGuiSystem.setNewText(player, 12);
                            nameOK = true;
                        }
                    } catch (FactoryAlreadyExistsExeption ex) {
                        player.showErrorMessageBox("Factory Name existiert", "Diese Name existiert bereits!");
                    } catch (SQLException sqlex) {
                        player.showErrorMessageBox("ERROR - SQL", "Factory kann nicht gespeichert werden!");
                        plugin.GUI.speakGuiSystem.setNewText(player, 14);
                        nameOK = true;
                    }
                });
            }

            plugin.GUI.speakGuiSystem.setNewText(player, 12);
        });
        speakList.put(11, sp11);

        String[] sp12Text = {"Ok. Hier Firmenkonto wurde erfogreich eingerichtet.", "Kann ich sonst noch Helfen?"};
        SpeakObject sp12 = new SpeakObject(sp12Text);
        sp12.addAnswer("Ja.", 1);
        sp12.addAnswer("Nein. Auf Wiedersehen.");
        speakList.put(12, sp12);

        String[] sp13Text = {"Leider konnte ich Ihre Firmenkonto wegen eines Systemfehlers nicht anlegen.", "Bei Fragen, wenden Sie sich bitte an einen Admin."};
        SpeakObject sp13 = new SpeakObject(sp13Text);
        sp13.addAnswer("OK.");
        speakList.put(13, sp13);

        String[] sp14Text = {"Leider konnte ich Ihre neue Firma wegen eines Systemfehlers nicht anlegen.", "Bei Fragen, wenden Sie sich bitte an einen Admin."};
        SpeakObject sp14 = new SpeakObject(sp14Text);
        sp14.addAnswer("OK.");
        speakList.put(14, sp14);

        String[] sp15Text = {"Leider kann ich die Firma nicht unter der angegebenen ID finden."};
        SpeakObject sp15 = new SpeakObject(sp15Text);
        sp15.addAnswer("Ich versuch es nochmal.", 16);
        sp15.addAnswer("Versuche es später nochmal.");
        speakList.put(15, sp15);

        String[] sp16Text = {"OK. Wie lautet die ID?"};
        SpeakObject sp16 = new SpeakObject(sp16Text);
        sp16.addAnswer("[ID mitteilen]", () -> {
            plugin.GUI.speakGuiSystem.hide(player, false);
            player.showInputMessageBox("Firmenkonto anlegen", "Geben Sie die Firmen-Bank-ID ein:", null, (t) -> {
                Factory f = plugin.Factory.getFactoryByBankID(t);
                if (f != null) {
                    savedFactory = f;
                    savedID = t;
                    plugin.GUI.speakGuiSystem.show(player, npc, 17);
                } else {
                    plugin.GUI.speakGuiSystem.show(player, npc, 15);
                }
            });
        });
        sp16.addAnswer("Vielen Dank für die Info. Ich komme nochmal auf Sie zu.");
        speakList.put(16, sp16);

        String[] sp17Text = {"Sie wollen ein Firmenkonto für die Firma " + savedFactory.getName() + " erstellen?", "Sind Sie sicher?"};
        SpeakObject sp17 = new SpeakObject(sp17Text);
        sp17.addAnswer("Ja.", () -> {
            Factory f = savedFactory;
            if (f != null) {
                FactoryAccount fa = null;
                try {
                    fa = plugin.Bankystem.FactoryBankSystem.addFactoryAccount(player, f);
                } catch (SQLException ex) {
                    Console.sendErr("AddFactoryAccount-SQL", "========== iConomy-Error ==========");
                    Console.sendErr("AddFactoryAccount-SQL", ex.getMessage());
                    Console.sendErr("AddFactoryAccount-SQL", ex.getSQLState());
                    for (StackTraceElement el : ex.getStackTrace()) {
                        Console.sendErr("AddFactoryAccount-SQL", el.toString());
                    }
                    Console.sendErr("AddFactoryAccount-IO", "========== iConomy-Error ==========");
                    plugin.GUI.speakGuiSystem.show(player, npc, 13);
                } catch (IOException ex) {
                    Console.sendErr("AddFactoryAccount-IO", "========== iConomy-Error ==========");
                    Console.sendErr("AddFactoryAccount-IO", ex.getMessage());
                    for (StackTraceElement el : ex.getStackTrace()) {
                        Console.sendErr("AddFactoryAccount-IO", el.toString());
                    }
                    Console.sendErr("AddFactoryAccount-IO", "========== iConomy-Error ==========");
                    plugin.GUI.speakGuiSystem.show(player, npc, 13);
                }
                if (fa != null) {
                    plugin.GUI.speakGuiSystem.show(player, npc, 12);
                }
            } else {
                plugin.GUI.speakGuiSystem.show(player, npc, 15);
            }

        });
        sp17.addAnswer("Nein. Ich versuche es später nochmal.");
        speakList.put(16, sp16);
        
        } catch (Exception ex ) {
            
        }
    }

    public SpeakObject getSpeak(int id) {
        return speakList.get(id);
    }

    public class SpeakObject {

        private final String[] speakText;
        private final List<SpeakAnswer> answers;
        private final boolean onlyText;

        public SpeakObject(String[] speakText) {
            this.speakText = speakText;
            this.answers = new ArrayList<>();
            this.onlyText = false;
        }

        public SpeakObject(String[] speakText, boolean onlyText) {
            this.speakText = speakText;
            this.answers = new ArrayList<>();
            this.onlyText = onlyText;
        }

        public boolean isOnlyText() {
            return onlyText;
        }

        public String[] getSpeakText() {
            return speakText;
        }

        public List<SpeakAnswer> getAnswers() {
            return answers;
        }

        /**
         * Add answer (with GoTo)
         *
         * @param text The Text
         * @param nextID The ID of the next answer
         */
        public void addAnswer(String text, int nextID) {
            answers.add(new SpeakAnswer(text, nextID));
        }

        /**
         * Add a answer (with Code)
         *
         * @param text The Text
         * @param code The Code
         */
        public void addAnswer(String text, SpeakActionCode code) {
            answers.add(new SpeakAnswer(text, code));
        }

        /**
         * Add a Answer (with Stop)
         *
         * @param text The Text
         */
        public void addAnswer(String text) {
            answers.add(new SpeakAnswer(text));
        }

        public class SpeakAnswer {

            private final String text;
            private final SpeakAction action;
            private final SpeakActionCode code;
            private final int nextID;

            public SpeakAnswer(String text, int nextID) {
                this.text = text;
                this.action = SpeakAction.GoTo;
                this.code = null;
                this.nextID = nextID;
            }

            public SpeakAnswer(String text, SpeakActionCode code) {
                this.text = text;
                this.action = SpeakAction.Code;
                this.code = code;
                this.nextID = -1;
            }

            public SpeakAnswer(String text) {
                this.text = text;
                this.code = null;
                this.nextID = -1;
                this.action = SpeakAction.Stop;

            }

            public SpeakAction getAction() {
                return action;
            }

            public String getText() {
                return text;
            }

            public SpeakActionCode getCode() {
                return code;
            }

            public int getNextID() {
                return nextID;
            }

        }
    }

    public enum SpeakAction {
        GoTo,
        Code,
        Stop;

    }

}
