package de.bdj.sb.utlility;

import de.bdj.sb.SB;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PlayerAtlas {


    private static HashMap<String, String> atlasUuidToName = new HashMap<>();
    private static HashMap<String, String> atlasNameToUuid = new HashMap<>();

    public static void loadAtlas() {
        File file = new File("plugins/" + SB.name() + "/playeratlas/atlas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> regPlayers = (ArrayList<String>) cfg.getStringList("Registered Players");

        for(String uuid : regPlayers) {
            String name = cfg.getString(uuid);
            atlasUuidToName.put(uuid, name);
            atlasNameToUuid.put(name, uuid);
        }
    }

    public static void register(Player p) {

        File file = new File("plugins/" + SB.name() + "/playeratlas/atlas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> regPlayers = (ArrayList<String>) cfg.getStringList("Registered Players");
        if(!regPlayers.contains(p.getUniqueId().toString())) {
            regPlayers.add(p.getUniqueId().toString());
            cfg.set("Registered Players", regPlayers);
        }

        cfg.set(p.getUniqueId().toString(), p.getName());
        cfg.set(p.getName(), p.getUniqueId().toString());

        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static void register(List<Player> players) {
        File file = new File("plugins/" + SB.name() + "/playeratlas/atlas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> regPlayers = (ArrayList<String>) cfg.getStringList("Registered Players");
        for(Player p : players) {

            if(!regPlayers.contains(p.getUniqueId().toString())) {
                regPlayers.add(p.getUniqueId().toString());
                cfg.set("Registered Players", regPlayers);
            }
            if(cfg.getString(p.getUniqueId().toString()) == null) cfg.set(p.getUniqueId().toString(), p.getName());
            if(cfg.getString(p.getName()) == null) cfg.set(p.getName(), p.getUniqueId().toString());
        }


        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static void register(Collection<Player> players) {
        File file = new File("plugins/" + SB.name() + "/playeratlas/atlas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> regPlayers = (ArrayList<String>) cfg.getStringList("Registered Players");
        for(Player p : players) {

            if(!regPlayers.contains(p.getUniqueId().toString())) {
                regPlayers.add(p.getUniqueId().toString());
                cfg.set("Registered Players", regPlayers);
            }
            if(cfg.getString(p.getUniqueId().toString()) == null) cfg.set(p.getUniqueId().toString(), p.getName());
            if(cfg.getString(p.getName()) == null) cfg.set(p.getName(), p.getUniqueId().toString());
        }


        try { cfg.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static void checkIfNameHasChanged(Player p) {
        if(!getName(p.getUniqueId().toString()).equals(p.getName())) {
            register(p);
        }
    }
    public static String getUUID(String playername) {
        return atlasNameToUuid.get(playername);
    }
    public static String getName(String uuid) {
        if(!atlasUuidToName.containsKey(uuid)){
            File file = new File("plugins/" + SB.name() + "/playeratlas/atlas.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            ArrayList<String> regPlayers = (ArrayList<String>) cfg.getStringList("Registered Players");
            if(regPlayers.contains(uuid)) {
                return cfg.getString(uuid);
            } else return "Name Unbekannt";
        } else return atlasUuidToName.get(uuid);
    }
    public static void displayContent() {
        for(String uuid : atlasUuidToName.keySet()) {
            Chat.debug("uuid = " + uuid + " & name = " + atlasUuidToName.get(uuid));
        }
    }

}
