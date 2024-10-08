package de.bdj.sb.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    private static ConcurrentHashMap<UUID, PlayerProfile> profiles = new ConcurrentHashMap<UUID, PlayerProfile>();

    public static void registerProfile(UUID uuid) {
        if(profiles.containsKey(uuid)) return;
        profiles.put(uuid, new PlayerProfile(uuid));
    }

    public static void unregisterProfile(UUID uuid) {
        if(!profiles.containsKey(uuid)) return;

        profiles.get(uuid).save();

        profiles.remove(uuid);
    }

    public static void unregisterAllProfiles() {
        for(UUID uuid : profiles.keySet()) unregisterProfile(uuid);
    }

    public static void reloadAll() {
        if(!profiles.isEmpty()) {
            for(UUID uuid : profiles.keySet()) {
                profiles.get(uuid).load();
            }
        } else {
            for(Player p : Bukkit.getOnlinePlayers()) registerProfile(p.getUniqueId());
        }
    }

    public static PlayerProfile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public static ConcurrentHashMap<UUID, PlayerProfile> getProfiles() {
        return profiles;
    }

}
