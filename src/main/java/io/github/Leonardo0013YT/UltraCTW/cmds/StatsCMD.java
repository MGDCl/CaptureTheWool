package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCMD implements CommandExecutor {
    private final UltraCTW plugin;
    public StatsCMD(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length != 1) {
                p.sendMessage(plugin.getLang().get(p, "stats"));
                return true;
            }
            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage(plugin.getLang().get("messages.notOnline"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            p.sendMessage(plugin.getLang().get(target, "stats"));
        }
        return false;
    }
}