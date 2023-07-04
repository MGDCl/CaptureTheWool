package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {
    private final UltraCTW plugin;
    public JoinCommand(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {//TODO agregar BungeeMode
            Player p = (Player)sender;
            if (!plugin.getGm().isPlayerInGame(p)) {
                p.sendMessage(plugin.getLang().get("messages.notInGame"));
                return true;
            }
            Game game = plugin.getGm().getSelectedGame();
            Team team = game.getTeamPlayer(p);
            if (game.getPlayers().size() >= game.getMax()) {
                p.sendMessage(this.plugin.getLang().get("messages.maxPlayers"));
                return true;
            }
            if (team == null){
                plugin.getGem().createTeamsMenu(p, game);
            } else {
                p.sendMessage(plugin.getLang().get("messages.alreadyTeam"));
            }
        }
        return false;
    }
}
