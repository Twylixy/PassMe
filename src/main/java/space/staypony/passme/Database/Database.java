package space.staypony.passme.Database;

import space.staypony.passme.Config.Config;
import space.staypony.passme.Database.DatabaseEntities.PlayerDatabaseEntity;
import space.staypony.passme.Database.DatabaseEntities.SessionDatabaseEntity;

import javax.annotation.Nullable;
import java.io.File;
import java.sql.*;
import java.time.Instant;

public class Database {
    private static Connection databaseConnection;

    public static void initDatabase(String databasePath) {
        boolean databaseExists = new File(databasePath).exists();

        try {
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            databaseConnection.setAutoCommit(true);

            if (databaseExists) return;

            Statement statement = databaseConnection.createStatement();
            String createUsersQuery =
                    "CREATE TABLE users " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT NOT NULL," +
                    "hash TEXT NOT NULL," +
                    "isProtected BOOL DEFAULT 1);";

            String createSessionsQuery =
                    "CREATE TABLE sessions" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT NOT NULL," +
                    "ip TEXT NOT NULL," +
                    "createdAt INTEGER NOT NULL," +
                    "expiresAt INTEGER NOT NULL);";

            statement.execute(createUsersQuery);
            statement.execute(createSessionsQuery);
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void disposeDatabase() {
        try {
            databaseConnection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void addPlayer(String uuid, String hash) {
        try {
            String sql = "INSERT INTO users (uuid, hash) VALUES (?, ?);";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, hash);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Nullable
    public static PlayerDatabaseEntity getPlayer(String uuid) {
        try {
            String sql = "SELECT * FROM users WHERE uuid=?;";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setString(1, uuid);

            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                rs.close();
                statement.close();
                return null;
            }

            PlayerDatabaseEntity playerDatabaseEntity = new PlayerDatabaseEntity(
                    rs.getInt("id"),
                    rs.getString("uuid"),
                    rs.getString("hash"),
                    rs.getBoolean("isProtected")
            );
            rs.close();

            return playerDatabaseEntity;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void updatePlayerHash(String uuid, String newHash) {
        try {
            String sql = "UPDATE users SET hash=? WHERE uuid=?;";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setString(1, newHash);
            statement.setString(2, uuid);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void addPlayerSession(String uuid, String ip) {
        long createdAt = Instant.now().getEpochSecond();
        long expiresAt = createdAt + Config.sessions.expirationTime;

        try {
            String sql = "INSERT INTO sessions (uuid, ip, createdAt, expiresAt) VALUES (?, ?, ?, ?);";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, ip);
            statement.setLong(3, createdAt);
            statement.setLong(4, expiresAt);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Nullable
    public static SessionDatabaseEntity getValidSession(String uuid, String ip) {
        long now = Instant.now().getEpochSecond();

        try {
            String sql = "SELECT * FROM sessions WHERE uuid=? AND expiresAt > ? AND ip=?;";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setLong(2, now);
            statement.setString(3, ip);

            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                rs.close();
                statement.close();
                return null;
            }

            SessionDatabaseEntity sessionDatabaseEntity = new SessionDatabaseEntity(
                    rs.getInt("id"),
                    rs.getString("uuid"),
                    rs.getString("ip"),
                    rs.getLong("createdAt"),
                    rs.getLong("expiresAt")
            );

            rs.close();
            statement.close();

            return sessionDatabaseEntity;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void dropSession(int id) {
        try {
            String sql = "DELETE FROM sessions WHERE id=?;";

            PreparedStatement statement = databaseConnection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
