package de.bdj.sb.quest;

import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.quest.core.Quest;
import de.bdj.sb.quest.core.QuestType;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Gradient;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmLogQuest extends Quest implements Listener {

    private final int maxQuestLevel = 10;
    private int questLevel = 1;
    private int logFarmed = 0;

    public FarmLogQuest(IslandProfile ip) {
        super(ip, QuestType.FARM_LOG);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void saveTo(String section, FileConfiguration cfg) {
        cfg.set(section + questType.toString() + ".currentQuestLevel", questLevel);
        cfg.set(section + questType.toString() + ".logFarmed", logFarmed);
    }

    @Override
    protected void readFrom(String section, FileConfiguration cfg) {
        questLevel = cfg.getInt(section + questType.toString() + ".currentQuestLevel");
        logFarmed = cfg.getInt(section + questType.toString() + ".logFarmed");
    }

    public void logFarmed(BlockBreakEvent e) {
        if(!e.getBlock().getType().toString().contains("_LOG")) return;
        if (!islandProfile.isIn(e.getBlock().getLocation())) return;
        logFarmed += 1;
        if(getQuestLevel() == maxQuestLevel) accomplish();

        Chat.info(Bukkit.getPlayer(islandProfile.getOwnerUuid()), "FarmLogQuest.class: Logs farmed: " + logFarmed);
    }

    private void newLevelReached() {
        accomplish(Gradient.applyGradient("Logs farmen", "#8B5129", "#8C4700"),
                Gradient.applyGradient("Meilenstein Level " + questLevel, "#9F9F9F", "#9F9F9F"));
        if(questLevel != maxQuestLevel) isAccomplished = false;
    }

    public int getQuestLevel() {
        /*
        100, 250, 500, 1000, 2000
        4000, 8000, 16000, 32000
         */
        int newQuestLevel = 0;
        if(questLevel == 9 && logFarmed >= 32000) newQuestLevel = 10; // 32000
        else if(questLevel == 8 && logFarmed >= 16000) newQuestLevel = 9; // 16000
        else if(questLevel == 7 && logFarmed >= 8000) newQuestLevel = 8; // 8000
        else if(questLevel == 6 && logFarmed >= 4000) newQuestLevel = 7; // 4000
        else if(questLevel == 5 && logFarmed >= 2000) newQuestLevel = 6; // 2000
        else if(questLevel == 4 && logFarmed >= 1000) newQuestLevel = 5; // 1000
        else if(questLevel == 3 && logFarmed >= 500) newQuestLevel = 4; // 500
        else if(questLevel == 2 && logFarmed >= 250) newQuestLevel = 3; // 250
        else if(questLevel == 1 && logFarmed >= 100) newQuestLevel = 2; // 100

        if(questLevel < newQuestLevel) {
            questLevel = newQuestLevel;
            newLevelReached();
            logFarmed = 0;
        }

        return newQuestLevel;
    }
}
