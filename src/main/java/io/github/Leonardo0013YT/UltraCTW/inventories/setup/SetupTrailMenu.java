package io.github.Leonardo0013YT.UltraCTW.inventories.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetupTrailMenu implements UltraInventory {

    private String title;
    private Map<Integer, ItemStack> config = new HashMap<>();
    private Map<Integer, ItemStack> contents = new HashMap<>();
    private int rows = 5;
    private String name;
    private UltraCTW plugin;

    public SetupTrailMenu(UltraCTW plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.title = plugin.getLang().get(null, "menus." + name + ".title");
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
                            new String[]{"{TRAILICON}", plugin.getLang().get(null, "menus.trails.icon.nameItem"), plugin.getLang().get(null, "menus.trails.icon.loreItem")},
                            new String[]{"{TRAILSPEED}", plugin.getLang().get(null, "menus.trails.speed.nameItem"), plugin.getLang().get(null, "menus.trails.speed.loreItem")},
                            new String[]{"{TRAILOFFSETX}", plugin.getLang().get(null, "menus.trails.offsetX.nameItem"), plugin.getLang().get(null, "menus.trails.offsetX.loreItem")},
                            new String[]{"{TRAILOFFSETY}", plugin.getLang().get(null, "menus.trails.offsetY.nameItem"), plugin.getLang().get(null, "menus.trails.offsetY.loreItem")},
                            new String[]{"{TRAILOFFSETZ}", plugin.getLang().get(null, "menus.trails.offsetZ.nameItem"), plugin.getLang().get(null, "menus.trails.offsetZ.loreItem")},
                            new String[]{"{TRAILAMOUNT}", plugin.getLang().get(null, "menus.trails.amount.nameItem"), plugin.getLang().get(null, "menus.trails.amount.loreItem")},
                            new String[]{"{TRAILRANGE}", plugin.getLang().get(null, "menus.trails.range.nameItem"), plugin.getLang().get(null, "menus.trails.range.loreItem")},
                            new String[]{"{TRAILPRICE}", plugin.getLang().get(null, "menus.trails.price.nameItem"), plugin.getLang().get(null, "menus.trails.price.loreItem")},
                            new String[]{"{TRAILSLOT}", plugin.getLang().get(null, "menus.trails.slot.nameItem"), plugin.getLang().get(null, "menus.trails.slot.loreItem")},
                            new String[]{"{TRAILPAGE}", plugin.getLang().get(null, "menus.trails.page.nameItem"), plugin.getLang().get(null, "menus.trails.page.loreItem")},
                            new String[]{"{TRAILPARTICLE}", plugin.getLang().get(null, "menus.trails.particle.nameItem"), plugin.getLang().get(null, "menus.trails.particle.loreItem")},
                            new String[]{"{TRAILPERMISSION}", plugin.getLang().get(null, "menus.trails.permission.nameItem"), plugin.getLang().get(null, "menus.trails.permission.loreItem")},
                            new String[]{"{TRAILNAME}", plugin.getLang().get(null, "menus.trails.name.nameItem"), plugin.getLang().get(null, "menus.trails.name.loreItem")},
                            new String[]{"{TRAILISBUY}", plugin.getLang().get(null, "menus.trails.isBuy.nameItem"), plugin.getLang().get(null, "menus.trails.isBuy.loreItem")},
                            new String[]{"{TRAILSAVE}", plugin.getLang().get(null, "menus.trails.save.nameItem"), plugin.getLang().get(null, "menus.trails.save.loreItem")});
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
                            new String[]{"{TRAILICON}", plugin.getLang().get(null, "menus.trails.icon.nameItem"), plugin.getLang().get(null, "menus.trails.icon.loreItem")},
                            new String[]{"{TRAILSPEED}", plugin.getLang().get(null, "menus.trails.speed.nameItem"), plugin.getLang().get(null, "menus.trails.speed.loreItem")},
                            new String[]{"{TRAILOFFSETX}", plugin.getLang().get(null, "menus.trails.offsetX.nameItem"), plugin.getLang().get(null, "menus.trails.offsetX.loreItem")},
                            new String[]{"{TRAILOFFSETY}", plugin.getLang().get(null, "menus.trails.offsetY.nameItem"), plugin.getLang().get(null, "menus.trails.offsetY.loreItem")},
                            new String[]{"{TRAILOFFSETZ}", plugin.getLang().get(null, "menus.trails.offsetZ.nameItem"), plugin.getLang().get(null, "menus.trails.offsetZ.loreItem")},
                            new String[]{"{TRAILAMOUNT}", plugin.getLang().get(null, "menus.trails.amount.nameItem"), plugin.getLang().get(null, "menus.trails.amount.loreItem")},
                            new String[]{"{TRAILRANGE}", plugin.getLang().get(null, "menus.trails.range.nameItem"), plugin.getLang().get(null, "menus.trails.range.loreItem")},
                            new String[]{"{TRAILPRICE}", plugin.getLang().get(null, "menus.trails.price.nameItem"), plugin.getLang().get(null, "menus.trails.price.loreItem")},
                            new String[]{"{TRAILSLOT}", plugin.getLang().get(null, "menus.trails.slot.nameItem"), plugin.getLang().get(null, "menus.trails.slot.loreItem")},
                            new String[]{"{TRAILPAGE}", plugin.getLang().get(null, "menus.trails.page.nameItem"), plugin.getLang().get(null, "menus.trails.page.loreItem")},
                            new String[]{"{TRAILPARTICLE}", plugin.getLang().get(null, "menus.trails.particle.nameItem"), plugin.getLang().get(null, "menus.trails.particle.loreItem")},
                            new String[]{"{TRAILPERMISSION}", plugin.getLang().get(null, "menus.trails.permission.nameItem"), plugin.getLang().get(null, "menus.trails.permission.loreItem")},
                            new String[]{"{TRAILNAME}", plugin.getLang().get(null, "menus.trails.name.nameItem"), plugin.getLang().get(null, "menus.trails.name.loreItem")},
                            new String[]{"{TRAILISBUY}", plugin.getLang().get(null, "menus.trails.isBuy.nameItem"), plugin.getLang().get(null, "menus.trails.isBuy.loreItem")},
                            new String[]{"{TRAILSAVE}", plugin.getLang().get(null, "menus.trails.save.nameItem"), plugin.getLang().get(null, "menus.trails.save.loreItem")});
                    contents.put(slot, item);
                    config.put(slot, litem);
                }
                this.contents = contents;
                this.config = config;
            }
        }
    }

}