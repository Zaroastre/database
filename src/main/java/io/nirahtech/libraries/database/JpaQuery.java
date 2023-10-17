package io.nirahtech.libraries.database;

@FunctionalInterface
public interface JpaQuery<S, T, C, L> {
    L search(S session, T table, C criteriaQuery);
}
