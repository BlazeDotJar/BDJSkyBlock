package de.bdj.sb.gui.quest;

import de.bdj.sb.SB;
import de.bdj.sb.event.gui.GuiClickListener;
import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.quest.FarmCobblestoneQuest;
import de.bdj.sb.quest.core.QuestType;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.ProgressVisualizer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class FarmCobblestoneQuestGUI {
    public static final String persistentKey = "";
    public static BukkitRunnable timer;

    public static void open(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 54, GuiManager.ISLAND_QUEST_GUI_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            ItemStack cobble = new ItemStack(Material.COBBLESTONE);
            ItemStack progress;
            ItemStack copperBlocks = new ItemStack(Material.RAW_COPPER_BLOCK);
            ItemStack ironBlocks = new ItemStack(Material.RAW_IRON_BLOCK);
            ItemStack goldBlocks = new ItemStack(Material.RAW_GOLD_BLOCK);

            ItemMeta meta = cobble.getItemMeta();

            FarmCobblestoneQuest fcq = (FarmCobblestoneQuest)ip.getQuestManager().getQuest(QuestType.FARM_COBBLESTONE);
            int currentCobbleNeeded = fcq.getLevelRequirement(fcq.getQuestLevel());
            int questLevel = 1;
            for(int i = 0; i != 27; i++) {
                if(i >= 9 && i < 18) {
                    //Milestone Visuals
                    meta.setItemName("Farm " + fcq.getLevelRequirement(questLevel) + " Cobblestone");
                    meta.setTooltipStyle(new NamespacedKey("minecraft", "quest_001_tooltip"));
                    cobble.setItemMeta(meta);
                    inv.setItem(i, cobble);

                    ItemStack[] reward = fcq.getLevelReward(questLevel);
                    if(reward != null && reward.length != 0) {
                        int index = 1;
                        for(ItemStack it : reward) {
                            if(it != null) {
                                ItemMeta m = it.getItemMeta();
                                //Diese PersistentData Einträge werden irgendwie nicht übernommen.
                                //in der Klasse FarmCobblestoneQuestFunction lässt sich da bestätigen.
                                m.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), GuiClickListener.guiBtnNamespace), PersistentDataType.STRING, "q-001");
                                m.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "guibtn_quest_reward"), PersistentDataType.STRING, "q-001");
                                m.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "milestone_reached"), PersistentDataType.STRING, "q-001");
                                m.setTooltipStyle(new NamespacedKey("minecraft", "quest_001_tooltip"));
                                it.setItemMeta(m);


                                inv.setItem(i + 9 + (index * 9), it.clone());
                                index+=1;
                            }
                        }
                        ItemStack claimed = new ItemStack(Material.BARRIER);
                        ItemMeta mc = claimed.getItemMeta();
                        mc.setItemName("§aBelohnung bereits eingefordert!");
                        mc.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), GuiClickListener.guiBtnNamespace), PersistentDataType.STRING, "q-001");
                        mc.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "guibtn_quest_reward"), PersistentDataType.STRING, "q-001");
                        mc.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "milestone_reached"), PersistentDataType.STRING, "q-001");
                        mc.setCustomModelData(100);
                        claimed.setItemMeta(mc);
                        inv.setItem(i + 9 + (index * 9), claimed.clone());
                    }

                    questLevel += 1;
                    if(questLevel >= fcq.maxQuestLevel) questLevel = 1;
                }else if(i >= 18 && i < 27) {
                    //Progress Bar
                    boolean skip = (i - 17) > fcq.getQuestLevel();
                    boolean thisMilestoneIsReached = (i - 17) < fcq.getQuestLevel();
                    int cobbleNeeded = fcq.getLevelRequirement(i - 17);
                    int percentage = fcq.cobblestoneFarmed / (cobbleNeeded / 100);
                    progress = ProgressVisualizer.getProgressVisual((skip ? 0 : (thisMilestoneIsReached ? 100 : percentage)));
                    meta = progress.getItemMeta();
                    meta.setDisplayName("§7" + fcq.cobblestoneFarmed + " / " + currentCobbleNeeded);
                    progress.setItemMeta(meta);
                    ItemEditor.addAllHideItemFlags(progress);
                    inv.setItem(i, progress);
                }
            }

            meta = copperBlocks.getItemMeta();
            meta.setItemName("Farm " + fcq.getLevelRequirement(2) + " Cobblestone");
            meta.setTooltipStyle(new NamespacedKey("minecraft", "quest_001_tooltip"));
            copperBlocks.setItemMeta(meta);

            meta = ironBlocks.getItemMeta();
            meta.setItemName("Farm " + fcq.getLevelRequirement(4) + " Cobblestone");
            meta.setTooltipStyle(new NamespacedKey("minecraft", "quest_001_tooltip"));
            ironBlocks.setItemMeta(meta);

            meta = goldBlocks.getItemMeta();
            meta.setItemName("Farm " + fcq.getLevelRequirement(6) + " Cobblestone");
            meta.setTooltipStyle(new NamespacedKey("minecraft", "quest_001_tooltip"));
            goldBlocks.setItemMeta(meta);

            inv.setItem(10, copperBlocks);
            inv.setItem(12, ironBlocks);
            inv.setItem(14, goldBlocks);


            inv.setItem(8, GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_QUESTS));

            ItemStack guiItem = new ItemStack(Material.BARRIER);
            meta = guiItem.getItemMeta();
            meta.setCustomModelData(200);
            guiItem.setItemMeta(meta);
            inv.setItem(0, guiItem);

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

    public void clicked(InventoryClickEvent e) {

    }

}
