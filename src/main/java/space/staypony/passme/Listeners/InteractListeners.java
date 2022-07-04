package space.staypony.passme.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import space.staypony.passme.Services.AuthService;

public class InteractListeners implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractHighest(PlayerInteractEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityHighest(PlayerInteractEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractAtEntityHighest(PlayerInteractAtEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEventHighest(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getWhoClicked()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractNormal(PlayerInteractEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntityNormal(PlayerInteractEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractAtEntityNormal(PlayerInteractAtEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEventNormal(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getWhoClicked()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractLowest(PlayerInteractEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntityLowest(PlayerInteractEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractAtEntityLowest(PlayerInteractAtEntityEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryInteractEventLowest(InventoryOpenEvent event) {
        if (AuthService.isPlayerInAnySession((Player) event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClickEventLowest(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getWhoClicked()))
            event.setCancelled(true);
    }
}
