package io.github.Leonardo0013YT.UltraCTW.setup;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class KitLevelSetup {

    private ItemStack icon = new ItemStack(Material.DIAMOND_SWORD);
    private ItemStack[] inv = new ItemStack[36], armor = {null, null, null, null};
    private double price;
    private boolean buy;
    private int level, slot, page;

    public KitLevelSetup(int level) {
        this.level = level;
        this.slot = 10;
        this.page = 1;
        this.buy = true;
        this.price = 500;
    }

}