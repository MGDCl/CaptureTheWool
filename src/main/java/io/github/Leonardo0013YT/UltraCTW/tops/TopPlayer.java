package io.github.Leonardo0013YT.UltraCTW.tops;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopPlayer {

    private String type;
    private String uuid;
    private String name;
    private int position;
    private int amount;

    @Override
    public String toString() {
        return type + ":" + uuid + ":" + amount;
    }

}