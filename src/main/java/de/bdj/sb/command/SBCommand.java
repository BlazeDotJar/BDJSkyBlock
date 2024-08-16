package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.lobby.Waitlobby;
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

import java.util.*;

public class SBCommand implements CommandExecutor, TabCompleter {

    public SBCommand() {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("sb");
        cmds.add("sbdev");

        for(String cmd : cmds) {
            SB.log("Registriere Command: /" + cmd);
            SB.getInstance().getCommand(cmd).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender instanceof Player p) {
            if(cmd.getName().equalsIgnoreCase("sb") && Perms.hasPermission(p, Perms.getPermission("sb"))) {
                switch(args.length) {
                    case 0:
                        sendCommandHelp(sender, "sb");
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase("rl") && Perms.hasPermission(p, Perms.getPermission("sb rl"))) {
                            Settings.reload();
                            Chat.info(p, "Die Settings wurden neu geladen!");
                        } else if(args[0].equalsIgnoreCase("twl") && Perms.hasPermission(p, Perms.getPermission("sb twl"))) {
                            Waitlobby.teleport(p);
                            Chat.info(p, "Du wurdest zur Wartelobby teleportiert.");
                        } else if(args[0].equalsIgnoreCase("swl") && Perms.hasPermission(p, Perms.getPermission("sb swl"))) {
                            TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                            if(ts != null) ts.terminate();
                            ConfirmSession cs = new ConfirmSession(p, "/sb swl confirm", "/sb swl deny", "Die Position der Wartelobby wurde nicht erneuert. Du warst zu langsam.");
                            ProfileManager.getProfile(p.getUniqueId()).addTempSession(cs);
                        }
                            break;
                    case 2:
                        if(args[0].equalsIgnoreCase("swl") && Perms.hasPermission(p, Perms.getPermission("sb swl"))) {
                            if(args[1].equalsIgnoreCase("confirm")) {
                                TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                                if(ts != null) ts.terminate();
                                Waitlobby.setNewLocation(p.getLocation());
                                Chat.info(p, "Du hast die Location der Wartelobby neu gesetzt");
                            } else if(args[1].equalsIgnoreCase("deny")) {
                                TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                                if(ts != null) ts.terminate();
                                Chat.info(p, XColor.c2 + "Vorgang: §fWartelobby Position neusetzen " + XColor.c2 + "wurde abgelehnt.");
                            }
                        }
                        break;
                }
            } else if(cmd.getName().equalsIgnoreCase("sbdev") && p.getUniqueId().toString().equals("242dad39-544a-4c3a-8d61-17a38e004a6f")) {
                switch(args.length) {
                    case 0:
                        sendCommandHelp(sender, "sbdev");
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase("listprofiles") || args[0].equalsIgnoreCase("lp")) {
                            HashMap<UUID, PlayerProfile> profiles = ProfileManager.getProfiles();
                            Chat.info(p, "Geladene PlayerProfiles");
                            for(UUID uuid : profiles.keySet()) {
                                PlayerProfile pro = profiles.get(uuid);
                                Chat.sendHoverableCommandHelpMessage(p, " -> §e" + uuid.toString(),
                                        XColor.c1 + "UUID: §f" + uuid.toString() + "\n" +
                                        XColor.c1 + "Last Joined: §f" + pro.getLastJoin(), false, false);
                            }
                        } else if(args[0].equalsIgnoreCase("listisland") || args[0].equalsIgnoreCase("li")) {
                            Chat.info(p, "Gebe einen Bereich an Inseln an, den du gerne auslesen möchtest.", "Als Beispiel: /sbdev li 0-50", "Du würdest also die Inseln mit einer Id zwischen 0 und 50 auslesen.");
                        }
                        break;
                    case 2:
                        if(args[0].equalsIgnoreCase("listisland") || args[0].equalsIgnoreCase("li")) {
                            String range = args[1];
                            if(!range.contains("-")) {
                                Chat.error(p, "Das angegebene Format für deinen gewünschten Bereich ist falsch.", "Format: [von]-[bis]", "Beachte das bei der Bereichsangabe keine Leerzeichen verwendet werden dürfen!");
                                return false;
                            }
                            String[] split = range.split("-");
                            int von = Integer.parseInt(split[0]);
                            int bis = Integer.parseInt(split[1]);
                            if(von > bis) {
                                // Reverse von and bis so that von is the minimum and bis is the maximum
                                int a = Math.max(von, bis);
                                von = Math.min(von, bis);
                                bis = a;
                            }
                            if(bis - von > 90) {
                                bis = von + 90;
                                Chat.info(p, "Die maximale Bereichspanne wird aufgrund der Chatlänge auf 90 reduziert. Der Bereich wurde korrigiert.");
                            }
                            Chat.info(p, "Hier sind alle Inseln von " + von + " bis " +  bis);
                            for(int i = von; i != bis + 1; i++) {
                                IslandProfile ip = IslandManager.getIslandDataFromIndexFile(i);
                                Chat.sendClickableMessage(p, " -> ID-" + i,
                                        XColor.c3 + "Island-Id: §f" + ip.getIslandId() + "\n" +
                                        XColor.c3 + "Owner UUID: §e" + (ip.getOwnerUuid() == null ? "nicht vorhanden" : ip.getOwnerUuid().toString()) + "\n" +
                                        XColor.c3 + "Is claimed: " + (ip.isClaimed() ? "§cBesetzt" : "§aFrei") + "\n" +
                                        XColor.c3 + "X: §f" + ip.getX() + "\n" +
                                        XColor.c3 + "Y: §f" + IslandManager.islandY + "\n" +
                                        XColor.c3 + "Z: §f" + ip.getZ() + "\n" +
                                        "§bKlicke, um dich zu dieser Insel zu teleportieren!", "/minecraft:teleport " + p.getName() + " " + ip.getX() + " " + IslandManager.islandY + " " + ip.getZ(), false, false);
                            }
                        }
                        break;
                }
            }
        }

        return false;
    }

    public void sendCommandHelp(CommandSender sender, String command) {
        if(command.equalsIgnoreCase("sb")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren BDJSkyBlock Befehle:");
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb §fHilfe erhalten", XColor.c3 + "Erhalte eine klickbare Liste mit allen SkyBlock Befehlen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb") : ""), "/sb", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb rl §fSettings laden", XColor.c3 + "Lade die Einstellungen aus allen Config-Dateien von BDJSkyBlock neu." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb rl") : ""), "/sb rl", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sb twl §fZur Wartelobby", XColor.c3 + "Teleportiere dich zur Wartelobby." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb twl") : ""), "/sb twl", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb swl §fWartelobby setzen", XColor.c3 + "Setze die Location für die Wartelobby. Die Wartelobby wird von Spielern betreten,\ndie darauf warten dass ihre Insel fertig generiert wurde." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb swl") : ""), "/sb swl", false, false);
            }
        } else if(command.equalsIgnoreCase("sbdev")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren SBDeveloper Befehle:");
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev lp §fLoaded PlayerProfiles", XColor.c3 + "", "/sbdev lp", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev li §fList IslandData", XColor.c3 + "", "/sbdev li", false, false);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {

        if(cmd.getName().equalsIgnoreCase("sb") && args.length == 1) return Arrays.asList("rl", "twl", "swl");
        else {
            if(sender instanceof Player p) {
                if(cmd.getName().equalsIgnoreCase("sbdev") && p.getUniqueId().toString().equals("242dad39-544a-4c3a-8d61-17a38e004a6f")) {
                    if(args.length == 1) return Arrays.asList("lp", "li");
                    else if(args.length == 2) {
                        if(args[0].equalsIgnoreCase("li")) return Arrays.asList("0-15");
                    }
                }
            }
        }
        return List.of();
    }
}
