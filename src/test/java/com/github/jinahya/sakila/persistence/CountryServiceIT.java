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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableNavigableMap;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * A class for integration-testing {@link CountryService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CountryServiceIT extends BaseEntityServiceIT<CountryService, Country> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The total number of countries.
     */
    static final int COUNTRY_COUNT = entityCountAsInt(Country.class);

    /**
     * Returns a random country.
     *
     * @return a random country.
     */
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
                    "country_list_country.txt",
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
     * An unmodifiable navigable map of country id and country.
     */
    private static final NavigableMap<Integer, String> COUNTRY_ID_COUNTRY;

    static {
        try {
            COUNTRY_ID_COUNTRY = unmodifiableNavigableMap(applyResourceScanner(
                    "country_country_id_country.txt",
                    s -> {
                        final NavigableMap<Integer, String> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final Integer countryId = s.nextInt();
                            final String country = s.nextLine();
                            final String previous = map.put(countryId, country);
                            assert previous == null : "duplicate country id: " + countryId;
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static String country(final int countryId) {
        return COUNTRY_ID_COUNTRY.get(countryId);
    }

    // -----------------------------------------------------------------------------------------------------------------

    static Country unknown() {
        final Country unknown = new Country();
        do {
            unknown.setId(current().nextInt());
        } while (COUNTRY_ID_COUNTRY.containsKey(unknown.getId()));
        return unknown;
    }

    static Stream<Arguments> sourceUnknownCountries() {
        return IntStream.range(0, 17).mapToObj(i -> unknown()).map(Arguments::of);
    }

    static Stream<Arguments> sourceRandomCountries() {
        return IntStream.range(0, 17).mapToObj(i -> randomCountry()).map(Arguments::of);
    }

    // -----------------------------------------------------------------------------------------------------------------
    /**
     * Provides arguments for {@link #testFindByCountry(String)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestFindByCountry() {
        return IntStream.range(0, 17).mapToObj(i -> randomCountry()).map(Country::getCountry).map(Arguments::of);
    }

    /**
     * Provides arguments for {@link #testListSortedByCountry(boolean, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListSortedByCountry() {
        return IntStream.range(0, 17).mapToObj(i -> arguments(
                current().nextBoolean(), firstResult(Country.class), maxResults(Country.class)));
    }

    // -----------------------------------------------------------------------------------------------------------------a

    /**
     * Creates a new instance.
     */
    CountryServiceIT() {
        super(CountryService.class, Country.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link CountryService#findByCountry(String)} method.
     *
     * @param country a value for {@code country} parameter.
     */
    // TODO: 7/17/2019 enable, assert fails, implement, assert passes.
    @Disabled
    @MethodSource({"argumentsForTestFindByCountry"})
    @ParameterizedTest
    void testFindByCountry(@NotNull final String country) {
        final Optional<Country> found = serviceInstance().findByCountry(country);
        assertThat(found)
                .isPresent()
                .hasValueSatisfying(v -> assertThat(v.getCountry()).isEqualTo(country));
    }

    /**
     * Tests {@link CountryService#listSortedByCountry(boolean, Integer, Integer)} method.
     *
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    // TODO: 7/17/2019 enable, assert fails, implement, assert passes.
    @Disabled
    @MethodSource({"argumentsForTestListSortedByCountry"})
    @ParameterizedTest
    void testListSortedByCountry(final boolean ascendingOrder, final Integer firstResult, final Integer maxResults) {
        final List<Country> list = serviceInstance().listSortedByCountry(ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(Country.comparaingCountry(ascendingOrder))
                .size().satisfies(s -> ofNullable(maxResults).ifPresent(x -> assertThat(s).isLessThanOrEqualTo(x)));
    }
}

