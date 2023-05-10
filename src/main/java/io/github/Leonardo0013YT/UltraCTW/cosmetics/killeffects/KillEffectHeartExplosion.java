package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class KillEffectHeartExplosion implements KillEffect, Cloneable {

    private UltraCTW plugin;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        this.plugin = plugin;
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        for (int i = 0; i < 10; i++) {
            plugin.getVc().getNMS().broadcastParticle(death.getLocation(), 1, 0, 1, 2, "HEART", 2, 2);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectHeartExplosion();
    }

}