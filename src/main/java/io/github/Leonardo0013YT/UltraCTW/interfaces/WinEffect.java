package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.entity.Player;

public interface WinEffect {

    void start(Player p, Game game);

    void stop();

    WinEffect clone();

}