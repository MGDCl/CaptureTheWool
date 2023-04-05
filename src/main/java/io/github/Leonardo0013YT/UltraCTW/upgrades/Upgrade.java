package io.github.Leonardo0013YT.UltraCTW.upgrades;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.game.GamePlayer;
import io.github.Leonardo0013YT.UltraCTW.interfaces.Request;
import io.github.Leonardo0013YT.UltraCTW.objects.ObjectPotion;
import io.github.Leonardo0013YT.UltraCTW.team.FlagTeam;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

@Getter
public class Upgrade {

    private int slot;
    private String name, lore;
    private XMaterial material;
    private List<String> materials;
    private HashMap<Integer, UpgradeLevel> levels = new HashMap<>();
    private UltraCTW plugin;
    private String key;

    public Upgrade(UltraCTW plugin, String path, String key) {
        this.plugin = plugin;
        this.key = key;
        this.slot = plugin.getUpgrades().getInt(path + ".slot");
        this.name = plugin.getUpgrades().get(path + ".name");
        this.lore = plugin.getUpgrades().get(path + ".lore");
        this.material = XMaterial.matchXMaterial(plugin.getUpgrades().get(path + ".material")).orElse(XMaterial.IRON_PICKAXE);
        this.materials = plugin.getUpgrades().getList(path + ".materials");
        for (String s : plugin.getUpgrades().getConfig().getConfigurationSection(path + ".levels").getKeys(false)) {
            int level = plugin.getUpgrades().getInt(path + ".levels." + s + ".level");
            levels.put(level, new UpgradeLevel(plugin, path + ".levels." + s));
        }
    }

    public void apply(Request r, Player p, FlagTeam team, UpgradeLevel upgrade) {
        for (ObjectPotion up : upgrade.getTeamEffects()) {
            team.getMembers().forEach(m -> m.addPotionEffect(new PotionEffect(up.getPotion().parsePotionEffectType(), up.getDuration(), up.getLevel())));
        }
        for (ObjectPotion up : upgrade.getSelfEffects()) {
            p.addPotionEffect(new PotionEffect(up.getPotion().parsePotionEffectType(), up.getDuration(), up.getLevel()));
        }
        if (upgrade.getEnchantments().isEmpty()) return;
        if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
            return;
        }
        ItemStack item = p.getItemInHand();
        if (materials.contains(item.getType().name())) {
            for (UpgradeEnchantment ue : upgrade.getEnchantments()) {
                ItemMeta im = item.getItemMeta();
                im.addEnchant(ue.getEnchantment().parseEnchantment(), ue.getLevel(), ue.isIgnore());
                item.setItemMeta(im);
            }
            r.request(true);
        } else {
            r.request(false);
        }
    }

    public ItemStack getIcon(FlagTeam ft, GamePlayer gp) {
        UpgradeLevel ul = getLevel((key.equalsIgnoreCase("youpickaxe")) ? gp.getPiUpgrade() : ft.getUpgradeHaste());
        UpgradeLevel next = getNextLevel((key.equalsIgnoreCase("youpickaxe")) ? gp.getPiUpgrade() : ft.getUpgradeHaste());
        String state;
        if (next == null) {
            state = plugin.getUpgrades().get("max");
        } else if (gp.getCoins() < next.getPrice()) {
            state = plugin.getUpgrades().get("noMoney");
        } else {
            state = plugin.getUpgrades().get("buy");
        }
        ItemStack icon = new ItemUtils(material).setDisplayName(name).setLore(lore.replace("<now>", ul.getName()).replace("<next>", (next == null) ? plugin.getUpgrades().get("max") : next.getName()).replace("<status>", state)).applyAttributes().build();
        return NBTEditor.set(icon, key, "UPGRADE", "FLAG", "MENU");
    }

    public UpgradeLevel getLevel(int level) {
        return levels.get(level);
    }

    public UpgradeLevel getNextLevel(int level) {
        return levels.get(level + 1);
    }

}