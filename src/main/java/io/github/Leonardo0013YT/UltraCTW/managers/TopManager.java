package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.TopType;
import io.github.Leonardo0013YT.UltraCTW.tops.Top;
import java.util.HashMap;
import java.util.List;

public class TopManager {

    private final HashMap<TopType, Top> tops = new HashMap<>();
    private final UltraCTW plugin;

    public TopManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void addTop(TopType type, List<String> tops) {
        this.tops.put(type, new Top(type, tops));
    }

}