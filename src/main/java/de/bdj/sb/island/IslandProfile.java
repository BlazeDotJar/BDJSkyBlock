package de.bdj.sb.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class IslandProfile {

    private int islandId = -1;
    private int x = -1;
    private int z = -1;
    private UUID ownerUuid;
    private boolean isClaimed = false;
    private boolean needClearing = false;
    private Location islandLocation = null;
    private Location spawnPoint = null;
    private IslandArea area;

    public IslandProfile(int islandId, UUID ownerUuid, int x, int z, boolean isClaimed) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.x = x;
        this.z = z;
        setClaimed(isClaimed);
        islandLocation = new Location(Bukkit.getWorld("world"), x, IslandManager.islandY, z); //TODO: Die Welt muss angepasst werden, sobald die Multi-World-Funktion implementiert wurde!
        spawnPoint = islandLocation.clone().add(0.5, 2,0.5);
    }

    public void teleport(LivingEntity ent) {
        ent.teleport(spawnPoint);
    }

    public int getIslandId() {
        return islandId;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public void setClaimed(boolean value) {
        this.isClaimed = value;
        this.needClearing = (isClaimed && (ownerUuid == null || ownerUuid.toString().equals("none")));
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public Location getIslandLocation() {
        return islandLocation;
    }

    public boolean needCleearing() {
        return needClearing;
    }
}
