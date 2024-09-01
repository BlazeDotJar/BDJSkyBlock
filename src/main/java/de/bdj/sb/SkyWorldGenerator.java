package de.bdj.sb;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class SkyWorldGenerator extends ChunkGenerator {
    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        super.generateSurface(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        super.generateBedrock(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public void generateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        super.generateCaves(worldInfo, random, chunkX, chunkZ, chunkData);
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        super.generateNoise(worldInfo, random, chunkX, chunkZ, chunkData);
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
        // Überprüfen, ob die Welt bereits existiert
        if (Bukkit.getWorld(Settings.sbOverworldName) != null) {
            SB.log("Die Welt " + Settings.sbOverworldName + " ist bereits geladen.");
            return;
        }

        // Erstelle einen WorldCreator für die neue Welt
        WorldCreator creator = new WorldCreator(Settings.sbOverworldName);
        creator.environment(World.Environment.NORMAL); // Setze die Umgebung (NORMAL, NETHER, THE_END)
        creator.type(WorldType.NORMAL); // Setze den Welt-Typ (NORMAL, FLAT, LARGE_BIOMES, AMPLIFIED)
        creator.generateStructures(true); // Strukturen wie Dörfer, Minenschächte etc. generieren

        // Optional: Einen eigenen ChunkGenerator setzen, falls benötigt
        creator.generator(new SkyWorldGenerator());

        // Erstelle die Welt
        World world = creator.createWorld();

        if (world != null) {
            SB.log("Die Welt " + Settings.sbOverworldName + " wurde erfolgreich erstellt.");
        } else {
            SB.log("Fehler beim Erstellen der Welt " + Settings.sbOverworldName + ".");
        }
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
