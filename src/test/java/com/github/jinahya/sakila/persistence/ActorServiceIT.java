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
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertActor;
import static com.github.jinahya.sakila.persistence.FullNamed.comparingFirstName;
import static com.github.jinahya.sakila.persistence.FullNamed.comparingLastName;
import static java.util.Collections.unmodifiableNavigableMap;
import static java.util.Collections.unmodifiableNavigableSet;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * A class for testing {@link ActorService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class ActorServiceIT extends BaseEntityServiceIT<ActorService, Actor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The total number of actors in database. The value is {@value}.
     */
    static final int ACTOR_COUNT_AS_INT = entityCountAsInt(Actor.class);

    /**
     * Returns a random actor picked from the database.
     *
     * @return a random actor picked from the database.
     */
    static Actor randomActor() {
        return randomEntity(Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable set of {@link FullNamedEntity#COLUMN_NAME_FIRST_NAME first_name} column values of
     * {@link Actor#TABLE_NAME actor} table.
     */
    static final NavigableSet<String> FIRST_NAMES;

    static {
        try {
            FIRST_NAMES = unmodifiableNavigableSet(applyResourceScanner("actor_set_first_name.txt", s -> {
                final NavigableSet<String> set = new TreeSet<>();
                while (s.hasNext()) {
                    final String firstName = s.nextLine();
                    final boolean added = set.add(firstName);
                    assert added : "duplicate first name: " + firstName;
                }
                return set;
            }));
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable set of {@link FullNamedEntity#COMPARING_LAST_NAME last_name} column values of {@link
     * Actor#TABLE_NAME actor} table.
     */
    static final NavigableSet<String> LAST_NAMES;

    static {
        try {
            LAST_NAMES = unmodifiableNavigableSet(applyResourceScanner("actor_set_last_name.txt", s -> {
                final NavigableSet<String> set = new TreeSet<>();
                while (s.hasNext()) {
                    final String lastName = s.nextLine();
                    final boolean added = set.add(lastName);
                    assert added : "duplicate last name: " + lastName;
                }
                return set;
            }));
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable map of first names and counts.
     */
    static final NavigableMap<String, Integer> FIRST_NAMES_AND_COUNTS;

    static {
        try {
            FIRST_NAMES_AND_COUNTS = unmodifiableNavigableMap(applyResourceScanner(
                    "actor_map_first_name_count.txt",
                    s -> {
                        final NavigableMap<String, Integer> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final String key = s.next();
                            final int value = s.nextInt();
                            final Integer previousValue = map.put(key, value);
                            assert previousValue == null : "duplicate first name: " + key;
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable map of last names and counts.
     */
    static final NavigableMap<String, Integer> LAST_NAMES_AND_COUNTS;

    static {
        try {
            LAST_NAMES_AND_COUNTS = unmodifiableNavigableMap(applyResourceScanner(
                    "actor_map_last_name_count.txt",
                    s -> {
                        final NavigableMap<String, Integer> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final String key = s.next();
                            final int value = s.nextInt();
                            final Integer previousValue = map.put(key, value);
                            assert previousValue == null : "duplicate last name: " + key;
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testListSortedByIdInAscendingOrder(String, String, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListSortedByIdInAscendingOrder() {
        return IntStream.range(1, current().nextInt(8, 17))
                .mapToObj(i -> randomActor())
                .map(v -> arguments(v.getFirstName(), v.getLastName(), firstResult(Actor.class),
                                    maxResults(Actor.class)))
                ;
    }

    /**
     * Provides arguments for {@link #testListSortedByFirstName(String, boolean, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> provideArgumentsForTestListSortedByFirstName() {
        return IntStream.range(1, current().nextInt(8, 17)).mapToObj(i -> {
            final String lastName = current().nextBoolean() ? null : randomEntity(Actor.class).getLastName();
            final boolean ascendingOrder = current().nextBoolean();
            final Integer firstResult = current().nextBoolean() ? null : firstResult(Actor.class);
            final Integer maxResult = current().nextBoolean() ? null : maxResults(Actor.class);
            return Arguments.of(lastName, ascendingOrder, firstResult, maxResult);
        });
    }

    /**
     * Provides arguments for {@link #testListSortedByLastName(String, boolean, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> provideArgumentsForTestListSortedByLastName() {
        return IntStream.range(1, current().nextInt(8, 17)).mapToObj(i -> {
            final String lastName = current().nextBoolean() ? null : randomEntity(Actor.class).getLastName();
            final boolean ascendingOrder = current().nextBoolean();
            final Integer firstResult = current().nextBoolean() ? null : firstResult(Actor.class);
            final Integer maxResult = current().nextBoolean() ? null : maxResults(Actor.class);
            return Arguments.of(lastName, ascendingOrder, firstResult, maxResult);
        });
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorServiceIT() {
        super(ActorService.class, Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Asserts {@link ActorService#listSortedByIdInAscendingOrder(String, String, Integer, Integer)} returns an empty
     * for an unknown.
     */
    @Test
    void assertListSortedByIdInAscendingOrderReturnsEmptyForUnknown() {
        assertThat(serviceInstance().listSortedByIdInAscendingOrder("John", "Wayne", null, null)).isEmpty();
    }

    /**
     * Tests {@link ActorService#listSortedByIdInAscendingOrder(String, String, Integer, Integer) list(firstName,
     * lastName, firstResult, maxResults)} method with given values.
     *
     * @param firstName   a value for {@code firstName} parameter.
     * @param lastName    a value for {@code lastName} parameter.
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    @MethodSource({"argumentsForTestListSortedByIdInAscendingOrder"})
    @ParameterizedTest
    void testListSortedByIdInAscendingOrder(@Nullable final String firstName, @Nullable final String lastName,
                                            @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        final List<Actor> list = serviceInstance().listSortedByIdInAscendingOrder(
                firstName, lastName, firstResult, maxResults);
        assertThat(list)
                .isSortedAccordingTo(BaseEntity.COMPARING_ID)
                .hasSizeLessThanOrEqualTo(2) // SUSAN DAVIS
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link ActorService#listSortedByFirstName(String, boolean, Integer, Integer)
     * listSortedByFirstName(lastName, ascendingOrder, firstResult, lastResult)} method with given arguments.
     *
     * @param lastName       a value for {@code lastName} parameter.
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    @MethodSource({"provideArgumentsForTestListSortedByFirstName"})
    @ParameterizedTest
    void testListSortedByFirstName(@Nullable final String lastName, final boolean ascendingOrder,
                                   @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        final List<Actor> list = serviceInstance()
                .listSortedByFirstName(lastName, ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isSortedAccordingTo(comparingFirstName(ascendingOrder))
                .allSatisfy(actor -> ofNullable(lastName).ifPresent(v -> assertActor(actor).hasLastName(v)))
                .size()
                .satisfies(s -> ofNullable(maxResults).ifPresent(v -> assertThat(s).isLessThanOrEqualTo(v)))
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link ActorService#listSortedByLastName(String, boolean, Integer, Integer)} method with given arguments.
     *
     * @param firstName      a value for {@code firstName} parameter.
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    @MethodSource({"provideArgumentsForTestListSortedByLastName"})
    @ParameterizedTest
    void testListSortedByLastName(@Nullable final String firstName, final boolean ascendingOrder,
                                  @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        final List<Actor> list = serviceInstance()
                .listSortedByLastName(firstName, ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isSortedAccordingTo(comparingLastName(ascendingOrder))
                .allSatisfy(actor -> ofNullable(firstName).ifPresent(v -> assertActor(actor).hasFirstName(v)))
                .size()
                .satisfies(s -> ofNullable(maxResults).ifPresent(v -> assertThat(s).isLessThanOrEqualTo(v)))
        ;
    }
}
