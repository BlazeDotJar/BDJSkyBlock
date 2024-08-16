package de.bdj.sb.island;

import java.util.UUID;

public class IslandProfile {

    private int islandId = -1;
    private int x = -1;
    private int z = -1;
    private UUID ownerUuid;
    private boolean isClaimed = false;

    public IslandProfile(int islandId, UUID ownerUuid, int x, int zy) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.x = x;
        this.z = z;
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
    }

    public boolean isClaimed() {
        return isClaimed;
    }


}
