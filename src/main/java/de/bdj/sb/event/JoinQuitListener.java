package de.bdj.sb.event;

import de.bdj.sb.SB;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener {

    public static void onJoin(PlayerJoinEvent e) {
        String date = SB.timeStamp.getCurrentDate();
        String time = SB.timeStamp.getCurrentTime();
        String lastJoin = date + " / " + time;
        ProfileManager.registerProfile(e.getPlayer().getUniqueId());
        ProfileManager.getProfile(e.getPlayer().getUniqueId()).setLastJoin(lastJoin);
    }

    public static void onQuit(PlayerQuitEvent e) {
        ProfileManager.unregisterProfile(e.getPlayer().getUniqueId());
    }

}
