package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
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
            if (!this.plugin.getGm().isPlayerInGame(p)) {
                p.sendMessage(plugin.getLang().get("messages.notInGame"));
                return true;
            }
            if (p.getGameMode() == GameMode.SPECTATOR) {
                Game selected3 = this.plugin.getGm().getSelectedGame();
                if (selected3 == null) {
                    return true;
                }
                if (selected3.getPlayers().size() >= selected3.getMax()) {
                    p.sendMessage(this.plugin.getLang().get("messages.maxPlayers"));
                    return true;
                }
                this.plugin.getGem().createTeamsMenu(p, selected3);
            }
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.sendMessage(plugin.getLang().get("messages.alreadyTeam"));
            }
        }
        return false;
    }
}
