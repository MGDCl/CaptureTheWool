package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class WinDanceFireworks implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private Random random;
    private int minOfCenter, maxOfCenter, firstUp, taskTick;

    public WinDanceFireworks() {
        this.random = new Random();
    }

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            minOfCenter = plugin.getWindance().getIntOrDefault(path + ".minOfCenter", 10);
            maxOfCenter = plugin.getWindance().getIntOrDefault(path + ".maxOfCenter", 10);
            firstUp = plugin.getWindance().getIntOrDefault(path + ".firstUp", 75);
            taskTick = plugin.getWindance().getIntOrDefault(path + ".taskTick", 20);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Game game) {
        World world = game.getSpectator().getWorld();
        Location loc1 = new Location(world, minOfCenter, firstUp, minOfCenter);
        Location loc2 = new Location(world, -minOfCenter, firstUp, minOfCenter);
        Location loc3 = new Location(world, minOfCenter, firstUp, -minOfCenter);
        Location loc4 = new Location(world, -minOfCenter, firstUp, -minOfCenter);
        Location loc5 = new Location(world, maxOfCenter, firstUp, maxOfCenter);
        Location loc6 = new Location(world, -maxOfCenter, firstUp, maxOfCenter);
        Location loc7 = new Location(world, maxOfCenter, firstUp, -maxOfCenter);
        Location loc8 = new Location(world, -maxOfCenter, firstUp, -maxOfCenter);
        task = new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                firework(loc1);
                firework(loc2);
                firework(loc3);
                firework(loc4);
                firework(loc5);
                firework(loc6);
                firework(loc7);
                firework(loc8);
            }
        }.runTaskTimer(UltraCTW.get(), 0, taskTick);
    }

    @Override
    public void start(Player p, GameFlag game) {
        World world = game.getSpectator().getWorld();
        Location loc1 = new Location(world, minOfCenter, firstUp, minOfCenter);
        Location loc2 = new Location(world, -minOfCenter, firstUp, minOfCenter);
        Location loc3 = new Location(world, minOfCenter, firstUp, -minOfCenter);
        Location loc4 = new Location(world, -minOfCenter, firstUp, -minOfCenter);
        Location loc5 = new Location(world, maxOfCenter, firstUp, maxOfCenter);
        Location loc6 = new Location(world, -maxOfCenter, firstUp, maxOfCenter);
        Location loc7 = new Location(world, maxOfCenter, firstUp, -maxOfCenter);
        Location loc8 = new Location(world, -maxOfCenter, firstUp, -maxOfCenter);
        task = new BukkitRunnable() {
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                firework(loc1);
                firework(loc2);
                firework(loc3);
                firework(loc4);
                firework(loc5);
                firework(loc6);
                firework(loc7);
                firework(loc8);
            }
        }.runTaskTimer(UltraCTW.get(), 0, taskTick);
    }


    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceFireworks();
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

}