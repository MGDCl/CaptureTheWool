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
    private int kills, deaths = 0, PiUpgrade = 0;
    private int coins;
    private ItemStack[] inv, armor;
    private int  xp, souls;
    private boolean reset;
    private String pickaxeKey, teamHaste;

    public GamePlayer(Player p) {
        this.p = p;
        this.kills = 0;
        this.coins = 0;
        this.xp = 0;
        this.souls = 0;
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

    public int getXP() {
        return xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }

    public int getSouls() {
        return souls;
    }

    public void addSouls(int souls) {
        this.souls += souls;
    }

}