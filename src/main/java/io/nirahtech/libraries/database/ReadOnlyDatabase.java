package io.nirahtech.libraries.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import io.nirahtech.libraries.database.sql.Sql;

public final class ReadOnlyDatabase extends AbstractDatabase implements ReadOnly {

    public ReadOnlyDatabase(final File file) {
        super(file);
    }

    @Override
    public ResultSet select(Sql sql) {
        final String sqlQuery = sql.toSql();
        if (!sqlQuery.toUpperCase().startsWith("SELECT ")) {
            throw new RuntimeException("Unsupported operation");
        }
        ResultSet resultSet;
        System.out.println(sqlQuery);
        try (Connection connection = DriverManager.getConnection(super.connectionString); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return resultSet;
    }

}
