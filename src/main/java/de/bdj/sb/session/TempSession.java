package de.bdj.sb.session;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TempSession {

    protected int delayTime = 10;
    protected String runOutOfTimeString = "";
    protected BukkitRunnable delay;
    protected boolean isListening = false;

    public abstract void start();
    public abstract void stop();
    public abstract void terminate();
    public abstract void chatEvent(AsyncPlayerChatEvent e);

}
