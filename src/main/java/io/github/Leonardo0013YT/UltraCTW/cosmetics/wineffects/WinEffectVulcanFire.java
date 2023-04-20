package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectVulcanFire implements WinEffect, Cloneable {

    private Collection<FallingBlock> fires = new ArrayList<>();
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
                p.playSound(p.getLocation(), UltraCTW.get().getCm().getWineffectsvulcanfire(), 1.0f, 1.0f);
                FallingBlock fallingBlock = spawnFire(p.getLocation(), random(-0.5, 0.5), random(-0.5, 0.5));
                fallingBlock.setDropItem(false);
                fires.add(fallingBlock);
            }
        }.runTaskTimer(UltraCTW.get(), 0, 2);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
        for (FallingBlock fb : fires) {
            if (fb == null) continue;
            if (!fb.isDead()) {
                fb.remove();
            } else if (fb.isOnGround()) {
                fb.getLocation().getBlock().setType(Material.AIR);
            }
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectVulcanFire();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private FallingBlock spawnFire(Location location, double d, double d3) {
        @SuppressWarnings("deprecation")
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, Material.FIRE, (byte) ThreadLocalRandom.current().nextInt(15));
        fallingBlock.setVelocity(new Vector(d, 0.75, d3));
        return fallingBlock;
    }

}
