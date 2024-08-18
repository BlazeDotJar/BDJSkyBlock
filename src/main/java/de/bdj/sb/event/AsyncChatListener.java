package de.bdj.sb.event;

import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncChatListener {

    public static void onAsyncChat(AsyncPlayerChatEvent e) {
        ConcurrentHashMap<UUID, PlayerProfile> profiles = ProfileManager.getProfiles();
        for(UUID uuid : profiles.keySet()) {
            profiles.get(uuid).chatEvent(e);
        }
    }

}


