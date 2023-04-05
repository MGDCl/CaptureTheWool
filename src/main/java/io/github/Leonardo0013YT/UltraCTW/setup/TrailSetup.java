package io.github.Leonardo0013YT.UltraCTW.setup;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TrailSetup {

    private Player p;
    private ItemStack icon;
    private String name, permission, particle;
    private float offsetX, offsetY, offsetZ;
    private int amount, slot, page, price;
    private double range, speed;
    private boolean isBuy;

    public TrailSetup(Player p, String name) {
        this.p = p;
        this.name = name;
        this.icon = new ItemStack(Material.BOW);
        this.permission = "ultractw.trails." + name;
        this.particle = "LAVA";
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
        this.offsetZ = 0.0f;
        this.speed = 10.0D;
        this.range = 15.0D;
        this.amount = 1;
        this.slot = 10;
        this.page = 1;
        this.price = 500;
        this.isBuy = true;
    }

    public void saveTrail(Player p) {
        UltraCTW plugin = UltraCTW.get();
        plugin.getTrail().set("trails." + name + ".id", plugin.getTlm().getNextId());
        plugin.getTrail().set("trails." + name + ".name", name);
        ItemStack icon = this.icon.clone();
        ItemMeta im = icon.getItemMeta();
        im.setDisplayName("§a" + name);
        im.setLore(Arrays.asList("§7This is a default lore.", "§7Change me in trails.yml"));
        icon.setItemMeta(im);
        plugin.getTrail().set("trails." + name + ".icon", icon);
        plugin.getTrail().set("trails." + name + ".permission", permission);
        plugin.getTrail().set("trails." + name + ".particle", particle);
        plugin.getTrail().set("trails." + name + ".offsetX", offsetX);
        plugin.getTrail().set("trails." + name + ".offsetY", offsetY);
        plugin.getTrail().set("trails." + name + ".offsetZ", offsetZ);
        plugin.getTrail().set("trails." + name + ".speed", speed);
        plugin.getTrail().set("trails." + name + ".range", range);
        plugin.getTrail().set("trails." + name + ".amount", amount);
        plugin.getTrail().set("trails." + name + ".slot", slot);
        plugin.getTrail().set("trails." + name + ".page", page);
        plugin.getTrail().set("trails." + name + ".price", price);
        plugin.getTrail().set("trails." + name + ".isBuy", isBuy);
        plugin.getTrail().save();
        p.sendMessage(plugin.getLang().get(p, "setup.trails.save"));
        plugin.getTlm().loadTrails();
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

    public String getParticle() {
        return particle;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }
}