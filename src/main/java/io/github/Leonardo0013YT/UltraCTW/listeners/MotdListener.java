package io.github.Leonardo0013YT.UltraCTW.listeners;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    private final UltraCTW plugin;

    public MotdListener(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMotd(ServerListPingEvent e) {
        Game g = plugin.getGm().getBungee();
        if (g == null) return;
        e.setMotd(plugin.getLang().get("motds." + g.getState().name()).replace("<max>", String.valueOf(g.getMax())).replace("<players>", String.valueOf(g.getPlayers().size())).replace("<map>", g.getName()));
    }

}
