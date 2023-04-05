package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectNotes implements WinEffect {

    private ArrayList<Item> items = new ArrayList<>();
    private BukkitTask task;
    private Material[] discs = new Material[]{Material.GOLD_RECORD, Material.GREEN_RECORD, Material.RECORD_3, Material.RECORD_4, Material.RECORD_5, Material.RECORD_6, Material.RECORD_7, Material.RECORD_8, Material.RECORD_9, Material.RECORD_10, Material.RECORD_11, Material.RECORD_12};

    @Override
    public void start(Player p, Game game) {
        task = new BukkitRunnable() {
            String name = game.getSpectator().getWorld().getName();

            @Override
            public void run() {
                if (p == null || !p.isOnline() || !name.equals(p.getWorld().getName())) {
                    stop();
                    return;
                }
                Item item = spawnDisc(p.getLocation(), random(-0.25, 0.25), 0.8, random(-0.25, 0.25));
                p.playSound(item.getLocation(), UltraCTW.get().getCm().getWineffectnotes(), 1.0f, 1.0f);
                UltraCTW.get().getVc().getNMS().broadcastParticle(p.getLocation(), ThreadLocalRandom.current().nextInt(0, 24), 0, 0, 1, "NOTE", 5, 10);
                items.add(item);
                for (Item c : new ArrayList<>(items)) {
                    if (c.getTicksLived() > 30) {
                        c.remove();
                        UltraCTW.get().getVc().getNMS().broadcastParticle(item.getLocation(), ThreadLocalRandom.current().nextInt(0, 24), 0, 0, 1, "NOTE", 5, 10);
                        items.remove(c);
                    }
                }
            }
        }.runTaskTimer(UltraCTW.get(), 0, 6);
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
                Item item = spawnDisc(p.getLocation(), random(-0.25, 0.25), 0.8, random(-0.25, 0.25));
                p.playSound(item.getLocation(), UltraCTW.get().getCm().getWineffectnotes(), 1.0f, 1.0f);
                UltraCTW.get().getVc().getNMS().broadcastParticle(p.getLocation(), ThreadLocalRandom.current().nextInt(0, 24), 0, 0, 1, "NOTE", 5, 10);
                items.add(item);
                for (Item c : new ArrayList<>(items)) {
                    if (c.getTicksLived() > 30) {
                        c.remove();
                        UltraCTW.get().getVc().getNMS().broadcastParticle(item.getLocation(), ThreadLocalRandom.current().nextInt(0, 24), 0, 0, 1, "NOTE", 5, 10);
                        items.remove(c);
                    }
                }
            }
        }.runTaskTimer(UltraCTW.get(), 0, 6);
    }

    @Override
    public void stop() {
        items.clear();
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectNotes();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }

    private Item spawnDisc(Location location, double d, double d2, double d3) {
        ItemStack itemStack = new ItemStack(this.discs[ThreadLocalRandom.current().nextInt(this.discs.length)]);
        Item item = location.getWorld().dropItem(location, itemStack);
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setVelocity(new Vector(d, d2, d3));
        return item;
    }

}