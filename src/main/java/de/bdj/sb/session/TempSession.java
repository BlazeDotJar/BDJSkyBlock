package de.bdj.sb.session;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class TempSession {

    public int delayTime = 10;
    protected String runOutOfTimeString = "";
    protected BukkitRunnable delay;
    protected boolean isListening = false;
    protected HashMap<String, Object> extraData = null;

    public abstract void start();
    public abstract void stop();
    public abstract void terminate();
    public abstract void chatEvent(AsyncPlayerChatEvent e);

    public void addExtraData(String key, Object value) {
        if(extraData == null) extraData = new HashMap<>();
        extraData.put(key, value);
    }

    public Object getExtraData(String key) {
        return extraData.get(key);
    }

    public void playerQuit(PlayerQuitEvent e) {

    }

}
