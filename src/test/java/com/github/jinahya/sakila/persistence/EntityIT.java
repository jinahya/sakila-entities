package com.github.jinahya.sakila.persistence;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Map;
import java.util.WeakHashMap;

import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.lang.StrictMath.toIntExact;
import static java.util.Collections.synchronizedMap;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

@ExtendWith({WeldJunit5Extension.class})
abstract class EntityIT<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of entity classes and their entity counts.
     */
    private static final Map<Class<?>, Long> COUNTS = synchronizedMap(new WeakHashMap<>());

    /**
     * Counts the total number of entities of specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class to count.
     * @return the number of all entities of specified class.
     */
    static long entityCount(final EntityManager entityManager, final Class<?> entityClass) {
        synchronized (COUNTS) {
            return COUNTS.computeIfAbsent(entityClass, k -> {
                final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
                criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
                final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
                final long count = typedQuery.getSingleResult();
                assert count > 0L;
                return count;
            });
        }
    }

    static long entityCount(final Class<?> entityClass) {
        return applyEntityManager(v -> entityCount(v, requireNonNull(entityClass, "entityClass is null")));
    }

    static int entityCountAsInt(final Class<?> entityClass) {
        return toIntExact(entityCount(requireNonNull(entityClass, "entityClass is null")));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Selects a random entity of specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class to select.
     * @param <T>           entity type parameter
     * @return a randomly selected entity instance of specified class.
     */
    static <T> T randomEntity(final EntityManager entityManager, final Class<T> entityClass) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        final TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(toIntExact(current().nextLong(entityCount(entityManager, entityClass))));
        typedQuery.setMaxResults(1);
        return typedQuery.getSingleResult();
    }

    static <T> T randomEntity(final Class<? extends T> entityClass) {
        return applyEntityManager(v -> randomEntity(v, requireNonNull(entityClass, "entityClass is null")));
    }

    // -----------------------------------------------------------------------------------------------------------------
    EntityIT(final Class<? extends T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager.
     *
     * @return an entity manager.
     */
    EntityManager entityManager() {
        if (entityManagerProxy == null) {
            entityManagerProxy = uncloseable(entityManager);
        }
        return entityManagerProxy;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns all count of the {@link #entityClass}.
     *
     * @return all count of the {@link #entityClass}.
     */
    final long entityCount() {
        return entityCount(entityManager(), entityClass);
    }

    /**
     * Select a random entity from database.
     *
     * @return a random entity.
     */
    final T randomEntity() {
        return randomEntity(entityManager(), entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<? extends T> entityClass;

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
