package io.nirahtech.libraries.database;

import java.io.File;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public final class ReadOnlyDatabase extends AbstractDatabase implements ReadOnly {

    public ReadOnlyDatabase(final File file) {
        super(file);
    }

    @Override
    public <T> List<T> select(Class<T> table) {
        Session session = super.orm().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(table);
        Root<T> rootEntry = criteriaQuery.from(table);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public <T> List<T> search(Class<T> table, JpaQuery<Session, Class<T>, CriteriaQuery<T>, List<T>> filter) {
        final Session session = super.orm().openSession();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(table);
        return filter.search(session, table, criteriaQuery);
    }

}
