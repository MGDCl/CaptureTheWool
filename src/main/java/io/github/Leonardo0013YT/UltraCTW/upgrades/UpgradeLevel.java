package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.objects.ObjectPotion;
import io.github.Leonardo0013YT.UltraCTW.xseries.XEnchantment;
import io.github.Leonardo0013YT.UltraCTW.xseries.XPotion;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UpgradeLevel {

    private double price;
    private int level;
    private String name;
    private ArrayList<UpgradeEnchantment> enchantments = new ArrayList<>();
    private ArrayList<ObjectPotion> selfEffects = new ArrayList<>(), teamEffects = new ArrayList<>();

    public UpgradeLevel(UltraCTW plugin, String path) {
        this.price = plugin.getUpgrades().getConfig().getDouble(path + ".price");
        this.level = plugin.getUpgrades().getInt(path + ".level");
        this.name = plugin.getUpgrades().get(path + ".name");
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".enchants", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            String[] st = s.split(":");
            XEnchantment e = XEnchantment.matchXEnchantment(st[0]).orElse(XEnchantment.DIG_SPEED);
            int level = Integer.parseInt(st[1]);
            boolean ignore = Boolean.parseBoolean(st[2]);
            enchantments.add(new UpgradeEnchantment(e, level, ignore));
        }
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".selfEffects", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            String[] st = s.split(":");
            XPotion potion = XPotion.matchXPotion(st[0]).orElse(XPotion.ABSORPTION);
            int level = Integer.parseInt(st[1]);
            int duration = Integer.parseInt(st[2]);
            selfEffects.add(new ObjectPotion(potion, level, duration));
        }
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".teamEffects", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            String[] st = s.split(":");
            XPotion potion = XPotion.matchXPotion(st[0]).orElse(XPotion.ABSORPTION);
            int level = Integer.parseInt(st[1]);
            int duration = Integer.parseInt(st[2]);
            teamEffects.add(new ObjectPotion(potion, level, duration));
        }
    }

}