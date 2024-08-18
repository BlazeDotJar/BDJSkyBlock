package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.island.IslandCreator;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Perms;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class SBDEVCommand implements CommandExecutor, TabCompleter {

    public SBDEVCommand() {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("sbdev");

        for(String cmd : cmds) {
            SB.log("Registriere Command: /" + cmd);
            SB.getInstance().getCommand(cmd).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player p) {
            if(cmd.getName().equalsIgnoreCase("sbdev") && p.getUniqueId().toString().equals("242dad39-544a-4c3a-8d61-17a38e004a6f")) {
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
                                                XColor.c1 + "Last Joined: §f" + pro.getLastJoin() + "\n" +
                                        XColor.c1 + "Island ID: §f" + pro.getIslandId(), false, false);
                            }
                        } else if(args[0].equalsIgnoreCase("listisland") || args[0].equalsIgnoreCase("li")) {
                            Chat.info(p, "Gebe einen Bereich an Inseln an, den du gerne auslesen möchtest.", "Als Beispiel: /sbdev li 0-50", "Du würdest also die Inseln mit einer Id zwischen 0 und 50 auslesen.");
                        } else if(args[0].equalsIgnoreCase("walls") || args[0].equalsIgnoreCase("w")) {
                            Chat.info(p, "Gebe als zweites Argument eine Insel ID ein.");
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
                        } else if(args[0].equalsIgnoreCase("walls") || args[0].equalsIgnoreCase("w")) {
                            int islandId = Integer.parseInt(args[1]);
                            IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
                            Location l = ip.getIslandLocation();
                            l.getBlock().setType(Material.RED_WOOL);
                            for(int i = 0; i != 200; i++) {
                                l.add(1, 0, 0);
                                l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != 200; i++) {
                                l.add(0, 0, 1);
                                l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != 200; i++) {
                                l.add(-1, 0, 0);
                                l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != 200; i++) {
                                l.add(0, 0, -1);
                                l.getBlock().setType(Material.RED_WOOL);
                            }
                            Chat.info(p, "Walls für Insel " + islandId + " erstellt!");
                        }
                        break;
                }
            }
        }
        return false;
    }

    public void sendCommandHelp(CommandSender sender, String command) {
        if(command.equalsIgnoreCase("sbdev")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren SBDeveloper Befehle:");
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev lp §fLoaded PlayerProfiles", XColor.c3 + "Zeige alle geladenen Spielerprofile an.", "/sbdev lp", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev li §fList IslandData", XColor.c3 + "Zeige dir alle Insel-Daten von Inseln mit einer ID zwischen <von-bis> an.", "/sbdev li", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev walls §fCreate island walls", XColor.c3 + "Lasse dir die Borders einer Insel anzeigen / bauen", "/sbdev walls", false, false);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        //TODO: BUG_1
        //Every player gets a completion, even without the needed permissions!
        if(sender instanceof Player p) {
            if(cmd.getName().equalsIgnoreCase("sbdev") && p.getUniqueId().toString().equals("242dad39-544a-4c3a-8d61-17a38e004a6f")) {
                switch(args.length) {
                    case 0:
                        break;
                    case 1:
                        return Arrays.asList("lp", "li");
                    case 2:
                        if(args[0].equalsIgnoreCase("li")) return Arrays.asList("0-15");
                        break;
                }
            }
        }
        return List.of();
    }

}
