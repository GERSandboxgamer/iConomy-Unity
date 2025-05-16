package de.sbg.unity.iconomy.Listeners.Commands;

import de.chaoswg.events.call.ModelAuswahl;
import de.chaoswg.model3d.Model3DObject;
import de.chaoswg.model3d.Model3DPlace;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Objects.AtmObject;
import de.sbg.unity.iconomy.Utils.AtmUtils;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.EventCancel;
import static de.sbg.unity.iconomy.Utils.TransferResult.PlayerNotConnected;
import static de.sbg.unity.iconomy.Utils.TransferResult.PlayerNotExist;
import static de.sbg.unity.iconomy.Utils.TransferResult.Successful;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.World;
import net.risingworld.api.definitions.Definitions;
import net.risingworld.api.definitions.Npcs;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;

public class AdminMoneyCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final MoneyFormate mFormat;
    private final TextFormat format;

    public AdminMoneyCommandListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.mFormat = new MoneyFormate(plugin, Console);
        this.format = new TextFormat();
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();

        if (player.isAdmin()) {

            String[] cmd = event.getCommand().split(" ");

            if (cmd[0].toLowerCase().equals("/money") || cmd[0].toLowerCase().equals("/eco") || cmd[0].toLowerCase().equals("/$") || cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy")) {
                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("info")) {
                        player.sendTextMessage(format.Color("orange", "========== iConomy-Info =========="));
                        player.sendTextMessage(format.Color("orange", "Name: " + plugin.getDescription("name")));
                        player.sendTextMessage(format.Color("orange", "Version: " + plugin.getDescription("version")));
                        player.sendTextMessage(format.Color("orange", "Update: " + plugin.hasUpdate()));
                        player.sendTextMessage(format.Color("orange", "Debug: " + plugin.Config.Debug));
                        player.sendTextMessage(format.Color("orange", "Currency: " + plugin.Config.Currency));
                        player.sendTextMessage(format.Color("orange", "MoneyFormat: " + plugin.Config.MoneyFormat));
                        player.sendTextMessage(format.Color("orange", "MoneyInfoTime: " + plugin.Config.MoneyInfoTime));
                        player.sendTextMessage(format.Color("orange", "PlayerBankAccountCost: " + plugin.Config.PlayerBankAccountCost));
                        player.sendTextMessage(format.Color("orange", "PlayerBankStartAmount: " + plugin.Config.PlayerBankStartAmount));
                        player.sendTextMessage(format.Color("orange", "PlayerCashStartAmount: " + plugin.Config.PlayerCashStartAmount));
                        player.sendTextMessage(format.Color("orange", "SaveTimer: " + plugin.Config.SaveTimer));
                        player.sendTextMessage(format.Color("orange", "ShowBalaceAtStart: " + plugin.Config.ShowBalanceAtStart));
                        player.sendTextMessage(format.Color("orange", "CreateAccountViaCommand: " + plugin.Config.CreateAccountViaCommand));
                        player.sendTextMessage(format.Color("orange", "Command_Bank_OnlyAdmin: " + plugin.Config.Command_Bank_OnlyAdmin));
                        player.sendTextMessage(format.Color("orange", "=================================="));
                    }

                    if (cmd[1].toLowerCase().equals("save")) {
                        try {
                            player.sendTextMessage(format.Color("orange", "Save all...."));
                            plugin.Databases.saveAll();
                            player.sendTextMessage(format.Color("green", "Done!"));
                        } catch (IOException ex) {
                            player.sendTextMessage(format.Color("red", "IOException"));
                        } catch (SQLException sql) {
                            player.sendTextMessage(format.Color("red", "SQLException"));
                        }
                    }

                    if (cmd[1].toLowerCase().equals("bs") || cmd[1].toLowerCase().equals("banksystem")) {
                        if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                            plugin.GUI.Bankystem.MainGui.showGUI(player);
                        } else {
                            player.showErrorMessageBox("No bank account", "You do not have a bank account!");
                        }
                    }

                }
                if (cmd.length == 3) {

                    if (cmd[1].toLowerCase().equals("bs") || cmd[1].toLowerCase().equals("banksystem")) {
                        if (cmd[2].toLowerCase().equals("true")) {
                            plugin.GUI.Bankystem.MainGui.showGUI(player, true);
                        } else {
                            plugin.GUI.Bankystem.MainGui.showGUI(player, false);
                        }
                    }

                    if (cmd[1].toLowerCase().equals("createbank") || cmd[1].toLowerCase().equals("cb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            if (!plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                plugin.Bankystem.PlayerSystem.addPlayerAccount(p2.getUID());
                                player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getCreateBank(lang)));
                            } else {
                                player.sendTextMessage(format.Color("red", plugin.Language.getCommand().getCreateBank_HasAccount(lang)));
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                        }
                    }

                    if (cmd[1].toLowerCase().equals("test")) {
                        if (cmd[2].toLowerCase().equals("suitcase")) {
                            long c = plugin.CashSystem.getCash(player);
                            if (c > 0) {
                                plugin.GameObject.suitcase.spawn(player, c, player.getPosition());
                                plugin.CashSystem.removeCash(player, c, RemoveCashEvent.Reason.Lost);
                                player.sendTextMessage(format.Color("orange", plugin.Language.getStatus().getLostMoney(lang)));
                            } else {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getAmountBigger(lang)));
                            }
                        }
                        if (cmd[2].toLowerCase().equals("bundle")) {
                            player.sendTextMessage("Bundles:");
                            if (!plugin.GameObject.getListBundle().isEmpty()) {
                                for (String s : plugin.GameObject.getListBundle().keySet()) {
                                    player.sendTextMessage("- " + s + "\n(Path: " + plugin.GameObject.getListBundle().get(s).getBundlePath() + "\n" + "/" + s + "/" + plugin.GameObject.getListBundle().get(s).getPrefabAsset().getPath() + ")");
                                }
                            } else {
                                player.sendTextMessage("List is empty!");
                            }
                        }

                        if (cmd[2].toLowerCase().equals("place")) {
                            Model3DPlace place = Model3DPlace.create(plugin, player, plugin.Language.getRadialPlace());
                            ModelAuswahl modelAuswahl = new ModelAuswahl();
                            modelAuswahl.setBundle(true);
                            modelAuswahl.setBundle(plugin.GameObject.getListBundle().get("ATM").getBundlePath() + "/" + "ATM" + "/" + plugin.GameObject.getListBundle().get("ATM").getPrefabAsset().getPath());
                            Model3DObject gameObject = new Model3DObject(player.getUID(), -1, modelAuswahl);
                            player.setListenForMouseInput(true);
                            plugin.registerEventListener(place);

                            place.place(gameObject, false, false);
                        }

                    }
                    if (cmd[1].toLowerCase().equals("npc")) {
                        if (cmd[2].toLowerCase().equals("select")) {
                            boolean mode = plugin.Attribute.player.getNpcSelectMode(player);
                            if (!mode) {
                                plugin.Attribute.player.setNpcSelectMode(player, true);
                                //TODO MSG
                            } else {
                                plugin.Attribute.player.setNpcSelectMode(player, false);
                                //TODO MSG
                            }
                            if (cmd[2].toLowerCase().equals("move")) {
                                Npc npc = plugin.Attribute.player.getSelectNpc(player);
                                if (npc != null) {
                                    npc.setPosition(player.getPosition());
                                    npc.setRotation(player.getRotation());
                                    npc.setViewDirection(player.getViewDirection());
                                }
                            }
                        }
                    }
                }
                if (cmd.length == 4) {
                    if (cmd[1].toLowerCase().equals("givecash") || cmd[1].toLowerCase().equals("gc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (l > 0) {
                                TransferResult tr = plugin.CashSystem.addCash(p2, l, AddCashEvent.Reason.Player);
                                switch (tr) {
                                    case EventCancel -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    }
                                    case PlayerNotConnected -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    }
                                    case PlayerNotExist -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                    case Successful -> {
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminGivecash(lang)));
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("takecash") || cmd[1].toLowerCase().equals("tc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (plugin.CashSystem.getCash(p2) - l < 0) {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerNotAnounthMoney(lang)));
                            } else {
                                switch (plugin.CashSystem.removeCash(p2, l, RemoveCashEvent.Reason.Player)) {
                                    case EventCancel -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    }
                                    case PlayerNotConnected -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    }
                                    case PlayerNotExist -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                    case Successful -> {
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminTakecash(lang)));
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("setcash") || cmd[1].toLowerCase().equals("sc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (l > 0) {
                                TransferResult tr = plugin.CashSystem.setCash(p2, l);
                                switch (tr) {
                                    case EventCancel -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    }
                                    case PlayerNotConnected -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    }
                                    case PlayerNotExist -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                    case Successful -> {
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminSetcash(lang)));
                                    }
                                }
                            } else {
                                player.sendTextMessage(plugin.Language.getStatus().getAmountBigger(lang));
                            }

                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        } catch (CashFormatExeption ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getAmountBigger(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("givebank") || cmd[1].toLowerCase().equals("gb")) {
                        PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (l > 0) {
                                if (pa != null) {
                                    switch (pa.addMoney(player, l, AddBankMoneyEvent.Reason.Command)) {
                                        case Successful ->
                                            player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminGivebank(lang)));
                                        case EventCancel ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                        case PlayerNotConnected ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                        default ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                } else {
                                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerHasNoAccount(lang)));
                                }
                            } else {
                                player.sendTextMessage(plugin.Language.getStatus().getAmountBigger(lang));
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }

                    }
                    if (cmd[1].toLowerCase().equals("takebank") || cmd[1].toLowerCase().equals("tb")) {
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (l > 0) {
                                PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                                if (pa != null) {
                                    switch (pa.removeMoney(player, l, RemoveBankMoneyEvent.Reason.Command)) {
                                        case Successful ->
                                            player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminTakebank(lang)));
                                        case EventCancel ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                        case NotEnoughMoney ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang)));
                                        case PlayerNotConnected ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                        default ->
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                } else {
                                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerHasNoAccount(lang)));
                                }
                            } else {
                                player.sendTextMessage(plugin.Language.getStatus().getAmountBigger(lang));
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("setbank") || cmd[1].toLowerCase().equals("sb")) {
                        long l = mFormat.getMoneyAsLong(cmd[3]);

                        PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);

                        if (pa != null) {
                            if (l >= pa.getMin()) {
                                pa.setMoney(cmd[3]);
                                player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminSetbank(lang)));
                            } else {
                                player.sendTextMessage(format.Color("red", "Amount must be more then " + plugin.moneyFormat.getMoneyAsFormatedString(player, pa.getMin()) + "!"));
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerHasNoAccount(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("npc")) {
                        if (cmd[2].toLowerCase().equals("create")) {

                            Npcs.NpcDefinition def = Definitions.getNpcDefinition("dummy");

                            Npc npc = World.spawnNpc(def.id, player.getPosition(), player.getRotation());
                            npc.setLocked(true);
                            npc.setName(cmd[3]);
                            npc.setInteractable(true);
                            try {
                                plugin.Bankystem.npcSystem.addNpc(npc, 1);
                            } catch (SQLException ex) {
                                player.sendTextMessage(format.Color("red", "ERR: Can not save npc to database!"));
                                Console.sendErr("Command (npc create)", "Can not save npc to database!");
                                Console.sendErr("Command (npc create)", ex.getMessage());
                                for (StackTraceElement ste : ex.getStackTrace()) {
                                    Console.sendErr("Command (npc create)", ste.toString());
                                }
                            }

                            plugin.Attribute.player.setSelectNpc(player, npc);
                        }
                    }
                }
                if (cmd.length >= 2) {
                    if (cmd[1].toLowerCase().equals("bank")) {
                        if (cmd.length >= 3) {
                            if (cmd[2].toLowerCase().equals("addatm")) {
                                if (cmd.length == 3) {
                                    plugin.GameObject.atm.createAtm(player, AtmUtils.AtmType.Standart);
                                } else if (cmd.length == 4) {
                                    switch (cmd[3].toLowerCase()) {
                                        case "in" -> {
                                            plugin.GameObject.atm.createAtm(player, AtmUtils.AtmType.In);
                                        }
                                        case "out" -> {
                                            plugin.GameObject.atm.createAtm(player, AtmUtils.AtmType.Out);
                                        }
                                        default -> {
                                            plugin.GameObject.atm.createAtm(player, AtmUtils.AtmType.Standart);
                                        }
                                    }
                                }
                            }
                            if (cmd.length == 3) {
                                if (cmd[2].toLowerCase().equals("atmlist")) {
                                    player.sendTextMessage("==== ATM-List ====");
                                    for (AtmObject atm : plugin.GameObject.atm.getAtmList()) {
                                        player.sendTextMessage("- ID: " + atm.getID());
                                    }
                                    player.sendTextMessage("==================");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
