package io.github.Leonardo0013YT.UltraCTW.objects;

import lombok.Getter;

@Getter
public class Multiplier {

    private String type, name;
    private int id;
    private long remaining;
    private double amount;

    public Multiplier(int id, String type, String name, long remaining, double amount) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.remaining = remaining;
        this.amount = amount;
    }

}