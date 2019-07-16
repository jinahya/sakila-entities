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
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.function.Function;

import static com.github.jinahya.sakila.persistence.BeanValidationTests.isValid;
import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.lang.StrictMath.toIntExact;
import static java.util.Collections.synchronizedMap;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * An abstract class for testing entities.
 *
 * @param <T> entity type parameter
 */
@ExtendWith({WeldJunit5Extension.class})
abstract class EntityIT<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Applies a stream of a resource of specified name to specified function and returns the result.
     *
     * @param name     the name of the resource.
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @throws IOException if an I/O error occurs.
     * @see #applyResourceScanner(String, Function)
     */
    static <R> R applyResourceStream(@NotNull final String name,
                                     @NotNull final Function<? super InputStream, ? extends R> function)
            throws IOException {
        try (InputStream stream = ActorServiceIT.class.getResourceAsStream(name)) {
            return function.apply(stream);
        }
    }

    /**
     * Applies a scanner of the resource of specified named to specified function.
     *
     * @param name     the resource name.
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @throws IOException if an I/O error occurs.
     * @see #applyResourceStream(String, Function)
     */
    static <R> R applyResourceScanner(@NotNull final String name,
                                      @NotNull final Function<? super Scanner, ? extends R> function)
            throws IOException {
        return applyResourceStream(
                name,
                v -> {
                    try (Scanner scanner = new Scanner(v)) {
                        return function.apply(scanner);
                    }
                }
        );
    }

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

    /**
     * Returns the total number of entities of specified class.
     *
     * @param entityClass the entity class.
     * @return the total number of entities of specified class.
     */
    static long entityCount(final Class<?> entityClass) {
        return applyEntityManager(v -> entityCount(v, requireNonNull(entityClass, "entityClass is null")));
    }

    /**
     * Returns the value of {@link #entityCount(Class)} as {@code int}.
     *
     * @param entityClass the entity class.
     * @return the value of {@link #entityCount(Class)} as {@code int}.
     * @see StrictMath#toIntExact(long)
     */
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

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to test.
     * @see #entityClass
     */
    EntityIT(final Class<? extends T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void assertEntityCountAsIntReturnsNonZeroPositive() {
        assertTrue(entityCountAsInt() > 0);
    }

    @RepeatedTest(16)
    void testRandomEntity() {
        final T randomEntity = randomEntity();
        assertNotNull(randomEntity);
        assertTrue(isValid(randomEntity));
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
     * Returns the total number of {@link #entityClass}.
     *
     * @return the total number of {@link #entityClass}.
     */
    @Positive
    final long entityCount() {
        return entityCount(entityManager(), entityClass);
    }

    /**
     * Returns the value of {@link #entityCount()} as {@code int}.
     *
     * @return the value of {@link #entityCount()} as {@code int}.
     * @see StrictMath#toIntExact(long)
     */
    @Positive
    final int entityCountAsInt() {
        return toIntExact(entityCount());
    }

    /**
     * Select a random entity from database.
     *
     * @return a random entity.
     */
    @Valid
    @NotNull
    final T randomEntity() {
        return randomEntity(entityManager(), entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    final Class<? extends T> entityClass;

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
