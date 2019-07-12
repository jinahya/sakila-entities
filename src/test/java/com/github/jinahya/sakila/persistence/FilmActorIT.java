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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.BaseEntityIT.distinctById;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class for testing {@link FilmActor}.
 */
@Slf4j
class FilmActorIT extends EntityIT<FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Arguments> provideActorAndFilmCounts() {
        return Stream.of(
                Arguments.of(BaseEntityIT.of(Actor::new, 148), 14L),
                Arguments.of(BaseEntityIT.of(Actor::new, 199), 15L),
                Arguments.of(BaseEntityIT.of(Actor::new, 107), 42L),
                Arguments.of(BaseEntityIT.of(Actor::new, 102), 41L)
        );
    }

    static Stream<Arguments> provideActorList() {
        return Stream.of(
                Arguments.of(asList(BaseEntityIT.of(Actor::new, 148), BaseEntityIT.of(Actor::new, 199))),
                Arguments.of(asList(BaseEntityIT.of(Actor::new, 107), BaseEntityIT.of(Actor::new, 102)))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorIT() {
        super(FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmActor#countFilms(EntityManager, Actor)}.
     *
     * @param actor    a value for {@code actor} parameter.
     * @param expected an expected value of {@link FilmActor#countFilms(EntityManager, Actor)} for specified {@code
     *                 actor}.
     */
    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorAndFilmCounts"})
    @ParameterizedTest
    void testCountFilms(final Actor actor, final long expected) {
        final long actual = FilmActor.countFilms(getEntityManager(), actor);
        assertEquals(expected, actual);
        log.debug("expected: {}, actual: {}", expected, actual);
    }

    /**
     * Tests {@link FilmActor#listFilms(EntityManager, Actor, Integer, Integer)}.
     *
     * @param actor    an actor to test with.
     * @param expected the expected value of {@link FilmActor#listFilms(EntityManager, Actor, Integer, Integer)} for
     *                 specified {@code actor}.
     */
    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorAndFilmCounts"})
    @ParameterizedTest
    void testListFilms(final Actor actor, final long expected) {
        {
            final List<Film> list = FilmActor.listFilms(getEntityManager(), actor, null, null);
            log.debug("list: {}", list);
            assertEquals(expected, list.size());
            log.debug("expected: {}, actual: {}", expected, list.size());
        }
        {
            int actual = 0;
            final int maxResults = current().nextInt(1, 17);
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Film> page = FilmActor.listFilms(getEntityManager(), actor, firstResult, maxResults);
                log.debug("page: ({}){}", page.size(), page);
                if (page.isEmpty()) {
                    break;
                }
                actual += page.size();
            }
            assertEquals(expected, actual);
            log.debug("expected: {}, actual: {}", expected, actual);
        }
    }

    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorList"})
    @ParameterizedTest
    @Test
    void testCountFilms(final List<? extends Actor> actors) {
        final long expected = actors.stream().mapToLong(v -> FilmActor.countFilms(getEntityManager(), v)).sum();
        final long actual = FilmActor.countFilms(getEntityManager(), actors);
        log.debug("expected: {}, actual: {}", expected, actual);
        assertTrue(actual <= expected);
    }

    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorList"})
    @ParameterizedTest
    void testStreamFilms(final List<? extends Actor> actors) {
        final List<Film> expected = actors.stream()
                .flatMap(a -> FilmActor.listFilms(getEntityManager(), a, null, null).stream())
                .sorted(comparing(BaseEntity::getId))
                .filter(distinctById())
                .collect(toList());
        log.debug("expected: {}", expected);
        final List<Film> actual = FilmActor.streamFilms(getEntityManager(), actors, null, null)
                .sorted(comparing(Film::getId))
                .collect(toList());
        log.debug("actual: {}", actors);
        assertIterableEquals(expected, actual);
    }
}
