package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public interface IDatabase {

    void loadMultipliers(Request request);

    void createMultiplier(String type, String name, double amount, long ending, Request request);

    boolean removeMultiplier(int id);

    void loadTopCaptured();

    void loadTopKills();

    void loadTopWins();

    void loadTopBounty();

    void createPlayer(UUID uuid, String name, CTWPlayer ctw);

    void loadPlayer(Player p);

    void savePlayer(UUID uuid, boolean sync);

    void close();

    HashMap<UUID, CTWPlayer> getPlayers();

    CTWPlayer getCTWPlayer(Player p);
}