package io.github.Leonardo0013YT.UltraCTW.game;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.enums.PhaseType;
import io.github.Leonardo0013YT.UltraCTW.upgrades.UpgradeEnchantment;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XEnchantment;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

@Getter
@Setter
public class GameEvent {

    private int time;
    private int reset;
    private Material material;
    private PhaseType type;
    private ArrayList<UpgradeEnchantment> enchantments = new ArrayList<>();

    public GameEvent(UltraCTW plugin, String path) {
        this.time = plugin.getConfig().getInt(path + ".time");
        this.type = PhaseType.valueOf(plugin.getConfig().getString(path + ".type"));
        this.reset = time;
        this.material = Material.valueOf(plugin.getConfig().getString(path + ".pickaxe"));
        for (String s : plugin.getConfig().getStringList(path + ".enchants")) {
            if (s.equals("NONE")) continue;
            String[] st = s.split(":");
            XEnchantment e = XEnchantment.matchXEnchantment(st[0]).orElse(XEnchantment.DIG_SPEED);
            int level = Integer.parseInt(st[1]);
            boolean ignore = Boolean.parseBoolean(st[2]);
            enchantments.add(new UpgradeEnchantment(e, level, ignore));
        }
    }

    public GameEvent(int time, Material material, ArrayList<UpgradeEnchantment> enchantments) {
        this.time = time;
        this.material = material;
        this.enchantments = enchantments;
    }

    public void reduce() {
        time--;
    }

    public void start(GameFlag flag) {
        for (Player on : flag.getPlayers()) {
            applyEnchant(on);
            on.sendMessage(UltraCTW.get().getLang().get("messages.startPhase").replace("<phase>", UltraCTW.get().getLang().get("phases." + type.name())));
        }
    }

    private void applyEnchant(Player on) {
        for (ItemStack item : on.getInventory().getContents()) {
            if (item == null || item.getType().equals(Material.AIR) || !item.getType().name().endsWith("PICKAXE"))
                continue;
            boolean nbt = NBTEditor.contains(item, "FLAG", "PICKAXE", "DEFAULT");
            if (!nbt) {
                continue;
            }
            ItemStack newPickaxe = new ItemStack(item);
            newPickaxe.setType(material);
            ItemMeta newPickaxeM = newPickaxe.getItemMeta();
            for (UpgradeEnchantment ue : getEnchantments()) {
                newPickaxeM.addEnchant(ue.getEnchantment().parseEnchantment(), ue.getLevel(), ue.isIgnore());
            }
            newPickaxe.setItemMeta(newPickaxeM);
            on.getInventory().remove(item);
            NBTEditor.set(newPickaxe, "PICKAXE", "FLAG", "PICKAXE", "DEFAULT");
            on.getInventory().addItem(newPickaxe);
        }
    }

    public void apply(Player on) {
        applyEnchant(on);
    }

    public void reset() {
        this.time = reset;
    }

    public GameEvent clone() {
        return new GameEvent(reset, material, enchantments);
    }

}