package io.github.Leonardo0013YT.UltraCTW.setup;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TauntTypeSetup {

    private Player p;
    private String damage;
    private ArrayList<String> msg;

    public TauntTypeSetup(Player p, String damage, List<String> msg) {
        this.p = p;
        this.damage = damage;
        this.msg = new ArrayList<>(msg);
    }

    public String getDamage() {
        return damage;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }
}