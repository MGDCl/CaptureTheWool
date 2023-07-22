package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class GameWin {

    private String winner = "";
    private Team teamWin;
    private TreeMap<String, Integer> sorted_map;
    private final Game game;

    public GameWin(Game game) {
        this.game = game;
        HashMap<String, Integer> map = new HashMap<>();
        ValueComparator bvc = new ValueComparator(map);
        sorted_map = new TreeMap<>(bvc);
        for (GamePlayer gp : game.getGamePlayer().values()) {
            map.put(gp.getP().getName(), gp.getKills());
        }
        sorted_map.putAll(map);
    }

    public String getWinner() {
        if (!winner.equals("")) {
            return winner;
        }
        if (teamWin == null) {
            return winner = "No present";
        }
        return winner = teamWin.getName();
    }
    public String Members() {
        if (!winner.equals("")) {
            return winner;
        }
        if (teamWin == null) {
            winner = "No present";
            return winner;
        }
        winner = getWinnerString(teamWin.getMembers());
        return winner;
    }

    public String getWinnerString(Collection<Player> players) {
        StringBuilder winner = new StringBuilder();
        int act = 0;
        int size = players.size();
        for (Player p : players) {
            if (act == size - 1) {
                winner.append(p.getName());
            } else {
                winner.append(p.getName()).append(", ");
            }
            act++;
        }
        return winner.toString();
    }

    public Team getTeamWin() {
        return teamWin;
    }

    public void setTeamWin(Team teamWin) {
        this.teamWin = teamWin;
    }

    public List<String> getTop() {
        List<String> tops = new ArrayList<>(Arrays.asList("none:0:RESET", "none:0:RESET", "none:0:RESET"));
        int top = 0;
        for (String key : sorted_map.keySet()) {
            String color = "RESET";
            Player on = Bukkit.getPlayer(key);
            if (on != null && game.getPlayers().contains(on)) {
                Team team = game.getTeamPlayer(on);
                if (team == null) continue;
                color = team.getColor().name();
            }
            tops.set(top, key + ":" + sorted_map.ceilingEntry(key).getValue() + ":" + color);
            top++;
            if (top >= 3) {
                break;
            }
        }
        return tops;
    }

    static class ValueComparator implements Comparator<String> {

        Map<String, Integer> base;

        public ValueComparator(Map<String, Integer> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

}