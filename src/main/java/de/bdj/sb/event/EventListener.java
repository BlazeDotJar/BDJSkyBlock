package de.bdj.sb.event;

import de.bdj.sb.SB;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    public EventListener() {
        SB.getInstance().getServer().getPluginManager().registerEvents(this, SB.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        JoinQuitListener.onJoin(e);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        JoinQuitListener.onQuit(e);
    }

    @EventHandler
    public void asyncChat(AsyncPlayerChatEvent e) {
        AsyncChatListener.onAsyncChat(e);
    }


}
