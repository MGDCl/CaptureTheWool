package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KillEffectBlood implements KillEffect, Cloneable {

    private boolean loaded = false;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        death.getWorld().playEffect(loc.clone().add(0, 0.9, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        death.getWorld().playEffect(loc.clone().add(0, 0.5, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        death.getWorld().playEffect(loc.clone().add(0, 0.1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        death.getWorld().playEffect(loc.clone().add(1, 0.9, 1), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        death.getWorld().playEffect(loc.clone().add(1, 0.5, 1), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        death.getWorld().playEffect(loc.clone().add(1, 0.1, 1), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectBlood();
    }

}