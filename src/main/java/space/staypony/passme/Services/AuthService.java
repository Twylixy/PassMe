package space.staypony.passme.Services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Enums.AuthStatus;
import space.staypony.passme.Enums.LoginMode;
import space.staypony.passme.Enums.RegistrationStatus;
import space.staypony.passme.PassMe;
import space.staypony.passme.Tasks.AuthSessionTask;
import space.staypony.passme.Tasks.RegistrationSessionTask;
import space.staypony.passme.Utils.MessageBuilder;

import java.io.Console;
import java.util.HashMap;

public class AuthService {
    private static final HashMap<Player, AuthSessionTask> authTasks = new HashMap<>();
    private static final HashMap<Player, RegistrationSessionTask> regTasks = new HashMap<>();
    public static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public static void addLoginFail(Player player) {
        if (!authTasks.containsKey(player)) return;
        AuthSessionTask authSessionTask = authTasks.get(player);

        authSessionTask.tries += 1;

        if (authSessionTask.tries == 3) {
            authSessionTask.cancel();
            player.kickPlayer(MessageBuilder.buildClearMessage(Config.messages.loginTimeExceed));
        }
    }

    public static boolean isPlayerRegistered(Player player) {
        return Database.getPlayer(player.getName()) != null;
    }

    public static boolean isPlayerInAnySession(Player player) {
        return authTasks.containsKey(player) || regTasks.containsKey(player);
    }

    public static void beginAuthSession(PassMe plugin, Player player) {
        if (authTasks.containsKey(player)) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 86400, 10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 86400, 10));

        player.sendTitle(
                MessageBuilder.buildClearMessage(Config.messages.loginRequired),
                MessageBuilder.buildClearMessage(Config.messages.loginHint),
                10,
                Config.login.loginTimeLimit * 20,
                0
                );

        AuthSessionTask authSessionTask = new AuthSessionTask(player);
        authTasks.put(player, authSessionTask);

        authSessionTask.runTaskTimer(plugin, 20, 20);
    }

    public static void beginRegistrationSession(PassMe plugin, Player player) {
        if (regTasks.containsKey(player)) return;

        RegistrationSessionTask registrationSessionTask = new RegistrationSessionTask(player);
        regTasks.put(player, registrationSessionTask);

        player.sendTitle(
                MessageBuilder.buildClearMessage(Config.messages.registrationMustBeSet),
                MessageBuilder.buildClearMessage(Config.messages.registrationHint),
                10,
                Config.login.registrationTimeLimit * 20,
                0
        );

        registrationSessionTask.runTaskTimer(plugin, 20, 20);
    }

    public static void closeAuthSession(Player player, boolean success) {
        if (!authTasks.containsKey(player)) return;
        AuthSessionTask authSessionTask = authTasks.get(player);

        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.SLOW);

        player.sendTitle(
                MessageBuilder.buildClearMessage(Config.messages.loginRequired),
                MessageBuilder.buildClearMessage(Config.messages.loginHint),
                0,
                0,
                10
        );

        if (!success) {
            authSessionTask.status = AuthStatus.DROPPED;
        } else {
            authSessionTask.status = AuthStatus.SUCCESS;
        }
        authTasks.remove(player);
    }

    public static void closeRegisterSession(Player player, boolean success) {
        if (!regTasks.containsKey(player)) return;
        RegistrationSessionTask registrationSessionTask = regTasks.get(player);

        player.sendTitle(
                MessageBuilder.buildClearMessage(Config.messages.registrationMustBeSet),
                MessageBuilder.buildClearMessage(Config.messages.registrationHint),
                0,
                0,
                10
        );

        if (!success) {
            registrationSessionTask.status = RegistrationStatus.DROPPED;
        } else {
            registrationSessionTask.status = RegistrationStatus.SUCCESS;
        }

        regTasks.remove(player);
    }

    public static boolean canPassRegistration(Player player) {
        if (Config.login.mode.equals(LoginMode.STRICT)) {
            return false;
        } else if (Config.login.mode.equals(LoginMode.STRONG)) {
            for (int index = 0; index < Config.rules.stringModePermissions.size(); index++) {
                if (player.hasPermission(Config.rules.stringModePermissions.get(index)))
                    return false;
            }
            return true;
        }
        return true;
    }
}
