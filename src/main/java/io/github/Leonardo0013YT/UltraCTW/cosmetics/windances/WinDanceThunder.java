package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class WinDanceThunder implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private int xOfCenter, zOfCenter, minOfCenter, maxOfCenter, taskTick;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            xOfCenter = plugin.getWindance().getIntOrDefault(path + ".xOfCenter", 20);
            zOfCenter = plugin.getWindance().getIntOrDefault(path + ".zOfCenter", 20);
            minOfCenter = plugin.getWindance().getIntOrDefault(path + ".minOfCenter", 10);
            maxOfCenter = plugin.getWindance().getIntOrDefault(path + ".maxOfCenter", 15);
            taskTick = plugin.getWindance().getIntOrDefault(path + ".taskTick", 20);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Game game) {
        World w = game.getSpectator().getWorld();
        Location loc1 = new Location(w, minOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), minOfCenter);
        Location loc2 = new Location(w, -minOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), minOfCenter);
        Location loc3 = new Location(w, minOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), -minOfCenter);
        Location loc4 = new Location(w, -minOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), -10);
        Location loc5 = new Location(w, maxOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), maxOfCenter);
        Location loc6 = new Location(w, -maxOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), maxOfCenter);
        Location loc7 = new Location(w, maxOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), -maxOfCenter);
        Location loc8 = new Location(w, -maxOfCenter, w.getHighestBlockYAt(xOfCenter, zOfCenter), -maxOfCenter);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (p == null || !p.isOnline() || !w.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                thunder(loc1);
                thunder(loc2);
                thunder(loc3);
                thunder(loc4);
                thunder(loc5);
                thunder(loc6);
                thunder(loc7);
                thunder(loc8);
            }
        }.runTaskTimer(UltraCTW.get(), 0, taskTick);
        p.getWorld().setTime(p.getWorld().getTime() + 14000);
    }

    private void thunder(Location loc) {
        loc.getWorld().strikeLightningEffect(loc);
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceThunder();
    }

}