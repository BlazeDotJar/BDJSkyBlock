package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        //TODO: Start placing process
        IslandProfile ip = IslandManager.getIslandDataFromIndexFile(islandId);
        Location l = new Location(Bukkit.getWorld("world"), ip.getX(), IslandManager.islandY, ip.getZ());
        l.add(-1, 0, -1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        l.add(1, 0, 0).getBlock().setType(Material.BEDROCK);

        Chat.warn(p, "Die Island Erstellung ist noch in Arbeit. Die Insel ist also vorerst nur eine Bedrock Schicht. Diese Nachricht wurde aus der IslandPlacer.java gesendet");

        p.teleport(new Location(Bukkit.getWorld("world"), ip.getX(), IslandManager.islandY, ip.getZ()).add(0.5, 3, 0.5));

        finish();
    }

    public void finish() {
        //Island creation has finished. Deleting the IslandPlacer session
        IslandGenManager.cancelIslandPlacer(uuid);
    }
}
