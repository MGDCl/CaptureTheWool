package io.github.Leonardo0013YT.UltraCTW.objects;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.api.events.CTWNPCInteractEvent;
import io.github.Leonardo0013YT.UltraCTW.interfaces.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ProtocolLib {

    private static ProtocolManager protocolManager;
    private final HashMap<UUID, Long> lastClick = new HashMap<>();
    private final UltraCTW main;

    public ProtocolLib(UltraCTW main) {
        this.main = main;
        protocolManager = ProtocolLibrary.getProtocolManager();
        register();
    }

    public void register() {
        protocolManager.addPacketListener(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    Player p = event.getPlayer();
                    if (!main.getNpc().getNpcs().containsKey(p)) return;
                    lastClick.putIfAbsent(p.getUniqueId(), 0L);
                    if (lastClick.get(p.getUniqueId()) + 1000 > System.currentTimeMillis()) return;
                    try {
                        PacketContainer packet = event.getPacket();
                        int id = packet.getIntegers().read(0);
                        for (NPC npc : main.getNpc().getNpcs().get(p)) {
                            if (npc.getEntityID() == id) {
                                CTWNPCInteractEvent interactevent = new CTWNPCInteractEvent(p, npc);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(UltraCTW.get(), () -> Bukkit.getPluginManager().callEvent(interactevent));
                                lastClick.put(p.getUniqueId(), System.currentTimeMillis());
                                break;
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        });
    }

}