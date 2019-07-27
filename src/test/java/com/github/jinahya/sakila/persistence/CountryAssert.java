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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for asserting {@link Country}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CountryAssert extends BaseEntityAssert<CountryAssert, Country> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     *
     * @param actual an actual.
     */
    CountryAssert(final Country actual) {
        super(actual);
    }

    // --------------------------------------------------------------------------------------------------------- country

    /**
     * Asserts that the {@link #actual} has the same {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute as
     * specified.
     *
     * @param country the value for {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @return this assert.
     */
    CountryAssert hasCountry(@NotNull final String country) {
        isNotNull().satisfies(a -> assertThat(a.getCountry()).isNotNull().isEqualTo(country));
        return this;
    }
}
