package de.bdj.sb.gui;

import de.bdj.NameFetcher;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.MemberProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GuiManager {

    // Titles of the guis.
    // If a new gui title is created, it has to be included to the Block #001 in GuiClickListener.onInventoryClick();
    public static String ISLAND_PROPERTIES_TITLE = "Insel Einstellungen";
    public static String ISLAND_DASHBOARD_TITLE = "D A S H B O A R D";
    public static String DEV_TOOLS_TITLE = "D E V T O O L S";
    public static String MEMBERS_GUI_TITLE = "Members";
    public static String MEMBER_ADMINISTRATION_GUI_TITLE = "Member Verwaltung";
    // --------------------------------------------------------------------------------------------------------------

    public static void openIslandDashboard(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() == 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel erstellen");

            inv.setItem(8, close);
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_CLASSIC_SKYBLOCK));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_ONE_BLOCK_SKYBLOCK));

            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_DASHBOARD_TITLE);
            ItemStack playerSkull = ItemEditor.setSkullOwner(GuiButtonManager.getGuiBtn(GuiButtonType.MEMBERS_GUI), p.getUniqueId().toString());

            inv.setItem(8, close);
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_ACHIEVEMENTS));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_TELEPORT));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_PROPERTIES));
            inv.setItem(18, GuiButtonManager.getGuiBtn(GuiButtonType.SET_ISLAND_SPAWN));
            inv.setItem(19, GuiButtonManager.getGuiBtn(GuiButtonType.RELOAD_DATA));
            inv.setItem(25, playerSkull);
            inv.setItem(26, GuiButtonManager.getGuiBtn(GuiButtonType.KILL_MONSTERS));


            p.openInventory(inv);
        }
    }

    public static void openDeveloperGui(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        Inventory inv = Bukkit.createInventory(null, 27, DEV_TOOLS_TITLE);

        inv.setItem(8, close);
        inv.setItem(0, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_TOOL_BIOME_CHANGE));
        inv.setItem(1, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL));
        inv.setItem(2, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL_2));

        p.openInventory(inv);
    }

    public static void openPropertiesMenu(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_PROPERTIES_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            inv.setItem(0, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLON), "sb_prop", "all:true"));
            inv.setItem(1, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLOFF), "sb_prop", "all:false"));
            inv.setItem(9, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_PVP));
            inv.setItem(10, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_MOB_GRIEFING));
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_EXPLOSION_DAMAGE));
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_TNT_DAMAGE));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_SPREAD_FIRE));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_NATURAL_MONSTER_SPAWN));
            inv.setItem(8, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "dashboard"));

            HashMap<String, String> props = ip.getProperties();
            for(String prop : props.keySet()) {
                // Setting the value of the props under the prop items
                String val = props.get(prop);
                if(prop.equalsIgnoreCase("pvp")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "pvp:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "pvp:true"));
                } else if(prop.equalsIgnoreCase("mob griefing")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "mob griefing:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "mob griefing:true"));
                } else if(prop.equalsIgnoreCase("explosion damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "explosion damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "explosion damage:true"));
                } else if(prop.equalsIgnoreCase("tnt damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "tnt damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "tnt damage:true"));
                } else if(prop.equalsIgnoreCase("spread fire")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "spread fire:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "spread fire:true"));
                } else if(prop.equalsIgnoreCase("natural monster spawn")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "natural monster spawn:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "natural monster spawn:true"));
                }
            }

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

    /*

    Member GUIS

     */

    public static void openMemberProfile(Player p, int islandid, String memberUuid) {
        ItemStack back = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "members");
        back = ItemEditor.rename(back, "§cZurück").clone();
        Inventory inv = Bukkit.createInventory(null, 54, MEMBER_ADMINISTRATION_GUI_TITLE);
        inv.setItem(8, back);

        IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);
        MemberProfile mp = ip.getMemberProfile(memberUuid);
        String memberName = NameFetcher.getName(memberUuid);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(memberUuid)));
        skullMeta.setDisplayName("§7Du verwaltest");

        lore.add(XColor.green + memberName);
        skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta);
        lore.clear();
        skull = ItemEditor.addPersistentData(skull, "sb_guibtn", "");
        inv.setItem(0, skull);

        ItemStack item = new ItemStack(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eEntfernen");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        lore.add("§7Entferne §f" + memberName + " §7von deiner Insel.");
        lore.add("§7Dieser Spieler kann somit nichts mehr auf dieser Insel machen,");
        lore.add("§7da dieser kein Mitspieler mehr ist.");
        lore.add("");
        lore.add("§3Klicke, um auszuführen");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "remove");
        item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
        inv.setItem(9, item);

        item = new ItemStack(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        meta = item.getItemMeta();
        meta.setDisplayName("§eVerbannen");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        lore.add("§7Verbanne §f" + memberName + " §7von deiner Insel.");
        lore.add("§7Diesem Spieler wird automatisch, sofern er ein");
        lore.add("§7Member ist, die Memberrolle entzogen");
        lore.add("§7und anschließend verbannt. Dem Spieler ist es");
        lore.add("§7somit nicht möglich deine Insel zu betreten.");
        lore.add("");
        lore.add("§3Klicke, um auszuführen");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "ban");
        item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
        inv.setItem(10, item);

        if(mp.isAllowedModify()) {
            item = new ItemStack(Material.DIAMOND_PICKAXE);
            meta = item.getItemMeta();
            meta.setDisplayName("§eBauen/Abbauen");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            lore.add("§7Verbiete das " + XColor.green + "Bauen §7und " + XColor.green + "Abbauen §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_modify", "deny");
            inv.setItem(18, item);
        } else {
            item = new ItemStack(Material.WOODEN_PICKAXE);
            meta = item.getItemMeta();
            meta.setDisplayName("§eBauen/Abbauen");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            lore.add("§7Erlaube das " + XColor.green + "Bauen §7und " + XColor.green + "Abbauen §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_modify", "allow");
            inv.setItem(18, item);
        }

        if(mp.isAllowedMobkilling()) {
            item = new ItemStack(Material.DIAMOND_SWORD);
            meta = item.getItemMeta();
            meta.setDisplayName("§eTöten von Mobs");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            lore.add("§7Verbiete das " + XColor.green + "Töten von Mobs §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_mobkilling", "deny");
            inv.setItem(19, item);
        } else {
            item = new ItemStack(Material.WOODEN_SWORD);
            meta = item.getItemMeta();
            meta.setDisplayName("§eTöten von Mobs");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            lore.add("§7Erlaube das " + XColor.green + "Töten von Mobs §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_mobkilling", "allow");
            inv.setItem(19, item);
        }


        p.openInventory(inv);
    }

    public static void openIslandMembersGui(Player p, int islandid) {
        ItemStack back = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "dashboard");
        back = ItemEditor.rename(back, "§cZurück").clone();
        Inventory inv = Bukkit.createInventory(null, 27, MEMBERS_GUI_TITLE);
        inv.setItem(8, back);
        IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(XColor.green + "Member hinzufügen");
        lore.add("§7Füge einen Spieler als Member hinzu.");
        lore.add("");
        lore.add("§3Klicke, um Spielerliste zu öffnen");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "");
        inv.setItem(0, item);
        lore.clear();

        item = new ItemStack(Material.ENDER_PEARL);
        meta = item.getItemMeta();
        meta.setDisplayName(XColor.green + "Aktualisieren");
        meta.setLore(lore);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "refresh members");
        inv.setItem(1, item);
        lore.clear();

        if(!ip.getMembers().isEmpty()) {
            int i = 9;
            lore.add("§7Klicke, um das Memberprofil zu öffnen.");
            for(String mUuid : ip.getMembers()) {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
                skullmeta.setDisplayName(XColor.green + NameFetcher.getName(mUuid));

                skullmeta.setLore(lore);
                skull.setItemMeta(skullmeta);

                skull = ItemEditor.addPersistentData(skull, "sb_guibtn", "");
                skull = ItemEditor.addPersistentData(skull, "sb_member", "open member profile");
                skull = ItemEditor.addPersistentData(skull, "sb_target_member", mUuid);

                skull = ItemEditor.setSkullOwner(skull, mUuid);


                inv.setItem(i, skull);
                i++;
            }
        } else {
            item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            meta = item.getItemMeta();
            meta.setDisplayName("§eKeine Member vorhanden");
            item.setItemMeta(meta);
            for(int i = 9; i != 27; i++) {
                inv.setItem(i, item);
            }
        }

        p.openInventory(inv);
    }

    public static void playerFinderGui() {
        //TODO:
    }

}
