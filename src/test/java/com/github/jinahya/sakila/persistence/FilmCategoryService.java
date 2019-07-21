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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

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
     * Counts categories as which specified film is categorized.
     *
     * @param film the film whose categories are counted.
     * @return the number of categories of specified film.
     */
    public @PositiveOrZero long countCategories(@NotNull final Film film) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists categories of specified film ordered by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending
     * order.
     *
     * @param film the film whose categories are listed.
     * @return a list of categories of specified film.
     */
    public @NotNull List<@NotNull Category> listCategories(@NotNull final Film film) {
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
    public @PositiveOrZero long countFilms(@NotNull final Category category) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists all films categorized as specified category ordered by {@link Film#ATTRIBUTE_NAME_TITLE title} attribute in
     * ascending order.
     *
     * @param category the category as which films are categorized.
     * @return a list of films categorized as specified category.
     */
    public @NotNull List<@NotNull Category> listFilms(@NotNull final Category category) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
