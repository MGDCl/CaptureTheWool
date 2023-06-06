package io.github.Leonardo0013YT.UltraCTW.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.entity.Entity;

public class NMSReflection {

    private static String version;
    private Method a, position;
    private static Class<?> packet;
    private Object enumTimes, enumTitle, enumSubtitle;
    private Constructor<?> packetPlayOutTitle, packetPlayOutTimes;

    public NMSReflection() {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            enumTimes = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
            enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
            enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
            packetPlayOutTimes = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            packetPlayOutTitle = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"));
            packet = getNMSClass("Packet");
            position = getNMSClass("Entity").getMethod("setPositionRotation", double.class, double.class, double.class, float.class, float.class);
            a = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
        } catch (Exception ignored) {
        }
    }

    public static void sendPacket(Player player, Object object) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = handle.getClass().getField("playerConnection").get(handle);
            connection.getClass().getMethod("sendPacket", packet).invoke(connection, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception var3) {
            return null;
        }
    }

    public static Class<?> getOBClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (Exception var3) {
            return null;
        }
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Player... players) {
        sendTitle(title, subtitle, fadeIn, stay, fadeOut, Arrays.asList(players));
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Collection<Player> players) {
        try {
            Object titleC = a.invoke(null, "{\"text\": \"" + title + "\"}");
            Object subtitleC = a.invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Object timesPacket = packetPlayOutTimes.newInstance(enumTimes, null, fadeIn, stay, fadeOut);
            Object titlePacket = packetPlayOutTitle.newInstance(enumTitle, titleC);
            Object subtitlePacket = packetPlayOutTitle.newInstance(enumSubtitle, subtitleC);
            for (Player p : players) {
                if (p == null || !p.isOnline()) continue;
                sendPacket(p, timesPacket);
                sendPacket(p, titlePacket);
                sendPacket(p, subtitlePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveDragon(Entity ent, double x, double y, double z, float yaw, float pitch) {
        if (ent == null) return;
        try {
            Object handle = ent.getClass().getMethod("getHandle").invoke(ent);
            position.invoke(handle, x, y, z, yaw, pitch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}