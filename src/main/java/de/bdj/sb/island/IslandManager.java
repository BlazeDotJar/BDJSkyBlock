package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.TimeStamp;
import de.bdj.sb.utlility.XColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class IslandManager {

    private static HashMap<Integer, IslandProfile> profiles = new HashMap<Integer, IslandProfile>();
    private static int amountClaimed = 0;
    public static int islandY = 100;
    public static int maxClaimedListAmount = 50;

    public static void reloadFiles() {
        SB.getInstance().saveResource("island_index_file.yml", false);
    }

    public static IslandProfile getIslandDataFromIndexFile(int islandId) {
        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String islandPath = "Islands.ID-"+islandId;
        String owner = cfg.getString(islandPath + ".Owner UUID");
        if(owner == null) {
            Chat.sendOperatorMessage("DEBUG -> IslandManager.getIslandDataFromIndexFile -> IslandID = " + islandId + " Does not exist!");
            return null;
        }
        int x = cfg.getInt(islandPath +  ".LocX");
        int z = cfg.getInt(islandPath + ".LocZ");
        boolean claimed = cfg.getBoolean(islandPath + ".Claimed");

        if(Settings.pluginDeveloperHelpMode) {
            SB.log("-> getIslandDataFromIndexFile@IslandManager.java");
            SB.log("----> Loading Island " + islandId);
            SB.log("----> island path = " + islandPath);
            SB.log("----> claimed = " + claimed);
            SB.log("----> owner = " + owner);
            SB.log("----> x = " + x);
            SB.log("----> z = " + z);
        }

        IslandProfile ip =  new IslandProfile(islandId, (owner == null ? null : owner.equals("none") ? null : UUID.fromString(owner)), x, z, claimed);
        ip.setClaimed(claimed);
        return ip;
    }
    public static HashMap<Integer, IslandProfile> getIslandDataFromIndexFile(int start, int end) {
        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        HashMap<Integer, IslandProfile> islands = new HashMap<>();

        for(int islandId = start; islandId <= end; islandId++) {
            String islandPath = "Islands.ID-"+islandId;
            String owner = cfg.getString(islandPath + ".Owner UUID");
            if(owner == null) {
                Chat.sendOperatorMessage("DEBUG -> IslandManager.getIslandDataFromIndexFile -> IslandID = " + islandId + " Does not exist!");
                continue;
            }
            int x = cfg.getInt(islandPath +  ".LocX");
            int z = cfg.getInt(islandPath + ".LocZ");
            boolean claimed = cfg.getBoolean(islandPath + ".Claimed");

            if(Settings.pluginDeveloperHelpMode) {
                SB.log("-> getIslandDataFromIndexFile@IslandManager.java");
                SB.log("----> Loading Island " + islandId);
                SB.log("----> island path = " + islandPath);
                SB.log("----> claimed = " + claimed);
                SB.log("----> owner = " + owner);
                SB.log("----> x = " + x);
                SB.log("----> z = " + z);
            }

            IslandProfile ip =  new IslandProfile(islandId, (owner == null ? null : owner.equals("none") ? null : UUID.fromString(owner)), x, z, claimed);
            ip.setClaimed(claimed);
            islands.put(islandId, ip);
        }
        return islands;
    }

    public static boolean removeOwner(int islandId) {
        Chat.sendOperatorMessage("IslandManager.java : try to delete island #" + islandId);
        IslandProfile ip = getIslandDataFromIndexFile(islandId);
        File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Owner UUID", "none");
        cfg.set("Owner Deleted This Island", true);
        try {
            cfg.save(file);
            file.renameTo(new File("plugins/" + SB.name() + "/islands/deleted_" + SB.timeStamp.getCurrentDate() + "_" + SB.timeStamp.getCurrentTime().replace(":", "-") + "_" + ip.getOwnerUuid().toString() + ".yml"));

            File f = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
            if(!f.exists()) reloadFiles();
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("Islands.ID-" + islandId + ".Owner UUID", "none");

            c.save(f);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getAmountClaimedIslands() {
        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        return cfg.getInt("Amount Claimed");
    }

    public static HashMap<Integer, IslandProfile> getIslandsInIslandIdRange(int startId, int endId) {
        if(startId > endId) {
            int max = startId;
            startId = endId;
            endId = max;
        }

        HashMap<Integer, IslandProfile> islands = getIslandDataFromIndexFile(startId, endId);

        return islands;
    }


    public static void listClaimedIslands(Player p, int startId, int endId) {
        HashMap<Integer, HashMap<String, String>> islands = new HashMap<>();

        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for(int s = startId; s != endId + 1; s++) {
            if(cfg.getBoolean("Islands.ID-" + s + ".Claimed")) {
                String claimed = String.valueOf(cfg.getBoolean("Islands.ID-" + s + ".Claimed"));
                String owner = cfg.getString("Islands.ID-" + s + ".Owner UUID");
                String x = String.valueOf(cfg.getInt("Islands.ID-" + s + ".LocX"));
                String y = String.valueOf(islandY);
                String z = String.valueOf(cfg.getInt("Islands.ID-" + s + ".LocZ"));

                HashMap<String, String> values = new HashMap<>();

                values.put("claimed", claimed);
                values.put("owner", owner);
                values.put("x", x);
                values.put("y", y);
                values.put("z", z);
                values.put("need clearing", (claimed.equals("true") && owner.equals("none") ? "true" : "false"));
                islands.put(s, values);
            }
        }

        if(!islands.isEmpty()) {
            Chat.info(p, "Insel " + startId + " bis " + endId + " aufgelistet(" + islands.size() + " Inseln):");
            for(int i : islands.keySet()) {
                Chat.sendHoverableCommandHelpMessage(p, " - " + XColor.c1 + "Island " + i, XColor.c2 + "Claimed: §f" + (islands.get(i).get("claimed").equals("true") ? "Ja" : "Nein") + "\n" +
                        XColor.c2 + "Owner UUID: §f" + islands.get(i).get("owner") + "\n" +
                        XColor.c2 + "X-Coord: §f" + islands.get(i).get("x") + "\n" +
                        XColor.c2 + "Y-Coord: §f" + islands.get(i).get("y") + "\n" +
                        XColor.c2 + "Z-Coord: §f" + islands.get(i).get("z") + "\n" +
                        XColor.c2 + "Muss bereinigt werden: §f" + (islands.get(i).get("need clearing").equals("true") ? "§cJa\nNutze §c/sb helpadmin islandclear" : "§aNein"), false, false);
            }
        } else Chat.info(p, "Es gibt keine belegte Insel im Bereich von " + startId + " und " + endId);

    }

}
