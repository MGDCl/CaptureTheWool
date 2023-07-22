package io.github.Leonardo0013YT.UltraCTW.objects;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

@Getter
public class Squared {

    private final boolean noBreak;
    private final boolean noEntry;
    private final Location min;
    private final Location max;
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;

    public Squared(Location max, Location min, boolean noBreak, boolean noEntry) {
        this.min = min;
        this.max = max;
        this.noBreak = noBreak;
        this.noEntry = noEntry;
        this.xMin = Math.min(min.getBlockX(), max.getBlockX());
        this.xMax = Math.max(min.getBlockX(), max.getBlockX());
        this.yMin = Math.min(min.getBlockY(), max.getBlockY());
        this.yMax = Math.max(min.getBlockY(), max.getBlockY());
        this.zMin = Math.min(min.getBlockZ(), max.getBlockZ());
        this.zMax = Math.max(min.getBlockZ(), max.getBlockZ());
    }

    public Location getMin() {
        return min;
    }

    public Location getMax() {
        return max;
    }

    public boolean isInCuboid(Location loc) {
        return loc.getWorld() == this.max.getWorld() && loc.getBlockX() >= this.xMin && loc.getBlockX() <= this.xMax && loc.getBlockY() >= this.yMin && loc.getBlockY() <= this.yMax && loc.getBlockZ() >= this.zMin && loc.getBlockZ() <= this.zMax;
    }

    public boolean isInCuboid(Entity e) {
        return this.isInCuboid(e.getLocation());
    }

}