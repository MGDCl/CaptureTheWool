package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.objects.Selection;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Setter
@Getter
public class ArenaSetup {

    private UltraCTW plugin;
    private Player p;
    private String name;
    private String schematic;
    private Location lobby, spectator;
    private int min, teamSize, woolSize, amountTeams;
    private HashMap<Integer, TeamSetup> teams = new HashMap<>();
    private ArrayList<ChatColor> colors = new ArrayList<>();
    private ArrayList<Squared> squareds = new ArrayList<>();
    private ArrayList<String> npcShop = new ArrayList<>(), npcKits = new ArrayList<>();
    private TeamSetup actual;
    private Selection selection = new Selection();
    private Squared protection;

    public ArenaSetup(UltraCTW plugin, Player p, String name, String schematic) {
        this.plugin = plugin;
        this.p = p;
        this.name = name;
        this.schematic = schematic;
        this.min = 10;
        this.teamSize = 5;
        this.woolSize = 2;
        this.amountTeams = 2;
    }

    public ArrayList<ChatColor> getAvailableColors() {
        ArrayList<ChatColor> empty = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat() || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE))
                continue;
            boolean contains = false;
            for (TeamSetup ts : teams.values()) {
                if (ts.getColors().contains(color)) {
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

    public ArrayList<ChatColor> getWools() {
        if (!colors.isEmpty()) {
            return colors;
        }
        int max = woolSize * amountTeams;
        int amount = 0;
        ArrayList<ChatColor> materials = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isFormat() || color.equals(ChatColor.RESET) || color.equals(ChatColor.DARK_RED) || color.equals(ChatColor.DARK_BLUE))
                continue;
            amount++;
            colors.add(color);
            if (amount >= max) {
                break;
            }
        }
        return materials;
    }

    public void addSquared(Selection s) {
        squareds.add(new Squared(s.getPos2(), s.getPos1(), true, true));
    }

    public HashMap<Integer, TeamSetup> getTeams() {
        return teams;
    }

    public void saveTeam() {
        teams.put(teams.size(), actual);
        actual = null;
    }

    public void save(Player p) {
        String path = "arenas." + name;
        plugin.getArenas().set(path + ".type", "NORMAL");
        plugin.getArenas().set(path + ".name", name);
        plugin.getArenas().set(path + ".schematic", schematic);
        plugin.getArenas().set(path + ".lobby", Utils.getLocationString(lobby));
        plugin.getArenas().set(path + ".spectator", Utils.getLocationString(spectator));
        plugin.getArenas().set(path + ".npcShop", npcShop);
        plugin.getArenas().set(path + ".npcKits", npcKits);
        plugin.getArenas().set(path + ".teamSize", teamSize);
        plugin.getArenas().set(path + ".woolSize", woolSize);
        plugin.getArenas().set(path + ".min", min);
        plugin.getArenas().set(path + ".amountTeams", amountTeams);
        if (protection != null) {
            plugin.getArenas().set(path + ".lobbyProtection.min", Utils.getLocationString(protection.getMin()));
            plugin.getArenas().set(path + ".lobbyProtection.max", Utils.getLocationString(protection.getMax()));
        }
        String ssqpath = path + ".squareds";
        int ii = 0;
        for (Squared c : squareds) {
            plugin.getArenas().set(ssqpath + "." + ii + ".min", Utils.getLocationString(c.getMin()));
            plugin.getArenas().set(ssqpath + "." + ii + ".max", Utils.getLocationString(c.getMax()));
            ii++;
        }
        for (TeamSetup ts : teams.values()) {
            String tpath = "arenas." + name + ".teams." + ts.getColor().name();
            plugin.getArenas().set(tpath + ".spawn", Utils.getLocationString(ts.getSpawn()));
            plugin.getArenas().set(tpath + ".color", ts.getColor().name());
            String spath = tpath + ".spawners";
            for (ChatColor c : ts.getSpawners().keySet()) {
                plugin.getArenas().set(spath + "." + c.name() + ".color", c.name());
                plugin.getArenas().set(spath + "." + c.name() + ".loc", Utils.getLocationString(ts.getSpawners().get(c)));
            }
            String lpath = tpath + ".wools";
            for (ChatColor c : ts.getWools().keySet()) {
                plugin.getArenas().set(lpath + "." + c.name() + ".color", c.name());
                plugin.getArenas().set(lpath + "." + c.name() + ".loc", Utils.getLocationString(ts.getWools().get(c)));
            }
            String sqpath = tpath + ".squareds";
            int i = 0;
            for (Squared c : ts.getSquareds()) {
                plugin.getArenas().set(sqpath + "." + i + ".min", Utils.getLocationString(c.getMin()));
                plugin.getArenas().set(sqpath + "." + i + ".max", Utils.getLocationString(c.getMax()));
                i++;
            }
        }
        plugin.getArenas().save();
        p.sendMessage(plugin.getLang().get("setup.arena.saveGame"));
    }

}