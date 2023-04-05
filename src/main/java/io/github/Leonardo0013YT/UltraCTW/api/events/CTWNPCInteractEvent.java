package io.github.Leonardo0013YT.UltraCTW.api.events;

import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CTWNPCInteractEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private NPC npc;
    private boolean isCancelled = false;

    public CTWNPCInteractEvent(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public NPC getNpc() {
        return npc;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}