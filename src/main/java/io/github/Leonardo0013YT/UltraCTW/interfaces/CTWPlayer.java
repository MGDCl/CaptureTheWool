package io.github.Leonardo0013YT.UltraCTW.interfaces;

import java.util.ArrayList;

public interface CTWPlayer {

    void addCoins(double coins);

    void removeCoins(double coins);

    int getKills();

    void setKills(int kills);

    int getWinDance();

    void setWinDance(int winDance);

    int getWinEffect();

    void setWinEffect(int winEffect);

    ArrayList<Integer> getWineffects();

    void setWineffects(ArrayList<Integer> wineffects);

    ArrayList<Integer> getWindances();

    void setWindances(ArrayList<Integer> windances);

    double getCoins();

    void setCoins(double coins);

    int getKillEffect();

    void setKillEffect(int killEffect);

    int getTaunt();

    void setTaunt(int taunt);

    int getTrail();

    void setTrail(int trail);

    int getParting();

    void setParting(int parting);

    int getKillSound();

    void setKillSound(int killSound);

    ArrayList<Integer> getKillsounds();

    void setKillsounds(ArrayList<Integer> killsounds);

    ArrayList<Integer> getTaunts();

    void setTaunts(ArrayList<Integer> taunts);

    ArrayList<Integer> getTrails();

    void setTrails(ArrayList<Integer> trails);

    int getAssists();

    void setAssists(int assists);

    int getBowKillDistance();

    void setBowKillDistance(int bowKillDistance);

    int getMaxBowDistance();

    void setMaxBowDistance(int maxBowDistance);

    int getBowKills();

    void setBowKills(int bowKills);

    int getTotalKills();

    int getKit();

    void setKit(int kit);

    int getKitLevel();

    void setKitLevel(int kitLevel);

    void addKitLevel(int kitID, int level);

    void removeKitLevel(int kitID, int level);

    boolean hasKitLevel(int kitID, int level);

    ArrayList<Integer> getShopkeepers();

    void setShopkeepers(ArrayList<Integer> shopkeepers);

    void addShopKeepers(int id);

    double getBounty();

    void setBounty(double bounty);

    void addWoolCaptured();

    int getWoolCaptured();

    void setWoolCaptured(int woolCaptured);

    int getLevel();

    void setLevel(int level);

    int getXp();

    void setXp(int xp);

    int getPlaced();

    void setPlaced(int placed);

    int getDeaths();

    void setDeaths(int deaths);

    int getWins();

    void setWins(int wins);

    int getShots();

    void setShots(int shots);

    int getsShots();

    void setsShots(int sShots);

    int getWalked();

    void setWalked(int walked);

    int getPlayed();

    void setPlayed(int played);

    int getBroken();

    void setBroken(int broken);

    int getShopKeeper();

    void setShopKeeper(int shopKeeper);

    ArrayList<Integer> getPartings();

    void setPartings(ArrayList<Integer> partings);

    void addAssists(int assists);

    int getKill5();

    void setKill5(int kill5);

    void addKill5(int kill5);

    int getKill25();

    void setKill25(int kill25);

    void addKill25(int kill25);

    int getKill50();

    void setKill50(int kill50);

    void addKill50(int kill50);

    ArrayList<Integer> getKilleffects();

    void setKilleffects(ArrayList<Integer> killeffects);

    void addKillEffects(int id);

    void addKillSounds(int id);

    void addPartings(int id);

    void addTaunts(int id);

    void addTrails(int id);

    void addWinDances(int id);

    void addWinEffects(int id);

    void addWoolStolen();

    int getWoolStolen();

    void setWoolStolen(int woolStolen);

    void addKillsWoolHolder();

    int getKillsWoolHolder();

    void setKillsWoolHolder(int killsWoolHolder);

    int getLoses();

    void setLoses(int loses);
}