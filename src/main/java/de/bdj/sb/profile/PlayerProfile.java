package de.bdj.sb.profile;

import de.bdj.sb.Settings;
import de.bdj.sb.session.ConfirmSession;
import de.bdj.sb.session.TempSession;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private int islandId = -1;
    private String lastJoin = "";
    private HashMap<String, TempSession> tempSessions = new HashMap<String, TempSession>();
    //private IslandCreateSessions ics;

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
    }

    public void save() {
        //Future function
    }

    public void chatEvent(AsyncPlayerChatEvent e) {
        if(tempSessions.isEmpty()) return;
        for(TempSession ts : tempSessions.values()) ts.chatEvent(e);
    }

    public void addTempSession(TempSession ts) {
        if(ts instanceof ConfirmSession) {
            if(tempSessions.containsKey(Settings.confirmationSessionKey)) tempSessions.get(Settings.confirmationSessionKey).terminate();
            tempSessions.put(Settings.confirmationSessionKey, (ConfirmSession)ts);
        }
    }

    public TempSession getTempSession(String key) {
        return tempSessions.get(key);
    }
    public void removeTempSession(String key, TempSession session) {
        tempSessions.remove(key, session);
    }


    public void setLastJoin(String lastJoin) {
        this.lastJoin = lastJoin;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getIslandId() {
        return islandId;
    }

    public String getLastJoin() {
        return lastJoin;
    }
}
