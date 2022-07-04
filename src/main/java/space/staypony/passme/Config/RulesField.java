package space.staypony.passme.Config;

import space.staypony.passme.PassMe;

import java.util.List;

public class RulesField extends AbstractField {
    public boolean registrationRequired;
    public List<String> strongModePermissions;
    public List<String> allowedCommandsDuringLogin;

    @Override
    public void reloadValues() {
        registrationRequired = PassMe.rawConfig.getBoolean("registration-required");
        strongModePermissions = PassMe.rawConfig.getStringList("rules.strong-mode-permissions");
        allowedCommandsDuringLogin = PassMe.rawConfig.getStringList("rules.allowed-commands-during-login");
    }
}
