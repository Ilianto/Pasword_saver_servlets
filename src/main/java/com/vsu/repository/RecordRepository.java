package com.vsu.repository;

import com.vsu.Entity.Record;
import com.vsu.exception.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordRepository {
    private static final String INSERT_RECORD_QUERY = "INSERT INTO record (profile_id, site_address, login, password) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM record WHERE id = ?";
    private static final String FIND_BY_PROFILE_ID_QUERY = "SELECT * FROM record WHERE profile_id = ?";
    private static final String UPDATE_QUERY = "UPDATE record SET site_address = ?, login = ?, password = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM record WHERE id = ?";

    public boolean save(Record record) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_RECORD_QUERY)) {
            stmt.setLong(1, record.getProfileId());
            stmt.setString(2, record.getSiteAddress());
            stmt.setString(3, record.getLogin());
            stmt.setString(4, record.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Record findById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return getRecordsFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public List<Record> getRecordsByProfileId(Long id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(FIND_BY_PROFILE_ID_QUERY)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            List<Record> records = new ArrayList<>();
            while (rs.next()) {
                records.add(getRecordsFromResultSet(rs));
            }
            return records;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private Record getRecordsFromResultSet(ResultSet rs) throws SQLException {
        Record record = new Record();
        record.setId(rs.getLong("id"));
        record.setProfileId(rs.getLong("profile_id"));
        record.setSiteAddress(rs.getString("site_address"));
        record.setLogin(rs.getString("login"));
        record.setPassword(rs.getString("password"));
        return record;
    }


    public boolean update(Record record) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY)) {
            stmt.setString(1, record.getSiteAddress());
            stmt.setString(2, record.getLogin());
            stmt.setString(3, record.getPassword());
            stmt.setLong(4, record.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}