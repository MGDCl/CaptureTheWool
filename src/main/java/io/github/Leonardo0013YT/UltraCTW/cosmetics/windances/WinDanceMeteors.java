package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class WinDanceMeteors implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private int maxOfCenter, firstUp, taskTick;

    public WinDanceMeteors() {
        this.task = null;
    }

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            maxOfCenter = plugin.getWindance().getIntOrDefault(path + ".maxOfCenter", 3);
            firstUp = plugin.getWindance().getIntOrDefault(path + ".firstUp", 100);
            taskTick = plugin.getWindance().getIntOrDefault(path + ".taskTick", 4);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Game game) {
        World world = game.getSpectator().getWorld();
        Location center = new Location(world, 0, firstUp, 0);
        task = new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                Fireball fb = world.spawn(center, Fireball.class);
                fb.setVelocity(new Vector(ThreadLocalRandom.current().nextDouble(-maxOfCenter, maxOfCenter), -1.5, ThreadLocalRandom.current().nextDouble(-maxOfCenter, maxOfCenter)));
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
        return new WinDanceMeteors();
    }

}