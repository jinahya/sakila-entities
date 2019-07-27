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

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;

import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * An abstract class for entity service classes.
 *
 * @param <T> entity type parameter
 */
@Slf4j
abstract class EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static Metamodel metamodel(@NotNull final EntityManager entityManager) {
        return entityManager.getEntityManagerFactory().getMetamodel();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <X> ManagedType<X> managedType(@NotNull final EntityManager entityManager,
                                          @NotNull final Class<X> entityClass) {
        return metamodel(entityManager).managedType(entityClass);
    }

    static <X> EntityType<X> entityType(@NotNull final EntityManager entityManager,
                                        @NotNull final Class<X> entityClass) {
        return metamodel(entityManager).entity(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <X, Y> SingularAttribute<? super X, Y> singularAttribute(final EntityManager entityManager,
                                                                    final Class<X> entityClass,
                                                                    final String attributeName,
                                                                    final Class<Y> attributeType) {
        return managedType(entityManager, entityClass).getSingularAttribute(attributeName, attributeType);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the entity name of the specified entity class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class whose name is returned.
     * @return the entity name of {@code entityClass}.
     * @see EntityType#getName()
     */
    static String entityName(@NotNull final EntityManager entityManager, @NotNull final Class<?> entityClass) {
        return entityType(entityManager, entityClass).getName();
    }

    static String entityName(@NotNull final Class<?> entityClass) {
        return applyEntityManager(v -> entityName(v, entityClass));
    }

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
     * Returns the total number of entities of {@link #entityClass} in database.
     *
     * @return the total number of entities.
     */
    public long count() {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT COUNT(e) FROM " + entityName() + " AS e");
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(e) FROM " + entityName() + " AS e", Long.class);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the entity name of the {@link #entityClass}.
     *
     * @return the name of the {@code #entityClass}.
     */
    final @NotNull String entityName() {
        return entityName(entityManager(), entityClass);
    }

    final @NotNull <A> SingularAttribute<? super T, A> singularAttribute(@NotNull final String attributeName,
                                                                         @NotNull final Class<A> attributeType) {
        return singularAttribute(entityManager(), entityClass, attributeName, attributeType);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager for accessing persistence context.
     *
     * @return an entity manager.
     */
    final @NotNull EntityManager entityManager() {
        if (entityManagerUncloseable == null) {
            entityManagerUncloseable = uncloseable(entityManager);
        }
        return entityManagerUncloseable;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class of entity to serve.
     */
    final Class<T> entityClass;

    @Inject
    private EntityManager entityManager;

    private transient EntityManager entityManagerUncloseable;
}
