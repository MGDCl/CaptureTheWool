package io.github.Leonardo0013YT.UltraCTW.placeholders;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.CTWPlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.Level;
import io.github.Leonardo0013YT.UltraCTW.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private final UltraCTW plugin;

    public Placeholders(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public @NotNull String getIdentifier() {
        return "ctw";
    }

    public @NotNull String getAuthor() {
        return "Leonardo0013YT";
    }

    public @NotNull String getVersion() {
        return "2.5.4";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String id) {
        CTWPlayer sw = plugin.getDb().getCTWPlayer(p);
        switch (id) {
            case "players_wool":
                return "" + plugin.getGm().getGameSize("wool");
            case "game_name":
                return plugin.getGm().getSelectedGame().getName();
            case "game_state":
                return "" + plugin.getGm().getSelectedGame().getState();
            case "total_trails":
                return "" + plugin.getTlm().getTrailsSize();
            case "total_windances":
                return "" + plugin.getWdm().getWinDancesSize();
            case "total_wineffects":
                return "" + plugin.getWem().getWinEffectsSize();
            case "total_killsounds":
                return "" + plugin.getKsm().getKillSoundsSize();
            case "total_killeffects":
                return "" + plugin.getKem().getKillEffectSize();
            case "total_taunts":
                return "" + plugin.getTm().getTauntsSize();
        }
        if (sw == null) {
            return "";
        }
        switch (id) {
            case "level_progress": {
                Level l = plugin.getLvl().getLevel(p);
                int xp = sw.getXp() - l.getXp();
                int max = l.getLevelUp() - l.getXp();
                return Utils.getProgressBar(xp, max, plugin.getCm().getProgressBarAmount());
            }
            case "selected_trail":
                return plugin.getTlm().getSelected(sw);
            case "selected_windance":
                return plugin.getWdm().getSelected(sw);
            case "selected_wineffect":
                return plugin.getWem().getSelected(sw);
            case "selected_taunt":
                return plugin.getTm().getSelected(sw);
            case "selected_killsound":
                return plugin.getKsm().getSelected(sw);
            case "selected_killeffect":
                return plugin.getKem().getSelected(sw);
            case "unlocked_trails":
                return "" + sw.getTrails().size();
            case "percentage_trails":
                return Utils.getProgressBar(sw.getTrails().size(), plugin.getTlm().getTrailsSize());
            case "bar_trails":
                return Utils.getProgressBar(sw.getTrails().size(), plugin.getTlm().getTrailsSize(), plugin.getCm().getProgressBarAmount());
            case "unlocked_windances":
                return "" + sw.getWindances().size();
            case "percentage_windances":
                return Utils.getProgressBar(sw.getWindances().size(), plugin.getWdm().getWinDancesSize());
            case "bar_windances":
                return Utils.getProgressBar(sw.getWindances().size(), plugin.getWdm().getWinDancesSize(), plugin.getCm().getProgressBarAmount());
            case "unlocked_wineffects":
                return "" + sw.getWineffects().size();
            case "percentage_wineffects":
                return Utils.getProgressBar(sw.getWineffects().size(), plugin.getWem().getWinEffectsSize());
            case "bar_wineffects":
                return Utils.getProgressBar(sw.getWineffects().size(), plugin.getWem().getWinEffectsSize(), plugin.getCm().getProgressBarAmount());
            case "unlocked_taunts":
                return "" + sw.getTaunts().size();
            case "percentage_taunts":
                return Utils.getProgressBar(sw.getTaunts().size(), plugin.getTm().getTauntsSize());
            case "bar_taunts":
                return Utils.getProgressBar(sw.getTaunts().size(), plugin.getTm().getTauntsSize(), plugin.getCm().getProgressBarAmount());
            case "unlocked_killsounds":
                return "" + sw.getKillsounds().size();
            case "percentage_killsounds":
                return Utils.getProgressBar(sw.getKillsounds().size(), plugin.getKsm().getKillSoundsSize());
            case "bar_killsounds":
                return Utils.getProgressBar(sw.getKillsounds().size(), plugin.getKsm().getKillSoundsSize(), plugin.getCm().getProgressBarAmount());
            case "unlocked_killeffects":
                return "" + sw.getKilleffects().size();
            case "percentage_killeffects":
                return Utils.getProgressBar(sw.getKilleffects().size(), plugin.getKem().getKillEffectSize());
            case "bar_killeffects":
                return Utils.getProgressBar(sw.getKilleffects().size(), plugin.getKem().getKillEffectSize(), plugin.getCm().getProgressBarAmount());
            case "level_prefix":
                return plugin.getLvl().getLevelPrefix(p);
            case "level":
                return "" + sw.getLevel();
            case "coins":
                return Utils.format(plugin.getAdm().getCoins(p));
            case "bow_distance":
                return "" + sw.getMaxBowDistance();
            case "bowkill_distance":
                return "" + sw.getBowKillDistance();
            case "captured":
                return "" + sw.getWoolCaptured();
            case "wool_stolen":
                return "" + sw.getWoolStolen();
            case "wool_holder":
                return "" + sw.getKillsWoolHolder();
            case "xp":
                return "" + sw.getXp();
            case "exp_remaining": {
                Level l = plugin.getLvl().getLevel(p);
                return String.valueOf(l.getLevelUp() - sw.getXp());
            }
            case "kills":
                return "" + sw.getKills();
            case "assists":
                return "" + sw.getAssists();
            case "bow_kills":
                return "" + sw.getBowKills();
            case "total_kills":
                return "" + sw.getTotalKills();
            case "wins":
                return "" + sw.getWins();
            case "loses":
                return "" + sw.getLoses();
            case "deaths":
                return "" + sw.getDeaths();
            case "shots":
                return "" + sw.getShots();
            case "sshots":
                return "" + sw.getsShots();
            case "walked":
                return "" + sw.getWalked();
            case "played":
                return "" + sw.getPlayed();
            case "placed":
                return "" + sw.getPlaced();
            case "break":
                return "" + sw.getBroken();
        }
        return null;
    }

}