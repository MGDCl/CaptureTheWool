package io.github.Leonardo0013YT.UltraCTW.nms.npc;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.NPCType;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class NPC_v1_8_r3 implements NPC {

    private ArrayList<EntityLiving> armors = new ArrayList<>();
    private EntityLiving entity;
    private Player p;
    private Location loc;
    private EntityType type;
    private UltraCTW plugin;
    private WorldServer nmsWorld;
    private NPCType npcType;
    private double up = 0.8;
    private boolean showing;

    public NPC_v1_8_r3(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public void create(Player p, Location loc, EntityType type, NPCType npcType) {
        this.p = p;
        this.loc = loc;
        this.type = type;
        this.showing = false;
        this.npcType = npcType;
        this.nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        if (type.equals(EntityType.IRON_GOLEM) || type.equals(EntityType.WITHER)) {
            up += 0.75;
        }
    }

    @Override
    public boolean isShowing() {
        return showing;
    }

    @Override
    public boolean toHide(Location loc) {
        if (!this.loc.getWorld().getName().equals(loc.getWorld().getName())) return true;
        return this.loc.distance(loc) > 30;
    }

    @Override
    public void spawn() {
        this.showing = true;
        EntityLiving ev = getEntityByType(type, nmsWorld);
        ev.setLocation(loc.getX(), loc.getY(), loc.getZ(), newDirection(loc.getYaw()), newDirection(loc.getPitch()));
        ev.setCustomNameVisible(false);
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutSpawnEntityLiving(ev));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(ev, newDirection(loc.getYaw())));
        entity = ev;
        spawnHologram();
    }

    @Override
    public void spawnHologram() {
        Location start = loc.clone().add(0, up, 0);
        ArrayList<String> reverse = new ArrayList<>(plugin.getLang().getList("holograms." + npcType.name().toLowerCase()));
        Collections.reverse(reverse);
        for (String s : reverse) {
            EntityArmorStand eas = new EntityArmorStand(nmsWorld);
            eas.setLocation(start.getX(), start.getY(), start.getZ(), 0, 0);
            eas.setGravity(false);
            eas.setInvisible(true);
            eas.setBasePlate(false);
            eas.setSmall(true);
            eas.setArms(false);
            eas.setCustomNameVisible(true);
            eas.setCustomName(s);
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutSpawnEntityLiving(eas));
            armors.add(eas);
            start.add(0, 0.35, 0);
        }
    }

    @Override
    public Location getLoc() {
        return loc;
    }

    @Override
    public NPCType getNpcType() {
        return npcType;
    }

    @Override
    public void destroy() {
        this.showing = false;
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        if (connection == null) return;
        if (entity != null) {
            connection.sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
            nmsWorld.removeEntity(entity);
        }
        for (Entity e : armors) {
            if (e == null) continue;
            connection.sendPacket(new PacketPlayOutEntityDestroy(e.getId()));
            nmsWorld.removeEntity(e);
        }
    }

    @Override
    public org.bukkit.entity.Entity getBukkitEntity() {
        if (entity == null) return null;
        return entity.getBukkitEntity();
    }

    @Override
    public int getEntityID() {
        if (entity == null) return -5000;
        return entity.getId();
    }

    private EntityLiving getEntityByType(EntityType type, WorldServer nmsWorld) {
        if (type.equals(EntityType.ZOMBIE)) {
            return new EntityZombie(nmsWorld);
        } else if (type.equals(EntityType.VILLAGER)) {
            return new EntityVillager(nmsWorld);
        } else if (type.equals(EntityType.CHICKEN)) {
            return new EntityChicken(nmsWorld);
        } else if (type.equals(EntityType.RABBIT)) {
            return new EntityRabbit(nmsWorld);
        } else if (type.equals(EntityType.BLAZE)) {
            return new EntityBlaze(nmsWorld);
        } else if (type.equals(EntityType.CREEPER)) {
            return new EntityCreeper(nmsWorld);
        } else if (type.equals(EntityType.CAVE_SPIDER)) {
            return new EntityCaveSpider(nmsWorld);
        } else if (type.equals(EntityType.COW)) {
            return new EntityCow(nmsWorld);
        } else if (type.equals(EntityType.HORSE)) {
            return new EntityHorse(nmsWorld);
        } else if (type.equals(EntityType.IRON_GOLEM)) {
            return new EntityIronGolem(nmsWorld);
        } else if (type.equals(EntityType.MAGMA_CUBE)) {
            return new EntityMagmaCube(nmsWorld);
        } else if (type.equals(EntityType.SLIME)) {
            return new EntitySlime(nmsWorld);
        } else if (type.equals(EntityType.PIG_ZOMBIE)) {
            return new EntityPigZombie(nmsWorld);
        } else if (type.equals(EntityType.SKELETON)) {
            return new EntitySkeleton(nmsWorld);
        } else if (type.equals(EntityType.SNOWMAN)) {
            return new EntitySnowman(nmsWorld);
        } else if (type.equals(EntityType.WITCH)) {
            return new EntityWitch(nmsWorld);
        } else if (type.equals(EntityType.SHEEP)) {
            return new EntitySheep(nmsWorld);
        } else if (type.equals(EntityType.WITHER)) {
            return new EntityWither(nmsWorld);
        } else if (type.equals(EntityType.PIG)) {
            return new EntityPig(nmsWorld);
        } else if (type.equals(EntityType.WOLF)) {
            return new EntityWolf(nmsWorld);
        }
        return new EntityZombie(nmsWorld);
    }

    private byte newDirection(float loc) {
        return (byte) ((int) (loc * 256.0F / 360.0F));
    }

}