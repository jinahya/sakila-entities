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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static java.util.Collections.unmodifiableMap;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        try {
            ACTOR_ID_FILM_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_actor_map_actor_id_film_count.txt",
                    s -> {
                        final Map<Integer, Integer> m = new HashMap<>();
                        while (s.hasNext()) {
                            final int actorId = s.nextInt();
                            final int filmCount = s.nextInt();
                            final Integer previous = m.put(actorId, filmCount);
                            assert previous == null : "duplicate actorId: " + actorId;
                        }
                        return m;
                    }));
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static int filmCount(final Actor actor) {
        return ACTOR_ID_FILM_COUNT.get(requireNonNull(actor, "actor is null").getId());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of film id and actor count.
     */
    static final Map<Integer, Integer> FILM_ID_ACTOR_COUNT;

    static {
        try {
            FILM_ID_ACTOR_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_actor_film_id_actor_count.txt",
                    s -> {
                        final Map<Integer, Integer> m = new HashMap<>();
                        while (s.hasNext()) {
                            final int filmId = s.nextInt();
                            final int actorCount = s.nextInt();
                            final Integer previous = m.put(filmId, actorCount);
                            assert previous == null : "duplicate filmId: " + filmId;
                        }
                        return m;
                    }
            ));
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static int actorCount(final Film film) {
        return FILM_ID_ACTOR_COUNT.get(requireNonNull(film, "film is null").getId());
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<Arguments> actorArgumentsStream() {
        return applyEntityManager(
                v -> IntStream.range(1, 16).mapToObj(i -> randomEntity(v, Actor.class)).map(Arguments::of));
    }

    private static Stream<Arguments> filmArgumentsStream() {
        return applyEntityManager(
                v -> IntStream.range(1, 16).mapToObj(i -> randomEntity(v, Film.class)).map(Arguments::of));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorServiceIT() {
        super(FilmActorService.class, FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-08-04 add test case for find(Film, Actor)

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmActorService#countFilms(Actor)} method.
     *
     * @param actor an actor to test with.
     */
    // TODO: 2019-07-12 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"actorArgumentsStream"})
    @ParameterizedTest
    void testCountFilms(final Actor actor) {
        final long expected = ACTOR_ID_FILM_COUNT.get(actor.getId());
        final long actual = serviceInstance().countFilms(actor);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#listFilms(Actor, Integer, Integer)} method.
     *
     * @param actor an actor to test with.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"actorArgumentsStream"})
    @ParameterizedTest
    void testListFilms(final Actor actor) {
        {
            final List<Film> films = serviceInstance().listFilms(actor, null, null);
            assertThat(films)
                    .isSortedAccordingTo(comparing(Film::getReleaseYear).reversed())
                    .hasSize(filmCount(actor))
            ;
        }
        {
            int filmCount = 0;
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Film> films = serviceInstance().listFilms(actor, firstResult, maxResults);
                assertThat(films)
                        .isSortedAccordingTo(comparing(Film::getReleaseYear).reversed())
                        .size().satisfies(s -> assertThat(s).isLessThanOrEqualTo(maxResults));
                filmCount += films.size();
                if (films.size() < maxResults) {
                    break;
                }
            }
            assertEquals(filmCount(actor), filmCount);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmActorService#countActors(Film)}} method.
     *
     * @param film a film to test with.
     */
    // TODO: 2019-07-12 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"filmArgumentsStream"})
    @ParameterizedTest
    void testCountActors(final Film film) {
        final long expected = FILM_ID_ACTOR_COUNT.get(film.getId());
        final long actual = serviceInstance().countActors(film);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FilmActorService#listActors(Film, Integer, Integer)} method.
     *
     * @param film a film to test with.
     */
    // TODO: 2019-07-13 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"filmArgumentsStream"})
    @ParameterizedTest
    void testListActors(final Film film) {
        {
            final List<Actor> actors = serviceInstance().listActors(film, null, null);
            assertThat(actors)
                    .isSortedAccordingTo(FullNamed.COMPARING_FIRST_NAME)
                    .hasSize(actorCount(film))
            ;
        }
        {
            int actorCount = 0;
            final int maxResults = current().nextInt(1, 17); // [1..16]
            for (int firstResult = 0; true; firstResult += maxResults) {
                final List<Actor> actors = serviceInstance().listActors(film, firstResult, maxResults);
                assertThat(actors)
                        .isSortedAccordingTo(FullNamed.COMPARING_FIRST_NAME)
                        .size().satisfies(s -> assertThat(s).isLessThanOrEqualTo(maxResults));
                actorCount += actors.size();
                if (actors.size() < maxResults) {
                    break;
                }
            }
            assertEquals(actorCount(film), actorCount);
        }
    }
}
