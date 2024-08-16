package de.bdj.sb.lobby;

import de.bdj.sb.SB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;

public class Waitlobby {

    private static Location spawnpoint;

    public static void reloadLocation() {
        File file = new File("plugins/" + SB.name() + "/waitlobby.yml");
        if(!file.exists()) SB.getInstance().saveResource("waitlobby.yml", false);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String world = cfg.getString("Waitlobby.World");
        double x = cfg.getDouble("Waitlobby.X");
        double y = cfg.getDouble("Waitlobby.Y");
        double z = cfg.getDouble("Waitlobby.Z");
        float yaw = (float) cfg.getInt("Waitlobby.Yaw");
        float pitch = (float) cfg.getInt("Waitlobby.Pitch");

        spawnpoint = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public static void setNewLocation(Location loc) {
        File file = new File("plugins/" + SB.name() + "/waitlobby.yml");
        if(!file.exists()) SB.getInstance().saveResource("waitlobby.yml", false);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("Waitlobby.World", loc.getWorld().getName());
        cfg.set("Waitlobby.X", loc.getX());
        cfg.set("Waitlobby.Y", loc.getY());
        cfg.set("Waitlobby.Z", loc.getZ());
        cfg.set("Waitlobby.Yaw", loc.getYaw());
        cfg.set("Waitlobby.Pitch", loc.getPitch());

        spawnpoint = loc.clone();

        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }

    }

    public static void teleport(LivingEntity p) {
        p.teleport(spawnpoint);
        //Maybe add some effects, sounds, etc.
    }

}
