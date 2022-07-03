package space.staypony.passme.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Services.SessionService;
import space.staypony.passme.Utils.MessageBuilder;

import java.util.Arrays;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.onlyForPlayers));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.registerUsage));
            return true;
        }
        if (AuthService.isPlayerRegistered((Player) sender)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.missingRegistration));
            return true;
        }

        Player player = (Player) sender;
        String playerIp = SessionService.resolvePlayerIp(player);

        char[] password = args[0].toCharArray();
        char[] againPassword = args[1].toCharArray();

        try {
            if (Arrays.equals(password, againPassword)) {
                String hash = AuthService.argon2.hash(10, 65536, 1, password);
                Database.addPlayer(player.getUniqueId().toString(), hash);
                AuthService.closeRegisterSession(player, true);

                if (playerIp != null)
                    Database.addPlayerSession(player.getUniqueId().toString(), playerIp);
                sender.sendMessage(MessageBuilder.buildMessage(Config.messages.registerSuccess));
            } else {
                sender.sendMessage("Passwords doesn't equals");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            AuthService.argon2.wipeArray(password);
            AuthService.argon2.wipeArray(againPassword);
        }
        return true;
    }
}
