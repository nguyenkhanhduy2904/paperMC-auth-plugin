package me.duy.minecraftauth.database;

import java.io.File;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private Connection connection;
    private final File dataFolder;

    public DatabaseManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, "auth.db");

        connection = DriverManager.getConnection(
                "jdbc:sqlite:" + dbFile.getAbsolutePath()
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTables() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS accounts (
                uuid TEXT PRIMARY KEY,
                username TEXT NOT NULL,
                password TEXT NOT NULL
            )
            """;

        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
//    public Set<UUID> loadRegisteredSet()throws SQLException{
//        Set<UUID> set = new HashSet<>();
//
//        String sql = "";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql);
//             ResultSet result = statement.executeQuery()) {
//
//            while (result.next()) {
//                String uuidString = result.getString("uuid");
//                UUID uuid = UUID.fromString(uuidString);
//
//                set.add(uuid);
//            }
//        }
//
//        return set;
//    }

    public Map<UUID, String> loadPasswordMap() throws SQLException {
        Map<UUID, String> map = new HashMap<>();

        String sql = "SELECT uuid, password FROM accounts";

        try (
                PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet resultSet = pstmt.executeQuery()
        ) {
            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String password = resultSet.getString("password");

                map.put(uuid, password);
            }
        }

        return map;
    }

    public boolean insertAccount(UUID uuid, String password, String username){

        String sql ="INSERT into accounts (uuid, password, username) VALUES (?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, password);
            pstmt.setString(3, username);

            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }




    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
