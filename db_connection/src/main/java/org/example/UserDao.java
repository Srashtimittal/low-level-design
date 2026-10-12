package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public record User(int id, String name, String email) {}

    public User createUser(String name, String email) throws SQLException {

        String sql =
                "INSERT INTO users(name, email) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            ps.setString(1, name);
            ps.setString(2, email);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return new User(
                            rs.getInt(1),
                            name,
                            email
                    );
                }
            }
        }

        throw new SQLException("Failed to create user");
    }

    public List<User> getAllUsers() throws SQLException {

        String sql =
                "SELECT id, name, email FROM users";

        List<User> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                users.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email")
                        )
                );
            }
        }

        return users;
    }

    public boolean updateUser(
            int id,
            String name,
            String email
    ) throws SQLException {

        String sql =
                "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int id) throws SQLException {

        String sql =
                "DELETE FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}