package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandCreator;
import de.bdj.sb.island.IslandGenManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.result.AddMemberToIslandResult;
import de.bdj.sb.island.result.IslandCreatorReserveResult;
import de.bdj.sb.island.result.RemoveMemberFromIslandResult;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.session.ConfirmSession;
import de.bdj.sb.session.TempSession;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Perms;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
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
                                IslandManager.reloadFile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());
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

                            if(isp.isClaimed()) {
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
                        if(args[0].equalsIgnoreCase("help") && Perms.hasPermission(p, Perms.getPermission("is help"))) {
                            sendCommandHelp(p, "is help");
                        } else if(args[0].equalsIgnoreCase("delete") && Perms.hasPermission(p, Perms.getPermission("is delete"))) {
                            pro = ProfileManager.getProfile(p.getUniqueId());
                            if(pro.getIslandId() < 1) {
                                Chat.error(p, "Du hast keine Insel.");
                            } else {
                                IslandProfile ip = IslandManager.getIslandDataFromIndexFile(pro.getIslandId());
                                if(ip.getOwnerUuid() != null && !ip.getOwnerUuid().toString().equals(p.getUniqueId().toString())) {
                                    Chat.error(p, "Die Insel, die du versuchst zu löschen, gehört nicht dir.");
                                    return false;
                                }
                                if(pro.getTempSession(Settings.confirmationSessionKey) == null) {
                                    ConfirmSession cs = new ConfirmSession(p, "/is delete confirm", "/is delete deny", "Die Löschung deiner Insel wurde abgebrochen.");
                                    cs.delayTime = 10; //The player will have 10 seconds to confirm the action
                                    cs.start();
                                    pro.addTempSession(cs);
                                } else Chat.info(p, XColor.c2 + "Nutze §f/is delete confirm " + XColor.c2 + "um die Löschung zu bestätigen");
                            }
                        } else if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> add §fMember hinzufügen", XColor.c3 + "Füge einen Spieler zu deiner Insel hinzu. Dieser kann ab diesen Zeitpunkt dann alles auf deiner Insel machen.\n" +
                                    "Beispielsweise kann dieser in Kisten schauen, bauen, abbauen.\n" +
                                    "Was er nicht kann ist, die Insel zu verwalten zum Beispiel: Member adden, removen.", "/is member <Spielername> add", false, false);

                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> remove §fMember entfernen", XColor.c3 + "Lösche einen Spieler von deiner Insel.", "/is member <Spielername> remove", false, false);
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
                        } else if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            if(args[1].equalsIgnoreCase("confirm")) {
                                if(ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey) != null) {
                                    ConfirmSession cs = (ConfirmSession) ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey);
                                    if(cs != null) {
                                        Player target = (Player) cs.getExtraData("target_player");
                                        Player inviter = (Player) cs.getExtraData("inviter_player");
                                        //Erst ausführen nach confirmation
                                        AddMemberToIslandResult adtir = SkyBlockFunction.addOnlineMember(inviter, target);
                                        if(adtir == AddMemberToIslandResult.SUCCESS_MEMBER_ADDED) {
                                            Chat.info(inviter, "Du hast den Spieler " + target.getName() + " zu deiner Insel hinzugefügt.", "Dieser Spieler kann nun mit /is zu eurer Insel gelangen.");
                                            Chat.info(target, "Du bist nun Mitglied der Insel " + ProfileManager.getProfile(target.getUniqueId()).getIslandId() + ", Besitzer: " + inviter.getUniqueId());
                                        } else Chat.error(p, "Irgendetwas ist mit der Bestätigung schiefgelaufen. Fehlercode: " + adtir.toString());

                                    } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                                } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                            } else if(args[1].equalsIgnoreCase("deny")) {
                                Chat.info(p, "Nope");
                            } else {
                                Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> add §fMember hinzufügen", XColor.c3 + "Füge einen Spieler zu deiner Insel hinzu. Dieser kann ab diesen Zeitpunkt dann alles auf deiner Insel machen.\n" +
                                        "Beispielsweise kann dieser in Kisten schauen, bauen, abbauen.\n" +
                                        "Was er nicht kann ist, die Insel zu verwalten zum Beispiel: Member adden, removen.", "/is member <Spielername> add", false, false);

                                Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> remove §fMember entfernen", XColor.c3 + "Lösche einen Spieler von deiner Insel.", "/is member <Spielername> remove", false, false);
                            }
                        }
                        break;
                    case 3:
                        if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            if(args[2].equalsIgnoreCase("add")) {
                                String spielername = args[1];
                                Player target = null;
                                for(Player t : Bukkit.getOnlinePlayers()) {
                                    if(t.getName().equalsIgnoreCase(spielername)) {
                                        target = t;
                                    }
                                }

                                if(target != null) {
                                    if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() < 1) {
                                        Chat.error(p, "Du besitzt keine Insel.");
                                        return false;
                                    }
                                    ConfirmSession cs = new ConfirmSession(target, "/is confirm", "/is deny", "Das Hinzufügen eines Members wurde abgebrochen.");
                                    cs.addExtraData("inviter_player", p);
                                    cs.addExtraData("target_player", target);
                                    ProfileManager.getProfile(target.getUniqueId()).addTempSession(cs);
                                    cs.start();
                                } else Chat.error(p, "Der Spieler ist nicht online.");
                            } else if(args[2].equalsIgnoreCase("remove")) {
                                String spielername = args[1];
                                Player target = null;
                                for(Player t : Bukkit.getOnlinePlayers()) {
                                    if(t.getName().equalsIgnoreCase(spielername)) {
                                        target = t;
                                    }
                                }

                                if(target != null) {
                                    if(SkyBlockFunction.removeOnlineMember(p, target) == RemoveMemberFromIslandResult.SUCCESS_MEMBER_REMOVE) {
                                        Chat.info(p, "Du hast den Spieler " + spielername + " von deiner Insel entfernt.");
                                    }
                                } else Chat.error(p, "Der Spieler ist nicht online.");
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    public void sendCommandHelp(CommandSender sender, String command) {
        if(command.equalsIgnoreCase("is help")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren BDJSkyBlock Befehle:");
                Chat.sendClickableMessage(p, XColor.c2 + " /is §fInsel create/teleport", XColor.c3 + "Erstelle eine Insel oder teleportiere dich zu dieser, wenn du bereits eine Insel hast." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is") : ""), "/is", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /is help §fCommand Hilfe", XColor.c3 + "Liste alle /is Befehle auf um dir einen Überblick zu beschaffen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is help") : ""), "/is help", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is delete §fInsel löschen", XColor.c3 + "Lösche deine Insel. Dies kann nicht rückgängig gemacht werden!" + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is delete") : ""), "/is delete", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is member §fMember verwalten", XColor.c3 + "Verwalte Member deiner Insel. Füge Spieler hinzu, entferne diese wieder, etc." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is member") : ""), "/is member", false, false);
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
