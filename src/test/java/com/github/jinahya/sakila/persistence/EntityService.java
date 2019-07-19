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

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.util.Collections.synchronizedMap;
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
     * A map of entity classes and entity names.
     */
    private static final Map<Class<?>, String> ENTITY_NAMES = synchronizedMap(new WeakHashMap<>());

    /**
     * Returns the entity name of the specified entity class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class whose name is returned.
     * @return the entity name of {@code entityClass}.
     * @see #entityName(Class)
     */
    static String entityName(@NotNull final EntityManager entityManager, @NotNull final Class<?> entityClass) {
        synchronized (ENTITY_NAMES) {
            return ENTITY_NAMES.computeIfAbsent(entityClass, k ->
                    entityManager.getEntityManagerFactory().getMetamodel().entity(k).getName());
        }
    }

    /**
     * Returns the entity name of the specified entity class.
     *
     * @param entityClass the entity class whose entity name is returned.
     * @return the entity name of {@code entityClass}.
     * @see #entityName(EntityManager, Class)
     */
    static String entityName(@NotNull final Class<?> entityClass) {
        return PersistenceProducer.applyEntityManager(m -> entityName(m, entityClass));
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

    /**
     * Applies the (injected) entity manager to specified function and returns the result.
     *
     * @param function the function to be applied.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    <R> R applyEntityManager(@NotNull final Function<? super EntityManager, ? extends R> function) {
        return requireNonNull(function, "function is null").apply(entityManager());
    }

    <U, R> R applyEntityManager(@NotNull final BiFunction<? super EntityManager, ? super U, ? extends R> function,
                                @NotNull final Supplier<? extends U> supplier) {
        return applyEntityManager(v -> requireNonNull(function, "function is null")
                .apply(v, requireNonNull(supplier, "supplier is null").get()));
    }

    void acceptEntityManager(@NotNull final Consumer<? super EntityManager> consumer) {
        applyEntityManager(v -> {
            requireNonNull(consumer, "consumer is null").accept(v);
            return null;
        });
    }

    <U> void acceptEntityManager(@NotNull final BiConsumer<? super EntityManager, ? super U> consumer,
                                 @NotNull final Supplier<? extends U> supplier) {
        acceptEntityManager(v -> requireNonNull(consumer, "consumer is null")
                .accept(v, requireNonNull(supplier, "supplier is null").get()));
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

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class of entity to serve.
     */
    final Class<T> entityClass;

    @Inject
    private EntityManager entityManager;

    private transient EntityManager entityManagerUncloseable;
}
