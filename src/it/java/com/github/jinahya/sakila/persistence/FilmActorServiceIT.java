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
import java.util.Collection;
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
 * A class for testing {@link FilmActorService}.
 */
@Slf4j
class FilmActorServiceIT extends EntityServiceIT<FilmActorService, FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Arguments> provideActorAndFilmCount() {
        return Stream.of(
                Arguments.of(BaseEntityIT.of(Actor::new, 148), 14L),
                Arguments.of(BaseEntityIT.of(Actor::new, 199), 15L),
                Arguments.of(BaseEntityIT.of(Actor::new, 107), 42L),
                Arguments.of(BaseEntityIT.of(Actor::new, 102), 41L)
        );
    }

    static Stream<Arguments> provideActors() {
        return Stream.of(
                Arguments.of(asList(BaseEntityIT.of(Actor::new, 148), BaseEntityIT.of(Actor::new, 199))),
                Arguments.of(asList(BaseEntityIT.of(Actor::new, 107), BaseEntityIT.of(Actor::new, 102)))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorServiceIT() {
        super(FilmActorService.class, FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-12 un-disable, assert fails, implement, and assert passes
    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorAndFilmCount"})
    @ParameterizedTest
    void testCountFilms(final Actor actor, final long expected) {
        final long actual = serviceInstance().countFilms(actor);
        assertEquals(expected, actual);
    }

    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActorAndFilmCount"})
    @ParameterizedTest
    void testListFilms(final Actor actor, final long expected) {
        {
            final List<Film> list = serviceInstance().listFilms(actor, null, null);
            assertEquals(expected, list.size());
        }
        {
            int actual = 0;
            final int maxResults = current().nextInt(1, 17);
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Film> page = FilmActorService.listFilms(getEntityManager(), actor, firstResult, maxResults);
                assertTrue(page.size() <= maxResults);
                actual += page.size();
                if (page.size() < maxResults) {
                    break;
                }
            }
            assertEquals(expected, actual);
        }
    }

    /**
     * Tests {@link FilmActorService#streamFilms(EntityManager, Collection, Integer, Integer)} method.
     */
    @Disabled
    @Tag(TAG_JPQL)
    @Test
    void testCountFilms() {
        final List<Actor> actors = ActorIT.stream(getEntityManager()).collect(toList());
        final long expected = actors.stream()
                .flatMap(a -> FilmActorService.listFilms(getEntityManager(), a, null, null).stream())
                .filter(distinctById())
                .count();
        final long actual = FilmActorService.countFilms(getEntityManager(), actors);
        assertEquals(expected, actual);
    }

    @Disabled
    @Tag(TAG_JPQL)
    @MethodSource({"provideActors"})
    @ParameterizedTest
    void testStreamFilms() {
        final List<Actor> actors = ActorIT.stream(getEntityManager()).collect(toList());
        final List<Film> expected = actors.stream()
                .flatMap(a -> FilmActorService.listFilms(getEntityManager(), a, null, null).stream())
                .filter(distinctById())
                .sorted(comparing(BaseEntity::getId))
                .collect(toList());
        final List<Film> actual = FilmActorService.streamFilms(getEntityManager(), actors, null, null)
                .sorted(comparing(Film::getId))
                .collect(toList());
        assertIterableEquals(expected, actual);
    }
}
