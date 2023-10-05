package io.github.Leonardo0013YT.UltraCTW.utils;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Multiplier;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    private static final DecimalFormat df = new DecimalFormat("##.#");
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final static Random random = new Random();
    private static final UltraCTW plugin = UltraCTW.get();
    private static final ItemStack[] gifs = {NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNlZjlhYTE0ZTg4NDc3M2VhYzEzNGE0ZWU4OTcyMDYzZjQ2NmRlNjc4MzYzY2Y3YjFhMjFhODViNyJ9fX0="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI2NzMwZGU3ZTViOTQxZWZjNmU4Y2JhZjU3NTVmOTQyMWEyMGRlODcxNzU5NjgyY2Q4ODhjYzRhODEyODIifX19"),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA4Y2U3ZGViYTU2YjcyNmE4MzJiNjExMTVjYTE2MzM2MTM1OWMzMDQzNGY3ZDVlM2MzZmFhNmZlNDA1MiJ9fX0="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjNzVhMDViMzQ0ZWEwNDM4NjM5NzRjMTgwYmE4MTdhZWE2ODY3OGNiZWE1ZTRiYTM5NWY3NGQ0ODAzZDFkIn19fQ=="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzU0MTlmY2U1MDZhNDk1MzQzYTFkMzY4YTcxZDIyNDEzZjA4YzZkNjdjYjk1MWQ2NTZjZDAzZjgwYjRkM2QzIn19fQ=="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzYThmZDA4NTI5Nzc0NDRkOWZkNzc5N2NhYzA3YjhkMzk0OGFkZGM0M2YwYmI1Y2UyNWFlNzJkOTVkYyJ9fX0="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNlNThlYTdmMzExM2NhZWNkMmIzYTZmMjdhZjUzYjljYzljZmVkN2IwNDNiYTMzNGI1MTY4ZjEzOTFkOSJ9fX0="),
            NBTEditor.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19")};

    public static String format(double value) {
        return df.format(value);
    }

    public static String getProgressBar(int current, int max) {
        float percent = (float) current / max;
        double por = percent * 100;
        return new DecimalFormat("####.#").format(por);
    }

    public static String getProgressBar(int current, int max, int totalBars) {
        UltraCTW plugin = UltraCTW.get();
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        int leftOver = (totalBars - progressBars);
        StringBuilder sb = new StringBuilder();
        StringBuilder in = new StringBuilder();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < progressBars; i++) {
            in.append(plugin.getLang().get(null, "progressBar.symbol"));
        }
        for (int i = 0; i < leftOver; i++) {
            out.append(plugin.getLang().get(null, "progressBar.symbol"));
        }
        sb.append(plugin.getLang().get(null, "progressBar.in"));
        sb.append(in);
        sb.append(plugin.getLang().get(null, "progressBar.out"));
        sb.append(out);
        double por = percent * 100;
        String p = new DecimalFormat("####.#").format(por);
        return plugin.getLang().get(null, "progressBar.finish").replaceAll("<progress>", sb.toString()).replaceAll("<percent>", p);
    }

    public static String convertTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String formattedTime = "";
        if (hours > 0) {
            if (hours < 10)
                formattedTime += "0";
            formattedTime += hours + ":";
        }
        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";
        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;
        return formattedTime;
    }

    public static String getWoolsString(Team team) {
        if (team == null) return "";
        StringBuilder wools = new StringBuilder();
        for (ChatColor c : team.getColors()) {
            boolean inInProgress = team.isInProgress(c);
            boolean isCaptured = team.isCaptured(c);
            if (isCaptured) {
                wools.append(c).append(plugin.getLang().get("scoreboards.wools.captured")).append(" ");
            } else if (inInProgress) {
                wools.append(c).append(plugin.getLang().get("scoreboards.wools.inProcess")).append(" ");
            } else {
                wools.append(c).append(plugin.getLang().get("scoreboards.wools.noCaptured")).append(" ");
            }
        }
        return wools.toString();
    }

    public static String getWoolsTag(Team team){
        if (team == null) return "";
        StringBuilder wools = new StringBuilder();
        for (ChatColor c : team.getColors()) {
            boolean inInProgress = team.isInProgress(c);
            boolean isCaptured = team.isCaptured(c);
            if (isCaptured) {
                wools.append(c).append("⬛").append(" ");
            } else if (inInProgress) {
                wools.append(c).append("░").append(" ");
            } else {
                wools.append(c).append(" ").append(" ");
            }
        }
        return wools.toString();

    }

    public static String formatDouble(double d) {
        return df.format(d);
    }

    public static void setCleanPlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (PotionEffect e : p.getActivePotionEffects()) {
            p.removePotionEffect(e.getType());
        }
        p.setFlying(false);
        p.setAllowFlight(false);
        p.setLevel(0);
        p.setExp(0);
        p.setNoDamageTicks(0);
        p.setFireTicks(0);
        p.setWalkSpeed(0.2f);
        p.setFlySpeed(0.1f);
        p.setFoodLevel(20);
        p.setMaxHealth(20.0D);
        p.setHealth(20.0D);
        p.setGameMode(GameMode.SURVIVAL);
    }

    public static void updateSB() {
        if (plugin.getCm().getMainLobby() == null) return;
        plugin.getCm().getMainLobby().getWorld().getPlayers().forEach(p -> plugin.getSb().update(p));
    }

    public static void updateSB(Game game) {
        game.getCached().forEach(p -> plugin.getSb().update(p));
    }

    public static void updateSB(Player p) {
        plugin.getSb().update(p);
    }

    public static String parseBoolean(boolean bool) {
        return (bool) ? plugin.getLang().get(null, "activated") : plugin.getLang().get(null, "deactivated");
    }

    public static boolean existsFile(String schematic) {
        File file = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics", schematic);
        return file.exists();
    }

    public static String getFormatedLocation(Location loc) {
        if (loc == null) {
            return "§cNot set!";
        }
        return loc.getWorld().getName() + ", " + df.format(loc.getX()) + ", " + df.format(loc.getY()) + ", " + df.format(loc.getZ());
    }

    public static Location getStringLocation(String location) {
        if (location == null) return null;
        String[] l = location.split(";");
        if (l.length < 6) return null;
        World world = Bukkit.getWorld(l[0]);
        double x = Double.parseDouble(l[1]);
        double y = Double.parseDouble(l[2]);
        double z = Double.parseDouble(l[3]);
        float yaw = Float.parseFloat(l[4]);
        float pitch = Float.parseFloat(l[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Team getMinorPlayersTeam(Game game) {
        Team t = null;
        int menor = 100000;
        for (Team tt : game.getTeams().values()) {
            if (tt.getTeamSize() <= menor) {
                t = tt;
                menor = tt.getTeamSize();
            }
        }
        return t;
    }

    public static Team getMajorPlayersTeam(Game game) {
        Team t = null;
        int mayor = 0;
        for (Team tt : game.getTeams().values()) {
            if (tt.getTeamSize() >= mayor) {
                t = tt;
                mayor = tt.getTeamSize();
            }
        }
        return t;
    }

    public static String getLocationString(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
    }

    public static Color getColorByChatColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return Color.AQUA;
        }
        if (color.equals(ChatColor.DARK_BLUE)) {
            return Color.NAVY;
        }
        if (color.equals(ChatColor.BLACK)) {
            return Color.BLACK;
        }
        if (color.equals(ChatColor.BLUE)) {
            return Color.BLUE;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return Color.TEAL;
        }
        if (color.equals(ChatColor.RED) || color.equals(ChatColor.DARK_RED)) {
            return Color.RED;
        }
        if (color.equals(ChatColor.DARK_GRAY) || color.equals(ChatColor.GRAY)) {
            return Color.GRAY;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return Color.GREEN;
        }
        if (color.equals(ChatColor.GREEN)) {
            return Color.LIME;
        }
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return Color.FUCHSIA;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return Color.PURPLE;
        }
        if (color.equals(ChatColor.WHITE)) {
            return Color.WHITE;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return Color.YELLOW;
        }
        return null;
    }

    public static String getWoolColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA))
            return plugin.getLang().get("woolname.aqua");
        if (color.equals(ChatColor.DARK_AQUA))
            return plugin.getLang().get("woolname.dark_aqua");
        if (color.equals(ChatColor.BLUE))
            return plugin.getLang().get("woolname.blue");
        if (color.equals(ChatColor.DARK_BLUE))
            return plugin.getLang().get("woolname.dark_blue");
        if (color.equals(ChatColor.RED))
            return plugin.getLang().get("woolname.red");
        if (color.equals(ChatColor.DARK_RED))
            return plugin.getLang().get("woolname.dark_red");
        if (color.equals(ChatColor.GRAY))
            return plugin.getLang().get("woolname.gray");
        if (color.equals(ChatColor.DARK_GRAY))
            return plugin.getLang().get("woolname.dark_gray");
        if (color.equals(ChatColor.GREEN))
            return plugin.getLang().get("woolname.green");
        if (color.equals(ChatColor.DARK_GREEN))
            return plugin.getLang().get("woolname.dark_green");
        if (color.equals(ChatColor.LIGHT_PURPLE))
            return plugin.getLang().get("woolname.light_purple");
        if (color.equals(ChatColor.DARK_PURPLE))
            return plugin.getLang().get("woolname.dark_purple");
        if (color.equals(ChatColor.BLACK))
            return plugin.getLang().get("woolname.black");
        if (color.equals(ChatColor.WHITE))
            return plugin.getLang().get("woolname.white");
        if (color.equals(ChatColor.YELLOW))
            return plugin.getLang().get("woolname.yellow");
        if (color.equals(ChatColor.GOLD))
            return plugin.getLang().get("woolname.gold");
        return "";
    }

    public static XMaterial getXMaterialByColor(ChatColor color) {
        if (color.equals(ChatColor.AQUA)) {
            return XMaterial.LIGHT_BLUE_WOOL;
        }
        if (color.equals(ChatColor.BLACK)) {
            return XMaterial.BLACK_WOOL;
        }
        if (color.equals(ChatColor.BLUE)) {
            return XMaterial.BLUE_WOOL;
        }
        if (color.equals(ChatColor.DARK_AQUA)) {
            return XMaterial.CYAN_WOOL;
        }
        if (color.equals(ChatColor.RED)) {
            return XMaterial.RED_WOOL;
        }
        if (color.equals(ChatColor.GOLD)) {
            return XMaterial.ORANGE_WOOL;
        }
        if (color.equals(ChatColor.DARK_GRAY)) {
            return XMaterial.GRAY_WOOL;
        }
        if (color.equals(ChatColor.GRAY)) {
            return XMaterial.LIGHT_GRAY_WOOL;
        }
        if (color.equals(ChatColor.DARK_GREEN)) {
            return XMaterial.GREEN_WOOL;
        }
        if (color.equals(ChatColor.GREEN)) {
            return XMaterial.LIME_WOOL;
        }
        if (color.equals(ChatColor.LIGHT_PURPLE)) {
            return XMaterial.PINK_WOOL;
        }
        if (color.equals(ChatColor.DARK_PURPLE)) {
            return XMaterial.PURPLE_WOOL;
        }
        if (color.equals(ChatColor.WHITE)) {
            return XMaterial.WHITE_WOOL;
        }
        if (color.equals(ChatColor.YELLOW)) {
            return XMaterial.YELLOW_WOOL;
        }
        return XMaterial.AIR;
    }

    public static ChatColor getColorByXMaterial(XMaterial material) {
        if (material.equals(XMaterial.LIGHT_BLUE_WOOL)) {
            return ChatColor.AQUA;
        }
        if (material.equals(XMaterial.BLACK_WOOL)) {
            return ChatColor.BLACK;
        }
        if (material.equals(XMaterial.BLUE_WOOL)) {
            return ChatColor.BLUE;
        }
        if (material.equals(XMaterial.CYAN_WOOL)) {
            return ChatColor.DARK_AQUA;
        }
        if (material.equals(XMaterial.RED_WOOL)) {
            return ChatColor.RED;
        }
        if (material.equals(XMaterial.ORANGE_WOOL)) {
            return ChatColor.GOLD;
        }
        if (material.equals(XMaterial.GRAY_WOOL)) {
            return ChatColor.DARK_GRAY;
        }
        if (material.equals(XMaterial.LIGHT_GRAY_WOOL)) {
            return ChatColor.GRAY;
        }
        if (material.equals(XMaterial.GREEN_WOOL)) {
            return ChatColor.DARK_GREEN;
        }
        if (material.equals(XMaterial.LIME_WOOL)) {
            return ChatColor.GREEN;
        }
        if (material.equals(XMaterial.PINK_WOOL)) {
            return ChatColor.LIGHT_PURPLE;
        }
        if (material.equals(XMaterial.PURPLE_WOOL)) {
            return ChatColor.DARK_PURPLE;
        }
        if (material.equals(XMaterial.WHITE_WOOL)) {
            return ChatColor.WHITE;
        }
        if (material.equals(XMaterial.YELLOW_WOOL)) {
            return ChatColor.YELLOW;
        }
        return ChatColor.WHITE;
    }

    public static List<Block> getBlocksInRadius(Location location, int radius, boolean hollow) {
        List<Block> blocks = new ArrayList<>();
        int bX = location.getBlockX(), bY = location.getBlockY(), bZ = location.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; x++)
            for (int y = bY - radius; y <= bY + radius; y++)
                for (int z = bZ - radius; z <= bZ + radius; z++) {
                    double distance = ((bX - x) * (bX - x) + (bY - y) * (bY - y) + (bZ - z) * (bZ - z));
                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        Location l = new Location(location.getWorld(), x, y, z);
                        if (l.getBlock().getType() != Material.BARRIER)
                            blocks.add(l.getBlock());
                    }
                }
        return blocks;
    }

    public static ItemStack[] getGifs() {
        return gifs;
    }

    public static void firework(Location loc) {
        Firework fa = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fam = fa.getFireworkMeta();
        FireworkEffect.Type tipo = FireworkEffect.Type.values()[random.nextInt(4)];
        Color c1 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        Color c2 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        FireworkEffect ef = FireworkEffect.builder().withColor(c1).withFade(c2).with(tipo).build();
        fam.addEffect(ef);
        fam.setPower(0);
        fa.setFireworkMeta(fam);
    }

    public static String getDate() {
        return sdf.format(new Date());
    }

    public static void sendWinRewards(UltraCTW plugin, Player on){
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(on);
        if (ctw == null) return;
        int gxp = plugin.getCm().getXpWin();
        int gcoins = plugin.getCm().getCoinsWin();
        ctw.setXp(ctw.getXp() + (int) plugin.getMm().getPlayerMultiplier(on, "XP", gxp));
        ctw.addCoins((int) plugin.getMm().getPlayerMultiplier(on, "COINS", gcoins));
        Multiplier mx = plugin.getMm().getServerMultiplier("XP");
        Multiplier mc = plugin.getMm().getServerMultiplier("COINS");
        for (String s : plugin.getLang().get(on, "messages.summonary").split("\\n")) {
            if (s.contains("<center>")) {
                on.sendMessage(CenterMessage.getCenteredMessage(s.replaceAll("<center>", "")));
            } else if (s.contains("<xpGame>")) {
                on.sendMessage(plugin.getLang().get(on, "messages.xpGame").replaceAll("<amount>", String.valueOf(gxp)));
            } else if (s.contains("<coinsGame>")) {
                on.sendMessage(plugin.getLang().get(on, "messages.coinsGame").replaceAll("<amount>", String.valueOf(gcoins)));
            } else if (s.contains("<multiplierXP>")) {
                if (mx != null) {
                    double amount = (mx.getAmount() * gxp) - gxp;
                    on.sendMessage(plugin.getLang().get(on, "messages.xp").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mx.getName()).replaceAll("<value>", "" + mx.getAmount()));
                    ctw.setXp((int) amount);
                }
            } else if (s.contains("<multiplierXPYou>")) {
                if (plugin.getMm().getPlayerMultiplier(on, "XP", gxp) > 1) {
                    String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(on, "XP"));
                    String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(on, "XP", gxp));
                    on.sendMessage(plugin.getLang().get(on, "messages.xpYou").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<xp>", xp + gxp));
                }
            } else if (s.contains("<multiplierCoins>")) {
                if (mc != null) {
                    double amount = (mc.getAmount() * gcoins) - gcoins;
                    on.sendMessage(plugin.getLang().get(on, "messages.coins").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mc.getName()).replaceAll("<value>", "" + mc.getAmount()));
                    ctw.addCoins((int) amount);
                }
            } else if (s.contains("<multiplierCoinsYou>")) {
                if (plugin.getMm().getPlayerMultiplier(on, "COINS", gcoins) > 1) {
                    String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(on, "COINS"));
                    String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(on, "COINS", gcoins));
                    on.sendMessage(plugin.getLang().get(on, "messages.coinsYou").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<coins>", xp + gcoins));
                }
            } else {
                int totalxp = gxp + (int) plugin.getMm().getPlayerMultiplier(on ,"XP", gxp);
                int totalcoins = gcoins + (int) plugin.getMm().getPlayerMultiplier(on, "COINS", gcoins);
                on.sendMessage(s.replaceAll("<xp>", String.valueOf(totalxp))
                        .replaceAll("<coins>", String.valueOf(totalcoins)));
            }
        }
    }

    public static void sendKillRewards(UltraCTW plugin, Player p){
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        int gxp = plugin.getCm().getXpKill();
        int gcoins = (int) plugin.getCm().getCoinsKill();
        ctw.setXp(ctw.getXp() + (int) plugin.getMm().getPlayerMultiplier(p, "XP", gxp));
        ctw.addCoins((int) plugin.getMm().getPlayerMultiplier(p, "COINS", gcoins));
        Multiplier mx = plugin.getMm().getServerMultiplier("XP");
        Multiplier mc = plugin.getMm().getServerMultiplier("COINS");
        (new BukkitRunnable() {
            public void run() {
                for (String s : plugin.getLang().get(p, "messages.killmultiplier").split("\\n")) {
                    if (s.contains("<center>")) {
                        p.sendMessage(CenterMessage.getCenteredMessage(s.replaceAll("<center>", "")));
                    } else if (s.contains("<xpGame>")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.xpGameKill").replaceAll("<amount>", String.valueOf(gxp)));
                    } else if (s.contains("<coinsGame>")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.coinsGameKill").replaceAll("<amount>", String.valueOf(gcoins)));
                    } else if (s.contains("<multiplierXP>")) {
                        if (mx != null) {
                            double amount = (mx.getAmount() * gxp) - gxp;
                            p.sendMessage(plugin.getLang().get(p, "messages.xpKill").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mx.getName()).replaceAll("<value>", "" + mx.getAmount()));
                            ctw.setXp((int) amount);
                        }
                    } else if (s.contains("<multiplierXPYou>")) {
                        if (plugin.getMm().getPlayerMultiplier(p, "XP", gxp) > 1) {
                            String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(p, "XP"));
                            String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(p, "XP", gxp));
                            p.sendMessage(plugin.getLang().get(p, "messages.xpYouKill").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<xp>", xp + gxp));
                        }
                    } else if (s.contains("<multiplierCoins>")) {
                        if (mc != null) {
                            double amount = (mc.getAmount() * gcoins) - gcoins;
                            p.sendMessage(plugin.getLang().get(p, "messages.coinsKill").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mc.getName()).replaceAll("<value>", "" + mc.getAmount()));
                            ctw.addCoins((int) amount);
                        }
                    } else if (s.contains("<multiplierCoinsYou>")) {
                        if (plugin.getMm().getPlayerMultiplier(p, "COINS", gcoins) > 1) {
                            String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(p, "COINS"));
                            String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(p, "COINS", gcoins));
                            p.sendMessage(plugin.getLang().get(p, "messages.coinsYouKill").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<coins>", xp + gcoins));
                        }
                    }
                }
            }
        }).runTaskLater(plugin, 1L);
    }

    public static void sendLoseRewards(UltraCTW plugin, Player l){
        CTWPlayer lose = plugin.getDb().getCTWPlayer(l);
        int gxp = 10;
        int gcoins = 10;
        lose.setXp(lose.getXp() + (int) plugin.getMm().getPlayerMultiplier(l, "XP", gxp));
        lose.addCoins((int) plugin.getMm().getPlayerMultiplier(l, "COINS", gcoins));
        Multiplier mx = plugin.getMm().getServerMultiplier("XP");
        Multiplier mc = plugin.getMm().getServerMultiplier("COINS");
        for (String s : plugin.getLang().get(l, "messages.summonarylose").split("\\n")) {
            if (s.contains("<center>")) {
                l.sendMessage(CenterMessage.getCenteredMessage(s.replaceAll("<center>", "")));
            } else if (s.contains("<xpGame>")) {
                l.sendMessage(plugin.getLang().get(l, "messages.xpGameLose").replaceAll("<amount>", String.valueOf(gxp)));
            } else if (s.contains("<coinsGame>")) {
                l.sendMessage(plugin.getLang().get(l, "messages.coinsGameLose").replaceAll("<amount>", String.valueOf(gcoins)));
            } else if (s.contains("<multiplierXP>")) {
                if (mx != null) {
                    double amount = (mx.getAmount() * gxp) - gxp;
                    l.sendMessage(plugin.getLang().get(l, "messages.xpLose").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mx.getName()).replaceAll("<value>", "" + mx.getAmount()));
                    lose.setXp((int) amount);
                }
            } else if (s.contains("<multiplierXPYou>")) {
                if (plugin.getMm().getPlayerMultiplier(l, "XP", gxp) > 1) {
                    String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(l, "XP"));
                    String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(l, "XP", gxp));
                    l.sendMessage(plugin.getLang().get(l, "messages.xpYouLose").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<xp>", xp + gxp));
                }
            } else if (s.contains("<multiplierCoins>")) {
                if (mc != null) {
                    double amount = (mc.getAmount() * gcoins) - gcoins;
                    l.sendMessage(plugin.getLang().get(l, "messages.coinsLose").replaceAll("<amount>", String.valueOf(amount)).replace("<player>", mc.getName()).replaceAll("<value>", "" + mc.getAmount()));
                    lose.addCoins((int) amount);
                }
            } else if (s.contains("<multiplierCoinsYou>")) {
                if (plugin.getMm().getPlayerMultiplier(l, "COINS", gcoins) > 1) {
                    String mxp = String.valueOf(plugin.getMm().getPlayerMultiplier(l, "COINS"));
                    String xp = String.valueOf(plugin.getMm().getPlayerMultiplier(l, "COINS", gcoins));
                    l.sendMessage(plugin.getLang().get(l, "messages.coinsYouLose").replaceAll("<amount>", xp).replaceAll("<value>", mxp).replaceAll("<coins>", xp + gcoins));
                }
            } else {
                int totalxp = gxp + (int) plugin.getMm().getPlayerMultiplier(l,"XP", gxp);
                int totalcoins = gcoins + (int) plugin.getMm().getPlayerMultiplier(l, "COINS", gcoins);
                l.sendMessage(s.replaceAll("<xpLose>", String.valueOf(totalxp))
                        .replaceAll("<coinsLose>", String.valueOf(totalcoins)));
            }
        }
    }
}