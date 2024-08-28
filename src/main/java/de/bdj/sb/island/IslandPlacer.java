package de.bdj.sb.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.*;
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
        Location l = new Location(Bukkit.getWorld(Settings.sbOverworldName), ip.getX(), IslandManager.islandY, ip.getZ());

        try {
            pasteSchematic(l.clone().add(0, 2, 0), convertInputStreamToFile(SB.getInstance().getResource("schematics/island_1.schem"), "island_1.schem"));
            Chat.sendOperatorMessage("§aIsland gepastet!");
        } catch (IOException e) {
            Chat.sendOperatorMessage("Island konnte nicht gepastet werden!");
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

    public void pasteSchematic(Location loc, File schematicFile) {
        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(loc.getWorld());

        try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().newEditSession(adaptedWorld)) {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            if (format != null) {
                try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
                    Clipboard clipboard = reader.read();

                    Vector nv = loc.toVector();

                    ForwardExtentCopy copy = new ForwardExtentCopy(
                            clipboard,
                            clipboard.getRegion(),
                            clipboard.getOrigin(),
                            editSession,
                            new BlockVector3(nv.getBlockX(), nv.getBlockY(), nv.getBlockZ())
                    );
                    Operations.complete(copy);
                    editSession.flushSession();
                } catch (WorldEditException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
