package de.bdj.sb.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ProfileManager {

    private static HashMap<UUID, PlayerProfile> profiles = new HashMap<UUID, PlayerProfile>();

    public static void registerProfile(UUID uuid) {
        if(profiles.containsKey(uuid)) return;
        profiles.put(uuid, new PlayerProfile(uuid));
    }

    public static void unregisterProfile(UUID uuid) {
        if(!profiles.containsKey(uuid)) return;

        profiles.get(uuid).save();

        profiles.remove(uuid);
    }

    public static void reloadAll() {
        for(Player p : Bukkit.getOnlinePlayers()) registerProfile(p.getUniqueId());
    }

    public static PlayerProfile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public static HashMap<UUID, PlayerProfile> getProfiles() {
        return profiles;
    }

}
