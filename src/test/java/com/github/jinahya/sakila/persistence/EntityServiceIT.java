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
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.function.Function;

import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.lang.StrictMath.toIntExact;
import static java.util.Collections.synchronizedMap;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith({WeldJunit5Extension.class})
@Slf4j
abstract class EntityServiceIT<T extends EntityService<U>, U> {

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
        try (InputStream stream = EntityServiceIT.class.getResourceAsStream(requireNonNull(name, "name is null"))) {
            if (stream == null) {
                throw new RuntimeException("no resource for " + name);
            }
            return requireNonNull(function, "function is null").apply(stream);
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
    private static final Map<Class<?>, Long> ENTITY_COUNTS = synchronizedMap(new WeakHashMap<>());

    /**
     * Counts the total number of entities of specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the class whose entities are counted.
     * @return the number of all entities of specified class.
     */
    static @Positive long entityCount(@NotNull final EntityManager entityManager, @NotNull final Class<?> entityClass) {
        if (entityManager == null) {
            throw new NullPointerException("entityManager is null");
        }
        if (entityClass == null) {
            throw new NullPointerException("entityClass is null");
        }
        synchronized (ENTITY_COUNTS) {
            return ENTITY_COUNTS.computeIfAbsent(entityClass, k -> {
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

    static @Positive long entityCount(@NotNull final Class<?> entityClass) {
        return applyEntityManager(v -> entityCount(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------
    static @Positive int entityCountAsInt(@NotNull final EntityManager entityManager,
                                          @NotNull final Class<?> entityClass) {
        return toIntExact(entityCount(entityManager, entityClass));
    }

    static @Positive int entityCountAsInt(@NotNull final Class<?> entityClass) {
        return applyEntityManager(v -> entityCountAsInt(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Choose a random entity of specified class from the database;
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
        typedQuery.setFirstResult(current().nextInt(entityCountAsInt(entityManager, entityClass)));
        typedQuery.setMaxResults(1);
        return typedQuery.getSingleResult();
    }

    static <T> T randomEntity(final Class<T> entityClass) {
        return applyEntityManager(v -> randomEntity(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------
    static int firstResult(final EntityManager entityManager, final Class<?> entityClass) {
        return current().nextInt(entityCountAsInt(entityManager, entityClass));
    }

    static int firstResult(final Class<?> entityClass) {
        return applyEntityManager(v -> firstResult(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------
    static int maxResults(final EntityManager entityManager, final Class<?> entityClass) {
        return firstResult(entityManager, entityClass) + 1;
    }

    static int maxResults(final Class<?> entityClass) {
        return applyEntityManager(v -> maxResults(v, entityClass));
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
    EntityServiceIT(final Class<T> serviceClass, final Class<U> entityClass) {
        super();
        this.serviceClass = requireNonNull(serviceClass, "serviceClass is null");
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests miscellaneous things.
     */
    @Test
    void miscellaneous() {
        MiscellaneousTests.assertAnnotatedWithSlf4j(serviceClass);
        MiscellaneousTests.assertAnnotatedWithSlf4j(getClass());
    }

    // --------------------------------------------------------------------------------------------------------- count()

    /**
     * Tests {@link EntityService#count()} method.
     */
    @Test
    void testCount() {
        final long expected = entityCount(entityManager(), entityClass);
        final long actual = serviceInstance().count();
        assertThat(actual)
                .isNotNegative()
                .isEqualTo(expected);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an instance of {@link #serviceClass}.
     *
     * @return an instance of {@link #serviceClass}.
     */
    final @NotNull T serviceInstance() {
        return serviceInstance.select(serviceClass).get();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager for accessing persistence context.
     *
     * @return an entity manager.
     */
    final @NotNull EntityManager entityManager() {
        if (entityManagerProxy == null) {
            entityManagerProxy = uncloseable(entityManager);
        }
        return entityManagerProxy;
    }

    /**
     * Asserts {@link #entityManager()} method returns a non-null instance and {@link EntityManager#close() close()}
     * method of the object throws an {@link UnsupportedOperationException}.
     */
    @Test
    void assertEntityManagerNotNullNorCloseable() {
        assertThat(entityManager()).isNotNull();
        assertThatThrownBy(() -> entityManager().close()).isInstanceOf(UnsupportedOperationException.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The service class to test.
     */
    final Class<T> serviceClass;

    /**
     * The entity class of {@link #serviceClass}.
     */
    final Class<U> entityClass;

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private transient Instance<EntityService> serviceInstance;

    @Inject
    private EntityManager entityManager;

    private EntityManager entityManagerProxy;
}
