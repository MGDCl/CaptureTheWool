package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.customs.CustomScoreboard;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.entity.Player;

public class ScoreboardManager {

    UltraCTW plugin;
    private final CustomScoreboard scoreboard = CustomScoreboard.instance();

    public ScoreboardManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void update(Player p) {
        if (p == null || !p.isOnline()) {
            return;
        }
        if (!plugin.getGm().isPlayerInGame(p) && plugin.getCm().isLobbyScoreboard()) {
            if (!scoreboard.hasBoard(p)) {
                scoreboard.createBoard(p, main(p, plugin.getLang().get(p, "scoreboards.main.title")));
            }
            scoreboard.getBoard(p).setAll(main(p, plugin.getLang().get(p, "scoreboards.main.lines")).split("\\n"));
            return;
        }
        if (!plugin.getGm().isPlayerInGame(p)) {
            return;
        }
        Game game = plugin.getGm().getGameByPlayer(p);
        if (game != null) {
            Team team = game.getTeamPlayer(p);
            if (game.isState(State.WAITING)) {
                if (!scoreboard.hasBoard(p)) {
                    scoreboard.createBoard(p, waiting(p, plugin.getLang().get(p, "scoreboards.waiting.title"), game));
                }
                scoreboard.getBoard(p).setAll(waiting(p, plugin.getLang().get(p, "scoreboards.waiting.lines"), game).split("\\n"));
            } else if (game.isState(State.STARTING)) {
                if (!scoreboard.hasBoard(p)) {
                    scoreboard.createBoard(p, starting(p, plugin.getLang().get(p, "scoreboards.starting.title"), game));
                }
                scoreboard.getBoard(p).setAll(starting(p, plugin.getLang().get(p, "scoreboards.starting.lines"), game).split("\\n"));
            } else {
                if (team == null) {
                    return;
                }
                GamePlayer gp = game.getGamePlayer(p);
                Team t1 = game.getTeamByID(0);
                Team t2 = game.getTeamByID(1);
                if (!scoreboard.hasBoard(p)) {
                    scoreboard.createBoard(p, simple(p, plugin.getLang().get(p, "scoreboards.simple-game.title"), game, team, gp, t1, t2).replaceAll("<tcolor>", team.getColor() + ""));
                }
                scoreboard.getBoard(p).setAll(simple(p, plugin.getLang().get(p, "scoreboards.simple-game.lines"), game, team, gp, t1, t2).split("\\n"));
            }
        }
    }

    public String main(Player p, String s) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<levelUp>", String.valueOf(level.getLevelUp()))
                .replace("<gcoins>", Utils.format(ctw.getCoins()))
                .replace("<date>", Utils.getDate())
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<wins>", String.valueOf(ctw.getWins()))
                .replace("<deaths>", String.valueOf(ctw.getDeaths()))
                .replace("<captured>", String.valueOf(ctw.getWoolCaptured()))
                .replace("<kills>", String.valueOf(ctw.getKills()));
    }

    public String waiting(Player p, String s, Game game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<levelUp>", String.valueOf(level.getLevelUp()))
                .replace("<id>", String.valueOf(game.getId()))
                .replace("<date>", Utils.getDate())
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName().replace("_", " "));
    }

    public String starting(Player p, String s, Game game) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<levelUp>", String.valueOf(level.getLevelUp()))
                .replace("<id>", String.valueOf(game.getId()))
                .replace("<date>", Utils.getDate())
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<time>", Utils.convertTime(game.getStarting()))
                .replace("<max>", String.valueOf(game.getMax()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<map>", game.getName().replace("_", " "));
    }

    public String simple(Player p, String s, Game game, Team team, GamePlayer gp, Team t1, Team t2) {
        CTWPlayer ctw = plugin.getDb().getCTWPlayer(p);
        if (ctw == null) return s;
        Level level = plugin.getLvl().getLevel(p);
        return s.replace("<levelUp>", String.valueOf(level.getLevelUp()))
                .replace("<date>", Utils.getDate())
                .replace("<id>", String.valueOf(game.getId()))
                .replace("<now>", String.valueOf(ctw.getXp()))
                .replace("<gcoins>", Utils.format(ctw.getCoins()))
                .replace("<coins>", Utils.format(gp.getCoins()))
                .replace("<players>", String.valueOf(game.getPlayers().size()))
                .replace("<time>", Utils.convertTime(game.getTime()))
                .replace("<map>", game.getName())
                .replace("<T1Wools>", Utils.getWoolsString(t1))
                .replace("<T2Wools>", Utils.getWoolsString(t2))
                .replace("<T1p>", String.valueOf(t1.getTeamSize()))
                .replace("<T2p>", String.valueOf(t2.getTeamSize()))
                .replace("<T1>", plugin.getLang().get("scoreboards.team").replace("<TColor>", t1.getColor() + "").replace("<TName>", t1.getName()))
                .replace("<T2>", plugin.getLang().get("scoreboards.team").replace("<TColor>", t2.getColor() + "").replace("<TName>", t2.getName()))
                .replace("<team>", team.getName())
                .replace("<kills>", String.valueOf(gp.getKills()))
                .replace("<deaths>", String.valueOf(gp.getDeaths()));
    }

    public void remove(Player p) {
        scoreboard.removeBoard(p);
    }

}