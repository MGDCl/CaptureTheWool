package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.Kit;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class KitManager {

    private HashMap<Integer, Kit> kits = new HashMap<>();
    private ArrayList<Integer> defaultKits = new ArrayList<>();
    private int lastPage;
    private UltraCTW plugin;

    public KitManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadKits() {
        if (!plugin.getKits().isSet("kits")) return;
        for (String k : plugin.getKits().getConfig().getConfigurationSection("kits").getKeys(false)) {
            kits.put(plugin.getKits().getInt("kits." + k + ".id"), new Kit(plugin, "kits." + k));
        }
    }

    public void giveDefaultKit(Player p, Game game, Team team) {
        Kit kit = kits.get(game.getDefKit());
        if (kit == null) return;
        kit.giveKit(p, 1, team);
    }

    public void giveKit(Player p, int id, int level, Team team) {
        Kit kit = kits.get(id);
        if (kit == null) return;
        kit.giveKit(p, level, team);
    }

    public Kit getKitByItem(Player p, ItemStack item) {
        for (Kit k : kits.values()) {
            if (k.getKitLevelByItem(p, item) != null) {
                return k;
            }
        }
        return null;
    }

    public KitLevel getKitLevelByItem(Kit k, Player p, ItemStack item) {
        return k.getKitLevelByItem(p, item);
    }

    public int getNextID() {
        return kits.size();
    }

    public void setLastPage(int lastPage) {
        if (getLastPage() < lastPage) {
            this.lastPage = lastPage;
        }
    }

}