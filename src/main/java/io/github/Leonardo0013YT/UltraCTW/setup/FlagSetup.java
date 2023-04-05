package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.PhaseType;
import io.github.Leonardo0013YT.UltraCTW.objects.Selection;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class FlagSetup {

    private UltraCTW plugin;
    private Player p;
    private String name, schematic;
    private Location lobby, spectator;
    private HashMap<Location, Material> mines = new HashMap<>();
    private ArrayList<FlagTeamSetup> teams = new ArrayList<>();
    private ArrayList<String> npcUpgrades = new ArrayList<>(), npcBuff = new ArrayList<>(), npcShop = new ArrayList<>(), npcKits = new ArrayList<>();
    private ArrayList<PhaseType> phases = new ArrayList<>();
    private FlagTeamSetup actual;
    private Selection selection = new Selection();
    private int pool = 10, min, teamSize;
    private Squared protection;

    public FlagSetup(UltraCTW plugin, Player p, String name, String schematic) {
        this.plugin = plugin;
        this.p = p;
        this.name = name;
        this.schematic = schematic;
        this.min = 10;
        this.teamSize = 5;
    }

    public ArrayList<ChatColor> getAvailableColors() {
        ArrayList<ChatColor> empty = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat() || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE))
                continue;
            boolean contains = false;
            for (FlagTeamSetup ts : teams) {
                if (ts.getColor().equals(color)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                empty.add(color);
            }
        }
        return empty;
    }

    public void save() {
        String path = "arenas." + name;
        plugin.getArenas().set(path + ".type", "FLAG");
        plugin.getArenas().set(path + ".name", name);
        plugin.getArenas().set(path + ".schematic", schematic);
        plugin.getArenas().set(path + ".lobby", Utils.getLocationString(lobby));
        plugin.getArenas().set(path + ".spectator", Utils.getLocationString(spectator));
        plugin.getArenas().set(path + ".teamSize", teamSize);
        plugin.getArenas().set(path + ".min", min);
        plugin.getArenas().set(path + ".pool", pool);
        plugin.getArenas().set(path + ".npcUpgrade", npcUpgrades);
        plugin.getArenas().set(path + ".npcBuff", npcBuff);
        plugin.getArenas().set(path + ".npcShop", npcShop);
        plugin.getArenas().set(path + ".npcKits", npcKits);
        if (protection != null) {
            plugin.getArenas().set(path + ".lobbyProtection.min", Utils.getLocationString(protection.getMin()));
            plugin.getArenas().set(path + ".lobbyProtection.max", Utils.getLocationString(protection.getMax()));
        }
        int id = 0;
        for (Location l : mines.keySet()) {
            String tpath = "arenas." + name + ".mines." + id;
            Material material = mines.get(l);
            plugin.getArenas().set(tpath + ".loc", Utils.getLocationString(l));
            plugin.getArenas().set(tpath + ".material", material.name());
            id++;
        }
        for (FlagTeamSetup ts : teams) {
            String tpath = "arenas." + name + ".teams." + ts.getColor().name();
            plugin.getArenas().set(tpath + ".color", ts.getColor().name());
            plugin.getArenas().set(tpath + ".spawn", Utils.getLocationString(ts.getSpawn()));
            plugin.getArenas().set(tpath + ".flag", Utils.getLocationString(ts.getFlag()));
        }
        plugin.getArenas().save();
        p.sendMessage(plugin.getLang().get("setup.arena.saveGame"));
    }

}