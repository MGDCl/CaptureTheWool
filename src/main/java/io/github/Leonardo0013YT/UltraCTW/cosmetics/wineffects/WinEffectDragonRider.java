package io.github.Leonardo0013YT.UltraCTW.cosmetics.wineffects;

import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.interfaces.WinEffect;
import io.github.Leonardo0013YT.UltraCTW.xseries.XSound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;

public class WinEffectDragonRider implements WinEffect {

    private EnderDragon dragon;

    @Override
    public void start(Player p, Game game) {
        dragon = p.getWorld().spawn(p.getLocation(), EnderDragon.class);
        dragon.setPassenger(p);
        p.getWorld().playSound(p.getLocation(), XSound.ENTITY_ENDER_DRAGON_GROWL.parseSound(), 1.0f, 1.0f);
    }

    @Override
    public void stop() {
        if (dragon != null) {
            dragon.remove();
        }
    }

    @Override
    public WinEffect clone() {
        return new WinEffectDragonRider();
    }

}