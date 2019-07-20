package com.github.jinahya.sakila.persistence;

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
    private static Stream<Arguments> sourceForTestList() {
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
    @MethodSource({"sourceForTestList"})
    @ParameterizedTest
    void testListByCountry(@NotNull final Country country, @Nullable final Integer firstResult,
                           @Nullable final Integer maxResult) {
        final List<City> list = serviceInstance().listByCountry(country, firstResult, maxResult);
        assertThat(list)
                .isNotNull()
                .allSatisfy(city -> assertThat(city).residesIn(country))
                .isSortedAccordingTo(COMPARING_CITY)
                .hasSizeLessThanOrEqualTo(ofNullable(maxResult).orElse(Integer.MAX_VALUE))
        ;
    }
}
