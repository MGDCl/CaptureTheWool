package io.github.Leonardo0013YT.UltraCTW.cosmetics.windances;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UltraWinDance implements Purchasable {

    private String name, permission, type, autoGivePermission;
    private boolean isBuy, needPermToBuy;
    private int id, slot, page, price;
    private ItemStack icon;

    public UltraWinDance(UltraCTW plugin, String s) {
        this.id = plugin.getWindance().getInt(s + ".id");
        this.name = plugin.getWindance().get(null, s + ".name");
        this.type = plugin.getWindance().get(null, s + ".type");
        this.permission = plugin.getWindance().get(null, s + ".permission");
        this.slot = plugin.getWindance().getInt(s + ".slot");
        this.page = plugin.getWindance().getInt(s + ".page");
        this.price = plugin.getWindance().getInt(s + ".price");
        this.isBuy = plugin.getWindance().getBoolean(s + ".isBuy");
        this.icon = plugin.getWindance().getConfig().getItemStack(s + ".icon");
        this.needPermToBuy = plugin.getWindance().getBooleanOrDefault(s + ".needPermToBuy", false);
        this.autoGivePermission = plugin.getWindance().getOrDefault(s + ".autoGivePermission", "ultraskywars.windances.autogive." + name);
        plugin.getWdm().setLastPage(page);
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }

    @Override
    public boolean needPermToBuy() {
        return needPermToBuy;
    }

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public String getAutoGivePermission() {
        return autoGivePermission;
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
                    if (!sw.getWindances().contains(id)) {
                        icon = ItemBuilder.item(XMaterial.matchDefinedXMaterial(UltraCTW.get().getCm().getRedPanelMaterial().name(), (byte) UltraCTW.get().getCm().getRedPanelData()).orElse(XMaterial.RED_STAINED_GLASS_PANE), 1, icon.getItemMeta().getDisplayName(), icon.getItemMeta().getLore());
                    }
                }
            }
        }
        ItemMeta iconM = icon.getItemMeta();
        List<String> lore = icon.getItemMeta().getLore();
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            switch (s) {
                case "<price>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (isBuy && !sw.getWindances().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.price").replaceAll("<price>", String.valueOf(price)));
                        } else if (!isBuy && !sw.getWindances().contains(id)) {
                            if (needPermToBuy && p.hasPermission(permission)) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.price").replaceAll("<price>", String.valueOf(price)));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.noBuyable"));
                            }
                        } else if (sw.getWindances().contains(id) || !needPermToBuy) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.buyed"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.buyed"));
                    }
                    break;
                case "<status>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (sw.getWindances().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.hasBuy"));
                        } else if (isBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.noMoney"));
                            }
                        } else if (needPermToBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.noMoney"));
                            }
                        } else {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.noPermission"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.windancesselector.hasBuy"));
                    }
                    break;
            }
        }
        iconM.setLore(lore);
        icon.setItemMeta(iconM);
        return NBTEditor.set(icon, id, "ULTRASKYWARS", "WINDANCE");
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public String getType() {
        return type;
    }

}