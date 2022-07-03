package space.staypony.passme.Config;

public class Config {
    public static LoginField login = new LoginField();
    public static MessagesField messages = new MessagesField();
    public static SessionsField sessions = new SessionsField();
    public static RulesField rules = new RulesField();

    public static void reloadValues() {
        login.reloadValues();
        messages.reloadValues();
        sessions.reloadValues();
        rules.reloadValues();
    }
}
