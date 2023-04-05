package io.github.Leonardo0013YT.UltraCTW.listeners;

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
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.shop.Shop;
import io.github.Leonardo0013YT.UltraCTW.shop.ShopItem;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.upgrades.Upgrade;
import io.github.Leonardo0013YT.UltraCTW.upgrades.UpgradeLevel;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.atomic.AtomicBoolean;

public class MenuListener implements Listener {

    private UltraCTW plugin;

    public MenuListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenu(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE) || e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }
        ItemStack item = e.getCurrentItem();
        if (item.equals(plugin.getIm().getPoints()) || item.equals(plugin.getIm().getLobby()) || item.equals(plugin.getIm().getTeams()) || item.equals(plugin.getIm().getLeave()) || item.equals(plugin.getIm().getSetup())) {
            e.setCancelled(true);
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.buffItems.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            String key = NBTEditor.getString(item, "BUFF", "FLAG", "MENU");
            if (key == null || !plugin.getUm().getShops().containsKey(key)) return;
            String sKey = NBTEditor.getString(item, "SHOP", "FLAG", "MENU");
            Shop shop = plugin.getUm().getShops().get(key);
            if (!shop.getItems().containsKey(sKey)) return;
            ShopItem si = shop.getItems().get(sKey);
            GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
            GamePlayer gp = gf.getGamePlayer(p);
            if (si.getPrice() > gp.getCoins()) {
                p.sendMessage(plugin.getLang().get("messages.noCoins"));
                return;
            }
            FlagTeam ft = gf.getTeamPlayer(p);
            if (ft == null) return;
            si.execute(gf, ft);
            if (si.isYourTeam()) {
                ft.sendMessage(plugin.getLang().get("messages.buyedTeam").replace("<player>", p.getName()).replace("<enchant>", si.getName()));
            } else {
                ft.sendMessage(plugin.getLang().get("messages.buyedOtherTeam").replace("<player>", p.getName()).replace("<enchant>", si.getName()));
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.buffDebuff.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            String key = NBTEditor.getString(item, "BUFF", "FLAG", "MENU");
            if (key == null || !plugin.getUm().getShops().containsKey(key)) return;
            GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
            GamePlayer gp = gf.getGamePlayer(p);
            plugin.getFgm().createShopItemsMenu(p, plugin.getUm().getShops().get(key), gp);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.upgrades.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            String key = NBTEditor.getString(item, "UPGRADE", "FLAG", "MENU");
            if (key == null || !plugin.getUm().getUpgrades().containsKey(key)) return;
            Upgrade upgrade = plugin.getUm().getUpgrade(key);
            GameFlag gf = plugin.getGm().getGameFlagByPlayer(p);
            GamePlayer gp = gf.getGamePlayer(p);
            UpgradeLevel next;
            FlagTeam ft = gf.getTeamPlayer(p);
            if (key.equalsIgnoreCase("youpickaxe")) {
                next = upgrade.getNextLevel(gp.getPiUpgrade());
                gp.setPickaxeKey(key);
            } else {
                next = upgrade.getNextLevel(ft.getUpgradeHaste());
                gp.setTeamHaste(key);
            }
            if (next == null) {
                p.sendMessage(plugin.getLang().get("messages.maxImprovement"));
                return;
            }
            if (next.getPrice() < gp.getCoins()) {
                AtomicBoolean hand = new AtomicBoolean(true);
                upgrade.apply(r -> {
                    if (!r) {
                        p.sendMessage(plugin.getLang().get("messages.needItemHand"));
                        hand.set(false);
                    }
                }, p, ft, next);
                if (!hand.get()) return;
                gp.removeCoins(next.getPrice());
                if (key.equalsIgnoreCase("youpickaxe")) {
                    gp.setPiUpgrade(gp.getPiUpgrade() + 1);
                } else if (key.equalsIgnoreCase("teampickaxe")) {
                    ft.setUpgradeHaste(ft.getUpgradeHaste() + 1);
                }
                p.playSound(p.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1.0f, 1.0f);
                p.sendMessage(plugin.getLang().get("messages.buyed").replace("<enchant>", next.getName()));
                plugin.getFgm().createMainUpgradeMenu(p);
            } else {
                p.playSound(p.getLocation(), XSound.ENTITY_ENDERMAN_TELEPORT.parseSound(), 1.0f, 1.0f);
                p.sendMessage(plugin.getLang().get("messages.noCoins"));
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.shop.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            int id = NBTEditor.getInt(item, "SHOP", "ID", "BUY");
            io.github.Leonardo0013YT.UltraCTW.objects.ShopItem si = plugin.getShm().getItems().get(id);
            if (si == null) return;
            plugin.getShm().buy(p, si, si.getItem().getItemMeta().getDisplayName());
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.kitlevels.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.kitlevels.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            Kit k = plugin.getKm().getKitByItem(p, item);
            if (k == null) {
                return;
            }
            KitLevel kl = plugin.getKm().getKitLevelByItem(k, p, item);
            if (kl == null) {
                return;
            }
            plugin.getShm().buy(p, kl, k.getName());
            p.closeInventory();
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.kitflaglevels.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.kitflaglevels.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            Kit k = plugin.getKm().getKitByItem(p, item);
            if (k == null) {
                return;
            }
            KitLevel kl = plugin.getKm().getKitLevelByItem(k, p, item);
            if (kl == null) {
                return;
            }
            if (p.hasPermission(kl.getAutoGivePermission())) {
                sw.setKit(k.getId());
                sw.setKitLevel(kl.getLevel());
                p.sendMessage(plugin.getLang().get(p, "messages.select").replaceAll("<kit>", k.getName()));
                plugin.getUim().createKitSelectorMenu(p);
                return;
            }
            if (!sw.hasKitLevel(k.getId(), kl.getLevel())) {
                if (kl.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, kl, k.getName());
                }
            } else {
                sw.setKit(k.getId());
                sw.setKitLevel(kl.getLevel());
                p.sendMessage(plugin.getLang().get(p, "messages.select").replaceAll("<kit>", k.getName()));
            }
            p.closeInventory();
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.kitflagselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (e.getClick().equals(ClickType.LEFT)) {
                if (display.equals(plugin.getLang().get(p, "menus.kitflagselector.close.nameItem"))) {
                    if (e.getClick().equals(ClickType.RIGHT)) {
                        p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                        return;
                    }
                    p.closeInventory();
                    return;
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKitFlagSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKitFlagSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.kitflagselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.kitflagselector.deselect.nameItem"))) {
                p.sendMessage(plugin.getLang().get(p, "messages.deselect"));
                plugin.getUim().createKitFlagSelectorMenu(p);
                return;
            }
            Kit k = plugin.getKm().getKitByItem(p, item);
            if (k == null) {
                return;
            }
            plugin.getUim().createFlagKitLevelSelectorMenu(p, k);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.kitselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (e.getClick().equals(ClickType.LEFT)) {
                if (display.equals(plugin.getLang().get(p, "menus.kitselector.close.nameItem"))) {
                    if (e.getClick().equals(ClickType.RIGHT)) {
                        p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                        return;
                    }
                    p.closeInventory();
                    return;
                }
            }
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKitSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKitSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.kitselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.kitselector.deselect.nameItem"))) {
                p.sendMessage(plugin.getLang().get(p, "messages.deselect"));
                plugin.getUim().createKitSelectorMenu(p);
                return;
            }
            Kit k = plugin.getKm().getKitByItem(p, item);
            if (k == null) {
                return;
            }
            plugin.getUim().createKitLevelSelectorMenu(p, k);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get("menus.teams.title"))) {
            e.setCancelled(true);
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            Game game = plugin.getGm().getGameByPlayer(p);
            GameFlag gamef = plugin.getGm().getGameFlagByPlayer(p);
            String d = item.getItemMeta().getDisplayName();
            if (d.equals(plugin.getLang().get("menus.teams.random.nameItem"))) {
                if (game != null) {
                    game.addPlayerRandomTeam(p);
                } else {
                    gamef.addPlayerRandomTeam(p);
                }
                p.closeInventory();
                return;
            }
            String co = NBTEditor.getString(item, "SELECTOR", "TEAM", "COLOR");
            if (co == null) return;
            if (!p.hasPermission("ctw.teams.select")) {
                p.sendMessage(plugin.getLang().get("messages.noSelector"));
                return;
            }
            ChatColor c = ChatColor.valueOf(co);
            if (game != null) {
                Team team = game.getTeams().get(c);
                Team mayo = Utils.getMajorPlayersTeam(game);
                if (team.getTeamSize() > mayo.getTeamSize()) {
                    p.sendMessage(plugin.getLang().get("messages.teamMajorPlayers"));
                    return;
                }
                game.addPlayerTeam(p, team);
                p.sendMessage(plugin.getLang().get("messages.joinTeam").replaceAll("<team>", team.getName()));
            } else {
                FlagTeam team = gamef.getTeams().get(c);
                FlagTeam mayo = Utils.getMajorPlayersTeam(gamef);
                if (team.getTeamSize() > mayo.getTeamSize()) {
                    p.sendMessage(plugin.getLang().get("messages.teamMajorPlayers"));
                    return;
                }
                gamef.addPlayerTeam(p, team);
                p.sendMessage(plugin.getLang().get("messages.joinTeam").replaceAll("<team>", team.getName()));
            }
            p.closeInventory();
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.lobby.title"))) {
            if (plugin.getSm().isSetupInventory(p)) {
                return;
            }
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.lobby.shopkeepers.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createShopKeeperSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.trails.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createTrailsSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.taunts.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createTauntsSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.wineffects.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createWinEffectSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.killeffects.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createKillEffectSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.windances.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createWinDanceSelectorMenu(p);
            }
            if (display.equals(plugin.getLang().get(p, "menus.lobby.killsound.nameItem"))) {
                plugin.getUim().getPages().put(p, 1);
                plugin.getUim().createKillSoundSelectorMenu(p);
            }
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.shopkeeperselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createShopKeeperSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createShopKeeperSelectorMenu(p);
                return;
            }
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.shopkeeperselector.shopkeeper.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.shopkeeperselector.deselect.nameItem"))) {
                if (sw.getShopKeeper() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setShopKeeper(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectShopKeeper"));
                plugin.getUim().createShopKeeperSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.shopkeeperselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            ShopKeeper k = plugin.getSkm().getShopKeeperByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setShopKeeper(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectShopKeeper").replaceAll("<shopkeeper>", k.getName()));
                plugin.getUim().createShopKeeperSelectorMenu(p);
                return;
            }
            if (!sw.getShopkeepers().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setShopKeeper(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectShopKeeper").replaceAll("<shopkeeper>", k.getName()));
            }
            plugin.getUim().createShopKeeperSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.trailsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.deselect.nameItem"))) {
                if (sw.getTrail() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setTrail(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectTrail"));
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.trailsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            Trail k = plugin.getTlm().getTrailByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setTrail(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTrail").replaceAll("<trail>", k.getName()));
                plugin.getUim().createTrailsSelectorMenu(p);
                return;
            }
            if (!sw.getTrails().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setTrail(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTrail").replaceAll("<trail>", k.getName()));
            }
            plugin.getUim().createTrailsSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.tauntsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.deselect.nameItem"))) {
                if (sw.getTaunt() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setTaunt(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectTaunt"));
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.tauntsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            Taunt k = plugin.getTm().getTauntByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setTaunt(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTaunt").replaceAll("<taunt>", k.getName()));
                plugin.getUim().createTauntsSelectorMenu(p);
                return;
            }
            if (!sw.getTaunts().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setTaunt(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectTaunt").replaceAll("<taunt>", k.getName()));
            }
            plugin.getUim().createTauntsSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.killsoundsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.deselect.nameItem"))) {
                if (sw.getKillSound() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setKillSound(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselect"));
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killsoundsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            KillSound k = plugin.getKsm().getKillSoundByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setKillSound(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillSound").replaceAll("<killsound>", k.getName()));
                plugin.getUim().createKillSoundSelectorMenu(p);
                return;
            }
            if (!sw.getKillsounds().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setKillSound(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillSound").replaceAll("<killsound>", k.getName()));
            }
            plugin.getUim().createKillSoundSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.wineffectsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.deselect.nameItem"))) {
                if (sw.getWinEffect() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setWinEffect(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectWinEffect"));
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.wineffectsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraWinEffect k = plugin.getWem().getWinEffectByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setWinEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinEffect").replaceAll("<wineffect>", k.getName()));
                plugin.getUim().createWinEffectSelectorMenu(p);
                return;
            }
            if (!sw.getWineffects().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setWinEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinEffect").replaceAll("<wineffect>", k.getName()));
            }
            plugin.getUim().createWinEffectSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.killeffectsselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.deselect.nameItem"))) {
                if (sw.getKillEffect() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setKillEffect(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectKillEffect"));
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.killeffectsselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraKillEffect k = plugin.getKem().getKillEffectByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setKillEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillEffect").replaceAll("<killeffect>", k.getName()));
                plugin.getUim().createKillEffectSelectorMenu(p);
                return;
            }
            if (!sw.getKilleffects().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setKillEffect(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectKillEffect").replaceAll("<killeffect>", k.getName()));
            }
            plugin.getUim().createKillEffectSelectorMenu(p);
        }
        if (e.getView().getTitle().equals(plugin.getLang().get(p, "menus.windancesselector.title"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            ItemMeta im = item.getItemMeta();
            String display = im.getDisplayName();
            CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
            if (display.equals(plugin.getLang().get(p, "menus.next.nameItem"))) {
                plugin.getUim().addPage(p);
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.last.nameItem"))) {
                plugin.getUim().removePage(p);
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.kit.nameItem"))) {
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.deselect.nameItem"))) {
                if (sw.getWinDance() == 999999) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noSelect"));
                    return;
                }
                sw.setWinDance(999999);
                p.sendMessage(plugin.getLang().get(p, "messages.deselectWinDance"));
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (display.equals(plugin.getLang().get(p, "menus.windancesselector.close.nameItem"))) {
                if (e.getClick().equals(ClickType.RIGHT)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.closeWithClick"));
                    return;
                }
                p.closeInventory();
                return;
            }
            UltraWinDance k = plugin.getWdm().getWinDanceByItem(p, item);
            if (k == null) {
                return;
            }
            if (p.hasPermission(k.getAutoGivePermission())) {
                sw.setWinDance(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinDance").replaceAll("<windance>", k.getName()));
                plugin.getUim().createWinDanceSelectorMenu(p);
                return;
            }
            if (!sw.getWindances().contains(k.getId())) {
                if (k.needPermToBuy() && !p.hasPermission(k.getPermission())) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noPermit"));
                } else {
                    plugin.getShm().buy(p, k, k.getName());
                }
            } else {
                sw.setWinDance(k.getId());
                p.sendMessage(plugin.getLang().get(p, "messages.selectWinDance").replaceAll("<windance>", k.getName()));
            }
            plugin.getUim().createWinDanceSelectorMenu(p);
        }
    }

}