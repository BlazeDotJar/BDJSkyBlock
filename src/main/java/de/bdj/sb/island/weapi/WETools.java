package de.bdj.sb.island.weapi;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import de.bdj.sb.SB;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Set;

public class WETools {

    private static HashMap<String, Integer> timers = new HashMap<String, Integer>();

    public static void changeBiome(Location pos1, Location pos2) {
        CuboidRegion cr = new CuboidRegion(BlockVector3.at(pos1.getX(), pos1.getY(), pos1.getZ()), BlockVector3.at(pos2.getX(), pos2.getY(), pos2.getZ()));
        Set<BlockVector2> chunks = cr.getChunks();

        HashMap<Integer, BlockVector2> ch = new HashMap<>();

        int a = 0;
        for(BlockVector2 bv : chunks) {
            ch.put(a, bv);
            a++;
        }

        long startedAt = System.currentTimeMillis();
        BukkitRunnable br = new BukkitRunnable() {
            int xx = 0;
            int yy = 0;
            int zz = 0;
            int b = 0;
            @Override
            public void run() {
                BlockVector2 bv = ch.get(b);
                Chunk c = pos1.getWorld().getChunkAt(bv.x(), bv.z());
                for(int x = 0; x != 16; x++) {
                    for(int z = 0; z != 16; z++) {
                        for(int y = -64; y != 320; y++) {
                            xx = x;
                            yy = y;
                            zz = z;
                            c.getBlock(x, y, z).setBiome(Biome.PLAINS);
                            if(y == 100) {
                                c.getBlock(x, y, z).setType(Material.WHITE_STAINED_GLASS);
                            }
                        }
                    }
                }
                Chat.debug("Changed Chunk #" + b + " of " + ch.size());
                b++;
                if(b == ch.size()) {
                    long finishedAt = System.currentTimeMillis();
                    Chat.debug("Biome Changed in " + ((finishedAt - startedAt) / 1000) + " Sekunden");
                    this.cancel();
                }
            }
        };
        br.runTaskTimer(SB.getInstance(), 0L, 5L);

    }

    /*

            int xx = 0;
        int yy = 0;
        int zz = 0;
        try {
            for(BlockVector2 bv : chunks) {
                Chunk c = pos1.getWorld().getChunkAt(bv.x(), bv.z());
                for(int x = 0; x != 15; x++) {
                    for(int z = 0; z != 15; z++) {
                        for(int y = -64; y != 320; y++) {
                            xx = x;
                            yy = y;
                            zz = z;
                            c.getBlock(x, y, z).setBiome(Biome.PLAINS);
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Chat.sendOperatorMessage("Fehler beim Biome Change.", "x = " + xx, "y = " + yy, "z = " + zz);
        }

     */

    private static String locString(Location pos1, Location pos2) {
        return pos1.getX() + "/" + pos1.getY() + "/" + pos1.getZ() + "/" + pos2.getX() + "/" + pos2.getY() + "/" + pos2.getZ();
    }

}
