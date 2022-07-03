package space.staypony.passme.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Services.AuthService;

public class MiscListeners implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatMessageAsyncHighest(AsyncPlayerChatEvent event) {
        String command = event.getMessage().split(" ")[0];

        if (!Config.rules.allowedCommandsDuringLogin.contains(command))
            event.setCancelled(true);
    }

    @EventHandler
    public void onChatMessageAsyncNormal(AsyncPlayerChatEvent event) {
        String command = event.getMessage().split(" ")[0];

        if (!Config.rules.allowedCommandsDuringLogin.contains(command))
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChatMessageAsyncLowest(AsyncPlayerChatEvent event) {
        String command = event.getMessage().split(" ")[0];

        if (!Config.rules.allowedCommandsDuringLogin.contains(command))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (AuthService.isPlayerInAnySession(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChanged(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityHealthRegain(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (AuthService.isPlayerInAnySession((Player) event.getEntity().getShooter()))
            event.setCancelled(true);
    }
}
