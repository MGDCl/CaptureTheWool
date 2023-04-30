package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CTWCMD implements CommandExecutor {

    private UltraCTW plugin;

    public CTWCMD(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length < 1) {
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "gcoins":
                    if (args.length < 4) {
                        sendHelp(sender);
                        return true;
                    }
                    if (!p.hasPermission("ctw.admin")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.noPermission"));
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "add":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw = plugin.getDb().getCTWPlayer(on);
                            sw.addCoins(amount);
                            p.sendMessage(plugin.getLang().get(p, "coins.add.you").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<player>", on.getName()));
                            on.sendMessage(plugin.getLang().get(p, "coins.add.receiver").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on);
                            break;
                        case "remove":
                            Player on1 = Bukkit.getPlayer(args[2]);
                            if (on1 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount1;
                            try {
                                amount1 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw1 = plugin.getDb().getCTWPlayer(on1);
                            sw1.removeCoins(amount1);
                            p.sendMessage(plugin.getLang().get(p, "coins.remove.you").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<player>", on1.getName()));
                            on1.sendMessage(plugin.getLang().get(p, "coins.remove.receiver").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on1);
                            break;
                        case "set":
                            Player on2 = Bukkit.getPlayer(args[2]);
                            if (on2 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            int amount2;
                            try {
                                amount2 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw2 = plugin.getDb().getCTWPlayer(on2);
                            sw2.setCoins(amount2);
                            p.sendMessage(plugin.getLang().get(p, "coins.set.you").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<player>", on2.getName()));
                            on2.sendMessage(plugin.getLang().get(p, "coins.set.receiver").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<sender>", p.getName()));
                            Utils.updateSB(on2);
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                    break;
                case "join":
                    if (plugin.getGm().isPlayerInGame(p)) {
                        p.sendMessage(plugin.getLang().get("messages.alreadyIngame"));
                        return true;
                    }
                    Game game = plugin.getGm().getSelectedGame();
                    plugin.getGem().createJoinMenu(p, game);
                    break;
                case "kitsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    Game game2 = plugin.getGm().getGameByPlayer(p);
                    if (game2 == null) {
                        return true;
                    }
                    plugin.getUim().createKitSelectorMenu(p);
                    break;
                case "killsoundsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createKillSoundSelectorMenu(p);
                    break;
                case "killeffectsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createKillEffectSelectorMenu(p);
                    break;
                case "windancesmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createWinDanceSelectorMenu(p);
                    break;
                case "wineffectsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createWinEffectSelectorMenu(p);
                    break;
                case "trailsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createTrailsSelectorMenu(p);
                    break;
                case "tauntsmenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createTauntsSelectorMenu(p);
                    break;
                case "shopkeepermenu":
                    plugin.getUim().getPages().put(p, 1);
                    plugin.getUim().createShopKeeperSelectorMenu(p);
                    break;
                case "multiplier":
                    if (!p.hasPermission("ctw.admin")) {
                        p.sendMessage(plugin.getLang().get(p, "messages.noPermission"));
                        return true;
                    }
                    if (args.length < 5) {
                        sendHelp(sender);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "coins":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            double amount;
                            try {
                                amount = Double.parseDouble(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            int seconds;
                            try {
                                seconds = Integer.parseInt(args[4]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            plugin.getDb().createMultiplier("COINS", on.getName(), amount, System.currentTimeMillis() + (seconds * 1000), b -> {
                                if (b) {
                                    plugin.getDb().loadMultipliers(b1 -> {
                                        if (b1) {
                                            p.sendMessage(plugin.getLang().get(p, "messages.multiplier").replaceAll("<type>", "Coins").replace("<name>", on.getName()).replace("<amount>", String.valueOf(amount)).replace("<time>", Utils.convertTime(seconds)));
                                        }
                                    });
                                }
                            });
                            break;
                        case "xp":
                            Player on3 = Bukkit.getPlayer(args[2]);
                            if (on3 == null) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noOnline"));
                                return true;
                            }
                            double amount3;
                            try {
                                amount3 = Double.parseDouble(args[3]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            int seconds3;
                            try {
                                seconds3 = Integer.parseInt(args[4]);
                            } catch (NumberFormatException e) {
                                p.sendMessage(plugin.getLang().get(p, "setup.noNumber"));
                                return true;
                            }
                            plugin.getDb().createMultiplier("XP", on3.getName(), amount3, System.currentTimeMillis() + (seconds3 * 1000), b -> {
                                if (b) {
                                    plugin.getDb().loadMultipliers(b1 -> {
                                        if (b1) {
                                            p.sendMessage(plugin.getLang().get(p, "messages.multiplier").replaceAll("<type>", "XP").replace("<name>", on3.getName()).replace("<amount>", String.valueOf(amount3)).replace("<time>", Utils.convertTime(seconds3)));
                                        }
                                    });
                                }
                            });
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                    break;
                default:
                    sendHelp(sender);
                    break;
            }
        } else {
            if (args.length < 1) {
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "gcoins":
                    if (args.length < 4) {
                        sendHelp(sender);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "add":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw = plugin.getDb().getCTWPlayer(on);
                            sw.addCoins(amount);
                            sender.sendMessage(plugin.getLang().get("coins.add.you").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<player>", on.getName()));
                            on.sendMessage(plugin.getLang().get("coins.add.receiver").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on);
                            break;
                        case "remove":
                            Player on1 = Bukkit.getPlayer(args[2]);
                            if (on1 == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount1;
                            try {
                                amount1 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw1 = plugin.getDb().getCTWPlayer(on1);
                            sw1.removeCoins(amount1);
                            sender.sendMessage(plugin.getLang().get("coins.remove.you").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<player>", on1.getName()));
                            on1.sendMessage(plugin.getLang().get("coins.remove.receiver").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on1);
                            break;
                        case "set":
                            Player on2 = Bukkit.getPlayer(args[2]);
                            if (on2 == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount2;
                            try {
                                amount2 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            CTWPlayer sw2 = plugin.getDb().getCTWPlayer(on2);
                            sw2.setCoins(amount2);
                            sender.sendMessage(plugin.getLang().get("coins.set.you").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<player>", on2.getName()));
                            on2.sendMessage(plugin.getLang().get("coins.set.receiver").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on2);
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                    break;
                case "coins":
                    if (args.length < 4) {
                        sendHelp(sender);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "add":
                            Player on = Bukkit.getPlayer(args[2]);
                            if (on == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            Game gpgg = plugin.getGm().getGameByPlayer(on);
                            GamePlayer gp;
                            if (gpgg != null) {
                                gp = gpgg.getGamePlayer(on);
                            }
                            sender.sendMessage(plugin.getLang().get("coins.add.you").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<player>", on.getName()));
                            on.sendMessage(plugin.getLang().get("coins.add.receiver").replaceAll("<coins>", String.valueOf(amount)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on);
                            break;
                        case "remove":
                            Player on1 = Bukkit.getPlayer(args[2]);
                            if (on1 == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount1;
                            try {
                                amount1 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            Game gpgg1 = plugin.getGm().getGameByPlayer(on1);
                            GamePlayer gp1;
                            if (gpgg1 != null) {
                                gp1 = gpgg1.getGamePlayer(on1);
                            }
                            sender.sendMessage(plugin.getLang().get("coins.remove.you").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<player>", on1.getName()));
                            on1.sendMessage(plugin.getLang().get("coins.remove.receiver").replaceAll("<coins>", String.valueOf(amount1)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on1);
                            break;
                        case "set":
                            Player on2 = Bukkit.getPlayer(args[2]);
                            if (on2 == null) {
                                sender.sendMessage(plugin.getLang().get("setup.noOnline"));
                                return true;
                            }
                            int amount2;
                            try {
                                amount2 = Integer.parseInt(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(plugin.getLang().get("setup.noNumber"));
                                return true;
                            }
                            Game gpgg2 = plugin.getGm().getGameByPlayer(on2);
                            GamePlayer gp2;
                            if (gpgg2 != null) {
                                gp2 = gpgg2.getGamePlayer(on2);
                            }
                            sender.sendMessage(plugin.getLang().get("coins.set.you").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<player>", on2.getName()));
                            on2.sendMessage(plugin.getLang().get("coins.set.receiver").replaceAll("<coins>", String.valueOf(amount2)).replaceAll("<sender>", sender.getName()));
                            Utils.updateSB(on2);
                            break;
                        default:
                            sendHelp(sender);
                            break;
                    }
                    break;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender p) {
        for (String s : plugin.getLang().getList("messages.help")){
            p.sendMessage(s.replaceAll("&", "§"));
        }
    }

}