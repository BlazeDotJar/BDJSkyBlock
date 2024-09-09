package de.bdj.sb.gui;

import de.bdj.sb.SB;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class GuiButtonManager {

    private static ItemStack CREATE_CLASSIC_SKYBLOCK;
    private static ItemStack CREATE_ONE_BLOCK_SKYBLOCK;
    private static ItemStack ISLAND_TELEPORT;
    private static ItemStack ISLAND_ACHIEVEMENTS;
    private static ItemStack ISLAND_PROPERTIES;
    private static ItemStack BACK_TO_DASHBOARD;
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
    private static ItemStack KILL_MONSTERS;
    private static ItemStack DEV_TOOL_BIOME_CHANGE;
    private static ItemStack DEV_BUILD_COL;
    private static ItemStack DEV_BUILD_COL_2;

    public GuiButtonManager() {
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

        ISLAND_ACHIEVEMENTS = new ItemStack(Material.BOOK);
        meta = ISLAND_ACHIEVEMENTS.getItemMeta();
        meta.setDisplayName("§6Insel Achievements");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_achievements_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§8§oBisher noch keine");
        meta.setLore(lore);
        ISLAND_ACHIEVEMENTS.setItemMeta(meta);
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

        KILL_MONSTERS = new ItemStack(Material.TOTEM_OF_UNDYING);
        meta = KILL_MONSTERS.getItemMeta();
        meta.setDisplayName("§6Insel von Monstern befreien");

        key = new NamespacedKey(SB.getInstance(), "guibtn_island_kill_monsters_skyblock");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");
        key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "");

        lore = new ArrayList<>();
        lore.add("§fLösche alle Monster von deiner Insel, die kein NameTag tragen");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        KILL_MONSTERS.setItemMeta(meta);
        lore.clear();

        SET_ISLAND_SPAWN = new ItemStack(Material.LIME_BED);
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
        SET_ISLAND_SPAWN.setItemMeta(meta);
        lore.clear();

        RELOAD_DATA = new ItemStack(Material.CHEST);
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
        RELOAD_DATA.setItemMeta(meta);
        lore.clear();

        // Dev Tool Buttons

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
    }

    public static ItemStack getGuiBtn(GuiButtonType gbt) {
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
            case ISLAND_ACHIEVEMENTS -> {
                return ISLAND_ACHIEVEMENTS.clone();
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
            case KILL_MONSTERS -> {
                return KILL_MONSTERS.clone();
            }
            case SET_ISLAND_SPAWN -> {
                return SET_ISLAND_SPAWN.clone();
            }
            case RELOAD_DATA -> {
                return RELOAD_DATA;
            }
            default -> {
                return null;
            }
        }
    }

}
