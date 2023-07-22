package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.objects.Selection;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.setup.*;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class SetupListener implements Listener {

    private final UltraCTW plugin;

    public SetupListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (plugin.getSm().isSetupName(p)) {
            if (plugin.getSm().isSetupTaunt(p)) {
                if (plugin.getSm().isSetupName(p)) {
                    e.setCancelled(true);
                    TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                    String type = plugin.getSm().getSetupName(p);
                    if (ts.getActual() != null) {
                        TauntTypeSetup tts = ts.getActual();
                        if (type.equals("tauntstypeadd")) {
                            tts.getMsg().add(e.getMessage());
                            plugin.getUim().openTauntInventory(p, plugin.getUim().getMenus("tauntstype"), tts);
                        }
                        return;
                    }
                    if (type.equals("tauntslot")) {
                        int slot;
                        try {
                            slot = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (slot < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.taunts.minSlot"));
                            return;
                        }
                        if (slot > 53) {
                            p.sendMessage(plugin.getLang().get(p, "setup.taunts.maxSlot"));
                            return;
                        }
                        ts.setSlot(slot);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntpage")) {
                        int page;
                        try {
                            page = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (page < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.taunts.minPage"));
                            return;
                        }
                        ts.setPage(page);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntprice")) {
                        int price;
                        try {
                            price = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (price < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.taunts.minPrice"));
                            return;
                        }
                        ts.setPrice(price);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntpermission")) {
                        ts.setPermission(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("taunttitle")) {
                        ts.setTitle(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntsubtitle")) {
                        ts.setSubtitle(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntplayer")) {
                        ts.setPlayer(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("tauntnone")) {
                        ts.setNone(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                }
            }
            if (plugin.getSm().isSetupTrail(p)) {
                if (plugin.getSm().isSetupName(p)) {
                    e.setCancelled(true);
                    TrailSetup ts = plugin.getSm().getSetupTrail(p);
                    String type = plugin.getSm().getSetupName(p);
                    if (type.equals("trailspeed")) {
                        int speed;
                        try {
                            speed = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (speed < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minSpeed"));
                            return;
                        }
                        ts.setSpeed(speed);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailoffsetx")) {
                        float offsetX;
                        try {
                            offsetX = Float.parseFloat(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (offsetX < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetX"));
                            return;
                        }
                        ts.setOffsetX(offsetX);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailoffsety")) {
                        float offsetY;
                        try {
                            offsetY = Float.parseFloat(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (offsetY < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetY"));
                            return;
                        }
                        ts.setOffsetY(offsetY);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailoffsetz")) {
                        float offsetZ;
                        try {
                            offsetZ = Float.parseFloat(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (offsetZ < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minoffsetZ"));
                            return;
                        }
                        ts.setOffsetZ(offsetZ);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailpermission")) {
                        ts.setPermission(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailamount")) {
                        int amount;
                        try {
                            amount = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (amount < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minAmount"));
                            return;
                        }
                        ts.setAmount(amount);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailrange")) {
                        double range;
                        try {
                            range = Double.parseDouble(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (range < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minRange"));
                            return;
                        }
                        ts.setRange(range);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailprice")) {
                        int price;
                        try {
                            price = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (price < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minPrice"));
                            return;
                        }
                        ts.setPrice(price);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailslot")) {
                        int slot;
                        try {
                            slot = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (slot < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minSlot"));
                            return;
                        }
                        if (slot > 53) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.maxSlot"));
                            return;
                        }
                        ts.setSlot(slot);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailpage")) {
                        int page;
                        try {
                            page = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (page < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.minPage"));
                            return;
                        }
                        ts.setPage(page);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailparticle")) {
                        if (!plugin.getVc().getNMS().isParticle(e.getMessage().toUpperCase())) {
                            p.sendMessage(plugin.getLang().get(p, "setup.trails.noParticle"));
                            return;
                        }
                        ts.setParticle(e.getMessage().toUpperCase());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                    if (type.equals("trailpermission")) {
                        ts.setPermission(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    }
                }
            }
            if (plugin.getSm().isSetupKillSound(p)) {
                if (plugin.getSm().isSetupName(p)) {
                    e.setCancelled(true);
                    KillSoundSetup bs = plugin.getSm().getSetupKillSound(p);
                    String type = plugin.getSm().getSetupName(p);
                    if (type.equals("killsoundsprice")) {
                        int price;
                        try {
                            price = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (price < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minPrice"));
                            return;
                        }
                        bs.setPrice(price);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundsslot")) {
                        int slot;
                        try {
                            slot = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (slot < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minSlot"));
                            return;
                        }
                        if (slot > 53) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.maxSlot"));
                            return;
                        }
                        bs.setSlot(slot);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundspage")) {
                        int page;
                        try {
                            page = Integer.parseInt(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (page < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.balloons.minPage"));
                            return;
                        }
                        bs.setPage(page);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundspermission")) {
                        bs.setPermission(e.getMessage());
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundssound")) {
                        List<String> sounds = new ArrayList<>();
                        for (Sound v : Sound.values()) {
                            sounds.add(v.name());
                        }
                        if (!sounds.contains(e.getMessage().toUpperCase())) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.noSound"));
                            return;
                        }
                        bs.setSound(Sound.valueOf(e.getMessage().toUpperCase()));
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundsvol1")) {
                        float vol1;
                        try {
                            vol1 = Float.parseFloat(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (vol1 < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minVol1"));
                            return;
                        }
                        bs.setVol1(vol1);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                    if (type.equals("killsoundsvol2")) {
                        float vol2;
                        try {
                            vol2 = Float.parseFloat(e.getMessage());
                        } catch (NumberFormatException ex) {
                            p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                            return;
                        }
                        if (vol2 < 0) {
                            p.sendMessage(plugin.getLang().get(p, "setup.killsounds.minVol2"));
                            return;
                        }
                        bs.setVol2(vol2);
                        plugin.getSm().removeName(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<slot>", "" + bs.getSlot()},
                                new String[]{"<sound>", "" + bs.getSound().name()},
                                new String[]{"<vol1>", "" + bs.getVol1()},
                                new String[]{"<vol2>", "" + bs.getVol2()},
                                new String[]{"<price>", "" + bs.getPrice()},
                                new String[]{"<page>", "" + bs.getPage()},
                                new String[]{"<permission>", bs.getPermission()},
                                new String[]{"<name>", bs.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                    }
                }
            }
            if (plugin.getSm().isSetup(p)) {
                e.setCancelled(true);
                ArenaSetup as = plugin.getSm().getSetup(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("min")) {
                    int min;
                    try {
                        min = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (min < 2) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minMin"));
                        return;
                    }
                    as.setMin(min);
                    plugin.getSm().removeName(p);
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("teamsize")) {
                    int teamsize;
                    try {
                        teamsize = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (teamsize < 1) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minTeamSize"));
                        return;
                    }
                    as.setTeamSize(teamsize);
                    plugin.getSm().removeName(p);
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("woolsize")) {
                    int woolsize;
                    try {
                        woolsize = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (woolsize < 1) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minWoolSize"));
                        return;
                    }
                    as.setWoolSize(woolsize);
                    plugin.getSm().removeName(p);
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
                if (type.equals("amountteams")) {
                    int amountteams;
                    try {
                        amountteams = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    if (amountteams < 2) {
                        p.sendMessage(plugin.getLang().get(p, "setup.arena.minAmountTeams"));
                        return;
                    }
                    as.setAmountTeams(amountteams);
                    plugin.getSm().removeName(p);
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : as.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                            new String[]{"<name>", as.getName()},
                            new String[]{"<schematic>", as.getSchematic()},
                            new String[]{"<min>", "" + as.getMin()},
                            new String[]{"<teamSize>", "" + as.getTeamSize()},
                            new String[]{"<woolSize>", "" + as.getWoolSize()},
                            new String[]{"<squareds>", "" + getString(sq)},
                            new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                            new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                            new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                }
            }
            if (plugin.getSm().isSetupKit(p)) {
                e.setCancelled(true);
                KitSetup ks = plugin.getSm().getSetupKit(p);
                String type = plugin.getSm().getSetupName(p);
                if (type.equals("kitpermission")) {
                    ks.setPermission(e.getMessage());
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitslot")) {
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.setSlot(slot);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitpage")) {
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.setPage(page);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitMenu(p, ks);
                }
                if (type.equals("kitlevelslot")) {
                    int slot;
                    try {
                        slot = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setSlot(slot);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
                }
                if (type.equals("kitlevelpage")) {
                    int page;
                    try {
                        page = Integer.parseInt(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setPage(page);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
                }
                if (type.equals("kitlevelprice")) {
                    double price;
                    try {
                        price = Double.parseDouble(e.getMessage());
                    } catch (NumberFormatException ex) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                        return;
                    }
                    ks.getKls().setPrice(price);
                    plugin.getSm().removeName(p);
                    plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
            return;
        }
        ItemStack item = p.getItemInHand();
        if (item.equals(plugin.getIm().getSetup())) {
            if (plugin.getSm().isSetup(p)) {
                ArenaSetup as = plugin.getSm().getSetup(p);
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
            }
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (plugin.getSm().isSetup(p)) {
                ArenaSetup as = plugin.getSm().getSetup(p);
                if (item.equals(plugin.getIm().getPoints())) {
                    as.getSelection().setPos2(e.getClickedBlock().getLocation());
                    p.sendMessage(plugin.getLang().get("setup.setPosition").replaceAll("<pos>", "2"));
                }
            }
        }
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (plugin.getSm().isSetup(p)) {
                ArenaSetup as = plugin.getSm().getSetup(p);
                if (item.equals(plugin.getIm().getPoints())) {
                    e.setCancelled(true);
                    as.getSelection().setPos1(e.getClickedBlock().getLocation());
                    p.sendMessage(plugin.getLang().get("setup.setPosition").replaceAll("<pos>", "1"));
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (plugin.getSm().isSetup(p)) {
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemStack item = p.getItemInHand();
            if (item == null || item.getType().equals(Material.AIR)) return;
            if (as.getActual() == null) return;
            if (item.getType().name().contains("WOOL")) {
                XMaterial ma = XMaterial.matchXMaterial(item);
                TeamSetup ts = as.getActual();
                Block b = e.getBlockPlaced();
                ts.getWools().put(Utils.getColorByXMaterial(ma), b.getLocation());
                removeItemInHand(p);
                p.sendMessage(plugin.getLang().get("setup.arena.addWool").replaceAll("<loc>", Utils.getFormatedLocation(b.getLocation())));
            }
        }
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getClickedInventory().getType().equals(InventoryType.PLAYER) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (plugin.getSm().isSetupInventory(p)) {
            return;
        }
        if (plugin.getSm().isSetupTaunt(p)) {
            UltraInventory t = plugin.getUim().getMenus("taunts");
            UltraInventory tt = plugin.getUim().getMenus("tauntstype");
            if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                return;
            }
            if (e.getView().getTitle().equals(tt.getTitle())) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                    return;
                }
                if (!tt.getContents().containsKey(e.getSlot())) {
                    return;
                }
                ItemStack item = tt.getContents().get(e.getSlot());
                if (!item.hasItemMeta()) {
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()) {
                    return;
                }
                TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.tauntstype.add.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntstypeadd");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.tauntstype.setMessage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.tauntstype.save.nameItem"))) {
                    ts.saveTauntType(p);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                            new String[]{"<title>", ts.getTitle()},
                            new String[]{"<subtitle>", ts.getSubtitle()},
                            new String[]{"<name>", ts.getName()},
                            new String[]{"<player>", ts.getPlayer()},
                            new String[]{"<none>", ts.getNone()},
                            new String[]{"<slot>", "" + ts.getSlot()},
                            new String[]{"<price>", "" + ts.getPrice()},
                            new String[]{"<page>", "" + ts.getPage()},
                            new String[]{"<permission>", ts.getPermission()},
                            new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                }
            }
            if (e.getView().getTitle().equals(t.getTitle())) {
                e.setCancelled(true);
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                    return;
                }
                if (!t.getContents().containsKey(e.getSlot())) {
                    return;
                }
                ItemStack item = t.getContents().get(e.getSlot());
                if (!item.hasItemMeta()) {
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()) {
                    return;
                }
                TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.taunts.slot.nameItem"))) {
                    if (p.getItemOnCursor() == null) {
                        p.sendMessage(plugin.getLang().get(p, "setup.taunts.noCursor"));
                        return;
                    }
                    ItemStack it = p.getItemOnCursor();
                    if (it.hasItemMeta()) {
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    ts.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setplayer.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntplayer");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPlayer"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setnone.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntnone");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setNone"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.settitle.nameItem"))) {
                    plugin.getSm().setSetupName(p, "taunttitle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setTitle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.setsubtitle.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntsubtitle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setSubTitle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.slot.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.price.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.page.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.permission.nameItem"))) {
                    plugin.getSm().setSetupName(p, "tauntpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.isBuy.nameItem"))) {
                    ts.setBuy(!ts.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.setBuy").replace("<state>", Utils.parseBoolean(ts.isBuy())));
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                            new String[]{"<title>", ts.getTitle()},
                            new String[]{"<subtitle>", ts.getSubtitle()},
                            new String[]{"<name>", ts.getName()},
                            new String[]{"<player>", ts.getPlayer()},
                            new String[]{"<none>", ts.getNone()},
                            new String[]{"<slot>", "" + ts.getSlot()},
                            new String[]{"<price>", "" + ts.getPrice()},
                            new String[]{"<page>", "" + ts.getPage()},
                            new String[]{"<permission>", ts.getPermission()},
                            new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                }
                List<String> s = Arrays.asList(plugin.getVc().getNMS().getDamageCauses());
                String d = display.replace("§e", "");
                if (s.contains(d)) {
                    if (ts.getActual() != null) {
                        plugin.getUim().openTauntInventory(p, plugin.getUim().getMenus("tauntstype"), ts.getActual());
                    } else {
                        TauntTypeSetup tts = ts.getTaunts().get(d);
                        plugin.getUim().openTauntInventory(p, plugin.getUim().getMenus("tauntstype"), tts);
                        ts.setActual(tts);
                    }
                }
                if (display.equals(plugin.getLang().get(p, "menus.taunts.save.nameItem"))) {
                    ts.saveTaunt(p);
                    plugin.getSm().removeTaunt(p);
                    p.closeInventory();
                }
            }
        }
        if (plugin.getSm().isSetupTrail(p)) {
            UltraInventory t = plugin.getUim().getMenus("trails");
            if (e.getView().getTitle().equals(t.getTitle())) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                    return;
                }
                if (!t.getContents().containsKey(e.getSlot())) {
                    return;
                }
                e.setCancelled(true);
                ItemStack item = t.getContents().get(e.getSlot());
                if (!item.hasItemMeta()) {
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()) {
                    return;
                }
                TrailSetup ts = plugin.getSm().getSetupTrail(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.trails.icon.nameItem"))) {
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()) {
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    ts.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.speed.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailspeed");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setSpeed"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetX.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailoffsetx");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetX"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetY.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailoffsety");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetY"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.offsetZ.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailoffsetz");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setoffsetZ"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.amount.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailamount");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setAmount"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.range.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailrange");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setRange"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.price.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.slot.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.page.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailpage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.particle.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailparticle");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setParticle"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.permission.nameItem"))) {
                    plugin.getSm().setSetupName(p, "trailpermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.isBuy.nameItem"))) {
                    ts.setBuy(!ts.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.setBuy").replace("<state>", Utils.parseBoolean(ts.isBuy())));
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                            new String[]{"<name>", ts.getName()},
                            new String[]{"<slot>", "" + ts.getSlot()},
                            new String[]{"<price>", "" + ts.getPrice()},
                            new String[]{"<page>", "" + ts.getPage()},
                            new String[]{"<speed>", "" + ts.getSpeed()},
                            new String[]{"<offsetX>", "" + ts.getOffsetX()},
                            new String[]{"<offsetY>", "" + ts.getOffsetY()},
                            new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                            new String[]{"<amount>", "" + ts.getAmount()},
                            new String[]{"<range>", "" + ts.getRange()},
                            new String[]{"<particle>", ts.getParticle()},
                            new String[]{"<permission>", ts.getPermission()},
                            new String[]{"<name>", ts.getName()},
                            new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                }
                if (display.equals(plugin.getLang().get(p, "menus.trails.save.nameItem"))) {
                    ts.saveTrail(p);
                    p.closeInventory();
                    plugin.getSm().removeTrail(p);
                }
            }
        }
        if (plugin.getSm().isSetupKillSound(p)) {
            UltraInventory t = plugin.getUim().getMenus("killsounds");
            if (e.getView().getTitle().equals(t.getTitle())) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                    return;
                }
                if (!t.getContents().containsKey(e.getSlot())) {
                    return;
                }
                e.setCancelled(true);
                ItemStack item = t.getContents().get(e.getSlot());
                if (!item.hasItemMeta()) {
                    return;
                }
                if (!item.getItemMeta().hasDisplayName()) {
                    return;
                }
                KillSoundSetup bs = plugin.getSm().getSetupKillSound(p);
                ItemMeta im = item.getItemMeta();
                String display = im.getDisplayName();
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.icon.nameItem"))) {
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noHand"));
                        return;
                    }
                    ItemStack it = p.getItemInHand();
                    if (it.hasItemMeta()) {
                        ItemMeta imt = it.getItemMeta();
                        imt.setDisplayName(null);
                        imt.setLore(null);
                        it.setItemMeta(imt);
                    }
                    bs.setIcon(it);
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setIcon"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.price.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundsprice");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPrice"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.slot.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundsslot");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setSlot"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.page.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundspage");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPage"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.permission.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundspermission");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setPermission"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.sound.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundssound");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setSound"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.vol1.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundsvol1");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setVol1"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.vol2.nameItem"))) {
                    plugin.getSm().setSetupName(p, "killsoundsvol2");
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setVol2"));
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.isBuy.nameItem"))) {
                    bs.setBuy(!bs.isBuy());
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.setBuy").replace("<state>", Utils.parseBoolean(bs.isBuy())));
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                            new String[]{"<name>", bs.getName()},
                            new String[]{"<slot>", "" + bs.getSlot()},
                            new String[]{"<sound>", "" + bs.getSound().name()},
                            new String[]{"<vol1>", "" + bs.getVol1()},
                            new String[]{"<vol2>", "" + bs.getVol2()},
                            new String[]{"<price>", "" + bs.getPrice()},
                            new String[]{"<page>", "" + bs.getPage()},
                            new String[]{"<permission>", bs.getPermission()},
                            new String[]{"<name>", bs.getName()},
                            new String[]{"<purchasable>", Utils.parseBoolean(bs.isBuy())});
                }
                if (display.equals(plugin.getLang().get(p, "menus.killsounds.save.nameItem"))) {
                    bs.saveKillSound(p);
                    p.closeInventory();
                    plugin.getSm().removeKillSound(p);
                }
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kititems.title"))) {
            KitSetup ks = plugin.getSm().getSetupKit(p);
            KitLevelSetup kls = ks.getKls();
            ItemStack item = e.getCurrentItem();
            if (e.getSlot() == 3) {
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[3]);
                            kls.getArmor()[3] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    e.setCancelled(true);
                    kls.getArmor()[3] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 2) {
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[2]);
                            kls.getArmor()[2] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    e.setCancelled(true);
                    kls.getArmor()[2] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 1) {
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[1]);
                            kls.getArmor()[1] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    e.setCancelled(true);
                    kls.getArmor()[1] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (e.getSlot() == 0) {
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                if (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR) || p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(kls.getArmor()[0]);
                            kls.getArmor()[0] = null;
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    e.setCancelled(true);
                    kls.getArmor()[0] = p.getItemOnCursor();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setItemOnCursor(null);
                            plugin.getSem().createSetupKitItemsMenu(p, kls);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
            if (plugin.getSem().getWhites().contains(e.getSlot())) {
                e.setCancelled(true);
            }
            if (e.getSlot() == 7) {
                e.setCancelled(true);
            }
            if (e.getSlot() == 8) {
                e.setCancelled(true);
                for (int i = 10; i < 36; i++) {
                    kls.getInv()[i] = e.getInventory().getItem(i);
                }
                for (int i = 45; i < 54; i++) {
                    kls.getInv()[i - 45] = e.getInventory().getItem(i);
                }
                p.sendMessage(plugin.getLang().get("setup.kits.save"));
                plugin.getSem().createSetupKitLevelsMenu(p, kls);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kitlevelssetup.title"))) {
            e.setCancelled(true);
            KitSetup ks = plugin.getSm().getSetupKit(p);
            KitLevelSetup kls = ks.getKls();
            ItemStack item = e.getCurrentItem();
            String display = item.getItemMeta().getDisplayName();
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.icon.nameItem"))) {
                if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(plugin.getLang().get(p, "setup.noHand"));
                    return;
                }
                ItemStack it = p.getItemInHand();
                if (it.hasItemMeta()) {
                    ItemMeta imt = it.getItemMeta();
                    imt.setDisplayName("§a" + ks.getName());
                    imt.setLore(null);
                    it.setItemMeta(imt);
                }
                kls.setIcon(it);
                p.sendMessage(plugin.getLang().get(p, "setup.kits.setIcon"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.slot.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitlevelslot");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setSlot"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.page.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitlevelpage");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPage"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.buy.nameItem"))) {
                kls.setBuy(!kls.isBuy());
                p.sendMessage(plugin.getLang().get(p, "setup.setBuy").replaceAll("<state>", Utils.parseBoolean(kls.isBuy())));
                plugin.getSem().createSetupKitLevelsMenu(p, kls);
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.price.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitlevelprice");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPrice"));
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.items.nameItem"))) {
                plugin.getSem().createSetupKitItemsMenu(p, kls);
            }
            if (display.equals(plugin.getLang().get("menus.kitlevelssetup.save.nameItem"))) {
                ks.saveKitLevel();
                plugin.getSem().createSetupKitMenu(p, ks);
                p.sendMessage(plugin.getLang().get("setup.kits.saveLevel"));
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.kitsetup.title"))) {
            e.setCancelled(true);
            KitSetup ks = plugin.getSm().getSetupKit(p);
            ItemStack item = e.getCurrentItem();
            String display = item.getItemMeta().getDisplayName();
            if (display.equals(plugin.getLang().get("menus.kitsetup.permission.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitpermission");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPermission"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.slot.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitslot");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setSlot"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.page.nameItem"))) {
                plugin.getSm().setSetupName(p, "kitpage");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.setPage"));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.flag.nameItem"))) {
                ks.setFlag(!ks.isFlag());
                plugin.getSem().createSetupKitMenu(p, ks);
                p.sendMessage(plugin.getLang().get(p, "setup.setBuy").replace("<state>", Utils.parseBoolean(ks.isFlag())));
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.levels.nameItem"))) {
                if (ks.getKls() == null) {
                    ks.setKls(new KitLevelSetup(ks.getLevels().size() + 1));
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.newLevel"));
                }
                plugin.getSem().createSetupKitLevelsMenu(p, ks.getKls());
            }
            if (display.equals(plugin.getLang().get("menus.kitsetup.save.nameItem"))) {
                ks.save();
                plugin.getSm().removeSetupKit(p);
                p.closeInventory();
                p.sendMessage(plugin.getLang().get("setup.kits.saveKit"));
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamColor.title"))) {
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            String c = NBTEditor.getString(item, "SELECT", "TEAM", "COLORS");
            if (c == null) {
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            if (plugin.getSm().isSetup(p)) {
                ArenaSetup as = plugin.getSm().getSetup(p);
                TeamSetup ts = plugin.getSm().getSetupTeam(p);
                ts.getColors().add(color);
                if (ts.getColors().size() >= as.getWoolSize()) {
                    p.closeInventory();
                    p.sendMessage(plugin.getLang().get("setup.arena.setColors"));
                    ArrayList<ChatColor> wools = ts.getColors();
                    wools.forEach(w -> p.getInventory().addItem(Utils.getXMaterialByColor(w).parseItem()));
                    p.sendMessage(plugin.getLang().get("setup.team.giveAvailableWools"));
                    return;
                }
                p.sendMessage(plugin.getLang().get("setup.arena.addColor").replaceAll("<color>", plugin.getLang().get("teams." + color.name().toLowerCase())));
                plugin.getSem().createSetupColorTeam(p, as);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.spawners.title"))) {
            e.setCancelled(true);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemStack item = e.getCurrentItem();
            String c = NBTEditor.getString(item, "SELECT", "TEAM", "SPAWNER");
            if (c == null) {
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            ts.getSpawners().put(color, p.getLocation());
            p.sendMessage(plugin.getLang().get("setup.arena.addSpawner").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
            if (ts.getSpawners().size() >= ts.getColors().size()) {
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : ts.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            } else {
                plugin.getSem().createSetupSpawnerColor(p, ts);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamsetup.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            TeamSetup ts = plugin.getSm().getSetupTeam(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawn.nameItem"))) {
                ts.setSpawn(p.getLocation());
                p.sendMessage(plugin.getLang().get("setup.arena.setSpawn").replaceAll("<location>", Utils.getFormatedLocation(p.getLocation())));
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : ts.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});

            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.squared.nameItem"))) {
                Selection s = as.getSelection();
                if (e.getClick().equals(ClickType.RIGHT)) {
                    if (ts.getSquareds().isEmpty()) {
                        p.sendMessage(plugin.getLang().get("setup.arena.noLast"));
                        return;
                    }
                    ts.getSquareds().remove(ts.getSquareds().size() - 1);
                    p.sendMessage(plugin.getLang().get("setup.arena.removed"));
                } else {
                    if (s.getPos1() == null || s.getPos2() == null) {
                        p.sendMessage(plugin.getLang().get("setup.arena.needPositions"));
                        return;
                    }
                    ts.addSquared(s);
                    p.sendMessage(plugin.getLang().get("setup.arena.setProteccion"));
                    s.setPos1(null);
                    s.setPos2(null);
                }
                ArrayList<String> sp = new ArrayList<>();
                ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared ss : ts.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                        new String[]{"<color>", ts.getColor().name()},
                        new String[]{"<generators>", getString(sp)},
                        new String[]{"<squareds>", getString(sq)},
                        new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.spawner.nameItem"))) {
                if (ts.getColors().size() < as.getWoolSize()) {
                    p.sendMessage(plugin.getLang().get("setup.arena.firstWool"));
                    return;
                }
                if (e.getClick().equals(ClickType.RIGHT)) {
                    if (ts.getSpawners().isEmpty()) {
                        p.sendMessage(plugin.getLang().get("setup.team.noSpawners"));
                        return;
                    }
                    TreeMap<ChatColor, Location> tm = new TreeMap<>(ts.getSpawners());
                    ts.getSpawners().remove(tm.lastKey());
                    p.sendMessage(plugin.getLang().get("setup.team.removed"));
                    ArrayList<String> sp = new ArrayList<>();
                    ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared ss : ts.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                            new String[]{"<color>", ts.getColor().name()},
                            new String[]{"<generators>", getString(sp)},
                            new String[]{"<squareds>", getString(sq)},
                            new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
                } else {
                    plugin.getSem().createSetupSpawnerColor(p, ts);
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.wool.nameItem"))) {
                if (ts.getColors().size() >= as.getWoolSize()) {
                    p.sendMessage(plugin.getLang().get("setup.arena.alreadySetWool"));
                    return;
                }
                plugin.getSem().createSetupColorTeam(p, as);
            }
            if (display.equals(plugin.getLang().get(p, "menus.teamsetup.save.nameItem"))) {
                if (ts.getSpawn() == null) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.spawn"));
                    return;
                }
                if (ts.getWools().size() < as.getWoolSize()) {
                    p.sendMessage(plugin.getLang().get("setup.team.noWools"));
                    return;
                }
                if (ts.getColors().size() < as.getWoolSize()) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.noColors"));
                    return;
                }
                if (ts.getSpawners().size() < as.getWoolSize()) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.noSpawner"));
                    return;
                }
                as.saveTeam();
                p.sendMessage(plugin.getLang().get("setup.arena.teamSaved"));
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                plugin.getSm().removeTeam(p);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teamsColor.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemStack item = e.getCurrentItem();
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get("menus.back.nameItem"))) {
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                return;
            }
            String c = NBTEditor.getString(item, "SETUP", "TEAM", "COLOR");
            if (c == null) {
                return;
            }
            ChatColor color = ChatColor.valueOf(c);
            TeamSetup ts = new TeamSetup(color);
            plugin.getSm().setSetupTeam(p, ts);
            ArrayList<String> sp = new ArrayList<>();
            ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
            ArrayList<String> sq = new ArrayList<>();
            for (Squared s : ts.getSquareds()) {
                sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                sq.add("§7");
            }
            plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                    new String[]{"<color>", ts.getColor().name()},
                    new String[]{"<generators>", getString(sp)},
                    new String[]{"<squareds>", getString(sq)},
                    new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
            p.sendMessage(plugin.getLang().get("setup.arena.createDontWools"));
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.setup.title"))) {
            e.setCancelled(true);
            ArenaSetup as = plugin.getSm().getSetup(p);
            ItemMeta im = e.getCurrentItem().getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.setup.npcKits.nameItem"))) {
                as.getNpcKits().add(Utils.getLocationString(p.getLocation()));
                p.sendMessage(plugin.getLang().get("setup.arena.setNPCKits"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.npcShop.nameItem"))) {
                as.getNpcShop().add(Utils.getLocationString(p.getLocation()));
                p.sendMessage(plugin.getLang().get("setup.arena.setNPCShop"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.teams.nameItem"))) {
                if (!plugin.getSm().isSetupTeam(p)) {
                    plugin.getSem().createSetupSelectMenu(p, as);
                } else {
                    TeamSetup ts = plugin.getSm().getSetupTeam(p);
                    as.setActual(ts);
                    ArrayList<String> sp = new ArrayList<>();
                    ts.getSpawners().values().forEach(l -> sp.add(Utils.getFormatedLocation(l)));
                    ArrayList<String> sq = new ArrayList<>();
                    for (Squared s : ts.getSquareds()) {
                        sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                        sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                        sq.add("§7");
                    }
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("teamsetup"),
                            new String[]{"<color>", ts.getColor().name()},
                            new String[]{"<generators>", getString(sp)},
                            new String[]{"<squareds>", getString(sq)},
                            new String[]{"<spawn>", "" + Utils.getFormatedLocation(ts.getSpawn())});
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.squared.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    if (as.getSquareds().isEmpty()) {
                        p.sendMessage(plugin.getLang().get("setup.arena.noLast"));
                        return;
                    }
                    as.getSquareds().remove(as.getSquareds().size() - 1);
                    p.sendMessage(plugin.getLang().get("setup.arena.removed"));
                } else {
                    Selection s = as.getSelection();
                    if (s.getPos1() == null || s.getPos2() == null) {
                        p.sendMessage(plugin.getLang().get("setup.arena.needPositions"));
                        return;
                    }
                    as.addSquared(s);
                    p.sendMessage(plugin.getLang().get("setup.arena.setProteccion"));
                    s.setPos1(null);
                    s.setPos2(null);
                }
                ArrayList<String> sq = new ArrayList<>();
                for (Squared ss : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.protection.nameItem"))) {
                Selection s = as.getSelection();
                if (s.getPos1() == null || s.getPos2() == null) {
                    p.sendMessage(plugin.getLang().get("setup.arena.needPositions"));
                    return;
                }
                as.setProtection(new Squared(s.getPos2(), s.getPos1(), false, true));
                p.sendMessage(plugin.getLang().get("setup.arena.setProteccion"));
                s.setPos1(null);
                s.setPos2(null);
                ArrayList<String> sq = new ArrayList<>();
                for (Squared ss : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(ss.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(ss.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.min.nameItem"))) {
                plugin.getSm().setSetupName(p, "min");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setMin"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.teamSize.nameItem"))) {
                plugin.getSm().setSetupName(p, "teamsize");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setTeamSize"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.teamsAmount.nameItem"))) {
                plugin.getSm().setSetupName(p, "amountteams");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setAmountTeams"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.woolSize.nameItem"))) {
                if (as.getTeams().size() > 0) {
                    p.sendMessage(plugin.getLang().get("setup.arena.alreadyTeam"));
                    return;
                }
                plugin.getSm().setSetupName(p, "woolsize");
                p.closeInventory();
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setWoolSize"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.lobby.nameItem"))) {
                as.setLobby(p.getLocation());
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setLobby"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.spect.nameItem"))) {
                as.setSpectator(p.getLocation());
                ArrayList<String> sq = new ArrayList<>();
                for (Squared s : as.getSquareds()) {
                    sq.add("§bMax: §e" + Utils.getFormatedLocation(s.getMax()));
                    sq.add("§bMin: §e" + Utils.getFormatedLocation(s.getMin()));
                    sq.add("§7");
                }
                plugin.getUim().openInventory(p, plugin.getUim().getMenus("setup"),
                        new String[]{"<name>", as.getName()},
                        new String[]{"<schematic>", as.getSchematic()},
                        new String[]{"<min>", "" + as.getMin()},
                        new String[]{"<teamSize>", "" + as.getTeamSize()},
                        new String[]{"<woolSize>", "" + as.getWoolSize()},
                        new String[]{"<squareds>", "" + getString(sq)},
                        new String[]{"<teamAmount>", "" + as.getAmountTeams()},
                        new String[]{"<lobby>", Utils.getFormatedLocation(as.getLobby())},
                        new String[]{"<spect>", Utils.getFormatedLocation(as.getSpectator())});
                p.sendMessage(plugin.getLang().get(p, "setup.arena.setSpect"));
            }
            if (display.equals(plugin.getLang().get(p, "menus.setup.save.nameItem"))) {
                if (as.getLobby() == null) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.lobby"));
                    return;
                }
                if (as.getSpectator() == null) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.spectator"));
                    return;
                }
                if (as.getTeams().size() < 2) {
                    p.sendMessage(plugin.getLang().get("setup.arena.noSet.needTwoTeams"));
                    return;
                }
                p.closeInventory();
                as.save(p);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (plugin.getSm().isSetupInventory(p)) {
            UltraInventory i = plugin.getSm().getSetupInventory(p);
            plugin.getUim().setInventory(i.getName(), e.getInventory());
            plugin.getSm().removeInventory(p);
            p.sendMessage(plugin.getLang().get(p, "setup.menus.finishEdit"));
        }
    }

    public String getString(ArrayList<String> list) {
        if (list.isEmpty()) {
            return "§cEmpty";
        }
        StringBuilder r = new StringBuilder();
        for (String s : list) {
            r.append("<newLine>").append("§e").append(s);
        }
        return r.toString().replaceFirst("<newLine>", "");
    }

    private void removeItemInHand(Player p) {
        ItemStack item = p.getItemInHand();
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            p.setItemInHand(null);
        }
    }

}