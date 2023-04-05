package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.flag.Mine;
import io.github.Leonardo0013YT.UltraCTW.game.GameEvent;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.MineCountdown;
import io.github.Leonardo0013YT.UltraCTW.objects.ObjectPotion;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.upgrades.Upgrade;
import io.github.Leonardo0013YT.UltraCTW.upgrades.UpgradeLevel;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class FlagListener implements Listener {

    private UltraCTW plugin;

    public FlagListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    static void addStats(EntityDamageByEntityEvent e, Player p, Player d, UltraCTW plugin) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(d);
        if (p.getWorld().equals(d.getWorld())) {
            int distance = (int) d.getLocation().distance(p.getLocation());
            if (ctw.getMaxBowDistance() < distance) {
                ctw.setMaxBowDistance(distance);
            }
            if (p.getHealth() - e.getFinalDamage() <= 0) {
                if (ctw.getBowKillDistance() < distance) {
                    ctw.setBowKillDistance(distance);
                }
            }
        }
        ctw.setsShots(ctw.getsShots() + 1);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        e.getRecipients().clear();
        String msg;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            msg = formatLobby(p, e.getMessage());
            e.getRecipients().addAll(g.getCached());
        } else {
            FlagTeam t = g.getTeamPlayer(p);
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

    private String formatLobby(Player p, String msg) {
        return plugin.getLang().get(p, "chat.lobby").replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatTeam(Player p, FlagTeam team, String msg) {
        return plugin.getLang().get(p, "chat.team").replaceAll("<team>", team.getName()).replaceAll("<player>", p.getName()).replaceAll("<msg>", msg);
    }

    private String formatGame(Player p, FlagTeam team, String msg) {
        return plugin.getLang().get(p, "chat.global").replaceAll("<team>", team.getName()).replaceAll("<player>", p.getName()).replaceAll("<msg>", msg.replaceFirst("!", ""));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        Material material = b.getType();
        Location loc = b.getLocation();
        if (g.getMines().containsKey(loc)) {
            Mine mine = plugin.getFm().getMine(material);
            if (g.getCountdowns().containsKey(loc)) {
                e.setCancelled(true);
            } else {
                GamePlayer gp = g.getGamePlayer(p);
                gp.addCoins(mine.getCoins());
                p.sendMessage(plugin.getLang().get("messages.winCoins").replace("<coins>", String.valueOf(mine.getCoins())));
                g.getCountdowns().put(loc, new MineCountdown(mine.getKey(), loc, mine.getRegenerate()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        b.setType(Material.COBBLESTONE);
                    }
                }.runTaskLater(plugin, 1L);
            }
            return;
        }
        FlagTeam ft = g.getTeamByLoc(loc);
        FlagTeam yt = g.getTeamPlayer(p);
        if (ft == null) {
            if (!g.getPlaced().contains(loc)) {
                p.sendMessage(plugin.getLang().get("messages.onlyBreakPlaced"));
                e.setCancelled(true);
            } else {
                g.getPlaced().remove(loc);
            }
            return;
        }
        if (ft.equals(yt)) {
            e.setCancelled(true);
            p.sendMessage(plugin.getLang().get("messages.noBreakFlag"));
            return;
        }
        e.setCancelled(true);
        b.setType(Material.AIR);
        yt.setCapturing(p, ft.getColor());
        p.getInventory().setHelmet(ft.getFlagItem());
        ft.sendMessage(plugin.getLang().get("messages.breakFlag").replace("<color>", ft.getColor() + "").replace("<player>", p.getName()));
        yt.sendMessage(plugin.getLang().get("messages.stealFlag").replace("<team>", ft.getName()).replace("<color>", ft.getColor() + "").replace("<player>", p.getName()));
        ft.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f);
        yt.playSound(XSound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.0f);
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player d = (Player) e.getDamager();
                sendDamage(e, p, d);
            }
            if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
                Player d = (Player) ((Projectile) e.getDamager()).getShooter();
                sendDamage(e, p, d);
                addStats(e, p, d, plugin);
            }
        }
    }

    private void sendDamage(EntityDamageByEntityEvent e, Player p, Player d) {
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        FlagTeam tp = g.getTeamPlayer(p);
        FlagTeam td = g.getTeamPlayer(d);
        if (tp == null || td == null) {
            e.setCancelled(true);
            return;
        }
        if (tp.getId() == td.getId()) {
            e.setCancelled(true);
            return;
        }
        double damage = e.getFinalDamage();
        plugin.getTgm().setTag(d, p, damage);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        FlagTeam team = g.getTeamPlayer(p);
        if (team == null) return;
        e.setDeathMessage(null);
        team.removeLife();
        p.spigot().respawn();
        g.checkWin();
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        if (plugin.getTgm().hasTag(p)) {
            Player k = plugin.getTgm().getTagged(p).getLast();
            if (k != null) {
                CTWPlayer sk = plugin.getDb().getCTWPlayer(k);
                for (ObjectPotion op : plugin.getCm().getEffectsOnKill()) {
                    k.addPotionEffect(new PotionEffect(op.getPotion().parsePotionEffectType(), op.getDuration(), op.getLevel()));
                }
                GamePlayer gp = g.getGamePlayer().get(k);
                if (gp != null) {
                    gp.addCoins(plugin.getCm().getCoinsKill());
                    gp.addKill();
                }
                if (sk != null) {
                    plugin.getKem().execute(g, k, p, p.getLocation(), sk.getKillEffect());
                    plugin.getKsm().execute(k, p, sk.getKillSound());
                    sk.setKills(sk.getKills() + 1);
                }
                if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() == null) {
                    EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.CONTACT;
                    if (sk != null) {
                        plugin.getTm().execute(p, cause, g, sk.getTaunt());
                    } else {
                        plugin.getTm().execute(p, cause, g, 0);
                    }
                } else {
                    EntityDamageEvent.DamageCause cause = p.getLastDamageCause().getCause();
                    if (sk != null) {
                        plugin.getTm().execute(p, cause, g, sk.getTaunt());
                    } else {
                        plugin.getTm().execute(p, cause, g, 0);
                    }
                }
                plugin.getTgm().executeRewards(p, p.getMaxHealth());
                return;
            }
        }
        if (sw != null) {
            plugin.getTm().execute(p, g, sw.getTaunt());
        }
        if (!g.isState(State.FINISH)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    p.teleport(team.getSpawn());
                    GamePlayer gp = g.getGamePlayer(p);
                    respawn(p, g, team, gp);
                    gp.addDeath();
                }
            }.runTaskLater(plugin, 3L);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        FlagTeam team = g.getTeamPlayer(p);
        if (team == null) return;
        if (!team.getFlag().getWorld().getName().equals(p.getLocation().getWorld().getName())) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            if (g.getLobbyProtection() != null) {
                Squared s = g.getLobbyProtection();
                if (!s.isInCuboid(p)) {
                    p.teleport(g.getLobby());
                }
            }
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) {
            CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
            ctw.setWalked(ctw.getWalked() + 1);
        }
        if (team.getFlag().distance(p.getLocation()) < 2) {
            if (team.isCapturing(p)) {
                p.getInventory().setHelmet(null);
                ChatColor color = team.getCapturing(p);
                team.removeCapturing(p);
                FlagTeam ft = g.getTeamByColor(color);
                ft.setLifes(0);
                Block b = ft.getFlag().getBlock();
                b.setType(XMaterial.WHITE_BANNER.parseMaterial());
                Banner banner = (Banner) b.getState();
                banner.setBaseColor(Utils.getDyeColorByChatColor(ft.getColor()));
                banner.update(true, true);
                team.playSound(XSound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                team.sendTitle(plugin.getLang().get("titles.capturedFlag.title").replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), plugin.getLang().get("titles.capturedFlag.subtitle").replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), 0, 30, 0);
                ft.sendTitle(plugin.getLang().get("titles.otherCapturedFlag.title").replace("<player>", p.getName()).replace("<name>", team.getName()).replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<color>", team.getColor() + ""), plugin.getLang().get("titles.otherCapturedFlag.subtitle").replace("<player>", p.getName()).replace("<flag>", Utils.getFlagIcon(ft.getColor())).replace("<player>", p.getName()).replace("<name>", team.getName()).replace("<color>", team.getColor() + ""), 0, 30, 0);
                ft.playSound(XSound.ENTITY_WITHER_HURT, 1.0f, 1.0f);
                g.checkWin();
            }
        }
        if (to.getBlockY() < 10) {
            if (plugin.getCm().isInstaKillOnVoidFlag()) {
                p.damage(10000);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR) && e.getSlot() == 39) {
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().name().endsWith("BANNER")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        GameFlag g = plugin.getGm().getGameFlagByPlayer(p);
        if (g == null) return;
        if (g.isState(State.WAITING) || g.isState(State.STARTING)) {
            e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        if (g.isGracePeriod()) {
            boolean isVoid = isVoidBlock(b.getLocation().clone());
            e.setCancelled(isVoid);
            if (isVoid) {
                p.sendMessage(plugin.getLang().get("messages.noPlaceInGrace"));
                return;
            }
        }
        g.getPlaced().add(b.getLocation());
    }

    public void respawn(Player p, GameFlag gf, FlagTeam ft, GamePlayer gp) {
        p.getInventory().addItem(plugin.getIm().getPickaxe());
        GameEvent ge = gf.getLastEvent();
        if (ge != null) {
            ge.apply(p);
        }
        if (gp.getPickaxeKey() != null) {
            Upgrade upgrade1 = plugin.getUm().getUpgrade(gp.getPickaxeKey());
            UpgradeLevel u1 = upgrade1.getLevel(gp.getPiUpgrade());
            upgrade1.apply(r -> {
            }, p, ft, u1);
        }
        if (gp.getTeamHaste() != null) {
            Upgrade upgrade2 = plugin.getUm().getUpgrade(gp.getTeamHaste());
            UpgradeLevel u2 = upgrade2.getLevel(ft.getUpgradeHaste());
            upgrade2.apply(r -> {
            }, p, ft, u2);
        }
    }

    public boolean isVoidBlock(Location loc) {
        int airBlocks = 0;
        for (int i = 1; i < 30; i++) {
            if (!loc.clone().subtract(0, i, 0).getBlock().getType().equals(Material.AIR)) {
                break;
            }
            airBlocks++;
        }
        return airBlocks > 27;
    }

}