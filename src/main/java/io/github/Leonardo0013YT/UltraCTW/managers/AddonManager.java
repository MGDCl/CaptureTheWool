package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.addons.PlaceholderAPIAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class AddonManager {

    private UltraCTW plugin;
    private PlaceholderAPIAddon placeholder;

    public AddonManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        if (plugin.getCm().isPlaceholdersAPI()) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholder = new PlaceholderAPIAddon();
                plugin.sendLogMessage("Hooked into §aPlaceholderAPI§e!");
            } else {
                plugin.getConfig().set("addons.placeholdersAPI", false);
                plugin.saveConfig();
                plugin.getCm().reload();
            }
        }
    }

    public String parsePlaceholders(Player p, String value) {
        if (plugin.getCm().isPlaceholdersAPI()) {
            return placeholder.parsePlaceholders(p, value);
        }
        return value;
    }

    public double getCoins(Player p) {
        return plugin.getDb().getCTWPlayer(p).getCoins();
    }

    public void removeCoins(Player p, double price) {
        plugin.getDb().getCTWPlayer(p).removeCoins(price);
    }

}