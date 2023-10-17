package io.nirahtech.libraries.database;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

sealed interface ReadOnly extends AccessMode permits ReadOnlyCluster, ReadOnlyDatabase {
    <T> List<T> select(Class<T> table);
    <T> List<T> search(Class<T> table, ThreeFunction<Session, Class<T>, Root<T>, CriteriaQuery<T>, List<T>> filter);
}
