package de.bdj.sb.quest;

import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.quest.core.Quest;
import de.bdj.sb.quest.core.QuestType;
import de.bdj.sb.utlility.Gradient;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class FarmCobblestoneQuest extends Quest implements Listener {

    public final int maxQuestLevel = 10;
    private int questLevel = 1;
    public int cobblestoneFarmed = 0;
    private HashMap<Integer, Integer> levelRequirements = new HashMap<>(); //int: level, int: amount of cobblestone farmed

    public FarmCobblestoneQuest(IslandProfile ip) {
        super(ip, QuestType.FARM_COBBLESTONE);

        levelRequirements.put(1, 100);
        levelRequirements.put(2, 250);
        levelRequirements.put(3, 500);
        levelRequirements.put(4, 1000);
        levelRequirements.put(5, 2000);
        levelRequirements.put(6, 4000);
        levelRequirements.put(7, 8000);
        levelRequirements.put(8, 16000);
        levelRequirements.put(9, 32000);

        ItemStack[] reward = new ItemStack[3];
        ItemStack r = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta m = r.getItemMeta();
        m.setItemName("Cobbler");
        m.setLore(List.of("§bBelohnung aus der §fFarm Cobblestone Quest§b."));
        m.addEnchant(Enchantment.EFFICIENCY, 2, true);
        m.addEnchant(Enchantment.UNBREAKING, 2, true);
        r.setItemMeta(m);
        reward[0] = r;

        r = new ItemStack(Material.BREAD, 16);
        m = r.getItemMeta();
        m.setLore(List.of("§7Hart und trocken.", "§7Noch vom Vortag.", "§bBelohnung aus der §fFarm Cobblestone Quest§b."));
        r.setItemMeta(m);
        reward[1] = r;
        levelRewards.put(1, reward);

        reward = new ItemStack[3];
        r = new ItemStack(Material.STONE_PICKAXE);
        m = r.getItemMeta();
        m.setItemName("Better Cobbler");
        m.setLore(List.of("§bBelohnung aus der §fFarm Cobblestone Quest§b."));
        m.addEnchant(Enchantment.EFFICIENCY, 5, true);
        m.addEnchant(Enchantment.UNBREAKING, 3, true);
        r.setItemMeta(m);
        reward[0] = r;

        r = new ItemStack(Material.BREAD, 16);
        m = r.getItemMeta();
        m.setLore(List.of("§7Hart und trocken.", "§7Noch vom Vortag.", "§bBelohnung aus der §fFarm Cobblestone Quest§b."));
        r.setItemMeta(m);
        reward[1] = r;

        levelRewards.put(2, reward);

    }

    @Override
    protected void update() {

    }

    @Override
    protected void saveTo(String section, FileConfiguration cfg) {
        cfg.set(section + questType.toString() + ".currentQuestLevel", questLevel);
        cfg.set(section + questType.toString() + ".cobblestoneFarmed", cobblestoneFarmed);
    }

    @Override
    protected void readFrom(String section, FileConfiguration cfg) {
        questLevel = cfg.getInt(section + questType.toString() + ".currentQuestLevel");
        cobblestoneFarmed = cfg.getInt(section + questType.toString() + ".cobblestoneFarmed");
    }

    public void cobbleFarmed(BlockBreakEvent e) {
        if(e.getBlock().getType() != Material.COBBLESTONE && e.getBlock().getType() != Material.STONE) return;
        if (!islandProfile.isIn(e.getBlock().getLocation())) return;
        cobblestoneFarmed += 1;
        if(getNewQuestLevel() == maxQuestLevel) accomplish();

    }

    private void newLevelReached() {
        accomplish(Gradient.applyGradient("Cobblestone farmen", "#FFFFFF", "#A1A1A1"),
                Gradient.applyGradient("Meilenstein Level " + questLevel, "#FFFFFF", "#A1A1A1"));
        if(questLevel != maxQuestLevel) isAccomplished = false;
    }
    public int getQuestLevel() {
        return questLevel;
    }

    public int getNewQuestLevel() {
        /*
        100, 250, 500, 1000, 2000
        4000, 8000, 16000, 32000
         */
        int newQuestLevel = 0;
        if(questLevel == 9 && cobblestoneFarmed >= 32000) newQuestLevel = 10; // 32000
        else if(questLevel == 8 && cobblestoneFarmed >= 16000) newQuestLevel = 9; // 16000
        else if(questLevel == 7 && cobblestoneFarmed >= 8000) newQuestLevel = 8; // 8000
        else if(questLevel == 6 && cobblestoneFarmed >= 4000) newQuestLevel = 7; // 4000
        else if(questLevel == 5 && cobblestoneFarmed >= 2000) newQuestLevel = 6; // 2000
        else if(questLevel == 4 && cobblestoneFarmed >= 1000) newQuestLevel = 5; // 1000
        else if(questLevel == 3 && cobblestoneFarmed >= 500) newQuestLevel = 4; // 500
        else if(questLevel == 2 && cobblestoneFarmed >= 250) newQuestLevel = 3; // 250
        else if(questLevel == 1 && cobblestoneFarmed >= 100) newQuestLevel = 2; // 100

        if(questLevel < newQuestLevel) {
            questLevel = newQuestLevel;
            newLevelReached();
            cobblestoneFarmed = 0;
        }

        return newQuestLevel;
    }

    public int getLevelRequirement(int level) {
        if(!levelRequirements.containsKey(level)) return -1;
        return levelRequirements.get(level);
    }
}
