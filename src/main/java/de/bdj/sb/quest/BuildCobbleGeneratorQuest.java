package de.bdj.sb.quest;

import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.quest.core.Quest;
import de.bdj.sb.quest.core.QuestType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BuildCobbleGeneratorQuest extends Quest implements Listener {

    public BuildCobbleGeneratorQuest(IslandProfile ip) {
        super(ip, QuestType.BUILD_COBBLE_GENERATOR);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void saveTo(String section, FileConfiguration cfg) {
        cfg.set(section + questType.toString() + ".accomplished", isAccomplished);
    }

    @Override
    protected void readFrom(String section, FileConfiguration cfg) {
        isAccomplished = cfg.getBoolean(section + questType.toString() + ".accomplished");
    }

    public void cobbleGeneration(BlockFromToEvent e) {
        if(islandProfile.isIn(e.getBlock().getLocation())) accomplish("§5Baue Cobblegenerator", "§derfolgreich gemeistert!");
    }
}
