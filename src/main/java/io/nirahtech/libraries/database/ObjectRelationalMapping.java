package io.nirahtech.libraries.database;

import java.io.Closeable;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;

public interface ObjectRelationalMapping extends Closeable {
    EntityManager getEntityManager();
    Session openSession();
    
}
