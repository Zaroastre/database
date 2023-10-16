package io.nirahtech.libraries.database;

import java.io.File;
import java.util.Collection;

public interface Database {
    File getFile();
    void manage(Class<?> table);
    Collection<Class<?>> getManagedClasses();
}
