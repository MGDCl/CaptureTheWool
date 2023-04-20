package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WinDancePigLand implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private Random random;
    private int maxOfCenter, firstUp, maxRandomUp, taskTick;

    public WinDancePigLand() {
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
                Pig p1 = world.spawn(loc1, Pig.class);
                Pig p2 = world.spawn(loc2, Pig.class);
                Pig p3 = world.spawn(loc3, Pig.class);
                Pig p4 = world.spawn(loc4, Pig.class);
                Pig p5 = world.spawn(loc5, Pig.class);
                Pig p6 = world.spawn(loc6, Pig.class);
                Pig p7 = world.spawn(loc7, Pig.class);
                Pig p8 = world.spawn(loc8, Pig.class);
                Pig p9 = world.spawn(loc9, Pig.class);
                Pig p10 = world.spawn(loc10, Pig.class);
                Pig p11 = world.spawn(loc11, Pig.class);
                Pig p12 = world.spawn(loc12, Pig.class);
                p1.setNoDamageTicks(Integer.MAX_VALUE);
                p2.setNoDamageTicks(Integer.MAX_VALUE);
                p3.setNoDamageTicks(Integer.MAX_VALUE);
                p4.setNoDamageTicks(Integer.MAX_VALUE);
                p5.setNoDamageTicks(Integer.MAX_VALUE);
                p6.setNoDamageTicks(Integer.MAX_VALUE);
                p7.setNoDamageTicks(Integer.MAX_VALUE);
                p8.setNoDamageTicks(Integer.MAX_VALUE);
                p9.setNoDamageTicks(Integer.MAX_VALUE);
                p10.setNoDamageTicks(Integer.MAX_VALUE);
                p11.setNoDamageTicks(Integer.MAX_VALUE);
                p12.setNoDamageTicks(Integer.MAX_VALUE);
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
        return new WinDancePigLand();
    }

}