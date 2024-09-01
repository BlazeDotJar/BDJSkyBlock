package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.island.IslandCreator;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.lobby.Lobby;
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
import java.util.concurrent.ConcurrentHashMap;

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
                            ConcurrentHashMap<UUID, PlayerProfile> profiles = ProfileManager.getProfiles();
                            Chat.info(p, "Geladene PlayerProfiles");
                            for(UUID uuid : profiles.keySet()) {
                                PlayerProfile pro = profiles.get(uuid);
                                IslandProfile ip = IslandManager.getIslandDataFromIndexFile(pro.getIslandIsCurrentIn());
                                Chat.sendClickableMessage(p, " -> §e" + uuid.toString(),
                                        XColor.c1 + "UUID: §f" + uuid.toString() + "\n" +
                                                XColor.c1 + "Last Joined: §f" + pro.getLastJoin() + "\n" +
                                                XColor.c1 + "Currently in IslandID: §f" + pro.getIslandIsCurrentIn() + "\n" +
                                        XColor.c1 + "Island ID: §f" + pro.getIslandId(), (ip == null ? "" : "/minecraft:tp " + ip.getX() + " " + IslandManager.islandY + " " + ip.getZ()), false, false);
                            }
                        } else if(args[0].equalsIgnoreCase("listisland") || args[0].equalsIgnoreCase("li")) {
                            Chat.info(p, "Gebe einen Bereich an Inseln an, den du gerne auslesen möchtest.", "Als Beispiel: /sbdev li 0-50", "Du würdest also die Inseln mit einer Id zwischen 0 und 50 auslesen.");
                        } else if(args[0].equalsIgnoreCase("walls") || args[0].equalsIgnoreCase("w")) {
                            Chat.info(p, "Gebe als zweites Argument eine Insel ID ein.");
                        } else if(args[0].equalsIgnoreCase("hub") || args[0].equalsIgnoreCase("h")) {
                            Lobby.teleport(p);
                        } else if(args[0].equalsIgnoreCase("debug")) {
                            Settings.pluginDeveloperHelpMode = !Settings.pluginDeveloperHelpMode;
                            Chat.info(p, "Plugin Developer Mode: §f" + Settings.pluginDeveloperHelpMode);
                        } else if(args[0].equalsIgnoreCase("tools")) {
                            GuiManager.openDeveloperGui(p);
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
                            ConcurrentHashMap<Integer, IslandProfile> profiles = IslandManager.getProfiles();
                            for(int i : profiles.keySet()) {
                                if(i >= von && i <= bis) {
                                    IslandProfile ip = profiles.get(i);
                                    if(ip == null) continue;
                                    Chat.sendClickableMessage(p, " -> ID-" + i,
                                            XColor.c3 + "Island-Id: §f" + ip.getIslandId() + "\n" +
                                                    XColor.c3 + "Owner UUID: §e" + (ip.getOwnerUuid() == null ? "nicht vorhanden" : ip.getOwnerUuid().toString()) + "\n" +
                                                    XColor.c3 + "Is claimed: " + (ip.isClaimed() ? "§cBesetzt" : "§aFrei") + "\n" +
                                                    XColor.c3 + "X: §f" + ip.getX() + "\n" +
                                                    XColor.c3 + "Y: §f" + IslandManager.islandY + "\n" +
                                                    XColor.c3 + "Z: §f" + ip.getZ() + "\n" +
                                                    XColor.c3 + "Members: §f" + ip.getMembers().toString() + "\n" +
                                                    XColor.c3 + "Banned Players: §f" + ip.getBannedPlayers().toString() + "\n" +
                                                    XColor.c3 + "Properties: §f" + ip.getProperties().toString() + "\n" +
                                                    XColor.c3 + "Braucht Bereinigung: §f" + ip.needCleearing() + "\n" +
                                                    "§bKlicke, um dich zu dieser Insel zu teleportieren!", "/minecraft:teleport " + p.getName() + " " + ip.getX() + " " + IslandManager.islandY + " " + ip.getZ(), false, false);
                                } else if(i > bis) break;
                            }
                            /*
                                                        for(int i = von; i != bis + 1; i++) {
                                IslandProfile ip = IslandManager.getIslandDataFromIndexFile(i);
                                if(ip == null) continue;
                                Chat.sendClickableMessage(p, " -> ID-" + i,
                                        XColor.c3 + "Island-Id: §f" + ip.getIslandId() + "\n" +
                                                XColor.c3 + "Owner UUID: §e" + (ip.getOwnerUuid() == null ? "nicht vorhanden" : ip.getOwnerUuid().toString()) + "\n" +
                                                XColor.c3 + "Is claimed: " + (ip.isClaimed() ? "§cBesetzt" : "§aFrei") + "\n" +
                                                XColor.c3 + "X: §f" + ip.getX() + "\n" +
                                                XColor.c3 + "Y: §f" + IslandManager.islandY + "\n" +
                                                XColor.c3 + "Z: §f" + ip.getZ() + "\n" +
                                                XColor.c3 + "Braucht Bereinigung: §f" + ip.needCleearing() + "\n" +
                                                "§bKlicke, um dich zu dieser Insel zu teleportieren!", "/minecraft:teleport " + p.getName() + " " + ip.getX() + " " + IslandManager.islandY + " " + ip.getZ(), false, false);
                            }
                             */
                        } else if(args[0].equalsIgnoreCase("walls") || args[0].equalsIgnoreCase("w")) {
                            int islandId = Integer.parseInt(args[1]);
                            IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
                            Location l = ip.getIslandLocation().clone();
                            l.getBlock().setType(Material.RED_WOOL);
                            int xMin = ip.getArea().getxMin();
                            int xMax = ip.getArea().getxMax();
                            int zMin = ip.getArea().getzMin();
                            int zMax = ip.getArea().getzMax();

                            for(int i = xMin; i != xMax; i++) {
                                l.add(1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.LIME_WOOL);
                            }
                            for(int i = zMin; i != zMax; i++) {
                                l.add(0, 0, 1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.LIME_WOOL);
                            }
                            for(int i = xMin; i != xMax; i++) {
                                l.add(-1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.LIME_WOOL);
                            }
                            for(int i = zMin; i != zMax; i++) {
                                l.add(0, 0, -1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.LIME_WOOL);
                            }

                            l = ip.getIslandLocation().clone();
                            l.setY(-64);
                            for(int i = -64; i != 320; i++) {
                                l.add(0, 1, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.YELLOW_WOOL);
                            }
                            l.add(IslandManager.islandDiameter, 0, 0);
                            l.setY(-64);
                            for(int i = -64; i != 320; i++) {
                                l.add(0, 1, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.YELLOW_WOOL);
                            }
                            l.add(0, 0, IslandManager.islandDiameter);
                            l.setY(-64);
                            for(int i = -64; i != 320; i++) {
                                l.add(0, 1, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.YELLOW_WOOL);
                            }
                            l.add(-IslandManager.islandDiameter, 0, 0);
                            l.setY(-64);
                            for(int i = -64; i != 320; i++) {
                                l.add(0, 1, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.YELLOW_WOOL);
                            }
                            /*

                                                        for(int i = 0; i != IslandManager.islandDiameter; i++) {
                                l.add(1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != IslandManager.islandDiameter; i++) {
                                l.add(0, 0, 1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != IslandManager.islandDiameter; i++) {
                                l.add(-1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != IslandManager.islandDiameter; i++) {
                                l.add(0, 0, -1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }

                             */
                            Chat.info(p, "Walls für Insel " + islandId + " erstellt!");
                        } else if(args[0].equalsIgnoreCase("quarter") || args[0].equalsIgnoreCase("q")) {
                            int islandId = Integer.parseInt(args[1]);
                            IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
                            Location l = ip.getIslandLocation();
                            l.getBlock().setType(Material.RED_WOOL);
                            for(int i = 0; i != (IslandManager.islandDiameter / 2); i++) {
                                l.add(1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != (IslandManager.islandDiameter / 2); i++) {
                                l.add(0, 0, 1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != (IslandManager.islandDiameter / 2); i++) {
                                l.add(-1, 0, 0);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
                            }
                            for(int i = 0; i != (IslandManager.islandDiameter / 2); i++) {
                                l.add(0, 0, -1);
                                if(l.getBlock().getType() == Material.AIR) l.getBlock().setType(Material.RED_WOOL);
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
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev lp §fLoaded PlayerProfiles", XColor.c2 + "Zeige alle geladenen Spielerprofile an.", "/sbdev lp", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev li §fList IslandData", XColor.c2 + "Zeige dir alle Insel-Daten von Inseln mit einer ID zwischen <von-bis> an.", "/sbdev li", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sbdev walls §fCreate island walls", XColor.c2 + "Lasse dir die Borders einer Insel anzeigen / bauen", "/sbdev walls", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sbdev quarter §fCreate island quarter walls", XColor.c2 + "Erstelle ein Viertel der Insel Border", "/sbdev quarter", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sbdev hub §fTp to hub", XColor.c2 + "Tp dich zur hub", "/sbdev hub", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sbdev debug §fToggle debug", XColor.c2 + "Toggle debug mode", "/sbdev debug", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sbdev tools §fOpen Tools Menu", XColor.c2 + "Open Tools Menu", "/sbdev tools", false, false);
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
                        return Arrays.asList("debug", "hub", "lp", "li", "walls", "quarter", "tools");
                    case 2:
                        if(args[0].equalsIgnoreCase("li")) return List.of("1-15");
                        else if(args[0].equalsIgnoreCase("walls")) return List.of("1");
                        else if(args[0].equalsIgnoreCase("quarter")) return List.of("1");
                        break;
                }
            }
        }
        return List.of();
    }

}
