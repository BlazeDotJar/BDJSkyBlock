package de.bdj.sb.quest.reward;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RewardTable {

    private int maximalMilestone = 9;
    private HashMap<Integer/* <-Quest level*/, /*RewardTableContent-> */HashMap<RewardRequirement, ItemStack[]/* <-Content[]: Max 3*/>> tables;

}
