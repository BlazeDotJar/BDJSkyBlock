package de.bdj.sb.gui.management;

import de.bdj.sb.SB;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class GuiButtonManager {

    /* BLOCK #1003 */
    // "Register new property"
    // Create a new itemstack corresponding to the new property
    private static ItemStack CREATE_CLASSIC_SKYBLOCK;
    private static ItemStack CREATE_ONE_BLOCK_SKYBLOCK;
    private static ItemStack ISLAND_TELEPORT;
    private static ItemStack ISLAND_QUESTS;
    private static ItemStack ISLAND_PROPERTIES;
    private static ItemStack BACK_TO_DASHBOARD;
    private static ItemStack BACK_TO_QUESTS;
    private static ItemStack PROP_BTN_ALLON;
    private static ItemStack PROP_BTN_ALLOFF;
    private static ItemStack SET_ISLAND_SPAWN;
    private static ItemStack RELOAD_DATA;
    private static ItemStack PROP_PVP;
    private static ItemStack PROP_MOB_GRIEFING;
    private static ItemStack PROP_EXPLOSION_DAMAGE;
    private static ItemStack PROP_TNT_DAMAGE;
    private static ItemStack PROP_SPREAD_FIRE;
    private static ItemStack PROP_NATURAL_MONSTER_SPAWN;
    private static ItemStack PROP_STATE_ON;
    private static ItemStack PROP_STATE_OFF;
    private static ItemStack MEMBERS_GUI;
    private static ItemStack KILL_MONSTERS;
    private static ItemStack PROP_MOB_KILLING;
    private static ItemStack DEV_TOOL_BIOME_CHANGE;
    private static ItemStack DEV_BUILD_COL;
    private static ItemStack DEV_BUILD_COL_2;
    private static ItemStack QUEST_FARM_COBBLE_BTN;
    private static ItemStack QUEST_FARM_LOG_BTN;
    private static ItemStack QUEST_BUILD_COBBLE_GENERATOR_BTN;
    // BLOCK #1003 END

    public GuiButtonManager() {
        /* BLOCK #1005 */
        // "Register new property"
        // Define the new itemstack
        // BLOCK #1005 END
        CREATE_CLASSIC_SKYBLOCK = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta meta = CREATE_CLASSIC_SKYBLOCK.getItemMeta();
        meta.setDisplayName("Classic SkyBlock erstellen");

        NamespacedKey key = new NamespacedKey(SB.getInstance(), "guibtn_create_classic_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§bKlicke, um dir eine SkyBlock Insel");
        lore.add("§bmit klassischem SkyBlock Gameplay zu erstellen.");
        meta.setLore(lore);
        CREATE_CLASSIC_SKYBLOCK.setItemMeta(meta);
        lore.clear();

        CREATE_ONE_BLOCK_SKYBLOCK = new ItemStack(Material.COARSE_DIRT);
        meta = CREATE_ONE_BLOCK_SKYBLOCK.getItemMeta();
        meta.setDisplayName("One Block Insel erstellen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_create_one_block_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§bKlicke, um dir eine SkyBlock Insel");
        lore.add("§bmit One Block Gameplay zu erstellen.");
        meta.setLore(lore);
        CREATE_ONE_BLOCK_SKYBLOCK.setItemMeta(meta);
        lore.clear();

        ISLAND_TELEPORT = new ItemStack(Material.COMPASS);
        meta = ISLAND_TELEPORT.getItemMeta();
        meta.setDisplayName("§bTeleportiere dich auf deine Insel");
        meta.setCustomModelData(99);

        key = new NamespacedKey(SB.getInstance(), "guibtn_teleport_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        ISLAND_TELEPORT.setItemMeta(meta);
        lore.clear();

        ISLAND_QUESTS = new ItemStack(Material.BOOK);
        meta = ISLAND_QUESTS.getItemMeta();
        meta.setDisplayName("§6Insel Quests");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_quests_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        ISLAND_QUESTS.setItemMeta(meta);
        lore.clear();

        ISLAND_PROPERTIES = new ItemStack(Material.PAPER);
        meta = ISLAND_PROPERTIES.getItemMeta();
        meta.setDisplayName("§6Insel Einstellungen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fHier kannst du Einstellungen");
        lore.add("§fvornehmen und Regeln festlegen.");
        meta.setLore(lore);
        ISLAND_PROPERTIES.setItemMeta(meta);
        lore.clear();

        BACK_TO_DASHBOARD = new ItemStack(Material.BARRIER);
        meta = BACK_TO_DASHBOARD.getItemMeta();
        meta.setDisplayName("§cZurück");

        key = new NamespacedKey(SB.getInstance(), "guibtn_back_to_dashboard_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        BACK_TO_DASHBOARD.setItemMeta(meta);
        lore.clear();

        PROP_BTN_ALLON = new ItemStack(Material.LIME_CONCRETE);
        meta = PROP_BTN_ALLON.getItemMeta();
        meta.setDisplayName("§fAlle Props §aeinschalten");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_all_on_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        PROP_BTN_ALLON.setItemMeta(meta);
        lore.clear();

        PROP_BTN_ALLOFF = new ItemStack(Material.LIGHT_GRAY_CONCRETE);
        meta = PROP_BTN_ALLOFF.getItemMeta();
        meta.setDisplayName("§fAlle Props §causschalten");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_all_off_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        PROP_BTN_ALLOFF.setItemMeta(meta);
        lore.clear();

        PROP_STATE_ON = new ItemStack(Material.LIME_DYE);
        meta = PROP_STATE_ON.getItemMeta();
        meta.setDisplayName("§aEingeschaltet");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_state_on_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        PROP_STATE_ON.setItemMeta(meta);
        lore.clear();


        PROP_STATE_OFF = new ItemStack(Material.GRAY_DYE);
        meta = PROP_STATE_OFF.getItemMeta();
        meta.setDisplayName("§7Ausgeschaltet");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_state_off_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        PROP_STATE_OFF.setItemMeta(meta);
        lore.clear();

        PROP_PVP = new ItemStack(Material.GOLDEN_SWORD);
        meta = PROP_PVP.getItemMeta();
        meta.setDisplayName("§6PVP");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_pvp_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle PVP auf deiner Insel");
        meta.setLore(lore);
        PROP_PVP.setItemMeta(meta);
        lore.clear();

        PROP_MOB_GRIEFING = new ItemStack(Material.CREEPER_HEAD);
        meta = PROP_MOB_GRIEFING.getItemMeta();
        meta.setDisplayName("§6Mob Griefing");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_mob_griefing_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle Mob Griefing auf deiner Insel");
        meta.setLore(lore);
        PROP_MOB_GRIEFING.setItemMeta(meta);
        lore.clear();

        PROP_EXPLOSION_DAMAGE = new ItemStack(Material.GUNPOWDER);
        meta = PROP_EXPLOSION_DAMAGE.getItemMeta();
        meta.setDisplayName("§6Explosions Damage");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_explosion_damage_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle Explosion Mob Damage auf deiner Insel");
        meta.setLore(lore);
        PROP_EXPLOSION_DAMAGE.setItemMeta(meta);
        lore.clear();

        PROP_TNT_DAMAGE = new ItemStack(Material.TNT);
        meta = PROP_TNT_DAMAGE.getItemMeta();
        meta.setDisplayName("§6TNT Damage");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_tnt_damage_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle TNT Block Damage auf deiner Insel");
        meta.setLore(lore);
        PROP_TNT_DAMAGE.setItemMeta(meta);
        lore.clear();

        PROP_SPREAD_FIRE = new ItemStack(Material.FLINT_AND_STEEL);
        meta = PROP_SPREAD_FIRE.getItemMeta();
        meta.setDisplayName("§6Spread Fire");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_fire_spread_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle Spread Fire auf deiner Insel");
        meta.setLore(lore);
        PROP_SPREAD_FIRE.setItemMeta(meta);
        lore.clear();


        PROP_NATURAL_MONSTER_SPAWN = new ItemStack(Material.SPAWNER);
        meta = PROP_NATURAL_MONSTER_SPAWN.getItemMeta();
        meta.setDisplayName("§6Natürliches Monsterspawnen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_natural_monster_spawn_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggle Natural Monster Spawn auf deiner Insel");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        PROP_NATURAL_MONSTER_SPAWN.setItemMeta(meta);
        lore.clear();

        PROP_MOB_KILLING = new ItemStack(Material.TOTEM_OF_UNDYING);
        meta = PROP_MOB_KILLING.getItemMeta();
        meta.setDisplayName("§6Mob Killing");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_properties_mob_killing_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fToggleMob Killing auf deiner Insel");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        PROP_MOB_KILLING.setItemMeta(meta);
        lore.clear();

        KILL_MONSTERS = new ItemStack(Material.DIAMOND_HOE); //Default: TOTEM_OF_UNDYING
        meta = KILL_MONSTERS.getItemMeta();
        meta.setDisplayName("§6Insel von Monstern befreien");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_kill_monsters_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fLösche alle Monster von deiner Insel, die kein NameTag tragen");
        lore.add("§fdie kein NameTag tragen");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setCustomModelData(21);
        KILL_MONSTERS.setItemMeta(meta);
        lore.clear();

        SET_ISLAND_SPAWN = new ItemStack(Material.DIAMOND_HOE); //Default: Bed
        meta = SET_ISLAND_SPAWN.getItemMeta();
        meta.setDisplayName("§6Insel Spawnpunkt setzen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_set_island_spawn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fSetze die Location, an der du");
        lore.add("§fdich befindest, als Insel Spawnpunkt.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setCustomModelData(21);
        SET_ISLAND_SPAWN.setItemMeta(meta);
        lore.clear();

        RELOAD_DATA = new ItemStack(Material.DIAMOND_HOE); //Default: Chest
        meta = RELOAD_DATA.getItemMeta();
        meta.setDisplayName("§6Inseldaten neuladen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_reload_data");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fLade Insel Daten neu.");
        lore.add("§fKann im Fehlerfall helfen.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setCustomModelData(21);
        RELOAD_DATA.setItemMeta(meta);
        lore.clear();

        MEMBERS_GUI = new ItemStack(Material.PLAYER_HEAD); //Default: PLAYER_HEAD
        meta = MEMBERS_GUI.getItemMeta();
        meta.setDisplayName("§6Insel Member anzeigen");

        key = new NamespacedKey(SB.getInstance(), "guibtn_members");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fVerwalte deine Insel Member.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setCustomModelData(21);
        MEMBERS_GUI.setItemMeta(meta);
        lore.clear();


        QUEST_FARM_COBBLE_BTN = new ItemStack(Material.COBBLESTONE);
        meta = QUEST_FARM_COBBLE_BTN.getItemMeta();
        meta.setItemName("Farm Cobblestone");

        key = new NamespacedKey(SB.getInstance(), "guibtn_quest_farm_cobble");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§7Baue Cobblestone oder Clean Stone ab,");
        lore.add("§7um Meilensteine zu erreichen.");
        lore.add("");
        lore.add("§9§oStatus dieser Quest ansehen");
        lore.add("§9§oQ-001");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        QUEST_FARM_COBBLE_BTN.setItemMeta(meta);
        lore.clear();

        QUEST_FARM_LOG_BTN = new ItemStack(Material.OAK_LOG);
        meta = QUEST_FARM_LOG_BTN.getItemMeta();
        meta.setItemName("Farm Log");

        key = new NamespacedKey(SB.getInstance(), "guibtn_quest_farm_log");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§7Baue Holzstämme ab,");
        lore.add("§7um Meilensteine zu erreichen.");
        lore.add("");
        lore.add("§9§oStatus dieser Quest ansehen");
        lore.add("§9§oQ-002");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        QUEST_FARM_LOG_BTN.setItemMeta(meta);
        lore.clear();

        QUEST_BUILD_COBBLE_GENERATOR_BTN = new ItemStack(Material.LAVA_BUCKET);
        meta = QUEST_BUILD_COBBLE_GENERATOR_BTN.getItemMeta();
        meta.setItemName("Gen-Cobble");

        key = new NamespacedKey(SB.getInstance(), "guibtn_quest_build_cobblestone_generator");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§7Baue einen Cobblestone Generator.");
        lore.add("");
        lore.add("§9§oStatus dieser Quest ansehen");
        lore.add("§9§oQ-003");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        QUEST_BUILD_COBBLE_GENERATOR_BTN.setItemMeta(meta);
        lore.clear();

        BACK_TO_QUESTS = new ItemStack(Material.BARRIER);
        meta = BACK_TO_QUESTS.getItemMeta();
        meta.setItemName("§cZurück");

        key = new NamespacedKey(SB.getInstance(), "guibtn_back_to_quests");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        BACK_TO_QUESTS.setItemMeta(meta);
        lore.clear();

        // Dev Tools \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/ \/

        DEV_TOOL_BIOME_CHANGE = new ItemStack(Material.OAK_SAPLING);
        meta = DEV_TOOL_BIOME_CHANGE.getItemMeta();
        meta.setDisplayName("§6Insel Biom auf Plains ändern");

        key = new NamespacedKey(SB.getInstance(), "guibtn_dev_tool_change_biome");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fDies kann etwas dauern und");
        lore.add("§fkann zum Crash führen.");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        DEV_TOOL_BIOME_CHANGE.setItemMeta(meta);
        lore.clear();

        DEV_BUILD_COL = new ItemStack(Material.COMPASS);
        meta = DEV_BUILD_COL.getItemMeta();

        key = new NamespacedKey(SB.getInstance(), "guibtn_dev_tool_build_column");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        meta.setCustomModelData(98);

        lore = new ArrayList<>();
        lore.add("2x1 Column");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        DEV_BUILD_COL.setItemMeta(meta);
        lore.clear();


        DEV_BUILD_COL_2 = new ItemStack(Material.COMPASS);
        meta = DEV_BUILD_COL_2.getItemMeta();

        key = new NamespacedKey(SB.getInstance(), "guibtn_dev_tool_build_column");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        meta.setCustomModelData(97);

        lore = new ArrayList<>();
        lore.add("1x1 Column");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        DEV_BUILD_COL_2.setItemMeta(meta);
        lore.clear();
        // Dev Tools /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\ /\
    }

    public static ItemStack getGuiBtn(GuiButtonType gbt) {
        /* BLOCK #1004 */
        // "Register new property"
        // Add a new case corresponding to the new property itemstack you have created
        switch (gbt) {
            case DEV_TOOL_BIOME_CHANGE -> {
                return DEV_TOOL_BIOME_CHANGE.clone();
            }
            case DEV_BUILD_COL -> {
                return DEV_BUILD_COL.clone();
            }
            case DEV_BUILD_COL_2 -> {
                return DEV_BUILD_COL_2.clone();
            }
            case CREATE_CLASSIC_SKYBLOCK -> {
                return CREATE_CLASSIC_SKYBLOCK.clone();
            }
            case CREATE_ONE_BLOCK_SKYBLOCK -> {
                return CREATE_ONE_BLOCK_SKYBLOCK.clone();
            }
            case ISLAND_TELEPORT -> {
                return ISLAND_TELEPORT.clone();
            }
            case ISLAND_PROPERTIES -> {
                return ISLAND_PROPERTIES.clone();
            }
            case ISLAND_QUESTS -> {
                return ISLAND_QUESTS.clone();
            }
            case BACK_TO_DASHBOARD -> {
                return BACK_TO_DASHBOARD.clone();
            }
            case PROP_BTN_ALLON -> {
                return PROP_BTN_ALLON.clone();
            }
            case PROP_BTN_ALLOFF -> {
                return PROP_BTN_ALLOFF.clone();
            }
            case PROP_STATE_ON -> {
                return PROP_STATE_ON.clone();
            }
            case PROP_STATE_OFF -> {
                return PROP_STATE_OFF.clone();
            }
            case PROP_PVP -> {
                return PROP_PVP.clone();
            }
            case PROP_MOB_GRIEFING -> {
                return PROP_MOB_GRIEFING.clone();
            }
            case PROP_EXPLOSION_DAMAGE -> {
                return PROP_EXPLOSION_DAMAGE.clone();
            }
            case PROP_SPREAD_FIRE -> {
                return PROP_SPREAD_FIRE.clone();
            }
            case PROP_TNT_DAMAGE -> {
                return PROP_TNT_DAMAGE.clone();
            }
            case PROP_NATURAL_MONSTER_SPAWN -> {
                return PROP_NATURAL_MONSTER_SPAWN.clone();
            }
            case PROP_MOB_KILLING -> {
                return PROP_MOB_KILLING;
            }
            case KILL_MONSTERS -> {
                return KILL_MONSTERS.clone();
            }
            case SET_ISLAND_SPAWN -> {
                return SET_ISLAND_SPAWN.clone();
            }
            case RELOAD_DATA -> {
                return RELOAD_DATA;
            }
            case MEMBERS_GUI -> {
                return MEMBERS_GUI;
            }
            case BACK_TO_QUESTS -> {
                return BACK_TO_QUESTS.clone();
            }
            case QUEST_FARM_COBBLE_BTN -> {
                return QUEST_FARM_COBBLE_BTN.clone();
            }
            case QUEST_FARM_LOG_BTN -> {
                return QUEST_FARM_LOG_BTN.clone();
            }
            case QUEST_BUILD_COBBLE_GENERATOR_BTN -> {
                return QUEST_BUILD_COBBLE_GENERATOR_BTN.clone();
            }
            default -> {
                return null;
            }
        }
        // BLOCK #1004 END
    }

}
