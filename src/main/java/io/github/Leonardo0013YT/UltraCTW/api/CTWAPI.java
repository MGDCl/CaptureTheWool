package io.github.Leonardo0013YT.UltraCTW.api;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.entity.Player;

public class CTWAPI {

    private static final UltraCTW plugin = UltraCTW.get();

    public static boolean isInLobby(Player p) {
        return !plugin.getGm().isPlayerInGame(p);
    }

}