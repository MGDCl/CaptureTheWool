package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectFireworks implements WinEffect, Cloneable {

    private BukkitTask task;
    private Random random;

    public WinEffectFireworks() {
        this.task = null;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public void start(Player p, Game game) {
        task = new BukkitRunnable() {
            int count = 0;
			Location playerloc = p.getLocation();

            @Override
            public void run() {
                if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
					cancel();
					return;
				}
                if(p.isOnline()) {
					playerloc = p.getLocation();
				}
                if(count == 0 || count == 8 || count == 16) {
					for(int x = -2; x<=2 ; x++) {
						for(int y = -2; y<=2 ; y++) {
							if(x==2 || x==-2 || y==2 || y==-2) {
								Location loc = p.getLocation().add(x, 0, y);
								Firework fw = (Firework) playerloc.getWorld().spawn(loc, Firework.class);
								FireworkMeta fmeta = fw.getFireworkMeta();
								fmeta.addEffect(FireworkEffect.builder().with(Type.BURST).withColor(
										Color.RED).withFlicker().withFade(Color.YELLOW).build());
								fmeta.setPower(1);
								fw.setFireworkMeta(fmeta);
							}
						}
					}
				}
                else if(count == 6 || count == 14) {
					Firework fw = (Firework) playerloc.getWorld().spawn(playerloc, Firework.class);
					FireworkMeta fmeta = fw.getFireworkMeta();
					fmeta.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).withColor
							(Color.PURPLE, Color.WHITE, Color.FUCHSIA).withFade(Color.YELLOW).build());
					fmeta.setPower(3);
					fw.setFireworkMeta(fmeta);
				}
                else {
					Firework fw = (Firework) playerloc.getWorld().spawn(playerloc, Firework.class);
					FireworkMeta fmeta = fw.getFireworkMeta();
					fmeta.addEffect(FireworkEffect.builder().with(Type.BALL).withColor
							(Color.GREEN, Color.LIME).withFade(Color.ORANGE).build());
					fmeta.setPower(2);
					fw.setFireworkMeta(fmeta);
				}
				count++;
				if(count > 16) {
					cancel();
					return;
				}
            }
        }.runTaskTimer(UltraCTW.get(), 0L, 10L);
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
                firework(p.getLocation());
            }
        }.runTaskTimer(UltraCTW.get(), 0, 6);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    private void firework(Location loc) {
        Firework fa = loc.getWorld().spawn(loc, Firework.class);
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
    public WinEffect clone() {
        return new WinEffectFireworks();
    }
}