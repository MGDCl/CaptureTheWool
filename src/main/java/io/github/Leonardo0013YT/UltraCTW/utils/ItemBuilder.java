package io.github.Leonardo0013YT.UltraCTW.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    public static ItemStack parse(ItemStack item, String[]... t) {
        ItemStack i = item.clone();
        String display = (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) ? i.getItemMeta().getDisplayName() : "";
        ItemMeta im = i.getItemMeta();
        for (String[] s : t) {
            String s1 = s[0];
            String s2 = s[1];
            String s3 = s[2];
            if (display.equals(s1)) {
                im.setDisplayName(display.replace(s1, s2));
                im.setLore(s3.isEmpty() ? new ArrayList<>() : Arrays.asList(s3.split("\\n")));
                break;
            }
        }
        if (im != null) {
            addItemFlags(im);
        }
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack parseVariables(Player p, ItemStack item, UltraCTW plugin, String[]... t) {
        ItemStack i = item.clone();
        String d = i.hasItemMeta() && i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : "";
        List<String> lore = i.hasItemMeta() && i.getItemMeta().hasLore() ? i.getItemMeta().getLore() : Collections.emptyList();
        List<String> newLore = new ArrayList<>();
        boolean anyVariable = true;
        for (String[] s : t) {
            String s1 = s[0];
            String s2 = s[1];
            if (preCheck(lore, s1)) {
                anyVariable = false;
                for (String value : lore) {
                    if (value.contains(s1)) {
                        if (s2.contains("<newLine>")) {
                            String[] var21 = s2.split("<newLine>");
                            for (String l : var21) {
                                String newValue = value.replace(s1, l);
                                newLore.add(plugin.getAdm().parsePlaceholders(p, newValue));
                            }
                        } else {
                            String newValue = value.replace(s1, s2);
                            newLore.add(plugin.getAdm().parsePlaceholders(p, newValue));
                        }
                    } else {
                        newLore.add(plugin.getAdm().parsePlaceholders(p, value));
                    }
                }
                d = d.replaceAll(s1, s2);
            }
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(d);
        if (!anyVariable) {
            im.setLore(null);
            im.setLore(newLore);
        }
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack item(Material material, String displayName, String s) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(XMaterial material, String displayName, String s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(ItemStack item, String displayName, List<String> s) {
        ItemStack itemStack = item.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            itemMeta.getLore().clear();
        }
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s);
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(XMaterial material, int n, String displayName, String s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack item(XMaterial material, int n, String displayName, List<String> s) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(s);
        addItemFlags(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack skull(XMaterial material, int n, String displayName, String s, String owner) {
        ItemStack itemStack = new ItemStack(material.parseMaterial(), n, material.getData());
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        skullMeta.setDisplayName(displayName);
        skullMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        addItemFlags(skullMeta);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public static ItemStack createSkull(String displayName, String lore, String url) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(displayName);
        headMeta.setLore(lore.isEmpty() ? new ArrayList<>() : Arrays.asList(lore.split("\\n")));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack createSkull(String displayName, List<String> lore, String url) {
        ItemStack head = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
        if (url.isEmpty()) return head;
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(displayName);
        headMeta.setLore(lore);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack nameLore(ItemStack itemStack, String displayName, String s) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(null);
        itemMeta.setLore(s.isEmpty() ? new ArrayList<>() : Arrays.asList(s.split("\\n")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void addItemFlags(ItemMeta itemMeta) {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
    }

    public static boolean preCheck(List<String> lore, String variable) {
        boolean check = false;
        for (String l : lore) {
            if (l.contains(variable)) {
                check = true;
                break;
            }
        }
        return check;
    }

}
