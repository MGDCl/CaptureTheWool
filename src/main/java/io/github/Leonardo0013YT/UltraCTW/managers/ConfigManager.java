package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;

@Getter
public class ConfigManager {

    private final UltraCTW plugin;
    private boolean kickOnStarted, sendLobbyOnQuit, bungeeModeEnabled, bungeeModeAutoJoin, bungeeModeKickOnFinish, wCMDEnabled, lCMDEnabled, kCMDEnabled, dCMDEnabled, statsCMD, autoJoinFinish, mobGriefing, totalBreak,instaKillOnVoidCTW, lobbyScoreboard, hungerCTW, breakMap, kitLevelsOrder, excluideDefKits, itemLobbyEnabled, itemLobby2Enabled, placeholdersAPI, redPanelInLocked, broadcastGame, joinMessage;
    private Location mainLobby;
    private short redPanelData;
    private Material back, redPanelMaterial;
    private Sound streak2, streak3, streak4, streak5, upgradeSound, cancelStartSound, wineffectschicken, wineffectsvulcanfire, wineffectvulcanwool, wineffectnotes, killEffectTNT, killEffectSquid;
    private XSound pickUpTeam, pickUpOthers, captured;
    private int gCoinsPickup, xpPickup, coinsPickup, ironGenerating, updatePlayersPlaceholder, limitOfYSpawn, itemLobbySlot, itemLobby2Slot, maxMultiplier, gCoinsKills, gCoinsWins, gCoinsAssists, gCoinsCapture, coinsKill, coinsWin, coinsAssists, coinsCapture, xpKill, xpWin, xpAssists, xpCapture, starting, progressBarAmount, timeToKill;
    private double bountyMin, bountyMax, bountyPerKill;
    private String bungeeModeLobbyServer, itemLobbyCMD, itemLobby2CMD;
    private List<String> winCommands, levelCommands, killCommands, deathCommands;
    private List<String> whitelistedCMD, noDrop, breakBypass;

    public ConfigManager(UltraCTW plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        this.wCMDEnabled = plugin.getConfig().getBoolean("win-commands.enabled");
        this.lCMDEnabled = plugin.getConfig().getBoolean("level-commands.enabled");
        this.kCMDEnabled = plugin.getConfig().getBoolean("kill-commands.enabled");
        this.dCMDEnabled = plugin.getConfig().getBoolean("death-commands.enabled");
        this.winCommands = plugin.getConfig().getStringList("win-commands.cmds");
        this.levelCommands = plugin.getConfig().getStringList("level-commands.cmds");
        this.killCommands = plugin.getConfig().getStringList("kill-commands.cmds");
        this.deathCommands = plugin.getConfig().getStringList("death-commands.cmds");
        this.sendLobbyOnQuit = plugin.getConfig().getBoolean("bungeeMode.sendLobbyOnQuit");
        this.kickOnStarted = plugin.getConfig().getBoolean("bungeeMode.kickOnStarted");
        this.bungeeModeEnabled = plugin.getConfig().getBoolean("bungeeMode.enabled");
        this.bungeeModeAutoJoin = plugin.getConfig().getBoolean("bungeeMode.autoJoin");
        this.bungeeModeKickOnFinish = plugin.getConfig().getBoolean("bungeeMode.kickOnFinish");
        this.bungeeModeLobbyServer = plugin.getConfig().getString("bungeeMode.lobbyServer");
        this.statsCMD = plugin.getConfig().getBoolean("statsCMD");
        this.mobGriefing = plugin.getConfig().getBoolean("mobGriefing");
        this.totalBreak = plugin.getConfig().getBoolean("breakMap.totalBreak");
        this.ironGenerating = plugin.getConfig().getInt("gameDefaults.ironGenerating");
        this.updatePlayersPlaceholder = plugin.getConfig().getInt("updatePlayersPlaceholder");
        this.lobbyScoreboard = plugin.getConfig().getBoolean("lobbyScoreboard");
        this.hungerCTW = plugin.getConfig().getBoolean("gameDefaults.hunger");
        this.instaKillOnVoidCTW = plugin.getConfig().getBoolean("gameDefaults.instaKillOnVoid");
        this.breakMap = plugin.getConfig().getBoolean("breakMap.enabled");
        this.breakBypass = plugin.getConfig().getStringList("breakMap.bypass");
        this.kitLevelsOrder = plugin.getConfig().getBoolean("kitLevelsOrder");
        this.excluideDefKits = plugin.getConfig().getBoolean("excluideDefKits");
        this.itemLobbyEnabled = plugin.getConfig().getBoolean("items.lobby.enabled");
        this.itemLobbySlot = plugin.getConfig().getInt("items.lobby.slot");
        this.itemLobby2Enabled = plugin.getConfig().getBoolean("items.lobby2.enabled");
        this.itemLobby2Slot = plugin.getConfig().getInt("items.lobby2.slot");
        this.whitelistedCMD = plugin.getConfig().getStringList("whitelistedCMD");
        this.itemLobbyCMD = plugin.getConfig().getString("items.lobby.cmd");
        this.itemLobby2CMD = plugin.getConfig().getString("items.lobby2.cmd");
        this.maxMultiplier = plugin.getConfig().getInt("gameDefaults.maxMultiplier");
        this.broadcastGame = plugin.getConfig().getBoolean("chat.broadcastGame");
        this.joinMessage = plugin.getConfig().getBoolean("chat.joinMessage");
        this.streak2 = XSound.matchXSound(plugin.getConfig().getString("sounds.streak2")).orElse(XSound.UI_BUTTON_CLICK).parseSound();
        this.streak3 = XSound.matchXSound(plugin.getConfig().getString("sounds.streak3")).orElse(XSound.UI_BUTTON_CLICK).parseSound();
        this.streak4 = XSound.matchXSound(plugin.getConfig().getString("sounds.streak4")).orElse(XSound.UI_BUTTON_CLICK).parseSound();
        this.streak5 = XSound.matchXSound(plugin.getConfig().getString("sounds.streak5")).orElse(XSound.UI_BUTTON_CLICK).parseSound();
        this.limitOfYSpawn = plugin.getConfig().getInt("gameDefaults.limitOfYSpawn");
        this.autoJoinFinish = plugin.getConfig().getBoolean("gameDefaults.autoJoinFinish");
        this.timeToKill = plugin.getConfig().getInt("gameDefaults.timeToKill");
        this.bountyMin = plugin.getConfig().getDouble("bounty.min");
        this.bountyMax = plugin.getConfig().getDouble("bounty.max");
        this.bountyPerKill = plugin.getConfig().getDouble("bounty.perKill");
        this.gCoinsKills = plugin.getConfig().getInt("gameDefaults.gcoins.kill");
        this.gCoinsWins = plugin.getConfig().getInt("gameDefaults.gcoins.win");
        this.gCoinsAssists = plugin.getConfig().getInt("gameDefaults.gcoins.assists");
        this.gCoinsCapture = plugin.getConfig().getInt("gameDefaults.gcoins.capture");
        this.gCoinsPickup = plugin.getConfig().getInt("gameDefaults.gcoins.pickup");
        this.gCoinsCapture = plugin.getConfig().getInt("gameDefaults.gcoins.capture");
        this.coinsKill = plugin.getConfig().getInt("gameDefaults.coins.kill");
        this.coinsWin = plugin.getConfig().getInt("gameDefaults.coins.win");
        this.coinsAssists = plugin.getConfig().getInt("gameDefaults.coins.assists");
        this.coinsCapture = plugin.getConfig().getInt("gameDefaults.coins.capture");
        this.coinsPickup = plugin.getConfig().getInt("gameDefaults.coins.pickup");
        this.xpKill = plugin.getConfig().getInt("gameDefaults.xp.kill");
        this.xpWin = plugin.getConfig().getInt("gameDefaults.xp.win");
        this.xpAssists = plugin.getConfig().getInt("gameDefaults.xp.assists");
        this.xpCapture = plugin.getConfig().getInt("gameDefaults.xp.capture");
        this.xpPickup = plugin.getConfig().getInt("gameDefaults.xp.pickup");
        this.upgradeSound = Sound.valueOf(plugin.getConfig().getString("sounds.upgrade"));
        this.starting = plugin.getConfig().getInt("gameDefaults.starting");
        this.progressBarAmount = plugin.getConfig().getInt("progressBarAmount");
        this.placeholdersAPI = plugin.getConfig().getBoolean("addons.placeholdersAPI");
        this.mainLobby = Utils.getStringLocation(plugin.getConfig().getString("mainLobby"));
        this.pickUpTeam = XSound.matchXSound(plugin.getConfig().getString("sounds.pickUpTeam")).orElse(XSound.ENTITY_FIREWORK_ROCKET_BLAST);
        this.pickUpOthers = XSound.matchXSound(plugin.getConfig().getString("sounds.pickUpOthers")).orElse(XSound.ENTITY_WITHER_HURT);
        this.captured = XSound.matchXSound(plugin.getConfig().getString("sounds.captured")).orElse(XSound.ENTITY_PLAYER_LEVELUP);
        this.cancelStartSound = Sound.valueOf(plugin.getConfig().getString("sounds.cancelStart"));
        this.wineffectschicken = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.chicken"));
        this.wineffectnotes = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.notes"));
        this.wineffectsvulcanfire = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanfire"));
        this.wineffectvulcanwool = Sound.valueOf(plugin.getConfig().getString("sounds.wineffects.vulcanwool"));
        this.redPanelData = (short) plugin.getConfig().getInt("redPanel.data");
        this.redPanelMaterial = Material.valueOf(plugin.getConfig().getString("redPanel.material"));
        this.redPanelInLocked = plugin.getConfig().getBoolean("redPanelInLocked");
        this.killEffectTNT = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.tnt"));
        this.killEffectSquid = Sound.valueOf(plugin.getConfig().getString("sounds.killeffects.squid"));
        this.back = Material.valueOf(plugin.getConfig().getString("materials.closeitem"));
        this.noDrop = plugin.getConfig().getStringList("gameDefaults.noDrop");
    }
}