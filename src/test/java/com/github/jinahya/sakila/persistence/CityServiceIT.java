package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Assertions.assertThat;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableNavigableMap;
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
     * The total number of cities in database.
     */
    static final int CITY_COUNT_AS_INT = entityCountAsInt(City.class);

    /**
     * Returns a random city found in database;
     *
     * @return a random city found in database;
     */
    static City randomCity() {
        return randomEntity(City.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable navigable map of country ids and a unmodifiable list of cities.
     */
    private static final NavigableMap<Integer, List<String>> COUNTRY_ID_CITIES;

    static {
        try {
            COUNTRY_ID_CITIES = unmodifiableNavigableMap(applyResourceScanner(
                    "city_country_id_city.txt",
                    s -> {
                        final NavigableMap<Integer, List<String>> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final Integer countryId = s.nextInt();
                            final String city = s.nextLine();
                            map.compute(countryId, (k, v) -> {
                                if (v == null) {
                                    v = new ArrayList<>();
                                }
                                v.add(city);
                                return v;
                            });
                        }
                        for (Integer key : map.keySet().toArray(new Integer[0])) {
                            map.put(key, unmodifiableList(map.get(key)));
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a stream of arguments of random cities.
     *
     * @return a stream of arguments of random cities.
     */
    static Stream<Arguments> sourceRandomCities() {
        return IntStream.range(1, 17).mapToObj(i -> randomCity()).map(Arguments::of);
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
     * Asserts {@link CityService#count(Country)} method returns {@code 0} for an unknown country.
     *
     * @param unknown an unknown country
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"com.github.jinahya.sakila.persistence.CountryServiceIT#sourceUnknownCountries"})
    @ParameterizedTest
    void assertCountReturnsZeroForUnknownCountry(@NotNull final Country unknown) {
        final long count = serviceInstance().count(unknown);
        assertThat(count)
                .isZero();
    }

    /**
     * Tests {@link CityService#count(Country)} method.
     *
     * @param country a value for {@code country} parameter to test with.
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"com.github.jinahya.sakila.persistence.CountryServiceIT#sourceRandomCountries"})
    @ParameterizedTest
    void testCount(@NotNull final Country country) {
        final long expected = COUNTRY_ID_CITIES.get(country.getId()).size();
        final long actual = serviceInstance().count(country);
        assertThat(actual)
                .isNotNegative()
                .isEqualTo(expected);
    }

    // ------------------------------------------------------------------------------------------------------------ list

    /**
     * Asserts {@link CityService#list(Country, Integer, Integer)} method returns an empty list for an unknown country.
     *
     * @param unknown an unknown country
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"com.github.jinahya.sakila.persistence.CountryServiceIT#sourceUnknownCountries"})
    @ParameterizedTest
    void assertListReturnsEmptyForUnknownCountry(@NotNull final Country unknown) {
        final List<City> list = serviceInstance().list(unknown, null, null);
        assertThat(list)
                .isNotNull()
                .hasSize(0)
        ;
    }

    /**
     * Tests {@link CityService#list(Country, Integer, Integer)} method.
     *
     * @param country     a country to test with.
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResult   a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-20 enable, assert fails, implements, and assert passes.
    @Disabled
    @MethodSource({"sourceForTestList"})
    @ParameterizedTest
    void testList(@NotNull final Country country, @Nullable final Integer firstResult,
                  @Nullable final Integer maxResult) {
        final List<City> list = serviceInstance().list(country, firstResult, maxResult);
        assertThat(list)
                .isNotNull()
                .allSatisfy(city -> assertThat(city).residesIn(country))
                .hasSizeLessThanOrEqualTo(ofNullable(maxResult).orElse(Integer.MAX_VALUE))
        ;
    }
}
