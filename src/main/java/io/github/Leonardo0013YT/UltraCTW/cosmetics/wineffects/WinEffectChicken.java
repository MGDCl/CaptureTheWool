package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectChicken implements WinEffect {

    private ArrayList<Chicken> chickens = new ArrayList<>();
    private BukkitTask task;

    @Override
    public void start(Player p, Game game) {
		task = new BukkitRunnable() {
            String name = game.getSpectator().getWorld().getName();

            @Override
            public void run() {
                if (p == null || !p.isOnline() || !name.equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                Chicken chicken = spawnChicken(p.getLocation(), random(-0.5, 0.5), 1.5, random(-0.5, 0.5));
                chicken.getLocation().getWorld().playSound(chicken.getLocation(), UltraCTW.get().getCm().getWineffectschicken(), 1.0f, 1.0f);
                chickens.add(chicken);
                for (Chicken c : new ArrayList<>(chickens)) {
                    if (c.getTicksLived() > 30) {
                        c.remove();
                        UltraCTW.get().getVc().getNMS().broadcastParticle(c.getLocation(), 0, 0, 0, 0, "REDSTONE", 1000, 10);
                        chicken.getLocation().getWorld().playSound(chicken.getLocation(), UltraCTW.get().getCm().getWineffectschicken(), 1.0f, 1.0f);
                        chickens.remove(c);
                    }
                }
            }
        }.runTaskTimer(UltraCTW.get(), 5, 5);
    }

    @Override
    public void start(Player p, GameFlag game) {
        task = new BukkitRunnable() {
            String name = game.getSpectator().getWorld().getName();

            @Override
            public void run() {
                if (p == null || !p.isOnline() || !name.equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                Chicken chicken = spawnChicken(p.getLocation(), random(-0.5, 0.5), 1.5, random(-0.5, 0.5));
                chicken.getLocation().getWorld().playSound(chicken.getLocation(), UltraCTW.get().getCm().getWineffectschicken(), 1.0f, 1.0f);
                chickens.add(chicken);
                for (Chicken c : new ArrayList<>(chickens)) {
                    if (c.getTicksLived() > 30) {
                        c.remove();
                        UltraCTW.get().getVc().getNMS().broadcastParticle(c.getLocation(), 0, 0, 0, 0, "REDSTONE", 1000, 10);
                        chicken.getLocation().getWorld().playSound(chicken.getLocation(), UltraCTW.get().getCm().getWineffectschicken(), 1.0f, 1.0f);
                        chickens.remove(c);
                    }
                }
            }
        }.runTaskTimer(UltraCTW.get(), 5, 5);
    }

    @Override
    public void stop() {
        chickens.clear();
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectChicken();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private Chicken spawnChicken(Location location, double d, double d2, double d3) {
        Chicken chicken = location.getWorld().spawn(location, Chicken.class);
        chicken.setVelocity(new Vector(d, d2, d3));
        return chicken;
    }

}