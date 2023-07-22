package io.github.Leonardo0013YT.UltraCTW.inventories.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetupTauntMenu implements UltraInventory {

    private String title;
    private Map<Integer, ItemStack> config = new HashMap<>();
    private Map<Integer, ItemStack> contents = new HashMap<>();
    private int rows = 6;
    private String name;
    private final UltraCTW plugin;

    public SetupTauntMenu(UltraCTW plugin, String name) {
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
                            new String[]{"{TAUNTTITLE}", plugin.getLang().get(null, "menus.taunts.settitle.nameItem"), plugin.getLang().get(null, "menus.taunts.settitle.loreItem")},
                            new String[]{"{TAUNTSUBTITLE}", plugin.getLang().get(null, "menus.taunts.setsubtitle.nameItem"), plugin.getLang().get(null, "menus.taunts.setsubtitle.loreItem")},
                            new String[]{"{TAUNTICON}", plugin.getLang().get(null, "menus.taunts.icon.nameItem"), plugin.getLang().get(null, "menus.taunts.icon.loreItem")},
                            new String[]{"{TAUNTPRICE}", plugin.getLang().get(null, "menus.taunts.price.nameItem"), plugin.getLang().get(null, "menus.taunts.price.loreItem")},
                            new String[]{"{TAUNTSLOT}", plugin.getLang().get(null, "menus.taunts.slot.nameItem"), plugin.getLang().get(null, "menus.taunts.slot.loreItem")},
                            new String[]{"{TAUNTPAGE}", plugin.getLang().get(null, "menus.taunts.page.nameItem"), plugin.getLang().get(null, "menus.taunts.page.loreItem")},
                            new String[]{"{TAUNTPERMISSION}", plugin.getLang().get(null, "menus.taunts.permission.nameItem"), plugin.getLang().get(null, "menus.taunts.permission.loreItem")},
                            new String[]{"{TAUNTNAME}", plugin.getLang().get(null, "menus.taunts.name.nameItem"), plugin.getLang().get(null, "menus.taunts.name.loreItem")},
                            new String[]{"{TAUNTISBUY}", plugin.getLang().get(null, "menus.taunts.isBuy.nameItem"), plugin.getLang().get(null, "menus.taunts.isBuy.loreItem")},
                            new String[]{"{TAUNTPLAYER}", plugin.getLang().get(null, "menus.taunts.setplayer.nameItem"), plugin.getLang().get(null, "menus.taunts.setplayer.loreItem")},
                            new String[]{"{TAUNTNONE}", plugin.getLang().get(null, "menus.taunts.setnone.nameItem"), plugin.getLang().get(null, "menus.taunts.setnone.loreItem")},
                            new String[]{"{TAUNTSAVE}", plugin.getLang().get(null, "menus.taunts.save.nameItem"), plugin.getLang().get(null, "menus.taunts.save.loreItem")});
                    contents.put(slot, item);
                    config.put(slot, litem);
                }
                int s = 0;
                for (String c : plugin.getVc().getNMS().getDamageCauses()) {
                    ItemStack dam = ItemBuilder.item(XMaterial.PAPER, "§e" + c, plugin.getLang().get(null, "menus.taunts.damage.loreItem"));
                    contents.put(s, dam);
                    s++;
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
                            new String[]{"{TAUNTTITLE}", plugin.getLang().get(null, "menus.taunts.settitle.nameItem"), plugin.getLang().get(null, "menus.taunts.settitle.loreItem")},
                            new String[]{"{TAUNTSUBTITLE}", plugin.getLang().get(null, "menus.taunts.setsubtitle.nameItem"), plugin.getLang().get(null, "menus.taunts.setsubtitle.loreItem")},
                            new String[]{"{TAUNTICON}", plugin.getLang().get(null, "menus.taunts.icon.nameItem"), plugin.getLang().get(null, "menus.taunts.icon.loreItem")},
                            new String[]{"{TAUNTPRICE}", plugin.getLang().get(null, "menus.taunts.price.nameItem"), plugin.getLang().get(null, "menus.taunts.price.loreItem")},
                            new String[]{"{TAUNTSLOT}", plugin.getLang().get(null, "menus.taunts.slot.nameItem"), plugin.getLang().get(null, "menus.taunts.slot.loreItem")},
                            new String[]{"{TAUNTPAGE}", plugin.getLang().get(null, "menus.taunts.page.nameItem"), plugin.getLang().get(null, "menus.taunts.page.loreItem")},
                            new String[]{"{TAUNTPERMISSION}", plugin.getLang().get(null, "menus.taunts.permission.nameItem"), plugin.getLang().get(null, "menus.taunts.permission.loreItem")},
                            new String[]{"{TAUNTNAME}", plugin.getLang().get(null, "menus.taunts.name.nameItem"), plugin.getLang().get(null, "menus.taunts.name.loreItem")},
                            new String[]{"{TAUNTISBUY}", plugin.getLang().get(null, "menus.taunts.isBuy.nameItem"), plugin.getLang().get(null, "menus.taunts.isBuy.loreItem")},
                            new String[]{"{TAUNTPLAYER}", plugin.getLang().get(null, "menus.taunts.setplayer.nameItem"), plugin.getLang().get(null, "menus.taunts.setplayer.loreItem")},
                            new String[]{"{TAUNTNONE}", plugin.getLang().get(null, "menus.taunts.setnone.nameItem"), plugin.getLang().get(null, "menus.taunts.setnone.loreItem")},
                            new String[]{"{TAUNTSAVE}", plugin.getLang().get(null, "menus.taunts.save.nameItem"), plugin.getLang().get(null, "menus.taunts.save.loreItem")});
                    contents.put(slot, item);
                    config.put(slot, litem);
                }
                int s = 0;
                for (String c : plugin.getVc().getNMS().getDamageCauses()) {
                    ItemStack dam = ItemBuilder.item(XMaterial.PAPER, "§e" + c, plugin.getLang().get(null, "menus.taunts.damage.loreItem"));
                    contents.put(s, dam);
                    s++;
                }
                this.contents = contents;
                this.config = config;
            }
        }
    }

}