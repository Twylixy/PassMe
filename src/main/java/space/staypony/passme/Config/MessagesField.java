package space.staypony.passme.Config;

import space.staypony.passme.PassMe;

public class MessagesField extends AbstractField {
    public String pluginPrefix;
    public String remindRegister;
    public String registerSuccess;
    public String loginRequired;
    public String loginHint;
    public String loginSuccess;
    public String logoutSuccess;
    public String sessionResumed;
    public String wrongPassword;
    public String sessionExpired;
    public String tooManyFails;
    public String passwordChanged;
    public String onlyForPlayers;
    public String loginUsage;
    public String registerUsage;
    public String registerConsoleUsage;
    public String changePasswordUsage;
    public String changePasswordConsoleUsage;
    public String missingRegistration;
    public String loginTimeExceed;
    public String registrationRequired;
    public String registrationHint;
    public String registrationTimeExceed;
    public String registrationMustBeSet;
    public String playerNotFound;
    public String passwordsNotEquals;

    @Override
    public void reloadValues() {
        pluginPrefix = PassMe.rawConfig.getString("messages.plugin-prefix");
        remindRegister = PassMe.rawConfig.getString("messages.remind-register");
        registerSuccess = PassMe.rawConfig.getString("messages.register-success");
        loginRequired = PassMe.rawConfig.getString("messages.login-required");
        loginHint = PassMe.rawConfig.getString("messages.login-hint");
        loginSuccess = PassMe.rawConfig.getString("messages.login-success");
        logoutSuccess = PassMe.rawConfig.getString("messages.logout-success");
        sessionResumed = PassMe.rawConfig.getString("messages.session-resumed");
        wrongPassword = PassMe.rawConfig.getString("messages.wrong-password");
        sessionExpired = PassMe.rawConfig.getString("messages.session-expired");
        tooManyFails = PassMe.rawConfig.getString("messages.too-many-fails");
        passwordChanged = PassMe.rawConfig.getString("messages.password-changed");
        onlyForPlayers = PassMe.rawConfig.getString("messages.only-for-players");
        loginUsage = PassMe.rawConfig.getString("messages.login-usage");
        registerUsage = PassMe.rawConfig.getString("messages.register-usage");
        registerConsoleUsage = PassMe.rawConfig.getString("messages.register-console-usage");
        changePasswordUsage = PassMe.rawConfig.getString("messages.change-password-usage");
        changePasswordConsoleUsage = PassMe.rawConfig.getString("messages.change-password-console-usage");
        missingRegistration = PassMe.rawConfig.getString("messages.missing-registration");
        loginTimeExceed = PassMe.rawConfig.getString("messages.login-time-exceed");
        registrationRequired = PassMe.rawConfig.getString("messages.registration-required");
        registrationHint = PassMe.rawConfig.getString("messages.registration-hint");
        registrationTimeExceed = PassMe.rawConfig.getString("messages.registration-time-exceed");
        registrationMustBeSet = PassMe.rawConfig.getString("messages.registration-must-be-set");
        playerNotFound = PassMe.rawConfig.getString("messages.player-not-found");
        passwordsNotEquals = PassMe.rawConfig.getString("messages.passwords-not-equals");
    }
}
