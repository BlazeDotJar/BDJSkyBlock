package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.island.IslandDataWriter;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.result.AddMemberToIslandResult;
import de.bdj.sb.island.result.RemoveMemberFromIslandResult;
import de.bdj.sb.lobby.Lobby;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.session.ConfirmSession;
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
import java.util.Arrays;
import java.util.List;

public class ISCommand implements CommandExecutor, TabCompleter {

    public ISCommand() {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("is");

        for (String cmd : cmds) {
            SB.log("Registriere Command: /" + cmd);
            SB.getInstance().getCommand(cmd).setExecutor(this);
        }
    }

    int a = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player p) {
            if (cmd.getName().equalsIgnoreCase("is") && Perms.hasPermission(p, Perms.getPermission("is"))) {
                PlayerProfile pro = ProfileManager.getProfile(p.getUniqueId());
                switch (args.length) {
                    case 0:
                        if(Settings.useGui) {
                            GuiManager.openIslandDashboard(p);
                        } else {
                            if(pro.getIslandId() >= 1) {
                                IslandManager.getLoadedIslandProfile(pro.getIslandId()).teleport(p);
                            } else Chat.error(p, "Du hast keine Insel, zu der du dich teleportieren kannst.");
                        }
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase("create") && Perms.hasPermission(p, Perms.getPermission("is create"))) {
                            SkyBlockFunction.createIsland(p);
                        } else if(args[0].equalsIgnoreCase("ban") && Perms.hasPermission(p, Perms.getPermission("is ban"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is ban <Spielername> §fVon Insel bannen", XColor.c3 + "Verbanne einen Spieler von deiner Insel. Dieser kann deine Insel also nicht betreten." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is ban") : ""), "/is ban", false, false);
                        } else if(args[0].equalsIgnoreCase("help") && Perms.hasPermission(p, Perms.getPermission("is help"))) {
                            sendCommandHelp(sender, "is help");
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
                                    ConfirmSession cs = new ConfirmSession(p, "/is confirm", "/is deny", "Die Löschung deiner Insel wurde abgebrochen.", ConfirmSession.ConfirmReason.DELETE_OPERATION);
                                    cs.delayTime = 10; //The player will have 10 seconds to confirm the action
                                    cs.start();
                                    pro.addTempSession(cs);
                                } else Chat.info(p, XColor.c2 + "Nutze §f/is confirm " + XColor.c2 + "um die Löschung zu bestätigen");
                            }
                        } else if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> add §fMember hinzufügen", XColor.c3 + "Füge einen Spieler zu deiner Insel hinzu. Dieser kann ab diesen Zeitpunkt dann alles auf deiner Insel machen.\n" +
                                    "Beispielsweise kann dieser in Kisten schauen, bauen, abbauen.\n" +
                                    "Was er nicht kann ist, die Insel zu verwalten zum Beispiel: Member adden, removen.", "/is member <Spielername> add", false, false);

                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> remove §fMember entfernen", XColor.c3 + "Lösche einen Spieler von deiner Insel.", "/is member <Spielername> remove", false, false);
                        } else if(args[0].equalsIgnoreCase("confirm") && Perms.hasPermission(p, Perms.getPermission("is confirm"))) {
                            // General confirm command
                            if(ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey) != null) {
                                // Checking is the player has a opened session
                                ConfirmSession cs = (ConfirmSession) ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey);
                                if(cs != null) {
                                    if(cs.getReason() == ConfirmSession.ConfirmReason.MEMBER_OPERATION) {
                                        //The ConfirmSession is about a member operation. Means: The player is doing something with members. Example: accepting an invitation
                                        Player target = (Player) cs.getExtraData("target_player");
                                        Player inviter = (Player) cs.getExtraData("inviter_player");
                                        //Erst ausführen nach confirmation
                                        AddMemberToIslandResult adtir = SkyBlockFunction.addOnlineMember(inviter, target);
                                        if(adtir == AddMemberToIslandResult.SUCCESS_MEMBER_ADDED) {
                                            Chat.info(inviter, "Du hast den Spieler " + target.getName() + " zu deiner Insel hinzugefügt.", "Dieser Spieler kann nun mit /is zu eurer Insel gelangen.");
                                            Chat.info(target, "Du bist nun Mitglied der Insel " + ProfileManager.getProfile(target.getUniqueId()).getIslandId() + ", Besitzer: " + inviter.getName());
                                            cs.terminate();
                                        } else if(adtir == AddMemberToIslandResult.CANCELLED_TARGET_PLAYER_ALREADY_HAS_ISLAND) {
                                            Chat.error(p, "Du hast bereits eine Insel!");
                                            cs.terminate();
                                        } else Chat.error(p, "Irgendetwas ist mit der Bestätigung schiefgelaufen. Fehlercode: " + adtir.toString());
                                    } else if(cs.getReason() == ConfirmSession.ConfirmReason.DELETE_OPERATION) {
                                        //The ConfirmSession is about deleting the island.
                                        cs.terminate();
                                        pro = ProfileManager.getProfile(p.getUniqueId());
                                        pro.removeTempSession(Settings.confirmationSessionKey, cs);

                                        IslandProfile ip = IslandManager.getLoadedIslandProfile(pro.getIslandId());
                                        if(ip.getMembers().size() != 0) {
                                            Chat.error(p, "Du musst erst alle Member löschen um deine Insel löschen zu können!");
                                            return false;
                                        }

                                        boolean successfullyDeleted = IslandDataWriter.removeOwner(pro.getIslandId());
                                        if(successfullyDeleted) {
                                            pro.setIslandId(0);
                                            pro.save();
                                            Chat.info(p, "Deine Löschung war erfolgreich.");
                                            Lobby.teleport(p);
                                        } else {
                                            Chat.error(p, "Die Löschung deiner Insel hat nicht geklappt. Probiere es erneut oder kontaktiere das Serverpersonal.");
                                        }
                                    }
                                } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                            } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                        } else if(args[0].equalsIgnoreCase("deny") && Perms.hasPermission(p, Perms.getPermission("is deny"))) {
                            if(ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey) != null) {
                                ConfirmSession cs = (ConfirmSession) ProfileManager.getProfile(p.getUniqueId()).getTempSession(Settings.confirmationSessionKey);
                                if(cs != null) {
                                    if(cs.getReason() == ConfirmSession.ConfirmReason.MEMBER_OPERATION) {
                                        //The ConfirmSession is about a member operation. Means: The player is doing something with members. Example: accepting an invitation
                                        Player target = (Player) cs.getExtraData("target_player");
                                        Player inviter = (Player) cs.getExtraData("inviter_player");
                                        cs.terminate();
                                        Chat.info(inviter, "Die Einladung zu deiner Insel wurde von " + target.getName() + " abgebrochen.");
                                        Chat.info(p, "Du hast die Insel Einladung von " + inviter.getName() + " abgebrochen.");
                                    } else if(cs.getReason() == ConfirmSession.ConfirmReason.DELETE_OPERATION) {
                                        cs.terminate();
                                        pro = ProfileManager.getProfile(p.getUniqueId());
                                        pro.removeTempSession(Settings.confirmationSessionKey, cs);
                                        Chat.info(p, "Du hast deine Löschung abgelehnt.");
                                    }
                                } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                            } else Chat.error(p, "Es gibt keine offene Bestätigungsanfrage.");
                        } else if(args[0].equalsIgnoreCase("quit") && Perms.hasPermission(p, Perms.getPermission("is quit"))) {
                            PlayerProfile pp = ProfileManager.getProfile(p.getUniqueId());
                            int id = pp.getIslandId();
                            IslandProfile ip = IslandManager.getLoadedIslandProfile(id);
                            if(ip == null) {
                                Chat.error(p, "Du bist kein Member auf einer Insel.");
                                return false;
                            }
                            if(!ip.getMembers().contains(p.getUniqueId().toString())) {
                                Chat.error(p, "Du bist kein Member auf einer Insel.");
                                return false;
                            }
                            ip.removeMember(p.getUniqueId().toString());
                            IslandDataWriter.removeMemberFromIsland(id, p.getUniqueId());
                            Chat.info(p, "Du hast deine Insel verlassen und kannst dir nun mit /is create eine neue erstellen.");
                            Lobby.teleport(p);
                        }
                        break;
                    case 2:
                        if(args[0].equalsIgnoreCase("ban") && Perms.hasPermission(p, Perms.getPermission("is ban"))) {
                            //TODO:
                        } else if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> add §fMember hinzufügen", XColor.c3 + "Füge einen Spieler zu deiner Insel hinzu. Dieser kann ab diesen Zeitpunkt dann alles auf deiner Insel machen.\n" +
                                    "Beispielsweise kann dieser in Kisten schauen, bauen, abbauen.\n" +
                                    "Was er nicht kann ist, die Insel zu verwalten zum Beispiel: Member adden, removen.", "/is member <Spielername> add", false, false);

                            Chat.sendSuggestCommandMessage(p, XColor.c2 + "/is member <Spielername> remove §fMember entfernen", XColor.c3 + "Lösche einen Spieler von deiner Insel.", "/is member <Spielername> remove", false, false);
                        }
                        break;
                    case 3:
                        if(args[0].equalsIgnoreCase("member") && Perms.hasPermission(p, Perms.getPermission("is member"))) {
                            if(args[2].equalsIgnoreCase("add")) {
                                String spielername = args[1];
                                if(spielername.equalsIgnoreCase(p.getName())) {
                                    Chat.error(p, "Du kannst dich nicht zu deiner eigenen Insel hinzufügen.");
                                    return false;
                                }
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

                                    if(IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId()).isMember(target.getUniqueId())) {
                                        Chat.error(p, "Dieser Spieler ist bereits Member auf deiner Insel.");
                                        return false;
                                    }
                                    ConfirmSession cs = new ConfirmSession(target, "/is confirm", "/is deny", "Das Hinzufügen eines Members wurde abgebrochen.", ConfirmSession.ConfirmReason.MEMBER_OPERATION);
                                    cs.addExtraData("inviter_player", p);
                                    cs.addExtraData("target_player", target);
                                    ProfileManager.getProfile(target.getUniqueId()).addTempSession(cs);
                                    Chat.info(target, XColor.c2 + "Du wurdest von §f" + p.getName() + XColor.c2 + " auf eine gemeinsame Insel eingeladen.");
                                    cs.start();
                                    Chat.info(p, "Du hast " + target.getName() + " zu deiner Insel eingeladen.");
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
                                        IslandProfile ip = IslandManager.getLoadedIslandProfile(pro.getIslandId());
                                        if(ip.getArea().isIn(target.getLocation())) Lobby.teleport(target);
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
                Chat.sendClickableMessage(p, XColor.c2 + " /is §fInsel Menü", XColor.c2 + "Öffne das Insel Menü." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is") : ""), "/is", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /is create §fInsel erstellen", XColor.c2 + "Erstelle eine Insel." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is create") : ""), "/is create", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /is help §fCommand Hilfe", XColor.c2 + "Liste alle /is Befehle auf um dir einen Überblick zu beschaffen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is help") : ""), "/is help", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is delete §fInsel löschen", XColor.c2 + "Lösche deine Insel. Dies kann nicht rückgängig gemacht werden!" + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is delete") : ""), "/is delete", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is member §fMember verwalten", XColor.c2 + "Verwalte Member deiner Insel. Füge Spieler hinzu, entferne diese wieder, etc." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is member") : ""), "/is member", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is quit §fInsel verlassen", XColor.c2 + "Wenn du Member einer Insel bist, kannst du sie hiermit verlassen um ein eigene zu erstellen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is quit") : ""), "/is quit", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /is ban <Spielername> §fVon Insel bannen", XColor.c2 + "Verbanne einen Spieler von deiner Insel. Dieser kann deine Insel also nicht betreten." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("is ban") : ""), "/is ban", false, false);
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
                    case 1:
                        return Arrays.asList("confirm", "deny", "create", "help", "delete", "member", "ban", "quit");
                    case 2:
                        if(args[0].equalsIgnoreCase("ban")) {
                            return null;
                        } else if(args[0].equalsIgnoreCase("member")) {
                            return null;
                        }
                    case 3:
                        if(args[0].equalsIgnoreCase("member")) {
                            return Arrays.asList("add", "remove");
                        }
                            break;
                }
            }
        }
        return List.of();
    }

}
