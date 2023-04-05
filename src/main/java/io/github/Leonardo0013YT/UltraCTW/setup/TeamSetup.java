package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.objects.Selection;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class TeamSetup {

    private ChatColor color;
    private Location spawn;
    private HashMap<ChatColor, Location> spawners = new HashMap<>();
    private HashMap<ChatColor, Location> wools = new HashMap<>();
    private ArrayList<ChatColor> colors = new ArrayList<>();
    private ArrayList<Squared> squareds = new ArrayList<>();

    public TeamSetup(ChatColor color) {
        this.color = color;
    }

    public void addSquared(Selection s) {
        squareds.add(new Squared(s.getPos2(), s.getPos1(), false, true));
    }

}