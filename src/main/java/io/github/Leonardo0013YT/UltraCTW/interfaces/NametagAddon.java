package io.github.Leonardo0013YT.UltraCTW.interfaces;

import org.bukkit.entity.Player;

public interface NametagAddon {

    void addPlayerNameTag(Player p);

    void removePlayerNameTag(Player p);

    void resetPlayerNameTag(Player p);

    String getPrefix(Player p);

    String getSuffix(Player p);

}
