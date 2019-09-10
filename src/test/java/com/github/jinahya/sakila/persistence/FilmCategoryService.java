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
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

/**
 * A service class for {@link FilmCategory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmCategoryService extends EntityService<FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmCategoryService() {
        super(FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Finds the entity whose {@link FilmCategory#ATTRIBUTE_NAME_FILM film} attribute and {@link
     * FilmCategory#ATTRIBUTE_NAME_CATEGORY category} attribute match to specified values.
     *
     * @param film     the value of {@link FilmCategory#ATTRIBUTE_NAME_FILM film} attribute to match.
     * @param category the value of {@link FilmCategory#ATTRIBUTE_NAME_CATEGORY category} attribute to match.
     * @return an optional of matched entity; empty if not matched.
     */
    @NotNull Optional<FilmCategory> find(@NotNull final Film film, @NotNull final Category category) {
        // TODO: 2019-08-04 implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts categories as which specified film is categorized.
     *
     * @param film the film whose categories are counted.
     * @return the number of categories of specified film.
     */
    @PositiveOrZero long countCategories(@NotNull final Film film) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists categories of specified film ordered by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending
     * order.
     * <blockquote><pre>{@code
     * SELECT c.*
     * FROM film_category AS fc
     *     INNER JOIN category AS c ON fc.category_id = c.id
     * WHERE fc.film_id = ?
     * ORDER BY c.name ASC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param film        the film whose categories are listed.
     * @param firstResult position of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                    result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of categories of specified film.
     */
    @NotNull List<@NotNull Category> listCategories(@NotNull final Film film,
                                                    @PositiveOrZero @Nullable final Integer firstResult,
                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of films categorized as specified category.
     *
     * @param category the category as which films are categorized.
     * @return the number of films categorized as specified category.
     */
    @PositiveOrZero long countFilms(@NotNull final Category category) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists all films categorized as specified category ordered by {@link Film#ATTRIBUTE_NAME_TITLE title} attribute in
     * ascending order.
     * <blockquote><pre>{@code
     * SELECT f.*
     * FROM film_category AS fc
     *     INNER JOIN film AS f ON fc.film_id = fc.id
     * WHERE fc.category_id = ?
     * ORDER BY f.title ASC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param category    the category as which films are categorized.
     * @param firstResult position of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                    result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of films categorized as specified category.
     */
    @NotNull List<@NotNull Film> listFilms(@NotNull final Category category,
                                           @PositiveOrZero @Nullable final Integer firstResult,
                                           @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
