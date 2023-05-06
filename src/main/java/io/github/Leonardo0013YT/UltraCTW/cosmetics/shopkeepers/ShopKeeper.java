package io.github.Leonardo0013YT.UltraCTW.cosmetics.shopkeepers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
public class ShopKeeper implements Purchasable {

    private String name, permission, autoGivePermission, entityType;
    private boolean isBuy, needPermToBuy;
    private int id, slot, page, price;
    private ItemStack icon;

    public ShopKeeper(UltraCTW plugin, String s) {
        this.id = plugin.getShopkeepers().getInt(s + ".id");
        this.name = plugin.getShopkeepers().get(s + ".name");
        this.permission = plugin.getShopkeepers().get(s + ".permission");
        this.entityType = plugin.getShopkeepers().get(s + ".entity");
        this.slot = plugin.getShopkeepers().getInt(s + ".slot");
        this.page = plugin.getShopkeepers().getInt(s + ".page");
        this.price = plugin.getShopkeepers().getInt(s + ".price");
        this.isBuy = plugin.getShopkeepers().getBoolean(s + ".isBuy");
        this.needPermToBuy = plugin.getShopkeepers().getBooleanOrDefault(s + ".needPermToBuy", false);
        this.icon = plugin.getShopkeepers().getConfig().getItemStack(s + ".icon");
        this.autoGivePermission = plugin.getShopkeepers().getOrDefault(s + ".autoGivePermission", "ultractw.shopkeepers.autogive." + name);
        plugin.getSkm().setLastPage(page);
    }

    public ItemStack getIcon(Player p) {
        if (!icon.hasItemMeta()) {
            return icon;
        }
        CTWPlayer sw = UltraCTW.get().getDb().getCTWPlayer(p);
        ItemStack icon = this.icon.clone();
        if (!p.hasPermission(autoGivePermission)) {
            if (price > 0) {
                if (UltraCTW.get().getCm().isRedPanelInLocked()) {
                    if (!sw.getShopkeepers().contains(id)) {
                        icon = ItemBuilder.item(XMaterial.matchXMaterial(UltraCTW.get().getCm().getRedPanelMaterial().name(), (byte) UltraCTW.get().getCm().getRedPanelData()).orElse(XMaterial.RED_STAINED_GLASS_PANE), 1, icon.getItemMeta().getDisplayName(), icon.getItemMeta().getLore());
                    }
                }
            }
        }
        ItemMeta iconM = icon.getItemMeta();
        iconM.setDisplayName(iconM.getDisplayName().replaceAll("&", "§"));
        List<String> lore = icon.getItemMeta().getLore();
        for (int i = 0; i < lore.size(); i++) {
            String r = lore.get(i);
            lore.set(i, r.replaceAll("&", "§"));
            String s = lore.get(i);
            switch (s) {
                case "<price>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (isBuy && !sw.getShopkeepers().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.price").replaceAll("<price>", String.valueOf(price)));
                        } else if (!isBuy && !sw.getShopkeepers().contains(id)) {
                            if (needPermToBuy && p.hasPermission(permission)) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.price").replaceAll("<price>", String.valueOf(price)));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.noBuyable"));
                            }
                        } else if (sw.getShopkeepers().contains(id) || !needPermToBuy) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.buyed"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.buyed"));
                    }
                    break;
                case "<status>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (sw.getShopkeepers().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.hasBuy"));
                        } else if (isBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.noMoney"));
                            }
                        } else if (needPermToBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.noMoney"));
                            }
                        } else {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.noPermission"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.shopkeeperselector.hasBuy"));
                    }
                    break;
            }
        }
        iconM.setLore(lore);
        icon.setItemMeta(iconM);
        return icon;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getAutoGivePermission() {
        return autoGivePermission;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }

    @Override
    public boolean needPermToBuy() {
        return needPermToBuy;
    }
}