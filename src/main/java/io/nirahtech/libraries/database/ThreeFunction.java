package io.nirahtech.libraries.database;

@FunctionalInterface
public interface ThreeFunction<S, T, C, R, L> {
    L execute(S session, T table, C criteriaQuery, R root);
}
