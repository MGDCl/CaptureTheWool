package io.github.Leonardo0013YT.UltraCTW.game;

import com.nametagedit.plugin.NametagEdit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class GamePlayer {

    private Player p;
    private int kills = 0, deaths = 0, PiUpgrade = 0;
    private double coins = 0;
    private ItemStack[] inv, armor;
    private boolean reset;
    private String pickaxeKey, teamHaste;

    public GamePlayer(Player p) {
        this.p = p;
        this.inv = p.getInventory().getContents();
        this.armor = p.getInventory().getArmorContents();
        this.reset = false;
    }

    public void reset() {
        if (reset) {
            return;
        }
        p.getInventory().setContents(inv);
        p.getInventory().setArmorContents(armor);
        NametagEdit.getApi().clearNametag(p);
        NametagEdit.getApi().reloadNametag(p);
        reset = true;
    }

    public void addCoins(double amount) {
        coins += amount;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void removeCoins(double amount) {
        coins -= amount;
    }

}