package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.utlility.Chat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MemberProfile {


    private String memberUuid = "";
    private int memberOnIslandId = 0;
    private boolean allowedModify = true;
    private boolean allowedMobkilling = true;
    private boolean allowMemberInvitation = false;
    private boolean allowContainerOpening = false;
    private boolean allowedInteractRedstoneBlocks = false;
    private boolean allowedInteractBlocks = false;


    public MemberProfile(String uuid, int islandId) {
        this.memberOnIslandId = islandId;
        this.memberUuid = uuid;
        loadData();
    }

    public void listPerms() {
        Chat.debug("allowContainerOpening = " + allowContainerOpening);
    }

    public void loadData() {
        File file = new File("plugins/" + SB.name() + "/memberprofiles/member-profile_" + memberUuid + ".yml");
        if(!file.exists()) saveData();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        allowedModify = cfg.getBoolean("Permissions.Allow Modify");
        allowedMobkilling = cfg.getBoolean("Permissions.Allow Mobkilling");
        allowMemberInvitation = cfg.getBoolean("Permissions.Allow Member Invitation");
        allowContainerOpening = cfg.getBoolean("Permissions.Allow Container Opening");
        allowedInteractRedstoneBlocks = cfg.getBoolean("Permissions.Allow Interaction With Redstone Blocks");
        allowedInteractBlocks = cfg.getBoolean("Permissions.Allow Interaction With Blocks");
    }

    public void saveData() {
        File file = new File("plugins/" + SB.name() + "/memberprofiles/member-profile_" + memberUuid + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("Member On Island", memberOnIslandId);
        cfg.set("Permissions.Allow Modify", allowedModify);
        cfg.set("Permissions.Allow Mobkilling", allowedMobkilling);
        cfg.set("Permissions.Allow Member Invitation", allowMemberInvitation);
        cfg.set("Permissions.Allow Container Opening", allowContainerOpening);
        cfg.set("Permissions.Allow Interaction With Redstone Blocks", allowedInteractRedstoneBlocks);
        cfg.set("Permissions.Allow Interaction With Blocks", allowedInteractBlocks);

        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    // =============================================
    // Setters
    public void mobkilling(boolean value) {
        this.allowedMobkilling = value;
    }
    public void modify(boolean value) {
        this.allowedModify = value;
    }
    public void memberInvitation(boolean value) {
        this.allowMemberInvitation = value;
    }
    public void containerOpening(boolean value) {
        this.allowContainerOpening = value;
    }
    public void interactRedstoneBlocks(boolean value) {
        this.allowedInteractRedstoneBlocks = value;
    }
    public void interactBlocks(boolean value) {
        this.allowedInteractBlocks = value;
    }

    // =============================================
    // Getters

    public boolean isAllowedMobkilling() {
        return allowedMobkilling;
    }
    public boolean isAllowedModify() {
        return allowedModify;
    }
    public boolean isAllowedMemberInvitation() {
        return allowMemberInvitation;
    }
    public boolean isAllowedOpenContainerBlocks() {
        return allowContainerOpening;
    }
    public boolean isAllowedInteractRedstoneBlocks() {
        return allowedInteractRedstoneBlocks;
    }
    public boolean isAllowedInteractBlocks() {
        return allowedInteractBlocks;
    }
}
