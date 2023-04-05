package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
public class KitSetup {

    private UltraCTW plugin;
    private String name, permission;
    private int slot = 10, page = 1;
    private HashMap<Integer, KitLevelSetup> levels = new HashMap<>();
    private KitLevelSetup kls;
    private boolean flag = false;

    public KitSetup(UltraCTW plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.permission = "ultractw.kits." + name;
    }

    public void saveKitLevel() {
        levels.put(kls.getLevel(), kls);
        kls = null;
    }

    public void save() {
        String n = "kits." + name;
        plugin.getKits().set(n + ".id", plugin.getKm().getNextID());
        plugin.getKits().set(n + ".name", name);
        plugin.getKits().set(n + ".permission", permission);
        plugin.getKits().set(n + ".slot", slot);
        plugin.getKits().set(n + ".page", page);
        plugin.getKits().set(n + ".flag", flag);
        for (KitLevelSetup kls : levels.values()) {
            String nl = "kits." + name + ".levels." + kls.getLevel();
            plugin.getKits().set(nl + ".level", kls.getLevel());
            plugin.getKits().set(nl + ".price", kls.getPrice());
            plugin.getKits().set(nl + ".slot", kls.getSlot());
            plugin.getKits().set(nl + ".page", kls.getPage());
            ItemStack defIcon = kls.getIcon();
            ItemMeta dM = defIcon.getItemMeta();
            dM.setDisplayName("§aDefault");
            dM.setLore(new ArrayList<>(Arrays.asList("§7This is a default icon:", "§7Change this in kits.yml")));
            defIcon.setItemMeta(dM);
            plugin.getKits().set(nl + ".icon", defIcon);
            plugin.getKits().set(nl + ".armor", kls.getArmor());
            plugin.getKits().set(nl + ".inv", kls.getInv());
            plugin.getKits().set(nl + ".permission", permission + "." + kls.getLevel());
        }
        plugin.getKits().save();
    }

}