package space.staypony.passme.Tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Enums.RegistrationStatus;
import space.staypony.passme.Utils.MessageBuilder;

import java.time.Instant;

public class RegistrationSessionTask extends BukkitRunnable {
    public final Player player;
    public RegistrationStatus status = RegistrationStatus.IN_PROGRESS;
    public long registrationSessionExpires;

    public RegistrationSessionTask(Player player) {
        this.player = player;
        this.registrationSessionExpires = Instant.now().getEpochSecond() + Config.login.registrationTimeLimit;
    }

    @Override
    public void run() {
        if (status != RegistrationStatus.SUCCESS) {
            if (Instant.now().getEpochSecond() >= registrationSessionExpires) {
                player.kickPlayer(MessageBuilder.buildMessage(Config.messages.registrationTimeExceed));
                cancel();
            }
            player.sendMessage(MessageBuilder.buildMessage(Config.messages.registrationHint));
        } else {
            cancel();
        }
    }
}
