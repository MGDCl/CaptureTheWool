package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KillEffectSquid implements KillEffect, Cloneable {

    private BukkitTask task;
    private int pased = 0, lavaAmount, explosionAmount;
    private boolean loaded = false;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            lavaAmount = plugin.getKilleffect().getIntOrDefault(path + ".lavaAmount", 1);
            explosionAmount = plugin.getKilleffect().getIntOrDefault(path + ".explosionAmount", 1);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        Squid squid = loc.getWorld().spawn(loc, Squid.class);
        squid.setNoDamageTicks(Integer.MAX_VALUE);
        squid.setMetadata("KILLEFFECT", new FixedMetadataValue(UltraCTW.get(), "KILLEFFECT"));
        task = new BukkitRunnable() {
            @Override
            public void run() {
                pased++;
                if (pased >= 20) {
                    squid.remove();
                    cancel();
                    return;
                }
                Location loc = squid.getLocation().clone().add(0, 0.3 * pased, 0);
                squid.teleport(loc);
                loc.getWorld().playEffect(loc, Effect.EXPLOSION, explosionAmount);
                loc.getWorld().playEffect(loc, Effect.LAVADRIP, lavaAmount);
                p.playSound(loc, UltraCTW.get().getCm().getKillEffectSquid(), 1.0f, 1.0f);
            }
        }.runTaskTimer(UltraCTW.get(), 0, 2);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSquid();
    }

}