package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Level {

    @Getter
    private final int id;
    @Getter
    private final int xp;
    @Getter
    private final int level;
    @Getter
    private final int levelUp;
    @Getter
    private final String prefix;
    private final List<String> rewards;

    public Level(UltraCTW plugin, String path, int id) {
        this.id = id;
        this.level = plugin.getLevels().getInt(path + ".level");
        this.xp = plugin.getLevels().getInt(path + ".xp");
        this.levelUp = plugin.getLevels().getInt(path + ".levelUp");
        this.prefix = plugin.getLevels().get(path + ".prefix");
        this.rewards = plugin.getLevels().getList(path + ".rewards");
    }

    public void execute(Player p) {
        for (String r : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), r.replaceFirst("/", "").replaceAll("<player>", p.getName()));
        }
    }

}