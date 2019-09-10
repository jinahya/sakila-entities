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

import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

/**
 * A service class for {@link FilmActor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
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
     * Finds the entity whose {@link FilmActor#ATTRIBUTE_NAME_FILM film} attribute and {@link
     * FilmActor#ATTRIBUTE_NAME_ACTOR actor} attribute match specified values.
     * <blockquote><pre>{@code
     * SELECT * FROM film_actor WHERE film_id = ? AND actor_id = ?
     * }</pre></blockquote>
     *
     * @param film  a value for {@link FilmActor#ATTRIBUTE_NAME_FILM film} attribute to match.
     * @param actor a value for {@link FilmActor#ATTRIBUTE_NAME_ACTOR actor} attribute to match.
     * @return an optional of found entity; empty if not found.
     */
    @NotNull Optional<FilmActor> find(@NotNull final Film film, @NotNull final Actor actor) {
        // TODO: 2019-08-04 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Counts all films that specified actor played.
     * <blockquote><pre>{@code
     * SELECT COUNT(*) FROM film_actor WHERE actor_id = ?
     * }</pre></blockquote>
     *
     * @param actor the actor whose films are counted.
     * @return the number of films {@code actor} played.
     */
    @PositiveOrZero long countFilms(@NotNull final Actor actor) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists films that specified actor ever played, sorted by {@link Film#ATTRIBUTE_NAME_RELEASE_YEAR releaseYear}
     * attribute in descending order.
     * <blockquote><pre>{@code
     * SELECT f.*
     * FROM film_actor AS fa
     *     INNER JOIN film AS f on fa.film_id = f.film_id
     * WHERE fa.actor_id = ?
     * ORDER BY f.release_year DESC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param actor       the actor whose films are listed.
     * @param firstResult position of the first result, numbered from {@code 0}.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of films of {@code actor}.
     */
    @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                  @Positive final Integer maxResults) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count all actors played in specified film.
     *
     * @param film the film whose actors are counted.
     * @return the number of actors mapped to specified film.
     */
    @PositiveOrZero long countActors(@NotNull final Film film) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists actors played in specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} in ascending
     * order.
     * <blockquote><pre>{@code
     * SELECT a.*
     * FROM film_actor AS fa
     *     INNER JOIN actor AS a ON fa.actor_id = a.id
     * WHERE fa.film_id = ?
     * ORDER BY a.first_name ASC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param film        the film whose actors are listed.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                    @Positive final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }
}
