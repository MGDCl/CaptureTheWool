package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class WinEffectCrazyBlocks implements WinEffect {

    private final ArrayList<Item> items = new ArrayList<>();
    private BukkitTask task;
    private final Material[] discs = new Material[]{Material.GOLD_RECORD, Material.GREEN_RECORD, Material.RECORD_3, Material.RECORD_4, Material.RECORD_5, Material.RECORD_6, Material.RECORD_7, Material.RECORD_8, Material.RECORD_9, Material.RECORD_10, Material.RECORD_11, Material.RECORD_12};

    @Override
    public void start(Player p, Game game) {
        task = new BukkitRunnable() {
            int count = 10;
            Location loc = p.getLocation();
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                if(p.getWorld().equals(Bukkit.getWorlds().get(0))) {
                    cancel();
                    return;
                }
                if(p.isOnline()) {
                    loc = p.getLocation();
                }
                count--;
                for(Block block : Utils.getBlocksInRadius(loc.clone().add(0, -1, 0), (10-count), true)){
                    int rand = ThreadLocalRandom.current().nextInt(100);
                    if(rand<30) {
                        if(!block.getType().equals(Material.AIR)) {
                            if(block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                                FallingBlock fblock = loc.getWorld().spawnFallingBlock(
                                        block.getLocation().clone().add(0, 1.1, 0), block.getType(), block.getData());
                                fblock.setVelocity(new Vector(0,0.3,0));
                                fblock.setDropItem(false);
                                fblock.setHurtEntities(false);
                                LivingEntity bat = (LivingEntity) loc.getWorld().spawnEntity(block.getLocation(), EntityType.BAT);
                                bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                                bat.setPassenger(fblock);
                            }
                        }
                    }
                }
                if(count==0) {
                    cancel();
                }
            }
        }.runTaskTimer(UltraCTW.get(), 0L, 3L);
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
        return new WinEffectCrazyBlocks();
    }

    protected double random(double d, double d2) {
        return d + ThreadLocalRandom.current().nextDouble() * (d2 - d);
    }


}