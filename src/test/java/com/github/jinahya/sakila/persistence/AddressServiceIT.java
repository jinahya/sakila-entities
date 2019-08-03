package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Address.COMPARING_ADDRESS_IGNORE_CASE;
import static com.github.jinahya.sakila.persistence.Address.COMPARING_DISTRICT_IGNORE_CASE;
import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for integration-testing {@link AddressService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressServiceIT extends BaseEntityServiceIT<AddressService, Address> {

    // -----------------------------------------------------------------------------------------------------------------
    static final Map<Integer, Integer> CITY_ID_ADDRESS_CONT;

    static {
        try {
            CITY_ID_ADDRESS_CONT = unmodifiableMap(applyResourceScanner(
                    "address_map_city_id_address_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new LinkedHashMap<>();
                        while (s.hasNext()) {
                            final int cityId = s.nextInt();
                            final int addressCount = s.nextInt();
                            final Integer previous = map.put(cityId, addressCount);
                            assert previous == null : "duplicate city id: " + cityId;
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testCount(City, int)} method.
     *
     * @return a stream of arguments
     */
    private static Stream<Arguments> argumentForTestCount() {
        return IntStream.range(0, 8)
                .mapToObj(i -> CITY_ID_ADDRESS_CONT.entrySet()
                        .stream()
                        .skip(i)
                        .findFirst()
                        .map(e -> {
                            final int cityId = e.getKey();
                            final int addressCount = e.getValue();
                            final City city = BaseEntityServiceIT.findById(City.class, cityId)
                                    .orElseThrow(() -> new RuntimeException("city not found for " + cityId));
                            return Arguments.of(city, addressCount);
                        })
                        .orElseThrow(() -> new RuntimeException("invalid index"))
                );
    }

    /**
     * Provides arguments for {@link #testList(City, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentForTestList() {
        return IntStream.range(0, 8).mapToObj(i -> {
            final City city = randomEntity(City.class);
            final Integer addressCount = CITY_ID_ADDRESS_CONT.get(city.getId());
            final Integer firstResult
                    = current().nextBoolean() || addressCount == null
                      ? null : current().nextInt(0, CITY_ID_ADDRESS_CONT.get(city.getId()));
            final Integer maxResults = current().nextBoolean() ? null : current().nextInt() >>> 1;
            return Arguments.of(city, firstResult, maxResults);
        });
    }
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressServiceIT() {
        super(AddressService.class, Address.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link AddressService#count(City)} method.
     *
     * @param city     a value for {@code city} argument.
     * @param expected an expected result of the method.
     */
    @MethodSource({"argumentForTestCount"})
    @ParameterizedTest
    void testCount(final City city, final int expected) {
        final long actual = serviceInstance().count(city);
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link AddressService#count(City)} method.
     *
     * @param city        a value for {@code city} argument.
     * @param firstResult a value for {@code firstResult} argument.
     * @param maxResults  a value for {@code maxResults} argument.
     */
    @MethodSource({"argumentForTestList"})
    @ParameterizedTest
    void testList(final City city, final Integer firstResult, final Integer maxResults) {
        final List<Address> list = serviceInstance().list(city, firstResult, maxResults);
        log.debug("list: {}", list);
        ofNullable(list).ifPresent(l -> {
            log.debug("list.size: {}", list.size());
            l.forEach(e -> log.debug("address: {}", e));
        });
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(COMPARING_DISTRICT_IGNORE_CASE.thenComparing(COMPARING_ADDRESS_IGNORE_CASE))
        ;
    }
}
