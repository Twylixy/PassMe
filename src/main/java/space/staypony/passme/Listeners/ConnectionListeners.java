package space.staypony.passme.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.staypony.passme.Config.Config;
import space.staypony.passme.PassMe;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Services.SessionService;
import space.staypony.passme.Utils.MessageBuilder;

public class ConnectionListeners implements Listener {
    private final PassMe plugin;

    public ConnectionListeners(PassMe plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinNormal(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!AuthService.isPlayerRegistered(player)) {
            if (!AuthService.canPassRegistration(player)) {
                player.sendMessage(MessageBuilder.buildMessage(Config.messages.registrationMustBeSet));
                AuthService.beginRegistrationSession(plugin, player);
                return;
            }
            player.sendMessage(MessageBuilder.buildMessage(Config.messages.remindRegister));
            return;
        }

        if (!SessionService.canResumeSession(player)) {
            player.sendMessage(MessageBuilder.buildMessage(Config.messages.sessionExpired));
        } else {
            player.sendMessage(MessageBuilder.buildMessage(Config.messages.sessionResumed));
            return;
        }

        player.sendMessage(MessageBuilder.buildMessage(Config.messages.loginRequired));
        AuthService.beginAuthSession(plugin, player);
    }

    @EventHandler
    public void onPlayerLoginNormal(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (AuthService.isPlayerRegistered(player) && Config.rules.registrationRequired) return;
        player.kickPlayer(MessageBuilder.buildMessage(Config.messages.registrationRequired));
    }

    @EventHandler
    public void onPlayerQuitNormal(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        AuthService.closeAuthSession(player, false);
        AuthService.closeRegisterSession(player, false);
    }
}
