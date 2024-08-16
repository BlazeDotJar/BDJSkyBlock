package de.bdj.sb.event;

import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class AsyncChatListener {

    public static void onAsyncChat(AsyncPlayerChatEvent e) {
        HashMap<UUID, PlayerProfile> profiles = ProfileManager.getProfiles();
        for(UUID uuid : profiles.keySet()) {
            profiles.get(uuid).chatEvent(e);
        }
    }

}
