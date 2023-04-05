package io.github.Leonardo0013YT.UltraCTW.tops;

import io.github.Leonardo0013YT.UltraCTW.enums.TopType;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Top {

    private Map<Integer, TopPlayer> tops = new HashMap<>();
    private TopType type;

    public Top(TopType type, List<String> tops) {
        this.type = type;
        for (String top : tops) {
            String[] t = top.split(":");
            String uuid = t[0];
            String name = t[1];
            int position = Integer.parseInt(t[2]);
            int amount = Integer.parseInt(t[3]);
            this.tops.put(position, new TopPlayer(type.name(), uuid, name, position, amount));
        }
    }

}