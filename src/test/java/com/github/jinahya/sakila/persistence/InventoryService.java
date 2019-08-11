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

/**
 * A service class for {@link Inventory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class InventoryService extends BaseEntityService<Inventory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    InventoryService() {
        super(Inventory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of stores that specified film is available.
     *
     * @param film the film to check.
     * @return the number of stores that {@code film} is available.
     */
    @PositiveOrZero long countStores(@NotNull final Film film) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * List stores that specified film is available sorted by {@link Country#ATTRIBUTE_NAME_COUNTRY Country#country}
     * attribute, {@link City#ATTRIBUTE_NAME_CITY City#city} attribute, {@link Address#ATTRIBUTE_NAME_DISTRICT
     * Address#district} attribute, and {@link BaseEntity#ATTRIBUTE_NAME_ID Store#storeId} attribute in all ascending
     * order.
     *
     * @param film        the film to check.
     * @param firstResult first index of the result; {@code null} for an unspecified result.
     * @param maxResults  maximum results to retrieve; {@code null} for an unspecified result.
     * @return a list of stores.
     */
    @NotNull List<@NotNull Store> listStores(@NotNull final Film film,
                                             @PositiveOrZero @Nullable final Integer firstResult,
                                             @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts distinct films available on specified store.
     *
     * @param store the store whose distinct films are counted.
     * @return the number of films available in {@code store}.
     */
    @PositiveOrZero long countFilms(@NotNull final Film store) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * List distinct films available on specified store sorted by {@link Film#ATTRIBUTE_NAME_TITLE title} in ascending
     * order.
     *
     * @param store       the store whose films are listed.
     * @param firstResult first index of the result; {@code null} for an unspecified result.
     * @param maxResults  maximum results to retrieve; {@code null} for an unspecified result.
     * @return a list of films.
     */
    @NotNull List<@NotNull Film> listFilm(@NotNull final Store store,
                                          @PositiveOrZero @Nullable final Integer firstResult,
                                          @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
