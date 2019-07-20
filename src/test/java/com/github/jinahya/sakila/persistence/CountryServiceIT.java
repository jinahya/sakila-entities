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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertThat;
import static com.github.jinahya.sakila.persistence.Country.comparaingCountry;
import static java.util.Collections.unmodifiableNavigableMap;
import static java.util.Collections.unmodifiableNavigableSet;
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
@Slf4j
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
    static final NavigableSet<Integer> COUNTRY_IDS;

    static {
        try {
            COUNTRY_IDS = unmodifiableNavigableSet(applyResourceScanner(
                    "country_set_id.txt",
                    s -> {
                        final NavigableSet<Integer> set = new TreeSet<>();
                        while (s.hasNext()) {
                            final int countryId = s.nextInt();
                            final boolean added = set.add(countryId);
                            assert added : "duplicate country id: " + countryId;
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

    /**
     * An unmodifiable navigable map of country id and city count.
     */
    static final NavigableMap<Integer, Integer> COUNTRY_ID_CITY_COUNT;

    static {
        try {
            COUNTRY_ID_CITY_COUNT = unmodifiableNavigableMap(applyResourceScanner(
                    "country_map_country_id_city_count.txt",
                    s -> {
                        final NavigableMap<Integer, Integer> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final Integer countryId = s.nextInt();
                            final Integer cityCount = s.nextInt();
                            final Integer previous = map.put(countryId, cityCount);
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

    static Integer cityCount(final int countryId) {
        return COUNTRY_ID_CITY_COUNT.get(countryId);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable map of country id and country.
     */
    private static final NavigableMap<Integer, String> COUNTRY_ID_COUNTRY;

    static {
        try {
            COUNTRY_ID_COUNTRY = unmodifiableNavigableMap(applyResourceScanner(
                    "country_map_country_id_country.txt",
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

    static Country unknownCountry() {
        final Country unknown = new Country();
        do {
            unknown.setId(current().nextInt());
        } while (COUNTRY_ID_COUNTRY.containsKey(unknown.getId()));
        return unknown;
    }

    static Stream<Arguments> sourceUnknownCountries() {
        return IntStream.range(0, 17).mapToObj(i -> unknownCountry()).map(Arguments::of);
    }

    static Stream<Arguments> sourceRandomCountries() {
        return IntStream.range(0, 17).mapToObj(i -> randomCountry()).map(Arguments::of);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<Arguments> argumentsForTestListByCountry() {
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
     * Tests {@link CountryService#listByCountry(String)} method.
     *
     * @param country a value for {@code country} parameter.
     */
    @MethodSource({"argumentsForTestListByCountry"})
    @ParameterizedTest
    void testListByCountry(@NotNull final String country) {
        final List<Country> list = serviceInstance().listByCountry(country);
        assertThat(list)
                .isNotEmpty()
                .allSatisfy(c -> assertThat(c).hasCountry(country))
                .isSortedAccordingTo(BaseEntity.COMPARING_ID)
        ;
    }

    /**
     * Tests {@link CountryService#listSortedByCountry(boolean, Integer, Integer)} method.
     *
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    @MethodSource({"argumentsForTestListSortedByCountry"})
    @ParameterizedTest
    void testListSortedByCountry(final boolean ascendingOrder, final Integer firstResult, final Integer maxResults) {
        final List<Country> list = serviceInstance().listSortedByCountry(ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(comparaingCountry(ascendingOrder))
                .hasSizeLessThanOrEqualTo(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }
}

