package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface UltraInventory {

    String getTitle();

    void setTitle(String title);

    Map<Integer, ItemStack> getContents();

    void setContents(Map<Integer, ItemStack> contents);

    Map<Integer, ItemStack> getConfig();

    void setConfig(Map<Integer, ItemStack> config);

    int getRows();

    void setRows(int rows);

    String getName();

    void setName(String name);

    void save();

    void reload();

}