package space.staypony.passme.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Database.DatabaseEntities.PlayerDatabaseEntity;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Services.SessionService;
import space.staypony.passme.Utils.MessageBuilder;

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.onlyForPlayers));
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.loginUsage));
            return true;
        }
        if (!AuthService.isPlayerRegistered((Player) sender)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.missingRegistration));
            return true;
        }
        if (!AuthService.isPlayerInAnySession((Player) sender)) return true;

        Player player = (Player) sender;
        String playerIp = SessionService.resolvePlayerIp(player);
        PlayerDatabaseEntity playerDatabaseEntity = Database.getPlayer(player.getName());
        char[] password = args[0].toCharArray();

        try {
            if (AuthService.argon2.verify(playerDatabaseEntity.hash, password)) {
                AuthService.closeAuthSession(player, true);

                if (playerIp != null)
                    Database.addPlayerSession(player.getName(), playerIp);

                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.loginSuccess));
            } else {
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.wrongPassword));
                AuthService.addLoginFail(player);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            AuthService.argon2.wipeArray(password);
        }
        return true;
    }
}
