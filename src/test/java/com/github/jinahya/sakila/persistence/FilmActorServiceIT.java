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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Collections.unmodifiableMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for testing {@link FilmActorService}.
 */
@Slf4j
class FilmActorServiceIT extends EntityServiceIT<FilmActorService, FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of actor id and film count.
     */
    static final Map<Integer, Integer> ACTOR_ID_FILM_COUNT;

    static {
        final Map<Integer, Integer> map = new HashMap<>();
        try {
            try (InputStream stream = FilmActorServiceIT.class.getResourceAsStream("film_actor_actorId_filmCount.txt");
                 Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNext()) {
                    map.put(scanner.nextInt(), scanner.nextInt());
                }
            }
            ACTOR_ID_FILM_COUNT = unmodifiableMap(map);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of film id and actor count.
     */
    static final Map<Integer, Integer> FILM_ID_ACTOR_COUNT;

    static {
        final Map<Integer, Integer> map = new HashMap<>();
        try {
            try (InputStream stream = FilmActorServiceIT.class.getResourceAsStream("film_actor_filmId_actorCount.txt");
                 Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNext()) {
                    map.put(scanner.nextInt(), scanner.nextInt());
                }
            }
            FILM_ID_ACTOR_COUNT = unmodifiableMap(map);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
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
    @Tag(TAG_JPQL)
    @Test
    void testCountFilms() {
        final Actor actor = random(entityManager, Actor.class);
        final long expected = ACTOR_ID_FILM_COUNT.get(actor.getId());
        final long actual = serviceInstance().countFilms(actor);
        assertEquals(expected, actual);
    }

//    @Disabled
//    @Tag(TAG_JPQL)
//    @MethodSource({"provideActorAndFilmCount"})
//    @ParameterizedTest
//    @Test
//    void testListFilms(final Actor actor, final long expected) {
//        {
//            final List<Film> list = serviceInstance().listFilms(actor, null, null);
//            assertEquals(expected, list.size());
//        }
//        {
//            int actual = 0;
//            final int maxResults = current().nextInt(1, 17);
//            for (int firstResult = 0; true; firstResult += maxResults) {
//                final List<Film> page = FilmActorService.listFilms(actor, firstResult, maxResults);
//                assertTrue(page.size() <= maxResults);
//                actual += page.size();
//                if (page.size() < maxResults) {
//                    break;
//                }
//            }
//            assertEquals(expected, actual);
//        }
//    }
//
//    /**
//     * Tests {@link FilmActorService#streamFilms(EntityManager, Collection, Integer, Integer)} method.
//     */
//    @Disabled
//    @Tag(TAG_JPQL)
//    @Test
//    void testCountFilms() {
//        final List<Actor> actors = ActorIT.stream(getEntityManager()).collect(toList());
//        final long expected = actors.stream()
//                .flatMap(a -> FilmActorService.listFilms(getEntityManager(), a, null, null).stream())
//                .filter(distinctById())
//                .count();
//        final long actual = FilmActorService.countFilms(getEntityManager(), actors);
//        assertEquals(expected, actual);
//    }
//
//    @Disabled
//    @Tag(TAG_JPQL)
//    @MethodSource({"provideActors"})
//    @ParameterizedTest
//    void testStreamFilms() {
//        final List<Actor> actors = ActorIT.stream(getEntityManager()).collect(toList());
//        final List<Film> expected = actors.stream()
//                .flatMap(a -> FilmActorService.listFilms(getEntityManager(), a, null, null).stream())
//                .filter(distinctById())
//                .sorted(comparing(BaseEntity::getId))
//                .collect(toList());
//        final List<Film> actual = FilmActorService.streamFilms(getEntityManager(), actors, null, null)
//                .sorted(comparing(Film::getId))
//                .collect(toList());
//        assertIterableEquals(expected, actual);
//    }
}
