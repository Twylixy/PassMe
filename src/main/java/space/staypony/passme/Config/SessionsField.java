package space.staypony.passme.Config;

import space.staypony.passme.PassMe;

public class SessionsField extends AbstractField {
    public boolean isEnabled;
    public int expirationTime;

    @Override
    public void reloadValues() {
        isEnabled = PassMe.rawConfig.getBoolean("sessions.is-enabled");
        expirationTime = PassMe.rawConfig.getInt("sessions.expiration-time");
    }
}
