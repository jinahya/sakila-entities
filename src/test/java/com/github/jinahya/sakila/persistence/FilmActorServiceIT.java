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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.github.jinahya.sakila.persistence.BaseEntity.COMPARING_ID;
import static com.github.jinahya.sakila.persistence.BaseEntityServiceIT.DISTINCT_BY_ID;
import static com.github.jinahya.sakila.persistence.BaseEntityServiceIT.randomEntities;
import static com.github.jinahya.sakila.persistence.util.AssertionsUtil.assertSorted;
import static java.util.Collections.unmodifiableMap;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class for testing {@link FilmActorService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
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
            try (InputStream stream
                         = FilmActorServiceIT.class.getResourceAsStream("film_actor_map_actor_id_film_count.txt");
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
            try (InputStream stream
                         = FilmActorServiceIT.class.getResourceAsStream("film_actor_film_id_actor_count.txt");
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

    /**
     * Tests {@link FilmActorService#countFilms(Actor)} method.
     */
    // TODO: 2019-07-12 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(8)
    void testCountFilmsOfSingleActor() {
        final Actor actor = randomEntity(entityManager(), Actor.class);
        final long expected = ACTOR_ID_FILM_COUNT.get(actor.getId());
        final long actual = serviceInstance().countFilms(actor);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#listFilms(Actor, Integer, Integer)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(8)
    void testListFilmsOfSingleActor() {
        // select a random actor
        final Actor actor = randomEntity(entityManager(), Actor.class);
        {
            // test for all films of the actor.
            final List<Film> films = serviceInstance().listFilms(actor, null, null);
            assertEquals(ACTOR_ID_FILM_COUNT.get(actor.getId()), films.size());
            assertSorted(films, COMPARING_ID);
        }
        {
            // test for paged films of the actor.
            int total = 0;
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Film> page = serviceInstance().listFilms(actor, firstResult, maxResults);
                assertTrue(page.size() <= maxResults);
                assertSorted(page, COMPARING_ID);
                total += page.size();
                if (page.size() < maxResults) {
                    break;
                }
            }
            assertEquals(ACTOR_ID_FILM_COUNT.get(actor.getId()), total);
        }
    }

    /**
     * Tests {@link FilmActorService#countFilms(Collection)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(4)
    void testCountFilmsWithMultipleActors() {
        // prepare a list of actors; random, distinct by id, ordered by id in ascending order.
        final List<Actor> actors = randomEntities(
                entityManager(), Actor.class, s -> s.sorted(COMPARING_ID).collect(toList()));
        // map films for each actor, distinct by id, sorted by id in ascending order, count them.
        final long expected = actors.stream()
                .flatMap(a -> serviceInstance().listFilms(a, null, null).stream())
                .filter(DISTINCT_BY_ID)
                .count();
        // compare the results.
        final long actual = serviceInstance().countFilms(actors);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#streamFilms(Collection, Integer, Integer)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(4)
    void testStreamFilms() {
        // prepare a list of actors; random, distinct by id, ordered by id in ascending order.
        final List<Actor> actors = randomEntities(
                entityManager(), Actor.class, s -> s.sorted(COMPARING_ID).collect(toList()));
        // map films for each actor, distinct by id, sorted by id in ascending order, collect them.
        final List<Film> films = actors.stream()
                .flatMap(a -> serviceInstance().listFilms(a, null, null).stream())
                .filter(DISTINCT_BY_ID)
                .collect(toList());
        {
            // test for the whole films.
            final List<Film> actual = serviceInstance().streamFilms(actors, null, null).collect(toList());
            assertIterableEquals(films, actual);
        }
        {
            // test for paged films.
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Film> actual
                        = serviceInstance().streamFilms(actors, firstResult, maxResults).collect(toList());
                assertTrue(actual.size() <= maxResults);
                assertSorted(actual, COMPARING_ID);
                if (actual.size() < maxResults) {
                    break;
                }
                final List<Film> expected = films.subList(firstResult, firstResult + actual.size());
                assertIterableEquals(expected, actual);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmActorService#countActors(Film)}} method.
     */
    // TODO: 2019-07-12 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(8)
    void testCountActorsWithSingleActor() {
        final Film film = randomEntity(entityManager(), Film.class);
        final long expected = FILM_ID_ACTOR_COUNT.get(film.getId());
        final long actual = serviceInstance().countActors(film);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#listActors(Film, Integer, Integer)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(8)
    void testListActorsWithSingleActor() {
        final Film film = randomEntity(entityManager(), Film.class);
        {
            final List<Actor> actors = serviceInstance().listActors(film, null, null);
            assertEquals(FILM_ID_ACTOR_COUNT.get(film.getId()), actors.size());
            assertSorted(actors, COMPARING_ID);
        }
        {
            int total = 0;
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Actor> page = serviceInstance().listActors(film, firstResult, maxResults);
                assertTrue(page.size() <= maxResults);
                assertSorted(page, COMPARING_ID);
                total += page.size();
                if (page.size() < maxResults) {
                    break;
                }
            }
            assertEquals(FILM_ID_ACTOR_COUNT.get(film.getId()), total);
        }
    }

    /**
     * Tests {@link FilmActorService#countActors(Collection)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(4)
    void testCountActorsWithMultipleActors() {
        final List<Film> films = randomEntities(
                entityManager(), Film.class, s -> s.sorted(COMPARING_ID).collect(toList()));
        final long expected = films.stream()
                .flatMap(f -> serviceInstance().listActors(f, null, null).stream())
                .filter(DISTINCT_BY_ID)
                .count();
        final long actual = serviceInstance().countActors(films);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#streamActors(Collection, Integer, Integer)} method.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @RepeatedTest(4)
    void testStreamActors() {
        final List<Film> films = randomEntities(
                entityManager(), Film.class, s -> s.sorted(COMPARING_ID).collect(toList()));
        final List<Actor> actors = films.stream()
                .flatMap(f -> serviceInstance().listActors(f, null, null).stream())
                .filter(DISTINCT_BY_ID)
                .collect(toList());
        {
            final List<Actor> actual = serviceInstance().streamActors(films, null, null).collect(toList());
            assertIterableEquals(actors, actual);
        }
        {
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Actor> actual
                        = serviceInstance().streamActors(films, firstResult, maxResults).collect(toList());
                assertTrue(actual.size() <= maxResults);
                assertSorted(actual, COMPARING_ID);
                if (actual.size() < maxResults) {
                    break;
                }
                final List<Actor> expected = actors.subList(firstResult, firstResult + actual.size());
                assertIterableEquals(expected, actual);
            }
        }
    }
}
