package de.bdj.sb.quest.core;

import de.bdj.sb.island.IslandProfile;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Quest {

    private int islandId = -1;
    protected IslandProfile islandProfile;
    protected QuestType questType;
    protected boolean isAccomplished = false;
    protected String accomplishedTextLine1 = "";
    protected String accomplishedTextLine2 = "";
    protected HashMap<Integer, ItemStack[]> levelRewards = new HashMap<>(); //int: level, ItemStack[]: rewards
    protected ArrayList<Integer> demandedRewards = new ArrayList<>();

    public Quest(IslandProfile ip, QuestType qt) {
        islandProfile = ip;
        this.islandId = ip.getIslandId();
        this.questType = qt;
    }

    protected abstract void update();
    protected abstract void saveTo(String section, FileConfiguration cfg);
    protected abstract void readFrom(String section, FileConfiguration cfg);

    protected void accomplish() {
        accomplish(accomplishedTextLine1, accomplishedTextLine2);
    }
    protected void accomplish(String line1, String line2) {
        if(isAccomplished) return;
        isAccomplished = true;
        //TODO: Send auch allen Member diese Quest Nachricht
        Player owner = Bukkit.getPlayer(islandProfile.getOwnerUuid());
        owner.playSound(owner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
        owner.sendTitle(line1, line2, 10, 60, 30);
        owner.getWorld().spawnParticle(Particle.FIREWORK, owner.getLocation(), 10);
    }
    public ItemStack[] getLevelReward(int level) {
        return levelRewards.get(level);
    }
    public int amountDemandableRewards(int level) {
        return levelRewards.get(level).length;
    }
    public ItemStack[] demandReward(int level) {
        demandedRewards.add(level);
        return levelRewards.get(level);
    }

}
