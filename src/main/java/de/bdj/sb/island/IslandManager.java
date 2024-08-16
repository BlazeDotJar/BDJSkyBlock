package de.bdj.sb.island;

import de.bdj.sb.SB;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class IslandManager {

    private static HashMap<Integer, IslandProfile> profiles = new HashMap<Integer, IslandProfile>();
    private static int amountClaimed = 0;
    public static int islandY = 100;

    public static void reloadFiles() {
        SB.getInstance().saveResource("island_index_file.yml", false);
    }

    public static IslandProfile getIslandDataFromIndexFile(int islandId) {
        File file = new File("plugins/" + SB.name() + "/island_index_file.yml");
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String islandPath = "Islands.ID-"+islandId;
        String owner = cfg.getString(islandPath + ".Owner UUID");
        int x = cfg.getInt(islandPath +  ".LocX");
        int y = cfg.getInt(islandPath + ".LocY");
        boolean claimed = cfg.getBoolean(islandPath + ".Claimed");

        SB.log("Loading Island " + islandId);
        SB.log("island path = " + islandPath);
        SB.log("owner = " + owner);
        SB.log("x = " + x);
        SB.log("y = " + y);
        SB.log("claimed = " + claimed);

        IslandProfile ip =  new IslandProfile(islandId, (owner == null ? null : owner.equals("none") ? null : UUID.fromString(owner)), x, y);
        ip.setClaimed(claimed);
        return ip;
    }

}
