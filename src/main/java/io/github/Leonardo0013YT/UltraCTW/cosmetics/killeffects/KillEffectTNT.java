package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;

public class KillEffectTNT implements KillEffect, Cloneable {

    private boolean loaded = false;
    private int fuseTicks;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            fuseTicks = plugin.getKilleffect().getIntOrDefault(path + ".fuseTicks", 4);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (p == null || !p.isOnline()) {
            return;
        }
        if (death == null || !death.isOnline()) {
            return;
        }
        TNTPrimed primed = loc.getWorld().spawn(loc, TNTPrimed.class);
        primed.setFuseTicks(fuseTicks);
        UltraCTW plugin = UltraCTW.get();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.getVc().is1_12()) {
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                } else {
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                    loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 1);
                }
                p.playSound(p.getLocation(), plugin.getCm().getKillEffectTNT(), 1.0f, 1.0f);
                primed.remove();
            }
        }.runTaskLater(plugin, fuseTicks);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectSquid();
    }

}
