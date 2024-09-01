package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.result.SetIslandSpawnResult;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class IslandDataReader {

    public static Location getSpawnPoint(String ownerUuid) {
        File file = new File("plugins/" + SB.name() + "/islands/" + ownerUuid + ".yml");
        if(!file.exists()) return null;
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String world = cfg.getString("Island Spawnpoint.World");
        double x = cfg.getDouble("Island Spawnpoint.X");
        double y = cfg.getDouble("Island Spawnpoint.Y");
        double z = cfg.getDouble("Island Spawnpoint.Z");
        float yaw = (float)cfg.getInt("Island Spawnpoint.Yaw");
        float pitch = (float)cfg.getInt("Island Spawnpoint.Pitch");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

        try {
            cfg.save(file);
            return loc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
