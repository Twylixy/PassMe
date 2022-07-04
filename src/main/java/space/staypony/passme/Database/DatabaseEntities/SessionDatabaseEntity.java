package space.staypony.passme.Database.DatabaseEntities;

public class SessionDatabaseEntity {
    public final int id;
    public final String username;
    public final String ip;
    public final long createdAt;
    public final long expiresAt;

    public SessionDatabaseEntity(int id, String username, String ip, long createdAt, long expiresAt) {
        this.id = id;
        this.username = username;
        this.ip = ip;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
