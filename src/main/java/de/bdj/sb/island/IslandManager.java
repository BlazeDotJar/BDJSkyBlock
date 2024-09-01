package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class IslandManager {

    private static ConcurrentHashMap<Integer, IslandProfile> profiles = new ConcurrentHashMap<Integer, IslandProfile>();
    private static int amountClaimed = 0;
    public static int islandY = 100;
    public static int maxClaimedListAmount = 50;
    public static int amountGenerated = 0;
    public static int islandDiameter = 400;
    public static int spaceBetweenIsland = 25;

    private static SlowIslandProfileLoader sipl;

    public static void reloadFiles() {
        if(sipl != null) sipl.cancel();
        if(!profiles.isEmpty()) {
            for(IslandProfile ip : profiles.values()) {
                ip.loadData();
            }
        } else {
            SB.getInstance().saveResource("island_index_file.yml", false);

            File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            amountGenerated = cfg.getInt("Islands.Amount Generated");

            for(Player p : Bukkit.getOnlinePlayers()) {
                loadPlayerIslandFile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());
            }
        }
        sipl = new SlowIslandProfileLoader();

    }
    public static void reloadFile(int islandId) {
        if(sipl != null) sipl.cancel();
        SB.getInstance().saveResource("island_index_file.yml", false);

        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        IslandProfile ip = getIslandDataFromIndexFile(islandId);
        profiles.remove(islandId);
        profiles.put(islandId, ip);
    }

    public static IslandProfile getLoadedIslandProfile(int islandId) {
        return profiles.get(islandId);
    }

    public static void loadPlayerIslandFile(int islandId) {
        if(islandId < 1) return;

        profiles.put(islandId, getIslandDataFromIndexFile(islandId));
    }

    public static IslandProfile getIslandDataFromIndexFile(int islandId) {
        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) reloadFiles();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String islandPath = "Islands.ID-"+islandId;
        String owner = cfg.getString(islandPath + ".Owner UUID");
        if(owner == null) {
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
                //Könnte eventuell gelöscht werden (dieser ganze if block)
                continue;
            }
            int x = cfg.getInt(islandPath +  ".LocX");
            int z = cfg.getInt(islandPath + ".LocZ");
            boolean claimed = cfg.getBoolean(islandPath + ".Claimed");

            IslandProfile ip =  new IslandProfile(islandId, (owner == null ? null : owner.equals("none") ? null : UUID.fromString(owner)), x, z, claimed);
            ip.setClaimed(claimed);
            islands.put(islandId, ip);
        }
        return islands;
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

    public static IslandProfile getIslandLocationIsIn(Location l) {
        for(int id : profiles.keySet()) {
            IslandProfile ip = profiles.get(id);
            if(ip.getArea().isIn(l)) return ip;
        }
        return null;
    }

    public static ConcurrentHashMap<Integer, IslandProfile> getProfiles() {
        return profiles;
    }

    public static class SlowIslandProfileLoader {

        private BukkitRunnable loader;
        private int lastLoadedId = 0;
        private int loadPerSecond = 500;

        public SlowIslandProfileLoader() {
            long started = System.currentTimeMillis();
            loader = new BukkitRunnable() {
                @Override
                public void run() {
                    if(lastLoadedId + loadPerSecond <= amountGenerated) {
                        //Load the amount of loadPerSecond of profiles at once
                        HashMap<Integer, IslandProfile> is = getIslandsInIslandIdRange(lastLoadedId, lastLoadedId + loadPerSecond);
                        for(int id : is.keySet()) {
                            profiles.put(id, is.get(id));
                        }
                       //SB.log("Loaded Profiles from " + lastLoadedId + " to " + (lastLoadedId + loadPerSecond));
                        lastLoadedId += loadPerSecond;
                    } else if(lastLoadedId <= amountGenerated){
                        //Load the last single profiles
                        IslandProfile ip = getIslandDataFromIndexFile(lastLoadedId);
                        profiles.put(lastLoadedId, ip);
                        lastLoadedId++;
                    } else {
                        cancel();
                        long ended = System.currentTimeMillis();
                        Chat.debug("Finished loading all " + amountGenerated + " IslandProfiles!", "Took " + ((ended - started) / 1000) + " Seconds");
                    }
                }
            };
            loader.runTaskTimer(SB.getInstance(), 0L, 10L);
        }

        public void cancel() {
            if(loader != null && !loader.isCancelled()) loader.cancel();
        }

    }

}
