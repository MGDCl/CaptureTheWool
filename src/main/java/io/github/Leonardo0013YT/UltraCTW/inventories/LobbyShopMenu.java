package io.github.Leonardo0013YT.UltraCTW.inventories;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LobbyShopMenu implements UltraInventory {

    private String title;
    private Map<Integer, ItemStack> config = new HashMap<>();
    private Map<Integer, ItemStack> contents = new HashMap<>();
    private int rows = 6;
    private String name;
    private UltraCTW plugin;

    public LobbyShopMenu(UltraCTW plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.title = plugin.getLang().get("menus." + name + ".title");
        if (plugin.getMenus().isSet("menus." + name)) {
            this.rows = plugin.getMenus().getInt("menus." + name + ".rows");
            Map<Integer, ItemStack> config = new HashMap<>();
            Map<Integer, ItemStack> contents = new HashMap<>();
            if (plugin.getMenus().getConfig().isSet("menus." + name + ".items")) {
                ConfigurationSection conf = plugin.getMenus().getConfig().getConfigurationSection("menus." + name + ".items");
                for (String c : conf.getKeys(false)) {
                    int slot = Integer.parseInt(c);
                    ItemStack litem = plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c);
                    ItemStack item = ItemBuilder.parse(plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c).clone(),
                            new String[]{"{LOBBYKEEPERS}", plugin.getLang().get("menus.lobby.shopkeepers.nameItem"), plugin.getLang().get("menus.lobby.shopkeepers.loreItem")},
                            new String[]{"{LOBBYTRAILS}", plugin.getLang().get("menus.lobby.trails.nameItem"), plugin.getLang().get("menus.lobby.trails.loreItem")},
                            new String[]{"{LOBBYTAUNTS}", plugin.getLang().get("menus.lobby.taunts.nameItem"), plugin.getLang().get("menus.lobby.taunts.loreItem")},
                            new String[]{"{LOBBYKILLSOUND}", plugin.getLang().get("menus.lobby.killsound.nameItem"), plugin.getLang().get("menus.lobby.killsound.loreItem")},
                            new String[]{"{LOBBYKILLEFFECTS}", plugin.getLang().get("menus.lobby.killeffects.nameItem"), plugin.getLang().get("menus.lobby.killeffects.loreItem")},
                            new String[]{"{LOBBYWINDANCES}", plugin.getLang().get("menus.lobby.windances.nameItem"), plugin.getLang().get("menus.lobby.windances.loreItem")},
                            new String[]{"{LOBBYWINEFFECTS}", plugin.getLang().get("menus.lobby.wineffects.nameItem"), plugin.getLang().get("menus.lobby.wineffects.loreItem")});
                    contents.put(slot, item);
                    config.put(slot, litem);
                }
                this.contents = contents;
                this.config = config;
            }
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
            Map<Integer, ItemStack> config = new HashMap<>();
            Map<Integer, ItemStack> contents = new HashMap<>();
            if (plugin.getMenus().getConfig().isSet("menus." + name + ".items")) {
                ConfigurationSection conf = plugin.getMenus().getConfig().getConfigurationSection("menus." + name + ".items");
                for (String c : conf.getKeys(false)) {
                    int slot = Integer.parseInt(c);
                    ItemStack litem = plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c);
                    ItemStack item = ItemBuilder.parse(plugin.getMenus().getConfig().getItemStack("menus." + name + ".items." + c).clone(),
                            new String[]{"{LOBBYKEEPERS}", plugin.getLang().get("menus.lobby.shopkeepers.nameItem"), plugin.getLang().get("menus.lobby.shopkeepers.loreItem")},
                            new String[]{"{LOBBYTRAILS}", plugin.getLang().get("menus.lobby.trails.nameItem"), plugin.getLang().get("menus.lobby.trails.loreItem")},
                            new String[]{"{LOBBYTAUNTS}", plugin.getLang().get("menus.lobby.taunts.nameItem"), plugin.getLang().get("menus.lobby.taunts.loreItem")},
                            new String[]{"{LOBBYKILLSOUND}", plugin.getLang().get("menus.lobby.killsound.nameItem"), plugin.getLang().get("menus.lobby.killsound.loreItem")},
                            new String[]{"{LOBBYKILLEFFECTS}", plugin.getLang().get("menus.lobby.killeffects.nameItem"), plugin.getLang().get("menus.lobby.killeffects.loreItem")},
                            new String[]{"{LOBBYWINDANCES}", plugin.getLang().get("menus.lobby.windances.nameItem"), plugin.getLang().get("menus.lobby.windances.loreItem")},
                            new String[]{"{LOBBYWINEFFECTS}", plugin.getLang().get("menus.lobby.wineffects.nameItem"), plugin.getLang().get("menus.lobby.wineffects.loreItem")});
                    contents.put(slot, item);
                    config.put(slot, litem);
                }
                this.contents = contents;
                this.config = config;
            }
        }
    }

}