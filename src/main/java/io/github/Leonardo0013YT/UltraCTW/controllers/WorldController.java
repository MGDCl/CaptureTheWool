package io.github.Leonardo0013YT.UltraCTW.controllers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.WorldEditUtils_Old;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WorldEdit;
import io.github.Leonardo0013YT.UltraSkyWars.WorldEditUtils_New;
import org.bukkit.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.util.*;

public class WorldController {

    private String clear;
    private UltraCTW plugin;
    private WorldEdit edit;

    public WorldController(UltraCTW plugin) {
        clear = plugin.getConfig().getString("schemaToClearLobby");
        this.plugin = plugin;
        if (plugin.getVc().is1_13to16()) {
            edit = new WorldEditUtils_New(plugin);
        } else {
            edit = new WorldEditUtils_Old(plugin);
        }
    }

    public void deleteWorld(String name) {
        World w = Bukkit.getWorld(name);
        if (w != null) {
            Bukkit.unloadWorld(w, false);
        }
        File path = new File(Bukkit.getWorldContainer(), name);
        deleteDirectory(path);
    }

    public void deleteDirectory(File source) {
        ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock", "Village.dat", "villages.dat"));
        if (!source.exists()) return;
        for (File f : source.listFiles()) {
            if (!ignore.contains(f.getName())) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    f.delete();
                }
            }
        }
    }

    public World createEmptyWorld(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(World.Environment.NORMAL);
        wc.generateStructures(false);
        wc.generator(getChunkGenerator());
        World w = wc.createWorld();
        w.setDifficulty(Difficulty.NORMAL);
        w.setSpawnFlags(true, true);
        w.setPVP(true);
        w.setStorm(false);
        w.setThundering(false);
        w.setWeatherDuration(Integer.MAX_VALUE);
        w.setKeepSpawnInMemory(false);
        w.setTicksPerAnimalSpawns(1);
        w.setTicksPerMonsterSpawns(1);
        w.setAutoSave(false);
        w.setGameRuleValue("mobGriefing", String.valueOf(plugin.getCm().isMobGriefing()));
        w.setGameRuleValue("doFireTick", "false");
        w.setGameRuleValue("showDeathMessages", "false");
        w.setGameRuleValue("doDaylightCycle", "false");
        w.setSpawnLocation(0, 75, 0);
        return w;
    }

    public void resetMap(Location spawn, String schematic) {
        edit.paste(spawn, schematic, true, (b) -> {
        });
    }

    public ChunkGenerator getChunkGenerator() {
        return new ChunkGenerator() {
            @Override
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Collections.emptyList();
            }

            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            public byte[] generate(World world, Random random, int x, int z) {
                return new byte[32768];
            }

            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 75, 0.0D);
            }
        };
    }

}