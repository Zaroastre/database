package io.nirahtech.libraries.database;

import java.util.List;

sealed interface ReadOnly extends AccessMode permits ReadOnlyCluster, ReadOnlyDatabase {
    <T> List<T> select(Class<T> table);
}
