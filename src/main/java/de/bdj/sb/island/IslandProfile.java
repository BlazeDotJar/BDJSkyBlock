package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final IslandArea area;
    private final ArrayList<String> members = new ArrayList<>(); //String = UUID as String
    private final ArrayList<String> bannedPlayers = new ArrayList<>(); //String = UUID as String
    private final HashMap<String, String> properties = new HashMap<>(); //String = UUID as String

    public IslandProfile(int islandId, UUID ownerUuid, int x, int z, boolean isClaimed) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.x = x;
        this.z = z;
        setClaimed(isClaimed);
        islandLocation = new Location(Bukkit.getWorld(Settings.sbOverworldName), x, IslandManager.islandY, z); //TODO: Die Welt muss angepasst werden, sobald die Multi-World-Funktion implementiert wurde!
        spawnPoint = islandLocation.clone().add(0.5, 2,0.5);
        Location p1 = islandLocation.clone();
        Location p2 = new Location(p1.getWorld(), x + IslandManager.islandDiameter, 319, z + IslandManager.islandDiameter);
        p1.setY(-64);
        area = new IslandArea(p1, p2);

        loadData();
    }
    public void loadData() {
        if(ownerUuid == null) {
            return;
        }
        File file = new File("plugins/" + SB.name() + "/islands/" + ownerUuid.toString() + ".yml");
        if(!file.exists()) {
            //Chat.sendOperatorMessage("Fehler beim Laden der Islanddatei für Insel " + islandId + ". Möglicherweise fehlt die Datei " + ownerUuid.toString()+".yml im /islands/ Ordner.");
            return;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> fileMembers = (ArrayList<String>) cfg.getStringList("Members");
        ArrayList<String> fileBannedPlayers = (ArrayList<String>) cfg.getStringList("Banned Players");
        ArrayList<String> fileProperties = (ArrayList<String>) cfg.getStringList("Properties");

        if(!fileMembers.isEmpty()) {
            members.addAll(fileMembers);
        }
        if(!fileBannedPlayers.isEmpty()) {
            bannedPlayers.addAll(fileBannedPlayers);
        }
        if(!fileProperties.isEmpty()) {
            for(String s : fileProperties) {
                String[] split = s.split(": ");
                String key = split[0];
                String value = split[1];
                properties.put(key, value);
            }
        }

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

    public boolean isMember(UUID uuid) {
        return members.contains(uuid.toString());
    }

    public boolean isNeedClearing() {
        return needClearing;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public ArrayList<String> getBannedPlayers() {
        return bannedPlayers;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }
}
