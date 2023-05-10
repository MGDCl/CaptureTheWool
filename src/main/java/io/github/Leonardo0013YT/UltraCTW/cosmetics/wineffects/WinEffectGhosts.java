package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectGhosts implements WinEffect, Cloneable {

    private Collection<FallingBlock> fires = new ArrayList<>();
    private BukkitTask task;

    @Override
    public void start(Player p, Game game) {
        Location loc = p.getLocation();
        task = new BukkitRunnable() {
            int count = 15;
            @Override
            public void run() {
                if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
                    cancel();
                    return;
                }
                count--;
                int xrand = ThreadLocalRandom.current().nextInt(-16,16);
                int zrand = ThreadLocalRandom.current().nextInt(-16,16);
                Location ranloc = loc.clone().add(xrand, 10, zrand);
                ranloc.getWorld().spawnEntity(ranloc, EntityType.GHAST);
                if(count==0) {
                    cancel();
                }
            }
        }.runTaskTimer(UltraCTW.get(), 0L, 3L);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectGhosts();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

}
