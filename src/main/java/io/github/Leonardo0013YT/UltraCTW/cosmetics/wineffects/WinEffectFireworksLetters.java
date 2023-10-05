package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class WinEffectFireworksLetters implements WinEffect {

    private BukkitTask task;
    @Override
    public void start(Player p, Game game) {

        task = new BukkitRunnable() {
            final String name = game.getSpectator().getWorld().getName();
            public void run() {
                if (p == null || !p.isOnline() || !name.equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                Location loc1 = p.getLocation().clone().add(0, -1,0);
                Location loc2 = p.getLocation().clone().add(0,-2,0);
                Location loc3 = p.getLocation().clone().add(0,-3,0);
                Location loc4 = p.getLocation().clone().add(0,-3,0);
                TextEffect effect = new TextEffect(UltraCTW.get().getEffectManager());
                effect.setDynamicOrigin(new DynamicLocation(p.getLocation().clone().add(0,8,0)));
                effect.text = (p.getDisplayName());
                effect.particle = ParticleEffect.FIREWORKS_SPARK;
                effect.duration = 1;
                effect.period = 20;
                effect.start();
                Utils.firework(loc1);
                Utils.firework(loc2);
                Utils.firework(loc3);
                Utils.firework(loc4);
            }
        }.runTaskTimer(UltraCTW.get(), 0, 20);

    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }

    }

    @Override
    public WinEffect clone() {
        return new WinEffectFireworksLetters();
    }
}

