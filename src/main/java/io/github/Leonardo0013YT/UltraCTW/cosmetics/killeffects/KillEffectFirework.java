package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class KillEffectFirework implements KillEffect, Cloneable {

    private Random random;

    public KillEffectFirework() {
        this.random = new Random();
    }

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        Firework fa = death.getWorld().spawn(loc, Firework.class);
        FireworkMeta fam = fa.getFireworkMeta();
        FireworkEffect.Type tipo = FireworkEffect.Type.values()[random.nextInt(4)];
        Color c1 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        Color c2 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        FireworkEffect ef = FireworkEffect.builder().withColor(c1).withFade(c2).with(tipo).build();
        fam.addEffect(ef);
        fam.setPower(0);
        fa.setFireworkMeta(fam);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectFirework();
    }

}