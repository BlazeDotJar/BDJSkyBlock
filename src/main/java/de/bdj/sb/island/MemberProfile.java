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


    public MemberProfile(String uuid, int islandId) {
        this.memberOnIslandId = islandId;
        this.memberUuid = uuid;
        loadData();
    }

    public void loadData() {
        File file = new File("plugins/" + SB.name() + "/memberprofiles/member-profile_" + memberUuid + ".yml");
        if(!file.exists()) saveData();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        allowedModify = cfg.getBoolean("Permissions.Allow Modify");
        allowedMobkilling = cfg.getBoolean("Permissions.Allow Mobkilling");
    }

    public void saveData() {
        File file = new File("plugins/" + SB.name() + "/memberprofiles/member-profile_" + memberUuid + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("Member On Island", memberOnIslandId);
        cfg.set("Permissions.Allow Modify", allowedModify);
        cfg.set("Permissions.Allow Mobkilling", allowedMobkilling);

        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    // =============================================
    // Togglers

    public void mobkilling(boolean value) {
        this.allowedMobkilling = value;
    }
    public void modify(boolean value) {
        this.allowedModify = value;
    }
    public void toggleMobkilling() {
        this.allowedMobkilling = !allowedMobkilling;
    }
    public void toggleModify() {
        this.allowedModify = !allowedModify;
    }

    // =============================================
    // Getters

    public boolean isAllowedMobkilling() {
        return allowedMobkilling;
    }

    public boolean isAllowedModify() {
        return allowedModify;
    }
}
