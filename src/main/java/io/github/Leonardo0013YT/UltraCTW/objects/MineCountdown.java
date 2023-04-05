package io.github.Leonardo0013YT.UltraCTW.objects;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public class MineCountdown {

    private String mine;
    private Location block;
    private int time;

    public MineCountdown(String mine, Location block, int time) {
        this.mine = mine;
        this.block = block;
        this.time = time;
    }

    public void reduce() {
        time--;
    }

}