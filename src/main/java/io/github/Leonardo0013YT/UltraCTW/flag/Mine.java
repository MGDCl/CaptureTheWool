package io.github.Leonardo0013YT.UltraCTW.flag;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class Mine {

    private Material material;
    private int regenerate, coins;
    private String key;

    public Mine(UltraCTW plugin, String path, String key) {
        this.key = key;
        this.material = Material.valueOf(plugin.getMines().get(path + ".material"));
        this.regenerate = plugin.getMines().getInt(path + ".regenerate");
        this.coins = plugin.getMines().getIntOrDefault(path + ".coins", 4);
    }

}