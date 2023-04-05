package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import lombok.Getter;

public class Level {

    @Getter
    private int id, xp, level, levelUp;
    @Getter
    private String prefix;

    public Level(UltraCTW plugin, String path, int id) {
        this.id = id;
        this.level = plugin.getLevels().getInt(path + ".level");
        this.xp = plugin.getLevels().getInt(path + ".xp");
        this.levelUp = plugin.getLevels().getInt(path + ".levelUp");
        this.prefix = plugin.getLevels().get(null, path + ".prefix");
    }

}