package io.github.Leonardo0013YT.UltraCTW.utils;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tagged {

    private HashMap<Player, Double> damagers = new HashMap<>();
    private HashMap<Player, Long> timer = new HashMap<>();
    private Player last;
    private Player damaged;
    private DecimalFormat f = new DecimalFormat("##.#");

    public Tagged(Player damaged) {
        this.damaged = damaged;
    }

    public void addPlayerDamage(Player p, double damage) {
        last = p;
        if (!damagers.containsKey(p)) {
            damagers.put(p, damage);
            timer.put(p, getTime());
            return;
        }
        double d = damagers.get(p);
        damagers.put(p, d + damage);
        timer.put(p, getTime());
    }

    public void removeDamage(double dam) {
        List<Player> to = new ArrayList<>();
        for (Player on : damagers.keySet()) {
            if (timer.get(on) < System.currentTimeMillis()) {
                to.add(on);
                continue;
            }
            if (damagers.get(on) - dam < 0) {
                to.add(on);
                continue;
            }
            damagers.put(on, damagers.get(on) - dam);
        }
        for (Player on : to) {
            timer.remove(on);
            damagers.remove(on);
        }
    }

    public void executeRewards(double maxHealth) {
        List<Player> to = new ArrayList<>();
        for (Player on : damagers.keySet()) {
            if (on == null || !on.isOnline()) continue;
            if (timer.get(on) < System.currentTimeMillis()) {
                to.add(on);
                continue;
            }
            if (on.getName().equals(last.getName())) {
                continue;
            }
            double damage = damagers.get(on);
            double percent = (damage * 100) / maxHealth;
            on.sendMessage(UltraCTW.get().getLang().get(on, "messages.assists").replaceAll("<percent>", f.format(percent)).replaceAll("<name>", damaged.getName()));
            CTWPlayer up = UltraCTW.get().getDb().getCTWPlayer(on);
            up.addCoins(UltraCTW.get().getCm().getGCoinsAssists());
            up.setXp(up.getXp() + UltraCTW.get().getCm().getXpAssists());
            up.addAssists(1);
        }
        for (Player on : to) {
            timer.remove(on);
            damagers.remove(on);
        }
        if (last != null) {
            if (!timer.containsKey(last) || !damagers.containsKey(last)) {
                return;
            }
            if (timer.get(last) < System.currentTimeMillis()) {
                timer.remove(last);
                return;
            }
            if (damagers.size() == 1) {
                CTWPlayer up = UltraCTW.get().getDb().getCTWPlayer(last);
                if (up == null) return;
                double percent = (last.getHealth() * 100) / last.getMaxHealth();
                if (percent <= 50 && percent > 25) {
                    up.addKill50(1);
                    up.addAssists(1);
                }
                if (percent <= 25 && percent > 5) {
                    up.addKill25(1);
                    up.addAssists(1);
                }
                if (percent <= 5 && percent > 1) {
                    up.addKill5(1);
                    up.addAssists(1);
                }
            }
        }
    }

    public Player getLast() {
        return last;
    }

    private long getTime() {
        return System.currentTimeMillis() + (10 * 1000);
    }

}