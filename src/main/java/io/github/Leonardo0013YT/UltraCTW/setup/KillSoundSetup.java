package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KillSoundSetup {

    private Player p;
    private ItemStack icon;
    private String name, permission;
    private int slot, page, price;
    private boolean isBuy;
    private float vol1, vol2;
    private Sound sound;

    public KillSoundSetup(Player p, String name) {
        this.p = p;
        this.name = name;
        this.permission = "ultractw.killsound." + name;
        this.icon = new ItemStack(Material.GHAST_TEAR);
        this.slot = 10;
        this.page = 1;
        this.price = 500;
        this.vol1 = 1.0f;
        this.vol2 = 1.0f;
        this.sound = (UltraCTW.get().getVc().is1_9to15()) ? Sound.valueOf("ENTITY_PLAYER_LEVELUP") : Sound.valueOf("LEVEL_UP");
        this.isBuy = true;
    }

    public void saveKillSound(Player p) {
        UltraCTW plugin = UltraCTW.get();
        plugin.getKillsound().set("killsounds." + name + ".id", plugin.getKsm().getNextId());
        plugin.getKillsound().set("killsounds." + name + ".name", name);
        plugin.getKillsound().set("killsounds." + name + ".permission", permission);
        ItemStack icon = this.icon.clone();
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName("§a" + name);
        im.setLore(Arrays.asList("§7This is a default lore.", "§7Change me in killsounds.yml"));
        icon.setItemMeta(im);
        plugin.getKillsound().set("killsounds." + name + ".icon", icon);
        plugin.getKillsound().set("killsounds." + name + ".slot", slot);
        plugin.getKillsound().set("killsounds." + name + ".sound", sound.name());
        plugin.getKillsound().set("killsounds." + name + ".vol1", vol1);
        plugin.getKillsound().set("killsounds." + name + ".vol2", vol2);
        plugin.getKillsound().set("killsounds." + name + ".page", page);
        plugin.getKillsound().set("killsounds." + name + ".price", price);
        plugin.getKillsound().set("killsounds." + name + ".isBuy", isBuy);
        plugin.getKillsound().set("killsounds." + name + ".message", slot);
        plugin.getKillsound().save();
        p.sendMessage(plugin.getLang().get(p, "setup.killsounds.save"));
        plugin.getKsm().loadKillSounds();
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public float getVol2() {
        return vol2;
    }

    public void setVol2(float vol2) {
        this.vol2 = vol2;
    }

    public float getVol1() {
        return vol1;
    }

    public void setVol1(float vol1) {
        this.vol1 = vol1;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

}