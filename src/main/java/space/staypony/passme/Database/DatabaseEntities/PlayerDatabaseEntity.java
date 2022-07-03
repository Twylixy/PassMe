package space.staypony.passme.Database.DatabaseEntities;

public class PlayerDatabaseEntity {
    public final int id;
    public final String uuid;
    public final String hash;
    public final boolean isProtected;

    public PlayerDatabaseEntity(int id, String uuid, String hash, boolean isProtected) {
        this.id = id;
        this.uuid = uuid;
        this.hash = hash;
        this.isProtected = isProtected;
    }
}
