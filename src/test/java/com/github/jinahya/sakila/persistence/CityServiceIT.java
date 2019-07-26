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
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertThat;
import static com.github.jinahya.sakila.persistence.City.COMPARING_CITY;
import static java.util.Optional.ofNullable;
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
    private static Stream<Arguments> sourceForListByCountry() {
        return CountryServiceIT.sourceRandomCountries()
                .map(a -> arguments(a.get()[0], firstResult(City.class), maxResults(City.class)));
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
     * @param maxResult   a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"sourceForListByCountry"})
    @ParameterizedTest
    void testListByCountry(@NotNull final Country country, @Nullable final Integer firstResult,
                           @Nullable final Integer maxResult) {
        final List<City> list = serviceInstance().listByCountry(country, firstResult, maxResult);
        list.forEach(city -> log.debug("city: {}", city));
        assertThat(list)
                .isNotNull()
                .allSatisfy(city -> assertThat(city).residesIn(country))
                .isSortedAccordingTo(COMPARING_CITY)
                .hasSizeLessThanOrEqualTo(ofNullable(maxResult).orElse(Integer.MAX_VALUE))
        ;
    }
}
