package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class KillEffectDiamondExplode implements KillEffect, Cloneable {

    private boolean loaded = false;
    private double xRandom, yRandom, zRandom, diamondsAmount;
    private int delayDelete;

    @Override
    public void loadCustoms(UltraCTW plugin, String path) {
        if (!loaded) {
            xRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".xRandom", 0.35);
            yRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".yRandom", 0.5);
            zRandom = plugin.getKilleffect().getDoubleOrDefault(path + ".xRandom", 0.35);
            diamondsAmount = plugin.getKilleffect().getIntOrDefault(path + ".diamondsAmount", 10);
            delayDelete = plugin.getKilleffect().getIntOrDefault(path + ".delayDelete", 40);
            loaded = true;
        }
    }

    @Override
    public void start(Player p, Player death, Location loc) {
        if (death == null || !death.isOnline()) {
            return;
        }
        ArrayList<Item> it = new ArrayList<>();
        for (int i = 0; i < diamondsAmount; i++) {
            it.add(spawnDiamond(loc, random(-xRandom, xRandom), yRandom, random(-zRandom, zRandom)));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Item itemStack : it) {
                    itemStack.remove();
                }
            }
        }.runTaskLater(UltraCTW.get(), delayDelete);
    }

    @Override
    public void stop() {
    }

    @Override
    public KillEffect clone() {
        return new KillEffectDiamondExplode();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private Item spawnDiamond(Location location, double d, double d2, double d3) {
        Item item = location.getWorld().dropItem(location, new ItemStack(Material.DIAMOND));
        item.setVelocity(new Vector(d, d2, d3));
        item.setPickupDelay(Integer.MAX_VALUE);
        return item;
    }

}