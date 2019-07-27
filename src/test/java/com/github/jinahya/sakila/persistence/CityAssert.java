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

import javax.validation.constraints.NotNull;

import java.util.Objects;

import static com.github.jinahya.sakila.persistence.Assertions.assertCountry;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for asserting {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CityAssert extends BaseEntityAssert<CityAssert, City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     *
     * @param actual an actual.
     */
    CityAssert(final City actual) {
        super(actual);
    }

    // ------------------------------------------------------------------------------------------------------------ city

    /**
     * Asserts the {@link #actual} has the value of {@link City#ATTRIBUTE_NAME_CITY city} attribute as specified value.
     *
     * @param city the value for {@link City#ATTRIBUTE_NAME_CITY city} attribute to match.
     * @return this assert.
     */
    CityAssert hasCity(@NotNull final String city) {
        isNotNull().matches(c -> Objects.equals(c.getCity(), city), "actual has city of " + city);
        return this;
    }

    @Deprecated // forRemoval = true
    CityAssert hasName(@NotNull final String city) {
        return hasCity(city);
    }

    // --------------------------------------------------------------------------------------------------------- country
    CityAssert residesInTheCountryWhoseHasId(@NotNull final int countryId) {
        isNotNull().satisfies(a -> assertCountry(a.getCountry()).hasId(countryId));
        return this;
    }

    CityAssert residesIn(@NotNull final Country country) {
        return residesInTheCountryWhoseHasId(country.getId());
    }
}
