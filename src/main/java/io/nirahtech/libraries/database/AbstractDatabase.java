package io.nirahtech.libraries.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

abstract class AbstractDatabase implements Database {
    private final File file;
    private final Set<Class<?>> managedClasses = new HashSet<>();
    private ObjectRelationalMapping orm = null;

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
    }

    @Override
    public final File getFile() {
        return this.file;
    }

    @Override
    public final void manage(Class<?> table) {
        this.managedClasses.add(table);
    }

    @Override
    public final Collection<Class<?>> getManagedClasses() {
        return Collections.unmodifiableCollection(this.managedClasses);
    }

    @Override
    public void close() throws IOException {
        this.orm.close();
    }

    protected final ObjectRelationalMapping orm() {
        if (Objects.isNull(this.orm)) {
            this.orm = new HibernateORM(this.file, this.managedClasses);
        }
        return this.orm;
    }
}
