package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.State;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCommand implements CommandExecutor {
    private final UltraCTW plugin;
    public ShoutCommand(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String cmdlabel, String[] args) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                Game game = plugin.getGm().getSelectedGame();
                Team team = game.getTeamPlayer(p);
                if (!plugin.getGm().isPlayerInGame(p)) {
                    p.sendMessage(plugin.getLang().get("messages.notInGame"));
                    return true;
                }
                if (game.isState(State.WAITING) || game.isState(State.STARTING) || game.isState(State.RESTARTING)) {
                    p.sendMessage(plugin.getLang().get(p, "messages.noIngame"));
                    return true;
                }
                if (team == null){
                    p.sendMessage(plugin.getLang().get("messages.notInGame"));
                    return true;
                }
                if (args.length == 0) {
                    p.sendMessage("Usa /shout <msg> para escribir en global");
                } else {
                    sendGlobalMsg(args, p);
                }
            } else {
                sender.sendMessage("No puedes usar comando desde la consola");
            }
        return false;
    }

    private void sendGlobalMsg(final String[] msgList, final Player p) {
        Game game = plugin.getGm().getSelectedGame();
        Team team = game.getTeamPlayer(p);
        Bukkit.getScheduler().runTaskAsynchronously(UltraCTW.get(), () -> {
            StringBuilder msg = new StringBuilder();
            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = msgList).length, b = 0; b < i; ) {
                String s = arrayOfString[b];
                msg.append(s).append(" ");
                b++;
            }
            String mFormat = plugin.getLang().get(p, "chat.global").replaceAll("<team>", team.getPrefix()).replaceAll("<color>", team.getColor() + "").replaceAll("<player>", p.getName());
            mFormat = mFormat.replaceAll("<msg>", msg.toString());
            for (Player on : game.getCached()) {
                on.sendMessage(mFormat);
            }
        });
    }
}


