package io.github.Leonardo0013YT.UltraCTW.cosmetics.killsounds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Purchasable;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class KillSound implements Purchasable {

    private final String name;
    private final String permission;
    private final String autoGivePermission;
    private final Sound sound;
    private final boolean isBuy;
    private final boolean needPermToBuy;
    private final float vol1;
    private final float vol2;
    private final int id;
    private final int slot;
    private final int page;
    private final int price;
    private final ItemStack icon;

    public KillSound(UltraCTW plugin, String s) {
        this.id = plugin.getKillsound().getInt(s + ".id");
        this.name = plugin.getKillsound().get(null, s + ".name");
        this.sound = Sound.valueOf(plugin.getKillsound().get(null, s + ".sound"));
        this.permission = plugin.getKillsound().get(null, s + ".permission");
        this.slot = plugin.getKillsound().getInt(s + ".slot");
        this.page = plugin.getKillsound().getInt(s + ".page");
        this.price = plugin.getKillsound().getInt(s + ".price");
        this.needPermToBuy = plugin.getKillsound().getBooleanOrDefault(s + ".needPermToBuy", false);
        this.autoGivePermission = plugin.getKillsound().getOrDefault(s + ".autoGivePermission", "ultractw.killsound.autogive." + name);
        this.isBuy = plugin.getKillsound().getBoolean(s + ".isBuy");
        this.icon = plugin.getKillsound().getConfig().getItemStack(s + ".icon");
        this.vol1 = (float) plugin.getKillsound().getConfig().getDouble(s + ".vol1");
        this.vol2 = (float) plugin.getKillsound().getConfig().getDouble(s + ".vol1");
        plugin.getKsm().setLastPage(page);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public boolean isBuy() {
        return isBuy;
    }

    @Override
    public boolean needPermToBuy() {
        return needPermToBuy;
    }

    public float getVol1() {
        return vol1;
    }

    public float getVol2() {
        return vol2;
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String getAutoGivePermission() {
        return autoGivePermission;
    }

    @Override
    public int getPrice() {
        return price;
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
                    if (!sw.getKillsounds().contains(id)) {
                        icon = ItemBuilder.item(XMaterial.matchXMaterial(UltraCTW.get().getCm().getRedPanelMaterial().name(), (byte) UltraCTW.get().getCm().getRedPanelData()).orElse(XMaterial.RED_STAINED_GLASS_PANE), 1, icon.getItemMeta().getDisplayName(), icon.getItemMeta().getLore());
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
                        if (isBuy && !sw.getKillsounds().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.price").replaceAll("<price>", String.valueOf(price)));
                        } else if (!isBuy && !sw.getKillsounds().contains(id)) {
                            if (needPermToBuy && p.hasPermission(permission)) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.price").replaceAll("<price>", String.valueOf(price)));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.noBuyable"));
                            }
                        } else if (sw.getKillsounds().contains(id) || !needPermToBuy) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.buyed"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.buyed"));
                    }
                    break;
                case "<status>":
                    if (!p.hasPermission(autoGivePermission)) {
                        if (sw.getKillsounds().contains(id)) {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.hasBuy"));
                        } else if (isBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.noMoney"));
                            }
                        } else if (needPermToBuy) {
                            if (UltraCTW.get().getAdm().getCoins(p) > price) {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.buy"));
                            } else {
                                lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.noMoney"));
                            }
                        } else {
                            lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.noPermission"));
                        }
                    } else {
                        lore.set(i, UltraCTW.get().getLang().get(p, "menus.killsoundsselector.hasBuy"));
                    }
                    break;
            }
        }
        iconM.setLore(lore);
        icon.setItemMeta(iconM);
        return icon;
    }

    public void execute(Player k, Player d) {
        k.playSound(k.getLocation(), sound, getVol1(), getVol2());
        d.playSound(d.getLocation(), sound, getVol1(), getVol2());
    }

}