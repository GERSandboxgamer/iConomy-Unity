package de.sbg.unity.iconomy;

import de.chaoswg.lang.Model3DPlaceLang;
import java.util.HashMap;

public class icLanguage {

    private String defaultLanguage;
    private Command command;
    private Status status;
    private Other other;
    private GUI gui;
    private GameObject gameObject;
    private Model3DPlaceLang.RadialPlaceClass RadialPlace;
//    private Factory factory;

    public icLanguage() {
        this.command = new Command();
        this.status = new Status();
        this.other = new Other();
        this.gui = new GUI();
        this.gameObject = new GameObject();
        this.RadialPlace = new Model3DPlaceLang().new RadialPlaceClass();
        
//      this.factory = new Factory();
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
        HashMap<String, String> Sign_Misspelled = new HashMap<>();
        Sign_Misspelled.put("de", "Schild wurde falsch geschrieben!");
        Sign_Misspelled.put("en", "Sign was misspelled!");
        getStatus().setSign_Misspelled(Sign_Misspelled);

        HashMap<String, String> Sign_OK = new HashMap<>();
        Sign_OK.put("de", "Schild wurde erfolgreich erstellt!");
        Sign_OK.put("en", "Sign set!");
        getStatus().setSign_OK(Sign_OK);

        HashMap<String, String> Sign_Distroy_Fail = new HashMap<>();
        Sign_Distroy_Fail.put("de", "Dieses Schild kann nicht zerstört werden!");
        Sign_Distroy_Fail.put("en", "This sign cannot be destroyed!");
        getStatus().setSign_Distroy_Fail(Sign_Distroy_Fail);

        HashMap<String, String> PlayerNotConnected = new HashMap<>();
        PlayerNotConnected.put("de", "Spieler momentan nicht auf dem Server!");
        PlayerNotConnected.put("en", "Player not connected!");
        getStatus().setPlayerNotConnected(PlayerNotConnected);

        HashMap<String, String> PlayerNotExist = new HashMap<>();
        PlayerNotExist.put("de", "Spieler existiert nicht!");
        PlayerNotExist.put("en", "Player not exist!");
        getStatus().setPlayerNotExist(PlayerNotExist);

        HashMap<String, String> LostMoney = new HashMap<>();
        LostMoney.put("de", "Du hast dein Bargeld verlohren!");
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
        PlayerNotAnounthMoney.put("en", "You do not have anouth money!");
        getStatus().setPlayerNotAnounthMoney(PlayerNotAnounthMoney);

        HashMap<String, String> OtherPlayerNotAnounthMoney = new HashMap<>();
        OtherPlayerNotAnounthMoney.put("de", "Der Spieler hat nicht genug Geld!");
        OtherPlayerNotAnounthMoney.put("en", "The player has not anouth money!");
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
        PlaceAtmGui_Amount.put("de", "Menge:");
        PlaceAtmGui_Amount.put("en", "Amount:");
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
        GUI_Amount.put("de", "Betrag:");
        GUI_Amount.put("en", "Amount:");
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
                PlayerDeath_MoneyEmpty_Player;

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
                PlaceAtmGui_Amount, PlaceAtmGui_Mode, PlaceAtmGui_Escape;

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

//    public class Factory {
//
//        public Factory() {
//        }
//
//    }
}
