package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.addons.NametagEditAddon;
import io.github.Leonardo0013YT.UltraCTW.addons.PlaceholderAPIAddon;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NametagAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class AddonManager {

    private UltraCTW plugin;
    private PlaceholderAPIAddon placeholder;

    private NametagAddon tag;

    public AddonManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public boolean check(String pluginName) {
        UltraCTW plugin = UltraCTW.get();
        if (plugin.getConfig().isSet("addons." + pluginName) && plugin.getConfig().getBoolean("addons." + pluginName)) {
            if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
                plugin.sendLogMessage("Hooked into §a" + pluginName + "§e!");
                return true;
            } else {
                plugin.getConfig().set("addons." + pluginName, false);
                plugin.saveConfig();
                return false;
            }
        }
        return false;
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
        if (check("NametagEdit")) {
            tag = new NametagEditAddon();
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


    public String getPlayerPrefix(Player p) {
        if (tag != null) {
            return tag.getPrefix(p);
        }
        return "";
    }

    public String getPlayerSuffix(Player p) {
        if (tag != null) {
            return tag.getSuffix(p);
        }
        return "";
    }

}