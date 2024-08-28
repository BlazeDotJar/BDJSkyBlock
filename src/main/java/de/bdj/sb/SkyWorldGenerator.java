package de.bdj.sb;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class SkyWorldGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return super.generateChunkData(world, random, x, z, biome);
    }

    public static void checkWorlds() {
        if(Bukkit.getWorld(Settings.sbOverworldName) == null) {
            SB.log("-----> Creating " + Settings.sbOverworldName);
            SkyWorldGenerator.createOverWorld();
            SB.log("-----> Finished " + Settings.sbOverworldName + "!");
        }
        if(Bukkit.getWorld(Settings.sbNetherName) == null) {
            SB.log("-----> Creating " + Settings.sbNetherName);
            SkyWorldGenerator.createNetherWorld();
            SB.log("-----> Finished " + Settings.sbNetherName + "!");
        }
        if(Bukkit.getWorld(Settings.sbEndName) == null) {
            SB.log("-----> Creating " + Settings.sbEndName);
            SkyWorldGenerator.createEndWorld();
            SB.log("-----> Finished " + Settings.sbEndName + "!");
        }
    }

    public static void createOverWorld() {
        WorldCreator wc = new WorldCreator(Settings.sbOverworldName);
        wc.generator(new SkyWorldGenerator());
        wc.environment(World.Environment.NORMAL);
        wc.createWorld();
    }

    public static void createNetherWorld() {
        WorldCreator wc = new WorldCreator(Settings.sbNetherName);
        wc.generator(new SkyWorldGenerator());
        wc.environment(World.Environment.NETHER);
        wc.createWorld();
    }

    public static void createEndWorld() {
        WorldCreator wc = new WorldCreator(Settings.sbEndName);
        wc.generator(new SkyWorldGenerator());
        wc.environment(World.Environment.THE_END);
        wc.createWorld();
    }

}
