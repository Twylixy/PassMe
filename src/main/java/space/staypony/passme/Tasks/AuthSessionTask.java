package space.staypony.passme.Tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Enums.AuthStatus;
import space.staypony.passme.Utils.MessageBuilder;

import java.time.Instant;

public class AuthSessionTask extends BukkitRunnable {
    public final Player player;
    public int tries = 0;
    public AuthStatus status = AuthStatus.IN_PROGRESS;
    public long authSessionExpires;

    public AuthSessionTask(Player player) {
        this.player = player;
        this.authSessionExpires = Instant.now().getEpochSecond() + Config.login.loginTimeLimit;
    }

    @Override
    public void run() {
        if (status != AuthStatus.SUCCESS) {
            if (Instant.now().getEpochSecond() >= authSessionExpires) {
                player.kickPlayer(MessageBuilder.buildMessage(Config.messages.loginTimeExceed));
                cancel();
            }
            player.sendMessage(MessageBuilder.buildMessage(Config.messages.loginHint));
        } else {
            cancel();
        }
    }
}
