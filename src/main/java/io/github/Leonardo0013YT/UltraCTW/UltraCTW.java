package io.github.Leonardo0013YT.UltraCTW;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.Leonardo0013YT.UltraCTW.adapters.ICTWPlayerAdapter;
import io.github.Leonardo0013YT.UltraCTW.cmds.*;
import io.github.Leonardo0013YT.UltraCTW.config.Settings;
import io.github.Leonardo0013YT.UltraCTW.controllers.VersionController;
import io.github.Leonardo0013YT.UltraCTW.controllers.WorldController;
import io.github.Leonardo0013YT.UltraCTW.database.MySQLDatabase;
import io.github.Leonardo0013YT.UltraCTW.fanciful.FancyMessage;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.IDatabase;
import io.github.Leonardo0013YT.UltraCTW.listeners.*;
import io.github.Leonardo0013YT.UltraCTW.managers.*;
import io.github.Leonardo0013YT.UltraCTW.menus.GameMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.SetupMenu;
import io.github.Leonardo0013YT.UltraCTW.menus.UltraInventoryMenu;
import io.github.Leonardo0013YT.UltraCTW.objects.ProtocolLib;
import io.github.Leonardo0013YT.UltraCTW.placeholders.Placeholders;
import io.github.Leonardo0013YT.UltraCTW.utils.MetricsLite;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class UltraCTW extends JavaPlugin {

    private static UltraCTW instance;
    private Gson ctw;
    private Settings arenas, lang, menus, kits, sources, windance, wineffect, killsound, taunt, trail, killeffect, shopkeepers, levels, shop;
    private boolean debugMode, stop = false;
    private GameManager gm;
    private ConfigManager cm;
    private AddonManager adm;
    private SetupManager sm;
    private SetupMenu sem;
    private ItemManager im;
    private KitManager km;
    private ScoreboardManager sb;
    private WorldController wc;
    private UltraInventoryMenu uim;
    private GameMenu gem;
    private IDatabase db;
    private VersionController vc;
    private WinDancesManager wdm;
    private WinEffectsManager wem;
    private TrailsManager tlm;
    private TauntsManager tm;
    private KillSoundManager ksm;
    private KillEffectsManager kem;
    private ShopKeepersManager skm;
    private TaggedManager tgm;
    private LevelManager lvl;
    private ShopManager shm;
    private NPCManager npc;
    private InjectionManager ijm;
    private StreakManager stm;
    private TopManager top;
    private MultiplierManager mm;

    public static UltraCTW get() {
        return instance;
    }



    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        vc = new VersionController(this);
        setupSounds();
        saveConfig();
        ctw = new GsonBuilder().registerTypeAdapter(CTWPlayer.class, new ICTWPlayerAdapter()).create();
        arenas = new Settings(this, "arenas", false, false);
        lang = new Settings(this, "lang", true, false);
        sources = new Settings(this, "sources", true, false);
        menus = new Settings(this, "menus", false, false);
        kits = new Settings(this, "kits", false, false);
        windance = new Settings(this, "windance", false, false);
        wineffect = new Settings(this, "wineffect", false, false);
        killsound = new Settings(this, "killsounds", false, false);
        taunt = new Settings(this, "taunts", false, false);
        trail = new Settings(this, "trails", false, false);
        killeffect = new Settings(this, "killeffect", false, false);
        shopkeepers = new Settings(this, "shopkeepers", false, false);
        levels = new Settings(this, "levels", false, false);
        shop = new Settings(this, "shop", false, false);
        debugMode = getConfig().getBoolean("debugMode");

        ijm = new InjectionManager(this);
        ijm.loadWEInjection();
        wc = new WorldController(this);
        db = new MySQLDatabase(this);
        cm = new ConfigManager(this);
        if (getCm().isBungeeModeEnabled()) {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            sendLogMessage("§eLoading options for BungeeMode...");
        }
        adm = new AddonManager(this);
        im = new ItemManager(this);
        sm = new SetupManager(this);
        sem = new SetupMenu(this);
        km = new KitManager(this);
        km.loadKits();
        gm = new GameManager(this);
        uim = new UltraInventoryMenu(this);
        sb = new ScoreboardManager(this);
        gem = new GameMenu(this);
        wdm = new WinDancesManager(this);
        wdm.loadWinDances();
        wem = new WinEffectsManager(this);
        wem.loadWinEffects();
        tlm = new TrailsManager(this);
        tlm.loadTrails();
        tm = new TauntsManager(this);
        tm.loadTaunts();
        ksm = new KillSoundManager(this);
        ksm.loadKillSounds();
        kem = new KillEffectsManager(this);
        kem.loadKillEffects();
        tgm = new TaggedManager(this);
        shm = new ShopManager(this);
        skm = new ShopKeepersManager(this);
        skm.loadShopKeepers();
        lvl = new LevelManager(this);
        npc = new NPCManager(this);
        ijm.loadInjections();
        stm = new StreakManager(this);
        top = new TopManager(this);
        mm = new MultiplierManager();
        new ProtocolLib(this);
        getCommand("ctws").setExecutor(new SetupCMD(this));
        getCommand("ctw").setExecutor(new CTWCMD(this));
        getCommand("leave").setExecutor(new LeaveCommand(this));
        getCommand("join").setExecutor(new JoinCommand(this));
        if (cm.isStatsCMD()) {
            getCommand("stats").setExecutor(new StatsCMD(this));
        }
        getServer().getPluginManager().registerEvents(new SetupListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        if (getCm().isBungeeModeEnabled()){
            sendLogMessage("§eLoading Listeners for BungeeMode...");
            getServer().getPluginManager().registerEvents(new MotdListener(this), this);
        }
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                getGm().getGames().values().forEach(Game::update);
            }
        }.runTaskTimer(this, 20, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                Utils.updateSB();
            }
        }.runTaskTimer(this, 100, 100);
        new MetricsLite(this, 9621);
    }

    @Override
    public void onDisable() {
        stop = true;
        if (db != null) {
            for (Player on : Bukkit.getOnlinePlayers()) {
                if (gm.isPlayerInGame(on)) {
                    gm.removePlayerGame(on, false);
                }
                db.savePlayer(on.getUniqueId(), true);
            }
            db.close();
        }
    }

    public void sendToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void broadcastGame(Game game) {
        if (!getCm().isBroadcastGame()) return;
        FancyMessage fm = new FancyMessage(getLang().get(null, "fancy.message").replaceAll("<game>", game.getName()))
                .addMsg(getLang().get(null, "fancy.click"))
                .setClick(ClickEvent.Action.RUN_COMMAND, "/ctw join " + game.getName())
                .setHover(HoverEvent.Action.SHOW_TEXT, getLang().get(null, "fancy.hover")).build();
        for (Player on : Bukkit.getOnlinePlayers()) {
            if (getGm().isPlayerInGame(on)) {
                continue;
            }
            fm.send(on);
        }
    }

    public void reload() {
        reloadConfig();
        lang.reload();
        arenas.reload();
        shop.reload();
        killeffect.reload();
        killsound.reload();
        kits.reload();
        levels.reload();
        menus.reload();
        shopkeepers.reload();
        sources.reload();
        taunt.reload();
        trail.reload();
        windance.reload();
        wineffect.reload();
        cm.reload();
        adm.reload();
        gm.reload();
        kem.loadKillEffects();
        km.loadKits();
        ksm.loadKillSounds();
        lvl.reload();
        skm.loadShopKeepers();
        tm.loadTaunts();
        tlm.loadTrails();
        wdm.loadWinDances();
        wem.loadWinEffects();
        shm.reload();
        ijm.reload();
        Utils.updateSB();
    }

    private void setupSounds() {
        getConfig().addDefault("sounds.streak2", "CLICK");
        getConfig().addDefault("sounds.streak3", "CLICK");
        getConfig().addDefault("sounds.streak4", "CLICK");
        getConfig().addDefault("sounds.streak5", "CLICK");
        getConfig().addDefault("sounds.cancelStart", "NOTE_BASS");
        getConfig().addDefault("sounds.upgrade", "LEVEL_UP");
        getConfig().addDefault("sounds.wineffects.vulcanwool", "CHICKEN_EGG_POP");
        getConfig().addDefault("sounds.wineffects.vulcanfire", "FUSE");
        getConfig().addDefault("sounds.wineffects.notes", "FIREWORK_LAUNCH");
        getConfig().addDefault("sounds.wineffects.chicken", "FIREWORK_LAUNCH");
        getConfig().addDefault("sounds.pickUpTeam", "FIREWORK_LAUNCH");
        getConfig().addDefault("sounds.pickUpOthers", "WITHER_HURT");
        getConfig().addDefault("sounds.captured", "LEVEL_UP");
        getConfig().addDefault("sounds.killeffects.tnt", "EXPLODE");
        getConfig().addDefault("sounds.killeffects.squid", "ITEM_PICKUP");
    }

    public void sendDebugMessage(String... s) {
        if (debugMode) {
            for (String st : s) {
                Bukkit.getConsoleSender().sendMessage("§7[§eCTW Debug§7] §e" + st);
            }
        }
    }

    public void sendLogMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage("§7[§eCTW§7] §e" + msg);
    }

    public void sendLogMessage(String... msg) {
        for (String m : msg) {
            Bukkit.getConsoleSender().sendMessage("§7[§eCTW§7] §e" + m);
        }
    }

    public String toStringCTWPlayer(CTWPlayer pd) {
        return ctw.toJson(pd, CTWPlayer.class);
    }

    public CTWPlayer fromStringCTWPlayer(String data) {
        return ctw.fromJson(data, CTWPlayer.class);
    }

}