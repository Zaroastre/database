package io.nirahtech.libraries.database;

import java.sql.ResultSet;

import io.nirahtech.libraries.database.sql.Sql;

sealed interface ReadOnly extends AccessMode permits ReadOnlyCluster, ReadOnlyDatabase {
    ResultSet select(Sql sql);
}
