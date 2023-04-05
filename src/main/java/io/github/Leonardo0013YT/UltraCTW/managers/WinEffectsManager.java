package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.*;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class WinEffectsManager {

    private HashMap<Integer, UltraWinEffect> winEffects = new HashMap<>();
    private UltraCTW plugin;
    private int lastPage;

    public WinEffectsManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadWinEffects() {
        winEffects.clear();
        if (plugin.getWineffect().isSet("wineffects")) {
            ConfigurationSection conf = plugin.getWineffect().getConfig().getConfigurationSection("wineffects");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getWineffect().getInt("wineffects." + c + ".id");
                winEffects.put(id, new UltraWinEffect(plugin, "wineffects." + c));
            }
        }
    }

    public void execute(Game game, Player p, int id) {
        UltraWinEffect uwe = winEffects.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        WinEffect we;
        switch (uwe.getType()) {
            case "fireworks":
                we = new WinEffectFireworks();
                we.start(p, game);
                break;
            case "vulcanfire":
                we = new WinEffectVulcanFire();
                we.start(p, game);
                break;
            case "icewalker":
                we = new WinEffectStorm();
                we.start(p, game);
                break;
            case "notes":
                we = new WinEffectNotes();
                we.start(p, game);
                break;
            case "chickens":
                we = new WinEffectChicken();
                we.start(p, game);
                break;
            case "guardian":
                we = new WinEffectGuardians();
                we.start(p, game);
                break;
            case "dragon":
                we = new WinEffectDragonRider();
                we.start(p, game);
                break;
            default:
                we = new WinEffectVulcanWool();
                we.start(p, game);
                break;
        }
        game.addWinEffects(we);
    }

    public void execute(GameFlag game, Player p, int id) {
        UltraWinEffect uwe = winEffects.get(id);
        if (uwe == null || uwe.getType().equals("none")) {
            return;
        }
        WinEffect we;
        switch (uwe.getType()) {
            case "fireworks":
                we = new WinEffectFireworks();
                we.start(p, game);
                break;
            case "vulcanfire":
                we = new WinEffectVulcanFire();
                we.start(p, game);
                break;
            case "icewalker":
                we = new WinEffectStorm();
                we.start(p, game);
                break;
            case "notes":
                we = new WinEffectNotes();
                we.start(p, game);
                break;
            case "chickens":
                we = new WinEffectChicken();
                we.start(p, game);
                break;
            case "guardian":
                we = new WinEffectGuardians();
                we.start(p, game);
                break;
            case "dragon":
                we = new WinEffectDragonRider();
                we.start(p, game);
                break;
            default:
                we = new WinEffectVulcanWool();
                we.start(p, game);
                break;
        }
        game.addWinEffects(we);
    }

    public UltraWinEffect getWinEffectByItem(Player p, ItemStack item) {
        for (UltraWinEffect k : winEffects.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, UltraWinEffect> getWinEffects() {
        return winEffects;
    }

    public int getWinEffectsSize() {
        return winEffects.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (winEffects.containsKey(sw.getWinEffect())) {
            return winEffects.get(sw.getWinEffect()).getName();
        }
        return plugin.getLang().get(null, "messages.noSelected");
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        if (getLastPage() < lastPage) {
            this.lastPage = lastPage;
        }
    }

}