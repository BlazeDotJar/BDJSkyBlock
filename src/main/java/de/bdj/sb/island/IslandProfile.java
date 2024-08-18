package de.bdj.sb.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
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
    private ArrayList<String> members = new ArrayList<>();

    public IslandProfile(int islandId, UUID ownerUuid, int x, int z, boolean isClaimed) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.x = x;
        this.z = z;
        setClaimed(isClaimed);
        islandLocation = new Location(Bukkit.getWorld("world"), x, IslandManager.islandY, z); //TODO: Die Welt muss angepasst werden, sobald die Multi-World-Funktion implementiert wurde!
        spawnPoint = islandLocation.clone().add(0.5, 2,0.5);
        Location p1 = islandLocation.clone();
        Location p2 = new Location(p1.getWorld(), x + IslandManager.islandDiameter, 319, z + IslandManager.islandDiameter);
        p1.setY(-64);
        area = new IslandArea(p1, p2);
    }

    public void teleport(LivingEntity ent) {
        ent.setFallDistance(0f);
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

    public void setOwnerUuid(UUID uuid) {
        this.ownerUuid = uuid;
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

    public IslandArea getArea() {
        return area;
    }

    public boolean isIn(Location l ) {
        return area.isIn(l);
    }

    public void addMember(String uuid) {
        if(!members.contains(uuid)) members.add(uuid);
    }

    public void removeMember(String uuid) {
        members.remove(uuid);
    }

    public ArrayList<String> getMembers() {
        return members;
    }
}
