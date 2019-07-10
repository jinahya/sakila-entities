package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * An abstract class for testing an entity class.
 *
 * @param <T> entity type parameter
 */
@ExtendWith({WeldJunit5Extension.class})
@Slf4j
public abstract class EntityIT<T> extends AbstractEntityTest {

    // -----------------------------------------------------------------------------------------------------------------
    @Deprecated
    protected static List selectAll(final EntityManager entityManager, final Class<?> entityClass) {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " AS e").getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance for specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public EntityIT(final Class<? extends T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the injected instance of entity manager.
     *
     * @return the injected instance of entity manager.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    @PersistenceContext
    private transient EntityManager entityManager;
}
