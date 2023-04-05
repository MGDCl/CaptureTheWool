package io.github.Leonardo0013YT.UltraCTW.team;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class FlagTeam {

    private final ChatColor color;
    private final Location spawn, flag;
    private final UltraCTW plugin;
    private final GameFlag gameFlag;
    private final ArrayList<Player> members = new ArrayList<>();
    private final HashMap<Player, ChatColor> capturing = new HashMap<>();
    private final int id;
    private int maxLifes;
    private final String name;
    @Setter
    private int lifes, upgradeHaste = 0;

    public FlagTeam(UltraCTW plugin, GameFlag gameFlag, String path, int id) {
        this.plugin = plugin;
        this.gameFlag = gameFlag;
        this.id = id;
        this.color = ChatColor.valueOf(plugin.getArenas().get(path + ".color"));
        this.spawn = Utils.getStringLocation(plugin.getArenas().get(path + ".spawn"));
        this.flag = Utils.getStringLocation(plugin.getArenas().get(path + ".flag"));
        this.name = plugin.getLang().get("teams." + color.name().toLowerCase());
        this.lifes = 0;
    }

    public void updateWorld(World w){
        flag.setWorld(w);
        spawn.setWorld(w);
    }

    public void addMember(Player p) {
        if (!members.contains(p)) {
            members.add(p);
        }
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

    public void fillLifes() {
        lifes = gameFlag.getPool() * Math.max(members.size(), 1);
        maxLifes = lifes;
    }

    public void removeLife() {
        lifes--;
    }

    public void setCapturing(Player p, ChatColor color) {
        capturing.put(p, color);
    }

    public boolean isCapturing(Player p) {
        return capturing.containsKey(p);
    }

    public ChatColor getCapturing(Player p) {
        return capturing.get(p);
    }

    public void removeCapturing(Player p) {
        capturing.remove(p);
    }

    public boolean isStolen() {
        return flag.getBlock().getType().equals(Material.AIR);
    }

    public boolean isFlag(Location loc) {
        return loc.getBlock().getLocation().equals(flag.getBlock().getLocation());
    }

    public ItemStack getFlagItem() {
        ItemStack banner = new ItemStack(Material.BANNER, 1);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.setBaseColor(Utils.getDyeColorByChatColor(color));
        banner.setItemMeta(meta);
        return banner;
    }

    public void reset() {
        members.clear();
    }

    public void removeMember(Player p) {
        members.remove(p);
    }

    public int getTeamSize() {
        return members.size();
    }

    public boolean equals(FlagTeam ft) {
        return id == ft.getId();
    }

}