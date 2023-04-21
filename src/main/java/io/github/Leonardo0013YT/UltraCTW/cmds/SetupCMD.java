package io.github.Leonardo0013YT.UltraCTW.cmds;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.*;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCMD implements CommandExecutor {

    private UltraCTW plugin;

    public SetupCMD(UltraCTW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("ctw.admin")) {
                p.sendMessage(plugin.getLang().get(p, "messages.noPermission"));
                return true;
            }
            if (args.length < 1) {
                sendHelp(sender);
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "delete":
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    String delete = args[1];
                    if (plugin.getSm().isDelete(p)) {
                        plugin.getArenas().set("arenas." + delete, null);
                        plugin.getArenas().save();
                        plugin.getSm().removeDelete(p);
                        p.sendMessage("§aYou've removed the map §e" + delete + "§a.");
                        return true;
                    }
                    plugin.getSm().setDelete(p, delete);
                    p.sendMessage("§cPlease config your delete executing §e/ctws delete " + delete + "§c.");
                    break;
                case "killsounds":
                    if (plugin.getSm().isSetupKillSound(p)) {
                        KillSoundSetup kss = plugin.getSm().getSetupKillSound(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                                new String[]{"<name>", kss.getName()}, new String[]{"<slot>", "" + kss.getSlot()}, new String[]{"<sound>", "" + kss.getSound().name()},
                                new String[]{"<vol1>", "" + kss.getVol1()}, new String[]{"<vol2>", "" + kss.getVol2()}, new String[]{"<price>", "" + kss.getPrice()},
                                new String[]{"<page>", "" + kss.getPage()}, new String[]{"<permission>", kss.getPermission()}, new String[]{"<name>", kss.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(kss.isBuy())});
                        return true;
                    }
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    String nameks = args[1];
                    KillSoundSetup kss = new KillSoundSetup(p, nameks);
                    plugin.getSm().setSetupKillSound(p, kss);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("killsounds"),
                            new String[]{"<name>", kss.getName()}, new String[]{"<slot>", "" + kss.getSlot()},
                            new String[]{"<sound>", "" + kss.getSound().name()}, new String[]{"<vol1>", "" + kss.getVol1()}, new String[]{"<vol2>", "" + kss.getVol2()},
                            new String[]{"<price>", "" + kss.getPrice()}, new String[]{"<page>", "" + kss.getPage()}, new String[]{"<permission>", kss.getPermission()},
                            new String[]{"<name>", kss.getName()}, new String[]{"<purchasable>", Utils.parseBoolean(kss.isBuy())});
                    p.sendMessage(plugin.getLang().get(p, "setup.killsounds.created").replaceAll("<name>", kss.getName()));
                    break;
                case "trails":
                    if (plugin.getSm().isSetupTrail(p)) {
                        TrailSetup ts = plugin.getSm().getSetupTrail(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<speed>", "" + ts.getSpeed()},
                                new String[]{"<offsetX>", "" + ts.getOffsetX()},
                                new String[]{"<offsetY>", "" + ts.getOffsetY()},
                                new String[]{"<offsetZ>", "" + ts.getOffsetZ()},
                                new String[]{"<amount>", "" + ts.getAmount()},
                                new String[]{"<range>", "" + ts.getRange()},
                                new String[]{"<particle>", ts.getParticle()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                        return true;
                    }
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    String namett = args[1];
                    TrailSetup tts = new TrailSetup(p, namett);
                    plugin.getSm().setSetupTrail(p, tts);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("trails"),
                            new String[]{"<name>", tts.getName()},
                            new String[]{"<slot>", "" + tts.getSlot()},
                            new String[]{"<price>", "" + tts.getPrice()},
                            new String[]{"<page>", "" + tts.getPage()},
                            new String[]{"<speed>", "" + tts.getSpeed()},
                            new String[]{"<offsetX>", "" + tts.getOffsetX()},
                            new String[]{"<offsetY>", "" + tts.getOffsetY()},
                            new String[]{"<offsetZ>", "" + tts.getOffsetZ()},
                            new String[]{"<amount>", "" + tts.getAmount()},
                            new String[]{"<range>", "" + tts.getRange()},
                            new String[]{"<particle>", tts.getParticle()},
                            new String[]{"<permission>", tts.getPermission()},
                            new String[]{"<name>", tts.getName()},
                            new String[]{"<purchasable>", Utils.parseBoolean(tts.isBuy())});
                    p.sendMessage(plugin.getLang().get(p, "setup.trails.created").replaceAll("<name>", tts.getName()));
                    break;
                case "taunts":
                    if (plugin.getSm().isSetupTaunt(p)) {
                        TauntSetup ts = plugin.getSm().getSetupTaunt(p);
                        plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                                new String[]{"<title>", ts.getTitle()},
                                new String[]{"<subtitle>", ts.getSubtitle()},
                                new String[]{"<name>", ts.getName()},
                                new String[]{"<player>", ts.getPlayer()},
                                new String[]{"<none>", ts.getNone()},
                                new String[]{"<slot>", "" + ts.getSlot()},
                                new String[]{"<price>", "" + ts.getPrice()},
                                new String[]{"<page>", "" + ts.getPage()},
                                new String[]{"<permission>", ts.getPermission()},
                                new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                        return true;
                    }
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    String namet = args[1];
                    TauntSetup ts = new TauntSetup(plugin, p, namet);
                    plugin.getSm().setSetupTaunt(p, ts);
                    plugin.getUim().openInventory(p, plugin.getUim().getMenus("taunts"),
                            new String[]{"<title>", ts.getTitle()},
                            new String[]{"<subtitle>", ts.getSubtitle()},
                            new String[]{"<name>", ts.getName()},
                            new String[]{"<player>", ts.getPlayer()},
                            new String[]{"<none>", ts.getNone()},
                            new String[]{"<slot>", "" + ts.getSlot()},
                            new String[]{"<price>", "" + ts.getPrice()},
                            new String[]{"<page>", "" + ts.getPage()},
                            new String[]{"<permission>", ts.getPermission()},
                            new String[]{"<purchasable>", Utils.parseBoolean(ts.isBuy())});
                    p.sendMessage(plugin.getLang().get(p, "setup.taunts.created").replaceAll("<name>", ts.getName()));
                    break;
                case "addshopitem":
                    if (args.length < 2) {
                        sendHelp(sender);
                        return true;
                    }
                    if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                        p.sendMessage(plugin.getLang().get("setup.itemInHand"));
                        return true;
                    }
                    double price;
                    try {
                        price = Double.parseDouble(args[1]);
                    } catch (NumberFormatException e) {
                        p.sendMessage(plugin.getLang().get("setup.noNumber"));
                        return true;
                    }
                    plugin.getShm().addItem(p.getItemInHand(), price);
                    p.sendMessage(plugin.getLang().get("setup.addItem"));
                    break;
                case "addshop":
                    if (!plugin.getSm().isSetup(p)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.alreadyCreating"));
                        return true;
                    }
                    ArenaSetup as = plugin.getSm().getSetup(p);
                    as.getNpcShop().add(Utils.getLocationString(p.getLocation()));
                    p.sendMessage(plugin.getLang().get("setup.arena.setNPCShop"));
                    break;
                case "addkits":
                    if (!plugin.getSm().isSetup(p)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.alreadyCreating"));
                        return true;
                    }
                    ArenaSetup as1 = plugin.getSm().getSetup(p);
                    as1.getNpcKits().add(Utils.getLocationString(p.getLocation()));
                    p.sendMessage(plugin.getLang().get("setup.arena.setNPCKits"));
                    break;
                case "create":
                    if (args.length < 3) {
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getSm().isSetup(p)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.alreadyCreating"));
                        return true;
                    }
                    String name = args[1];
                    String schematic;
                    if (args[2].endsWith(".schematic")) {
                        schematic = args[2];
                    } else {
                        schematic = args[2] + ".schematic";
                    }
                    if (!Utils.existsFile(schematic)) {
                        p.sendMessage(plugin.getLang().get(p, "setup.noSchema"));
                        return true;
                    }
                    plugin.getSm().setSetup(p, new ArenaSetup(plugin, p, name, schematic));
                    World w = plugin.getWc().createEmptyWorld(name);
                    w.getBlockAt(0, 75, 0).setType(Material.STONE);
                    w.setSpawnLocation(0, 75, 0);
                    plugin.getWc().resetMap(w.getSpawnLocation(), schematic);
                    p.teleport(w.getSpawnLocation());
                    p.getInventory().remove(plugin.getIm().getSetup());
                    p.getInventory().remove(plugin.getIm().getPoints());
                    p.getInventory().addItem(plugin.getIm().getPoints());
                    p.getInventory().addItem(plugin.getIm().getSetup());
                    break;
                case "kits":
                    if (args.length < 2) {
                        sendHelp(sender);
                        return true;
                    }
                    if (plugin.getSm().isSetupKit(p)) {
                        plugin.getSem().createSetupKitMenu(p, plugin.getSm().getSetupKit(p));
                        return true;
                    }
                    String kitName = args[1];
                    p.sendMessage(plugin.getLang().get(p, "setup.kits.created"));
                    plugin.getSm().setSetupKit(p, new KitSetup(plugin, kitName));
                    plugin.getSem().createSetupKitMenu(p, plugin.getSm().getSetupKit(p));
                    break;
                case "setmainlobby":
                    plugin.getConfig().set("mainLobby", Utils.getLocationString(p.getLocation()));
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    plugin.getCm().reload();
                    p.sendMessage(plugin.getLang().get("setup.setMainLobby"));
                    break;
                case "reload":
                    plugin.reload();
                    p.sendMessage(plugin.getLang().get("setup.reload"));
                    break;
                case "inventory":
                    if (args.length < 2) {
                        sendHelp(p);
                        return true;
                    }
                    switch (args[1].toLowerCase()) {
                        case "setup":
                        case "lobby":
                        case "taunts":
                        case "tauntstype":
                        case "killsounds":
                        case "trails":
                        case "teamsetup":
                            UltraInventory inv = plugin.getUim().getMenus(args[1].toLowerCase());
                            plugin.getUim().openInventory(p, inv);
                            plugin.getSm().setSetupInventory(p, inv);
                            break;
                        default:
                            p.sendMessage("§cThe available menus are:");
                            p.sendMessage("§7 - §eSetup");
                            p.sendMessage("§7 - §eTrails");
                            p.sendMessage("§7 - §eKillSound");
                            p.sendMessage("§7 - §eTaunts");
                            p.sendMessage("§7 - §eTauntsType");
                            p.sendMessage("§7 - §eTeamSetup");
                            p.sendMessage("§7 - §eLobby");
                            break;
                    }
                    break;
                default:
                    sendHelp(sender);
                    break;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage("§7§m--------------------------------");
        s.sendMessage("§e/ctws setmainlobby §7- §aSet main lobby.");
        s.sendMessage("§e/ctws create <name> <schematic> §7- §aCreate a map.");
        s.sendMessage("§e/ctws delete <name> §7- §aDelete a map.");
        s.sendMessage("§e/ctws addshop §7- §aSet the shop npc.");
        s.sendMessage("§e/ctws addkits §7- §aSet the shop kit.");
        s.sendMessage("§e/ctw addshopitem <price> §7- §aYou must have the item in your hand.");
        s.sendMessage("§e/ctws kits <name> §7- §aCreate a kit.");
        s.sendMessage("§e/ctws taunts <name> §7- §aCreate a taunt.");
        s.sendMessage("§e/ctws trails <name> §7- §aCreate a trail.");
        s.sendMessage("§e/ctws killsounds <name> §7- §aCreate a sound.");
        s.sendMessage("§e/ctws inventory <type> §7- §aEdit an inventory.");
        s.sendMessage("§7§m--------------------------------");
    }

}