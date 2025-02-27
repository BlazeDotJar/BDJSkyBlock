package de.bdj.sb.quest.core;

import de.bdj.sb.SB;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.quest.BuildCobbleGeneratorQuest;
import de.bdj.sb.quest.FarmCobblestoneQuest;
import de.bdj.sb.quest.FarmLogQuest;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.TimeStamp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class QuestManager {

    private int islandId = -1;
    private HashMap<QuestType, Quest> quests = new HashMap<>();

    public QuestManager(IslandProfile ip) {
        this.islandId = ip.getIslandId();
        //File file = new File("plugins/" + SB.name() + "/islands/" + ip.getOwnerUuid().toString() + ".yml");
        //FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        quests.put(QuestType.BUILD_COBBLE_GENERATOR, new BuildCobbleGeneratorQuest(ip));
        quests.put(QuestType.FARM_COBBLESTONE, new FarmCobblestoneQuest(ip));
        quests.put(QuestType.FARM_LOG, new FarmLogQuest(ip));
    }

    public void applyQuestData(HashMap<QuestType, Quest> quests) {
        this.quests = quests;
    }

    public Quest getQuest(QuestType type) {
        return quests.get(type);
    }

    public void saveData(SaveReason reason, UUID hostUuid) {
        if(hostUuid == null) {
            Chat.debug("Error: 101", "Error while saving QuestManager data. HostUUID is null.");
            return;
        }
        File file = new File("plugins/" + SB.name() + "/playerprofiles/" + hostUuid.toString() + "/quest_manager.yml");
        if(!file.exists()) {
            file.mkdir();
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("Host UUID", hostUuid.toString());
        cfg.set("Data from.Date", SB.timeStamp.current_date);
        cfg.set("Data from.Time", SB.timeStamp.current_time);
        switch(reason) {
            case ROUTINE_SAVE, PLAYER_QUIT, PROGRESS_MADE, REWARD_CLAIMED, FILE_DONT_EXIST, ADMIN_MANIPULATION:
                for(QuestType qt : quests.keySet()) {
                    quests.get(qt).saveTo("Quests.", cfg);
                }
                break;
        }
        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void readData(UUID hostUuid) {
        if(hostUuid == null) {
            Chat.debug("Error: 102", "Error while reading QuestManager data. HostUUID is null.");
            return;
        }
        File file = new File("plugins/" + SB.name() + "/playerprofiles/" + hostUuid.toString() + "/quest_manager.yml");
        if(!file.exists()) {
            saveData(SaveReason.FILE_DONT_EXIST, hostUuid);
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for(QuestType qt : quests.keySet()) {
            quests.get(qt).readFrom("Quests.", cfg);
        }
    }

    public static enum SaveReason {
        ROUTINE_SAVE,
        PLAYER_QUIT,
        PROGRESS_MADE,
        REWARD_CLAIMED,
        FILE_DONT_EXIST,
        ADMIN_MANIPULATION;
    }

}
