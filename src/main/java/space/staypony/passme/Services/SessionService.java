package space.staypony.passme.Services;


import org.bukkit.entity.Player;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Database.DatabaseEntities.SessionDatabaseEntity;

import javax.annotation.Nullable;

public class SessionService {
    @Nullable
    public static String resolvePlayerIp(Player player) {
        if (player.getAddress() == null) return null;
        return player.getAddress().toString().split(":")[0];
    }

    public static boolean canResumeSession(Player player) {
        String playerIp = resolvePlayerIp(player);

        if (playerIp == null) return false;
        return Database.getValidSession(player.getName(), playerIp) != null;
    }

    public static void purgeSessions(Player player) {
        Database.purgeSessions(player.getName());
    }

    public static void dropActiveSession(Player player) {
        String playerIp = resolvePlayerIp(player);

        if (playerIp == null) return;
        SessionDatabaseEntity sessionDatabaseEntity = Database.getValidSession(player.getName(), playerIp);

        if (sessionDatabaseEntity == null) return;
        Database.dropSession(sessionDatabaseEntity.id);
    }
}
