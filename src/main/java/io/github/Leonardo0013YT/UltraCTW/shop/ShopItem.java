package io.github.Leonardo0013YT.UltraCTW.shop;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GameFlag;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.objects.ObjectPotion;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XPotion;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

@Getter
public class ShopItem {

    private UltraCTW plugin;
    private int slot, data;
    private double price;
    private boolean yourTeam;
    private Material material;
    private String name;
    private String lore;
    private String shop;
    private String key;
    private ArrayList<ObjectPotion> potions = new ArrayList<>();

    public ShopItem(UltraCTW plugin, String path, String shop, String key) {
        this.plugin = plugin;
        this.shop = shop;
        this.key = key;
        this.slot = plugin.getUpgrades().getInt(path + ".slot");
        this.price = plugin.getUpgrades().getConfig().getDouble(path + ".price");
        this.yourTeam = plugin.getUpgrades().getBoolean(path + ".yourTeam");
        this.name = plugin.getUpgrades().get(path + ".name");
        this.lore = plugin.getUpgrades().get(path + ".lore");
        String material = plugin.getUpgrades().get(path + ".material");
        String[] m = material.split(":");
        this.material = Material.valueOf(m[0]);
        this.data = Integer.parseInt(m[1]);
        for (String s : plugin.getUpgrades().getListOrDefault(path + ".effects", new ArrayList<>())) {
            if (s.equalsIgnoreCase("none")) continue;
            String[] st = s.split(":");
            XPotion potion = XPotion.matchXPotion(st[0]).orElse(XPotion.ABSORPTION);
            int level = Integer.parseInt(st[1]);
            int duration = Integer.parseInt(st[2]);
            potions.add(new ObjectPotion(potion, level, duration));
        }
    }

    public void execute(GameFlag gf, FlagTeam ft) {
        if (yourTeam) {
            for (ObjectPotion op : potions) {
                ft.getMembers().forEach(m -> m.addPotionEffect(new PotionEffect(op.getPotion().parsePotionEffectType(), op.getDuration(), op.getLevel())));
            }
        } else {
            for (FlagTeam t : gf.getTeams().values()) {
                if (ft.equals(t)) continue;
                for (ObjectPotion op : potions) {
                    t.getMembers().forEach(m -> m.addPotionEffect(new PotionEffect(op.getPotion().parsePotionEffectType(), op.getDuration(), op.getLevel())));
                }
            }
        }
    }

    public ItemStack getIcon(GamePlayer gp) {
        ItemStack icon = new ItemUtils(material, 1, data).setDisplayName(name).setLore(lore.replace("<status>", (gp.getCoins() < price) ? plugin.getUpgrades().get("noMoney") : plugin.getUpgrades().get("buy"))).applyAttributes().build();
        icon = NBTEditor.set(icon, key, "SHOP", "FLAG", "MENU");
        return NBTEditor.set(icon, shop, "BUFF", "FLAG", "MENU");
    }

}