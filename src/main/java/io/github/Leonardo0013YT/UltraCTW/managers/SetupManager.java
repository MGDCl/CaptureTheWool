package io.github.Leonardo0013YT.UltraCTW.managers;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.interfaces.UltraInventory;
import io.github.Leonardo0013YT.UltraCTW.setup.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SetupManager {

    private UltraCTW plugin;
    private HashMap<Player, UltraInventory> setupInventory = new HashMap<>();
    private HashMap<Player, String> editName = new HashMap<>();
    private HashMap<Player, ArenaSetup> setup = new HashMap<>();
    private HashMap<Player, TeamSetup> setupTeam = new HashMap<>();
    private HashMap<Player, KitSetup> setupKit = new HashMap<>();
    private HashMap<Player, KillSoundSetup> setupKillSound = new HashMap<>();
    private HashMap<Player, TauntSetup> setupTaunt = new HashMap<>();
    private HashMap<Player, TrailSetup> setupTrail = new HashMap<>();
    private HashMap<UUID, String> delete = new HashMap<>();

    public SetupManager(UltraCTW plugin) {
        this.plugin = plugin;
    }

    public void setDelete(Player p, String name) {
        delete.put(p.getUniqueId(), name);
    }

    public boolean isDelete(Player p) {
        return delete.containsKey(p.getUniqueId());
    }

    public void removeDelete(Player p) {
        delete.remove(p.getUniqueId());
    }

    public String getDelete(Player p) {
        return delete.get(p.getUniqueId());
    }

    public void setSetupKit(Player p, KitSetup ks) {
        setupKit.put(p, ks);
    }

    public boolean isSetupKit(Player p) {
        return setupKit.containsKey(p);
    }

    public KitSetup getSetupKit(Player p) {
        return setupKit.get(p);
    }

    public void removeSetupKit(Player p) {
        setupKit.remove(p);
    }

    public void setSetupName(Player p, String a) {
        editName.put(p, a);
    }

    public void setSetupInventory(Player p, UltraInventory a) {
        setupInventory.put(p, a);
    }

    public UltraInventory getSetupInventory(Player p) {
        return setupInventory.get(p);
    }

    public boolean isSetupInventory(Player p) {
        return setupInventory.containsKey(p);
    }

    public void removeInventory(Player p) {
        setupInventory.remove(p);
    }

    public String getSetupName(Player p) {
        return editName.get(p);
    }

    public boolean isSetupName(Player p) {
        return editName.containsKey(p);
    }

    public void removeName(Player p) {
        editName.remove(p);
    }

    public void setSetup(Player p, ArenaSetup a) {
        setup.put(p, a);
    }

    public ArenaSetup getSetup(Player p) {
        return setup.get(p);
    }

    public boolean isSetup(Player p) {
        return setup.containsKey(p);
    }

    public void remove(Player p) {
        setup.remove(p);
    }

    public void setSetupTeam(Player p, TeamSetup a) {
        setupTeam.put(p, a);
    }

    public TeamSetup getSetupTeam(Player p) {
        return setupTeam.get(p);
    }

    public boolean isSetupTeam(Player p) {
        return setupTeam.containsKey(p);
    }

    public void removeTeam(Player p) {
        setupTeam.remove(p);
    }

    public void setSetupKillSound(Player p, KillSoundSetup a) {
        setupKillSound.put(p, a);
    }

    public KillSoundSetup getSetupKillSound(Player p) {
        return setupKillSound.get(p);
    }

    public boolean isSetupKillSound(Player p) {
        return setupKillSound.containsKey(p);
    }

    public void removeKillSound(Player p) {
        setupKillSound.remove(p);
    }

    public void setSetupTrail(Player p, TrailSetup a) {
        setupTrail.put(p, a);
    }

    public TrailSetup getSetupTrail(Player p) {
        return setupTrail.get(p);
    }

    public boolean isSetupTrail(Player p) {
        return setupTrail.containsKey(p);
    }

    public void removeTrail(Player p) {
        setupTrail.remove(p);
    }

    public void setSetupTaunt(Player p, TauntSetup a) {
        setupTaunt.put(p, a);
    }

    public TauntSetup getSetupTaunt(Player p) {
        return setupTaunt.get(p);
    }

    public boolean isSetupTaunt(Player p) {
        return setupTaunt.containsKey(p);
    }

    public void removeTaunt(Player p) {
        setupTaunt.remove(p);
    }
}