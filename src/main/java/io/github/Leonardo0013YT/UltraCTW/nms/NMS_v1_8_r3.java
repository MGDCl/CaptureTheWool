package io.github.Leonardo0013YT.UltraCTW.nms;

import io.github.Leonardo0013YT.UltraCTW.interfaces.NMS;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class NMS_v1_8_r3 implements NMS {

    @Override
    public Horse spawnHorse(Location loc, Player p) {
        Horse horse = loc.getWorld().spawn(loc, Horse.class);
        horse.setVariant(Horse.Variant.SKELETON_HORSE);
        horse.setOwner(p);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setDomestication(horse.getMaxDomestication());
        horse.setAdult();
        return horse;
    }

    @Override
    public void sendActionBar(Player p, String msg) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(("{\"text\": \"" + msg + "\"}"));
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
    }

    @Override
    public String[] getDamageCauses() {
        return new String[]{"CONTACT", "ENTITY_ATTACK", "PROJECTILE", "SUFFOCATION", "FALL", "FIRE", "FIRE_TICK",
                "MELTING", "LAVA", "DROWNING", "BLOCK_EXPLOSION", "ENTITY_EXPLOSION", "VOID", "LIGHTNING",
                "SUICIDE", "STARVATION", "POISON", "MAGIC", "WITHER", "FALLING_BLOCK", "THORNS", "CUSTOM"};
    }

    @Override
    public void followPlayer(Player player, LivingEntity entity, double d) {
        float f = (float) d;
        ((EntityInsentient) ((CraftEntity) entity).getHandle()).getNavigation().a(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), f);
    }

    @Override
    public void displayParticle(Player player, Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(enumParticle), false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, amount);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void broadcastParticle(Location location, float offsetX, float offsetY, float offsetZ, int speed, String enumParticle, int amount, double range) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.valueOf(enumParticle), false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, amount);
        if (location.getWorld() == null) return;
        Collection<Entity> ents;
        try {
            ents = location.getWorld().getNearbyEntities(location, range, range, range);
        } catch (Exception ignored) {
            return;
        }
        if (ents.isEmpty()) return;
        ents.removeIf(e -> !e.getType().equals(EntityType.PLAYER));
        for (Entity p : ents) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public boolean isParticle(String particle) {
        try {
            EnumParticle.valueOf(particle);
        } catch (EnumConstantNotPresentException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public void freezeEntity(Entity en) {
        net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        nmsEn.c(compound);
        compound.setByte("NoAI", (byte) 1);
        nmsEn.f(compound);
    }

}