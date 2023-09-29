package io.github.Leonardo0013YT.UltraCTW.controllers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NMS;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import io.github.Leonardo0013YT.UltraCTW.nms.*;
import io.github.Leonardo0013YT.UltraCTW.nms.npc.*;
import org.bukkit.Bukkit;

public class VersionController {

    private final UltraCTW plugin;
    private String version;
    private NMS nms;
    private final boolean is1_13to15 = false;
    private final boolean is1_9to15 = false;
    private final NMSReflection reflection;

    public VersionController(UltraCTW plugin) {
        this.plugin = plugin;
        setupVersion();
        this.reflection = new NMSReflection();
    }

    private void setupVersion() {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            switch (version) {
                case "v1_8_R3":
                    nms = new NMS_v1_8_r3();
                    break;
                case "v1_9_R1":
                    plugin.sendLogMessage("§cYou have an outdated version §e1.9§c, please use version §a1.9.4§c.");
                    disable();
                    break;
                default:
                    plugin.sendLogMessage("§cYou have an outdated version §e1.8§c, please use version §a1.8.8§c.");
                    disable();
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public NPC createNewNPC() {
        if (version.equals("v1_8_R3")) {
            return new NPC_v1_8_r3(plugin);
        } else {
            plugin.sendLogMessage("§cYou have an outdated version §e1.8§c, please use version §a1.8.8§c.");
            disable();
        }
        return null;
    }

    public NMSReflection getReflection() {
        return reflection;
    }

    public void disable() {
        Bukkit.getScheduler().cancelTasks(plugin);
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public NMS getNMS() {
        return nms;
    }

    public String getVersion() {
        return version;
    }


    public boolean is1_13to16() {
        return is1_13to15;
    }

    public boolean is1_9to15() {
        return this.is1_9to15;
    }
}