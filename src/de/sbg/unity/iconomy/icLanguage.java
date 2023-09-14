package de.sbg.unity.iconomy;

import java.util.HashMap;

public class icLanguage {

    private String defaultLanguage;
    private Command command;
    private Status status;
    private Other other;
    private GUI gui;
//    private Factory factory;

    public icLanguage() {
        this.command = new Command();
        this.status = new Status();
        this.other = new Other();
        this.gui = new GUI();
//      this.factory = new Factory();
        setDefaultLanguage("en");

        //Other
        HashMap<String, String> NoPermission = new HashMap<>();
        NoPermission.put("de", "Du hast nicht genug Berechtigung!");
        NoPermission.put("en", "You do not have enough permission!");
        getOther().setNoPermission(NoPermission);

        //Command
        
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

        //Status
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
        MoneyMustBeNumber.put("en", "The amounth must be a number!");
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

        HashMap<String, String> EmptyAmounth = new HashMap<>();
        EmptyAmounth.put("de", "Der Betrag ist leer!");
        EmptyAmounth.put("en", "The amounth is empty!");
        getStatus().setEmptyAmounth(EmptyAmounth);

        HashMap<String, String> AmounthBigger = new HashMap<>();
        AmounthBigger.put("de", "Der Betrag muss mehr als 0 sein!");
        AmounthBigger.put("en", "The amount must be more than 0!");
        getStatus().setAmounthBigger(AmounthBigger);

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

        //GUI
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
        SendCashGUI_Player.put("de", "Spielername:");
        SendCashGUI_Player.put("en", "Playername:");
        getGui().setSendCashGUI_Player(SendCashGUI_Player);

        HashMap<String, String> SendCashGUI_BodyText = new HashMap<>();
        SendCashGUI_BodyText.put("de", "Sende Cash zu einem Spieler!");
        SendCashGUI_BodyText.put("en", "Send cash to a player!");
        getGui().setSendCashGUI_BodyText(SendCashGUI_BodyText);

        HashMap<String, String> GUI_Amounth = new HashMap<>();
        GUI_Amounth.put("de", "Betrag:");
        GUI_Amounth.put("en", "Amounth:");
        getGui().setGUI_Amounth(GUI_Amounth);

        HashMap<String, String> SendCashGUI_YourCash = new HashMap<>();
        SendCashGUI_YourCash.put("de", "Dein Cash:");
        SendCashGUI_YourCash.put("en", "Your Cash:");
        getGui().setSendCashGUI_YourCash(SendCashGUI_YourCash);
        
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
                AdminGiveMoney, TransferCancel, PlayerNotAnounthMoney, EmptyAmounth, SendCashToSelf,
                OtherPlayerNotAnounthMoney, PlayerHasNoAccount, OtherPlayerHasNoAccount, AmounthBigger,
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
            this.EmptyAmounth = new HashMap<>();
            this.OtherPlayerNotAnounthMoney = new HashMap<>();
            this.AmounthBigger = new HashMap<>();
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
        
        public String getAmounthBigger(String lang) {
            return AmounthBigger.get(lang) != null ? AmounthBigger.get(lang) : AmounthBigger.get(defaultLanguage);
        }

        public HashMap<String, String> getAmounthBigger() {
            return AmounthBigger;
        }

        public void setAmounthBigger(HashMap<String, String> AmounthBigger) {
            this.AmounthBigger = AmounthBigger;
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

        public String getEmptyAmounth(String lang) {
            return EmptyAmounth.get(lang) != null ? EmptyAmounth.get(lang) : EmptyAmounth.get(defaultLanguage);
        }

        public HashMap<String, String> getEmptyAmounth() {
            return EmptyAmounth;
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

        public void setEmptyAmounth(HashMap<String, String> EmptyAmounth) {
            this.EmptyAmounth = EmptyAmounth;
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

        private HashMap<String, String> Send, Cancel, SendCashGUI_Title, SendCashGUI_BodyText, GUI_Amounth, SendCashGUI_Player,
                SendCashGUI_YourCash, NoAccount, CashInOutGUI_In_Message, CashInOutGUI_Out_Message, YourCash, YourBank;

        public GUI() {
            this.Cancel = new HashMap<>();
            this.Send = new HashMap<>();
            this.SendCashGUI_Title = new HashMap<>();
            this.GUI_Amounth = new HashMap<>();
            this.SendCashGUI_BodyText = new HashMap<>();
            this.SendCashGUI_Player = new HashMap<>();
            this.SendCashGUI_YourCash = new HashMap<>();
            this.NoAccount = new HashMap<>();
            this.CashInOutGUI_In_Message = new HashMap<>();
            this.CashInOutGUI_Out_Message = new HashMap<>();
            this.YourCash = new HashMap<>();
            this.YourBank = new HashMap<>();
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
        
        public String getSendCashGUI_YourCash(String lang) {
            return SendCashGUI_YourCash.get(lang) != null ? SendCashGUI_YourCash.get(lang) : SendCashGUI_YourCash.get(defaultLanguage);
        }

        public String getSendCashGUI_BodyText(String lang) {
            return SendCashGUI_BodyText.get(lang) != null ? SendCashGUI_BodyText.get(lang) : SendCashGUI_BodyText.get(defaultLanguage);
        }

        public String getGUI_Amounth(String lang) {
            return GUI_Amounth.get(lang) != null ? GUI_Amounth.get(lang) : GUI_Amounth.get(defaultLanguage);
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

        public HashMap<String, String> getGUI_Amounth() {
            return GUI_Amounth;
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

        public HashMap<String, String> getSendCashGUI_YourCash() {
            return SendCashGUI_YourCash;
        }

        public void setGUI_Amounth(HashMap<String, String> GUI_Amounth) {
            this.GUI_Amounth = GUI_Amounth;
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

        public void setSendCashGUI_YourCash(HashMap<String, String> SendCashGUI_YourCash) {
            this.SendCashGUI_YourCash = SendCashGUI_YourCash;
        }

    }

//    public class Factory {
//
//        public Factory() {
//        }
//
//    }
}
