package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KillEffectSnowExplosion implements KillEffect, Cloneable {

    private boolean loaded = false;
    private double xRandom, yRandom, zRandom, snowsAmount;
    private int delayDelete;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            xRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".xRandom", 0.35);
            yRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".yRandom", 0.5);
            zRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".xRandom", 0.35);
            snowsAmount = plugin.getKilleffect().getIntOrDefault(path + ".snowsAmount", 20);
            delayDelete = plugin.getKilleffect().getIntOrDefault(path + ".delayDelete", 40);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        ArrayList<Snowball> it = new ArrayList<>();
        for (int i = 0; i < snowsAmount; i++) {
            it.add(spawnSnow(loc, random(-xRandom, xRandom), yRandom, random(-zRandom, zRandom)));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Snowball snow : it) {
                    snow.remove();
                }
            }
        }.runTaskLater(UltraCTW.get(), delayDelete);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSnowExplosion();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private Snowball spawnSnow(Location location, double d, double d2, double d3) {
        Snowball item = location.getWorld().spawn(location, Snowball.class);
        item.setVelocity(new Vector(d, d2, d3));
        item.setMetadata("SNOWBALL", new FixedMetadataValue(UltraCTW.get(), "SNOWBALL"));
        return item;
    }

}