package io.nirahtech.libraries.database;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.Transaction;


public final class WriteOnlyDatabase extends AbstractDatabase implements WriteOnly {

    public WriteOnlyDatabase(final File file) {
        super(file);
    }
    
    @Override
    public <T> T insert(T data) {
        Session session = super.orm().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(data);
        transaction.commit();
        return data;
    }

    @Override
    public <T> T update(T data) {
        Session session = super.orm().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(data);
        transaction.commit();
        return data;
    }

    @Override
    public <T> void delete(T data) {
        Session session = super.orm().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(data);
        transaction.commit();
    }
    
}
