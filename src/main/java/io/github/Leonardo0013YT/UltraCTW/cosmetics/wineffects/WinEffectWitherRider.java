package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.metadata.FixedMetadataValue;

public class WinEffectWitherRider implements WinEffect {

    private Wither wither;

    @Override
    public void start(Player p, Game game) {
        wither = p.getWorld().spawn(p.getLocation(), Wither.class);
        wither.setPassenger(p);
        wither.setMetadata("NO_TARGET", new FixedMetadataValue(UltraCTW.get(), ""));
        p.getWorld().playSound(p.getLocation(), XSound.ENTITY_WITHER_SPAWN.parseSound(), 1.0f, 1.0f);
    }

    @Override
    public void stop() {
        if (wither != null) {
            wither.remove();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectWitherRider();
    }

}