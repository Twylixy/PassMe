package space.staypony.passme.Database.DatabaseEntities;

public class PlayerDatabaseEntity {
    public final int id;
    public final String username;
    public final String hash;
    public final boolean isProtected;

    public PlayerDatabaseEntity(int id, String username, String hash, boolean isProtected) {
        this.id = id;
        this.username = username;
        this.hash = hash;
        this.isProtected = isProtected;
    }
}
