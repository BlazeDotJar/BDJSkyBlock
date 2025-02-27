package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.gui.MemberFinderGUI;
import de.bdj.sb.gui.MemberProfileGUI;
import de.bdj.sb.gui.MembersGUI;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.NameFetcher;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MemberGuiFunction {

    public static boolean clicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member"))) {
            // Open MemberProfile GUI
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member"), PersistentDataType.STRING);
            String memberUuid = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_target_member"), PersistentDataType.STRING);
            if(value.equalsIgnoreCase("open finder")) {
                MemberFinderGUI.open((Player)e.getWhoClicked(), ip.getIslandId(), 1);
            } else if(value.equalsIgnoreCase("refresh members")) {
                MembersGUI.open((Player)e.getWhoClicked(), ip.getIslandId());
            } else if(value.equalsIgnoreCase("refresh finder")) {
                MemberFinderGUI.open((Player)e.getWhoClicked(), ip.getIslandId(), 1);
            } else if(value.startsWith("finder page:")) {
                String[] split = value.split(":");
                String pageStr = split[1];
                int page = Integer.parseInt(pageStr);
                MemberFinderGUI.open((Player)e.getWhoClicked(), ip.getIslandId(), page);
            } else if(value.startsWith("invite")) {
                String[] split = value.split(":");
                String name = split[1];
                String id = split[2];
                int islandid = Integer.parseInt(id);
                ((Player)e.getWhoClicked()).performCommand("is member " + name + " add");
            } else if(value.equalsIgnoreCase("open member profile")) {

                if (meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_target_member"))) {
                    MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                } else Chat.debug("Error while opening member profile. PersistentData \"sb_target_member\" is missing! DashboardFunction.java Section \"Open MemberProfile GUI\"");
            } else if(value.equalsIgnoreCase("remove")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sb delmember " + NameFetcher.getName(memberUuid) + " " + ip.getIslandId());
                //ip.removeMember(memberUuid);
                MembersGUI.open((Player) e.getWhoClicked(), ip.getIslandId());
                Chat.info(e.getWhoClicked(), "Du hast " + NameFetcher.getName(memberUuid) + " die MemberRolle entzogen!");
            } else if(value.equalsIgnoreCase("ban")) {
                //TODO:
                // ip.ban(memberUuid);
                Chat.info(e.getWhoClicked(), "Du hast " + NameFetcher.getName(memberUuid) + " von deiner Insel gebannt!");
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_mobkilling"))) {
                // Member Property: Mobkilling
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_mobkilling"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).mobkilling(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).mobkilling(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_modify"))) {
                // Member Property: Modify
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_modify"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).modify(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).modify(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_member_invitation"))) {
                // Member Property: memberInvitation
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_member_invitation"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).memberInvitation(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).memberInvitation(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_container_opening"))) {
                // Member Property: containerOpening
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_container_opening"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).containerOpening(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).containerOpening(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_interact_redstone_blocks"))) {
                // Member Property: redstone block interacting
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_interact_redstone_blocks"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).interactRedstoneBlocks(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).interactRedstoneBlocks(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_member_prop_interact_blocks"))) {
                // Member Property: block interacting
                String propValue = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_member_prop_interact_blocks"), PersistentDataType.STRING);

                if(propValue.equalsIgnoreCase("deny")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).interactBlocks(false);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                } else if(propValue.equalsIgnoreCase("allow")) {
                    if(memberUuid != null) {
                        ip.getMemberProfile(memberUuid).interactBlocks(true);
                        MemberProfileGUI.open((Player) e.getWhoClicked(), ip.getIslandId(), memberUuid);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
