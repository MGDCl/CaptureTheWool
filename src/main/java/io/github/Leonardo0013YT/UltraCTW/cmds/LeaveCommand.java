package io.github.Leonardo0013YT.UltraCTW.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;

public class LeaveCommand implements CommandExecutor{

    private final UltraCTW plugin;

    public LeaveCommand(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!plugin.getGm().isPlayerInGame(p)) {
                p.sendMessage(plugin.getLang().get(p, "messages.noGame"));
                return true;
            }
            plugin.getGm().removePlayerGame(p, true);
            givePlayerItems(p);
            p.sendMessage(plugin.getLang().get(p, "messages.leaveGame"));
        }
        return false;
    }

    private void givePlayerItems(Player p) {
        if (plugin.getCm().isItemLobbyEnabled()) {
            p.getInventory().setItem(plugin.getCm().getItemLobbySlot(), plugin.getIm().getLobby());
        }
        if (plugin.getCm().isItemLobby2Enabled()) {
            p.getInventory().setItem(plugin.getCm().getItemLobby2Slot(), plugin.getIm().getLobby2());
        }
    }
}
