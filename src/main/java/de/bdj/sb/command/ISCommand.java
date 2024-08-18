package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.*;
import de.bdj.sb.island.result.IslandCreatorReserveResult;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.session.ConfirmSession;
import de.bdj.sb.session.TempSession;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Perms;
import de.bdj.sb.utlility.XColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ISCommand implements CommandExecutor, TabCompleter {

    public ISCommand() {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("is");

        for(String cmd : cmds) {
            SB.log("Registriere Command: /" + cmd);
            SB.getInstance().getCommand(cmd).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player p) {
            if (cmd.getName().equalsIgnoreCase("is") && Perms.hasPermission(p, Perms.getPermission("is"))) {
                switch (args.length) {
                    case 0:
                        PlayerProfile pro = ProfileManager.getProfile(p.getUniqueId());
                        if(pro.getIslandId() < 1) {
                            //Create an island
                            IslandCreator ic = new IslandCreator(p);
                            IslandCreatorReserveResult icrr = ic.reserve();
                            if(icrr == IslandCreatorReserveResult.SUCCESS_ISLAND_RESERVED) {
                                //
                                Chat.info(p, "§3Island created!");
                                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
                            } else if(icrr == IslandCreatorReserveResult.CANCELLED_PLAYER_IS_ALREADY_OWNING_AN_ISLAND) {
                                //
                                Chat.info(p, "Du hast bereits eine Insel: CANCELLED_PLAYER_IS_ALREADY_OWNING_AN_ISLAND");
                                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
                            } else if(icrr == IslandCreatorReserveResult.CANCELLED_OLD_PLAYER_ISLAND_FILE_STILL_EXISTS) {
                                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
                            }
                        } else {
                            //Teleport to the players island
                            IslandProfile ip = IslandManager.getIslandDataFromIndexFile(pro.getIslandId());
                            IslandProfile isp = IslandManager.getIslandDataFromIndexFile(ip.getIslandId());

                            if(isp.isClaimed() && isp.getOwnerUuid().toString().equals(p.getUniqueId().toString())) {
                                Chat.info(p, "Du wirst zu deiner Insel teleportiert...");
                                isp.teleport(p);
                            } else {
                                Chat.error(p, "Es tut uns leid. Etwas ist schief gelaufen mit deiner Insel.");
                                Chat.sendOperatorMessage("Der Spieler " + p.getName() + " hat Probleme mit seiner Insel. Er/Sie versuchte vermutlich mit /is zur Insel zu gelangen.",
                                        "Möglicherweise fehlt die Inseldatei '" + SB.name() + "/islands/" + p.getUniqueId().toString() + ".yml'.",
                                        "Es kann auch sein, dass in der " + Settings.islandIndexFileName + " Datei die Werte für 'Claimed' und 'Owner UUID' fehlen.");
                            }

                        }
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase("delete") && Perms.hasPermission(p, Perms.getPermission("is delete"))) {
                            pro = ProfileManager.getProfile(p.getUniqueId());
                            if(pro.getIslandId() < 1) {
                                Chat.error(p, "Du hast keine Insel.");
                            } else {
                                if(pro.getTempSession(Settings.confirmationSessionKey) == null) {
                                    ConfirmSession cs = new ConfirmSession(p, "/is delete confirm", "/is delete deny", "Die Löschung deiner Insel wurde abgebrochen.");
                                    cs.delayTime = 10; //The player will have 10 seconds to confirm the action
                                    cs.start();
                                    pro.addTempSession(cs);
                                } else Chat.info(p, XColor.c2 + "Nutze §f/is delete confirm " + XColor.c2 + "um die Löschung zu bestätigen");
                            }
                        }
                        break;
                    case 2:
                        if(args[0].equalsIgnoreCase("delete") && Perms.hasPermission(p, Perms.getPermission("is delete"))) {
                            pro = ProfileManager.getProfile(p.getUniqueId());
                            TempSession ts = pro.getTempSession(Settings.confirmationSessionKey);
                            if(ts != null && ts instanceof ConfirmSession) {
                                ts = (ConfirmSession) ts;
                                if(args[1].equalsIgnoreCase("confirm")) {
                                    ts.terminate();
                                    pro.removeTempSession(Settings.confirmationSessionKey, ts);

                                    boolean successfullyDeleted = IslandManager.removeOwner(pro.getIslandId());
                                    if(successfullyDeleted) {
                                        pro.setIslandId(0);
                                        pro.save();
                                        Chat.info(p, "Deine Löschung war erfolgreich.");
                                    } else {
                                        Chat.error(p, "Die Löschung deiner Insel hat nicht geklappt. Probiere es erneut oder kontaktiere das Serverpersonal.");
                                    }

                                } else if(args[1].equalsIgnoreCase("deny")) {
                                    ts.stop();
                                    Chat.info(p, "Du hast deine Löschung abgelehnt.");
                                }
                            } else if(ts == null) {
                                Chat.info(p, "Du musst vorerst die Löschung deiner Insel auslösen. Nutze dafür §f/is delete");
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    public void sendCommandHelp(CommandSender sender, String command) {
        if(command.equalsIgnoreCase("is")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren BDJSkyBlock Befehle:");
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is §fInsel create/teleport", XColor.c3 + "Erstelle eine Insel oder teleportiere dich zu dieser, wenn du bereits eine Insel hast." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is") : ""), "/is", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is delete §fInsel löschen", XColor.c3 + "Lösche deine Insel. Dies kann nicht rückgängig gemacht werden!" + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is delete") : ""), "/is delete", false, false);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        //TODO: BUG_1
        //Every player gets a completion, even without the needed permissions!
        if(sender instanceof Player p) {
            if(cmd.getName().equalsIgnoreCase("is")) {
                switch(args.length) {
                    case 0:
                        break;
                }
            }
        }
        return List.of();
    }

}
