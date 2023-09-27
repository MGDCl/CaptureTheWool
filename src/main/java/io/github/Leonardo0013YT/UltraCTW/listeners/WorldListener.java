package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    private final UltraCTW plugin;

    public WorldListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM) || e.getEntity().getType().equals(EntityType.PLAYER) || e.getEntity() instanceof Item) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        if(e.toWeatherState()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void stopLiquids(BlockFromToEvent event) {
        if (!event.getBlock().isLiquid()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getEntity() instanceof Wither) {
            if (!(e.getTarget() instanceof Player)) {
                e.setCancelled(true);
                e.setTarget(null);
            }
        }
    }

}