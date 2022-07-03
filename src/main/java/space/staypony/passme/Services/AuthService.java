package space.staypony.passme.Services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Enums.AuthStatus;
import space.staypony.passme.Enums.LoginMode;
import space.staypony.passme.Enums.RegistrationStatus;
import space.staypony.passme.PassMe;
import space.staypony.passme.Tasks.AuthSessionTask;
import space.staypony.passme.Tasks.RegistrationSessionTask;

import java.util.HashMap;

public class AuthService {
    private static final HashMap<Player, AuthSessionTask> authTasks = new HashMap<>();
    private static final HashMap<Player, RegistrationSessionTask> regTasks = new HashMap<>();
    public static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    public static boolean isPlayerRegistered(Player player) {
        return Database.getPlayer(player.getUniqueId().toString()) != null;
    }

    public static boolean isPlayerInAnySession(Player player) {
        return authTasks.containsKey(player) || regTasks.containsKey(player);
    }

    public static void beginAuthSession(PassMe plugin, Player player) {
        if (authTasks.containsKey(player)) return;

        AuthSessionTask authSessionTask = new AuthSessionTask(player);
        authTasks.put(player, authSessionTask);

        authSessionTask.runTaskTimer(plugin, 100, 100);
    }

    public static void beginRegistrationSession(PassMe plugin, Player player) {
        if (regTasks.containsKey(player)) return;

        RegistrationSessionTask registrationSessionTask = new RegistrationSessionTask(player);
        regTasks.put(player, registrationSessionTask);

        registrationSessionTask.runTaskTimer(plugin, 100, 100);
    }

    public static void closeAuthSession(Player player, boolean success) {
        if (!authTasks.containsKey(player)) return;
        AuthSessionTask authSessionTask = authTasks.get(player);

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
