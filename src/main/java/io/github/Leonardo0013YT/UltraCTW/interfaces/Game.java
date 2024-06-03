package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Squared;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface Game {

    void addPlayer(Player p);

    void removePlayer(Player p);

    void checkStart();

    void reset();

    void setSpect(Player p);

    void update();

    void checkWin();

    void checkCancel();

    void cancel();

    void win(Team team);

    void addKill(Player p, boolean bowKill);

    void addDeath(Player p);

    void sendGameMessage(String msg);

    void sendGameTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

    void sendGameActionBar(Player p, String msg);

    void sendGameSound(Sound sound);

    int getTime();

    void CycleMap(Game game);

    boolean isState(State state);

    boolean isNearby(Location loc);

    void addPlayerRandomTeam(Player p);

    void addPlayerTeam(Player p, Team team);

    ArrayList<Location> getPlaced();

    void addWinEffects(WinEffect e);

    void addWinDance(WinDance e);

    void addKillEffects(KillEffect e);

    void removePlayerTeam(Player p, Team team);

    GamePlayer getGamePlayer(Player p);

    Team getTeamByID(int id);

    Team getTeamByColor(ChatColor color);

    Team getTeamByWool(ChatColor color);

    void checkTeamBalance();

    void joinRandomTeam(Player p);

    void removePlayerAllTeam(Player p);

    Team getLastTeam();

    int getTeamAlive();

    Team getTeamPlayer(Player p);

    void givePlayerItems(Player p);

    Squared getPlayerSquared(Player p);

    Squared getPlayerSquared(Location loc);

    int getMax();

    int getId();

    String getName();

    String getSchematic();

    HashSet<Player> getCached();

    HashSet<Player> getPlayers();

    HashSet<Player> getSpectators();

    HashMap<ChatColor, Team> getTeams();

    HashMap<Integer, ChatColor> getTeamsID();

    HashMap<Player, GamePlayer> getGamePlayer();

    ArrayList<Squared> getProtection();

    ArrayList<WinEffect> getWinEffects();

    ArrayList<WinDance> getWinDances();

    ArrayList<KillEffect> getKillEffects();

    Location getLobby();

    Location getSpectator();

    int getTeamSize();

    int getWoolSize();

    int getMin();

    int getStarting();

    State getState();

    void setState(State state);

    int getDefKit();

    HashMap<Location, ItemStack> getWools();

    HashSet<Player> getInLobby();

    HashSet<Player> getInGame();

    Squared getLobbyProtection();

    void setStarting(int i);
}