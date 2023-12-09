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
    private int kills;
    private int deaths = 0;
    private int assists = 0;

    private int woolStolen = 0;

    private int killsWoolHolder = 0;
    private int coins;
    private ItemStack[] inv, armor;
    private final int  xp;
    private final int souls;
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
        this.coins += amount;
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public void removeCoins(double amount) {
        this.coins -= (int) amount;
    }

    public Player getP() {
        return this.p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }


    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double coins) {
        this.coins = (int) coins;
    }


    public void setInv(ItemStack[] inv) {
        this.inv = inv;
    }


    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public boolean isReset() {
        return this.reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public int getAssists(){
        return this.assists;
    }

    public void setAssists(int assists){
        this.assists = assists;
    }

    public int getWoolStolen(){
        return this.woolStolen;
    }

    public void setWoolStolen(int woolStolen){
        this.woolStolen = woolStolen;
    }

    public int getKillsWoolHolder(){
        return this.killsWoolHolder;
    }

    public void setKillsWoolHolder(int killsWoolHolder){
        this.killsWoolHolder = killsWoolHolder;
    }
}