package io.github.Leonardo0013YT.UltraCTW.inventories.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetupFlagArenaMenu implements UltraInventory {

    private String title;
    private Map<Integer, ItemStack> config = new HashMap<>();
    private Map<Integer, ItemStack> contents = new HashMap<>();
    private int rows = 6;
    private String name;
    private UltraCTW plugin;

    public SetupFlagArenaMenu(UltraCTW plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.title = plugin.getLang().get("menus." + name + ".title");
        if (plugin.getMenus().isSet("menus." + name)) {
            this.rows = plugin.getMenus().getInt("menus." + name + ".rows");
            Map<Integer, ItemStack> contents = new HashMap<>();
            Map<Integer, ItemStack> config = new HashMap<>();
            ConfigurationSection conf = plugin.getMenus().getConfig().getConfigurationSection("menus." + name + ".items");
            for (String c : conf.getKeys(false)) {
                int slot = Integer.parseInt(c);
                ItemStack litem = plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c);
                ItemStack item = ItemBuilder.parse(plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c).clone(),
                        new String[]{"{NAME}", plugin.getLang().get("menus.flag.name.nameItem"), plugin.getLang().get("menus.flag.name.loreItem")},
                        new String[]{"{PROTECTION}", plugin.getLang().get("menus.flag.protection.nameItem"), plugin.getLang().get("menus.flag.protection.loreItem")},
                        new String[]{"{NPCUPGRADE}", plugin.getLang().get("menus.flag.npcUpgrade.nameItem"), plugin.getLang().get("menus.flag.npcUpgrade.loreItem")},
                        new String[]{"{NPCBUFF}", plugin.getLang().get("menus.flag.npcBuff.nameItem"), plugin.getLang().get("menus.flag.npcBuff.loreItem")},
                        new String[]{"{NPCSHOP}", plugin.getLang().get("menus.flag.npcShop.nameItem"), plugin.getLang().get("menus.flag.npcShop.loreItem")},
                        new String[]{"{NPCKITS}", plugin.getLang().get("menus.flag.npcKits.nameItem"), plugin.getLang().get("menus.flag.npcKits.loreItem")},
                        new String[]{"{SCHEMATIC}", plugin.getLang().get("menus.flag.schematic.nameItem"), plugin.getLang().get("menus.flag.schematic.loreItem")},
                        new String[]{"{MIN}", plugin.getLang().get("menus.flag.min.nameItem"), plugin.getLang().get("menus.flag.min.loreItem")},
                        new String[]{"{TEAMS}", plugin.getLang().get("menus.flag.teams.nameItem"), plugin.getLang().get("menus.flag.teams.loreItem")},
                        new String[]{"{TEAMSIZE}", plugin.getLang().get("menus.flag.teamSize.nameItem"), plugin.getLang().get("menus.flag.teamSize.loreItem")},
                        new String[]{"{LOBBY}", plugin.getLang().get("menus.flag.lobby.nameItem"), plugin.getLang().get("menus.flag.lobby.loreItem")},
                        new String[]{"{SPECT}", plugin.getLang().get("menus.flag.spect.nameItem"), plugin.getLang().get("menus.flag.spect.loreItem")},
                        new String[]{"{SAVE}", plugin.getLang().get("menus.flag.save.nameItem"), plugin.getLang().get("menus.flag.save.loreItem")});
                contents.put(slot, item);
                config.put(slot, litem);
            }
            this.contents = contents;
            this.config = config;
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Map<Integer, ItemStack> getContents() {
        return contents;
    }

    @Override
    public void setContents(Map<Integer, ItemStack> contents) {
        this.contents = contents;
    }

    @Override
    public Map<Integer, ItemStack> getConfig() {
        return config;
    }

    @Override
    public void setConfig(Map<Integer, ItemStack> config) {
        this.config = config;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void save() {
        plugin.getMenus().set("menus." + name + ".rows", rows);
        plugin.getMenus().set("menus." + name + ".items", null);
        for (int i : config.keySet()) {
            plugin.getMenus().set("menus." + name + ".items." + i, config.get(i));
        }
        plugin.getMenus().save();
        reload();
    }

    @Override
    public void reload() {
        if (plugin.getMenus().isSet("menus." + name)) {
            this.rows = plugin.getMenus().getInt("menus." + name + ".rows");
            Map<Integer, ItemStack> contents = new HashMap<>();
            Map<Integer, ItemStack> config = new HashMap<>();
            ConfigurationSection conf = plugin.getMenus().getConfig().getConfigurationSection("menus." + name + ".items");
            for (String c : conf.getKeys(false)) {
                int slot = Integer.parseInt(c);
                ItemStack litem = plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c);
                ItemStack item = ItemBuilder.parse(plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c).clone(),
                        new String[]{"{NAME}", plugin.getLang().get("menus.flag.name.nameItem"), plugin.getLang().get("menus.flag.name.loreItem")},
                        new String[]{"{PROTECTION}", plugin.getLang().get("menus.flag.protection.nameItem"), plugin.getLang().get("menus.flag.protection.loreItem")},
                        new String[]{"{NPCUPGRADE}", plugin.getLang().get("menus.flag.npcUpgrade.nameItem"), plugin.getLang().get("menus.flag.npcUpgrade.loreItem")},
                        new String[]{"{NPCBUFF}", plugin.getLang().get("menus.flag.npcBuff.nameItem"), plugin.getLang().get("menus.flag.npcBuff.loreItem")},
                        new String[]{"{NPCSHOP}", plugin.getLang().get("menus.flag.npcShop.nameItem"), plugin.getLang().get("menus.flag.npcShop.loreItem")},
                        new String[]{"{NPCKITS}", plugin.getLang().get("menus.flag.npcKits.nameItem"), plugin.getLang().get("menus.flag.npcKits.loreItem")},
                        new String[]{"{SCHEMATIC}", plugin.getLang().get("menus.flag.schematic.nameItem"), plugin.getLang().get("menus.flag.schematic.loreItem")},
                        new String[]{"{MIN}", plugin.getLang().get("menus.flag.min.nameItem"), plugin.getLang().get("menus.flag.min.loreItem")},
                        new String[]{"{TEAMS}", plugin.getLang().get("menus.flag.teams.nameItem"), plugin.getLang().get("menus.flag.teams.loreItem")},
                        new String[]{"{TEAMSIZE}", plugin.getLang().get("menus.flag.teamSize.nameItem"), plugin.getLang().get("menus.flag.teamSize.loreItem")},
                        new String[]{"{LOBBY}", plugin.getLang().get("menus.flag.lobby.nameItem"), plugin.getLang().get("menus.flag.lobby.loreItem")},
                        new String[]{"{SPECT}", plugin.getLang().get("menus.flag.spect.nameItem"), plugin.getLang().get("menus.flag.spect.loreItem")},
                        new String[]{"{SAVE}", plugin.getLang().get("menus.flag.save.nameItem"), plugin.getLang().get("menus.flag.save.loreItem")});
                contents.put(slot, item);
                config.put(slot, litem);
            }
            this.contents = contents;
            this.config = config;
        }
    }
}