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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * An abstract class for entity service classes.
 *
 * @param <T> entity type parameter
 */
abstract class EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the class of the entity to serve.
     */
    EntityService(final Class<T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of entities of {@link #entityClass}.
     *
     * @return the total number of entities.
     */
    public long size() {
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery("SELECT COUNT(e) FROM " + entityName() + " AS e");
            return (Long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager.createQuery(
                    "SELECT COUNT(e) FROM " + entityName() + " AS e", Long.class);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(root));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager for accessing persistence context.
     *
     * @return an entity manager.
     */
    final EntityManager entityManager() {
        if (entityManagerProxy == null) {
            entityManagerProxy = uncloseable(entityManager);
        }
        return entityManagerProxy;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the name of the entity from {@link EntityManagerFactory#getMetamodel()}.
     *
     * @return the name of the entity.
     * @see EntityManager#getEntityManagerFactory()
     * @see EntityManagerFactory#getMetamodel()
     * @see javax.persistence.metamodel.Metamodel#entity(Class)
     * @see EntityType#getName()
     */
    final String entityName() {
        if (entityName == null) {
            try {
                final EntityManagerFactory entityManagerFactory = entityManager().getEntityManagerFactory();
                final EntityType<? extends T> entityType = entityManagerFactory.getMetamodel().entity(entityClass);
                entityName = entityType.getName();
            } catch (final Exception e) { // OpenJPA
                entityName = entityClass.getSimpleName();
            }
        }
        return entityName;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class of entity to serve.
     */
    final Class<T> entityClass;

    @Inject
    private EntityManager entityManager;

    private transient EntityManager entityManagerProxy;

    private transient String entityName;
}
