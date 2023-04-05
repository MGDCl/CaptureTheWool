package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.UltraKillEffect;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.ShopKeeper;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.UltraWinDance;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.UltraWinEffect;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.objects.ShopItem;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class ShopManager {

    private HashMap<Integer, ShopItem> items = new HashMap<>();
    private UltraCTW plugin;

    public ShopManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        items.clear();
        if (!plugin.getShop().isSet("shop")) return;
        for (String s : plugin.getShop().getConfig().getConfigurationSection("shop").getKeys(false)) {
            int id = Integer.parseInt(s);
            items.put(id, new ShopItem(plugin, "shop." + s));
        }
    }

    public void addItem(ItemStack item, double price) {
        String path = "shop." + items.size();
        ItemStack newItem = item.clone();
        ItemMeta im = newItem.getItemMeta();
        im.setDisplayName("§eItem " + items.size());
        im.setLore(Arrays.asList("§7This is a default lore", "§7change in shop.yml"));
        newItem.setItemMeta(im);
        plugin.getShop().set(path + ".item", newItem);
        plugin.getShop().set(path + ".price", price);
        plugin.getShop().save();
        reload();
    }

    public void buy(Player p, Purchasable purchasable, String name) {
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        if (purchasable instanceof ShopItem) {
            Game g = plugin.getGm().getGameByPlayer(p);
            GamePlayer gp = g.getGamePlayer(p);
            if (gp.getCoins() < purchasable.getPrice()) {
                p.sendMessage(plugin.getLang().get(p, "messages.noCoins"));
                return;
            }
            ShopItem si = (ShopItem) purchasable;
            p.getInventory().addItem(si.getItem());
            gp.setCoins(gp.getCoins() - si.getPrice());
            return;
        } else if (purchasable instanceof KitLevel) {
            Game g = plugin.getGm().getGameByPlayer(p);
            if (g != null) {
                GamePlayer gp = g.getGamePlayer(p);
                if (gp.getCoins() < purchasable.getPrice()) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noCoins"));
                    return;
                }
                KitLevel k = (KitLevel) purchasable;
                gp.setCoins(gp.getCoins() - k.getPrice());
                Team team = g.getTeamPlayer(p);
                plugin.getKm().giveKit(p, k.getKitID(), k.getLevel(), team);
            } else {
                KitLevel k = (KitLevel) purchasable;
                if (plugin.getCm().isKitLevelsOrder() && !isLastLevel(sw, k)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.buyLastLevel"));
                    return;
                }
                sw.addKitLevel(k.getKit().getId(), k.getLevel());
            }
            return;
        }
        if (!purchasable.isBuy()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noBuy"));
            return;
        }
        if (plugin.getAdm().getCoins(p) < purchasable.getPrice()) {
            p.sendMessage(plugin.getLang().get(p, "messages.noCoins"));
            return;
        }
        if (purchasable instanceof UltraKillEffect) {
            UltraKillEffect k = (UltraKillEffect) purchasable;
            sw.addKillEffects(k.getId());
        } else if (purchasable instanceof KillSound) {
            KillSound k = (KillSound) purchasable;
            sw.addKillSounds(k.getId());
        } else if (purchasable instanceof Taunt) {
            Taunt k = (Taunt) purchasable;
            sw.addTaunts(k.getId());
        } else if (purchasable instanceof Trail) {
            Trail k = (Trail) purchasable;
            sw.addTrails(k.getId());
        } else if (purchasable instanceof UltraWinDance) {
            UltraWinDance k = (UltraWinDance) purchasable;
            sw.addWinDances(k.getId());
        } else if (purchasable instanceof UltraWinEffect) {
            UltraWinEffect k = (UltraWinEffect) purchasable;
            sw.addWinEffects(k.getId());
        } else if (purchasable instanceof ShopKeeper) {
            ShopKeeper k = (ShopKeeper) purchasable;
            sw.addShopKeepers(k.getId());
        }
        Utils.updateSB(p);
        p.sendMessage(plugin.getLang().get(p, "messages.bought").replaceAll("<name>", name));
        plugin.getAdm().removeCoins(p, purchasable.getPrice());
    }

    public HashMap<Integer, ShopItem> getItems() {
        return items;
    }

    public boolean isLastLevel(CTWPlayer sw, KitLevel kl) {
        if (kl.getLevel() == 1) {
            return true;
        }
        return sw.hasKitLevel(kl.getKit().getId(), kl.getLevel() - 1);
    }

}