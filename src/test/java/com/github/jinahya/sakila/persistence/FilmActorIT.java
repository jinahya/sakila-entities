package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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

    static Stream<Arguments> provideActorsAndFilmCounts() {
        return Stream.of(
                Arguments.of(asList(BaseEntity.of(Actor::new, 148),BaseEntity.of(Actor::new, 199)), 29L),
                Arguments.of(asList(BaseEntity.of(Actor::new, 107), BaseEntity.of(Actor::new, 102), 83L)
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
    void countFilms(final Actor actor, final long expected) {
        final long actual = FilmActor.countFilms(getEntityManager(), actor);
        assertEquals(expected, actual);
        log.debug("expected: {}, actual: {}", expected, actual);
    }

    /**
     * Tests {@link FilmActor#listFilms(EntityManager, Actor, UnaryOperator)}.
     *
     * @param actor    a value for {@code actor} parameter.
     * @param expected an expected value of {@link FilmActor#countFilms(EntityManager, Actor)}.
     */
    @MethodSource({"provideActorAndFilmCounts"})
    @ParameterizedTest
    void listFilms(final Actor actor, final long expected) {
        final List<Film> films = FilmActor.listFilms(getEntityManager(), actor, UnaryOperator.identity());
        log.debug("films: {}", films);
        log.debug("releaseYears: {}", films.stream().map(Film::getReleaseYear).collect(toList()));
        assertEquals(expected, films.size());
        log.debug("expected: {}, actual: {}", expected, films.size());
    }
}
