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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertBaseEntity;
import static com.github.jinahya.sakila.persistence.BaseEntity.comparingId;
import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * An abstract class for testing subclasses of {@link BaseEntityService}.
 *
 * @param <T> service type parameter
 * @param <U> entity type parameter
 */
@Slf4j
abstract class BaseEntityServiceIT<T extends BaseEntityService<U>, U extends BaseEntity> extends EntityServiceIT<T, U> {

    // -----------------------------------------------------------------------------------------------------------------
    // https://www.baeldung.com/java-streams-distinct-by
    static final Predicate<BaseEntity> DISTINCT_BY_ID = e -> {
        final Map<Integer, Boolean> seen = new ConcurrentHashMap<>();
        return seen.putIfAbsent(e.getId(), Boolean.TRUE) == null;
    };

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends BaseEntity> Optional<T> findById(final Class<T> entityClass, final int entityId) {
        return applyEntityManager(v -> ofNullable(v.find(entityClass, entityId)));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Selects random entities of specified class, as a stream, which each is distinct by {@link
     * BaseEntity#ATTRIBUTE_NAME_ID} attribute, and applies it to specified function.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the class of entities to select.
     * @param function      the function to be applied with a stream of selected entities.
     * @param <T>           entity type parameter
     * @param <R>           result type parameter
     * @return the result of the {@code function}.
     * @see #randomEntities(EntityManager, Class)
     */
    static <T extends BaseEntity, R> R randomEntities(
            final EntityManager entityManager, final Class<? extends T> entityClass,
            final Function<? super Stream<? extends T>, ? extends R> function) {
        return function.apply(rangeClosed(0L, current().nextLong(entityCount(entityManager, entityClass) >> 1))
                                      .mapToObj(i -> randomEntity(entityManager, entityClass))
                                      .filter(DISTINCT_BY_ID));
    }

    /**
     * Selects a list of random entities, which each is distinct by {@link BaseEntity#ATTRIBUTE_NAME_ID} attribute, of
     * specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the class of entities to select.
     * @param <T>           entity type parameter
     * @return a list of selected entities.
     * @see #randomEntities(EntityManager, Class, Function)
     */
    static <T extends BaseEntity> List<T> randomEntities(final EntityManager entityManager,
                                                         final Class<? extends T> entityClass) {
        return randomEntities(entityManager, entityClass, s -> s.collect(toList()));
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends BaseEntity> T unknownReference(@NotNull final Class<? extends T> entityClass) {
        try {
            final Constructor<? extends T> constructor
                    = requireNonNull(entityClass, "entityClass is null").getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            final T instance = constructor.newInstance();
            instance.setId(Integer.MIN_VALUE);
            return instance;
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified service class and entity class.
     *
     * @param serviceClass the service class to test.
     * @param entityClass  the entity class of the {@code serviceClass}.
     */
    BaseEntityServiceIT(final Class<T> serviceClass, final Class<U> entityClass) {
        super(serviceClass, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Asserts {@link BaseEntityService#findById(int)} method returns an empty optional for unknown id.
     */
    @Test
    void assertFindByIdReturnsEmptyForUnknownId() {
        final Optional<U> found = serviceInstance().findById(Integer.MIN_VALUE);
        assertThat(found).isEmpty();
    }

    /**
     * Tests {@link BaseEntityService#findById(int)}.
     */
    @RepeatedTest(16)
    void testFindById() {
        final U entity = randomEntity(entityManager(), entityClass);
        final Optional<U> found = serviceInstance().findById(entity.getId());
        assertThat(found)
                .isPresent()
                .hasValueSatisfying(v -> assertBaseEntity(v).hasSameIdAs(entity))
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link BaseEntityService#listSortedByIdIn(boolean, Integer, Integer)}.
     *
     * @param testReporter a test reporter.
     */
    @RepeatedTest(16)
    void testListSortedById(final TestReporter testReporter) {
        final boolean ascendingOrder = current().nextBoolean();
        final Integer firstResult = current().nextBoolean() ? null : firstResult(entityManager(), entityClass);
        final Integer maxResults = current().nextBoolean() ? null : maxResults(entityManager(), entityClass);
        testReporter.publishEntry("ascendingOrder", Boolean.toString(ascendingOrder));
        testReporter.publishEntry("firstResult", Objects.toString(firstResult));
        testReporter.publishEntry("maxResults", Objects.toString(maxResults));
        final List<U> list = serviceInstance().listSortedByIdIn(ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isSortedAccordingTo(comparingId(ascendingOrder))
                .size().satisfies(s -> ofNullable(maxResults).ifPresent(m -> assertThat(s).isLessThanOrEqualTo(m)))
        ;
    }
}
