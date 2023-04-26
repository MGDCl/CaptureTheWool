package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {
    private UltraCTW plugin;
    public JoinCommand(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!plugin.getGm().isPlayerInGame(p)) {
                p.sendMessage(plugin.getLang().get("messages.notInGame"));
                return true;
            }
            Game selected3 = plugin.getGm().getSelectedGame();
            Team team = selected3.getTeamPlayer(p);
            if (selected3.getPlayers().size() >= selected3.getMax()) {
                p.sendMessage(this.plugin.getLang().get("messages.maxPlayers"));
                return true;
            }
            if (team == null){
                plugin.getGem().createTeamsMenu(p, selected3);
            } else {
                p.sendMessage(plugin.getLang().get("messages.alreadyTeam"));
            }
        }
        return false;
    }
}
