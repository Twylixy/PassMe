package space.staypony.passme.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.staypony.passme.Config.Config;
import space.staypony.passme.PassMe;
import space.staypony.passme.Services.AuthService;
import space.staypony.passme.Services.SessionService;
import space.staypony.passme.Utils.MessageBuilder;

public class LogoutCommand implements CommandExecutor {
    private final PassMe plugin;

    public LogoutCommand(PassMe plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.onlyForPlayers));
            return true;
        }
        if (!AuthService.isPlayerRegistered((Player) sender)) {
            sender.sendMessage(MessageBuilder.buildMessage(Config.messages.missingRegistration));
            return true;
        }

        sender.sendMessage(MessageBuilder.buildMessage(Config.messages.logoutSuccess));
        SessionService.dropActiveSession((Player) sender);
        AuthService.beginAuthSession(plugin, (Player) sender);
        return true;
    }
}
