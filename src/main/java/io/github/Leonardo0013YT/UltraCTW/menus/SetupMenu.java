package io.github.Leonardo0013YT.UltraCTW.menus;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.setup.*;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class SetupMenu {

    private final int[] slots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 31};
    private final ArrayList<Integer> whites = new ArrayList<>(Arrays.asList(4, 5, 6, 36, 37, 38, 39, 40, 41, 42, 43, 44));
    private final ChatColor[] colors = {ChatColor.RED, ChatColor.BLUE, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.GREEN, ChatColor.GRAY, ChatColor.DARK_GRAY, ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, ChatColor.DARK_AQUA};
    private final UltraCTW plugin;

    public SetupMenu(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void createSetupKitMenu(Player p, KitSetup ks) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.kitsetup.title"));
        ItemStack name = new ItemUtils(XMaterial.OAK_SIGN).setDisplayName(plugin.getLang().get("menus.kitsetup.name.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.name.loreItem").replaceAll("<name>", ks.getName())).build();
        ItemStack levels = new ItemUtils(XMaterial.EXPERIENCE_BOTTLE).setDisplayName(plugin.getLang().get("menus.kitsetup.levels.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.levels.loreItem")).build();
        ItemStack permission = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.kitsetup.permission.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.permission.loreItem").replaceAll("<permission>", ks.getPermission())).build();
        ItemStack slot = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.kitsetup.slot.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.slot.loreItem").replaceAll("<slot>", String.valueOf(ks.getSlot()))).build();
        ItemStack page = new ItemUtils(XMaterial.MAP).setDisplayName(plugin.getLang().get("menus.kitsetup.page.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.page.loreItem").replaceAll("<page>", String.valueOf(ks.getPage()))).build();
        ItemStack banner = new ItemUtils(XMaterial.WHITE_BANNER).setDisplayName(plugin.getLang().get("menus.kitsetup.flag.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.flag.loreItem").replaceAll("<state>", Utils.parseBoolean(ks.isFlag()))).build();
        ItemStack save = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.kitsetup.save.nameItem")).setLore(plugin.getLang().get("menus.kitsetup.save.loreItem")).build();
        inv.setItem(4, name);
        inv.setItem(13, levels);
        inv.setItem(15, permission);
        inv.setItem(11, slot);
        inv.setItem(21, page);
        inv.setItem(22, banner);
        inv.setItem(40, save);
        p.openInventory(inv);
    }

    public void createSetupKitLevelsMenu(Player p, KitLevelSetup kls) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.kitlevelssetup.title"));
        ItemStack price = new ItemUtils(XMaterial.GOLD_NUGGET).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.price.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.price.loreItem").replaceAll("<price>", String.valueOf(kls.getPrice()))).build();
        ItemStack icon = new ItemUtils(XMaterial.DIAMOND_SWORD).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.icon.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.icon.loreItem")).build();
        ItemStack items = new ItemUtils(XMaterial.CHEST).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.items.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.items.loreItem")).build();
        ItemStack buy = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.buy.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.buy.loreItem").replaceAll("<status>", Utils.parseBoolean(kls.isBuy()))).build();
        ItemStack slot = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.slot.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.slot.loreItem").replaceAll("<slot>", String.valueOf(kls.getSlot()))).build();
        ItemStack page = new ItemUtils(XMaterial.MAP).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.page.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.page.loreItem").replaceAll("<page>", String.valueOf(kls.getPage()))).build();
        ItemStack save = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.kitlevelssetup.save.nameItem")).setLore(plugin.getLang().get("menus.kitlevelssetup.save.loreItem")).build();
        inv.setItem(11, icon);
        inv.setItem(13, items);
        inv.setItem(15, buy);
        inv.setItem(20, price);
        inv.setItem(22, slot);
        inv.setItem(24, page);
        inv.setItem(40, save);
        p.openInventory(inv);
    }

    public void createSetupKitItemsMenu(Player p, KitLevelSetup kls) {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getLang().get("menus.kititems.title"));
        ItemStack white = new ItemUtils(XMaterial.WHITE_STAINED_GLASS_PANE).setDisplayName("§7").setLore("§7").build();
        ItemStack helmet = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cHelmet").setLore("§7Click to change!").build();
        ItemStack chestplate = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cChestplate").setLore("§7Click to change!").build();
        ItemStack leggings = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cLeggings").setLore("§7Click to change!").build();
        ItemStack boots = new ItemUtils(XMaterial.BARRIER).setDisplayName("§cBoots").setLore("§7Click to change!").build();
        ItemStack analize = new ItemUtils(XMaterial.ENDER_EYE).setDisplayName(plugin.getLang().get("menus.kititems.analize.nameItem")).setLore(plugin.getLang().get("menus.kititems.analize.loreItem")).build();
        ItemStack save = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("menus.kititems.save.nameItem")).setLore(plugin.getLang().get("menus.kititems.save.loreItem")).build();
        for (int i : whites) {
            inv.setItem(i, white);
        }
        if (kls.getArmor()[0] == null) {
            inv.setItem(0, helmet);
        } else {
            inv.setItem(0, kls.getArmor()[0]);
        }
        if (kls.getArmor()[1] == null) {
            inv.setItem(1, chestplate);
        } else {
            inv.setItem(1, kls.getArmor()[1]);
        }
        if (kls.getArmor()[2] == null) {
            inv.setItem(2, leggings);
        } else {
            inv.setItem(2, kls.getArmor()[2]);
        }
        if (kls.getArmor()[3] == null) {
            inv.setItem(3, boots);
        } else {
            inv.setItem(3, kls.getArmor()[3]);
        }
        for (int i = 0; i < 36; i++) {
            if (i < 9) {
                inv.setItem(45 + i, kls.getInv()[i]);
            } else {
                inv.setItem(i, kls.getInv()[i]);
            }
        }
        inv.setItem(7, analize);
        inv.setItem(8, save);
        p.openInventory(inv);
    }

    public void createSetupSelectMenu(Player p, ArenaSetup as) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.teamsColor.title"));
        int i = 0;
        int max = as.getAmountTeams();
        int amount = 0;
        for (ChatColor color : colors) {
            ItemStack wool = NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(color)).setDisplayName(plugin.getLang().get("menus.teamsColor.color.nameItem").replaceAll("<name>", plugin.getLang().get("teams." + color.name().toLowerCase())).replaceAll("<color>", "" + color)).setLore(plugin.getLang().get("menus.teamsColor.color.loreItem")).build(), color.name(), "SETUP", "TEAM", "COLOR");
            inv.setItem(slots[i], wool);
            amount++;
            i++;
            if (amount >= max) {
                break;
            }
        }
        ItemStack back = new ItemUtils(XMaterial.BARRIER).setDisplayName(plugin.getLang().get("menus.back.nameItem")).setLore(plugin.getLang().get("menus.back.loreItem").replaceAll("<menu>", "Main Setup Menu")).build();
        inv.setItem(40, back);
        p.openInventory(inv);
    }

    public void createSetupColorTeam(Player p, ArenaSetup as) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.teamColor.title"));
        int i = 0;
        for (ChatColor xm : as.getAvailableColors()) {
            inv.setItem(slots[i], NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(xm)).setDisplayName(xm + "Team " + plugin.getLang().get("teams." + xm.name().toLowerCase())).build(), xm.name(), "SELECT", "TEAM", "COLORS"));
            i++;
        }
        p.openInventory(inv);
    }

    public void createSetupSpawnerColor(Player p, TeamSetup ts) {
        Inventory inv = Bukkit.createInventory(null, 45, plugin.getLang().get("menus.spawners.title"));
        int i = 0;
        for (ChatColor xm : ts.getColors()) {
            inv.setItem(slots[i], NBTEditor.set(new ItemUtils(Utils.getXMaterialByColor(xm)).setDisplayName(xm + "Spawner " + plugin.getLang().get("teams." + xm.name().toLowerCase())).build(), xm.name(), "SELECT", "TEAM", "SPAWNER"));
            i++;
        }
        p.openInventory(inv);
    }

    public ArrayList<Integer> getWhites() {
        return whites;
    }
}