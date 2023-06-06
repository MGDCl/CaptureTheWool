package io.github.Leonardo0013YT.UltraCTW.listeners;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.api.events.CTWNPCInteractEvent;
import io.github.Leonardo0013YT.UltraCTW.api.events.PlayerLoadEvent;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Tagged;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class PlayerListener implements Listener {

    private UltraCTW plugin;

    public PlayerListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getDb().loadPlayer(p);
        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> check(p, pl))
                .forEach(pl -> pl.hidePlayer(p));
        //TODO Enviar mensaje de bienvenido, toggleable por la config
    }

    @EventHandler
    public void onLoad(PlayerLoadEvent e) {
        Player p = e.getPlayer();
        plugin.getLvl().checkUpgrade(p);
        Utils.updateSB(p);
        givePlayerItems(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        remove(e.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        remove(e.getPlayer());
    }

    private void remove(Player p) {
        plugin.getLvl().remove(p);
        plugin.getTgm().removeTag(p);
        plugin.getStm().removeStreak(p);
        plugin.getSb().remove(p);
        plugin.getDb().savePlayer(p.getUniqueId(), false);
        plugin.getGm().removePlayerGame(p, true);
        NametagEdit.getApi().clearNametag(p);
    }

    @EventHandler
    public void onTNT(EntityExplodeEvent e) {
        String name = e.getLocation().getWorld().getName();
        if (plugin.getGm().getGameNames().containsKey(name)) {
            e.blockList().clear();
        }
    }

    @EventHandler
    public void onInteractEntity(CTWNPCInteractEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        NPC npc = e.getNpc();
        if (npc == null) {
            return;
        }
        if (g != null) {
            if (npc.getNpcType().equals(NPCType.KITS)) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createKitSelectorMenu(p);
            } else if (npc.getNpcType().equals(NPCType.SHOP)) {
                plugin.getGem().createShopMenu(p);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (plugin.getCm().getMainLobby() != null) {
            World w = plugin.getCm().getMainLobby().getWorld();
            if (w == null) return;
            if (p.getWorld().getName().equals(w.getName())) {
                e.getRecipients().clear();
                e.getRecipients().addAll(w.getPlayers());
                String msg = formatMainLobby(p, e.getMessage());
                msg = msg.replaceAll("%", "%%");
                e.setFormat(msg);
            }
        }
        if (g == null) return;
        e.getRecipients().clear();
        String msg;
        if (g.getInLobby().contains(p)) {
            msg = formatLobby(p, e.getMessage());
            e.getRecipients().addAll(g.getInLobby());
        } else {
            Team t = g.getTeamPlayer(p);
            if (t == null) {
                msg = formatLobby(p, e.getMessage());
                e.getRecipients().addAll(g.getCached());
            } else {
                if (ChatColor.stripColor(e.getMessage()).startsWith("!")) {
                    msg = formatGame(p, t, e.getMessage());
                    e.getRecipients().addAll(g.getCached());
                } else {
                    msg = formatTeam(p, t, e.getMessage());
                    e.getRecipients().addAll(t.getMembers());
                }
            }
        }
        msg = msg.replaceAll("%", "%%");
        e.setFormat(msg);
    }

    private String formatMainLobby(Player p, String msg) {
        return plugin.getLang().get(p, "chat.mainLobby").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatLobby(Player p, String msg) {
        return plugin.getLang().get(p, "chat.lobby").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatTeam(Player p, Team team, String msg) {
        return plugin.getLang().get(p, "chat.team").replaceAll("<team>", team.getPrefix()).replaceAll("<color>", team.getColor() + "").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatGame(Player p, Team team, String msg) {
        return plugin.getLang().get(p, "chat.global").replaceAll("<team>", team.getPrefix()).replaceAll("<color>", team.getColor() + "").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg.replaceFirst("!", ""));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null || g.isState(State.WAITING) || g.isState(State.STARTING) || g.isState(State.FINISH) || g.isState(State.RESTARTING)) {
            e.setCancelled(true);
            return;
        }
        Squared s1 = g.getPlayerSquared(e.getBlock().getLocation());
        Squared s2 = team.getPlayerSquared(e.getBlock().getLocation());
        Block b = e.getBlock();
        Location l = b.getLocation();
        if (plugin.getCm().getBreakBypass().contains(l.getBlock().getType().name())) {
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.setBroken(ctw.getBroken() + 1);
            return;
        }
        if (s1 != null) {
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noBreak"));
            return;
        }
        if (s2 != null) {
            e.setCancelled(s2.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noBreak"));
            return;
        }
        if (!g.getPlaced().contains(l)) {
            if (!plugin.getCm().isTotalBreak()) {
                p.sendMessage(plugin.getLang().get("messages.onlyBreakPlaced"));
                e.setCancelled(true);
                return;
            }
        } else {
            g.getPlaced().remove(l);
        }
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        ctw.setBroken(ctw.getBroken() + 1);


    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g != null) {
                if (plugin.getCm().isHungerCTW()) {
                    if (g.isState(State.WAITING) || g.isState(State.STARTING) || g.isState(State.FINISH) || g.isState(State.RESTARTING)) {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null || g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Location l = e.getBlockPlaced().getLocation();
        if (team.getSpawn().getBlockY() + plugin.getCm().getLimitOfYSpawn() < l.getBlockY()) {
            e.setCancelled(true);
            e.setBuild(false);
            p.sendMessage(plugin.getLang().get("messages.limit"));
            return;
        }
        ItemStack item = p.getItemInHand();
        if (team.getWools().containsKey(l)) {
            if (item == null || item.getType().equals(Material.AIR)) return;
            String co = NBTEditor.getString(item, "TEAM", "WOOL", "CAPTURE");
            if (co == null) {
                e.setCancelled(true);
                return;
            }
            ChatColor c = ChatColor.valueOf(co);
            ChatColor to = team.getWools().get(l);
            if (!to.equals(c)) {
                e.setCancelled(true);
                p.sendMessage(plugin.getLang().get("messages.incorrectWool").replaceAll("<wool>", c + "" + c.name()));
                return;
            }
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            GamePlayer gp = g.getGamePlayer(p);
            gp.addCoins(plugin.getCm().getCoinsCapture());
            ctw.addCoins(plugin.getCm().getGCoinsCapture());
            ctw.setXp(ctw.getXp() + plugin.getCm().getXpCapture());
            ctw.addWoolCaptured();

            if (item.getType().equals(Material.WOOL)){//TODO Buscar una mejor forma de detectar esto
                if (item.getDurability() == (short) 14){
                    for (String s : plugin.getLang().getList("woolplaced.red")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.red.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.red.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 8) {
                    for (String s : plugin.getLang().getList("woolplaced.lightgray")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.lightgray.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.lightgray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 15) {
                    for (String s : plugin.getLang().getList("woolplaced.black")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.black.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.black.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 13) {
                    for (String s : plugin.getLang().getList("woolplaced.green")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.green.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.green.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 9) {
                    for (String s : plugin.getLang().getList("woolplaced.cyan")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.cyan.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.cyan.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 10) {
                    for (String s : plugin.getLang().getList("woolplaced.purple")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.purple.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.purple.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 1) {
                    for (String s : plugin.getLang().getList("woolplaced.orange")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.orange.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.orange.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 7) {
                    for (String s : plugin.getLang().getList("woolplaced.gray")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.gray.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.gray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 11) {
                    for (String s : plugin.getLang().getList("woolplaced.blue")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.blue.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.blue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 5) {
                    for (String s : plugin.getLang().getList("woolplaced.lime")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.lime.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.lime.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 3) {
                    for (String s : plugin.getLang().getList("woolplaced.lightblue")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.lightblue.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.lightblue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 6) {
                    for (String s : plugin.getLang().getList("woolplaced.pink")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.pink.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.pink.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (item.getDurability() == (short) 4) {
                    for (String s : plugin.getLang().getList("woolplaced.yellow")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.yellow.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.yellow.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else {
                    for (String s : plugin.getLang().getList("woolplaced.white")){
                        team.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    team.sendTitle(plugin.getLang().get("titlesplaced.white.title").replaceAll("<color>", c + "").replace("<player>", p.getName()), plugin.getLang().get("titlesplaced.white.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                }
            }
            team.getCaptured().add(c);

            g.getTeams().values().stream().filter(t -> t.getId() != team.getId()).forEach(t -> {
                if (item.getType().equals(Material.WOOL)){
                    if (item.getDurability() == (short) 14){
                        for (String s : plugin.getLang().getList("woolplaced.red")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.red.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.red.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 8) {
                        for (String s : plugin.getLang().getList("woolplaced.lightgray")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.lightgray.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.lightgray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 15) {
                        for (String s : plugin.getLang().getList("woolplaced.black")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.black.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.black.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 13) {
                        for (String s : plugin.getLang().getList("woolplaced.green")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.green.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.green.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 9) {
                        for (String s : plugin.getLang().getList("woolplaced.cyan")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.cyan.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.cyan.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 10) {
                        for (String s : plugin.getLang().getList("woolplaced.purple")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.purple.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.purple.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 1) {
                        for (String s : plugin.getLang().getList("woolplaced.orange")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.orange.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.orange.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 7) {
                        for (String s : plugin.getLang().getList("woolplaced.gray")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.gray.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.gray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 11) {
                        for (String s : plugin.getLang().getList("woolplaced.blue")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.blue.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.blue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 5) {
                        for (String s : plugin.getLang().getList("woolplaced.lime")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.lime.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.lime.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 3) {
                        for (String s : plugin.getLang().getList("woolplaced.lightblue")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.lightblue.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.lightblue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 6) {
                        for (String s : plugin.getLang().getList("woolplaced.pink")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.pink.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.pink.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (item.getDurability() == (short) 4) {
                        for (String s : plugin.getLang().getList("woolplaced.yellow")){
                            t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                        }
                        t.sendTitle(plugin.getLang().get("titlesplaced.yellow.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.yellow.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    }
                } else {
                    for (String s : plugin.getLang().getList("woolplaced.white")){
                        t.sendMessage(s.replaceAll("&", "§").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + ""));
                    }
                    t.sendTitle(plugin.getLang().get("titlesplaced.white.title").replaceAll("<color>", c + "").replace("<player>", p.getName()).replaceAll("<name>", team.getName()).replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titlesplaced.white.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                }
            });
            team.playSound(plugin.getCm().getCaptured(), 1.0f, 1.0f);
            NametagEdit.getApi().setSuffix(p, " " + Utils.getWoolsTag(team));
            if (team.checkWools()) {
                g.win(team);
            }
            return;
        } else {
            if (item != null) {
                String co = NBTEditor.getString(item, "TEAM", "WOOL", "CAPTURE");
                if (co != null) {
                    e.getBlockPlaced().setType(Material.WOOL);
                    removeFromProgress(p, item, team, XMaterial.matchXMaterial(item));
                }
            }
        }

        Squared s1 = g.getPlayerSquared(l);
        Squared s2 = team.getPlayerSquared(l);
        if (s1 != null) {
            e.setCancelled(s1.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noPlace"));
            return;
        }
        if (s2 != null) {
            e.setCancelled(s2.isNoBreak());
            p.sendMessage(plugin.getLang().get("messages.noPlace"));
            return;
        }
        if (e.getBlockPlaced().getType().equals(Material.HOPPER) && g.isNearby(l)) {
            e.setCancelled(true);
            p.sendMessage(plugin.getLang().get("messages.noPlaceHopper"));
            return;
        }
        if (!plugin.getCm().getBreakBypass().contains(l.getBlock().getType().name()) && !plugin.getCm().isTotalBreak()) {
            g.getPlaced().add(l);
        }
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        ctw.setPlaced(ctw.getPlaced() + 1);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        Location t = e.getTo();
        if (team == null || g.isState(State.WAITING) || g.isState(State.STARTING)) {
            if (g.getLobbyProtection() != null) {
                Squared s = g.getLobbyProtection();
                if (!s.isInCuboid(p)) {
                    p.teleport(g.getLobby());
                }
                if (t.getY() <= 10) {
                    p.teleport(g.getLobby());
                }
            }
            return;
        }
        Squared s2 = team.getPlayerSquared(e.getTo());
        if (s2 != null) {
            e.setCancelled(s2.isNoEntry());
            p.teleport(e.getFrom());
            p.setVelocity(p.getVelocity().multiply(-1));
            p.sendMessage(plugin.getLang().get("messages.noEntry"));
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.setWalked(ctw.getWalked() + 1);
        }
        if (to.getBlockY() < -15) {
            if (plugin.getCm().isInstaKillOnVoidCTW()) {
                p.damage(1000);
            }
        }
        if (g.isState(State.FINISH)) {
            if (p.getVehicle() != null) {
                Entity ent = p.getVehicle();
                if (ent == null) return;
                if (ent.getType().equals(EntityType.ENDER_DRAGON) || ent.getType().equals(EntityType.WITHER) || ent.getType().equals(EntityType.HORSE)) {
                    Vector vec = p.getLocation().getDirection();
                    ent.setVelocity(vec.multiply(0.5));
                    plugin.getVc().getReflection().moveDragon(ent, ent.getLocation().getX(), ent.getLocation().getY(), ent.getLocation().getZ(), p.getLocation().getYaw() - 180, p.getLocation().getPitch());
                }
            }
        }
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        Item i = e.getEntity();
        if (i.hasMetadata("DROPPED")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItemDrop().getItemStack();
        if (NBTEditor.contains(item, "FLAG", "PICKAXE", "DEFAULT")) {
            e.setCancelled(true);
            return;
        }
        if (plugin.getCm().getNoDrop().contains(item.getType().name())) {
            e.setCancelled(true);
            return;
        }
        if (item.getType().equals(Material.WOOL)){
            e.setCancelled(true);
            return;
        }
        if (item.equals(plugin.getIm().getPoints()) || item.equals(plugin.getIm().getLobby()) || item.equals(plugin.getIm().getLobby2()) || item.equals(plugin.getIm().getTeams()) || item.equals(plugin.getIm().getLeave()) || item.equals(plugin.getIm().getSetup())) {
            e.setCancelled(true);
            return;
        }
        String co = NBTEditor.getString(item, "TEAM", "WOOL", "CAPTURE");
        if (co == null) return;
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        Team team = g.getTeamPlayer(p);
        if (team == null) return;
        e.getItemDrop().setMetadata("DROPPED", new FixedMetadataValue(UltraCTW.get(), co));
        removeFromProgress(p, item, team, XMaterial.matchXMaterial(item));
    }

    private void removeFromProgress(Player p, ItemStack item, Team team, XMaterial xMaterial) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!p.getInventory().containsAtLeast(item, 1)) {
                ChatColor c = Utils.getColorByXMaterial(xMaterial);
                if (team.getInProgress().containsKey(c)) {
                    team.getInProgress().get(c).remove(p.getUniqueId());
                }
            }
        }, 1L);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        Item i = e.getItem();
        ChatColor c = null;
        if (i.hasMetadata("DROPPED")) {
            c = ChatColor.valueOf(i.getMetadata("DROPPED").get(0).asString());
        } else if (NBTEditor.contains(i.getItemStack(), "TEAM", "WOOL", "CAPTURE")) {
            c = ChatColor.valueOf(NBTEditor.getString(i.getItemStack(), "TEAM", "WOOL", "CAPTURE"));
        }
        if (c == null) return;
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        if (g.isState(State.FINISH) || g.isState(State.RESTARTING)) {
            e.setCancelled(true);
            return;
        }
        Team team = g.getTeamPlayer(p);
        if (team == null) return;
        if (!team.getColors().contains(c)) {
            e.setCancelled(true);
            p.sendMessage(plugin.getLang().get("messages.noYourWool"));
            e.getItem().remove();
            return;
        }
        if (team.getCaptured().contains(c)) {
            return;
        }
        ArrayList<Team> others = g.getTeams().values().stream().filter(t -> t.getId() != team.getId()).collect(Collectors.toCollection(ArrayList::new));
        team.getDropped().remove(c);
        team.getInProgress().putIfAbsent(c, new ArrayList<>());

        if (!team.getInProgress().get(c).contains(p.getUniqueId())) {
            team.getInProgress().get(c).add(p.getUniqueId());
            if (e.getItem().getItemStack().getType() == Material.WOOL){
                if (i.getItemStack().getDurability()  == (short) 14){
                    team.sendMessage(plugin.getLang().get("teampickup.red").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.red.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.red.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 8) {
                    team.sendMessage(plugin.getLang().get("teampickup.lightgray").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.lightgray.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.lightgray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 15) {
                    team.sendMessage(plugin.getLang().get("teampickup.black").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.black.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.black.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 13) {
                    team.sendMessage(plugin.getLang().get("teampickup.green").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.green.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.green.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 9) {
                    team.sendMessage(plugin.getLang().get("teampickup.cyan").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.cyan.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.cyan.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 10) {
                    team.sendMessage(plugin.getLang().get("teampickup.purple").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.purple.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.purple.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 1) {
                    team.sendMessage(plugin.getLang().get("teampickup.orange").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.orange.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.orange.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 7) {
                    team.sendMessage(plugin.getLang().get("teampickup.gray").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.gray.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.gray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 11) {
                    team.sendMessage(plugin.getLang().get("teampickup.blue").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.blue.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.blue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 5) {
                    team.sendMessage(plugin.getLang().get("teampickup.lime").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.lime.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.lime.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 3) {
                    team.sendMessage(plugin.getLang().get("teampickup.lightblue").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.lightblue.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.lightblue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 6) {
                    team.sendMessage(plugin.getLang().get("teampickup.pink").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.pink.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.pink.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else if (i.getItemStack().getDurability()  == (short) 4) {
                    team.sendMessage(plugin.getLang().get("teampickup.yellow").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.yellow.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.yellow.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                } else {
                    team.sendMessage(plugin.getLang().get("teampickup.white").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + ""));
                    team.sendTitle(plugin.getLang().get("titlespickup.white.title").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()),plugin.getLang().get("titlespickup.white.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", c + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                }
            }
            team.playSound(plugin.getCm().getPickUpTeam(), 1.0f, 1.0f);
            NametagEdit.getApi().setSuffix(p, " " + Utils.getWoolsTag(team));

            ItemStack item = new ItemStack(322, 16);
            ItemStack chestplate = new ItemStack(311, 1);

            if (p.getInventory().contains(chestplate) || p.getInventory().getChestplate().equals(chestplate)){
                p.getInventory().addItem(item);
            } else {
                p.getInventory().setChestplate(chestplate);
                p.getInventory().addItem(item);
                p.sendMessage(plugin.getLang().get("messages.equipement"));
            }

            ChatColor finalC = c;

            g.getTeams().values().stream().filter(t -> t.getId() != team.getId()).forEach(t -> {
                if (e.getItem().getItemStack().getType() == Material.WOOL){
                    if (i.getItemStack().getDurability()  == (short) 14){
                        t.sendMessage(plugin.getLang().get("otherpickup.red").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.red.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.red.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 8) {
                        t.sendMessage(plugin.getLang().get("otherpickup.lightgray").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.lightgray.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.lightgray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 15) {
                        t.sendMessage(plugin.getLang().get("otherpickup.black").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.black.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.black.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 13) {
                        t.sendMessage(plugin.getLang().get("otherpickup.green").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.green.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.green.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 9) {
                        t.sendMessage(plugin.getLang().get("otherpickup.cyan").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.cyan.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.cyan.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 10) {
                        t.sendMessage(plugin.getLang().get("otherpickup.purple").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.purple.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.purple.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 1) {
                        t.sendMessage(plugin.getLang().get("otherpickup.orange").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.orange.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.orange.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 7) {
                        t.sendMessage(plugin.getLang().get("otherpickup.gray").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.gray.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.gray.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 11) {
                        t.sendMessage(plugin.getLang().get("otherpickup.blue").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.blue.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.blue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 5) {
                        t.sendMessage(plugin.getLang().get("otherpickup.lime").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.lime.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.lime.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 3) {
                        t.sendMessage(plugin.getLang().get("otherpickup.lightblue").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.lightblue.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.lightblue.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 6) {
                        t.sendMessage(plugin.getLang().get("otherpickup.pink").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.pink.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.pink.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else if (i.getItemStack().getDurability()  == (short) 4) {
                        t.sendMessage(plugin.getLang().get("otherpickup.yellow").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.yellow.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.yellow.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    } else {
                        t.sendMessage(plugin.getLang().get("otherpickup.white").replaceAll("<player>", p.getDisplayName()).replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + ""));
                        t.sendTitle(plugin.getLang().get("titlesotherpickup.white.title").replaceAll("<color>", finalC + "").replace("<player>", p.getName()),plugin.getLang().get("titlesotherpickup.white.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<color>", finalC + "").replace("<player>", p.getDisplayName()), 0, 30, 10);
                    }
                }
            });
            others.forEach(t -> t.playSound(plugin.getCm().getPickUpOthers(), 1.0f, 1.0f));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g != null) {
                Team team = g.getTeamPlayer(p);
                if (team == null || g.isState(State.WAITING) || g.isState(State.STARTING)) {
                    e.setCancelled(true);
                    return;
                }
                return;
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player d = (Player) e.getDamager();
                if (checkDamage(e, p, d)) return;
            }
            if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
                Player d = (Player) ((Projectile) e.getDamager()).getShooter();
                if (checkDamage(e, p, d));
            }
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Arrow)) {
            return;
        }
        if(!(((Arrow) e.getDamager()).getShooter() instanceof Player)) {
            return;
        }
        Player attacker = (Player) ((Arrow) e.getDamager()).getShooter();
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player victim = (Player) e.getEntity();
        Game g = plugin.getGm().getGameByPlayer(victim);
        Team team = g.getTeamPlayer(victim);
        if(attacker == victim) {
            e.setCancelled(true);
            return;
        }
        if(victim.getHealth() - e.getDamage() < 0) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run(){
                double health = Math.round(victim.getHealth() * 10.0) / 10.0;
                if (health != 20.0) {
                    attacker.sendMessage(plugin.getLang().get("messages.arrowdamage").replaceAll("<color>", team.getColor() + "").replaceAll("<victim>", victim.getDisplayName()).replaceAll("<health>", String.valueOf(health)));
                }
            }
        }.runTaskLater(plugin, 2L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g != null && g.isState(State.GAME)){
            if(e.getInventory().getType() == InventoryType.CHEST || e.getInventory().getType() == InventoryType.FURNACE) {
                if (e.getCurrentItem().getType().equals(Material.WOOL) && p.getGameMode().equals(GameMode.SURVIVAL)){
                    e.getWhoClicked().sendMessage(plugin.getLang().get("messages.noSave"));
                    e.setCancelled(true);
                }
            }
        }
    }


    private boolean checkDamage(EntityDamageByEntityEvent e, Player p, Player d) {
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return true;
        Team tp = g.getTeamPlayer(p);
        Team td = g.getTeamPlayer(d);
        if (tp == null || td == null) {
            e.setCancelled(true);
            return true;
        }
        if (tp.equals(td)) {
            e.setCancelled(true);
            return true;
        }
        double damage = e.getFinalDamage();
        plugin.getTgm().setTag(d, p, damage);
        return false;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player d = p.getKiller();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        if (plugin.getCm().isDCMDEnabled()) {
            plugin.getCm().getDeathCommands().forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("<player>", p.getName())));
        }
        e.getDrops().clear();
        e.setDroppedExp(0);
        e.setDeathMessage(null);
        if (d != null) {
            CTWPlayer sk = plugin.getDb().getCTWPlayer(d);
            if (sk != null) {
                if (plugin.getCm().isKCMDEnabled()) {
                    plugin.getCm().getKillCommands().forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("<player>", d.getName())));
                }
                if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() == null) {
                    EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CONTACT;
                    plugin.getTm().execute(p, cause, g, sk.getTaunt());
                } else {
                    EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
                    plugin.getTm().execute(p, cause, g, sk.getTaunt());
                }
                plugin.getKem().execute(g, d, p, p.getLocation(), sk.getKillEffect());
                plugin.getKsm().execute(d, p, sk.getKillSound());
            } else {
                executeTauntDefault(p, g);
            }
        } else {
            executeTauntDefault(p, g);
        }

        plugin.getTgm().executeRewards(p, p.getMaxHealth());

        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
                Team tp = g.getTeamPlayer(p);
                g.addDeath(p);
                respawn(tp, g, p);
            }
        }.runTaskLater(plugin, 3L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Game g = plugin.getGm().getGameByPlayer(p);
        if (g == null) return;
        e.setRespawnLocation(g.getSpectator());
    }

    private void executeTauntDefault(Player p, Game g) {
        if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() == null) {
            EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CONTACT;
            plugin.getTm().execute(p, cause, g, 0);
        } else {
            EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
            plugin.getTm().execute(p, cause, g, 0);
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g == null) {
                return;
            }
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            if (ctw == null) return;
            ctw.setShots(ctw.getShots() + 1);
            Projectile proj = e.getEntity();
            Trail trail = plugin.getTlm().getTrails().get(ctw.getTrail());
            if (trail == null) {
                return;
            }
            plugin.getTlm().spawnTrail(proj, trail);
        }
    }

    @EventHandler
    public void onHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (!plugin.getTgm().hasTag(p)) return;
            Tagged tag = plugin.getTgm().getTagged(p);
            tag.removeDamage(e.getAmount());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.PHYSICAL)) {
            return;
        }
        Game game = plugin.getGm().getGameByPlayer(p);
        if (game != null && game.isState(State.FINISH)) {
            if (p.getVehicle() != null) {
                Entity ent = p.getVehicle();
                if (ent == null) return;
                if (ent.getType().equals(EntityType.ENDER_DRAGON)) {
                    p.launchProjectile(Fireball.class, p.getEyeLocation().getDirection());
                }
                if (ent.getType().equals(EntityType.WITHER)) {
                    p.launchProjectile(WitherSkull.class, p.getEyeLocation().getDirection());
                }
                if (ent.getType().equals(EntityType.HORSE)) {
                    p.launchProjectile(Snowball.class, p.getEyeLocation().getDirection());
                }
            }
        }
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
            return;
        }
        ItemStack item = p.getItemInHand();
        if ((item.getType().equals(Material.LAVA_BUCKET) || item.getType().equals(Material.WATER_BUCKET))) {
            if (game != null) {
                Squared s = game.getPlayerSquared(p);
                if (s != null && s.isNoBreak()) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (item.equals(plugin.getIm().getTeams())) {
            if (game != null) {
                plugin.getGem().createTeamsMenu(p, game);
            }
        }
        if (item.equals(plugin.getIm().getLobby())) {
            p.chat(plugin.getCm().getItemLobbyCMD());
        }
		if (item.equals(plugin.getIm().getLobby2())) {
            p.chat(plugin.getCm().getItemLobby2CMD());
        }
        if (item.equals(plugin.getIm().getLeave())) {
            plugin.getGm().removePlayerGame(p, true);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        if (to.getWorld().getName().equals(from.getWorld().getName())) {
            return;
        }
        Player p = e.getPlayer();
        from.getWorld().getPlayers().forEach(pl -> pl.hidePlayer(p));
        from.getWorld().getPlayers().forEach(p::hidePlayer);
        for (Player pl : to.getWorld().getPlayers()) {
            pl.showPlayer(p);
        }
        for (Player pl : to.getWorld().getPlayers()) {
            p.showPlayer(pl);
        }
    }

    @EventHandler
    public void onCMD(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        Game game = plugin.getGm().getGameByPlayer(p);
        if (game == null) {
            return;
        }
        if (p.hasPermission("ultractw.mod")) {
            return;
        }
        for (String cmd : plugin.getCm().getWhitelistedCMD()) {
            if (e.getMessage().startsWith(cmd)) {
                return;
            }
        }
        e.setCancelled(true);
        p.sendMessage(plugin.getLang().get(p, "messages.noIngame"));
    }

    private boolean check(Player p1, Player p2) {
        return !p1.getWorld().getName().equals(p2.getWorld().getName());
    }

    private void respawn(Team team, Game g, Player p) {
        p.closeInventory();
        p.getInventory().clear();
        p.setNoDamageTicks(40);
        p.setFallDistance(0);
        p.setLevel(0);
        p.setExp(0);
        p.setHealth(p.getMaxHealth());
        p.teleport(team.getSpawn());
        p.setFoodLevel(20);
        plugin.getKm().giveDefaultKit(p, g, team);
        NametagEdit.getApi().setNametag(p, team.getPrefix() + " " + team.getColor() + "", "");
        for (ChatColor c : team.getColors()) {
            if (team.getInProgress().get(c).isEmpty()) continue;
            team.getInProgress().get(c).remove(p.getUniqueId());
            if (team.getInProgress().get(c).isEmpty()) {
                g.sendGameMessage(plugin.getLang().get("messages.lost").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + "").replaceAll("<wool>", c + "⬛"));
                team.sendTitle(plugin.getLang().get("titles.dropped.title"), plugin.getLang().get("titles.dropped.subtitle").replaceAll("<tcolor>", team.getColor() + "").replaceAll("<player>", p.getDisplayName()).replaceAll("<color>", c + "").replaceAll("<wool>", c + "⬛"), 0, 40, 0);
            }
        }
        p.setFireTicks(0);
        for (PotionEffect ef : p.getActivePotionEffects()) {
            p.removePotionEffect(ef.getType());
        }
        p.updateInventory();
    }

    private void givePlayerItems(Player p) {
        if (plugin.getCm().isItemLobbyEnabled()) {
            p.getInventory().setItem(plugin.getCm().getItemLobbySlot(), plugin.getIm().getLobby());
        }
        if (plugin.getCm().isItemLobby2Enabled()) {
            p.getInventory().setItem(plugin.getCm().getItemLobby2Slot(), plugin.getIm().getLobby2());
        }
    }
}