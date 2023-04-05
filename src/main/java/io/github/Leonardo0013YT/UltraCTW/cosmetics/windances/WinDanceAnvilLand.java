package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WinDanceAnvilLand implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private Random random;
    private int maxOfCenter, firstUp, maxRandomUp, taskTick;

    public WinDanceAnvilLand() {
        this.task = null;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            maxOfCenter = plugin.getWindance().getIntOrDefault(path + ".maxOfCenter", 25);
            firstUp = plugin.getWindance().getIntOrDefault(path + ".firstUp", 100);
            maxRandomUp = plugin.getWindance().getIntOrDefault(path + ".maxRandomUp", 5);
            taskTick = plugin.getWindance().getIntOrDefault(path + ".taskTick", 5);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Game game) {
        World world = game.getSpectator().getWorld();
        task = new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                byte blockData = 0x0;
                Location loc1 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc2 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc3 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc4 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc5 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc6 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc7 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc8 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc9 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc10 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc11 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc12 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                world.spawnFallingBlock(loc1, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc3, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc4, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc5, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc6, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc7, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc8, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc9, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc10, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc11, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc12, Material.ANVIL, blockData);
            }
        }.runTaskTimer(UltraCTW.get(), taskTick, taskTick);
    }

    @Override
    public void start(Player p, GameFlag game) {
        World world = game.getSpectator().getWorld();
        task = new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                byte blockData = 0x0;
                Location loc1 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc2 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc3 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc4 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc5 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc6 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc7 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc8 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc9 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc10 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter));
                Location loc11 = new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                Location loc12 = new Location(world, -random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), -random.nextInt(maxOfCenter));
                world.spawnFallingBlock(loc1, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc2, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc3, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc4, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc5, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc6, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc7, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc8, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc9, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc10, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc11, Material.ANVIL, blockData);
                world.spawnFallingBlock(loc12, Material.ANVIL, blockData);
            }
        }.runTaskTimer(UltraCTW.get(), taskTick, taskTick);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceAnvilLand();
    }

}