package space.staypony.passme.Database.DatabaseEntities;

public class SessionDatabaseEntity {
    public final int id;
    public final String uuid;
    public final String ip;
    public final long createdAt;
    public final long expiresAt;

    public SessionDatabaseEntity(int id, String uuid, String ip, long createdAt, long expiresAt) {
        this.id = id;
        this.uuid = uuid;
        this.ip = ip;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
