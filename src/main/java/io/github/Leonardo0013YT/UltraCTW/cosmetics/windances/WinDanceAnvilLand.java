package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
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
                for (int i = 0; i < 12; i++) {
                    world.spawnFallingBlock(new Location(world, random.nextInt(maxOfCenter), firstUp + random.nextInt(maxRandomUp), random.nextInt(maxOfCenter)), Material.ANVIL, blockData);
                }
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