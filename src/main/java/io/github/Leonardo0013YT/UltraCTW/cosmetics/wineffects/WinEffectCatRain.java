package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class WinEffectCatRain implements WinEffect, Cloneable {

    private BukkitTask task;

    @Override
    public void start(Player p, Game game) {
        task = new BukkitRunnable() {
            int count = 0;
            int rythm = 0;
            @Override
            public void run() {
                if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
                    cancel();
                    return;
                }
                int xrand = ThreadLocalRandom.current().nextInt(-16,16);
                int zrand = ThreadLocalRandom.current().nextInt(-16,16);
                Location ranloc = p.getLocation().clone().add(xrand, 10, zrand);
                Ocelot ocelot = (Ocelot) p.getWorld().spawnEntity(ranloc, EntityType.OCELOT);
                Ocelot.Type types[] = {Ocelot.Type.BLACK_CAT,Ocelot.Type.RED_CAT,Ocelot.Type.SIAMESE_CAT};
                ocelot.setCatType(types[ThreadLocalRandom.current().nextInt(types.length)]);
                for(Player lp : p.getWorld().getPlayers()) {
                    if(rythm == 0) {
                        lp.playSound(lp.getLocation(), Sound.NOTE_BASS_DRUM, 10, 1);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 10, 1.2f);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 0.5f, 2.0f);
                    }
                    else if(rythm == 1) {
                        lp.playSound(lp.getLocation(), Sound.NOTE_STICKS, 10, 1.5f);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 0.5f, 2.0f);
                    }
                    else if(rythm == 2) {
                        lp.playSound(lp.getLocation(), Sound.NOTE_SNARE_DRUM, 10, 1);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 10, 0.8f);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 0.5f, 2.0f);
                    }
                    else if(rythm == 3) {
                        lp.playSound(lp.getLocation(), Sound.NOTE_STICKS, 10, 1.5f);
                        lp.playSound(lp.getLocation(), Sound.CAT_MEOW, 0.5f, 2.0f);
                    }
                }
                rythm++;
                if(rythm == 4) {
                    rythm = 0;
                }
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
        return new WinEffectCatRain();
    }
}

