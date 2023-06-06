package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.metadata.FixedMetadataValue;

public class WinEffectDareDevil implements WinEffect {

    private Vehicle horse;

    @Override
    public void start(Player p, Game game) {
        horse = UltraCTW.get().getVc().getNMS().spawnHorse(p.getLocation(), p);
        horse.setPassenger(p);
        horse.setMetadata("NO_TARGET", new FixedMetadataValue(UltraCTW.get(), ""));
        p.getWorld().playSound(p.getLocation(), XSound.ENTITY_ENDER_DRAGON_GROWL.parseSound(), 1.0f, 1.0f);
    }

    @Override
    public void stop() {
        if (horse != null) {
            horse.remove();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectDareDevil();
    }

}
