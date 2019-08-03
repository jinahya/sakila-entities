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
 * A service class for {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityService extends BaseEntityService<City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CityService() {
        super(City.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts all cities which each resides in specified country.
     * <blockquote><pre>{@code
     * SELECT COUNT(*) FROM city WHERE country_id = ?
     * }</pre></blockquote>
     *
     * @param country the value of {@link City#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @return the number of cities in specified country.
     */
    @PositiveOrZero long count(@NotNull final Country country) {
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists cities which each resides in specified country sorted by {@link City#ATTRIBUTE_NAME_CITY city} attribute in
     * ascending order.
     * <blockquote><pre>{@code
     * SELECT * FROM city WHERE country_id = ? ORDER BY city ASC LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param country     the value of {@link City#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @param firstResult position of the first result, numbered from 0; {@code null} for an unspecified result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of cities.
     */
    @NotNull List<@NotNull City> list(@NotNull final Country country,
                                      @PositiveOrZero @Nullable final Integer firstResult,
                                      @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
