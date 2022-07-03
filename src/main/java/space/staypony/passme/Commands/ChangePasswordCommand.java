package space.staypony.passme.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Database.DatabaseEntities.PlayerDatabaseEntity;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Utils.MessageBuilder;

public class ChangePasswordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.onlyForPlayers));
            return true;
        };
        if (!AuthService.isPlayerRegistered((Player) sender)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.missingRegistration));
            return true;
        }
        if (args.length != 2)  {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.changePasswordUsage));
            return true;
        }

        Player player = (Player) sender;
        PlayerDatabaseEntity playerDatabaseEntity = Database.getPlayer(player.getUniqueId().toString());

        char[] oldPassword = args[0].toCharArray();
        char[] newPassword = args[1].toCharArray();

        try {
            if (AuthService.argon2.verify(playerDatabaseEntity.hash, oldPassword)) {
                String hash = AuthService.argon2.hash(10, 65536, 1, newPassword);
                Database.updatePlayerHash(player.getUniqueId().toString(), hash);
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
        return true;
    }
}
