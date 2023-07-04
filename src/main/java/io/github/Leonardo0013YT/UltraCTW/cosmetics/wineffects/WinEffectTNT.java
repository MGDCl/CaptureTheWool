package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadLocalRandom;

public class WinEffectTNT implements WinEffect, Cloneable {

    private BukkitTask task;

    @Override
    public void start(Player p, Game game) {
        task = new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
                    cancel();
                    return;
                }
                int xrand = ThreadLocalRandom.current().nextInt(-16,16);
                int zrand = ThreadLocalRandom.current().nextInt(-16,16);
                Location ranloc = p.getLocation().clone().add(xrand, 10, zrand);
                p.getWorld().spawnEntity(ranloc, EntityType.PRIMED_TNT);
                count++;
                if(count > 36) {
                    cancel();
                }
            }
        }.runTaskTimer(UltraCTW.get(), 0L, 5L);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectTNT();
    }
}
