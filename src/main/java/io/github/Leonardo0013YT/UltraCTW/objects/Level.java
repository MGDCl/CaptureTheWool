package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Level {

    private final int id;
    private final int xp;
    private final int level;
    private final int levelUp;
    private final String prefix;
    private final List<String> rewards;

    public Level(UltraCTW plugin, String path, int id) {
        this.id = id;
        this.level = plugin.getLevels().getInt(path + ".level");
        this.xp = plugin.getLevels().getInt(path + ".xp");
        this.levelUp = plugin.getLevels().getInt(path + ".levelUp");
        this.prefix = plugin.getLevels().get(null, path + ".prefix");
        this.rewards = plugin.getLevels().getList(path + ".rewards");
    }

    public void execute(Player p) {
        for (String r : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), r.replaceFirst("/", "").replaceAll("<player>", p.getName()));
        }
    }

    public int getId() {
        return this.id;
    }

    public int getXp() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLevelUp() {
        return this.levelUp;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public List<String> getRewards() {
        return this.rewards;
    }

}