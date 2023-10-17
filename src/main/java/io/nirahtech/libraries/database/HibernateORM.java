package io.nirahtech.libraries.database;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManager;

public class HibernateORM implements ObjectRelationalMapping {

    private final Configuration configuration;
    private SessionFactory sessionFactory;
    private EntityManager entityManager;

    public HibernateORM(File file, Set<Class<?>> managedClasses) {
        this.configuration = new Configuration();
        this.configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
        this.configuration.setProperty("hibernate.connection.url",  String.format("jdbc:sqlite:%s", file.getAbsolutePath()));
        this.configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        this.configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        managedClasses.forEach(managedClass -> {
            this.configuration.addAnnotatedClass(managedClass);
        });
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(this.configuration.getProperties());
        this.sessionFactory = this.configuration.buildSessionFactory(builder.build());
        this.entityManager = this.sessionFactory.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        if (this.sessionFactory.isClosed()) {
            final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(this.configuration.getProperties());
            this.sessionFactory = this.configuration.buildSessionFactory(builder.build());
            this.entityManager = this.sessionFactory.createEntityManager();
        }
        return this.entityManager;
    }
    
    @Override
    public Session openSession() {
        if (this.sessionFactory.isClosed()) {
            final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(this.configuration.getProperties());
            this.sessionFactory = this.configuration.buildSessionFactory(builder.build());
            this.entityManager = this.sessionFactory.createEntityManager();
        }
        return this.sessionFactory.openSession();
    }

    @Override
    public void close() throws IOException {
        this.entityManager.close();
        this.sessionFactory.close();
    }
    
}
