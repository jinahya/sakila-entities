package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for testing {@link FilmActor}.
 */
@Slf4j
class FilmActorIT extends EntityIT<FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Arguments> provideActorAndFilmCounts() {
        return Stream.of(
                Arguments.of(BaseEntity.of(Actor::new, 148), 14L),
                Arguments.of(BaseEntity.of(Actor::new, 199), 15L),
                Arguments.of(BaseEntity.of(Actor::new, 107), 42L),
                Arguments.of(BaseEntity.of(Actor::new, 102), 41L)
        );
    }

    static Stream<Arguments> provideActorListAndFilmCounts() {
        return Stream.of(
                Arguments.of(asList(BaseEntity.of(Actor::new, 148), BaseEntity.of(Actor::new, 199)), 29L),
                Arguments.of(asList(BaseEntity.of(Actor::new, 107), BaseEntity.of(Actor::new, 102)), 83L)
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
     * @param expected an expected value of {@link FilmActor#countFilms(EntityManager, Actor)}.
     */
    @MethodSource({"provideActorAndFilmCounts"})
    @ParameterizedTest
    void testCountFilmsWithSingleActor(final Actor actor, final long expected) {
        final long actual = FilmActor.countFilms(getEntityManager(), actor);
        assertEquals(expected, actual);
        log.debug("expected: {}, actual: {}", expected, actual);
    }

    @MethodSource({"provideActorAndFilmCounts"})
    @ParameterizedTest
    void testListFilmsWithSingleActor(final Actor actor, final long expected) {
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

//    @MethodSource({"provideActorListAndFilmCounts"})
//    @ParameterizedTest
//    void testCountFilmsWithMultipleActors(final List<? extends Actor> actors, final long expected) {
//        final long actual = FilmActor.countFilms(getEntityManager(), actors);
//        log.debug("expected: {}, actual: {}", expected, actual);
//        assertEquals(expected, actual);
//    }
//
//    @MethodSource({"provideActorListAndFilmCounts"})
//    @ParameterizedTest
//    void testStreamFilmsWithMultipleActors(final List<? extends Actor> actors, final long expected) {
//        final Stream<Film> stream = FilmActor.streamFilms(getEntityManager(), actors, UnaryOperator.identity());
//        log.debug("films: {}", stream);
//        final List<Film> list = stream.collect(toList());
//        log.debug("list: {}", list);
//        log.debug("expected: {}, actual: {}", expected, list.size());
//        assertEquals(expected, list.size());
//    }
}
