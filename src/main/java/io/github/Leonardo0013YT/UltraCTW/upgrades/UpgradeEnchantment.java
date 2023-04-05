package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.xseries.XEnchantment;
import lombok.Getter;

@Getter
public class UpgradeEnchantment {

    private XEnchantment enchantment;
    private int level;
    private boolean ignore;

    public UpgradeEnchantment(XEnchantment enchantment, int level, boolean ignore) {
        this.enchantment = enchantment;
        this.level = level;
        this.ignore = ignore;
    }

}