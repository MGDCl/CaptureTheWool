package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects.UltraKillEffect;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds.KillSound;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.Kit;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.kits.KitLevel;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers.ShopKeeper;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts.Taunt;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.trails.Trail;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.windances.UltraWinDance;
import io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects.UltraWinEffect;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.inventories.LobbyShopMenu;
import io.github.Leonardo0013YT.UltraCTW.inventories.setup.*;
import io.github.Leonardo0013YT.UltraCTW.setup.TauntTypeSetup;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class UltraInventoryMenu {

    private HashMap<String, UltraInventory> menus = new HashMap<>();
    private HashMap<Player, Integer> pages = new HashMap<>();
    private UltraCTW plugin;

    public UltraInventoryMenu(UltraCTW plugin) {
        this.plugin = plugin;
        loadMenus();
    }

    public void loadMenus() {
        menus.clear();
        menus.put("setup", new SetupArenaMenu(plugin, "setup"));
        menus.put("teamsetup", new SetupTeamMenu(plugin, "teamsetup"));
        menus.put("lobby", new LobbyShopMenu(plugin, "lobby"));
        menus.put("killsounds", new SetupKillSoundMenu(plugin, "killsounds"));
        menus.put("taunts", new SetupTauntMenu(plugin, "taunts"));
        menus.put("tauntstype", new SetupTauntTypeMenu(plugin, "tauntstype"));
        menus.put("trails", new SetupTrailMenu(plugin, "trails"));
    }

    public UltraInventory getMenus(String t) {
        return menus.get(t);
    }

    public void openTauntInventory(Player p, UltraInventory i, TauntTypeSetup tts) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (String l : tts.getMsg()) {
            ItemStack it = ItemBuilder.item(XMaterial.PAPER, "§eMessage", l);
            inv.addItem(it);
        }
        for (Map.Entry<Integer, ItemStack> entry : i.getContents().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue();
            inv.setItem(s, it);
        }
        p.openInventory(inv);
    }

    public void openInventory(Player p, UltraInventory i) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getConfig().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue();
            inv.setItem(s, it);
        }
        p.openInventory(inv);
    }

    public Inventory openContentInventory(Player p, UltraInventory i) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getContents().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue();
            inv.setItem(s, ItemBuilder.parseVariables(p, it, plugin));
        }
        p.openInventory(inv);
        return inv;
    }

    public void openInventory(Player p, UltraInventory i, String[]... t) {
        Inventory inv = Bukkit.createInventory(null, i.getRows() * 9, i.getTitle());
        for (Map.Entry<Integer, ItemStack> entry : i.getContents().entrySet()) {
            Integer s = entry.getKey();
            ItemStack it = entry.getValue().clone();
            inv.setItem(s, ItemBuilder.parseVariables(p, it, plugin, t));
        }
        p.openInventory(inv);
    }

    public void setInventory(String inv, Inventory close) {
        if (menus.containsKey(inv)) {
            Map<Integer, ItemStack> items = new HashMap<>();
            for (int i = 0; i < close.getSize(); i++) {
                ItemStack it = close.getItem(i);
                if (it == null || it.getType().equals(Material.AIR)) {
                    continue;
                }
                items.put(i, it);
            }
            menus.get(inv).setConfig(items);
            menus.get(inv).save();
        }
    }

    public void createKitSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.kitselector.title"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.kitselector.close.nameItem"), plugin.getLang().get(p, "menus.kitselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (Kit k : plugin.getKm().getKits().values()) {
            if (k.isFlag()) continue;
            if (k.getPage() != page) continue;
            if (plugin.getCm().isExcluideDefKits()) {
                if (plugin.getKm().getDefaultKits().contains(k.getId())) continue;
            }
            inv.setItem(k.getSlot(), k.getLevels().get(1).getIcon(p));
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getKm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createKitLevelSelectorMenu(Player p, Kit k) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.kitlevels.title"));
        fillKits(p, k, inv);
    }

    private void fillKits(Player p, Kit k, Inventory inv) {
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.kitlevels.close.nameItem"), plugin.getLang().get(p, "menus.kitlevels.close.loreItem"));
        for (KitLevel kl : k.getLevels().values()) {
            ItemStack i = kl.getIcon(p);
            inv.setItem(kl.getSlot(), i);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createShopKeeperSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.shopkeeperselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.shopkeeperselector.deselect.nameItem"), plugin.getLang().get(p, "menus.shopkeeperselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.shopkeeperselector.close.nameItem"), plugin.getLang().get(p, "menus.shopkeeperselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (ShopKeeper k : plugin.getSkm().getShopkeepers().values()) {
            if (k.getId() == sw.getTrail()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.shopkeeperselector.shopkeeper.nameItem"), plugin.getLang().get(p, "menus.shopkeeperselector.shopkeeper.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getTrail() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getSkm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createTrailsSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.trailsselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.trailsselector.deselect.nameItem"), plugin.getLang().get(p, "menus.trailsselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.trailsselector.close.nameItem"), plugin.getLang().get(p, "menus.trailsselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (Trail k : plugin.getTlm().getTrails().values()) {
            if (k.getId() == sw.getTrail()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.trailsselector.trail.nameItem"), plugin.getLang().get(p, "menus.trailsselector.trail.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getTrail() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getTlm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createTauntsSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.tauntsselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.tauntsselector.deselect.nameItem"), plugin.getLang().get(p, "menus.tauntsselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.tauntsselector.close.nameItem"), plugin.getLang().get(p, "menus.tauntsselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (Taunt k : plugin.getTm().getTaunts().values()) {
            if (k.getId() == sw.getTaunt()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.tauntsselector.taunt.nameItem"), plugin.getLang().get(p, "menus.tauntsselector.taunt.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getTaunt() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getTm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createKillSoundSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.killsoundsselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.killsoundsselector.deselect.nameItem"), plugin.getLang().get(p, "menus.killsoundsselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.killsoundsselector.close.nameItem"), plugin.getLang().get(p, "menus.killsoundsselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (KillSound k : plugin.getKsm().getKillSounds().values()) {
            if (k.getId() == sw.getKillSound()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.killsoundsselector.killsound.nameItem"), plugin.getLang().get(p, "menus.killsoundsselector.killsound.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getKillSound() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getKsm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createKillEffectSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.killeffectsselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.killeffectsselector.deselect.nameItem"), plugin.getLang().get(p, "menus.killeffectsselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.killeffectsselector.close.nameItem"), plugin.getLang().get(p, "menus.killeffectsselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (UltraKillEffect k : plugin.getKem().getKillEffect().values()) {
            if (k.getId() == sw.getKillEffect()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.killeffectsselector.killeffect.nameItem"), plugin.getLang().get(p, "menus.killeffectsselector.killeffect.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getKillEffect() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getKem().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createWinEffectSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.wineffectsselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.wineffectsselector.deselect.nameItem"), plugin.getLang().get(p, "menus.wineffectsselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.wineffectsselector.close.nameItem"), plugin.getLang().get(p, "menus.wineffectsselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (UltraWinEffect k : plugin.getWem().getWinEffects().values()) {
            if (k.getId() == sw.getWinEffect()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.wineffectsselector.wineffect.nameItem"), plugin.getLang().get(p, "menus.wineffectsselector.wineffect.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getWinEffect() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getWem().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public void createWinDanceSelectorMenu(Player p) {
        int page = pages.get(p);
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get(p, "menus.windancesselector.title"));
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        ItemStack deselect = ItemBuilder.item(XMaterial.BARRIER, plugin.getLang().get(p, "menus.windancesselector.deselect.nameItem"), plugin.getLang().get(p, "menus.windancesselector.deselect.loreItem"));
        ItemStack close = ItemBuilder.item(XMaterial.matchXMaterial(plugin.getCm().getBack()), plugin.getLang().get(p, "menus.windancesselector.close.nameItem"), plugin.getLang().get(p, "menus.windancesselector.close.loreItem"));
        ItemStack next = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.next.nameItem"), plugin.getLang().get(p, "menus.next.loreItem"));
        ItemStack last = ItemBuilder.item(XMaterial.ARROW, 1, plugin.getLang().get(p, "menus.last.nameItem"), plugin.getLang().get(p, "menus.last.loreItem"));
        for (UltraWinDance k : plugin.getWdm().getWinDance().values()) {
            if (k.getId() == sw.getWinDance()) {
                ItemStack i = k.getIcon(p);
                ItemStack kit = ItemBuilder.nameLore(i.clone(), plugin.getLang().get(p, "menus.windancesselector.windance.nameItem"), plugin.getLang().get(p, "menus.windancesselector.windance.loreItem"));
                inv.setItem(50, kit);
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), i);
            } else {
                if (k.getPage() != page) continue;
                inv.setItem(k.getSlot(), k.getIcon(p));
            }
        }
        if (sw.getWinDance() != 999999) {
            inv.setItem(48, deselect);
        }
        if (page > 1) {
            inv.setItem(45, last);
        }
        if (page < plugin.getWdm().getLastPage()) {
            inv.setItem(53, next);
        }
        inv.setItem(49, close);
        p.openInventory(inv);
    }

    public HashMap<Player, Integer> getPages() {
        return pages;
    }

    public void addPage(Player p) {
        pages.put(p, pages.get(p) + 1);
    }

    public void removePage(Player p) {
        pages.put(p, pages.get(p) - 1);
    }

}