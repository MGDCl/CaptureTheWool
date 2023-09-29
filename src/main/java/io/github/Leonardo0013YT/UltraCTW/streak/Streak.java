package io.github.Leonardo0013YT.UltraCTW.streak;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Streak {

    private int streak, kills;
    private boolean bounty;
    private double price;
    private long lastKill;

    public Streak(int streak, int kills, boolean bounty, double price, long lastKill) {
        this.streak = streak;
        this.kills = kills;
        this.bounty = bounty;
        this.price = price;
        this.lastKill = lastKill;
    }

    public int getStreak() {
        return this.streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public boolean isBounty() {
        return this.bounty;
    }

    public void setBounty(boolean bounty) {
        this.bounty = bounty;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getLastKill() {
        return this.lastKill;
    }

    public void setLastKill(long lastKill) {
        this.lastKill = lastKill;
    }

}