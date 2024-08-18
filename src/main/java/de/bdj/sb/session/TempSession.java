package de.bdj.sb.session;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TempSession {

    public int delayTime = 10;
    protected String runOutOfTimeString = "";
    protected BukkitRunnable delay;
    protected boolean isListening = false;

    public abstract void start();
    public abstract void stop();
    public abstract void terminate();
    public abstract void chatEvent(AsyncPlayerChatEvent e);

    public void playerQuit(PlayerQuitEvent e) {

    }

}
