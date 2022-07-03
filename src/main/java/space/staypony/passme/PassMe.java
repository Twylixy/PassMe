package space.staypony.passme;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.staypony.passme.Commands.*;
import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.Database;
import space.staypony.passme.Listeners.BlockListeners;
import space.staypony.passme.Listeners.ConnectionListeners;
import space.staypony.passme.Listeners.InteractListeners;
import space.staypony.passme.Listeners.MiscListeners;

public final class PassMe extends JavaPlugin {
    public static FileConfiguration rawConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        rawConfig = getConfig();
        Config.reloadValues();
        Database.initDatabase(getDataFolder() + "\\data.db");

        getCommand("login").setExecutor(new LoginCommand());
        getCommand("logout").setExecutor(new LogoutCommand(this));
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("changepassword").setExecutor(new ChangePasswordCommand());
        getCommand("passme").setExecutor(new PassMeCommand());

        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new ConnectionListeners(this), this);
        getServer().getPluginManager().registerEvents(new InteractListeners(), this);
        getServer().getPluginManager().registerEvents(new MiscListeners(), this);
    }

    @Override
    public void onDisable() {
        Database.disposeDatabase();
    }
}
