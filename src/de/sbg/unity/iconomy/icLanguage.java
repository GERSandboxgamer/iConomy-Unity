package de.sbg.unity.iconomy;

import de.chaoswg.lang.Model3DPlaceLang;
import java.util.HashMap;

public class icLanguage {

    private String defaultLanguage;
    private Command command;
    private Status status;
    private Other other;
    private GUI gui;
    private Npc npc;
    private GameObject gameObject;
    private Model3DPlaceLang.RadialPlaceClass RadialPlace;
//    private Business factory;

    public icLanguage() {
        this.command = new Command();
        this.status = new Status();
        this.other = new Other();
        this.gui = new GUI();
        this.gameObject = new GameObject();
        this.RadialPlace = new Model3DPlaceLang().new RadialPlaceClass();
        this.npc = new Npc();

//      this.factory = new Business();
        setDefaultLanguage("en");

        //### Radial Place ###
        HashMap<String, String> scaleMap = new HashMap<>();
        scaleMap.put("de", "Größe");
        scaleMap.put("en", "Scale");
        getRadialPlace().setScale(scaleMap);

        HashMap<String, String> rotatMap = new HashMap<>();
        rotatMap.put("de", "Drehung");
        rotatMap.put("en", "Rotation");
        getRadialPlace().setRotation(rotatMap);

        HashMap<String, String> moveMap = new HashMap<>();
        moveMap.put("de", "Bewegen");
        moveMap.put("en", "Move");
        getRadialPlace().setMove(moveMap);

        HashMap<String, String> mBack = new HashMap<>();
        mBack.put("de", "Zurück");
        mBack.put("en", "Back");
        getRadialPlace().setBack(mBack);

        //TODO Lang - Other
        HashMap<String, String> NoPermission = new HashMap<>();
        NoPermission.put("de", "Du hast nicht genug Berechtigung!");
        NoPermission.put("en", "You do not have enough permission!");
        getOther().setNoPermission(NoPermission);

        //TODO Lang - Command
        HashMap<String, String> Use = new HashMap<>();
        Use.put("de", "Benutze:");
        Use.put("en", "Use:");
        getCommand().setUse(Use);

        HashMap<String, String> CreateBank = new HashMap<>();
        CreateBank.put("de", "Bank-Account erfolgreich erstellt!");
        CreateBank.put("en", "Create bank account successfully!");
        getCommand().setCreateBank(CreateBank);

        HashMap<String, String> CreateBank_HasAccount = new HashMap<>();
        CreateBank_HasAccount.put("de", "Du hast bereits ein Account!");
        CreateBank_HasAccount.put("en", "You already have an account!");
        getCommand().setCreateBank_HasAccount(CreateBank_HasAccount);

        HashMap<String, String> AdminGivecash = new HashMap<>();
        AdminGivecash.put("de", "Bargeld dem Spieler erfolgreich hinzugefügt.");
        AdminGivecash.put("en", "Successfully added cash to the player.");
        getCommand().setAdminGivecash(AdminGivecash);

        HashMap<String, String> AdminTakecash = new HashMap<>();
        AdminTakecash.put("de", "Bargeld erfolgreich vom Spieler abgezogen.");
        AdminTakecash.put("en", "Cash successfully withdrawn from player.");
        getCommand().setAdminTakecash(AdminTakecash);

        HashMap<String, String> AdminSetcash = new HashMap<>();
        AdminSetcash.put("de", "Bargeld des Spielers erfolgreich geändert.");
        AdminSetcash.put("en", "Successfully changed player's cash.");
        getCommand().setAdminSetcash(AdminSetcash);

        HashMap<String, String> AdminTakebank = new HashMap<>();
        AdminTakebank.put("de", "Geld erfolgreich von der Bank abgezogen.");
        AdminTakebank.put("en", "Money successfully withdrawn from the bank.");
        getCommand().setAdminTakebank(AdminTakebank);

        HashMap<String, String> AdminGivebank = new HashMap<>();
        AdminGivebank.put("de", "Geld erfolgreich zur Bank hinzugefügt.");
        AdminGivebank.put("en", "Successfully added money to the bank.");
        getCommand().setAdminGivebank(AdminGivebank);

        HashMap<String, String> AdminSetbank = new HashMap<>();
        AdminSetbank.put("de", "Geld der Bank erfolgreich geändert.");
        AdminSetbank.put("en", "Bank money successfully changed.");
        getCommand().setAdminSetbank(AdminSetbank);

        //TODO Lang - Status
        HashMap<String, String> SendMoneyOK = new HashMap<>();
        SendMoneyOK.put("de", "Erfolgreich Geld an anderen Spieler gesendet!");
        SendMoneyOK.put("en", "Send money to other player successfully!");
        getStatus().setSendMoneyOK(SendMoneyOK);

        HashMap<String, String> Sign_Misspelled = new HashMap<>();
        Sign_Misspelled.put("de", "Schild wurde falsch geschrieben!");
        Sign_Misspelled.put("en", "Sign was misspelled!");
        getStatus().setSign_Misspelled(Sign_Misspelled);

        HashMap<String, String> Sign_OK = new HashMap<>();
        Sign_OK.put("de", "Schild wurde erfolgreich erstellt!");
        Sign_OK.put("en", "Sign set!");
        getStatus().setSign_OK(Sign_OK);

        HashMap<String, String> Sign_Destroy_Fail = new HashMap<>();
        Sign_Destroy_Fail.put("de", "Dieses Schild kann nicht zerstört werden!");
        Sign_Destroy_Fail.put("en", "This sign cannot be destroyed!");
        getStatus().setSign_Distroy_Fail(Sign_Destroy_Fail);

        HashMap<String, String> PlayerNotConnected = new HashMap<>();
        PlayerNotConnected.put("de", "Spieler momentan nicht auf dem Server!");
        PlayerNotConnected.put("en", "Player not connected!");
        getStatus().setPlayerNotConnected(PlayerNotConnected);

        HashMap<String, String> PlayerNotExist = new HashMap<>();
        PlayerNotExist.put("de", "Spieler existiert nicht!");
        PlayerNotExist.put("en", "Player not exist!");
        getStatus().setPlayerNotExist(PlayerNotExist);

        HashMap<String, String> LostMoney = new HashMap<>();
        LostMoney.put("de", "Du hast dein Bargeld verloren!");
        LostMoney.put("en", "You have lost your cash!");
        getStatus().setLostMoney(LostMoney);

        HashMap<String, String> MoneyMustBeNumber = new HashMap<>();
        MoneyMustBeNumber.put("de", "Der Geldbetrag muss eine Zahl sein!");
        MoneyMustBeNumber.put("en", "The amount must be a number!");
        getStatus().setMoneyMustBeNumber(MoneyMustBeNumber);

        HashMap<String, String> TransferCancel = new HashMap<>();
        TransferCancel.put("de", "Der Transfer wurde abgebrochen!");
        TransferCancel.put("en", "Tranfer was cancelled!");
        getStatus().setTransferCancel(TransferCancel);

        HashMap<String, String> PlayerNotAnounthMoney = new HashMap<>();
        PlayerNotAnounthMoney.put("de", "Du hast nicht genug Geld!");
        PlayerNotAnounthMoney.put("en", "You do not have enough money!");
        getStatus().setPlayerNotAnounthMoney(PlayerNotAnounthMoney);

        HashMap<String, String> OtherPlayerNotAnounthMoney = new HashMap<>();
        OtherPlayerNotAnounthMoney.put("de", "Der Spieler hat nicht genug Geld!");
        OtherPlayerNotAnounthMoney.put("en", "The player has not enough money!");
        getStatus().setPlayerNotAnounthMoney(OtherPlayerNotAnounthMoney);

        HashMap<String, String> OtherPlayerHasNoAccount = new HashMap<>();
        OtherPlayerHasNoAccount.put("de", "Der Spieler hat kein Bank-Account!");
        OtherPlayerHasNoAccount.put("en", "The player has not a bank account!");
        getStatus().setOtherPlayerHasNoAccount(OtherPlayerHasNoAccount);

        HashMap<String, String> PlayerHasNoAccount = new HashMap<>();
        PlayerHasNoAccount.put("de", "Du hast kein kein Bank-Account!");
        PlayerHasNoAccount.put("en", "You do not have a bank account!");
        getStatus().setPlayerHasNoAccount(PlayerHasNoAccount);

        HashMap<String, String> EmptyAmount = new HashMap<>();
        EmptyAmount.put("de", "Der Betrag ist leer!");
        EmptyAmount.put("en", "The amount is empty!");
        getStatus().setEmptyAmount(EmptyAmount);

        HashMap<String, String> AmountBigger = new HashMap<>();
        AmountBigger.put("de", "Der Betrag muss mehr als 0 sein!");
        AmountBigger.put("en", "The amount must be more than 0!");
        getStatus().setAmountBigger(AmountBigger);

        HashMap<String, String> SendCashToSelf = new HashMap<>();
        SendCashToSelf.put("de", "Du kannst kein Geld an dich selbst senden!");
        SendCashToSelf.put("en", "You can not send money to your self!");
        getStatus().setSendCashToSelf(SendCashToSelf);

        HashMap<String, String> PlayerDeath_KillerAdd = new HashMap<>();
        PlayerDeath_KillerAdd.put("de", "Du hast Geld erbeutet.");
        PlayerDeath_KillerAdd.put("en", "You get money.");
        getStatus().setPlayerDeath_KillerAdd(PlayerDeath_KillerAdd);

        HashMap<String, String> PlayerDeath_MoneyEmpty = new HashMap<>();
        PlayerDeath_MoneyEmpty.put("de", "Der Spieler hatte kein Geld!");
        PlayerDeath_MoneyEmpty.put("en", "The player had no money!");
        getStatus().setPlayerDeath_MoneyEmpty(PlayerDeath_MoneyEmpty);

        HashMap<String, String> PlayerDeath_MoneyEmpty_Player = new HashMap<>();
        PlayerDeath_MoneyEmpty_Player.put("de", "Der Spieler hatte kein Geld!");
        PlayerDeath_MoneyEmpty_Player.put("en", "The player had no money!");
        getStatus().setPlayerDeath_MoneyEmpty_Player(PlayerDeath_MoneyEmpty_Player);

        //TODO Lang - GUI
        HashMap<String, String> PlaceAtmGui_Amount = new HashMap<>();
        PlaceAtmGui_Amount.put("de", "Menge");
        PlaceAtmGui_Amount.put("en", "Amount");
        getGui().setPlaceAtmGui_Amount(PlaceAtmGui_Amount);

        HashMap<String, String> PlaceAtmGui_Enter = new HashMap<>();
        PlaceAtmGui_Enter.put("de", "[Enter] = ATM platzieren");
        PlaceAtmGui_Enter.put("en", "[Enter] = Place ATM");
        getGui().setPlaceAtmGui_Enter(PlaceAtmGui_Enter);

        HashMap<String, String> PlaceAtmGui_Escape = new HashMap<>();
        PlaceAtmGui_Escape.put("de", "[Esc] = Abbrechen");
        PlaceAtmGui_Escape.put("en", "[Esc] = Cancel");
        getGui().setPlaceAtmGui_Escape(PlaceAtmGui_Escape);

        HashMap<String, String> PlaceAtmGui_LeftRight_Pos = new HashMap<>();
        PlaceAtmGui_LeftRight_Pos.put("de", "[Links]|[Rechts] = Y-Axe");
        PlaceAtmGui_LeftRight_Pos.put("en", "[Left]|[Right] = Y-Axe");
        getGui().setPlaceAtmGui_LeftRight_Pos(PlaceAtmGui_LeftRight_Pos);

        HashMap<String, String> PlaceAtmGui_LeftRight_Rot = new HashMap<>();
        PlaceAtmGui_LeftRight_Rot.put("de", "[Links]|[Rechts] = drehen");
        PlaceAtmGui_LeftRight_Rot.put("en", "[Left]|[Right] = rotate");
        getGui().setPlaceAtmGui_LeftRight_Rot(PlaceAtmGui_LeftRight_Rot);

        HashMap<String, String> PlaceAtmGui_Minus_Pos = new HashMap<>();
        PlaceAtmGui_Minus_Pos.put("de", "[-] = Position-Menge verringern");
        PlaceAtmGui_Minus_Pos.put("en", "[-] = Reduce position amount");
        getGui().setPlaceAtmGui_Minus_Pos(PlaceAtmGui_Minus_Pos);

        HashMap<String, String> PlaceAtmGui_Minus_Rot = new HashMap<>();
        PlaceAtmGui_Minus_Rot.put("de", "[-] = Rotation-Menge verringern");
        PlaceAtmGui_Minus_Rot.put("en", "[-] = Reduce rotation amount");
        getGui().setPlaceAtmGui_Minus_Rot(PlaceAtmGui_Minus_Rot);

        HashMap<String, String> PlaceAtmGui_UpDown_Pos = new HashMap<>();
        PlaceAtmGui_UpDown_Pos.put("de", "[Rauf]|[Runter] = X-Axe");
        PlaceAtmGui_UpDown_Pos.put("en", "[Up]|[Down] = X-Axe");
        getGui().setPlaceAtmGui_UpDown_Pos(PlaceAtmGui_UpDown_Pos);

        HashMap<String, String> PlaceAtmGui_UpDown_Rot = new HashMap<>();
        PlaceAtmGui_UpDown_Rot.put("de", "[Rauf]|[Runter] = kippen");
        PlaceAtmGui_UpDown_Rot.put("en", "[Up]|[Down] = tilt");
        getGui().setPlaceAtmGui_UpDown_Rot(PlaceAtmGui_UpDown_Rot);

        HashMap<String, String> PlaceAtmGui_Mode = new HashMap<>();
        PlaceAtmGui_Mode.put("de", "Modus:");
        PlaceAtmGui_Mode.put("en", "Mode:");
        getGui().setPlaceAtmGui_Mode(PlaceAtmGui_Mode);

        HashMap<String, String> PlaceAtmGui_Multiply = new HashMap<>();
        PlaceAtmGui_Multiply.put("de", "[*] = Modus wechseln");
        PlaceAtmGui_Multiply.put("en", "[*] = Change mode");
        getGui().setPlaceAtmGui_Multiply(PlaceAtmGui_Multiply);

        HashMap<String, String> PlaceAtmGui_PageUpDown = new HashMap<>();
        PlaceAtmGui_PageUpDown.put("de", "[Bild rauf]|[Bild runter] = Z-Axe");
        PlaceAtmGui_PageUpDown.put("en", "[Page Up]|[Page Down] = Z-Axe");
        getGui().setPlaceAtmGui_PageUpDown(PlaceAtmGui_PageUpDown);

        HashMap<String, String> PlaceAtmGui_Plus_Pos = new HashMap<>();
        PlaceAtmGui_Plus_Pos.put("de", "[+] = Position-Menge erhöhen");
        PlaceAtmGui_Plus_Pos.put("en", "[+] = Increase position amount");
        getGui().setPlaceAtmGui_Plus_Pos(PlaceAtmGui_Plus_Pos);

        HashMap<String, String> PlaceAtmGui_Plus_Rot = new HashMap<>();
        PlaceAtmGui_Plus_Rot.put("de", "[+] = Rotation-Menge erhöhen");
        PlaceAtmGui_Plus_Rot.put("en", "[+] = Increase rotation amount");
        getGui().setPlaceAtmGui_Plus_Rot(PlaceAtmGui_Plus_Rot);

        HashMap<String, String> SelectCashInOutGUI_Titel = new HashMap<>();
        SelectCashInOutGUI_Titel.put("de", "Modus wählen");
        SelectCashInOutGUI_Titel.put("en", "Select Mode");
        getGui().setSelectCashInOutGUI_Titel(SelectCashInOutGUI_Titel);

        HashMap<String, String> SelectCashInOutGUI_Message = new HashMap<>();
        SelectCashInOutGUI_Message.put("de", "Wähle einen Modus:");
        SelectCashInOutGUI_Message.put("en", "Choose a mode:");
        getGui().setSelectCashInOutGUI_Message(SelectCashInOutGUI_Message);

        HashMap<String, String> Send = new HashMap<>();
        Send.put("de", "Senden");
        Send.put("en", "Send");
        getGui().setSend(Send);

        HashMap<String, String> Cancel = new HashMap<>();
        Cancel.put("de", "Abbrechen");
        Cancel.put("en", "Cancel");
        getGui().setCancel(Cancel);

        HashMap<String, String> SendCashGUI_Title = new HashMap<>();
        SendCashGUI_Title.put("de", "iConomy - Sende Cash");
        SendCashGUI_Title.put("en", "iConomy - Send Cash");
        getGui().setSendCashGUI_Title(SendCashGUI_Title);

        HashMap<String, String> SendCashGUI_Player = new HashMap<>();
        SendCashGUI_Player.put("de", "UID des Spielers:");
        SendCashGUI_Player.put("en", "Player-UID:");
        getGui().setSendCashGUI_Player(SendCashGUI_Player);

        HashMap<String, String> SendCashGUI_BodyText = new HashMap<>();
        SendCashGUI_BodyText.put("de", "Sende Cash zu einem Spieler!");
        SendCashGUI_BodyText.put("en", "Send cash to a player!");
        getGui().setSendCashGUI_BodyText(SendCashGUI_BodyText);

        HashMap<String, String> GUI_Amount = new HashMap<>();
        GUI_Amount.put("de", "Betrag");
        GUI_Amount.put("en", "Amount");
        getGui().setGUI_Amount(GUI_Amount);

        HashMap<String, String> NoAccount = new HashMap<>();
        NoAccount.put("de", "(Kein Account)");
        NoAccount.put("en", "(No Account)");
        getGui().setNoAccount(NoAccount);

        HashMap<String, String> CashInOutGUI_In_Message = new HashMap<>();
        CashInOutGUI_In_Message.put("de", "Zahle Cash in die Bank ein!");
        CashInOutGUI_In_Message.put("en", "Put cash into the bank!");
        getGui().setCashInOutGUI_In_Message(CashInOutGUI_In_Message);

        HashMap<String, String> CashInOutGUI_Out_Message = new HashMap<>();
        CashInOutGUI_Out_Message.put("de", "Bekomme Cash von der Bank!");
        CashInOutGUI_Out_Message.put("en", "Get cash from the bank!");
        getGui().setCashInOutGUI_Out_Message(CashInOutGUI_Out_Message);

        HashMap<String, String> YourCash = new HashMap<>();
        YourCash.put("de", "Dein Cash:");
        YourCash.put("en", "Your Cash:");
        getGui().setYourCash(YourCash);

        HashMap<String, String> YourBank = new HashMap<>();
        YourBank.put("de", "Deine Bank:");
        YourBank.put("en", " Your Bank:");
        getGui().setYourBank(YourBank);

        HashMap<String, String> Transfer = new HashMap<>();
        Transfer.put("de", "Überweisung");
        Transfer.put("en", "Transfer");
        getGui().setTransfer(Transfer);

        HashMap<String, String> Cash = new HashMap<>();
        Cash.put("de", "Bar");
        Cash.put("en", "Cash");
        getGui().setCash(Cash);

        HashMap<String, String> TransferText = new HashMap<>();
        TransferText.put("de", "Überweise Geld an einen anderen Spieler");
        TransferText.put("en", "Transfer money to another player");
        getGui().setTransferText(TransferText);

        HashMap<String, String> Accounts = new HashMap<>();
        Accounts.put("de", "Bankkonten");
        Accounts.put("en", "Accounts");
        getGui().setAccounts(Accounts);

        HashMap<String, String> OwnAccount = new HashMap<>();
        OwnAccount.put("de", "Eigenes Konto");
        OwnAccount.put("en", "Own Account");
        getGui().setOwnAccount(OwnAccount);

        HashMap<String, String> OtherAccount = new HashMap<>();
        OtherAccount.put("de", "Anderes Konto");
        OtherAccount.put("en", "Other Account");
        getGui().setOtherAccount(OtherAccount);

        HashMap<String, String> Username = new HashMap<>();
        Username.put("de", "Spielername");
        Username.put("en", "Username");
        getGui().setUsername(Username);

        HashMap<String, String> AccountInfoText = new HashMap<>();
        AccountInfoText.put("de", "Hier stehen alle Informationen über dieses Account.");
        AccountInfoText.put("en", "All information about this account is here.");
        getGui().setAccountInfoText(AccountInfoText);

        HashMap<String, String> Balance = new HashMap<>();
        Balance.put("de", "Kontostand");
        Balance.put("en", "Balance");
        getGui().setBalance(Balance);

        HashMap<String, String> MinBalance = new HashMap<>();
        MinBalance.put("de", "Schuldengrenze");
        MinBalance.put("en", "Debt limit");
        getGui().setMinBalance(MinBalance);

        HashMap<String, String> Owner = new HashMap<>();
        Owner.put("de", "Eigentümer");
        Owner.put("en", "Owner");
        getGui().setOwner(Owner);

        HashMap<String, String> Statements = new HashMap<>();
        Statements.put("de", "Kontoauszug");
        Statements.put("en", "Statements");
        getGui().setStatements(Statements);

        HashMap<String, String> Permissions = new HashMap<>();
        Permissions.put("de", "Berechtigungen");
        Permissions.put("en", "Permissions");
        getGui().setPermissions(Permissions);

        HashMap<String, String> Money = new HashMap<>();
        Money.put("de", "Geld");
        Money.put("en", "Money");
        getGui().setMoney(Money);

        HashMap<String, String> MoneyText = new HashMap<>();
        MoneyText.put("de", "Sende Geld an andere Spieler oder verwalte dein Bargeld!");
        MoneyText.put("en", "Send money to other players or manage your cash!");
        getGui().setMoneyText(MoneyText);

        HashMap<String, String> Members = new HashMap<>();
        Members.put("de", "Mitglieder");
        Members.put("en", "Members");
        getGui().setMembers(Members);

        HashMap<String, String> MemberText1 = new HashMap<>();
        MemberText1.put("de", "Hier werden die Mitglieder dieses Accounts angezeigt.");
        MemberText1.put("en", "The members of this account are displayed here.");
        getGui().setMemberText1(MemberText1);

        HashMap<String, String> MemberText2 = new HashMap<>();
        MemberText2.put("de", "grün = Mitglied; rot = Kein Mitglied");
        MemberText2.put("en", "green = member; red = not a member");
        getGui().setMemberText2(MemberText2);

        //TODO Lang - GameObject
        HashMap<String, String> FindMoney = new HashMap<>();
        FindMoney.put("de", "Du hast Geld gefunden!");
        FindMoney.put("en", "You found money!");
        getGameObject().setFindMoney(FindMoney);

        HashMap<String, String> CreateAtm = new HashMap<>();
        CreateAtm.put("de", "ATM erstellt!");
        CreateAtm.put("en", "Create ATM!");
        getGameObject().setCreateAtm(CreateAtm);

        HashMap<String, String> CreateAtm_Fail = new HashMap<>();
        CreateAtm_Fail.put("de", "ATM kann nicht erstellt werden!");
        CreateAtm_Fail.put("en", "Can not create ATM!");
        getGameObject().setCreateAtm_Fail(CreateAtm_Fail);

        HashMap<String, String> RemoveAtm = new HashMap<>();
        RemoveAtm.put("de", "ATM wurde gelöscht!");
        RemoveAtm.put("en", "Delete ATM!");
        getGameObject().setRemoveAtm(RemoveAtm);

        HashMap<String, String> RemoveAtm_Fail = new HashMap<>();
        RemoveAtm_Fail.put("de", "ATM kann nicht zerstört werden!");
        RemoveAtm_Fail.put("en", "Can not destroy ATM!");
        getGameObject().setRemoveAtm_Fail(RemoveAtm_Fail);

        HashMap<String, String> SaveAtm_Fail = new HashMap<>();
        SaveAtm_Fail.put("de", "ATM konnte nicht in die Datenbank gespeichert werden!");
        SaveAtm_Fail.put("en", "Can not save ATM to the database!");
        getGameObject().setSaveAtm_Fail(SaveAtm_Fail);

        HashMap<String, String> InteractAtm_Fail = new HashMap<>();
        InteractAtm_Fail.put("de", "Du kannst dieses ATM nicht nutzen!");
        InteractAtm_Fail.put("en", "You can not interact with this atm!");
        getGameObject().setInteractAtm_Fail(InteractAtm_Fail);

        HashMap<String, String> PlaceAtm = new HashMap<>();
        PlaceAtm.put("de", "ATM platziert!");
        PlaceAtm.put("en", "Place ATM!");
        getGameObject().setPlaceAtm(PlaceAtm);

        HashMap<String, String> PlaceAtm_Fail = new HashMap<>();
        PlaceAtm_Fail.put("de", "Du kannst kein ATM platzieren!");
        PlaceAtm_Fail.put("en", "You can not place a ATM!");
        getGameObject().setPlaceAtm_Fail(PlaceAtm_Fail);

        //TODO Lang - Npc JASON
        HashMap<String, String> welcome = new HashMap<>(); // 1
        welcome.put("de", "Herzlich willkommen in unserer Filiale.");
        welcome.put("en", "Welcome to our branch.");
        getNpc().setWelcome(welcome);

        HashMap<String, String> myName = new HashMap<>(); // 2
        myName.put("de", "Mein Name ist %s.");
        myName.put("en", "My name is %s.");
        getNpc().setMyName(myName);

        HashMap<String, String> questionHelp = new HashMap<>(); // 3
        questionHelp.put("de", "Wie kann ich Ihnen helfen?");
        questionHelp.put("en", "How can I help you?");
        getNpc().setQuestionHelp(questionHelp);

        HashMap<String, String> createBank = new HashMap<>(); // 4
        createBank.put("de", "Hallo, ich möchte ein Bankkonto erstellen.");
        createBank.put("en", "Hello, I would like to open a bank account.");
        getNpc().setCreateBank(createBank);

        HashMap<String, String> goodbye = new HashMap<>(); // 5
        goodbye.put("de", "Auf Wiedersehen.");
        goodbye.put("en", "Goodbye.");
        getNpc().setGoodbye(goodbye);

        HashMap<String, String> confirmCreateBank = new HashMap<>(); // 6
        confirmCreateBank.put("de", "Sie wollen also ein Bankkonto erstellen.");
        confirmCreateBank.put("en", "So you want to open a bank account.");
        getNpc().setConfirmCreateBank(confirmCreateBank);

        HashMap<String, String> costMessage = new HashMap<>(); // 7
        costMessage.put("de", "Das macht dann %s.");
        costMessage.put("en", "That will be %s.");
        getNpc().setCostMessage(costMessage);
        
        HashMap<String, String> createBankAccountSaccessfully = new HashMap<>();
        createBankAccountSaccessfully.put("de", "Ich habe erfolgreich ein Bankaccount für Sie angelegt.");
        createBankAccountSaccessfully.put("en", "I have successfully created a bank account for you.");
        getNpc().setCreateBankAccountSaccessfully(createBankAccountSaccessfully);
        
        HashMap<String, String> createBankAccountFailMoney = new HashMap<>();
        createBankAccountFailMoney.put("de", "Du hast leider nicht genug Geld dafür.");
        createBankAccountFailMoney.put("en", "Unfortunately, you don't have enough money for that.");
        getNpc().setNotAnounthMoney(createBankAccountFailMoney);

        HashMap<String, String> changeMind = new HashMap<>(); // 8
        changeMind.put("de", "Ich habe es mir anders überlegt. Auf Wiedersehen.");
        changeMind.put("en", "I changed my mind. Goodbye.");
        getNpc().setChangeMind(changeMind);

        HashMap<String, String> alreadyHaveBank = new HashMap<>(); // 9
        alreadyHaveBank.put("de", "Wie ich sehe, haben Sie bereits ein Bankkonto.");
        alreadyHaveBank.put("en", "As I see, you already have a bank account.");
        getNpc().setAlreadyHaveBank(alreadyHaveBank);

        HashMap<String, String> openAnotherAccount = new HashMap<>(); // 10
        openAnotherAccount.put("de", "Wollen Sie noch ein Konto eröffnen?");
        openAnotherAccount.put("en", "Do you want to open another account?");
        getNpc().setOpenAnotherAccount(openAnotherAccount);

        HashMap<String, String> businessAccount = new HashMap<>(); // 11
        businessAccount.put("de", "Ja, ein Firmenkonto.");
        businessAccount.put("en", "Yes, a business account.");
        getNpc().setBusinessAccount(businessAccount);

        HashMap<String, String> anotherRequest = new HashMap<>(); // 12
        anotherRequest.put("de", "Nein. Ich hätte da noch eine andere Bitte.");
        anotherRequest.put("en", "No. I have another request.");
        getNpc().setAnotherRequest(anotherRequest);

        HashMap<String, String> notNecessary = new HashMap<>(); // 13
        notNecessary.put("de", "Nein, nicht nötig. Auf Wiedersehen.");
        notNecessary.put("en", "No, not necessary. Goodbye.");
        getNpc().setNotNecessary(notNecessary);

        HashMap<String, String> increaseDebt = new HashMap<>(); // 14
        increaseDebt.put("de", "Sie wollen also ihre Schuldengrenze erhöhen.");
        increaseDebt.put("en", "So you want to increase your debt limit.");
        getNpc().setIncreaseDebt(increaseDebt);

        HashMap<String, String> noProblem = new HashMap<>(); // 15
        noProblem.put("de", "Kein Problem. Aber übertreiben Sie es nicht.");
        noProblem.put("en", "No problem. But don't overdo it.");
        getNpc().setNoProblem(noProblem);

        HashMap<String, String> ofCourseNot = new HashMap<>(); // 16
        ofCourseNot.put("de", "Ich doch nicht.");
        ofCourseNot.put("en", "Of course not.");
        getNpc().setOfCourseNot(ofCourseNot);

        HashMap<String, String> debtLimitIncreased = new HashMap<>(); // 17
        debtLimitIncreased.put("de", "Ich habe Ihre Schuldengrenze auf %s erhöht.");
        debtLimitIncreased.put("en", "I have increased your debt limit to %s.");
        getNpc().setDebtLimitIncreased(debtLimitIncreased);

        HashMap<String, String> thankYouAnotherRequest = new HashMap<>(); // 18
        thankYouAnotherRequest.put("de", "Vielen Dank. Ich hätte da noch eine Bitte.");
        thankYouAnotherRequest.put("en", "Thank you. I have another request.");
        getNpc().setThankYouAnotherRequest(thankYouAnotherRequest);

        HashMap<String, String> thankYouGoodbye = new HashMap<>(); // 19
        thankYouGoodbye.put("de", "Vielen Dank. Auf Wiedersehen.");
        thankYouGoodbye.put("en", "Thank you. Goodbye.");
        getNpc().setThankYouGoodbye(thankYouGoodbye);

        HashMap<String, String> businessAccountNeeded = new HashMap<>(); // 20
        businessAccountNeeded.put("de", "Sie wollen ein Firmenkonto anlegen? Dafür brauche ich die Firmen-Bank-ID.");
        businessAccountNeeded.put("en", "You want to open a business account? I need the company bank ID.");
        getNpc().setBusinessAccountNeeded(businessAccountNeeded);

        HashMap<String, String> whereToFindInfo = new HashMap<>(); // 21
        whereToFindInfo.put("de", "Diese finden Sie im Firmen-GUI im Bereich 'Info'.");
        whereToFindInfo.put("en", "You can find it in the company GUI under 'Info'.");
        getNpc().setWhereToFindInfo(whereToFindInfo);

        HashMap<String, String> openBusinessAccount = new HashMap<>(); // 22
        openBusinessAccount.put("de", "Firmenkonto anlegen");
        openBusinessAccount.put("en", "Open business account");
        getNpc().setOpenBusinessAccount(openBusinessAccount);

        HashMap<String, String> enterBankId = new HashMap<>(); // 23
        enterBankId.put("de", "Geben Sie die Firmen-Bank-ID ein:");
        enterBankId.put("en", "Enter the company bank ID:");
        getNpc().setEnterBankId(enterBankId);

        HashMap<String, String> thanksForInfo = new HashMap<>(); // 24
        thanksForInfo.put("de", "Vielen Dank für die Info. Ich komme nochmal auf Sie zu.");
        thanksForInfo.put("en", "Thanks for the info. I'll get back to you.");
        getNpc().setThanksForInfo(thanksForInfo);

        HashMap<String, String> startCompany = new HashMap<>(); // 25
        startCompany.put("de", "Sie wollen eine Firma gründen? Das kostet Sie %s (Cash).");
        startCompany.put("en", "You want to start a company? That will cost you %s (cash).");
        getNpc().setStartCompany(startCompany);

        HashMap<String, String> notInterested = new HashMap<>(); // 26
        notInterested.put("de", "Nein, danke. Ich hätte da noch eine andere Bitte.");
        notInterested.put("en", "No, thanks. I have another request.");
        getNpc().setNotInterested(notInterested);

        HashMap<String, String> congratsOwner = new HashMap<>(); // 27
        congratsOwner.put("de", "Herzlichen Glückwunsch. Sie sind nun Eigentümer der Firma %s.");
        congratsOwner.put("en", "Congratulations. You are now the owner of the company %s.");
        getNpc().setCongratsOwner(congratsOwner);

        HashMap<String, String> canIHelpElse = new HashMap<>(); // 28
        canIHelpElse.put("de", "Kann ich Ihnen sonst noch helfen?");
        canIHelpElse.put("en", "Can I help you with anything else?");
        getNpc().setCanIHelpElse(canIHelpElse);

        HashMap<String, String> yes = new HashMap<>(); // 29
        yes.put("de", "Ja.");
        yes.put("en", "Yes.");
        getNpc().setYes(yes);

        HashMap<String, String> noGoodbye = new HashMap<>(); // 30
        noGoodbye.put("de", "Nein. Auf Wiedersehen.");
        noGoodbye.put("en", "No. Goodbye.");
        getNpc().setNoGoodbye(noGoodbye);

        HashMap<String, String> noCompany = new HashMap<>(); // 31
        noCompany.put("de", "Einen Moment bitte … Sie besitzen leider keine Firma.");
        noCompany.put("en", "One moment please... Unfortunately, you do not own a company.");
        getNpc().setNoCompany(noCompany);

        HashMap<String, String> needToEstablish = new HashMap<>(); // 32
        needToEstablish.put("de", "Sie müssen erst eine gründen.");
        needToEstablish.put("en", "You must first establish one.");
        getNpc().setNeedToEstablish(needToEstablish);

        HashMap<String, String> wantToStartCompany = new HashMap<>(); // 33
        wantToStartCompany.put("de", "Dann möchte ich eine Firma gründen.");
        wantToStartCompany.put("en", "Then I would like to start a company.");
        getNpc().setWantToStartCompany(wantToStartCompany);

        HashMap<String, String> okOtherTopic = new HashMap<>(); // 34
        okOtherTopic.put("de", "Okay. Anderes Thema.");
        okOtherTopic.put("en", "Okay. Another topic.");
        getNpc().setOkOtherTopic(okOtherTopic);

        HashMap<String, String> okGoodbye = new HashMap<>(); // 35
        okGoodbye.put("de", "Okay. Auf Wiedersehen.");
        okGoodbye.put("en", "Okay. Goodbye.");
        getNpc().setOkGoodbye(okGoodbye);

        HashMap<String, String> whatToNameCompany = new HashMap<>(); // 36
        whatToNameCompany.put("de", "Vielen Dank. Wie wollen Sie Ihre neue Firma nennen?");
        whatToNameCompany.put("en", "Thank you. What do you want to name your new company?");
        getNpc().setWhatToNameCompany(whatToNameCompany);

        HashMap<String, String> factoryName = new HashMap<>(); // 37
        factoryName.put("de", "Business-Name");
        factoryName.put("en", "Business name");
        getNpc().setBusinessName(factoryName);

        HashMap<String, String> giveCompanyName = new HashMap<>(); // 38
        giveCompanyName.put("de", "Vergebe der Firma einen Namen");
        giveCompanyName.put("en", "Give the company a name");
        getNpc().setGiveCompanyName(giveCompanyName);

        HashMap<String, String> nameAlreadyExists = new HashMap<>(); // 39
        nameAlreadyExists.put("de", "Business-Name existiert bereits");
        nameAlreadyExists.put("en", "Business name already exists");
        getNpc().setNameAlreadyExists(nameAlreadyExists);

        HashMap<String, String> errorSQL = new HashMap<>(); // 40
        errorSQL.put("de", "ERROR - SQL");
        errorSQL.put("en", "ERROR - SQL");
        getNpc().setErrorSQL(errorSQL);

        HashMap<String, String> cannotSaveBusiness = new HashMap<>(); // 41
        cannotSaveBusiness.put("de", "Business kann nicht gespeichert werden!");
        cannotSaveBusiness.put("en", "Business cannot be saved!");
        getNpc().setCannotSaveBusiness(cannotSaveBusiness);

        HashMap<String, String> businessAccountSetup = new HashMap<>(); // 42
        businessAccountSetup.put("de", "Okay. Ihr Firmenkonto wurde erfolgreich eingerichtet.");
        businessAccountSetup.put("en", "Okay. Your business account has been successfully set up.");
        getNpc().setBusinessAccountSetup(businessAccountSetup);

        HashMap<String, String> adminHelp = new HashMap<>(); // 43
        adminHelp.put("de", "Bei Fragen, wenden Sie sich bitte an einen Admin.");
        adminHelp.put("en", "If you have any questions, please contact an admin.");
        getNpc().setAdminHelp(adminHelp);

        HashMap<String, String> ok = new HashMap<>(); // 44
        ok.put("de", "OK.");
        ok.put("en", "OK.");
        getNpc().setOk(ok);

        HashMap<String, String> systemErrorCompany = new HashMap<>(); // 45
        systemErrorCompany.put("de", "Leider konnte ich Ihre neue Firma wegen eines Systemfehlers nicht anlegen.");
        systemErrorCompany.put("en", "Unfortunately, I could not create your new company due to a system error.");
        getNpc().setSystemErrorCompany(systemErrorCompany);

        HashMap<String, String> cannotFindCompany = new HashMap<>(); // 46
        cannotFindCompany.put("de", "Leider kann ich die Firma nicht unter der angegebenen ID finden.");
        cannotFindCompany.put("en", "Unfortunately, I cannot find the company under the given ID.");
        getNpc().setCannotFindCompany(cannotFindCompany);

        HashMap<String, String> tryAgain = new HashMap<>(); // 47
        tryAgain.put("de", "Ich versuche es nochmal.");
        tryAgain.put("en", "I'll try again.");
        getNpc().setTryAgain(tryAgain);

        HashMap<String, String> tryAgainLater = new HashMap<>(); // 48
        tryAgainLater.put("de", "Versuche es später nochmal.");
        tryAgainLater.put("en", "Try again later.");
        getNpc().setTryAgainLater(tryAgainLater);

        HashMap<String, String> askForID = new HashMap<>(); // 49
        askForID.put("de", "OK. Wie lautet die ID?");
        askForID.put("en", "OK. What is the ID?");
        getNpc().setAskForID(askForID);

        HashMap<String, String> confirmBusinessAccount = new HashMap<>(); // 50
        confirmBusinessAccount.put("de", "Sie wollen ein Firmenkonto für die Firma %s erstellen?");
        confirmBusinessAccount.put("en", "Do you want to create a business account for the company %s?");
        getNpc().setConfirmBusinessAccount(confirmBusinessAccount);

        HashMap<String, String> bankAccountCreated = new HashMap<>(); // 51
        bankAccountCreated.put("de", "Ok. Ihr Firmenkonto wurde erfolgreich eingerichtet.");
        bankAccountCreated.put("en", "Ok. Your business account has been successfully set up.");
        getNpc().setBankAccountCreated(bankAccountCreated);

        HashMap<String, String> factoryExists = new HashMap<>(); // 52
        factoryExists.put("de", "Business Name existiert");
        factoryExists.put("en", "Business name already exists");
        getNpc().setBusinessExists(factoryExists);

        HashMap<String, String> helpNeeded = new HashMap<>(); // 53
        helpNeeded.put("de", "Kann ich sonst noch helfen?");
        helpNeeded.put("en", "Can I help with anything else?");
        getNpc().setHelpNeeded(helpNeeded);

        HashMap<String, String> idNotFound = new HashMap<>(); // 54
        idNotFound.put("de", "Leider kann ich die Firma nicht unter der angegebenen ID finden.");
        idNotFound.put("en", "Unfortunately, I cannot find the company under the given ID.");
        getNpc().setIDNotFound(idNotFound);

        HashMap<String, String> sqlError = new HashMap<>(); // 55
        sqlError.put("de", "ERROR - SQL");
        sqlError.put("en", "ERROR - SQL");
        getNpc().setSQL_Error(sqlError);

        HashMap<String, String> next = new HashMap<>(); // 56
        next.put("de", "[Weiter]");
        next.put("en", "[Next]");
        getNpc().setNext(next);

        HashMap<String, String> helloCreateCompany = new HashMap<>(); // 1
        helloCreateCompany.put("de", "Hallo, ich möchte eine Firma gründen.");
        helloCreateCompany.put("en", "Hello, I would like to start a company.");
        getNpc().setHelloCreateCompany(helloCreateCompany);

        HashMap<String, String> pay = new HashMap<>(); // 2
        pay.put("de", "[Bezahlen]");
        pay.put("en", "[Pay]");
        getNpc().setPay(pay);

        HashMap<String, String> shareID = new HashMap<>(); // 3
        shareID.put("de", "[ID mitteilen]");
        shareID.put("en", "[Share ID]");
        getNpc().setShareID(shareID);

        HashMap<String, String> assignName = new HashMap<>(); // 4
        assignName.put("de", "[Name vergeben]");
        assignName.put("en", "[Assign name]");
        getNpc().setAssignName(assignName);

        HashMap<String, String> areYouSure = new HashMap<>(); // 5
        areYouSure.put("de", "Sind Sie sicher?");
        areYouSure.put("en", "Are you sure?");
        getNpc().setAreYouSure(areYouSure);

        HashMap<String, String> noTryAgainLater = new HashMap<>();
        noTryAgainLater.put("de", "Nein. Ich versuche es später nochmal.");
        noTryAgainLater.put("en", "No. I will try again later.");
        getNpc().setNoTryAgainLater(noTryAgainLater);
        
        HashMap<String, String> comingSoon = new HashMap<>();
        comingSoon.put("de", "Diese Funktion steht bald zur Verfügung!");
        comingSoon.put("de", "Coming Soon!");
        getNpc().setComingSoon(comingSoon);

    }

    public Model3DPlaceLang.RadialPlaceClass getRadialPlace() {
        return RadialPlace;
    }

    public Status getStatus() {
        return status;
    }

    public Command getCommand() {
        return command;
    }

    public Npc getNpc() {
        return npc;
    }

    public Other getOther() {
        return other;
    }

    public GUI getGui() {
        return gui;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public class Command {

        private HashMap<String, String> AdminGivecash, AdminTakecash, AdminSetcash, AdminTakebank, AdminGivebank, AdminSetbank,
                CreateBank, Use, CreateBank_HasAccount;

        public Command() {
            this.AdminGivecash = new HashMap<>();
            this.AdminTakecash = new HashMap<>();
            this.AdminSetcash = new HashMap<>();
            this.AdminTakebank = new HashMap<>();
            this.AdminGivebank = new HashMap<>();
            this.AdminSetbank = new HashMap<>();
            this.CreateBank = new HashMap<>();
            this.Use = new HashMap<>();
            this.CreateBank_HasAccount = new HashMap<>();
        }

        public String getCreateBank(String lang) {
            return CreateBank.get(lang) != null ? CreateBank.get(lang) : CreateBank.get(defaultLanguage);
        }

        public String getUse(String lang) {
            return Use.get(lang) != null ? Use.get(lang) : Use.get(defaultLanguage);
        }

        public String getCreateBank_HasAccount(String lang) {
            return CreateBank_HasAccount.get(lang) != null ? CreateBank_HasAccount.get(lang) : CreateBank_HasAccount.get(defaultLanguage);
        }

        public HashMap<String, String> getUse() {
            return Use;
        }

        public HashMap<String, String> getCreateBank() {
            return CreateBank;
        }

        public HashMap<String, String> getCreateBank_HasAccount() {
            return CreateBank_HasAccount;
        }

        public void setUse(HashMap<String, String> Use) {
            this.Use = Use;
        }

        public void setCreateBank(HashMap<String, String> CreateBank) {
            this.CreateBank = CreateBank;
        }

        public void setCreateBank_HasAccount(HashMap<String, String> CreateBank_HasAccount) {
            this.CreateBank_HasAccount = CreateBank_HasAccount;
        }

        public String getAdminGivecash(String lang) {
            return AdminGivecash.get(lang) != null ? AdminGivecash.get(lang) : AdminGivecash.get(defaultLanguage);
        }

        public String getAdminTakecash(String lang) {
            return AdminTakecash.get(lang) != null ? AdminTakecash.get(lang) : AdminTakecash.get(defaultLanguage);
        }

        public String getAdminSetcash(String lang) {
            return AdminSetcash.get(lang) != null ? AdminSetcash.get(lang) : AdminSetcash.get(defaultLanguage);
        }

        public String getAdminTakebank(String lang) {
            return AdminTakebank.get(lang) != null ? AdminTakebank.get(lang) : AdminTakebank.get(defaultLanguage);
        }

        public String getAdminGivebank(String lang) {
            return AdminGivebank.get(lang) != null ? AdminGivebank.get(lang) : AdminGivebank.get(defaultLanguage);
        }

        public String getAdminSetbank(String lang) {
            return AdminSetbank.get(lang) != null ? AdminSetbank.get(lang) : AdminSetbank.get(defaultLanguage);
        }

        public HashMap<String, String> getAdminGivebank() {
            return AdminGivebank;
        }

        public HashMap<String, String> getAdminGivecash() {
            return AdminGivecash;
        }

        public HashMap<String, String> getAdminSetbank() {
            return AdminSetbank;
        }

        public HashMap<String, String> getAdminSetcash() {
            return AdminSetcash;
        }

        public HashMap<String, String> getAdminTakebank() {
            return AdminTakebank;
        }

        public HashMap<String, String> getAdminTakecash() {
            return AdminTakecash;
        }

        public void setAdminGivebank(HashMap<String, String> AdminGivebank) {
            this.AdminGivebank = AdminGivebank;
        }

        public void setAdminGivecash(HashMap<String, String> AdminGivecash) {
            this.AdminGivecash = AdminGivecash;
        }

        public void setAdminSetbank(HashMap<String, String> AdminSetbank) {
            this.AdminSetbank = AdminSetbank;
        }

        public void setAdminSetcash(HashMap<String, String> AdminSetcash) {
            this.AdminSetcash = AdminSetcash;
        }

        public void setAdminTakebank(HashMap<String, String> AdminTakebank) {
            this.AdminTakebank = AdminTakebank;
        }

        public void setAdminTakecash(HashMap<String, String> AdminTakecash) {
            this.AdminTakecash = AdminTakecash;
        }
    }

    public class Status {

        private HashMap<String, String> PlayerNotConnected, PlayerNotExist, LostMoney, MoneyMustBeNumber,
                AdminGiveMoney, TransferCancel, PlayerNotAnounthMoney, EmptyAmount, SendCashToSelf,
                OtherPlayerNotAnounthMoney, PlayerHasNoAccount, OtherPlayerHasNoAccount, AmountBigger,
                PlayerDeath_KillerAdd, PlayerDeath_MoneyEmpty, Sign_Misspelled, Sign_OK, Sign_Distroy_Fail,
                PlayerDeath_MoneyEmpty_Player, SendMoneyOK;

        public Status() {
            this.AdminGiveMoney = new HashMap<>();
            this.LostMoney = new HashMap<>();
            this.MoneyMustBeNumber = new HashMap<>();
            this.PlayerNotConnected = new HashMap<>();
            this.PlayerNotExist = new HashMap<>();
            this.TransferCancel = new HashMap<>();
            this.PlayerNotAnounthMoney = new HashMap<>();
            this.EmptyAmount = new HashMap<>();
            this.OtherPlayerNotAnounthMoney = new HashMap<>();
            this.AmountBigger = new HashMap<>();
            this.PlayerDeath_KillerAdd = new HashMap<>();
            this.PlayerDeath_MoneyEmpty = new HashMap<>();
            this.Sign_Misspelled = new HashMap<>();
            this.Sign_OK = new HashMap<>();
            this.Sign_Distroy_Fail = new HashMap<>();
            this.PlayerDeath_MoneyEmpty_Player = new HashMap<>();
            this.SendMoneyOK = new HashMap<>();
        }

        public HashMap<String, String> getSendMoneyOK() {
            return SendMoneyOK;
        }

        public void setSendMoneyOK(HashMap<String, String> SendMoneyOK) {
            this.SendMoneyOK = SendMoneyOK;
        }

        public String getSendMoneyOK(String lang) {
            return SendMoneyOK.get(lang) != null ? SendMoneyOK.get(lang) : SendMoneyOK.get(defaultLanguage);
        }

        public HashMap<String, String> getPlayerDeath_MoneyEmpty_Player() {
            return PlayerDeath_MoneyEmpty_Player;
        }

        public void setPlayerDeath_MoneyEmpty_Player(HashMap<String, String> PlayerDeath_MoneyEmpty_Player) {
            this.PlayerDeath_MoneyEmpty_Player = PlayerDeath_MoneyEmpty_Player;
        }

        public String getPlayerDeath_MoneyEmpty_Player(String lang) {
            return PlayerDeath_MoneyEmpty_Player.get(lang) != null ? PlayerDeath_MoneyEmpty_Player.get(lang) : PlayerDeath_MoneyEmpty_Player.get(defaultLanguage);
        }

        public String getPlayerDeath_KillerAdd(String lang) {
            return PlayerDeath_KillerAdd.get(lang) != null ? PlayerDeath_KillerAdd.get(lang) : PlayerDeath_KillerAdd.get(defaultLanguage);
        }

        public String getPlayerDeath_MoneyEmpty(String lang) {
            return PlayerDeath_MoneyEmpty.get(lang) != null ? PlayerDeath_MoneyEmpty.get(lang) : PlayerDeath_MoneyEmpty.get(defaultLanguage);
        }

        public String getSign_Misspelled(String lang) {
            return Sign_Misspelled.get(lang) != null ? Sign_Misspelled.get(lang) : Sign_Misspelled.get(defaultLanguage);
        }

        public String getSign_OK(String lang) {
            return Sign_OK.get(lang) != null ? Sign_OK.get(lang) : Sign_OK.get(defaultLanguage);
        }

        public String getSign_Distroy_Fail(String lang) {
            return Sign_Distroy_Fail.get(lang) != null ? Sign_Distroy_Fail.get(lang) : Sign_Distroy_Fail.get(defaultLanguage);
        }

        public HashMap<String, String> getPlayerDeath_KillerAdd() {
            return PlayerDeath_KillerAdd;
        }

        public HashMap<String, String> getPlayerDeath_MoneyEmpty() {
            return PlayerDeath_MoneyEmpty;
        }

        public HashMap<String, String> getSign_Distroy_Fail() {
            return Sign_Distroy_Fail;
        }

        public HashMap<String, String> getSign_Misspelled() {
            return Sign_Misspelled;
        }

        public HashMap<String, String> getSign_OK() {
            return Sign_OK;
        }

        public void setPlayerDeath_KillerAdd(HashMap<String, String> PlayerDeath_KillerAdd) {
            this.PlayerDeath_KillerAdd = PlayerDeath_KillerAdd;
        }

        public void setPlayerDeath_MoneyEmpty(HashMap<String, String> PlayerDeath_MoneyEmpty) {
            this.PlayerDeath_MoneyEmpty = PlayerDeath_MoneyEmpty;
        }

        public void setSign_Distroy_Fail(HashMap<String, String> Sign_Distroy_Fail) {
            this.Sign_Distroy_Fail = Sign_Distroy_Fail;
        }

        public void setSign_Misspelled(HashMap<String, String> Sign_Misspelled) {
            this.Sign_Misspelled = Sign_Misspelled;
        }

        public void setSign_OK(HashMap<String, String> Sign_OK) {
            this.Sign_OK = Sign_OK;
        }

        public String getAmountBigger(String lang) {
            return AmountBigger.get(lang) != null ? AmountBigger.get(lang) : AmountBigger.get(defaultLanguage);
        }

        public HashMap<String, String> getAmountBigger() {
            return AmountBigger;
        }

        public void setAmountBigger(HashMap<String, String> AmountBigger) {
            this.AmountBigger = AmountBigger;
        }

        public String getPlayerHasNoAccount(String lang) {
            return PlayerHasNoAccount.get(lang) != null ? PlayerHasNoAccount.get(lang) : PlayerHasNoAccount.get(defaultLanguage);
        }

        public String getOtherPlayerHasNoAccount(String lang) {
            return OtherPlayerHasNoAccount.get(lang) != null ? OtherPlayerHasNoAccount.get(lang) : OtherPlayerHasNoAccount.get(defaultLanguage);
        }

        public HashMap<String, String> getOtherPlayerHasNoAccount() {
            return OtherPlayerHasNoAccount;
        }

        public HashMap<String, String> getPlayerHasNoAccount() {
            return PlayerHasNoAccount;
        }

        public void setOtherPlayerHasNoAccount(HashMap<String, String> OtherPlayerHasNoAccount) {
            this.OtherPlayerHasNoAccount = OtherPlayerHasNoAccount;
        }

        public void setPlayerHasNoAccount(HashMap<String, String> PlayerHasNoAccount) {
            this.PlayerHasNoAccount = PlayerHasNoAccount;
        }

        public String getOtherPlayerNotAnounthMoney(String lang) {
            return OtherPlayerNotAnounthMoney.get(lang) != null ? OtherPlayerNotAnounthMoney.get(lang) : OtherPlayerNotAnounthMoney.get(defaultLanguage);
        }

        public HashMap<String, String> getOtherPlayerNotAnounthMoney() {
            return OtherPlayerNotAnounthMoney;
        }

        public void setOtherPlayerNotAnounthMoney(HashMap<String, String> OtherPlayerNotAnounthMoney) {
            this.OtherPlayerNotAnounthMoney = OtherPlayerNotAnounthMoney;
        }

        public String getSendCashToSelf(String lang) {
            return SendCashToSelf.get(lang) != null ? SendCashToSelf.get(lang) : SendCashToSelf.get(defaultLanguage);
        }

        public HashMap<String, String> getSendCashToSelf() {
            return SendCashToSelf;
        }

        public void setSendCashToSelf(HashMap<String, String> SendCashToSelf) {
            this.SendCashToSelf = SendCashToSelf;
        }

        public String getTransferCancel(String lang) {
            return TransferCancel.get(lang) != null ? TransferCancel.get(lang) : TransferCancel.get(defaultLanguage);
        }

        public String getPlayerNotAnounthMoney(String lang) {
            return PlayerNotAnounthMoney.get(lang) != null ? PlayerNotAnounthMoney.get(lang) : PlayerNotAnounthMoney.get(defaultLanguage);
        }

        public String getEmptyAmount(String lang) {
            return EmptyAmount.get(lang) != null ? EmptyAmount.get(lang) : EmptyAmount.get(defaultLanguage);
        }

        public HashMap<String, String> getEmptyAmount() {
            return EmptyAmount;
        }

        public HashMap<String, String> getPlayerNotAnounthMoney() {
            return PlayerNotAnounthMoney;
        }

        public HashMap<String, String> getTransferCancel() {
            return TransferCancel;
        }

        public String getAdminGiveMoney(String lang) {
            return AdminGiveMoney.get(lang) != null ? AdminGiveMoney.get(lang) : AdminGiveMoney.get(defaultLanguage);
        }

        public String getLostMoney(String lang) {
            return LostMoney.get(lang) != null ? LostMoney.get(lang) : LostMoney.get(defaultLanguage);
        }

        public String getMoneyMustBeNumber(String lang) {
            return MoneyMustBeNumber.get(lang) != null ? MoneyMustBeNumber.get(lang) : MoneyMustBeNumber.get(defaultLanguage);
        }

        public String getPlayerNotConnected(String lang) {
            return PlayerNotConnected.get(lang) != null ? PlayerNotConnected.get(lang) : PlayerNotConnected.get(defaultLanguage);
        }

        public String getPlayerNotExist(String lang) {
            return PlayerNotExist.get(lang) != null ? PlayerNotExist.get(lang) : PlayerNotExist.get(defaultLanguage);
        }

        public HashMap<String, String> getAdminGiveMoney() {
            return AdminGiveMoney;
        }

        public HashMap<String, String> getLostMoney() {
            return LostMoney;
        }

        public HashMap<String, String> getMoneyMustBeNumber() {
            return MoneyMustBeNumber;
        }

        public HashMap<String, String> getPlayerNotConnected() {
            return PlayerNotConnected;
        }

        public HashMap<String, String> getPlayerNotExist() {
            return PlayerNotExist;
        }

        public void setAdminGiveMoney(HashMap<String, String> AdminGiveMoney) {
            this.AdminGiveMoney = AdminGiveMoney;
        }

        public void setLostMoney(HashMap<String, String> LostMoney) {
            this.LostMoney = LostMoney;
        }

        public void setMoneyMustBeNumber(HashMap<String, String> MoneyMustBeNumber) {
            this.MoneyMustBeNumber = MoneyMustBeNumber;
        }

        public void setPlayerNotConnected(HashMap<String, String> PlayerNotConnected) {
            this.PlayerNotConnected = PlayerNotConnected;
        }

        public void setPlayerNotExist(HashMap<String, String> PlayerNotExist) {
            this.PlayerNotExist = PlayerNotExist;
        }

        public void setEmptyAmount(HashMap<String, String> EmptyAmount) {
            this.EmptyAmount = EmptyAmount;
        }

        public void setPlayerNotAnounthMoney(HashMap<String, String> PlayerNotAnounthMoney) {
            this.PlayerNotAnounthMoney = PlayerNotAnounthMoney;
        }

        public void setTransferCancel(HashMap<String, String> TransferCancel) {
            this.TransferCancel = TransferCancel;
        }

    }

    public class Other {

        private HashMap<String, String> NoPermission;

        public Other() {
            this.NoPermission = new HashMap<>();
        }

        public String getNoPermission(String lang) {
            return NoPermission.get(lang) != null ? NoPermission.get(lang) : NoPermission.get(defaultLanguage);
        }

        public HashMap getNoPermission() {
            return NoPermission;
        }

        public void setNoPermission(HashMap NoPermission) {
            this.NoPermission = NoPermission;
        }

    }

    public class GUI {

        private HashMap<String, String> Send, Cancel, SendCashGUI_Title, SendCashGUI_BodyText, GUI_Amount, SendCashGUI_Player,
                NoAccount, CashInOutGUI_In_Message, CashInOutGUI_Out_Message, YourCash, YourBank, SelectCashInOutGUI_Message, SelectCashInOutGUI_Titel,
                PlaceAtmGui_UpDown_Pos, PlaceAtmGui_LeftRight_Pos, PlaceAtmGui_PageUpDown, PlaceAtmGui_UpDown_Rot, PlaceAtmGui_LeftRight_Rot,
                PlaceAtmGui_Plus_Pos, PlaceAtmGui_Minus_Pos, PlaceAtmGui_Plus_Rot, PlaceAtmGui_Minus_Rot, PlaceAtmGui_Enter, PlaceAtmGui_Multiply,
                PlaceAtmGui_Amount, PlaceAtmGui_Mode, PlaceAtmGui_Escape, Transfer, TransferText, Cash, Accounts, OwnAccount, OtherAccount,
                Username, AccountInfoText, Statements, Permissions, Money, MoneyText, Members, MemberText1, MemberText2, Balance, MinBalance, Owner;

        public GUI() {
            this.Cancel = new HashMap<>();
            this.Send = new HashMap<>();
            this.SendCashGUI_Title = new HashMap<>();
            this.GUI_Amount = new HashMap<>();
            this.SendCashGUI_BodyText = new HashMap<>();
            this.SendCashGUI_Player = new HashMap<>();
            this.NoAccount = new HashMap<>();
            this.CashInOutGUI_In_Message = new HashMap<>();
            this.CashInOutGUI_Out_Message = new HashMap<>();
            this.YourCash = new HashMap<>();
            this.YourBank = new HashMap<>();
            this.SelectCashInOutGUI_Message = new HashMap<>();
            this.SelectCashInOutGUI_Titel = new HashMap<>();
            this.PlaceAtmGui_Amount = new HashMap<>();
            this.PlaceAtmGui_Enter = new HashMap<>();
            this.PlaceAtmGui_Escape = new HashMap<>();
            this.PlaceAtmGui_LeftRight_Pos = new HashMap<>();
            this.PlaceAtmGui_LeftRight_Rot = new HashMap<>();
            this.PlaceAtmGui_Minus_Pos = new HashMap<>();
            this.PlaceAtmGui_Minus_Rot = new HashMap<>();
            this.PlaceAtmGui_Mode = new HashMap<>();
            this.PlaceAtmGui_Multiply = new HashMap<>();
            this.PlaceAtmGui_PageUpDown = new HashMap<>();
            this.PlaceAtmGui_Plus_Pos = new HashMap<>();
            this.PlaceAtmGui_Plus_Rot = new HashMap<>();
            this.PlaceAtmGui_UpDown_Pos = new HashMap<>();
            this.PlaceAtmGui_UpDown_Rot = new HashMap<>();
            Transfer = new HashMap<>();
            TransferText = new HashMap<>();
            Cash = new HashMap<>();
            Accounts = new HashMap<>();
            OwnAccount = new HashMap<>();
            OtherAccount = new HashMap<>();
            Username = new HashMap<>();
            AccountInfoText = new HashMap<>();
            Statements = new HashMap<>();
            Permissions = new HashMap<>();
            Money = new HashMap<>();
            MoneyText = new HashMap<>();
            Members = new HashMap<>();
            MemberText1 = new HashMap<>();
            MemberText2 = new HashMap<>();
            Balance = new HashMap<>();
            MinBalance = new HashMap<>();
            Owner = new HashMap<>();

        }

        public String getBalance(String lang) {
            return Balance.get(lang) != null ? Balance.get(lang) : Balance.get(defaultLanguage);
        }

        public String getMinBalance(String lang) {
            return MinBalance.get(lang) != null ? MinBalance.get(lang) : MinBalance.get(defaultLanguage);
        }

        public String getOwner(String lang) {
            return Owner.get(lang) != null ? Owner.get(lang) : Owner.get(defaultLanguage);
        }

        public String getMoney(String lang) {
            return Money.get(lang) != null ? Money.get(lang) : Money.get(defaultLanguage);
        }

        public String getMoneyText(String lang) {
            return MoneyText.get(lang) != null ? MoneyText.get(lang) : MoneyText.get(defaultLanguage);
        }

        public String getMembers(String lang) {
            return Members.get(lang) != null ? Members.get(lang) : Members.get(defaultLanguage);
        }

        public String getMemberText1(String lang) {
            return MemberText1.get(lang) != null ? MemberText1.get(lang) : MemberText1.get(defaultLanguage);
        }

        public String getMemberText2(String lang) {
            return MemberText2.get(lang) != null ? MemberText2.get(lang) : MemberText2.get(defaultLanguage);
        }

        public String getUsername(String lang) {
            return Username.get(lang) != null ? Username.get(lang) : Username.get(defaultLanguage);
        }

        public String getAccountInfoText(String lang) {
            return AccountInfoText.get(lang) != null ? AccountInfoText.get(lang) : AccountInfoText.get(defaultLanguage);
        }

        public String getStatements(String lang) {
            return Statements.get(lang) != null ? Statements.get(lang) : Statements.get(defaultLanguage);
        }

        public String getPermissions(String lang) {
            return Permissions.get(lang) != null ? Permissions.get(lang) : Permissions.get(defaultLanguage);
        }

        public String getOwnAccount(String lang) {
            return OwnAccount.get(lang) != null ? OwnAccount.get(lang) : OwnAccount.get(defaultLanguage);
        }

        public String getOtherAccount(String lang) {
            return OtherAccount.get(lang) != null ? OtherAccount.get(lang) : OtherAccount.get(defaultLanguage);
        }

        public String getAccounts(String lang) {
            return Accounts.get(lang) != null ? Accounts.get(lang) : Accounts.get(defaultLanguage);
        }

        public String getTransfer(String lang) {
            return Transfer.get(lang) != null ? Transfer.get(lang) : Transfer.get(defaultLanguage);
        }

        public String getTransferText(String lang) {
            return TransferText.get(lang) != null ? TransferText.get(lang) : TransferText.get(defaultLanguage);
        }

        public String getCash(String lang) {
            return Cash.get(lang) != null ? Cash.get(lang) : Cash.get(defaultLanguage);
        }

        public HashMap<String, String> getAccountInfoText() {
            return AccountInfoText;
        }

        public HashMap<String, String> getAccounts() {
            return Accounts;
        }

        public HashMap<String, String> getBalance() {
            return Balance;
        }

        public HashMap<String, String> getCash() {
            return Cash;
        }

        public HashMap<String, String> getMembers() {
            return Members;
        }

        public HashMap<String, String> getMemberText1() {
            return MemberText1;
        }

        public HashMap<String, String> getMemberText2() {
            return MemberText2;
        }

        public HashMap<String, String> getMinBalance() {
            return MinBalance;
        }

        public HashMap<String, String> getMoney() {
            return Money;
        }

        public HashMap<String, String> getMoneyText() {
            return MoneyText;
        }

        public HashMap<String, String> getOtherAccount() {
            return OtherAccount;
        }

        public HashMap<String, String> getOwnAccount() {
            return OwnAccount;
        }

        public HashMap<String, String> getOwner() {
            return Owner;
        }

        public HashMap<String, String> getPermissions() {
            return Permissions;
        }

        public HashMap<String, String> getStatements() {
            return Statements;
        }

        public HashMap<String, String> getTransfer() {
            return Transfer;
        }

        public HashMap<String, String> getTransferText() {
            return TransferText;
        }

        public HashMap<String, String> getUsername() {
            return Username;
        }

        public void setAccountInfoText(HashMap<String, String> AccountInfoText) {
            this.AccountInfoText = AccountInfoText;
        }

        public void setAccounts(HashMap<String, String> Accounts) {
            this.Accounts = Accounts;
        }

        public void setBalance(HashMap<String, String> Balance) {
            this.Balance = Balance;
        }

        public void setCash(HashMap<String, String> Cash) {
            this.Cash = Cash;
        }

        public void setMembers(HashMap<String, String> Members) {
            this.Members = Members;
        }

        public void setMemberText1(HashMap<String, String> MemberText1) {
            this.MemberText1 = MemberText1;
        }

        public void setMemberText2(HashMap<String, String> MemberText2) {
            this.MemberText2 = MemberText2;
        }

        public void setMinBalance(HashMap<String, String> MinBalance) {
            this.MinBalance = MinBalance;
        }

        public void setMoney(HashMap<String, String> Money) {
            this.Money = Money;
        }

        public void setMoneyText(HashMap<String, String> MoneyText) {
            this.MoneyText = MoneyText;
        }

        public void setOtherAccount(HashMap<String, String> OtherAccount) {
            this.OtherAccount = OtherAccount;
        }

        public void setOwnAccount(HashMap<String, String> OwnAccount) {
            this.OwnAccount = OwnAccount;
        }

        public void setOwner(HashMap<String, String> Owner) {
            this.Owner = Owner;
        }

        public void setPermissions(HashMap<String, String> Permissions) {
            this.Permissions = Permissions;
        }

        public void setStatements(HashMap<String, String> Statements) {
            this.Statements = Statements;
        }

        public void setTransfer(HashMap<String, String> Transfer) {
            this.Transfer = Transfer;
        }

        public void setTransferText(HashMap<String, String> TransferText) {
            this.TransferText = TransferText;
        }

        public void setUsername(HashMap<String, String> Username) {
            this.Username = Username;
        }

        public String getPlaceAtmGui_UpDown_Pos(String lang) {
            return PlaceAtmGui_UpDown_Pos.get(lang) != null ? PlaceAtmGui_UpDown_Pos.get(lang) : PlaceAtmGui_UpDown_Pos.get(defaultLanguage);
        }

        public String getPlaceAtmGui_UpDown_Rot(String lang) {
            return PlaceAtmGui_UpDown_Rot.get(lang) != null ? PlaceAtmGui_UpDown_Rot.get(lang) : PlaceAtmGui_UpDown_Rot.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Multiply(String lang) {
            return PlaceAtmGui_Multiply.get(lang) != null ? PlaceAtmGui_Multiply.get(lang) : PlaceAtmGui_Multiply.get(defaultLanguage);
        }

        public String getPlaceAtmGui_PageUpDown(String lang) {
            return PlaceAtmGui_PageUpDown.get(lang) != null ? PlaceAtmGui_PageUpDown.get(lang) : PlaceAtmGui_PageUpDown.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Plus_Pos(String lang) {
            return PlaceAtmGui_Plus_Pos.get(lang) != null ? PlaceAtmGui_Plus_Pos.get(lang) : PlaceAtmGui_Plus_Pos.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Plus_Rot(String lang) {
            return PlaceAtmGui_Plus_Rot.get(lang) != null ? PlaceAtmGui_Plus_Rot.get(lang) : PlaceAtmGui_Plus_Rot.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Amount(String lang) {
            return PlaceAtmGui_Amount.get(lang) != null ? PlaceAtmGui_Amount.get(lang) : PlaceAtmGui_Amount.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Enter(String lang) {
            return PlaceAtmGui_Enter.get(lang) != null ? PlaceAtmGui_Enter.get(lang) : PlaceAtmGui_Enter.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Escape(String lang) {
            return PlaceAtmGui_Escape.get(lang) != null ? PlaceAtmGui_Escape.get(lang) : PlaceAtmGui_Escape.get(defaultLanguage);
        }

        public String getPlaceAtmGui_LeftRight_Pos(String lang) {
            return PlaceAtmGui_LeftRight_Pos.get(lang) != null ? PlaceAtmGui_LeftRight_Pos.get(lang) : PlaceAtmGui_LeftRight_Pos.get(defaultLanguage);
        }

        public String getPlaceAtmGui_LeftRight_Rot(String lang) {
            return PlaceAtmGui_LeftRight_Rot.get(lang) != null ? PlaceAtmGui_LeftRight_Rot.get(lang) : PlaceAtmGui_LeftRight_Rot.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Minus_Pos(String lang) {
            return PlaceAtmGui_Minus_Pos.get(lang) != null ? PlaceAtmGui_Minus_Pos.get(lang) : PlaceAtmGui_Minus_Pos.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Minus_Rot(String lang) {
            return PlaceAtmGui_Minus_Rot.get(lang) != null ? PlaceAtmGui_Minus_Rot.get(lang) : PlaceAtmGui_Minus_Rot.get(defaultLanguage);
        }

        public String getPlaceAtmGui_Mode(String lang) {
            return PlaceAtmGui_Mode.get(lang) != null ? PlaceAtmGui_Mode.get(lang) : PlaceAtmGui_Mode.get(defaultLanguage);
        }

        public HashMap<String, String> getPlaceAtmGui_Amount() {
            return PlaceAtmGui_Amount;
        }

        public HashMap<String, String> getPlaceAtmGui_Enter() {
            return PlaceAtmGui_Enter;
        }

        public HashMap<String, String> getPlaceAtmGui_Escape() {
            return PlaceAtmGui_Escape;
        }

        public HashMap<String, String> getPlaceAtmGui_LeftRight_Pos() {
            return PlaceAtmGui_LeftRight_Pos;
        }

        public HashMap<String, String> getPlaceAtmGui_LeftRight_Rot() {
            return PlaceAtmGui_LeftRight_Rot;
        }

        public HashMap<String, String> getPlaceAtmGui_Minus_Pos() {
            return PlaceAtmGui_Minus_Pos;
        }

        public HashMap<String, String> getPlaceAtmGui_Minus_Rot() {
            return PlaceAtmGui_Minus_Rot;
        }

        public HashMap<String, String> getPlaceAtmGui_Mode() {
            return PlaceAtmGui_Mode;
        }

        public HashMap<String, String> getPlaceAtmGui_Multiply() {
            return PlaceAtmGui_Multiply;
        }

        public HashMap<String, String> getPlaceAtmGui_PageUpDown() {
            return PlaceAtmGui_PageUpDown;
        }

        public HashMap<String, String> getPlaceAtmGui_Plus_Pos() {
            return PlaceAtmGui_Plus_Pos;
        }

        public HashMap<String, String> getPlaceAtmGui_Plus_Rot() {
            return PlaceAtmGui_Plus_Rot;
        }

        public HashMap<String, String> getPlaceAtmGui_UpDown_Pos() {
            return PlaceAtmGui_UpDown_Pos;
        }

        public HashMap<String, String> getPlaceAtmGui_UpDown_Rot() {
            return PlaceAtmGui_UpDown_Rot;
        }

        public void setPlaceAtmGui_Amount(HashMap<String, String> PlaceAtmGui_Amount) {
            this.PlaceAtmGui_Amount = PlaceAtmGui_Amount;
        }

        public void setPlaceAtmGui_Enter(HashMap<String, String> PlaceAtmGui_Enter) {
            this.PlaceAtmGui_Enter = PlaceAtmGui_Enter;
        }

        public void setPlaceAtmGui_Escape(HashMap<String, String> PlaceAtmGui_Escape) {
            this.PlaceAtmGui_Escape = PlaceAtmGui_Escape;
        }

        public void setPlaceAtmGui_LeftRight_Pos(HashMap<String, String> PlaceAtmGui_LeftRight_Pos) {
            this.PlaceAtmGui_LeftRight_Pos = PlaceAtmGui_LeftRight_Pos;
        }

        public void setPlaceAtmGui_LeftRight_Rot(HashMap<String, String> PlaceAtmGui_LeftRight_Rot) {
            this.PlaceAtmGui_LeftRight_Rot = PlaceAtmGui_LeftRight_Rot;
        }

        public void setPlaceAtmGui_Minus_Pos(HashMap<String, String> PlaceAtmGui_Minus_Pos) {
            this.PlaceAtmGui_Minus_Pos = PlaceAtmGui_Minus_Pos;
        }

        public void setPlaceAtmGui_Minus_Rot(HashMap<String, String> PlaceAtmGui_Minus_Rot) {
            this.PlaceAtmGui_Minus_Rot = PlaceAtmGui_Minus_Rot;
        }

        public void setPlaceAtmGui_Mode(HashMap<String, String> PlaceAtmGui_Mode) {
            this.PlaceAtmGui_Mode = PlaceAtmGui_Mode;
        }

        public void setPlaceAtmGui_Multiply(HashMap<String, String> PlaceAtmGui_Multiply) {
            this.PlaceAtmGui_Multiply = PlaceAtmGui_Multiply;
        }

        public void setPlaceAtmGui_PageUpDown(HashMap<String, String> PlaceAtmGui_PageUpDown) {
            this.PlaceAtmGui_PageUpDown = PlaceAtmGui_PageUpDown;
        }

        public void setPlaceAtmGui_Plus_Rot(HashMap<String, String> PlaceAtmGui_Plus_Rot) {
            this.PlaceAtmGui_Plus_Rot = PlaceAtmGui_Plus_Rot;
        }

        public void setPlaceAtmGui_Plus_Pos(HashMap<String, String> PlaceAtmGui_Plus_Pos) {
            this.PlaceAtmGui_Plus_Pos = PlaceAtmGui_Plus_Pos;
        }

        public void setPlaceAtmGui_UpDown_Pos(HashMap<String, String> PlaceAtmGui_UpDown_Pos) {
            this.PlaceAtmGui_UpDown_Pos = PlaceAtmGui_UpDown_Pos;
        }

        public void setPlaceAtmGui_UpDown_Rot(HashMap<String, String> PlaceAtmGui_UpDown_Rot) {
            this.PlaceAtmGui_UpDown_Rot = PlaceAtmGui_UpDown_Rot;
        }

        public String getSelectCashInOutGUI_Titel(String lang) {
            return SelectCashInOutGUI_Titel.get(lang) != null ? SelectCashInOutGUI_Titel.get(lang) : SelectCashInOutGUI_Titel.get(defaultLanguage);
        }

        public HashMap<String, String> getSelectCashInOutGUI_Titel() {
            return SelectCashInOutGUI_Titel;
        }

        public void setSelectCashInOutGUI_Titel(HashMap<String, String> SelectCashInOutGUI_Titel) {
            this.SelectCashInOutGUI_Titel = SelectCashInOutGUI_Titel;
        }

        public String getSelectCashInOutGUI_Message(String lang) {
            return SelectCashInOutGUI_Message.get(lang) != null ? SelectCashInOutGUI_Message.get(lang) : SelectCashInOutGUI_Message.get(defaultLanguage);
        }

        public HashMap<String, String> getSelectCashInOutGUI_Message() {
            return SelectCashInOutGUI_Message;
        }

        public void setSelectCashInOutGUI_Message(HashMap<String, String> SelectCashInOutGUI_Message) {
            this.SelectCashInOutGUI_Message = SelectCashInOutGUI_Message;
        }

        public HashMap<String, String> getCashInOutGUI_In_Message() {
            return CashInOutGUI_In_Message;
        }

        public HashMap<String, String> getCashInOutGUI_Out_Message() {
            return CashInOutGUI_Out_Message;
        }

        public void setCashInOutGUI_In_Message(HashMap<String, String> CashInOutGUI_In_Message) {
            this.CashInOutGUI_In_Message = CashInOutGUI_In_Message;
        }

        public void setCashInOutGUI_Out_Message(HashMap<String, String> CashInOutGUI_Out_Message) {
            this.CashInOutGUI_Out_Message = CashInOutGUI_Out_Message;
        }

        public void setYourBank(HashMap<String, String> YourBank) {
            this.YourBank = YourBank;
        }

        public void setYourCash(HashMap<String, String> YourCash) {
            this.YourCash = YourCash;
        }

        public HashMap<String, String> getYourBank() {
            return YourBank;
        }

        public HashMap<String, String> getYourCash() {
            return YourCash;
        }

        public String getCashInOutGUI_In_Message(String lang) {
            return CashInOutGUI_In_Message.get(lang) != null ? CashInOutGUI_In_Message.get(lang) : CashInOutGUI_In_Message.get(defaultLanguage);
        }

        public String getCashInOutGUI_Out_Message(String lang) {
            return CashInOutGUI_Out_Message.get(lang) != null ? CashInOutGUI_Out_Message.get(lang) : CashInOutGUI_Out_Message.get(defaultLanguage);
        }

        public String getYourCash(String lang) {
            return YourCash.get(lang) != null ? YourCash.get(lang) : YourCash.get(defaultLanguage);
        }

        public String getYourBank(String lang) {
            return YourBank.get(lang) != null ? YourBank.get(lang) : YourBank.get(defaultLanguage);
        }

        public String getNoAccount(String lang) {
            return NoAccount.get(lang) != null ? NoAccount.get(lang) : NoAccount.get(defaultLanguage);
        }

        public HashMap<String, String> getNoAccount() {
            return NoAccount;
        }

        public void setNoAccount(HashMap<String, String> NoAccount) {
            this.NoAccount = NoAccount;
        }

        public String getSendCashGUI_BodyText(String lang) {
            return SendCashGUI_BodyText.get(lang) != null ? SendCashGUI_BodyText.get(lang) : SendCashGUI_BodyText.get(defaultLanguage);
        }

        public String getGUI_Amount(String lang) {
            return GUI_Amount.get(lang) != null ? GUI_Amount.get(lang) : GUI_Amount.get(defaultLanguage);
        }

        public String getSendCashGUI_Player(String lang) {
            return SendCashGUI_Player.get(lang) != null ? SendCashGUI_Player.get(lang) : SendCashGUI_Player.get(defaultLanguage);
        }

        public String getSend(String lang) {
            return Send.get(lang) != null ? Send.get(lang) : Send.get(defaultLanguage);
        }

        public String getCancel(String lang) {
            return Cancel.get(lang) != null ? Cancel.get(lang) : Cancel.get(defaultLanguage);
        }

        public HashMap<String, String> getGUI_Amount() {
            return GUI_Amount;
        }

        public HashMap<String, String> getSendCashGUI_BodyText() {
            return SendCashGUI_BodyText;
        }

        public HashMap<String, String> getSendCashGUI_Player() {
            return SendCashGUI_Player;
        }

        public String getSendCashGUI_Title(String lang) {
            return SendCashGUI_Title.get(lang) != null ? SendCashGUI_Title.get(lang) : SendCashGUI_Title.get(defaultLanguage);
        }

        public HashMap<String, String> getSendCashGUI_Title() {
            return SendCashGUI_Title;
        }

        public HashMap<String, String> getCancel() {
            return Cancel;
        }

        public HashMap<String, String> getSend() {
            return Send;
        }

        public void setGUI_Amount(HashMap<String, String> GUI_Amount) {
            this.GUI_Amount = GUI_Amount;
        }

        public void setSendCashGUI_BodyText(HashMap<String, String> SendCashGUI_BodyText) {
            this.SendCashGUI_BodyText = SendCashGUI_BodyText;
        }

        public void setSendCashGUI_Player(HashMap<String, String> SendCashGUI_Player) {
            this.SendCashGUI_Player = SendCashGUI_Player;
        }

        public void setCancel(HashMap<String, String> Cancel) {
            this.Cancel = Cancel;
        }

        public void setSend(HashMap<String, String> Send) {
            this.Send = Send;
        }

        public void setSendCashGUI_Title(HashMap<String, String> SendCashGUI_Title) {
            this.SendCashGUI_Title = SendCashGUI_Title;
        }

    }

    public class GameObject {

        private HashMap<String, String> RemoveAtm_Fail, RemoveAtm, CreateAtm, CreateAtm_Fail, SaveAtm_Fail,
                InteractAtm_Fail, PlaceAtm, PlaceAtm_Fail, FindMoney;

        public GameObject() {
            this.CreateAtm = new HashMap<>();
            this.CreateAtm_Fail = new HashMap<>();
            this.RemoveAtm = new HashMap<>();
            this.RemoveAtm_Fail = new HashMap<>();
            this.SaveAtm_Fail = new HashMap<>();
            this.InteractAtm_Fail = new HashMap<>();
            this.PlaceAtm = new HashMap<>();
            this.PlaceAtm_Fail = new HashMap<>();
            this.FindMoney = new HashMap<>();
        }

        public String getFindMoney(String lang) {
            return FindMoney.get(lang) != null ? FindMoney.get(lang) : FindMoney.get(defaultLanguage);
        }

        public HashMap<String, String> getFindMoney() {
            return FindMoney;
        }

        public void setFindMoney(HashMap<String, String> FindMoney) {
            this.FindMoney = FindMoney;
        }

        public String getInteractAtm_Fail(String lang) {
            return InteractAtm_Fail.get(lang) != null ? InteractAtm_Fail.get(lang) : InteractAtm_Fail.get(defaultLanguage);
        }

        public String getPlaceAtm(String lang) {
            return PlaceAtm.get(lang) != null ? PlaceAtm.get(lang) : PlaceAtm.get(defaultLanguage);
        }

        public String getPlaceAtm_Fail(String lang) {
            return PlaceAtm_Fail.get(lang) != null ? PlaceAtm_Fail.get(lang) : PlaceAtm_Fail.get(defaultLanguage);
        }

        public HashMap<String, String> getInteractAtm_Fail() {
            return InteractAtm_Fail;
        }

        public HashMap<String, String> getPlaceAtm() {
            return PlaceAtm;
        }

        public HashMap<String, String> getPlaceAtm_Fail() {
            return PlaceAtm_Fail;
        }

        public void setInteractAtm_Fail(HashMap<String, String> InteractAtm_Fail) {
            this.InteractAtm_Fail = InteractAtm_Fail;
        }

        public void setPlaceAtm(HashMap<String, String> PlaceAtm) {
            this.PlaceAtm = PlaceAtm;
        }

        public void setPlaceAtm_Fail(HashMap<String, String> PlaceAtm_Fail) {
            this.PlaceAtm_Fail = PlaceAtm_Fail;
        }

        public String getSaveAtm_Fail(String lang) {
            return SaveAtm_Fail.get(lang) != null ? SaveAtm_Fail.get(lang) : SaveAtm_Fail.get(defaultLanguage);
        }

        public HashMap<String, String> getSaveAtm_Fail() {
            return SaveAtm_Fail;
        }

        public void setSaveAtm_Fail(HashMap<String, String> SaveAtm_Fail) {
            this.SaveAtm_Fail = SaveAtm_Fail;
        }

        public String getCreateAtm(String lang) {
            return CreateAtm.get(lang) != null ? CreateAtm.get(lang) : CreateAtm.get(defaultLanguage);
        }

        public String getCreateAtm_Fail(String lang) {
            return CreateAtm_Fail.get(lang) != null ? CreateAtm_Fail.get(lang) : CreateAtm_Fail.get(defaultLanguage);
        }

        public String getRemoveAtm(String lang) {
            return RemoveAtm.get(lang) != null ? RemoveAtm.get(lang) : RemoveAtm.get(defaultLanguage);
        }

        public String getRemoveAtm_Fail(String lang) {
            return RemoveAtm_Fail.get(lang) != null ? RemoveAtm_Fail.get(lang) : RemoveAtm_Fail.get(defaultLanguage);
        }

        public HashMap<String, String> getCreateAtm() {
            return CreateAtm;
        }

        public HashMap<String, String> getCreateAtm_Fail() {
            return CreateAtm_Fail;
        }

        public HashMap<String, String> getRemoveAtm() {
            return RemoveAtm;
        }

        public HashMap<String, String> getRemoveAtm_Fail() {
            return RemoveAtm_Fail;
        }

        public void setCreateAtm(HashMap<String, String> CreateAtm) {
            this.CreateAtm = CreateAtm;
        }

        public void setCreateAtm_Fail(HashMap<String, String> CreateAtm_Fail) {
            this.CreateAtm_Fail = CreateAtm_Fail;
        }

        public void setRemoveAtm(HashMap<String, String> RemoveAtm) {
            this.RemoveAtm = RemoveAtm;
        }

        public void setRemoveAtm_Fail(HashMap<String, String> RemoveAtm_Fail) {
            this.RemoveAtm_Fail = RemoveAtm_Fail;
        }

    }

    //TODO Class Npc
    public class Npc {

        private HashMap<String, String> Welcome, MyName, QuestionHelp, CreateBank, Goodbye, ConfirmCreateBank, CostMessage,
                ChangeMind, AlreadyHaveBank, OpenAnotherAccount, BusinessAccount, AnotherRequest, NotNecessary,
                IncreaseDebt, NoProblem, OfCourseNot, DebtLimitIncreased, ThankYouAnotherRequest, ThankYouGoodbye,
                BusinessAccountNeeded, WhereToFindInfo, OpenBusinessAccount, EnterBankId, ThanksForInfo, StartCompany,
                NotInterested, CongratsOwner, CanIHelpElse, Yes, NoGoodbye, NoCompany, NeedToEstablish,
                WantToStartCompany, OkOtherTopic, OkGoodbye, WhatToNameCompany, BusinessName, GiveCompanyName,
                NameAlreadyExists, ErrorSQL, CannotSaveBusiness, BusinessAccountSetup, AdminHelp, Ok,
                SystemErrorCompany, CannotFindCompany, TryAgain, TryAgainLater, AskForID, ConfirmBusinessAccount,
                BankAccountCreated, BusinessExists, HelpNeeded, IDNotFound, SQL_Error, Next, HelloCreateCompany,
                Pay, ShareID, AssignName, AreYouSure, NoTryAgainLater, createBankAccountSaccessfully, NotAnounthMoney, ComingSoon;

        public Npc() {
            this.Welcome = new HashMap<>();
            this.MyName = new HashMap<>();
            this.QuestionHelp = new HashMap<>();
            this.CreateBank = new HashMap<>();
            this.Goodbye = new HashMap<>();
            this.ConfirmCreateBank = new HashMap<>();
            this.CostMessage = new HashMap<>();
            this.ChangeMind = new HashMap<>();
            this.AlreadyHaveBank = new HashMap<>();
            this.OpenAnotherAccount = new HashMap<>();

            this.BusinessAccount = new HashMap<>();
            this.AnotherRequest = new HashMap<>();
            this.NotNecessary = new HashMap<>();
            this.IncreaseDebt = new HashMap<>();
            this.NoProblem = new HashMap<>();
            this.OfCourseNot = new HashMap<>();
            this.DebtLimitIncreased = new HashMap<>();
            this.ThankYouAnotherRequest = new HashMap<>();
            this.ThankYouGoodbye = new HashMap<>();
            this.BusinessAccountNeeded = new HashMap<>();

            this.WhereToFindInfo = new HashMap<>();
            this.OpenBusinessAccount = new HashMap<>();
            this.EnterBankId = new HashMap<>();
            this.ThanksForInfo = new HashMap<>();
            this.StartCompany = new HashMap<>();
            this.NotInterested = new HashMap<>();
            this.CongratsOwner = new HashMap<>();
            this.CanIHelpElse = new HashMap<>();
            this.Yes = new HashMap<>();
            this.NoGoodbye = new HashMap<>();

            this.NoCompany = new HashMap<>();
            this.NeedToEstablish = new HashMap<>();
            this.WantToStartCompany = new HashMap<>();
            this.OkOtherTopic = new HashMap<>();
            this.OkGoodbye = new HashMap<>();
            this.WhatToNameCompany = new HashMap<>();
            this.BusinessName = new HashMap<>();
            this.GiveCompanyName = new HashMap<>();
            this.NameAlreadyExists = new HashMap<>();
            this.ErrorSQL = new HashMap<>();

            this.CannotSaveBusiness = new HashMap<>();
            this.BusinessAccountSetup = new HashMap<>();
            this.AdminHelp = new HashMap<>();
            this.Ok = new HashMap<>();
            this.SystemErrorCompany = new HashMap<>();
            this.CannotFindCompany = new HashMap<>();
            this.TryAgain = new HashMap<>();
            this.TryAgainLater = new HashMap<>();
            this.AskForID = new HashMap<>();
            this.ConfirmBusinessAccount = new HashMap<>();

            this.BankAccountCreated = new HashMap<>();
            this.BusinessExists = new HashMap<>();
            this.HelpNeeded = new HashMap<>();
            this.IDNotFound = new HashMap<>();
            this.SQL_Error = new HashMap<>();
            this.Next = new HashMap<>();

            this.HelloCreateCompany = new HashMap<>();
            this.Pay = new HashMap<>();
            this.ShareID = new HashMap<>();
            this.AssignName = new HashMap<>();
            this.AreYouSure = new HashMap<>();
            this.NoTryAgainLater = new HashMap<>();
            
            this.createBankAccountSaccessfully = new HashMap<>();
            this.NotAnounthMoney = new HashMap<>();
            this.ComingSoon = new HashMap<>();
        }
        
        public String getComingSoon(String lang){
            return ComingSoon.getOrDefault(lang, ComingSoon.get(defaultLanguage));
        }

        public HashMap<String, String> getComingSoon() {
            return ComingSoon;
        }

        public void setComingSoon(HashMap<String, String> ComingSoon) {
            this.ComingSoon = ComingSoon;
        }
        
        public String getNotAnounthMoney(String lang){
            return NotAnounthMoney.getOrDefault(lang, NotAnounthMoney.get(defaultLanguage));
        }

        public HashMap<String, String> getNotAnounthMoney() {
            return NotAnounthMoney;
        }

        public void setNotAnounthMoney(HashMap<String, String> NotAnounthMoney) {
            this.NotAnounthMoney = NotAnounthMoney;
        }

        public HashMap<String, String> getCreateBankAccountSaccessfully() {
            return createBankAccountSaccessfully;
        }

        public void setCreateBankAccountSaccessfully(HashMap<String, String> createBankAccountSaccessfully) {
            this.createBankAccountSaccessfully = createBankAccountSaccessfully;
        }
        
        public String getCreateBankAccountSaccessfully(String lang) {
            return createBankAccountSaccessfully.getOrDefault(lang, createBankAccountSaccessfully.get(defaultLanguage));
        }

        public HashMap<String, String> getNoTryAgainLater() {
            return NoTryAgainLater;
        }

        public String getNoTryAgainLater(String lang) {
            return NoTryAgainLater.getOrDefault(lang, NoTryAgainLater.get(defaultLanguage));
        }

        public void setNoTryAgainLater(HashMap<String, String> NoTryAgainLater) {
            this.NoTryAgainLater = NoTryAgainLater;
        }

        public void setHelloCreateCompany(HashMap<String, String> HelloCreateCompany) {
            this.HelloCreateCompany = HelloCreateCompany;
        }

        public void setPay(HashMap<String, String> Pay) {
            this.Pay = Pay;
        }

        public void setShareID(HashMap<String, String> ShareID) {
            this.ShareID = ShareID;
        }

        public void setAssignName(HashMap<String, String> AssignName) {
            this.AssignName = AssignName;
        }

        public void setAreYouSure(HashMap<String, String> AreYouSure) {
            this.AreYouSure = AreYouSure;
        }

        public String getHelloCreateCompany(String lang) {
            return HelloCreateCompany.getOrDefault(lang, HelloCreateCompany.get(defaultLanguage));
        }

        public String getPay(String lang) {
            return Pay.getOrDefault(lang, Pay.get(defaultLanguage));
        }

        public String getShareID(String lang) {
            return ShareID.getOrDefault(lang, ShareID.get(defaultLanguage));
        }

        public String getAssignName(String lang) {
            return AssignName.getOrDefault(lang, AssignName.get(defaultLanguage));
        }

        public String getAreYouSure(String lang) {
            return AreYouSure.getOrDefault(lang, AreYouSure.get(defaultLanguage));
        }

        public HashMap<String, String> getHelloCreateCompany() {
            return HelloCreateCompany;
        }

        public HashMap<String, String> getPay() {
            return Pay;
        }

        public HashMap<String, String> getShareID() {
            return ShareID;
        }

        public HashMap<String, String> getAssignName() {
            return AssignName;
        }

        public HashMap<String, String> getAreYouSure() {
            return AreYouSure;
        }

        public HashMap<String, String> getNext() {
            return Next;
        }

        public void setNext(HashMap<String, String> Next) {
            this.Next = Next;
        }

        public String getNext(String lang) {
            return Next.getOrDefault(lang, Next.get(defaultLanguage));
        }

        public HashMap<String, String> getWelcome() {
            return Welcome;
        } // 1

        public HashMap<String, String> getMyName() {
            return MyName;
        } // 2

        public HashMap<String, String> getQuestionHelp() {
            return QuestionHelp;
        } // 3

        public HashMap<String, String> getCreateBank() {
            return CreateBank;
        } // 4

        public HashMap<String, String> getGoodbye() {
            return Goodbye;
        } // 5

        public HashMap<String, String> getConfirmCreateBank() {
            return ConfirmCreateBank;
        } // 6

        public HashMap<String, String> getCostMessage() {
            return CostMessage;
        } // 7

        public HashMap<String, String> getChangeMind() {
            return ChangeMind;
        } // 8

        public HashMap<String, String> getAlreadyHaveBank() {
            return AlreadyHaveBank;
        } // 9

        public HashMap<String, String> getOpenAnotherAccount() {
            return OpenAnotherAccount;
        } // 10

        public HashMap<String, String> getBusinessAccount() {
            return BusinessAccount;
        } // 11

        public HashMap<String, String> getAnotherRequest() {
            return AnotherRequest;
        } // 12

        public HashMap<String, String> getNotNecessary() {
            return NotNecessary;
        } // 13

        public HashMap<String, String> getIncreaseDebt() {
            return IncreaseDebt;
        } // 14

        public HashMap<String, String> getNoProblem() {
            return NoProblem;
        } // 15

        public HashMap<String, String> getOfCourseNot() {
            return OfCourseNot;
        } // 16

        public HashMap<String, String> getDebtLimitIncreased() {
            return DebtLimitIncreased;
        } // 17

        public HashMap<String, String> getThankYouAnotherRequest() {
            return ThankYouAnotherRequest;
        } // 18

        public HashMap<String, String> getThankYouGoodbye() {
            return ThankYouGoodbye;
        } // 19

        public HashMap<String, String> getBusinessAccountNeeded() {
            return BusinessAccountNeeded;
        } // 20

        public HashMap<String, String> getWhereToFindInfo() {
            return WhereToFindInfo;
        } // 21

        public HashMap<String, String> getOpenBusinessAccount() {
            return OpenBusinessAccount;
        } // 22

        public HashMap<String, String> getEnterBankId() {
            return EnterBankId;
        } // 23

        public HashMap<String, String> getThanksForInfo() {
            return ThanksForInfo;
        } // 24

        public HashMap<String, String> getStartCompany() {
            return StartCompany;
        } // 25

        public HashMap<String, String> getNotInterested() {
            return NotInterested;
        } // 26

        public HashMap<String, String> getCongratsOwner() {
            return CongratsOwner;
        } // 27

        public HashMap<String, String> getCanIHelpElse() {
            return CanIHelpElse;
        } // 28

        public HashMap<String, String> getYes() {
            return Yes;
        } // 29

        public HashMap<String, String> getNoGoodbye() {
            return NoGoodbye;
        } // 30

        public HashMap<String, String> getNoCompany() {
            return NoCompany;
        } // 31

        public HashMap<String, String> getNeedToEstablish() {
            return NeedToEstablish;
        } // 32

        public HashMap<String, String> getWantToStartCompany() {
            return WantToStartCompany;
        } // 33

        public HashMap<String, String> getOkOtherTopic() {
            return OkOtherTopic;
        } // 34

        public HashMap<String, String> getOkGoodbye() {
            return OkGoodbye;
        } // 35

        public HashMap<String, String> getWhatToNameCompany() {
            return WhatToNameCompany;
        } // 36

        public HashMap<String, String> getBusinessName() {
            return BusinessName;
        } // 37

        public HashMap<String, String> getGiveCompanyName() {
            return GiveCompanyName;
        } // 38

        public HashMap<String, String> getNameAlreadyExists() {
            return NameAlreadyExists;
        } // 39

        public HashMap<String, String> getErrorSQL() {
            return ErrorSQL;
        } // 40

        public HashMap<String, String> getCannotSaveBusiness() {
            return CannotSaveBusiness;
        } // 41

        public HashMap<String, String> getBusinessAccountSetup() {
            return BusinessAccountSetup;
        } // 42

        public HashMap<String, String> getAdminHelp() {
            return AdminHelp;
        } // 43

        public HashMap<String, String> getOk() {
            return Ok;
        } // 44

        public HashMap<String, String> getSystemErrorCompany() {
            return SystemErrorCompany;
        } // 45

        public HashMap<String, String> getCannotFindCompany() {
            return CannotFindCompany;
        } // 46

        public HashMap<String, String> getTryAgain() {
            return TryAgain;
        } // 47

        public HashMap<String, String> getTryAgainLater() {
            return TryAgainLater;
        } // 48

        public HashMap<String, String> getAskForID() {
            return AskForID;
        } // 49

        public HashMap<String, String> getConfirmBusinessAccount() {
            return ConfirmBusinessAccount;
        } // 50

        public HashMap<String, String> getBankAccountCreated() {
            return BankAccountCreated;
        } // 51

        public HashMap<String, String> getBusinessExists() {
            return BusinessExists;
        } // 52

        public HashMap<String, String> getHelpNeeded() {
            return HelpNeeded;
        } // 53

        public HashMap<String, String> getIDNotFound() {
            return IDNotFound;
        } // 54

        public HashMap<String, String> getSQL_Error() {
            return SQL_Error;
        } // 55

        //Setter
        public void setWelcome(HashMap<String, String> Welcome) {
            this.Welcome = Welcome;
        } // 1

        public void setMyName(HashMap<String, String> MyName) {
            this.MyName = MyName;
        } // 2

        public void setQuestionHelp(HashMap<String, String> QuestionHelp) {
            this.QuestionHelp = QuestionHelp;
        } // 3

        public void setCreateBank(HashMap<String, String> CreateBank) {
            this.CreateBank = CreateBank;
        } // 4

        public void setGoodbye(HashMap<String, String> Goodbye) {
            this.Goodbye = Goodbye;
        } // 5

        public void setConfirmCreateBank(HashMap<String, String> ConfirmCreateBank) {
            this.ConfirmCreateBank = ConfirmCreateBank;
        } // 6

        public void setCostMessage(HashMap<String, String> CostMessage) {
            this.CostMessage = CostMessage;
        } // 7

        public void setChangeMind(HashMap<String, String> ChangeMind) {
            this.ChangeMind = ChangeMind;
        } // 8

        public void setAlreadyHaveBank(HashMap<String, String> AlreadyHaveBank) {
            this.AlreadyHaveBank = AlreadyHaveBank;
        } // 9

        public void setOpenAnotherAccount(HashMap<String, String> OpenAnotherAccount) {
            this.OpenAnotherAccount = OpenAnotherAccount;
        } // 10

        public void setBusinessAccount(HashMap<String, String> BusinessAccount) {
            this.BusinessAccount = BusinessAccount;
        } // 11

        public void setAnotherRequest(HashMap<String, String> AnotherRequest) {
            this.AnotherRequest = AnotherRequest;
        } // 12

        public void setNotNecessary(HashMap<String, String> NotNecessary) {
            this.NotNecessary = NotNecessary;
        } // 13

        public void setIncreaseDebt(HashMap<String, String> IncreaseDebt) {
            this.IncreaseDebt = IncreaseDebt;
        } // 14

        public void setNoProblem(HashMap<String, String> NoProblem) {
            this.NoProblem = NoProblem;
        } // 15

        public void setOfCourseNot(HashMap<String, String> OfCourseNot) {
            this.OfCourseNot = OfCourseNot;
        } // 16

        public void setDebtLimitIncreased(HashMap<String, String> DebtLimitIncreased) {
            this.DebtLimitIncreased = DebtLimitIncreased;
        } // 17

        public void setThankYouAnotherRequest(HashMap<String, String> ThankYouAnotherRequest) {
            this.ThankYouAnotherRequest = ThankYouAnotherRequest;
        } // 18

        public void setThankYouGoodbye(HashMap<String, String> ThankYouGoodbye) {
            this.ThankYouGoodbye = ThankYouGoodbye;
        } // 19

        public void setBusinessAccountNeeded(HashMap<String, String> BusinessAccountNeeded) {
            this.BusinessAccountNeeded = BusinessAccountNeeded;
        } // 20

        public void setWhereToFindInfo(HashMap<String, String> WhereToFindInfo) {
            this.WhereToFindInfo = WhereToFindInfo;
        } // 21

        public void setOpenBusinessAccount(HashMap<String, String> OpenBusinessAccount) {
            this.OpenBusinessAccount = OpenBusinessAccount;
        } // 22

        public void setEnterBankId(HashMap<String, String> EnterBankId) {
            this.EnterBankId = EnterBankId;
        } // 23

        public void setThanksForInfo(HashMap<String, String> ThanksForInfo) {
            this.ThanksForInfo = ThanksForInfo;
        } // 24

        public void setStartCompany(HashMap<String, String> StartCompany) {
            this.StartCompany = StartCompany;
        } // 25

        public void setNotInterested(HashMap<String, String> NotInterested) {
            this.NotInterested = NotInterested;
        } // 26

        public void setCongratsOwner(HashMap<String, String> CongratsOwner) {
            this.CongratsOwner = CongratsOwner;
        } // 27

        public void setCanIHelpElse(HashMap<String, String> CanIHelpElse) {
            this.CanIHelpElse = CanIHelpElse;
        } // 28

        public void setYes(HashMap<String, String> Yes) {
            this.Yes = Yes;
        } // 29

        public void setNoGoodbye(HashMap<String, String> NoGoodbye) {
            this.NoGoodbye = NoGoodbye;
        } // 30

        public void setNoCompany(HashMap<String, String> NoCompany) {
            this.NoCompany = NoCompany;
        } // 31

        public void setNeedToEstablish(HashMap<String, String> NeedToEstablish) {
            this.NeedToEstablish = NeedToEstablish;
        } // 32

        public void setWantToStartCompany(HashMap<String, String> WantToStartCompany) {
            this.WantToStartCompany = WantToStartCompany;
        } // 33

        public void setOkOtherTopic(HashMap<String, String> OkOtherTopic) {
            this.OkOtherTopic = OkOtherTopic;
        } // 34

        public void setOkGoodbye(HashMap<String, String> OkGoodbye) {
            this.OkGoodbye = OkGoodbye;
        } // 35

        public void setWhatToNameCompany(HashMap<String, String> WhatToNameCompany) {
            this.WhatToNameCompany = WhatToNameCompany;
        } // 36

        public void setBusinessName(HashMap<String, String> BusinessName) {
            this.BusinessName = BusinessName;
        } // 37

        public void setGiveCompanyName(HashMap<String, String> GiveCompanyName) {
            this.GiveCompanyName = GiveCompanyName;
        } // 38

        public void setNameAlreadyExists(HashMap<String, String> NameAlreadyExists) {
            this.NameAlreadyExists = NameAlreadyExists;
        } // 39

        public void setErrorSQL(HashMap<String, String> ErrorSQL) {
            this.ErrorSQL = ErrorSQL;
        } // 40

        public void setCannotSaveBusiness(HashMap<String, String> CannotSaveBusiness) {
            this.CannotSaveBusiness = CannotSaveBusiness;
        } // 41

        public void setBusinessAccountSetup(HashMap<String, String> BusinessAccountSetup) {
            this.BusinessAccountSetup = BusinessAccountSetup;
        } // 42

        public void setAdminHelp(HashMap<String, String> AdminHelp) {
            this.AdminHelp = AdminHelp;
        } // 43

        public void setOk(HashMap<String, String> Ok) {
            this.Ok = Ok;
        } // 44

        public void setSystemErrorCompany(HashMap<String, String> SystemErrorCompany) {
            this.SystemErrorCompany = SystemErrorCompany;
        } // 45

        public void setCannotFindCompany(HashMap<String, String> CannotFindCompany) {
            this.CannotFindCompany = CannotFindCompany;
        } // 46

        public void setTryAgain(HashMap<String, String> TryAgain) {
            this.TryAgain = TryAgain;
        } // 47

        public void setTryAgainLater(HashMap<String, String> TryAgainLater) {
            this.TryAgainLater = TryAgainLater;
        } // 48

        public void setAskForID(HashMap<String, String> AskForID) {
            this.AskForID = AskForID;
        } // 49

        public void setConfirmBusinessAccount(HashMap<String, String> ConfirmBusinessAccount) {
            this.ConfirmBusinessAccount = ConfirmBusinessAccount;
        } // 50

        public void setBankAccountCreated(HashMap<String, String> BankAccountCreated) {
            this.BankAccountCreated = BankAccountCreated;
        } // 51

        public void setBusinessExists(HashMap<String, String> BusinessExists) {
            this.BusinessExists = BusinessExists;
        } // 52

        public void setHelpNeeded(HashMap<String, String> HelpNeeded) {
            this.HelpNeeded = HelpNeeded;
        } // 53

        public void setIDNotFound(HashMap<String, String> IDNotFound) {
            this.IDNotFound = IDNotFound;
        } // 54

        public void setSQL_Error(HashMap<String, String> SQL_Error) {
            this.SQL_Error = SQL_Error;
        } // 55

        //Getters mit Lang
        public String getWelcome(String lang) {
            return Welcome.getOrDefault(lang, Welcome.get(defaultLanguage));
        } // 1

        public String getMyName(String lang) {
            return MyName.getOrDefault(lang, MyName.get(defaultLanguage));
        } // 2

        public String getQuestionHelp(String lang) {
            return QuestionHelp.getOrDefault(lang, QuestionHelp.get(defaultLanguage));
        } // 3

        public String getCreateBank(String lang) {
            return CreateBank.getOrDefault(lang, CreateBank.get(defaultLanguage));
        } // 4

        public String getGoodbye(String lang) {
            return Goodbye.getOrDefault(lang, Goodbye.get(defaultLanguage));
        } // 5

        public String getConfirmCreateBank(String lang) {
            return ConfirmCreateBank.getOrDefault(lang, ConfirmCreateBank.get(defaultLanguage));
        } // 6

        public String getCostMessage(String lang) {
            return CostMessage.getOrDefault(lang, CostMessage.get(defaultLanguage));
        } // 7

        public String getChangeMind(String lang) {
            return ChangeMind.getOrDefault(lang, ChangeMind.get(defaultLanguage));
        } // 8

        public String getAlreadyHaveBank(String lang) {
            return AlreadyHaveBank.getOrDefault(lang, AlreadyHaveBank.get(defaultLanguage));
        } // 9

        public String getOpenAnotherAccount(String lang) {
            return OpenAnotherAccount.getOrDefault(lang, OpenAnotherAccount.get(defaultLanguage));
        } // 10

        public String getBusinessAccount(String lang) {
            return BusinessAccount.getOrDefault(lang, BusinessAccount.get(defaultLanguage));
        } // 11

        public String getAnotherRequest(String lang) {
            return AnotherRequest.getOrDefault(lang, AnotherRequest.get(defaultLanguage));
        } // 12

        public String getNotNecessary(String lang) {
            return NotNecessary.getOrDefault(lang, NotNecessary.get(defaultLanguage));
        } // 13

        public String getIncreaseDebt(String lang) {
            return IncreaseDebt.getOrDefault(lang, IncreaseDebt.get(defaultLanguage));
        } // 14

        public String getNoProblem(String lang) {
            return NoProblem.getOrDefault(lang, NoProblem.get(defaultLanguage));
        } // 15

        public String getOfCourseNot(String lang) {
            return OfCourseNot.getOrDefault(lang, OfCourseNot.get(defaultLanguage));
        } // 16

        public String getDebtLimitIncreased(String lang) {
            return DebtLimitIncreased.getOrDefault(lang, DebtLimitIncreased.get(defaultLanguage));
        } // 17

        public String getThankYouAnotherRequest(String lang) {
            return ThankYouAnotherRequest.getOrDefault(lang, ThankYouAnotherRequest.get(defaultLanguage));
        } // 18

        public String getThankYouGoodbye(String lang) {
            return ThankYouGoodbye.getOrDefault(lang, ThankYouGoodbye.get(defaultLanguage));
        } // 19

        public String getBusinessAccountNeeded(String lang) {
            return BusinessAccountNeeded.getOrDefault(lang, BusinessAccountNeeded.get(defaultLanguage));
        } // 20

        public String getWhereToFindInfo(String lang) {
            return WhereToFindInfo.getOrDefault(lang, WhereToFindInfo.get(defaultLanguage));
        } // 21

        public String getOpenBusinessAccount(String lang) {
            return OpenBusinessAccount.getOrDefault(lang, OpenBusinessAccount.get(defaultLanguage));
        } // 22

        public String getEnterBankId(String lang) {
            return EnterBankId.getOrDefault(lang, EnterBankId.get(defaultLanguage));
        } // 23

        public String getThanksForInfo(String lang) {
            return ThanksForInfo.getOrDefault(lang, ThanksForInfo.get(defaultLanguage));
        } // 24

        public String getStartCompany(String lang) {
            return StartCompany.getOrDefault(lang, StartCompany.get(defaultLanguage));
        } // 25

        public String getNotInterested(String lang) {
            return NotInterested.getOrDefault(lang, NotInterested.get(defaultLanguage));
        } // 26

        public String getCongratsOwner(String lang) {
            return CongratsOwner.getOrDefault(lang, CongratsOwner.get(defaultLanguage));
        } // 27

        public String getCanIHelpElse(String lang) {
            return CanIHelpElse.getOrDefault(lang, CanIHelpElse.get(defaultLanguage));
        } // 28

        public String getYes(String lang) {
            return Yes.getOrDefault(lang, Yes.get(defaultLanguage));
        } // 29

        public String getNoGoodbye(String lang) {
            return NoGoodbye.getOrDefault(lang, NoGoodbye.get(defaultLanguage));
        } // 30

        public String getNoCompany(String lang) {
            return NoCompany.getOrDefault(lang, NoCompany.get(defaultLanguage));
        } // 31

        public String getNeedToEstablish(String lang) {
            return NeedToEstablish.getOrDefault(lang, NeedToEstablish.get(defaultLanguage));
        } // 32

        public String getWantToStartCompany(String lang) {
            return WantToStartCompany.getOrDefault(lang, WantToStartCompany.get(defaultLanguage));
        } // 33

        public String getOkOtherTopic(String lang) {
            return OkOtherTopic.getOrDefault(lang, OkOtherTopic.get(defaultLanguage));
        } // 34

        public String getOkGoodbye(String lang) {
            return OkGoodbye.getOrDefault(lang, OkGoodbye.get(defaultLanguage));
        } // 35

        public String getWhatToNameCompany(String lang) {
            return WhatToNameCompany.getOrDefault(lang, WhatToNameCompany.get(defaultLanguage));
        } // 36

        public String getBusinessName(String lang) {
            return BusinessName.getOrDefault(lang, BusinessName.get(defaultLanguage));
        } // 37

        public String getGiveCompanyName(String lang) {
            return GiveCompanyName.getOrDefault(lang, GiveCompanyName.get(defaultLanguage));
        } // 38

        public String getNameAlreadyExists(String lang) {
            return NameAlreadyExists.getOrDefault(lang, NameAlreadyExists.get(defaultLanguage));
        } // 39

        public String getErrorSQL(String lang) {
            return ErrorSQL.getOrDefault(lang, ErrorSQL.get(defaultLanguage));
        } // 40

        public String getCannotSaveBusiness(String lang) {
            return CannotSaveBusiness.getOrDefault(lang, CannotSaveBusiness.get(defaultLanguage));
        } // 41

        public String getBusinessAccountSetup(String lang) {
            return BusinessAccountSetup.getOrDefault(lang, BusinessAccountSetup.get(defaultLanguage));
        } // 42

        public String getAdminHelp(String lang) {
            return AdminHelp.getOrDefault(lang, AdminHelp.get(defaultLanguage));
        } // 43

        public String getOk(String lang) {
            return Ok.getOrDefault(lang, Ok.get(defaultLanguage));
        } // 44

        public String getSystemErrorCompany(String lang) {
            return SystemErrorCompany.getOrDefault(lang, SystemErrorCompany.get(defaultLanguage));
        } // 45

        public String getCannotFindCompany(String lang) {
            return CannotFindCompany.getOrDefault(lang, CannotFindCompany.get(defaultLanguage));
        } // 46

        public String getTryAgain(String lang) {
            return TryAgain.getOrDefault(lang, TryAgain.get(defaultLanguage));
        } // 47

        public String getTryAgainLater(String lang) {
            return TryAgainLater.getOrDefault(lang, TryAgainLater.get(defaultLanguage));
        } // 48

        public String getAskForID(String lang) {
            return AskForID.getOrDefault(lang, AskForID.get(defaultLanguage));
        } // 49

        public String getConfirmBusinessAccount(String lang) {
            return ConfirmBusinessAccount.getOrDefault(lang, ConfirmBusinessAccount.get(defaultLanguage));
        } // 50

        public String getBankAccountCreated(String lang) {
            return BankAccountCreated.getOrDefault(lang, BankAccountCreated.get(defaultLanguage));
        } // 51

        public String getBusinessExists(String lang) {
            return BusinessExists.getOrDefault(lang, BusinessExists.get(defaultLanguage));
        } // 52

        public String getHelpNeeded(String lang) {
            return HelpNeeded.getOrDefault(lang, HelpNeeded.get(defaultLanguage));
        } // 53

        public String getIDNotFound(String lang) {
            return IDNotFound.getOrDefault(lang, IDNotFound.get(defaultLanguage));
        } // 54

        public String getSQL_Error(String lang) {
            return SQL_Error.getOrDefault(lang, SQL_Error.get(defaultLanguage));
        } // 55

    }

//    public class Business {
//
//        public Business() {
//        }
//
//    }
}
