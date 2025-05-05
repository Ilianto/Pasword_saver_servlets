package com.vsu.repository;

import com.vsu.Entity.Profile;
import com.vsu.exception.DBException;

import java.sql.*;

public class ProfileRepository {
    private static final String FIND_BY_LOGIN_QUERY =
            "SELECT id, login, password, salt FROM profile WHERE login = ?";
    private static final String INSERT_PROFILE_QUERY =
            "INSERT INTO profile (login, password, salt) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = " SELECT id, login, password, salt FROM profile WHERE id = ?";
    public Profile findByLogin(String login) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(FIND_BY_LOGIN_QUERY);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            Profile profile = new Profile();
            profile.setId(rs.getLong("id"));
            profile.setLogin(rs.getString("login"));
            profile.setPassword(rs.getString("password"));
            profile.setSalt(rs.getString("salt")); // Добавляем поле соли в сущность
            return profile;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    public Profile findById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profile profile = new Profile();
                profile.setId(rs.getLong("id"));
                profile.setLogin(rs.getString("login"));
                profile.setPassword(rs.getString("password"));
                profile.setSalt(rs.getString("salt"));
                return profile;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean save(Profile profile) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(INSERT_PROFILE_QUERY);
            stmt.setString(1, profile.getLogin());
            stmt.setString(2, profile.getPassword());
            stmt.setString(3, profile.getSalt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}