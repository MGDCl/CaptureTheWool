package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import org.bukkit.entity.Player;

public interface WinEffect {

    void start(Player p, Game game);

    void start(Player p, GameFlag game);

    void stop();

    WinEffect clone();

}