package de.bdj.sb.profile;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.session.ConfirmSession;
import de.bdj.sb.session.PlayerCreateIslandSession;
import de.bdj.sb.session.TempSession;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private int islandId = 0;
    private String lastJoin = "";
    private HashMap<String, TempSession> tempSessions = new HashMap<String, TempSession>();
    //private IslandCreateSessions ics;
    private int islandIsCurrentIn;

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        load();
    }

    public void load() {
        File file = new File("plugins/" + SB.name() + "/playerprofiles/" + uuid.toString() + ".yml");
        if(!file.exists()) save();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        int iid = cfg.getInt("Island ID");
        this.islandId = iid;
        this.lastJoin = cfg.getString("Last Seen");
    }

    public void save() {
        File file = new File("plugins/" + SB.name() + "/playerprofiles/" + uuid.toString() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String date = SB.timeStamp.getCurrentDate();
        String time = SB.timeStamp.getCurrentTime();
        String lastSeen = date + " / " + time;

        cfg.set("UUID", uuid.toString());
        cfg.set("Island ID", this.islandId);
        cfg.set("Last Seen", lastSeen);

        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chatEvent(AsyncPlayerChatEvent e) {
        if(tempSessions.isEmpty()) return;
        for(TempSession ts : tempSessions.values()) ts.chatEvent(e);
    }

    public void addTempSession(TempSession ts) {
        if(ts instanceof ConfirmSession) {
            if(tempSessions.containsKey(Settings.confirmationSessionKey)) tempSessions.get(Settings.confirmationSessionKey).terminate();
            tempSessions.put(Settings.confirmationSessionKey, (ConfirmSession)ts);
            return;
        } else if(ts instanceof PlayerCreateIslandSession) {
            if(tempSessions.containsKey(Settings.playerCreateSessionKey)) tempSessions.get(Settings.playerCreateSessionKey).terminate();
            tempSessions.put(Settings.playerCreateSessionKey, (PlayerCreateIslandSession)ts);
            return;
        }
        SB.log("TempSession konnte nicht hinzugefügt werden, weil die Child-Klasse nicht ermittelt werden konnte. Sie ist quasi durch den Filter gerutscht!",
                "addTempSession(TempSession ts)@PlayerProfile.java");
    }

    public TempSession getTempSession(String key) {
        return tempSessions.get(key);
    }
    public void removeTempSession(String key, TempSession session) {
        tempSessions.remove(key, session);
    }

    public PlayerProfile setLastJoin(String lastJoin) {
        this.lastJoin = lastJoin;
        return this;
    }

    public PlayerProfile setIslandId(int islandId) {
        this.islandId = islandId;
        return this;
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

    public boolean hasIsland() {
        return (islandId > 0);
    }

    public void setIslandIsCurrentIn(int value) {
        islandIsCurrentIn = value;
    }

    /**
     * Returns the island id of the island, the player is currently staying in.
     * @return Island ID at players current location
     */
    public int getIslandIsCurrentIn() {
        return islandIsCurrentIn;
    }
}
