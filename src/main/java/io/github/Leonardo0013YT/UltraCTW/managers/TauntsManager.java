package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TauntsManager {

    private final HashMap<Integer, Taunt> taunts = new HashMap<>();
    private final UltraCTW plugin;
    private int lastPage;

    public TauntsManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadTaunts() {
        if (!plugin.getTaunt().isSet("taunts")) {
            return;
        }
        taunts.clear();
        ConfigurationSection conf = plugin.getTaunt().getConfig().getConfigurationSection("taunts");
        for (String c : conf.getKeys(false)) {
            loadTaunt(c);
        }
    }

    private void loadTaunt(String c) {
        int id = plugin.getTaunt().getInt("taunts." + c + ".id");
        taunts.put(id, new Taunt(plugin, "taunts." + c));
    }

    public int getNextId() {
        return taunts.size();
    }

    public void execute(Player p, EntityDamageEvent.DamageCause cause, Game game, int id) {
        if (taunts.get(id) == null) {
            taunts.get(0).execute(p, cause, game);
        } else {
            taunts.get(id).execute(p, cause, game);
        }
    }

    public void execute(Player p, Game game, int id) {
        if (taunts.get(id) == null) {
            taunts.get(0).execute(p, game);
        } else {
            taunts.get(id).execute(p, game);
        }
    }

    public Taunt getTauntByItem(Player p, ItemStack item) {
        for (Taunt k : taunts.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, Taunt> getTaunts() {
        return taunts;
    }

    public int getTauntsSize() {
        return taunts.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (taunts.containsKey(sw.getTaunt())) {
            return taunts.get(sw.getTaunt()).getName();
        }
        return plugin.getLang().get(null, "messages.noSelected");
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        if (getLastPage() < lastPage) {
            this.lastPage = lastPage;
        }
    }

}