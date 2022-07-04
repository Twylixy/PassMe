package space.staypony.passme.Utils;

import org.bukkit.ChatColor;
import space.staypony.passme.Config.Config;

public class MessageBuilder {
    public static String buildMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', Config.messages.pluginPrefix + " " + message);
    }

    public static String buildClearMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
