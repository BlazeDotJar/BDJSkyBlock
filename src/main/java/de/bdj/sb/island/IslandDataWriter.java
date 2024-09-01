package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.result.SetIslandSpawnResult;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class IslandDataWriter {

    public static SetIslandSpawnResult setIslandSpawn(int islandId, Location loc) {
        IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
        if(ip == null) return SetIslandSpawnResult.NO_ISLAND_FOUND;
        File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        if(!file.exists()) return SetIslandSpawnResult.FILE_DOES_NOT_EXIST;
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Island Spawnpoint.World", loc.getWorld().getName());
        cfg.set("Island Spawnpoint.X", loc.getX() + (IslandManager.islandDiameter / 2) + 0.5);
        cfg.set("Island Spawnpoint.Y", loc.getY());
        cfg.set("Island Spawnpoint.Z", loc.getZ() + (IslandManager.islandDiameter / 2) + 0.5);
        cfg.set("Island Spawnpoint.Yaw", loc.getYaw());
        cfg.set("Island Spawnpoint.Pitch", loc.getPitch());
        try {
            cfg.save(file);
            return SetIslandSpawnResult.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return SetIslandSpawnResult.COULD_NOT_SAVE;
        }
    }

    public static boolean removeOwner(int islandId) {
        IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
        File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Owner UUID", "none");
        cfg.set("Owner Deleted This Island", true);
        try {
            cfg.save(file);
            file.renameTo(new File("plugins/" + SB.name() + "/islands/deleted_" + SB.timeStamp.getCurrentDate() + "_" + SB.timeStamp.getCurrentTime().replace(":", "-") + "_" + ip.getOwnerUuid().toString() + ".yml"));

            File f = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
            if(!f.exists()) IslandManager.reloadFiles();
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);

            c.set("Islands.ID-" + islandId + ".Owner UUID", "none");

            c.save(f);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addMemberToIsland(int islandId, UUID uuid) {
        if(!IslandManager.getProfiles().containsKey(islandId)) return false;

        IslandProfile ip = IslandManager.getProfiles().get(islandId);
        ip.addMember(uuid.toString());

        File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Members", ip.getMembers());
        try {
            cfg.save(file);
            ProfileManager.getProfile(uuid).setIslandId(islandId);
            ProfileManager.getProfile(uuid).save();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeMemberFromIsland(int islandId, UUID uuid) {
        if(!IslandManager.getProfiles().containsKey(islandId)) return false;

        IslandProfile ip = IslandManager.getProfiles().get(islandId);
        ip.removeMember(uuid.toString());

        File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Members", ip.getMembers());
        try {
            cfg.save(file);
            ProfileManager.getProfile(uuid).setIslandId(0);
            ProfileManager.getProfile(uuid).save();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
