package io.nirahtech.libraries.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.nirahtech.libraries.database.runtime.Table;

abstract class AbstractDatabase implements Database {
    private final File file;
    private final Set<Class<?>> managedClasses = new HashSet<>();
    protected final String connectionString;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected AbstractDatabase(final File file) {
        this.file = file;
        try {
            Files.createDirectories(Path.of(this.file.getParent()));
            if (Files.notExists(this.file.toPath())) {
                Files.createFile(this.file.toPath());
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.connectionString = String.format("jdbc:sqlite:%s", this.file.getAbsolutePath());
    }

    @Override
    public final File getFile() {
        return this.file;
    }

    @Override
    public final void manage(Class<?> table) {
        Table newTable = Table.from(table);
        try (Connection connection = DriverManager.getConnection(this.connectionString); Statement statement = connection.createStatement()) {
            statement.execute(newTable.generateCreationSqlQuery());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.managedClasses.add(table);
    }

    @Override
    public final Collection<Class<?>> getManagedClasses() {
        return Collections.unmodifiableCollection(this.managedClasses);
    }
}
