package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class NPCManager {

    private final HashMap<Player, ArrayList<NPC>> npcs = new HashMap<>();
    private final UltraCTW plugin;

    public NPCManager(UltraCTW plugin) {
        this.plugin = plugin;
        startUpdate();

    }

    public void startUpdate() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player on : npcs.keySet()) {
                for (NPC npc : npcs.get(on)) {
                    if (npc.toHide(on.getLocation())) {
                        if (npc.isShowing()) {
                            npc.destroy();
                        }
                    } else {
                        if (!npc.isShowing()) {
                            npc.spawn();
                        }
                    }
                }
            }
        }, 60, 60);
    }

    public void addNPC(Player p, NPC npc) {
        npcs.putIfAbsent(p, new ArrayList<>());
        npcs.get(p).add(npc);
    }

    public HashMap<Player, ArrayList<NPC>> getNpcs() {
        return npcs;
    }

    public void removePlayer(Player p) {
        if (npcs.containsKey(p)) {
            for (NPC npc : npcs.get(p)) {
                npc.destroy();
            }
            npcs.get(p).clear();
        }
    }

}