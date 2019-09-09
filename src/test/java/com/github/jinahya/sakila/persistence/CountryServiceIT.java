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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertCountry;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableNavigableSet;
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
     * An unmodifiable navigable set of {@link Country#ATTRIBUTE_NAME_COUNTRY country} attributes.
     */
    static final NavigableSet<String> COUNTRIES;

    static {
        try {
            COUNTRIES = unmodifiableNavigableSet(applyResourceScanner(
                    "country_set_country.txt",
                    s -> {
                        final NavigableSet<String> set = new TreeSet<>();
                        while (s.hasNext()) {
                            final String country = s.nextLine().trim();
                            assert !country.isEmpty() : "empty country";
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
     * An unmodifiable map of country id and city count.
     */
    static final Map<Integer, Integer> COUNTRY_ID_CITY_COUNT;

    static {
        try {
            COUNTRY_ID_CITY_COUNT = unmodifiableMap(applyResourceScanner(
                    "country_map_country_id_city_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new HashMap<>();
                        while (s.hasNext()) {
                            final int countryId = s.nextInt();
                            final int cityCount = s.nextInt();
                            assert cityCount > 0;
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

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<Arguments> argumentsOfRandomCountries() {
        return IntStream.range(0, current().nextInt(8, 17))
                .mapToObj(i -> randomEntity(Country.class)).map(Arguments::of);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testFind(String)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestFind() {
        return IntStream.range(0, current().nextInt(8, 17))
                .mapToObj(i -> randomEntity(Country.class))
                .map(Country::getCountry)
                .map(Arguments::of);
    }

    /**
     * Provides arguments for {@link #testList(Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestList() {
        return IntStream.range(0, 17).mapToObj(i -> arguments(
                current().nextBoolean() ? null : firstResult(Country.class),
                current().nextBoolean() ? null : maxResults(Country.class)));
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
     * Tests {@link CountryService#find(String)} method.
     *
     * @param country a value for {@code country} parameter.
     */
    @MethodSource({"argumentsForTestFind"})
    @ParameterizedTest
    void testFind(@NotNull final String country) {
        final Optional<Country> found = serviceInstance().find(country);
        assertThat(found)
                .isPresent()
                .hasValueSatisfying(v -> assertCountry(v).hasCountry(country))
        ;
    }

    /**
     * Asserts {@link CountryService#find(String)} method an empty optional for an unknown country.
     */
    // TODO: 09/09/2019 enable, assert fails, implement, and assert passes.
    @Disabled
    @Test
    void assertFindReturnsEmptyForUnknownCountry() {
        final Optional<Country> found = serviceInstance().find("Machu Picchu");
        assertThat(found)
                .isEmpty()
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link CountryService#list(Integer, Integer)} method.
     *
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    @MethodSource({"argumentsForTestList"})
    @ParameterizedTest
    void testList(@PositiveOrZero @Nullable final Integer firstResult, @Positive @Nullable final Integer maxResults) {
        final List<Country> list = serviceInstance().list(firstResult, maxResults);
        ofNullable(list).ifPresent(v -> {
            log.debug("list.size: {}", v.size());
            v.forEach(c -> {
                log.debug("country: {}", c.getCountry());
            });
        });
        assertThat(list)
                .isNotNull()
                .isNotEmpty()
                .isSortedAccordingTo(Country.COMPARING_COUNTRY_IGNORE_CASE)
                .hasSizeLessThanOrEqualTo(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }
}

