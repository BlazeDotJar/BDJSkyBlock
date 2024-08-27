package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.result.IslandCreatorReserveResult;
import de.bdj.sb.island.result.IslandPlacerStartResult;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.session.PlayerCreateIslandSession;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class IslandCreator {

    private UUID uuid;
    private Player p;
    private int preferredIslandId = 0;

    public IslandCreator(Player p) {
        this.p = p;
        this.uuid = p.getUniqueId();
    }

    public IslandCreator(Player p, int preferredIslandId) {
        this.p = p;
        this.uuid = p.getUniqueId();
        this.preferredIslandId = preferredIslandId;
    }
    public IslandCreatorReserveResult reserve() {
        File islandFile = new File("plugins/" + SB.name() + "/islands/" + uuid.toString()+".yml");
        if(islandFile.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(islandFile);

            if(!cfg.getBoolean("Owner Deleted This Island")) {
                return IslandCreatorReserveResult.CANCELLED_PLAYER_IS_ALREADY_OWNING_AN_ISLAND;
            }
        }

        PlayerCreateIslandSession cs = new PlayerCreateIslandSession(p);
        ProfileManager.getProfile(uuid).addTempSession(cs);

        File file = new File("plugins/" + SB.name() + "/" + Settings.islandIndexFileName);
        if(!file.exists()) SB.getInstance().saveResource("island_index_file.yml", false);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        int claimed = cfg.getInt("Islands.Amount Claimed");
        int generated = cfg.getInt("Islands.Amount Generated");
        int chosenIslandId = 0;

        Location islandLoc = null;


        if(preferredIslandId < 1) {
            //Find next free island
            for(int index = 1; index != generated; index++) {
                if(cfg.getBoolean("Islands.ID-" + index + ".Claimed")) continue;
                double x = (double) cfg.getInt("Islands.ID-" + index + ".LocX");
                double z = (double) cfg.getInt("Islands.ID-" + index + ".LocZ");
                islandLoc = new Location(Bukkit.getWorld(Settings.sbOverworldName), x, IslandManager.islandY, z);
                chosenIslandId = index;
                break;
            }
        } else {
            //Try to claim a specific island
            String ownerUuid = cfg.getString("Islands.ID-" + preferredIslandId + ".Owner UUID");
            if(cfg.getBoolean("Islands.ID-" + preferredIslandId + ".Claimed") && !ownerUuid.equals(p.getUniqueId().toString())) {
                Chat.warn(p, "Die angegebene Insel ID " + preferredIslandId + " gehört einem anderen Spieler.");
                return IslandCreatorReserveResult.CANCELLED_PREFERRED_ISLAND_ID_BELONGS_TO_SOMEONE_ELSE;
            }
            double x = (double) cfg.getInt("Islands.ID-" + preferredIslandId + ".LocX");
            double z = (double) cfg.getInt("Islands.ID-" + preferredIslandId + ".LocZ");
            islandLoc = new Location(Bukkit.getWorld(Settings.sbOverworldName), x, IslandManager.islandY, z);
            chosenIslandId = preferredIslandId;
        }

        if(islandLoc == null || chosenIslandId < 1) {
            Chat.error(Bukkit.getPlayer(uuid), "Fehlgeschlagen!", "Beim Generieren deiner Insel ist etwas schiefgelaufen.", "Versuche es einfach nochmal. Sollte es dies nochmal fehlschlagen, benachrichte bitte das Serverpersonal.");
            Bukkit.getPlayer(uuid).sendTitle("§4F E H L G E S C H L A G E N", "§cSchaue in den Chat für mehr Infos.", 0, 60, 20);
            SB.log(
                    "Player " + uuid.toString() + " tried to create an island.",
                    "If this message appears multiple times, send this entire error to BlazeDotJar(Developer of this plugin) %contact%",
                    "For plugin's developer only:",
                    "Plugin version: " + SB.version(),
                    "generate()@IslandCreator.java",
                    "claimed = " + claimed,
                    "generated = " + generated,
                    "islandLoc = " + (islandLoc == null ? "null" : "world: " + islandLoc.getWorld().getName() + " x: " + islandLoc.getX() + " y: " + islandLoc.getY() + " z: " +  islandLoc.getZ()),
                    "chosen Island ID: " + chosenIslandId);
            return IslandCreatorReserveResult.CANCELLED_PARAMETER_IS_NULL;
        }

        claimed++;
        cfg.set("Islands.Amount Claimed", claimed);
        cfg.set("Islands.ID-" + chosenIslandId + ".Claimed", true);
        cfg.set("Islands.ID-" + chosenIslandId + ".Owner UUID", uuid.toString());

        ProfileManager.getProfile(uuid).removeTempSession(Settings.playerCreateSessionKey, cs);

        try {
            cfg.save(file);

            IslandPlacerStartResult ipsr = IslandGenManager.runNewIslandPlacer(uuid, chosenIslandId);
            if(ipsr == IslandPlacerStartResult.CANCELLED_BECAUSE_ALREADY_STARTED) {
                Chat.error(p, "Deine Insel-Erstellung ist bereits gestartet!");
                return IslandCreatorReserveResult.CANCELLED_ALREADY_RESERVING_AN_ISLAND;

            } else if(ipsr == IslandPlacerStartResult.CANCELLED_PARAMETER_IS_NULL) {
                Chat.error(p, "Etwas ist schief gelaufen. Bitte versuche es erneut");
                return IslandCreatorReserveResult.CANCELLED_PARAMETER_IS_NULL;

            } else if(ipsr == IslandPlacerStartResult.STARTED) {

                File f = new File("plugins/" + SB.name() + "/islands/" + uuid.toString() + ".yml");
                if(f.exists()) {
                    Chat.warn(p, "Deine persönliche Inseldatei konnte nicht angelegt werden, da du bereits eine besitzt!", "Sprich mit einem Admin darüber.", "Dieser kann deine existierende Inseldatei löschen und du kannst den vorgang wiederholen.");
                    return IslandCreatorReserveResult.CANCELLED_OLD_PLAYER_ISLAND_FILE_STILL_EXISTS;
                } else {
                    FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                    c.set("Owner UUID", uuid.toString());
                    c.set("Owner Deleted This Island", false);
                    c.set("Date When Created", SB.timeStamp.getCurrentDate());
                    c.set("Time When Created", SB.timeStamp.getCurrentTime());
                    c.set("Island ID", chosenIslandId);
                    c.set("Island Level", 0);
                    c.set("Properties", Arrays.asList("pvp: false", "mob griefing: false", "explosion damage: false", "tnt damage: false", "spread fire: false", "natural monster spawn: true"));
                    c.set("Banned Players", Arrays.asList());
                    c.set("Members", Arrays.asList());

                    c.save(f);

                    PlayerProfile pro = ProfileManager.getProfile(uuid);
                    pro.setIslandId(chosenIslandId);
                    pro.save();
                }
            }
            return IslandCreatorReserveResult.SUCCESS_ISLAND_RESERVED;
        } catch (IOException e) {
            e.printStackTrace();
            if(p != null && p.isOnline()) {
                p.sendTitle("§4F E H L G E S C H L A G E N", "§cSchaue in den Chat für mehr Infos.", 0, 60, 20);
                Chat.error(p, "Beim Speichern deiner Insel-Daten ist ein fehler aufgetreten.", "Probiere es einfach noch einmal.");
                SB.log("Error while saving island ID-" + chosenIslandId + " into " + Settings.islandIndexFileName +"(island-index-file).", "Player " + uuid.toString() + " tried to create this island.");
            }
            return IslandCreatorReserveResult.CANCELLED_SOMETHING_WENT_WRONG;
        }
    }

}
