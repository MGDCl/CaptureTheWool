package io.github.Leonardo0013YT.UltraCTW.utils;

import de.slikey.effectlib.effect.CylinderEffect;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Game;
import io.github.Leonardo0013YT.UltraCTW.team.Team;

import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.DynamicLocation;
import de.slikey.effectlib.util.ParticleEffect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Color;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Random;

import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class EffectUtils {
    private final UltraCTW plugin;
    public EffectUtils(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void sentWinParticles(Team team, Game game){
        TextEffect effect = new TextEffect(UltraCTW.get().getEffectManager());
        effect.setDynamicOrigin(new DynamicLocation(game.getSpectator()));
        effect.text = (plugin.getLang().get("messages.particlesText").replaceAll("<team>", team.getParticles()));
        effect.particle = ParticleEffect.FLAME;
        effect.period = 20;
        effect.start();
    }

    public void woolPlacedEffect(Location l) {
        CylinderEffect effect = new CylinderEffect(UltraCTW.get().getEffectManager());
        effect.setDynamicOrigin(new DynamicLocation(l));
        effect.particle = ParticleEffect.FIREWORKS_SPARK;
        effect.duration = 60;
        effect.start();
    }

    public void winBaseEffect(Team team, Game game){
        World world = game.getSpectator().getWorld();
        Location firework1 = new Location(world, team.getSpawn().getX(), team.getSpawn().getY() + 5, team.getSpawn().getZ());
        Location firework2 = new Location(world, team.getSpawn().getX(), team.getSpawn().getY() + 10, team.getSpawn().getZ());
        Location firework3 = new Location(world, team.getSpawn().getX(), team.getSpawn().getY() + 20, team.getSpawn().getZ());
        Location firework4 = new Location(world, team.getSpawn().getX(), team.getSpawn().getY() + 25, team.getSpawn().getZ());
        Random ran = new Random();
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if(ran.nextBoolean()) {
                    randomFirework(firework1, ran);
                }
                if(ran.nextBoolean()) {
                    randomFirework(firework2, ran);
                }
                if(ran.nextBoolean()) {
                    randomFirework(firework3, ran);
                }
                if(ran.nextBoolean()) {
                    randomFirework(firework4, ran);
                }
                count ++;
                if(count == 30) {
                    cancel();
                }
            }
        }.runTaskTimer(UltraCTW.get(), 1, 5);
    }

    private static void randomFirework(Location loc, Random random) {
        Firework firework1 = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework1.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(getColor(random.nextInt(17) + 1)).withFade(getColor(random.nextInt(17) + 1)).with(FireworkEffect.Type.values()[random.nextInt((FireworkEffect.Type.values()).length)]).trail(random.nextBoolean()).build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(random.nextInt(2) + 1);
        firework1.setFireworkMeta(fireworkMeta);
    }

    private static Color getColor(int i) {
        switch (i) {
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.ORANGE;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.RED;
            case 14:
                return Color.SILVER;
            case 15:
                return Color.TEAL;
            case 16:
                return Color.WHITE;
            case 17:
                return Color.YELLOW;
        }
        return null;
    }

}

