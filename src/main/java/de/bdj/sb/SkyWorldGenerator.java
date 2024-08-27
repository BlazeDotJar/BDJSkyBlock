package de.bdj.sb;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class SkyWorldGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return super.generateChunkData(world, random, x, z, biome);
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
