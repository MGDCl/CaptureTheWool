package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.HelixTrail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.enums.TrailType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class TrailsManager {

    private HashMap<Integer, Trail> trails = new HashMap<>();
    private UltraCTW plugin;
    private int lastPage;

    public TrailsManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadTrails() {
        if (!plugin.getTrail().isSet("trails")) {
            return;
        }
        trails.clear();
        ConfigurationSection conf = plugin.getTrail().getConfig().getConfigurationSection("trails");
        for (String c : conf.getKeys(false)) {
            loadTrail(c);
        }
    }

    private void loadTrail(String c) {
        int id = plugin.getTrail().getInt("trails." + c + ".id");
        trails.put(id, new Trail(plugin, "trails." + c));
        plugin.sendDebugMessage("§aTrail §b" + c + " §acargado correctamente.");
    }

    public int getNextId() {
        return trails.size();
    }

    public HashMap<Integer, Trail> getTrails() {
        return trails;
    }

    public Trail getTrailByItem(Player p, ItemStack item) {
        for (Trail k : trails.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public void spawnTrail(Projectile proj, Trail trail) {
        if (trail.getParticle().equals("NONE")) {
            return;
        }
        TrailType t = trail.getType();
        HelixTrail helix = new HelixTrail(trail.getParticle(), t.getCount(), proj.getVelocity(), t.getRadius(), t.getSpeed());
        new BukkitRunnable() {
            int executes = 0;

            @Override
            public void run() {
                if (executes >= 40) {
                    cancel();
                    return;
                }
                if (!proj.isOnGround() && !proj.isDead()) {
                    if (!t.equals(TrailType.NORMAL)) {
                        helix.spawn(proj.getLocation());
                    } else {
                        plugin.getVc().getNMS().broadcastParticle(proj.getLocation(), trail.getOffsetX(), trail.getOffsetY(), trail.getOffsetZ(), (int) trail.getSpeed(), trail.getParticle(), trail.getAmount(), trail.getRange());
                    }
                    executes++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    public int getTrailsSize() {
        return trails.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (trails.containsKey(sw.getTrail())) {
            return trails.get(sw.getTrail()).getName();
        }
        return plugin.getLang().get(null, "messages.noSelected");
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

}