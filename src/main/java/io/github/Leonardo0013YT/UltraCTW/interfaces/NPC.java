package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public interface NPC {

    void create(Player p, Location loc, EntityType type, NPCType npcType);

    void spawnHologram();

    boolean isShowing();

    boolean toHide(Location loc);

    void spawn();

    Location getLoc();

    NPCType getNpcType();

    void destroy();

    org.bukkit.entity.Entity getBukkitEntity();

    int getEntityID();
}