package io.nirahtech.libraries.database;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaQuery;

sealed interface ReadOnly extends AccessMode permits ReadOnlyCluster, ReadOnlyDatabase {
    <T> List<T> select(Class<T> table);
    <T> List<T> search(Class<T> table, JpaQuery<Session, Class<T>, CriteriaQuery<T>, List<T>> filter);
}
