package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.LProtection.InjectionLProtection;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Injection;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class InjectionManager {

    private UltraCTW plugin;
    private File injections;
    private Injection lobbyProtect;

    public InjectionManager(UltraCTW plugin) {
        this.plugin = plugin;
        injections = new File(plugin.getDataFolder(), "injections");
        if (!injections.exists()) {
            injections.mkdirs();
        }
    }

    public void loadWEInjection() {
        File we;
        if (plugin.getVc().is1_13to16()) {
            we = new File(injections, "UltraCTW-WENew.jar");
            plugin.sendLogMessage("§eInjection §bUltraCTW §aWorldEdit §71.13 - 1.16 §eloaded correctly!");
        } else {
            we = new File(injections, "UltraCTW-WEOld.jar");
            plugin.sendLogMessage("§eInjection §bUltraCTW §aWorldEdit §71.8.8 §eloaded correctly!");
        }
        if (!we.exists()) {
            plugin.sendLogMessage("§cYou must put which version of WorldEdit Addon you'll be using,", "§cuse the UltraCTW-WEOld.jar injection for WorldEdit 1.8 through 1.12", "§cand UltraCTW-WENew.jar for WorldEdit 1.13 through 1.15.");
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }
        loadJarFile(we);
    }

    public void loadInjections() {
        File lp = new File(injections, "UltraCTW-LobbyProtect.jar");
        if (lp.exists()) {
            loadJarFile(lp);
            lobbyProtect = new InjectionLProtection();
            lobbyProtect.loadInjection(plugin);
            plugin.sendLogMessage("§eInjection §bUltraCTW §aLobby Protect§e loaded correctly!");
        }
    }

    public void reload() {
        File lp = new File(injections, "UltraCTW-LobbyProtect.jar");
        if (lobbyProtect == null) {
            if (lp.exists()) {
                loadJarFile(lp);
                lobbyProtect = new InjectionLProtection();
                lobbyProtect.loadInjection(plugin);
                plugin.sendLogMessage("§eInjection §bUltraCTW §aLobby Protect§e loaded correctly!");
            }
        } else {
            if (!lp.exists()) {
                lobbyProtect = null;
                plugin.sendLogMessage("§cInjection §bUltraCTW §aLobby Protect§c unloaded correctly!");
            } else {
                lobbyProtect.reload();
            }
        }
    }

    private void loadJarFile(File jar) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Class<?> getClass = classLoader.getClass();
            Method method = getClass.getSuperclass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, jar.toURI().toURL());
        } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}