package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.utils.ItemUtils;
import io.github.Leonardo0013YT.UltraCTW.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraCTW.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemManager {

    private final ItemStack setup, points, teams, lobby, lobby2, leave, pickaxe;

    public ItemManager(UltraCTW plugin) {
        this.lobby2 = new ItemUtils(XMaterial.PAPER).setDisplayName(plugin.getLang().get("items.lobby2.nameItem")).setLore(plugin.getLang().get("items.lobby2.loreItem")).build();
        this.lobby = new ItemUtils(XMaterial.EMERALD).setDisplayName(plugin.getLang().get("items.lobby.nameItem")).setLore(plugin.getLang().get("items.lobby.loreItem")).build();
        this.setup = new ItemUtils(XMaterial.DIAMOND).setDisplayName(plugin.getLang().get("items.setup.nameItem")).setLore(plugin.getLang().get("items.setup.loreItem")).build();
        this.points = new ItemUtils(XMaterial.STICK).setDisplayName(plugin.getLang().get("items.points.nameItem")).setLore(plugin.getLang().get("items.points.loreItem")).build();
        this.teams = new ItemUtils(XMaterial.WHITE_WOOL).setDisplayName(plugin.getLang().get("items.teams.nameItem")).setLore(plugin.getLang().get("items.teams.loreItem")).build();
        this.leave = new ItemUtils(XMaterial.NETHER_STAR).setDisplayName(plugin.getLang().get("items.leave.nameItem")).setLore(plugin.getLang().get("items.leave.loreItem")).build();
        this.pickaxe = NBTEditor.set(new ItemUtils(XMaterial.WOODEN_PICKAXE).build(), "PICKAXE", "FLAG", "PICKAXE", "DEFAULT");
    }

}