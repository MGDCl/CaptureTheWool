package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import java.util.concurrent.ThreadLocalRandom;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.scheduler.BukkitTask;


public class WinEffectStorm implements WinEffect, Cloneable {

    private BukkitTask task;

    @Override
    public void start(Player p, Game game) {
        p.getWorld().setTime(p.getWorld().getTime() + 14000);
		task = new BukkitRunnable() {
			int count = 0;
			@Override
			public void run() {
				if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
					cancel();
					return;
				}
				int xrand = ThreadLocalRandom.current().nextInt(-16,16);
				int zrand = ThreadLocalRandom.current().nextInt(-16,16);
				Location ranloc = p.getLocation().clone().add(xrand, 0, zrand);
				p.getWorld().strikeLightningEffect(ranloc);
				int horserand = ThreadLocalRandom.current().nextInt(10);
				if(horserand < 3) {
					Location horseloc = ranloc.clone();
					horseloc.setY(p.getWorld().getHighestBlockYAt(ranloc));
					Entity horse = p.getWorld().spawnEntity(horseloc, EntityType.HORSE);
					((Horse) horse).setVariant(Variant.SKELETON_HORSE);
				}
				count++;
				if(count > 36) {
					cancel();
				}
			}
		}.runTaskTimer(UltraCTW.get(), 0L, 5L);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectStorm();
    }
}