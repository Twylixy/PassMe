package space.staypony.passme.Config;

import space.staypony.passme.Enums.LoginMode;
import space.staypony.passme.PassMe;

public class LoginField extends AbstractField {
    public int maxTries;
    public int loginTimeLimit;
    public int registrationTimeLimit;
    public LoginMode mode;

    @Override
    public void reloadValues() {
        maxTries = PassMe.rawConfig.getInt("login.max-tries");
        loginTimeLimit = PassMe.rawConfig.getInt("login.login-time-limit");
        registrationTimeLimit = PassMe.rawConfig.getInt("login.registration-time-limit");
        String rawMode = PassMe.rawConfig.getString("login.mode");

        if (rawMode == null) {
            mode = LoginMode.STRICT;
            return;
        }

        switch (rawMode) {
            case "strong": {
                mode = LoginMode.STRONG;
                break;
            }
            case "passive": {
                mode = LoginMode.PASSIVE;
                break;
            }
            default: {
                mode = LoginMode.STRICT;
                break;
            }
        }
    }
}
