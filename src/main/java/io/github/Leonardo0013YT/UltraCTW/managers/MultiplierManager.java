package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.objects.Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiplierManager {

    private Map<String, ArrayList<Multiplier>> multipliers = new HashMap<>();

    public void addMultiplier(int id, String type, String name, double amount, long remaining) {
        if (!multipliers.containsKey(type)) {
            multipliers.put(type, new ArrayList<>());
        }
        multipliers.get(type).add(new Multiplier(id, type, name, remaining, amount));
    }

    public Multiplier getServerMultiplier(String type) {
        if (multipliers.containsKey(type)) {
            Multiplier m = multipliers.get(type).get(0);
            if (m.getRemaining() < System.currentTimeMillis()) {
                UltraCTW plugin = UltraCTW.get();
                boolean removed = plugin.getDb().removeMultiplier(m.getId());
                if (removed) {
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(plugin.getLang().get(p, "messages.multiplierFinish").replace("<name>", m.getName()).replace("<type>", m.getType())));
                }
                return null;
            }
            return m;
        }
        return null;
    }

    public double getPlayerMultiplier(Player p, String type) {
        UltraCTW plugin = UltraCTW.get();
        if (p.isOp() || p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".*")) {
            return plugin.getCm().getMaxMultiplier();
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".4")) {
            return 4.0;
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".3")) {
            return 3.0;
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".2")) {
            return 2.0;
        }
        return 1.0;
    }

    public double getPlayerMultiplier(Player p, String type, double amount) {
        UltraCTW plugin = UltraCTW.get();
        if (p.isOp() || p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".*")) {
            return (plugin.getCm().getMaxMultiplier() * amount) - amount;
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".4")) {
            return (4.0 * amount) - amount;
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".3")) {
            return (3.0 * amount) - amount;
        }
        if (p.hasPermission("ultractw.multiplier." + type.toLowerCase() + ".2")) {
            return (2.0 * amount) - amount;
        }
        return 0;
    }

    public void clear() {
        multipliers.clear();
    }

}