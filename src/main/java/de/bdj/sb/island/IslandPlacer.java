package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.SkyWorldGenerator;
import de.bdj.sb.island.weapi.WEIslandPaster;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class IslandPlacer {

    private UUID uuid;
    private int islandId = 0;
    private Player p;

    public IslandPlacer(UUID uuid, int islandId) {
        this.uuid = uuid;
        this.islandId = islandId;
        if(uuid == null ||islandId < 1) {
            SB.log("Placement of an island got cancelled, because the given parameter UUID is null or islandId is <1.", "Parameters:", "UUID: " + (uuid == null ? "null" : uuid.toString()), "islandId: " + islandId);
            Chat.sendOperatorMessage("§bACHTUNG", "Placement of an island got cancelled, because the given parameter UUID is null or islandId is <1.", "Parameters:", "UUID: " + (uuid == null ? "null" : uuid.toString()), "islandId: " + islandId);
            IslandGenManager.cancelIslandPlacer(uuid);
            return;
        }
        preInit();
    }

    public void preInit() {
        p = Bukkit.getPlayer(uuid);

        init();
    }
    public void init() {
        //TODO: Start placing process.
        IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
        if(ip != null && ip.getOwnerUuid() == null) ip.setOwnerUuid(uuid);
        if(Bukkit.getWorld(Settings.sbOverworldName) == null) {
            //Generate SkyBlock Worlds
            SkyWorldGenerator.checkWorlds();
        }
        Location l = new Location(Bukkit.getWorld(Settings.sbOverworldName), ip.getX(), IslandManager.islandY, ip.getZ());

        try {
            WEIslandPaster.pasteSchematic(l.clone().add(0, 2, 0), "island_1_overworld");
        } catch (Exception e) {
            l.add(-1, 0, -1).getBlock().setType(Material.BEDROCK);
            l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
            l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
            l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
            l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
            l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
            l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
            l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
            l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
            throw new RuntimeException(e);
        }

        l = new Location(Bukkit.getWorld(Settings.sbNetherName), ip.getX(), IslandManager.islandY, ip.getZ());
        l.add(-1, 0, -1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);

        l = new Location(Bukkit.getWorld(Settings.sbEndName), ip.getX(), IslandManager.islandY, ip.getZ());
        l.add(-1, 0, -1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);

        //Run Schematic placer here

        p.teleport(new Location(Bukkit.getWorld(Settings.sbOverworldName), ip.getX(), IslandManager.islandY, ip.getZ()).add(0.5, 3, 0.5));

        finish();
    }

    public void finish() {
        //Island creation has finished. Deleting the IslandPlacer session
        IslandGenManager.cancelIslandPlacer(uuid);
    }

    private File convertInputStreamToFile(InputStream inputStream, String fileName) throws IOException {
        // Erstelle eine temporäre Datei
        File tempFile = File.createTempFile(fileName, null);
        tempFile.deleteOnExit(); // Lösche die Datei, wenn das Programm beendet wird

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }
}
