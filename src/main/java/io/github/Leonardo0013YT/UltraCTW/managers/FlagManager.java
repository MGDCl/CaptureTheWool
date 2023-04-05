package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.flag.Mine;
import lombok.Getter;
import org.bukkit.Material;

import java.util.HashMap;

@Getter
public class FlagManager {

    private UltraCTW plugin;
    private HashMap<String, Mine> mines = new HashMap<>();
    private HashMap<Material, String> indexMines = new HashMap<>();

    public FlagManager(UltraCTW plugin) {
        this.plugin = plugin;
        if (plugin.getMines().isSet("mines")) {
            for (String s : plugin.getMines().getConfig().getConfigurationSection("mines").getKeys(false)) {
                Mine mine = new Mine(plugin, "mines." + s, s);
                mines.put(s, mine);
                indexMines.put(mine.getMaterial(), s);
            }
        }
    }

    public boolean isMine(Material material) {
        return indexMines.containsKey(material);
    }

    public Mine getMine(Material material) {
        return mines.get(indexMines.get(material));
    }

}