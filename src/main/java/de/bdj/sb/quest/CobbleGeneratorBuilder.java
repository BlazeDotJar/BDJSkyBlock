package de.bdj.sb.quest;

import de.bdj.sb.quest.core.Quest;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class CobbleGeneratorBuilder extends Quest implements Listener {

    public CobbleGeneratorBuilder(int islandId) {
        super(islandId);
    }

    @Override
    protected void update() {

    }

    public void cobbleGeneration(BlockFromToEvent e) {

    }
}
