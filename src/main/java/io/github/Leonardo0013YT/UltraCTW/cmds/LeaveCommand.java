package io.github.Leonardo0013YT.UltraCTW.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;

public class LeaveCommand implements CommandExecutor{

    private UltraCTW plugin;

    public LeaveCommand(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (plugin.getGm().isPlayerInGame(p)) {
                    plugin.getGm().removePlayerGame(p, true);
                    p.sendMessage(plugin.getLang().get("messages.leaveGame"));
                } 
            }
        return false;
    }
}
