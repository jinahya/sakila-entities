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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Country.comparingCountry;
import static java.util.Collections.unmodifiableNavigableMap;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
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
                            final int countryId = s.nextInt();
                            final int cityCount = s.nextInt();
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

    static int cityCount(final int countryId) {
        return ofNullable(COUNTRY_ID_CITY_COUNT.get(countryId))
                .orElseThrow(() -> new IllegalArgumentException("unknown country id: " + countryId));
    }

    static int cityCount(final Country country) {
        return cityCount(requireNonNull(country, "country is null").getId());
    }

    static final Comparator<Country> COMPARING_CITY_COUNT = comparingInt(CountryServiceIT::cityCount);

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Arguments> sourceRandomCountries() {
        return IntStream.range(0, 17).mapToObj(i -> randomEntity(Country.class)).map(Arguments::of);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testListSortedByCountryIn(boolean, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> sourceForTestListSortedByCountryIn() {
        return IntStream.range(0, 17).mapToObj(i -> arguments(
                current().nextBoolean(), firstResult(Country.class), maxResults(Country.class)));
    }

    private static Stream<Arguments> sourceForTestListSortedByCityCount() {
        return IntStream.range(0, 17).mapToObj(i -> arguments(firstResult(Country.class), maxResults(Country.class)));
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
     * Tests {@link CountryService#listSortedByCountryIn(boolean, Integer, Integer)} method.
     *
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    // TODO: 7/17/2019 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"sourceForTestListSortedByCountryIn"})
    @ParameterizedTest
    void testListSortedByCountryIn(final boolean ascendingOrder, @PositiveOrZero @Nullable final Integer firstResult,
                                   @Positive @Nullable final Integer maxResults) {
        final List<Country> list = serviceInstance().listSortedByCountryIn(ascendingOrder, firstResult, maxResults);
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(comparingCountry(ascendingOrder))
                .hasSizeLessThanOrEqualTo(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link CountryService#listSortedByCityCount(Integer, Integer)} method.
     *
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    // TODO: 7/17/2019 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"sourceForTestListSortedByCityCount"})
    @ParameterizedTest
    void testListSortedByCityCount(@PositiveOrZero @Nullable final Integer firstResult,
                                   @Positive @Nullable final Integer maxResults) {
        final List<Country> list = serviceInstance().listSortedByCityCount(firstResult, maxResults);
        assertThat(list).isSortedAccordingTo(COMPARING_CITY_COUNT.reversed());
        list.stream().collect(Collectors.groupingBy(CountryServiceIT::cityCount)).values()
                .forEach(v -> assertThat(v).isSortedAccordingTo(Country.COMPARING_COUNTRY));
    }
}

