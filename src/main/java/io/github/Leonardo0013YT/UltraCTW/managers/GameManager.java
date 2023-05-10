package io.github.Leonardo0013YT.UltraCTW.managers;

import com.nametagedit.plugin.NametagEdit;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameNoState;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class GameManager {

    private HashMap<Integer, Game> games = new HashMap<>();
    private HashMap<String, Integer> gameNames = new HashMap<>();
    private HashMap<UUID, Integer> playerGame = new HashMap<>();
    private HashMap<String, Integer> players = new HashMap<>();
    private long lastUpdatePlayers;
    private Game selectedGame;
    private UltraCTW plugin;

    public GameManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        games.clear();
        gameNames.clear();
        if (!plugin.getArenas().isSet("arenas")) return;
        int id = 0;
        for (String s : plugin.getArenas().getConfig().getConfigurationSection("arenas").getKeys(false)) {
            String type = plugin.getArenas().getOrDefault("arenas." + s + ".type", "NORMAL");
            if (type.equals("NORMAL")) {
                Game game = new GameNoState(plugin, "arenas." + s, id);
                games.put(id, game);
                gameNames.put(game.getName(), id);
                plugin.sendLogMessage("§aMap §e" + s + " §aloaded correctly.");
            }
            id++;
        }
        reset();
    }

    public void reset() {
        if (games.isEmpty()) {
            return;
        }
        Game selectedGame = new ArrayList<>(games.values()).get(ThreadLocalRandom.current().nextInt(0, games.values().size()));
        setSelectedGame(selectedGame);
    }

    public void reset(Game game) {
        if (games.isEmpty()) {
            return;
        }
        ArrayList<Game> back = new ArrayList<>(games.values());
        if (games.size() != 1) {
            back.remove(game);
        }
        Game selectedGame = new ArrayList<>(back).get(ThreadLocalRandom.current().nextInt(0, back.size()));
        setSelectedGame(selectedGame);
    }

    public int getGameSize(String type) {
        if (lastUpdatePlayers + plugin.getCm().getUpdatePlayersPlaceholder() < System.currentTimeMillis()) {
            updatePlayersPlaceholder();
        }
        return players.getOrDefault(type, 0);
    }

    public void updatePlayersPlaceholder() {
        if (getSelectedGame() != null) {
            players.put("wool", getSelectedGame().getPlayers().size());
        } else {
            players.put("wool", 0);
        }
        lastUpdatePlayers = System.currentTimeMillis();
    }

    public Game getSelectedGame() {
        return selectedGame;
    }

    public void setSelectedGame(Game selectedGame) {
        this.selectedGame = selectedGame;
    }

    public Game getGameByName(String name) {
        return games.get(gameNames.get(name));
    }

    public Game getGameByPlayer(Player p) {
        return games.get(playerGame.get(p.getUniqueId()));
    }

    public void addPlayerGame(Player p, int id) {
        Game game = games.get(id);
        playerGame.put(p.getUniqueId(), id);
        game.addPlayer(p);
    }

    public void removePlayerGame(Player p, boolean toLobby) {
        if (!playerGame.containsKey(p.getUniqueId())) return;
        int id = playerGame.get(p.getUniqueId());
        Game game = games.get(id);
        if (game != null) {
            game.removePlayer(p);
        }
        NametagEdit.getApi().clearNametag(p);
        playerGame.remove(p.getUniqueId());
        Utils.updateSB(p);
        if (toLobby) {
            if (plugin.getCm().getMainLobby() != null) {
                if (plugin.getCm().getMainLobby().getWorld() != null) {
                    p.teleport(plugin.getCm().getMainLobby());
                }
            }
        }
    }

    public boolean isPlayerInGame(Player p) {
        return playerGame.containsKey(p.getUniqueId());
    }

}