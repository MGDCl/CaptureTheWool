package io.github.Leonardo0013YT.UltraCTW.streak;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Streak {

    private int streak, kills;
    private boolean bounty;
    private double price;
    private long lastKill;

}