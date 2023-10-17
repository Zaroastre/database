package io.nirahtech.libraries.database;

import java.io.Closeable;
import java.io.File;
import java.util.Collection;

public interface Database extends Closeable {
    File getFile();
    void manage(Class<?> table);
    Collection<Class<?>> getManagedClasses();
}
