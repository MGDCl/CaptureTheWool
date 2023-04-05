package io.github.Leonardo0013YT.UltraCTW.setup;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

@Getter
@Setter
public class FlagTeamSetup {

    private ChatColor color;
    private Location spawn, flag;

    public FlagTeamSetup(ChatColor color) {
        this.color = color;
    }

}