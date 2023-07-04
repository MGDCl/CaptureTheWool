package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.ShopItem;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameMenu {

    private List<Integer> slots = Arrays.asList(10, 16, 19, 20, 21, 22, 23, 24, 25, 28);;
    private List<Integer> shop = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
    private UltraCTW plugin;

    public GameMenu(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void createTeamsMenu(Player p, Game game) {
        Inventory inv = Bukkit.createInventory(null, 27, plugin.getLang().get("menus.teams.title"));
        ItemStack random = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.teams.random.nameItem")).setLore(plugin.getLang().get("menus.teams.random.loreItem")).build();
        int i = 0;
        inv.setItem(13, random);
        for (Team t : game.getTeams().values()) {
            inv.setItem(slots.get(i), getTeamItem(t));
            i++;
        }
        p.openInventory(inv);
    }

    public void createJoinMenu(Player p, Game game){//TODO add BungeeMode
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.join.title"));
        inv.setItem(22, getGameItem(game));

        ItemStack stats = new ItemStack(339, 1);
        ItemMeta meta = stats.getItemMeta();
        meta.setDisplayName(plugin.getLang().get("menus.join.stats.nameItem"));
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        String lore = plugin.getLang().get("menus.join.stats.loreItem").replaceAll("<kills>", String.valueOf(ctw.getKills())).replaceAll("<deaths>", String.valueOf(ctw.getDeaths())).replaceAll("<coins>", String.valueOf(ctw.getCoins())).replaceAll("<captured>", String.valueOf(ctw.getWoolCaptured())).replaceAll("<level>", String.valueOf(ctw.getLevel())).replaceAll("<wins>", String.valueOf(ctw.getWins())).replaceAll("<xp>", String.valueOf(ctw.getXp())).replaceAll("<played>", String.valueOf(ctw.getPlayed())).replaceAll("<bowkills>", String.valueOf(ctw.getBowKills())).replaceAll("<bowdistance>", String.valueOf(ctw.getBowKillDistance())).replaceAll("<bowmaxdistance>", String.valueOf(ctw.getMaxBowDistance()));
        meta.setLore(lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split("\\n")));
        stats.setItemMeta(meta);
        inv.setItem(36, stats);

        ItemStack close = new ItemStack(166, 1);
        ItemMeta meta2 = close.getItemMeta();
        meta2.setDisplayName(plugin.getLang().get("menus.join.close.nameItem"));
        String lore2 = plugin.getLang().get("menus.join.close.loreItem");
        meta2.setLore(lore2.isEmpty() ? new ArrayList<>() : Arrays.asList(lore2.split("\\n")));
        close.setItemMeta(meta2);
        inv.setItem(44, close);
        p.openInventory(inv);
    }

    public void createShopMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.shop.title"));
        int i = 0;
        for (int id : plugin.getShm().getItems().keySet()) {
            ShopItem si = plugin.getShm().getItems().get(id);
            inv.setItem(shop.get(i), NBTEditor.set(si.getItem(), id, "SHOP", "ID", "BUY"));
            i++;
        }
        p.openInventory(inv);
    }

    private ItemStack getTeamItem(Team team) {
        ItemStack leather = NBTEditor.set(new ItemStack(Material.LEATHER_CHESTPLATE, 1), team.getColor().name(), "SELECTOR", "TEAM", "COLOR");
        LeatherArmorMeta bm = (LeatherArmorMeta) leather.getItemMeta();
        bm.setColor(Utils.getColorByChatColor(team.getColor()));
        bm.setDisplayName(plugin.getLang().get("menus.teams.team.nameItem").replaceAll("<team>", team.getName()));
        String lore = plugin.getLang().get("menus.teams.team.loreItem").replaceAll("<players>", String.valueOf(team.getTeamSize()));
        bm.setLore(lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split("\\n")));
        leather.setItemMeta(bm);
        return leather;
    }

    private ItemStack getGameItem(Game game){//TODO SAME
        ItemStack wool = new ItemStack(35, 1);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(plugin.getLang().get("menus.join.wool.nameItem"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(plugin.getLang().get("menus.join.wool.map").replace("<map>", game.getName()));
        lore.add("");
        if (game.isState(State.WAITING)){
            if (wool.getDurability() != (short) 0){
                wool.setDurability((short) 0);
            }
            lore.add(plugin.getLang().get("menus.join.wool.waiting"));
        }
        else if (game.isState(State.RESTARTING)) {
            if (wool.getDurability() != (short) 7){
                wool.setDurability((short) 7);
            }
            lore.add(plugin.getLang().get("menus.join.wool.restarting"));
        }
        else if (game.isState(State.FINISH)) {
            if (wool.getDurability() != (short) 14){
                wool.setDurability((short) 14);
            }
            lore.add(plugin.getLang().get("menus.join.wool.finish"));
        }
        else if (game.isState(State.STARTING)) {
            if (wool.getDurability() != (short) 4){
                wool.setDurability((short) 4);
            }
            lore.add(plugin.getLang().get("menus.join.wool.starting"));
        }
        else if (game.isState(State.GAME)) {
            if (wool.getDurability() != (short) 5){
                wool.setDurability((short) 5);
            }
            lore.add(plugin.getLang().get("menus.join.wool.ingame"));
        }
        lore.add("");
        lore.add(plugin.getLang().get("menus.join.wool.players").replaceAll("<size>", game.getPlayers().size() + "").replaceAll("<max>", game.getMax() + ""));
        lore.add("");
        lore.add(plugin.getLang().get("menus.join.wool.lore"));
        meta.setLore(lore);
        wool.setItemMeta(meta);
        return wool;
    }

}