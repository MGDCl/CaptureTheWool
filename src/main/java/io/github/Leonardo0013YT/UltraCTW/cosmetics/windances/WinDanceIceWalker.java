package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class WinDanceIceWalker implements WinDance, Cloneable {

    private boolean loaded = false;
    private BukkitTask task;
    private int round = 1, rangePerRound, taskTick;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            rangePerRound = plugin.getWindance().getIntOrDefault(path + ".rangePerRound", 5);
            taskTick = plugin.getWindance().getIntOrDefault(path + ".taskTick", 20);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Game game) {
        World world = game.getSpectator().getWorld();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                for (Block block : getNearbyBlocks(p.getLocation(), rangePerRound * round)) {
                    block.setType(Material.ICE);
                }
                round++;
            }
        }.runTaskTimer(UltraCTW.get(), 0, taskTick);
    }

    @Override
    public void start(Player p, GameFlag game) {
        World world = game.getSpectator().getWorld();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (p == null || !p.isOnline() || !world.getName().equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                for (Block block : getNearbyBlocks(p.getLocation(), rangePerRound * round)) {
                    block.setType(Material.ICE);
                }
                round++;
            }
        }.runTaskTimer(UltraCTW.get(), 0, taskTick);
    }

    public BukkitTask getTask() {
        return task;
    }

    private List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR || block.getType() == Material.ICE || block.getType() == Material.PACKED_ICE) {
                        continue;
                    }
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinDance clone() {
        return new WinDanceIceWalker();
    }

}