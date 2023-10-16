package io.nirahtech.libraries.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.nirahtech.libraries.database.sql.Sql;

public final class WriteOnlyDatabase extends AbstractDatabase implements WriteOnly {

    public WriteOnlyDatabase(final File file) {
        super(file);
    }
    
    @Override
    public int insert(Sql sql) {
        final String sqlQuery = sql.toSql();
        if (!sqlQuery.toUpperCase().startsWith("INSERT INTO ")) {
            throw new RuntimeException("Unsupported operation");
        }
        int totalAffectedRows;
        try (Connection connection = DriverManager.getConnection(super.connectionString); Statement statement = connection.createStatement()) {
            totalAffectedRows = statement.executeUpdate(sqlQuery);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return totalAffectedRows;
    }

    @Override
    public int update(Sql sql) {
        final String sqlQuery = sql.toSql();
        if (!sqlQuery.toUpperCase().startsWith("UPDATE ")) {
            throw new RuntimeException("Unsupported operation");
        }
        int totalAffectedRows;
        try (Connection connection = DriverManager.getConnection(super.connectionString); Statement statement = connection.createStatement()) {
            totalAffectedRows = statement.executeUpdate(sqlQuery);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return totalAffectedRows;
    }

    @Override
    public int delete(Sql sql) {
        final String sqlQuery = sql.toSql();
        if (!sqlQuery.toUpperCase().startsWith("DELETE ")) {
            throw new RuntimeException("Unsupported operation");
        }
        int totalAffectedRows;
        try (Connection connection = DriverManager.getConnection(super.connectionString); Statement statement = connection.createStatement()) {
            totalAffectedRows = statement.executeUpdate(sqlQuery);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return totalAffectedRows;
    }
    
}
