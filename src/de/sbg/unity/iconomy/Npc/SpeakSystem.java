package de.sbg.unity.iconomy.Npc;

import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.BusinessAlreadyExistsExeption;
import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.NotEnoughMoney;
import static de.sbg.unity.iconomy.Utils.TransferResult.Successful;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;

public class SpeakSystem {

    private final iConomy plugin;
    private final Player player;
    private Business savedBusiness;
    private final icConsole Console;
    private boolean nameOK;
    private String savedID;
    private final String lang;
    private Npc npc;
    private String factoryname;

    public SpeakSystem(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.player = player;
        this.Console = new icConsole(plugin);
        this.lang = player.getLanguage();
    }

    public Business getSavedBusiness() {
        return savedBusiness;
    }

    public void setSavedBusiness(Business savedBusiness) {
        this.savedBusiness = savedBusiness;
    }

    public interface SpeakActionCode {
        public void onRunCode();
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    public Npc getNpc() {
        return npc;
    }

    //LETZTE NUMMER: 19

    /**
     * id_00
     * <p>Deutscher Text:</p>
     * 1) "Herzlich willkommen in unserer Filiale."
     * 2) "Mein Name ist %s."
     * <p>Antworten (Deutsch):</p>
     * - "[Weiter]" -> führt zu id_01
     */
    public SpeakObject id_00() {
        String[] sp0Text = {
            plugin.Language.getNpc().getWelcome(player.getLanguage()),
            String.format(plugin.Language.getNpc().getMyName(lang), npc.getName())
        };
        SpeakObject sp0 = new SpeakObject(sp0Text);
        sp0.addAnswer(plugin.Language.getNpc().getNext(lang), id_01());
        return sp0;
    }

    /**
     * id_01
     * <p>Deutscher Text:</p>
     * 1) "Wie kann ich Ihnen helfen?"
     * <p>Antworten (Deutsch):</p>
     * - "Hallo, ich möchte ein Bankkonto erstellen." -> führt zu id_02 (falls kein Konto) oder id_03 (falls schon Konto)
     * - "Hallo, ich möchte eine Unternehmen gründen." -> führt zu id_07
     * - "Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_01() {
        String[] sp1Text = {plugin.Language.getNpc().getQuestionHelp(lang)};
        SpeakObject sp1 = new SpeakObject(sp1Text);

        if (!plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
            sp1.addAnswer(plugin.Language.getNpc().getCreateBank(lang), id_02());
        } else {
            sp1.addAnswer(plugin.Language.getNpc().getCreateBank(lang), id_03());
        }

        //sp1.addAnswer("Hallo, ich möchte meine Schuldengrenze erhöhen.", 4);
        if (plugin.Config.BusinessCreateGroups.contains(player.getPermissionGroup())) {
            sp1.addAnswer(plugin.Language.getNpc().getHelloCreateCompany(lang), id_CS()); //TODO id_07
        }

        // Gespräch beenden:
        sp1.addAnswer(plugin.Language.getNpc().getGoodbye(lang));
        return sp1;
    }

    /**
     * id_02
     * <p>Deutscher Text:</p>
     * 1) "Sie wollen also ein Bankkonto erstellen."
     * 2) "Das macht dann %s." (Kostenanzeige)
     * <p>Antworten (Deutsch):</p>
     * - "[Bezahlen]" -> führt zu id_18 (Erfolg) oder id_19 (nicht genug Geld)
     * - "Ich habe es mir anders überlegt. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_02() {
        String[] sp2Text = {
            plugin.Language.getNpc().getConfirmCreateBank(lang),
            String.format(
                plugin.Language.getNpc().getCostMessage(lang),
                plugin.moneyFormat.getMoneyAsFormatedString(player, plugin.Config.PlayerBankAccountCost)
            )
        };
        SpeakObject sp2 = new SpeakObject(sp2Text);

        sp2.addAnswer(plugin.Language.getNpc().getPay(lang), () -> {
            TransferResult result = plugin.CashSystem.removeCash(
                player,
                plugin.Config.PlayerBankAccountCost,
                RemoveCashEvent.Reason.Player
            );
            switch (result) {
                case Successful -> {
                    plugin.Bankystem.PlayerSystem.addPlayerAccount(player);
                    plugin.GUI.speakGuiSystem.setNewText(player, id_18());
                }
                case NotEnoughMoney -> {
                    plugin.GUI.speakGuiSystem.setNewText(player, id_19());
                }
            }
        });

        // Abbrechen
        sp2.addAnswer(plugin.Language.getNpc().getChangeMind(lang));
        return sp2;
    }

    /**
     * id_03
     * <p>Deutscher Text:</p>
     * 1) "Wie ich sehe, haben Sie bereits ein Bankkonto."
     * 2) "Wollen Sie noch ein Konto eröffnen?"
     * <p>Antworten (Deutsch):</p>
     * - "Ja, ein Firmenkonto." -> id_04
     * - "Nein. Ich hätte da noch eine andere Bitte." -> id_01
     * - "Nein, nicht nötig. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_03() {
        String[] sp3Text = {
            plugin.Language.getNpc().getAlreadyHaveBank(lang),
            plugin.Language.getNpc().getOpenAnotherAccount(lang)
        };
        SpeakObject sp3 = new SpeakObject(sp3Text);

        sp3.addAnswer(plugin.Language.getNpc().getBusinessAccount(lang), id_CS()); //TODO id_04
        sp3.addAnswer(plugin.Language.getNpc().getAnotherRequest(lang), id_01());
        sp3.addAnswer(plugin.Language.getNpc().getNotNecessary(lang));
        return sp3;
    }

    /**
     * id_04
     * <p>Deutscher Text:</p>
     * 1) "Sie wollen also ihre Schuldengrenze erhöhen."
     * 2) "Kein Problem. Aber übertreiben Sie es nicht."
     * <p>Antwort (Deutsch):</p>
     * - "Ich doch nicht." -> erhöht die Schuldengrenze; geht zu id_05
     */
    public SpeakObject id_04() {
        String[] sp4Text = {plugin.Language.getNpc().getIncreaseDebt(lang), plugin.Language.getNpc().getNoProblem(lang)};
        SpeakObject sp4 = new SpeakObject(sp4Text);

        sp4.addAnswer(plugin.Language.getNpc().getOfCourseNot(lang), () -> {
            long min = plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMin();
            // TODO Konfig-Eintrag: Wieviel senken?
            plugin.Bankystem.PlayerSystem.getPlayerAccount(player).setMin(min - 5);
            plugin.GUI.speakGuiSystem.setNewText(player, id_05());
        });

        return sp4;
    }

    /**
     * id_05
     * <p>Deutscher Text:</p>
     * 1) "Ich habe Ihre Schuldengrenze auf %s erhöht."
     * <p>Antworten (Deutsch):</p>
     * - "Vielen Dank. Ich hätte da noch eine Bitte." -> zurück zu id_01
     * - "Vielen Dank. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_05() {
        String[] sp5Text = {String.format(plugin.Language.getNpc().getDebtLimitIncreased(lang), plugin.moneyFormat.getMoneyAsFormatedString(player, plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMin()))};
        SpeakObject sp5 = new SpeakObject(sp5Text);
        sp5.addAnswer(plugin.Language.getNpc().getThankYouAnotherRequest(lang), id_01());
        sp5.addAnswer(plugin.Language.getNpc().getThankYouGoodbye(lang));
        return sp5;
    }

    /**
     * id_06
     * <p>Deutscher Text:</p>
     * 1) "Sie wollen ein Firmenkonto anlegen? Dafür brauche ich die Firmen-Bank-ID."
     * 2) "Diese finden Sie im Firmen-GUI im Bereich 'Info'."
     * <p>Antworten (Deutsch):</p>
     * - "[ID mitteilen]" -> zeigt Eingabefeld -> ggf. id_17 oder id_15
     * - "Vielen Dank für die Info. Ich komme nochmal auf Sie zu." -> beendet
     */
    public SpeakObject id_06() {
        String[] sp6Text = {
            plugin.Language.getNpc().getBusinessAccountNeeded(lang),
            plugin.Language.getNpc().getWhereToFindInfo(lang)
        };
        SpeakObject sp6 = new SpeakObject(sp6Text);

        sp6.addAnswer(plugin.Language.getNpc().getShareID(lang), () -> {
            plugin.GUI.speakGuiSystem.hide(player, false);
            player.showInputMessageBox(plugin.Language.getNpc().getOpenBusinessAccount(lang),
                plugin.Language.getNpc().getEnterBankId(lang),
                null,
                (t) -> {
                    Business f = plugin.Business.getBusinessByBankID(t);
                    if (f != null) {
                        savedID = t;
                        plugin.GUI.speakGuiSystem.show(player, npc, id_17(f));
                    } else {
                        plugin.GUI.speakGuiSystem.show(player, npc, id_15());
                    }
                }
            );
        });

        sp6.addAnswer(plugin.Language.getNpc().getThanksForInfo(lang));
        return sp6;
    }

    /**
     * id_07
     * <p>Deutscher Text:</p>
     * 1) "Sie wollen eine Unternehmen gründen? Das kostet Sie %s (Cash)."
     * <p>Antworten (Deutsch):</p>
     * - "[Bezahlen]" -> wenn genug Geld -> id_11
     * - "Nein, danke. Ich hätte da noch eine andere Bitte." -> id_01
     * - "Ich habe es mir anders überlegt. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_07() {
        String[] sp7Text = {String.format(plugin.Language.getNpc().getStartCompany(lang), plugin.moneyFormat.getMoneyAsFormatedString(player, plugin.Config.BusinessCost))};
        SpeakObject sp7 = new SpeakObject(sp7Text);

        sp7.addAnswer(plugin.Language.getNpc().getPay(lang), () -> {
            TransferResult result = plugin.CashSystem.removeCash(player, plugin.Config.BusinessCost, RemoveCashEvent.Reason.Buy);
            switch (result) {
                
                case Successful -> {
                    plugin.GUI.speakGuiSystem.setNewText(player, id_11());
                }
                case NotEnoughMoney -> {
                    plugin.GUI.speakGuiSystem.setNewText(player, id_19());
                }
            }
        });

        sp7.addAnswer(plugin.Language.getNpc().getNotInterested(lang), id_01());
        sp7.addAnswer(plugin.Language.getNpc().getChangeMind(lang));
        return sp7;
    }

    /**
     * id_08
     * <p>Deutscher Text:</p>
     * 1) "Herzlichen Glückwunsch. Sie sind nun Eigentümer der Unternehmen %s."
     * 2) "Kann ich Ihnen sonst noch helfen?"
     * <p>Antworten (Deutsch):</p>
     * - "Ja." -> id_01
     * - "Nein. Auf Wiedersehen." -> beendet
     * @param factoryname
     * @return The SpeakObject
     */
    public SpeakObject id_08(String factoryname) {
        String[] sp8Text = {
            String.format(plugin.Language.getNpc().getCongratsOwner(lang), factoryname),
            plugin.Language.getNpc().getCanIHelpElse(lang)
        };
        SpeakObject sp8 = new SpeakObject(sp8Text);

        sp8.addAnswer(plugin.Language.getNpc().getYes(lang), id_01());
        sp8.addAnswer(plugin.Language.getNpc().getNoGoodbye(lang));
        return sp8;
    }

    //    public SpeakObject id_09() {
    //        // Nicht implementiert
    //    }

    /**
     * id_10
     * <p>Deutscher Text:</p>
     * 1) "Einen Moment bitte … Sie besitzen leider keine Unternehmen."
     * 2) "Sie müssen erst eine gründen."
     * <p>Antworten (Deutsch):</p>
     * - "Dann möchte ich eine Unternehmen gründen." -> id_07
     * - "Okay. Anderes Thema." -> id_01
     * - "Okay. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_10() {
        String[] sp10Text = {
            plugin.Language.getNpc().getNoCompany(lang)
        };
        SpeakObject sp10 = new SpeakObject(sp10Text);

        sp10.addAnswer(plugin.Language.getNpc().getWantToStartCompany(lang), id_07());
        sp10.addAnswer(plugin.Language.getNpc().getOkOtherTopic(lang), id_01());
        sp10.addAnswer(plugin.Language.getNpc().getOkGoodbye(lang));
        return sp10;
    }

    /**
     * id_11
     * <p>Deutscher Text:</p>
     * 1) "Vielen Dank. Wie wollen Sie Ihre neue Unternehmen nennen?"
     * <p>Antworten (Deutsch):</p>
     * - "[Name vergeben]" -> Eingabefenster; bei Erfolg -> id_08, bei Existenz -> Fehlermeldung, bei SQL-Error -> id_14
     */
    public SpeakObject id_11() {
        String[] sp11Text = {
            plugin.Language.getNpc().getWhatToNameCompany(lang)
        };
        SpeakObject sp11 = new SpeakObject(sp11Text);
        
        sp11.addAnswer(plugin.Language.getNpc().getAssignName(lang), () -> {
            nameOK = false;
            
            while (!nameOK) {
                player.showInputMessageBox(plugin.Language.getNpc().getBusinessName(lang),
                    plugin.Language.getNpc().getGiveCompanyName(lang), "", (t) -> {
                        try {
                            savedBusiness = plugin.Business.addNewBusiness(player, t);
                            if (savedBusiness != null) {
                                nameOK = true;
                                factoryname = t;
                            }
                        } catch (BusinessAlreadyExistsExeption ex) {
                            player.showErrorMessageBox(
                                plugin.Language.getNpc().getBusinessExists(lang),
                                plugin.Language.getNpc().getNameAlreadyExists(lang)
                            );
                        } catch (SQLException sqlex) {
                            player.showErrorMessageBox(
                                plugin.Language.getNpc().getErrorSQL(lang),
                                plugin.Language.getNpc().getCannotSaveBusiness(lang)
                            );
                            plugin.GUI.speakGuiSystem.setNewText(player, id_14());
                            nameOK = true;
                        }
                    }
                );
            }

            plugin.GUI.speakGuiSystem.setNewText(player, id_08(factoryname));
        });

        return sp11;
    }

    /**
     * id_12
     * <p>Deutscher Text:</p>
     * 1) "Okay. Ihr Firmenkonto wurde erfolgreich eingerichtet."
     * 2) "Kann ich sonst noch helfen?"
     * <p>Antworten (Deutsch):</p>
     * - "Ja." -> id_01
     * - "Nein. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_12() {
        String[] sp12Text = {
            plugin.Language.getNpc().getBusinessAccountSetup(lang),
            plugin.Language.getNpc().getHelpNeeded(lang)
        };
        SpeakObject sp12 = new SpeakObject(sp12Text);

        sp12.addAnswer(plugin.Language.getNpc().getYes(lang), id_01());
        sp12.addAnswer(plugin.Language.getNpc().getNoGoodbye(lang));
        return sp12;
    }

    /**
     * id_13
     * <p>Deutscher Text:</p>
     * 1) "Leider konnte ich Ihre neue Unternehmen wegen eines Systemfehlers nicht anlegen."
     * 2) "Bei Fragen, wenden Sie sich bitte an einen Admin."
     * <p>Antwort (Deutsch):</p>
     * - "OK." -> beendet
     */
    public SpeakObject id_13() {
        String[] sp13Text = {plugin.Language.getNpc().getSystemErrorCompany(lang), plugin.Language.getNpc().getAdminHelp(lang)};
        SpeakObject sp13 = new SpeakObject(sp13Text);

        sp13.addAnswer(plugin.Language.getNpc().getOk(lang));
        return sp13;
    }

    /**
     * id_14
     * <p>Deutscher Text:</p>
     * 1) "Leider konnte ich Ihre neue Unternehmen wegen eines Systemfehlers nicht anlegen."
     * 2) "Bei Fragen, wenden Sie sich bitte an einen Admin."
     * <p>Antwort (Deutsch):</p>
     * - "OK." -> beendet
     * <p>(Gleicher Text wie id_13, aber wird im Code anders aufgerufen.)</p>
     */
    public SpeakObject id_14() {
        String[] sp14Text = {plugin.Language.getNpc().getSystemErrorCompany(lang), plugin.Language.getNpc().getAdminHelp(lang)};
        SpeakObject sp14 = new SpeakObject(sp14Text);

        sp14.addAnswer(plugin.Language.getNpc().getOk(lang));
        return sp14;
    }

    /**
     * id_15
     * <p>Deutscher Text:</p>
     * 1) "Leider kann ich die Unternehmen nicht unter der angegebenen ID finden."
     * <p>Antworten (Deutsch):</p>
     * - "Ich versuche es nochmal." -> id_16
     * - "Versuche es später nochmal." -> beendet
     */
    public SpeakObject id_15() {
        String[] sp15Text = {plugin.Language.getNpc().getIDNotFound(lang)};
        SpeakObject sp15 = new SpeakObject(sp15Text);

        sp15.addAnswer(plugin.Language.getNpc().getTryAgain(lang), id_16());
        sp15.addAnswer(plugin.Language.getNpc().getTryAgainLater(lang));
        return sp15;
    }

    /**
     * id_16
     * <p>Deutscher Text:</p>
     * 1) "OK. Wie lautet die ID?"
     * <p>Antworten (Deutsch):</p>
     * - "[ID mitteilen]" -> fragt erneut nach der ID -> ggf. id_17 oder id_15
     * - "Vielen Dank für die Info. Ich komme nochmal auf Sie zu." -> beendet
     */
    public SpeakObject id_16() {
        String[] sp16Text = {plugin.Language.getNpc().getAskForID(lang)};
        SpeakObject sp16 = new SpeakObject(sp16Text);

        sp16.addAnswer(plugin.Language.getNpc().getShareID(lang), () -> {
            plugin.GUI.speakGuiSystem.hide(player, false);
            player.showInputMessageBox(plugin.Language.getNpc().getOpenBusinessAccount(lang),
                plugin.Language.getNpc().getEnterBankId(lang),
                null,
                (t) -> {
                    Business f = plugin.Business.getBusinessByBankID(t);
                    if (f != null) {
                        plugin.GUI.speakGuiSystem.show(player, npc, id_17(f));
                    } else {
                        plugin.GUI.speakGuiSystem.show(player, npc, id_15());
                    }
                }
            );
        });

        sp16.addAnswer(plugin.Language.getNpc().getThanksForInfo(lang));
        return sp16;
    }

    /**
     * id_17
     * <p>Deutscher Text:</p>
     * 1) "Sie wollen ein Unternehmenskonto für das Unternehmen %s erstellen?"
     * 2) "Sind Sie sicher?"
     * <p>Antworten (Deutsch):</p>
     * - "Ja." -> legt Firmenkonto an -> id_12 (Erfolg) oder id_13 (Fehler) oder id_15 (Unternehmen nicht gefunden)
     * - "Nein. Ich versuche es später nochmal." -> beendet
     * @param firma
     * @return 
     */
    public SpeakObject id_17(Business firma) {
        String[] sp17Text = {String.format(plugin.Language.getNpc().getConfirmBusinessAccount(lang), firma.getName()), plugin.Language.getNpc().getAreYouSure(lang)};
        SpeakObject sp17 = new SpeakObject(sp17Text);

        sp17.addAnswer(plugin.Language.getNpc().getYes(lang), () -> {
            Business f = savedBusiness;
            if (f != null) {
                BusinessAccount fa = null;
                try {
                    fa = plugin.Bankystem.BusinessBankSystem.addBusinessAccount(player, f);
                } catch (SQLException ex) {
                    Console.sendErr("AddBusinessAccount-SQL", "========== iConomy-Error ==========");
                    Console.sendErr("AddBusinessAccount-SQL", ex.getMessage());
                    Console.sendErr("AddBusinessAccount-SQL", ex.getSQLState());
                    for (StackTraceElement el : ex.getStackTrace()) {
                        Console.sendErr("AddBusinessAccount-SQL", el.toString());
                    }
                    Console.sendErr("AddBusinessAccount-IO", "========== iConomy-Error ==========");
                    plugin.GUI.speakGuiSystem.show(player, npc, id_13());
                } catch (IOException ex) {
                    Console.sendErr("AddBusinessAccount-IO", "========== iConomy-Error ==========");
                    Console.sendErr("AddBusinessAccount-IO", ex.getMessage());
                    for (StackTraceElement el : ex.getStackTrace()) {
                        Console.sendErr("AddBusinessAccount-IO", el.toString());
                    }
                    Console.sendErr("AddBusinessAccount-IO", "========== iConomy-Error ==========");
                    plugin.GUI.speakGuiSystem.show(player, npc, id_13());
                }
                if (fa != null) {
                    plugin.GUI.speakGuiSystem.show(player, npc, id_12());
                }
            } else {
                plugin.GUI.speakGuiSystem.show(player, npc, id_15());
            }
        });

        sp17.addAnswer(plugin.Language.getNpc().getNoTryAgainLater(lang));
        // speakList.put(17, sp17);
        return sp17;
    }

    /**
     * id_18
     * <p>Deutscher Text:</p>
     * 1) "Ich habe erfolgreich ein Bankaccount für Sie angelegt."
     * <p>Antworten (Deutsch):</p>
     * - "Vielen Dank. Ich hätte da noch eine Bitte." -> id_01
     * - "Vielen Dank. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_18() {
        String[] sp18Text = {plugin.Language.getNpc().getCreateBankAccountSaccessfully(lang)};
        SpeakObject sp18 = new SpeakObject(sp18Text);

        sp18.addAnswer(plugin.Language.getNpc().getThankYouAnotherRequest(lang), id_01());
        sp18.addAnswer(plugin.Language.getNpc().getThankYouGoodbye(lang));
        return sp18;
    }

    /**
     * id_19
     * <p>Deutscher Text:</p>
     * 1) "Du hast leider nicht genug Geld dafür."
     * <p>Antwort (Deutsch):</p>
     * - "Okay. Auf Wiedersehen." -> beendet
     */
    public SpeakObject id_19() {
        String[] sp19Text = {plugin.Language.getNpc().getNotAnounthMoney(lang)};
        SpeakObject sp19 = new SpeakObject(sp19Text);

        sp19.addAnswer(plugin.Language.getNpc().getOkGoodbye(lang));
        return sp19;
    }
    /**
     * Coming Soon
    */
    public SpeakObject id_CS(){
        String[] spCsText = {plugin.Language.getNpc().getComingSoon(lang)};
        SpeakObject spCS = new SpeakObject(spCsText);
        
        spCS.addAnswer(plugin.Language.getNpc().getOk(lang), id_01());
        spCS.addAnswer(plugin.Language.getNpc().getOkGoodbye(lang));
        return spCS;
    }

    public void showAll() {
        System.out.println("ID\tText");
//        speakList.forEach((id, so) -> {
//            System.out.println(id + "\t" + (so == null ? "null" : so.speakText[0]));
//        });
        System.out.println();
    }

    public class SpeakObject {

        private final String[] speakText;
        private final List<SpeakAnswer> answers;
        private final boolean onlyText;
        private boolean showMoneyGui;

        public SpeakObject(String[] speakText) {
            this.speakText = speakText;
            this.answers = new ArrayList<>();
            this.onlyText = false;
            this.showMoneyGui = false;
        }

        public SpeakObject(String[] speakText, boolean onlyText) {
            this.speakText = speakText;
            this.answers = new ArrayList<>();
            this.onlyText = onlyText;
            this.showMoneyGui = false;
        }

        public boolean isShowMoneyGui() {
            return showMoneyGui;
        }

        public void setShowMoneyGui(boolean showMoneyGui) {
            this.showMoneyGui = showMoneyGui;
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
         * @param nextSpeak The next SpeakObject
         */
        public void addAnswer(String text, SpeakObject nextSpeak) {
            answers.add(new SpeakAnswer(text, nextSpeak));
        }

        /**
         * Add an answer (with Code)
         *
         * @param text The Text
         * @param code The Code to run
         */
        public void addAnswer(String text, SpeakActionCode code) {
            answers.add(new SpeakAnswer(text, code));
        }

        /**
         * Add an answer (with Stop)
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
            private final SpeakObject nextSpeak;

            public SpeakAnswer(String text, SpeakObject so) {
                this.text = text;
                this.action = SpeakAction.GoTo;
                this.code = null;
                this.nextSpeak = so;
            }

            public SpeakAnswer(String text, SpeakActionCode code) {
                this.text = text;
                this.action = SpeakAction.Code;
                this.code = code;
                this.nextSpeak = null;
            }

            public SpeakAnswer(String text) {
                this.text = text;
                this.code = null;
                this.nextSpeak = null;
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

            public SpeakObject getNextSpeak() {
                return nextSpeak;
            }
        }
    }

    public enum SpeakAction {
        GoTo,
        Code,
        Stop;
    }
}
