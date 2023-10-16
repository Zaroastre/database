package io.nirahtech.libraries.database;

import io.nirahtech.libraries.database.sql.Sql;

sealed interface WriteOnly extends AccessMode permits WriteOnlyCluster, WriteOnlyDatabase {
    int insert(Sql sql);
    int update(Sql sql);
    int delete(Sql sql);
}
