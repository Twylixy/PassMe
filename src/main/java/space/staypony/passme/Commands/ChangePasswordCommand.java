package space.staypony.passme.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Database.DatabaseEntities.PlayerDatabaseEntity;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Services.SessionService;
import space.staypony.passme.Utils.MessageBuilder;

public class ChangePasswordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args.length != 2) {
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.changePasswordConsoleUsage));
                return true;
            }

            String playerName = args[0];
            char[] password = args[1].toCharArray();

            Player player = Bukkit.getPlayerExact(playerName);

            if (player == null || !AuthService.isPlayerRegistered(player)) {
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.playerNotFound));
                return true;
            }

            try {
                String hash = AuthService.argon2.hash(10, 65536, 1, password);
                Database.updatePlayerHash(player.getName(), hash);
                SessionService.purgeSessions(player);
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.passwordChanged));
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                AuthService.argon2.wipeArray(password);
            }

            return true;
        } else if (sender instanceof Player) {
            if (!AuthService.isPlayerRegistered((Player) sender)) {
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.missingRegistration));
                return true;
            }
            if (args.length != 2)  {
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.changePasswordUsage));
                return true;
            }

            Player player = (Player) sender;
            PlayerDatabaseEntity playerDatabaseEntity = Database.getPlayer(player.getName());

            char[] oldPassword = args[0].toCharArray();
            char[] newPassword = args[1].toCharArray();

            try {
                if (AuthService.argon2.verify(playerDatabaseEntity.hash, oldPassword)) {
                    String hash = AuthService.argon2.hash(10, 65536, 1, newPassword);
                    Database.updatePlayerHash(player.getName(), hash);
                    SessionService.purgeSessions(player);
                    player.sendMessage(MessageBuilder.buildMessage(Config.messages.passwordChanged));
                } else {
                    player.sendMessage(MessageBuilder.buildMessage(Config.messages.wrongPassword));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                AuthService.argon2.wipeArray(oldPassword);
                AuthService.argon2.wipeArray(newPassword);
            }
        }
        return true;
    }
}
