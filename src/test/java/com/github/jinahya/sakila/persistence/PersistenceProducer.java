package com.github.jinahya.sakila.persistence;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class PersistenceProducer {

    // -----------------------------------------------------------------------------------------------------------------
    private static final String PERSISTENCE_UNIT_NAME = "sakilaPU";

    private static final EntityManagerFactory PERSISTENCE_UNIT
            = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    @PersistenceContext
    public EntityManager produceEntityManager() {
        return PERSISTENCE_UNIT.createEntityManager();
    }

    public void disposeEntityManager(@Disposes final EntityManager entityManager) {
        entityManager.close();
    }
}
