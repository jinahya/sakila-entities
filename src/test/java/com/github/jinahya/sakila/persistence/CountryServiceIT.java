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

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSortedSet;

class CountryServiceIT extends BaseEntityServiceIT<CountryService, Country> {

    // -----------------------------------------------------------------------------------------------------------------
    static final int COUNTRY_COUNT = entityCountAsInt(Country.class);

    static Country randomCountry() {
        return randomEntity(Country.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable sorted set of {@link Country#ATTRIBUTE_NAME_COUNTRY} attributes.
     */
    static final SortedSet<String> COUNTRIES;

    static {
        try {
            COUNTRIES = unmodifiableSortedSet(applyResourceScanner(
                    "country_set_country.txt",
                    s -> {
                        final SortedSet<String> set = new TreeSet<>();
                        while (s.hasNext()) {
                            final String country = s.nextLine();
                            final boolean added = set.add(country);
                            assert added : "duplicate country: " + country;
                        }
                        return set;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CountryServiceIT() {
        super(CountryService.class, Country.class);
    }
}
