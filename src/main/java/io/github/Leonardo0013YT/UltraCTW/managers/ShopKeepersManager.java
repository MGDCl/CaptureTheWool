package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.ShopKeeper;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ShopKeepersManager {

    private final HashMap<Integer, ShopKeeper> shopkeepers = new HashMap<>();
    private final UltraCTW plugin;
    private int lastPage;

    public ShopKeepersManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void loadShopKeepers() {
        shopkeepers.clear();
        if (!plugin.getShopkeepers().isSet("shopkeepers")) return;
        for (String s : plugin.getShopkeepers().getConfig().getConfigurationSection("shopkeepers").getKeys(false)) {
            int id = plugin.getShopkeepers().getInt("shopkeepers." + s + ".id");
            shopkeepers.put(id, new ShopKeeper(plugin, "shopkeepers." + s));
        }
    }

    public ShopKeeper getShopKeeper(int id) {
        return shopkeepers.get(id);
    }

    public void spawnShopKeeper(Player p, Location loc, int id, NPCType npcType) {
        ShopKeeper sk = getShopKeeper(id);
        if (sk == null) return;
        EntityType type = EntityType.valueOf(sk.getEntityType());
        NPC npc = plugin.getVc().createNewNPC();
        npc.create(p, loc, type, npcType);
        if (!npc.toHide(p.getLocation())) {
            npc.spawn();
        }
        plugin.getNpc().addNPC(p, npc);
    }

    public ShopKeeper getShopKeeperByItem(Player p, ItemStack item) {
        for (ShopKeeper k : shopkeepers.values()) {
            if (k.getIcon(p).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                return k;
            }
        }
        return null;
    }

    public HashMap<Integer, ShopKeeper> getShopkeepers() {
        return shopkeepers;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

}