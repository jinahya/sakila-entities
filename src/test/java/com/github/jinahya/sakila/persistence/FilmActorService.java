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
import java.util.List;

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
     * Counts all films that specified actor played.
     *
     * @param actor the actor whose films are counted.
     * @return the number of films {@code actor} played.
     */
    public @PositiveOrZero long countFilms(@NotNull final Actor actor) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists films that specified actor played, sorted by {@link Film#ATTRIBUTE_NAME_RELEASE_YEAR releaseYear} attribute
     * in descending order.
     *
     * @param actor       the actor whose films are listed.
     * @param firstResult position of the first result, numbered from 0.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of films of {@code actor}.
     */
    public @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                         @Positive final Integer maxResults) {
        // TODO: 7/10/2019 implement
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count all actors played in specified film.
     *
     * @param film the film to match.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Film film) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }

    /**
     * Lists actors played in specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} in ascending
     * order.
     *
     * @param film        the film to match.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    public @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                           @Positive final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("unsupported operation; not implemented yet");
    }
}
