package io.nirahtech.libraries.database;


sealed interface WriteOnly extends AccessMode permits WriteOnlyCluster, WriteOnlyDatabase {
    <T> T insert(T data);
    <T> T update(T data);
    <T> void delete(T data);
}
