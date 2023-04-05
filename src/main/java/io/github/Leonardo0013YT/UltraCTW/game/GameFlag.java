package io.github.Leonardo0013YT.UltraCTW.game;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.Kit;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.KillEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinDance;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.objects.MineCountdown;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Getter
public class GameFlag {

    private final UltraCTW plugin;
    private int starting, time = 0, nowEvent = 0;
    private final int id, teamSize, min, max, pool;
    private final HashMap<Integer, GameEvent> events = new HashMap<>();
    private final ArrayList<Player> cached = new ArrayList<>(), players = new ArrayList<>(), spectators = new ArrayList<>();
    private final String name, schematic;
    private final Location lobby, spectator;
    private final ArrayList<Location> npcUpgrades = new ArrayList<>(), npcBuff = new ArrayList<>(), npcShop = new ArrayList<>(), npcKits = new ArrayList<>();
    private final ArrayList<WinEffect> winEffects = new ArrayList<>();
    private final ArrayList<WinDance> winDances = new ArrayList<>();
    private final ArrayList<KillEffect> killEffects = new ArrayList<>();
    private final HashMap<ChatColor, FlagTeam> teams = new HashMap<>();
    private final HashMap<Integer, ChatColor> teamsID = new HashMap<>();
    private final HashMap<Player, GamePlayer> gamePlayer = new HashMap<>();
    private final HashMap<Location, Material> mines = new HashMap<>();
    private final HashMap<Location, MineCountdown> countdowns = new HashMap<>();
    private Squared lobbyProtection;
    private final ArrayList<Location> placed = new ArrayList<>();
    private State state;

    public GameFlag(UltraCTW plugin, String path, int id) {
        this.plugin = plugin;
        this.id = id;
        this.name = plugin.getArenas().get(path + ".name");
        plugin.getWc().createEmptyWorld(name);
        this.schematic = plugin.getArenas().get(path + ".schematic");
        this.lobby = Utils.getStringLocation(plugin.getArenas().get(path + ".lobby"));
        plugin.getWc().resetMap(new Location(lobby.getWorld(), 0, 75, 0), schematic);
        this.spectator = Utils.getStringLocation(plugin.getArenas().get(path + ".spectator"));
        this.teamSize = plugin.getArenas().getInt(path + ".teamSize");
        for (String s : plugin.getArenas().getListOrDefault(path + ".npcUpgrade", new ArrayList<>())) {
            npcUpgrades.add(Utils.getStringLocation(s));
        }
        for (String s : plugin.getArenas().getListOrDefault(path + ".npcBuff", new ArrayList<>())) {
            npcBuff.add(Utils.getStringLocation(s));
        }
        for (String s : plugin.getArenas().getListOrDefault(path + ".npcShop", new ArrayList<>())) {
            npcShop.add(Utils.getStringLocation(s));
        }
        for (String s : plugin.getArenas().getListOrDefault(path + ".npcKits", new ArrayList<>())) {
            npcKits.add(Utils.getStringLocation(s));
        }
        if (plugin.getArenas().isSet(path + ".mines")) {
            for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".mines").getKeys(false)) {
                Location loc = Utils.getStringLocation(plugin.getArenas().get(path + ".mines." + c + ".loc"));
                Material material = Material.valueOf(plugin.getArenas().get(path + ".mines." + c + ".material"));
                mines.put(loc, material);
            }
        }
        for (String c : plugin.getArenas().getConfig().getConfigurationSection(path + ".teams").getKeys(false)) {
            int tid = teams.size();
            ChatColor color = ChatColor.valueOf(c);
            teams.put(color, new FlagTeam(plugin, this, path + ".teams." + c, tid));
            teamsID.put(tid, color);
        }
        for (String s : plugin.getConfig().getConfigurationSection("flagDefaults.phases").getKeys(false)) {
            events.put(events.size(), new GameEvent(plugin, "flagDefaults.phases." + s));
        }
        if (plugin.getArenas().isSet(path + ".lobbyProtection.min")) {
            this.lobbyProtection = new Squared(Utils.getStringLocation(plugin.getArenas().get(path + ".lobbyProtection.max")), Utils.getStringLocation(plugin.getArenas().get(path + ".lobbyProtection.min")), false, true);
        }
        this.starting = plugin.getCm().getStarting();
        this.min = plugin.getArenas().getInt(path + ".min");
        this.max = teamSize * teams.size();
        this.pool = plugin.getArenas().getIntOrDefault(path + ".pool", 10);
        setState(State.WAITING);
        lobby.getWorld().getEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(Entity::remove);
    }

    public void addPlayer(Player p) {
        gamePlayer.put(p, new GamePlayer(p));
        Utils.setCleanPlayer(p);
        cached.add(p);
        players.add(p);
        p.teleport(lobby);
        givePlayerItems(p);
        Utils.updateSB(p);
        checkStart();
    }

    public void removePlayer(Player p) {
        Utils.setCleanPlayer(p);
        removePlayerAllTeam(p);
        cached.remove(p);
        players.remove(p);
        spectators.remove(p);
        if (gamePlayer.containsKey(p)) {
            GamePlayer gp = gamePlayer.get(p);
            gp.reset();
            gamePlayer.remove(p);
        }
        plugin.getTgm().removeTag(p);
        checkCancel();
        checkWin();
    }

    public void checkCancel() {
        if (isState(State.STARTING)) {
            if (min > players.size()) {
                cancel();
            }
        }
    }

    public void cancel() {
        this.starting = plugin.getCm().getStarting();
        setState(State.WAITING);
        sendGameMessage(plugin.getLang().get(null, "messages.cancelStart"));
        sendGameTitle(plugin.getLang().get(null, "titles.cancel.title"), plugin.getLang().get(null, "titles.cancel.subtitle"), 0, 40, 0);
        sendGameSound(plugin.getCm().getCancelStartSound());
    }

    public void checkStart() {
        if (isState(State.WAITING)) {
            if (players.size() >= min) {
                setState(State.STARTING);
            }
        }
    }

    public void reset() {
        winDances.forEach(WinDance::stop);
        winEffects.forEach(WinEffect::stop);
        killEffects.forEach(KillEffect::stop);
        gamePlayer.clear();
        placed.clear();
        spectators.clear();
        cached.clear();
        players.clear();
        teams.values().forEach(FlagTeam::reset);
        events.values().forEach(GameEvent::reset);
        plugin.getWc().resetMap(new Location(lobby.getWorld(), 0, 75, 0), schematic);
        World w = lobby.getWorld();
        lobbyProtection.getMax().setWorld(w);
        lobbyProtection.getMin().setWorld(w);
        w.setTime(500);
        w.getEntities().stream().filter(e -> !e.getType().equals(EntityType.PLAYER)).forEach(Entity::remove);
        teams.values().forEach(t -> t.updateWorld(w));
        spectator.setWorld(w);
        for (Location l : npcBuff) {
            l.setWorld(w);
        }
        for (Location l : npcShop) {
            l.setWorld(w);
        }
        for (Location l : npcUpgrades) {
            l.setWorld(w);
        }
        for (Location l : npcKits) {
            l.setWorld(w);
        }
        starting = plugin.getCm().getStarting();
        setState(State.WAITING);
        time = 0;
        nowEvent = 0;
    }

    public void update() {
        Utils.updateSB(this);
        if (isState(State.STARTING)) {
            if (starting == 30 || starting == 15 || starting == 10 || starting == 5 || starting == 4 || starting == 3 || starting == 2 || starting == 1) {
                sendGameTitle(plugin.getLang().get("titles.starting.title").replaceAll("<time>", String.valueOf(starting)), plugin.getLang().get("titles.starting.subtitle").replaceAll("<time>", String.valueOf(starting)), 0, 40, 0);
                sendGameMessage(plugin.getLang().get("messages.starting").replaceAll("<starting>", String.valueOf(starting)).replaceAll("<s>", (starting > 1) ? "s" : ""));
                sendGameSound(XSound.BLOCK_NOTE_BLOCK_PLING.parseSound());
            }
            if (starting == 29 || starting == 14 || starting == 9 || starting == 0) {
                sendGameTitle("", "", 0, 1, 0);
            }
            if (starting == 0) {
                setState(State.GAME);
                for (String s : plugin.getLang().getList("messages.startFlag")) {
                    sendGameMessage(s);
                }
                for (Player on : players) {
                    Utils.setCleanPlayer(on);
                    if (getTeamPlayer(on) == null) {
                        joinRandomTeam(on);
                    }
                    FlagTeam ft = getTeamPlayer(on);
                    CTWPlayer ctw = plugin.getDb().getCTWPlayer(on);
                    Kit kit = plugin.getKm().getKits().get(ctw.getKit());
                    if (kit != null) {
                        KitLevel kitLevel = kit.getLevels().get(ctw.getKitLevel());
                        if (kitLevel != null) {
                            kitLevel.giveKitLevel(on, ft);
                        }
                    }
                    on.getInventory().addItem(plugin.getIm().getPickaxe());
                    on.teleport(ft.getSpawn());
                    NametagEdit.getApi().setNametag(on, ft.getColor() + "", "");
                }
                for (FlagTeam ft : teams.values()) {
                    ft.fillLifes();
                }
            }
            starting--;
        }
        if (isState(State.GAME)) {
            time++;
            for (GameEvent e : events.values()) {
                if (e.getTime() < 0) continue;
                e.reduce();
                if (e.getTime() == 0) {
                    e.start(this);
                    nowEvent++;
                }
            }
            countdowns.values().forEach(MineCountdown::reduce);
            Iterator<Location> cools = countdowns.keySet().iterator();
            while (cools.hasNext()) {
                Location l = cools.next();
                MineCountdown mc = countdowns.get(l);
            }
        }
    }

    public void checkWin() {
        if (isState(State.GAME)) {
            int al = getTeamAlive();
            if (al == 1) {
                FlagTeam t = getLastTeam();
                win(t);
            } else if (al == 0) {
                reset();
            }
        }
    }

    public void win(FlagTeam team) {
        if (plugin.isStop()) return;
        setState(State.FINISH);
        GameWinFlag gw = new GameWinFlag(this);
        gw.setTeamWin(team);
        List<String> top = gw.getTop();
        String[] s1 = top.get(0).split(":");
        String[] s2 = top.get(1).split(":");
        String[] s3 = top.get(2).split(":");
        for (Player on : cached) {
            if (!team.getMembers().contains(on)) {
                plugin.getVc().getReflection().sendTitle(plugin.getLang().get("titles.lose.title"), plugin.getLang().get("titles.lose.subtitle"), 0, 40, 0, on);
                continue;
            }
            for (String s : plugin.getLang().getList("messages.winFlag")) {
                on.sendMessage(s.replaceAll("&", "§").replaceAll("<winner>", gw.getWinner()).replaceAll("<number1>", s1[1]).replaceAll("<top1>", s1[0]).replaceAll("<color1>", "" + ChatColor.valueOf(s1[2])).replaceAll("<number2>", s2[1]).replaceAll("<top2>", s2[0]).replaceAll("<color2>", "" + ChatColor.valueOf(s2[2])).replaceAll("<number3>", s3[1]).replaceAll("<top3>", s3[0]).replaceAll("<color3>", "" + ChatColor.valueOf(s3[2])));
            }
        }
        for (Player w : team.getMembers()) {
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(w);
            if (ctw == null) continue;
            if (plugin.getCm().isWCMDEnabled()) {
                plugin.getCm().getWinCommands().forEach(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("<player>", w.getName())));
            }
            ctw.addCoins(plugin.getCm().getGCoinsWins());
            ctw.setXp(ctw.getXp() + plugin.getCm().getXpWin());
            ctw.setWins(ctw.getWins() + 1);
            plugin.getLvl().checkUpgrade(w);
            plugin.getWem().execute(this, w, ctw.getWinEffect());
            plugin.getWdm().execute(this, w, ctw.getWinDance());
        }
        plugin.getVc().getReflection().sendTitle(plugin.getLang().get("titles.win.title").replaceAll("<color>", team.getColor() + ""), plugin.getLang().get("titles.win.subtitle"), 0, 40, 0, team.getMembers());
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> back = new ArrayList<>(cached);
                for (Player on : back) {
                    plugin.getGm().removePlayerGame(on, true);
                }
                reset();
            }
        }.runTaskLater(plugin, 20 * 15);
    }

    public void addPlayerRandomTeam(Player p) {
        FlagTeam t = Utils.getMinorPlayersTeam(this);
        addPlayerTeam(p, t);
        p.sendMessage(plugin.getLang().get("messages.randomTeam").replaceAll("<team>", t.getName()));
    }

    public void addPlayerTeam(Player p, FlagTeam team) {
        removePlayerAllTeam(p);
        team.addMember(p);
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        for (Location s : npcUpgrades) {
            plugin.getSkm().spawnShopKeeper(p, s, ctw.getShopKeeper(), NPCType.UPGRADES);
        }
        for (Location s : npcBuff) {
            plugin.getSkm().spawnShopKeeper(p, s, ctw.getShopKeeper(), NPCType.BUFF);
        }
        for (Location k : npcKits) {
            plugin.getSkm().spawnShopKeeper(p, k, ctw.getShopKeeper(), NPCType.KITS);
        }
        for (Location s : npcShop) {
            plugin.getSkm().spawnShopKeeper(p, s, ctw.getShopKeeper(), NPCType.SHOP);
        }
    }

    public void addKill(Player p, boolean bowKill) {
        if (gamePlayer.containsKey(p)) {
            GamePlayer gp = gamePlayer.get(p);
            gp.setKills(gp.getKills() + 1);
            gp.addCoins(plugin.getCm().getCoinsKill());
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.addCoins(plugin.getCm().getGCoinsKills());
            ctw.setXp(ctw.getXp() + plugin.getCm().getXpKill());
            if (bowKill) {
                ctw.setBowKills(ctw.getBowKills() + 1);
            } else {
                ctw.setKills(ctw.getKills() + 1);
            }
            plugin.getLvl().checkUpgrade(p);
        }
    }

    public GameEvent getNowEvent() {
        if (events.containsKey(nowEvent)) {
            return events.get(nowEvent);
        }
        return null;
    }

    public GameEvent getLastEvent() {
        if (events.containsKey(nowEvent - 1)) {
            return events.get(nowEvent - 1);
        }
        return null;
    }

    public GamePlayer getGamePlayer(Player p) {
        return gamePlayer.get(p);
    }

    public boolean isGracePeriod() {
        return plugin.getCm().getGracePeriod() - time > 0;
    }

    public void removePlayerTeam(Player p, FlagTeam team) {
        team.removeMember(p);
    }

    public void joinRandomTeam(Player p) {
        for (FlagTeam team : teams.values()) {
            if (team.getTeamSize() < teamSize) {
                addPlayerTeam(p, team);
                break;
            }
        }
    }

    public void removePlayerAllTeam(Player p) {
        plugin.getNpc().removePlayer(p);
        for (FlagTeam team : teams.values()) {
            if (team.getMembers().contains(p)) {
                removePlayerTeam(p, team);
            }
        }
    }

    public FlagTeam getLastTeam() {
        for (FlagTeam team : teams.values()) {
            if (team.getLifes() <= 0) continue;
            if (team.getTeamSize() > 0) {
                return team;
            }
        }
        return null;
    }

    public int getTeamAlive() {
        int c = 0;
        for (FlagTeam team : teams.values()) {
            if (team.getLifes() <= 0) continue;
            if (team.getTeamSize() > 0) {
                c++;
            }
        }
        return c;
    }

    public void addWinEffects(WinEffect e) {
        winEffects.add(e);
    }

    public void addWinDance(WinDance e) {
        winDances.add(e);
    }

    public void addKillEffects(KillEffect e) {
        killEffects.add(e);
    }

    public FlagTeam getTeamByColor(ChatColor color) {
        return teams.get(color);
    }

    public FlagTeam getTeamByLoc(Location loc) {
        for (FlagTeam team : teams.values()) {
            if (team.isFlag(loc)) {
                return team;
            }
        }
        return null;
    }

    public FlagTeam getTeamPlayer(Player p) {
        for (FlagTeam team : teams.values()) {
            if (team.getMembers().contains(p)) {
                return team;
            }
        }
        return null;
    }

    public void sendGameMessage(String msg) {
        for (Player p : cached) {
            p.sendMessage(msg);
        }
    }

    public void sendGameTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        plugin.getVc().getReflection().sendTitle(title, subtitle, fadeIn, stay, fadeOut, cached);
    }

    public void sendGameSound(Sound sound) {
        for (Player p : cached) {
            p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    public boolean isState(State state) {
        return this.state.equals(state);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void givePlayerItems(Player p) {
        p.getInventory().setItem(4, plugin.getIm().getTeams());
        p.getInventory().setItem(8, plugin.getIm().getLeave());
    }
}