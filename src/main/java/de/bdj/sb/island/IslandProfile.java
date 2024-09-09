package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

import java.io.File;
import java.io.IOException;
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
    private HashMap<String, String> properties = new HashMap<>(); //String = UUID as String

    public IslandProfile(int islandId, UUID ownerUuid, int x, int z, boolean isClaimed) {
        this.islandId = islandId;
        this.ownerUuid = ownerUuid;
        this.x = x;
        this.z = z;
        setClaimed(isClaimed);
        islandLocation = new Location(Bukkit.getWorld(Settings.sbOverworldName), x, IslandManager.islandY, z); //TODO: Die Welt muss angepasst werden, sobald die Multi-World-Funktion implementiert wurde!
        Location p1 = islandLocation.clone();
        Location p2 = new Location(p1.getWorld(), x + IslandManager.islandDiameter, 319, z + IslandManager.islandDiameter);
        p1.setY(-64);
        area = new IslandArea(p1, p2);

        if(ownerUuid == null) {
            spawnPoint = new Location(p1.getWorld(), x + (IslandManager.islandDiameter / 2), IslandManager.islandY, z + (IslandManager.islandDiameter / 2));
        } else spawnPoint = IslandDataReader.getSpawnPoint(ownerUuid.toString());

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
        Location l = null;
        if(spawnPoint != null) {
            Location l2 = spawnPoint.clone().add(0, -1, 0);
            if(l2.getBlock().getType() == Material.AIR || l2.getBlock().getType() == Material.LAVA) l2.getBlock().setType(Material.GLASS);
            l = spawnPoint.clone();
        } else {
            Location loc = islandLocation.clone().add(((double) IslandManager.islandDiameter / 2) + 0.5, 0, ((double) IslandManager.islandDiameter / 2) + 0.5);
            Location l1 = loc.clone().add(0,-1,0);
            if(l1.getBlock().getType() == Material.AIR || l1.getBlock().getType() == Material.LAVA) l1.getBlock().setType(Material.GLASS);
            l = loc.clone();
        }

        if(l.getBlock().getType() != Material.AIR) l.getBlock().setType(Material.AIR);
        if(l.clone().add(0,1,0).getBlock().getType() != Material.AIR) l.clone().add(0,1,0).getBlock().setType(Material.AIR);
        ent.setFallDistance(0f);
        ent.teleport(l);
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

    public void saveProperties() {
        File file = new File("plugins/" + SB.name() + "/islands/" + ownerUuid.toString() + ".yml");
        if(!file.exists()) {
            //Chat.sendOperatorMessage("Fehler beim Laden der Islanddatei für Insel " + islandId + ". Möglicherweise fehlt die Datei " + ownerUuid.toString()+".yml im /islands/ Ordner.");
            return;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> props = new ArrayList<>();
        HashMap<String, String> p = (HashMap<String, String>) properties.clone();
        for(String s : p.keySet()) {
            props.add(s + ": " + p.get(s));
        }

        cfg.set("Properties", props);
        try {
            cfg.save(file);
            Chat.debug("Properties von Insel " + islandId + " gespeichert!");
        } catch (IOException e) {
            SB.log("Konnte Properties von Insel " + islandId + " nicht speichern!");
            Chat.debug("Konnte Properties von Insel " + islandId + " nicht speichern!");
            throw new RuntimeException(e);
        }

    }

    public void setProperty(String key, String value) {
        properties.remove(key);
        properties.put(key, value);
    }
    public void setPropertyAllTo(String value) {
        HashMap<String, String> props = (HashMap<String, String>) properties.clone();
        for(String s : props.keySet()) {
            props.put(s, value);
        }
        properties = props;
    }

    public int killHostileMobs() {
        int amount = 0;
        for(Entity ent : area.getEntities()) {
            if(ent instanceof Monster) {
                if(ent.getCustomName() == null) {
                    ent.remove();
                    amount ++;
                }
            }
        }
        return amount;
    }

    public Location getCenter() {
        return area.getCenter();
    }
}
