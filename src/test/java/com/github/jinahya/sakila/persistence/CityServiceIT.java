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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertCity;
import static com.github.jinahya.sakila.persistence.City.COMPARING_CITY_IGNORE_CASE;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * A class for integration-testing {@link CityService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityServiceIT extends BaseEntityServiceIT<CityService, City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a stream of arguments of random cities.
     *
     * @return a stream of arguments of random cities.
     */
    static Stream<Arguments> sourceRandomCities() {
        return IntStream.range(1, 17).mapToObj(i -> randomEntity(City.class)).map(Arguments::of);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<Arguments> argumentsForTestListByCountry() {
        return CountryServiceIT.sourceRandomCountries()
                .map(a -> {
                    final Country country = (Country) a.get()[0];
                    final int cityCount = CountryServiceIT.COUNTRY_ID_CITY_COUNT.get(country.getId());
                    final Integer firstResult = current().nextBoolean() ? null : current().nextInt(cityCount);
                    final Integer maxResults = current().nextBoolean() ? null : current().nextInt(cityCount) + 1;
                    return arguments(country, firstResult, maxResults);
                });
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CityServiceIT() {
        super(CityService.class, City.class);
    }

    // ----------------------------------------------------------------------------------------------------------- count

    /**
     * Tests {@link CityService#countByCountry(Country)} method.
     *
     * @param country a value for {@code country} parameter to test with.
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"com.github.jinahya.sakila.persistence.CountryServiceIT#sourceRandomCountries"})
    @ParameterizedTest
    void testCountByCountry(@NotNull final Country country) {
        final long expected = CountryServiceIT.COUNTRY_ID_CITY_COUNT.get(country.getId());
        final long actual = serviceInstance().countByCountry(country);
        assertThat(actual)
                .isNotNegative()
                .isEqualTo(expected);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link CityService#listByCountry(Country, Integer, Integer)} method.
     *
     * @param country     a country to test with.
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"argumentsForTestListByCountry"})
    @ParameterizedTest
    void testListByCountry(@NotNull final Country country, @PositiveOrZero @Nullable final Integer firstResult,
                           @Positive @Nullable final Integer maxResults) {
        final List<City> list = serviceInstance().listByCountry(country, firstResult, maxResults);
        log.debug("list.size: {}", list.size());
        list.stream().map(City::getCity).forEach(city -> log.debug("city: {}", city));
        assertThat(list)
                .isNotNull()
                .isNotEmpty()
                .allSatisfy(city -> assertCity(city).isNotNull().residesIn(country))
                .isSortedAccordingTo(COMPARING_CITY_IGNORE_CASE)
                .hasSizeLessThanOrEqualTo(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }
}
