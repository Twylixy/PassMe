package space.staypony.passme.Config;

import space.staypony.passme.PassMe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RulesField extends AbstractField {
    public boolean registrationRequired;
    public List<String> stringModePermissions;
    public List<String> allowedCommandsDuringLogin;

    @Override
    public void reloadValues() {
        registrationRequired = PassMe.rawConfig.getBoolean("registration-required");
        stringModePermissions = PassMe.rawConfig.getStringList("rules.strong-mode-permissions");
        allowedCommandsDuringLogin = PassMe.rawConfig.getStringList("rules.allowed-commands-during-login");
    }
}
