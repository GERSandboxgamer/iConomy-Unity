package de.sbg.unity.iconomy;

import java.util.HashMap;

public class icLanguage {
    
    private String defaultLanguage;
    private Command command;
    private Status status;
    private Other other;
    private GUI gui;
    private Factory factory;

    public icLanguage() {
        this.command = new Command();
        this.status = new Status();
        this.other = new Other();
        this.gui = new GUI();
        this.factory = new Factory();
        setDefaultLanguage("en");
        
        
        //Other
        HashMap<String, String> NoPermission = new HashMap<>();
        NoPermission.put("de", "Du hast nicht genug Berechtigung!");
        NoPermission.put("en", "You do not have enough permission!");
        getOther().setNoPermission(NoPermission);
        
        //Status
        HashMap<String, String> PlayerNotConnected = new HashMap<>();
        PlayerNotConnected.put("de", "Du hast nicht genug Berechtigung!");
        PlayerNotConnected.put("en", "You do not have enough permission!");
        getStatus().setPlayerNotConnected(PlayerNotConnected);
        
        HashMap<String, String> PlayerNotExist = new HashMap<>();
        PlayerNotExist.put("de", "Du hast nicht genug Berechtigung!");
        PlayerNotExist.put("en", "You do not have enough permission!");
        getStatus().setPlayerNotExist(PlayerNotExist);
        
        HashMap<String, String> PlayerNotExist = new HashMap<>();
        PlayerNotExist.put("de", "Du hast nicht genug Berechtigung!");
        PlayerNotExist.put("en", "You do not have enough permission!");
        getStatus().setPlayerNotExist(PlayerNotExist);
        
        HashMap<String, String> LostMoney = new HashMap<>();
        LostMoney.put("de", "Du hast nicht genug Berechtigung!");
        LostMoney.put("en", "You do not have enough permission!");
        getStatus().setLostMoney(LostMoney);
        
        HashMap<String, String> MoneyMustBeNumber = new HashMap<>();
        MoneyMustBeNumber.put("de", "Du hast nicht genug Berechtigung!");
        MoneyMustBeNumber.put("en", "You do not have enough permission!");
        getStatus().setMoneyMustBeNumber(MoneyMustBeNumber);
        
        HashMap<String, String> AdminGiveMoney = new HashMap<>();
        AdminGiveMoney.put("de", "Du hast nicht genug Berechtigung!");
        AdminGiveMoney.put("en", "You do not have enough permission!");
        getStatus().setAdminGiveMoney(AdminGiveMoney);
        
        //GUI
        HashMap<String, String> Send = new HashMap<>();
        Send.put("de", "Senden");
        Send.put("en", "Send");
        getStatus().setSend(Send);
        
        HashMap<String, String> Cancel = new HashMap<>();
        Cancel.put("de", "Abbrechen");
        Cancel.put("en", "Cancel");
        getStatus().setCancel(Cancel);
        
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
    
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }
    
    public class Command {

        public Command() {
        }
        
    }
    
    public class Status {

        public Status() {
        }
        
    }
    
    public class Other {
        
        private HashMap NoPermission;

        public Other() {
        }

        public HashMap getNoPermission() {
            return NoPermission;
        }

        public void setNoPermission(HashMap NoPermission) {
            this.NoPermission = NoPermission;
        }
        
    }
    
    public class GUI {

        public GUI() {
        }
        
    }
    
    public class Factory {

        public Factory() {
        }
        
    }
    
}
