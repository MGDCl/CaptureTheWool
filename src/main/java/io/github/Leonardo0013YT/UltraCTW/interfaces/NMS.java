package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface NMS {

    void sendActionBar(Player p, String msg);

    String[] getDamageCauses();

    void followPlayer(Player player, LivingEntity entity, double d);

    void displayParticle(Player p, Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount);

    void broadcastParticle(Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount, double range);

    boolean isParticle(String particle);

    void freezeEntity(Entity en);

}