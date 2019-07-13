package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Map;
import java.util.WeakHashMap;

import static java.lang.StrictMath.toIntExact;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

@ExtendWith({WeldJunit5Extension.class})
abstract class EntityServiceIT<T extends EntityService<U>, U> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A tag value for JPQL.
     */
    static final String TAG_JPQL = "jpql";

    /**
     * A tag value for Criteria-API.
     */
    static final String TAG_CRITERIA_API = "criteria-api";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of entity classes and their entity counts.
     */
    private static Map<Class<?>, Long> COUNTS;

    private static Map<Class<?>, Long> counts() {
        if (COUNTS == null) {
            COUNTS = new WeakHashMap<>();
        }
        return COUNTS;
    }

    /**
     * Counts the total number of entities of specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class to count.
     * @return the number of all entities of specified class.
     */
    static long entityCount(final EntityManager entityManager, final Class<?> entityClass) {
        return counts().computeIfAbsent(entityClass, k -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
            final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            final long count = typedQuery.getSingleResult();
            assert count > 0L;
            return count;
        });
    }

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

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified service class and entity class.
     *
     * @param serviceClass the service class to test.
     * @param entityClass  the target entity class of the {@code serviceClass}.
     * @see #serviceClass
     * @see #entityClass
     */
    EntityServiceIT(final Class<? extends T> serviceClass, final Class<? extends U> entityClass) {
        super();
        this.serviceClass = requireNonNull(serviceClass, "serviceClass is null");
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an instance of {@link #serviceClass}.
     *
     * @return an instance of {@link #serviceClass}.
     */
    T serviceInstance() {
        return serviceInstance.select(serviceClass).get();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager.
     *
     * @return an entity manager.
     */
    EntityManager entityManager() {
        return entityManager;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns all count of the {@link #entityClass}.
     *
     * @return all count of the {@link #entityClass}.
     */
    final long entityCount() {
        if (entityClass == null) {
            entityCount = entityCount(entityManager, entityClass);
        }
        return entityCount;
    }

    final U randomEntity() {
        return randomEntity(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The service class to test.
     */
    final Class<? extends T> serviceClass;

    /**
     * The target entity class of the {@link #serviceClass}.
     */
    final Class<? extends U> entityClass;

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private transient Instance<EntityService> serviceInstance;

    @Inject
    private EntityManager entityManager;

    // -----------------------------------------------------------------------------------------------------------------
    private transient Long entityCount;
}
