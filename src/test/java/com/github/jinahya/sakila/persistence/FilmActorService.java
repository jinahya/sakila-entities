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

import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * A service class for {@link FilmActor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmActorService extends EntityService<FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorService() {
        super(FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts films mapped to specified actor.
     *
     * @param actor the actor whose films are counted.
     * @return the number of films mapped to specified actor.
     */
    public @PositiveOrZero long countFilms(@NotNull final Actor actor) {
        return entityManager()
                .createQuery("SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.actor = :actor", Long.class)
                .setParameter("actor", actor)
                .getSingleResult();
    }

    /**
     * Lists films of specified actor ordered by {@link Film#ATTRIBUTE_NAME_RELEASE_YEAR} attribute in descending
     * order.
     *
     * @param actor       the actor whose films are listed.
     * @param firstResult position of the first result, numbered from 0.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of films of {@code actor}.
     */
    public @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                         @Positive final Integer maxResults) {
        final TypedQuery<Film> typedQuery = entityManager().createQuery(
                "SELECT fa.film FROM FilmActor AS fa WHERE fa.actor = :actor ORDER BY fa.film.id ASC",
                Film.class);
        typedQuery.setParameter("actor", actor);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    /**
     * Counts distinct films mapped to any of specified actors.
     *
     * @param actors the actors whose films are counted.
     * @return the number of distinct films mapped to any of specified actors.
     */
    public @PositiveOrZero long countFilms(@NotNull final Collection<@NotNull ? extends Actor> actors) {
        return entityManager()
                .createQuery("SELECT COUNT(DISTINCT fa) FROM FilmActor AS fa WHERE fa.actor IN :actors", Long.class)
                .setParameter("actors", actors).setParameter("actors", actors)
                .getSingleResult();
    }

    /**
     * Returns a stream of distinct films mapped to any of specified actors ordered by {@link
     * BaseEntity#ATTRIBUTE_NAME_ID} in ascending order.
     *
     * @param actors      the actors to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}
     * @return a stream of distinct films mapped to any of specified actors.
     */
    public @NotNull Stream<Film> streamFilms(@NotNull final Collection<@NotNull ? extends Actor> actors,
                                             @PositiveOrZero final Integer firstResult,
                                             @Positive final Integer maxResults) {
        final TypedQuery<Film> typedQuery = entityManager()
                .createQuery("SELECT DISTINCT fa.film"
                             + " FROM FilmActor AS fa"
                             + " WHERE fa.actor IN :actors"
                             + " ORDER BY fa.film.id",
                             Film.class)
                .setParameter("actors", actors).setParameter("actors", actors);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count actors mapped to specified film.
     *
     * @param film the film to match.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Film film) {
        return entityManager()
                .createQuery("SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.film = :film", Long.class)
                .setParameter("film", film)
                .getSingleResult();
    }

    /**
     * Lists actors mapped to specified film ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in ascending order.
     *
     * @param film        the film to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    public @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                           @Positive final Integer maxResults) {
        final TypedQuery<Actor> typedQuery = entityManager()
                .createQuery("SELECT fa.actor FROM FilmActor AS fa WHERE fa.film = :film ORDER BY fa.actor.id ASC",
                             Actor.class)
                .setParameter("film", film);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    /**
     * Count distinct actors mapped to any of specified films.
     *
     * @param films the films to match.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Collection<@NotNull ? extends Film> films) {
        return entityManager()
                .createQuery("SELECT COUNT(DISTINCT fa) FROM FilmActor AS fa WHERE fa.film IN :films", Long.class)
                .setParameter("films", films)
                .getSingleResult();
    }

    /**
     * Returns a stream of distinct actors mapped to any of specified films ordered by {@link
     * BaseEntity#ATTRIBUTE_NAME_ID} attribute in ascending order.
     *
     * @param films       the films to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a stream of distinct actors mapped to any of specified films.
     */
    public @NotNull Stream<Actor> streamActors(@NotNull final Collection<@NotNull ? extends Film> films,
                                               @PositiveOrZero final Integer firstResult,
                                               @Positive final Integer maxResults) {
        final TypedQuery<Actor> typedQuery = entityManager()
                .createQuery("SELECT DISTINCT fa.actor"
                             + " FROM FilmActor AS fa"
                             + " WHERE fa.film IN :films"
                             + " ORDER BY fa.actor.id ASC",
                             Actor.class)
                .setParameter("films", films);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }
}
