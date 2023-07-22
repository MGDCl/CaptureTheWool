package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KillSoundManager {

    private final HashMap<Integer, KillSound> killSounds = new HashMap<>();
    private final UltraCTW plugin;
    private int lastPage;

    public KillSoundManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadKillSounds() {
        killSounds.clear();
        if (plugin.getKillsound().isSet("killsounds")) {
            ConfigurationSection conf = plugin.getKillsound().getConfig().getConfigurationSection("killsounds");
            for (String c : conf.getKeys(false)) {
                int id = plugin.getKillsound().getInt("killsounds." + c + ".id");
                killSounds.put(id, new KillSound(plugin, "killsounds." + c));
                plugin.sendDebugMessage("§akillSound §b" + c + " §acargado correctamente.");
            }
        }
    }

    public void execute(Player k, Player d, int id) {
        KillSound ks = killSounds.get(id);
        if (ks == null) {
            return;
        }
        ks.execute(k, d);
    }

    public int getNextId() {
        return killSounds.size();
    }

    public KillSound getKillSoundByItem(Player p, ItemStack item) {
        for (KillSound k : killSounds.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, KillSound> getKillSounds() {
        return killSounds;
    }

    public int getKillSoundsSize() {
        return killSounds.size();
    }

    public String getSelected(CTWPlayer sw) {
        if (killSounds.containsKey(sw.getKillSound())) {
            return killSounds.get(sw.getKillSound()).getName();
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