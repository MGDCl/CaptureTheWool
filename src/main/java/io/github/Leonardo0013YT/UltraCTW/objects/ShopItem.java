package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ShopItem implements Purchasable {

    private final ItemStack item;
    private final double price;

    public ShopItem(UltraCTW plugin, String path) {
        this.item = plugin.getShop().getConfig().getItemStack(path + ".item");
        this.price = plugin.getShop().getConfig().getDouble(path + ".price");
    }

    @Override
    public int getPrice() {
        return (int) price;
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getAutoGivePermission() {
        return "";
    }

    @Override
    public boolean isBuy() {
        return true;
    }

    @Override
    public boolean needPermToBuy() {
        return false;
    }

    public ItemStack getItem() {
        return this.item;
    }
}