package space.staypony.passme.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import space.staypony.passme.Services.AuthService;

public class BlockListeners implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreakHighest(BlockBreakEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlaceHighest(BlockPlaceEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakNormal(BlockBreakEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceNormal(BlockPlaceEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreakLowest(BlockBreakEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaceLowest(BlockPlaceEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }
}
