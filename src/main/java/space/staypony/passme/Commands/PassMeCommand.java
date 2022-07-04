package space.staypony.passme.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import space.staypony.passme.PassMe;

public class PassMeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equals("reload") && sender.hasPermission("passme.reload")) {
            PassMe.updateConfig();
            sender.sendMessage("[PassMe] Config reloaded");
            return true;
        }

        String pluginMessage = "&6&lPassMe &r&7- &2Authentication plugin&r\n" +
                "    &9Created by: &7Twylix\n" +
                "    &9Github: &7https://github.com/Twylixy/PassMe/\n" +
                "    &9Telegram: &7@Twylixy\n" +
                "    &9Discord: &7Twylix#7068";

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pluginMessage));
        return true;
    }
}
