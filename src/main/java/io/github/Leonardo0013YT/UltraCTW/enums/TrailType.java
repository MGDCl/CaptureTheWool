package io.github.Leonardo0013YT.UltraCTW.enums;

import lombok.Getter;

@Getter
public enum TrailType {

    NORMAL(0, 0, 1),
    SPIRAL(1.5, 20, 1),
    PYRAMID(1.5, 3, 3),
    HELIX(1.5, 15, 3);

    private double radius;
    private int count, speed;

    TrailType(double radius, int speed, int count) {
        this.radius = radius;
        this.speed = speed;
        this.count = count;
    }

}
