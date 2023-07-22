package io.github.Leonardo0013YT.UltraCTW.team;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;

@Getter
public class Team {

    private final Collection<Player> members = new ArrayList<>();
    private final ArrayList<ChatColor> colors = new ArrayList<>();
    private final Map<Location, ChatColor> wools = new HashMap<>();
    private final Map<Location, ChatColor> spawners = new HashMap<>();
    private final Map<ChatColor, Item> dropped = new HashMap<>();
    private final Map<ChatColor, ArrayList<UUID>> inProgress = new HashMap<>();
    private final ArrayList<ChatColor> captured = new ArrayList<>();
    private final ArrayList<Squared> squareds = new ArrayList<>();
    private final UltraCTW plugin;
    private final Game game;
    private final ChatColor color;
    private final int id;
    private int kills;
    private final Location spawn;
    private final String name;
    private final String prefix;

    public Team(UltraCTW plugin, Game game, String path, int id) {
        this.plugin = plugin;
        this.game = game;
        this.id = id;
        this.kills = 0;
        this.spawn = Utils.getStringLocation(plugin.getArenas().get(path + ".spawn"));
        this.color = ChatColor.valueOf(plugin.getArenas().get(path + ".color"));
        this.name = plugin.getLang().get("teams." + color.name().toLowerCase());
        this.prefix = plugin.getLang().get("prefix." + color.name().toLowerCase());
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".spawners").getKeys(false)) {
            String nowPath = path + ".spawners." + c;
            spawners.put(Utils.getStringLocation(plugin.getArenas().get(nowPath + ".loc")), ChatColor.valueOf(plugin.getArenas().get(nowPath + ".color")));
        }
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".wools").getKeys(false)) {
            String nowPath = path + ".wools." + c;
            wools.put(Utils.getStringLocation(plugin.getArenas().get(nowPath + ".loc")), ChatColor.valueOf(plugin.getArenas().get(nowPath + ".color")));
        }
        colors.addAll(wools.values());
        colors.forEach(c -> inProgress.put(c, new ArrayList<>()));
        if (!plugin.getArenas().isSet(path + ".squareds")) return;
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".squareds").getKeys(false)) {
            String nowPath = path + ".squareds." + c;
            squareds.add(new Squared(Utils.getStringLocation(plugin.getArenas().get(nowPath + ".min")), Utils.getStringLocation(plugin.getArenas().get(nowPath + ".max")), true, true));
        }
    }

    public boolean isNearby(Location loc) {
        if (!loc.getWorld().equals(spawn.getWorld())) {
            return false;
        }
        for (Location s : spawners.keySet()) {
            if (s.distance(loc) < 3) {
                return true;
            }
        }
        return false;
    }

    public void updateWorld(World w) {
        wools.keySet().forEach(o -> o.setWorld(w));
        spawners.keySet().forEach(o -> o.setWorld(w));
        spawn.setWorld(w);
        for (Squared s : squareds) {
            s.getMax().setWorld(w);
            s.getMin().setWorld(w);
        }
    }

    public void updateSpawner() {
        for (Location l : spawners.keySet()) {
            ChatColor c = spawners.get(l);
            if (!dropped.containsKey(c)) {
                Item i = l.getWorld().dropItem(l.clone().add(0, 1, 0), NBTEditor.set(Utils.getXMaterialByColor(c).parseItem(), c.name(), "TEAM", "WOOL", "CAPTURE"));
                for(double angle = 0.0; angle < 360.0; angle += 40.0) {
                    double x = Math.cos(angle);
                    double z = Math.sin(angle);
                    Vector d = (new Vector(x, 1.0, z)).multiply(0.15);
                    Item i1 = l.getWorld().dropItem(l.clone(), NBTEditor.set(Utils.getXMaterialByColor(c).parseItem(), c.name(), "TEAM", "WOOL", "CAPTURE"));
                    i1.setVelocity(d);
                }
                i.setVelocity(new Vector(0, 0, 0.1));
                i.setMetadata("DROPPED", new FixedMetadataValue(UltraCTW.get(), c.name()));
                plugin.getVc().getNMS().broadcastParticle(l, 0, 1, 0, 2, "HEART", 3, 1);
                this.dropped.put(c, i);
            }
        }
    }

    public boolean checkWools() {
        boolean all = true;
        for (ChatColor c : colors) {
            all = captured.contains(c);
            if (!all) {
                break;
            }
        }
        return all;
    }

    public boolean isInProgress(ChatColor c) {
        return !inProgress.get(c).isEmpty();
    }

    public boolean isCaptured(ChatColor c) {
        return captured.contains(c);
    }

    public void playSound(XSound sound, float v1, float v2) {
        members.forEach(m -> m.playSound(m.getLocation(), sound.parseSound(), v1, v2));
    }

    public void sendTitle(String title, String subtitle, int in, int stay, int out) {
        plugin.getVc().getReflection().sendTitle(title, subtitle, in, stay, out, members);
    }

    public void sendMessage(String msg) {
        members.forEach(m -> m.sendMessage(msg));
    }

    public void addKill() {
        kills++;
    }

    public void reset() {
        members.clear();
        inProgress.clear();
        captured.clear();
        dropped.clear();
        colors.forEach(c -> inProgress.put(c, new ArrayList<>()));
    }

    public void addMember(Player p) {
        if (!members.contains(p)) {
            members.add(p);
        }
    }

    public void removeMember(Player p) {
        members.remove(p);
    }

    public int getTeamSize() {
        return members.size();
    }

    public Squared getPlayerSquared(Player p) {
        for (Squared s : squareds) {
            if (s.isInCuboid(p)) {
                return s;
            }
        }
        return null;
    }

    public Squared getPlayerSquared(Location loc) {
        for (Squared s : squareds) {
            if (s.isInCuboid(loc)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id && kills == team.kills && color == team.color;
    }

}