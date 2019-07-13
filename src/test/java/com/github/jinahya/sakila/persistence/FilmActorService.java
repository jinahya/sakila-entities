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
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists films mapped to specified actor ordered by {@link BaseEntity#ATTRIBUTE_NAME_ID} attribute in ascending
     * order.
     *
     * @param actor       the actor to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}
     * @return a list of films.
     */
    public @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                         @Positive final Integer maxResults) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Counts distinct films mapped to any of specified actors.
     *
     * @param actors the actors whose films are counted.
     * @return the number of distinct films mapped to any of specified actors.
     */
    public @PositiveOrZero long countFilms(@NotNull final Collection<@NotNull ? extends Actor> actors) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
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
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count actors mapped to specified film.
     *
     * @param film the film to match.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Film film) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists actors mapped to specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME} in ascending order.
     *
     * @param film        the film to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    public @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                           @Positive final Integer maxResults) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Count distinct actors mapped to any of specified films.
     *
     * @param films the films to match.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Collection<@NotNull ? extends Film> films) {
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
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
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }
}
