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

    private final List<Integer> slots = Arrays.asList(10, 16, 19, 20, 21, 22, 23, 24, 25, 28);
    private final List<Integer> shop = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
    private final UltraCTW plugin;

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

    public void createJoinMenu(Player p, Game game){
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.join.title"));
        if (game != null){
            inv.setItem(22, getGameItem(game));
        }
        //stats
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        ItemStack stats = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.join.stats.nameItem")).setLore(plugin.getLang().get("menus.join.stats.loreItem").replace("<kills>", String.valueOf(ctw.getKills())).replace("<assists>", String.valueOf(ctw.getAssists())).replace("<deaths>", String.valueOf(ctw.getDeaths())).replace("<coins>", Utils.format(ctw.getCoins())).replace("<woolStolen>", String.valueOf(ctw.getWoolStolen())).replace("<woolHolder>", String.valueOf(ctw.getKillsWoolHolder())).replace("<captured>", String.valueOf(ctw.getWoolCaptured())).replace("<wins>", String.valueOf(ctw.getWins())).replace("<loses>", String.valueOf(ctw.getLoses())).replace("<played>", String.valueOf(ctw.getPlayed())).replace("<sShots>", String.valueOf(ctw.getsShots())).replace("<bowkills>", String.valueOf(ctw.getBowKills()))).build();
        ItemMeta meta = stats.getItemMeta();
        stats.setItemMeta(meta);
        inv.setItem(36, stats);
        //close
        ItemStack close = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.join.close.nameItem")).setLore(plugin.getLang().get("menus.join.close.loreItem")).build();
        ItemMeta meta2 = close.getItemMeta();
        close.setItemMeta(meta2);
        inv.setItem(44, close);
        //settings
        ItemStack settings = new ItemUtils(XMaterial.ANVIL).setDisplayName(plugin.getLang().get("menus.join.settings.nameItem")).setLore(plugin.getLang().get("menus.join.settings.loreItem")).build();
        ItemMeta meta3 = settings.getItemMeta();
        settings.setItemMeta(meta3);
        inv.setItem(40, settings);
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

    private ItemStack getGameItem(Game game){
        ItemStack wool = new ItemUtils(XMaterial.WHITE_WOOL).build();
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(plugin.getLang().get("menus.join.wool.nameItem"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(plugin.getLang().get("menus.join.wool.map").replace("<map>", game.getName().replaceAll("_", " ")));
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