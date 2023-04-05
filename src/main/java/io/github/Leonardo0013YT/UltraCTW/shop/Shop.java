package io.github.Leonardo0013YT.UltraCTW.shop;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
public class Shop {

    private UltraCTW plugin;
    private int slot;
    private Material material;
    private String key, name, lore;
    private HashMap<String, ShopItem> items = new HashMap<>();

    public Shop(UltraCTW plugin, String path, String key) {
        this.plugin = plugin;
        this.key = key;
        this.slot = plugin.getUpgrades().getInt(path + ".slot");
        this.material = Material.valueOf(plugin.getUpgrades().get(path + ".material"));
        this.name = plugin.getUpgrades().get(path + ".name");
        this.lore = plugin.getUpgrades().get(path + ".lore");
        if (plugin.getUpgrades().isSet(path + ".items")) {
            for (String s : plugin.getUpgrades().getConfig().getConfigurationSection(path + ".items").getKeys(false)) {
                items.put(s, new ShopItem(plugin, path + ".items." + s, key, s));
            }
        }
    }

    public ItemStack getIcon() {
        ItemStack icon = new ItemUtils(material, 1, 0).setDisplayName(name).setLore(lore).applyAttributes().build();
        return NBTEditor.set(icon, key, "BUFF", "FLAG", "MENU");
    }

}