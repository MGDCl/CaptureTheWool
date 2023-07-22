package io.github.Leonardo0013YT.UltraCTW.cosmetics.killeffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UltraKillEffect implements Purchasable {

    private final String name;
    private final String permission;
    private final String type;
    private final String autoGivePermission;
    private final boolean isBuy;
    private final boolean needPermToBuy;
    private final int id;
    private final int slot;
    private final int page;
    private final int price;
    private ItemStack icon;

    public UltraKillEffect(UltraCTW plugin, String s) {
        this.id = plugin.getKilleffect().getInt(s + ".id");
        this.name = plugin.getKilleffect().get(s + ".name");
        this.type = plugin.getKilleffect().get(s + ".type");
        this.permission = plugin.getKilleffect().get(s + ".permission");
        this.slot = plugin.getKilleffect().getInt(s + ".slot");
        this.page = plugin.getKilleffect().getInt(s + ".page");
        this.price = plugin.getKilleffect().getInt(s + ".price");
        this.isBuy = plugin.getKilleffect().getBoolean(s + ".isBuy");
        this.needPermToBuy = plugin.getKilleffect().getBooleanOrDefault(s + ".needPermToBuy", false);
        this.icon = plugin.getKilleffect().getConfig().getItemStack(s + ".icon");
        this.autoGivePermission = plugin.getKilleffect().getOrDefault(s + ".autoGivePermission", "ultraskywars.killeffect.autogive." + name);
        plugin.getKem().setLastPage(page);
    }

    @Override
    public String getAutoGivePermission() {
        return autoGivePermission;
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

    public ItemStack getIcon(Player p) {
        if (icon == null || icon.getType().equals(Material.AIR)) {
            icon = new ItemStack(Material.BEDROCK);
        }
        if (!icon.hasItemMeta()) {
            return icon;
        }
        CTWPlayer sw = UltraCTW.get().getDb().getCTWPlayer(p);
        ItemStack icon = this.icon.clone();
        if (!p.hasPermission(autoGivePermission)) {
            if (price > 0) {
                if (UltraCTW.get().getCm().isRedPanelInLocked()) {
                    if (!sw.getKilleffects().contains(id)) {
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
                        if (isBuy && !sw.getKilleffects().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.price").replaceAll("<price>", String.valueOf(price)));
                        } else if (!isBuy && !sw.getKilleffects().contains(id)) {
                            if (needPermToBuy && p.hasPermission(permission)) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.price").replaceAll("<price>", String.valueOf(price)));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.noBuyable"));
                            }
                        } else if (sw.getKilleffects().contains(id) || !needPermToBuy) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.buyed"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.buyed"));
                    }
                    break;
                case "<status>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (sw.getKilleffects().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.hasBuy"));
                        } else if (isBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.noMoney"));
                            }
                        } else if (needPermToBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.noMoney"));
                            }
                        } else {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.noPermission"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.killeffectsselector.hasBuy"));
                    }
                    break;
            }
        }
        iconM.setLore(lore);
        icon.setItemMeta(iconM);
        return NBTEditor.set(icon, id, "ULTRASKYWARS", "KILLEFFECT");
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