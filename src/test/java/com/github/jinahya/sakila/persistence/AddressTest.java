package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteOrder;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for testing {@link Address} class.
 */
@Slf4j
class AddressTest extends BaseEntityTest<Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides instances of {@link ByteOrder}.
     *
     * @return a stream of arguments of a byte order.
     */
    static Stream<Arguments> provideByteOrders() {
        return Stream.of(
                Arguments.of(ByteOrder.BIG_ENDIAN),
                Arguments.of(ByteOrder.LITTLE_ENDIAN)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressTest() {
        super(Address.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link Address#setLocationAsPoint(double, double, ByteOrder)} method and {@link
     * Address#applyLocationAsPoint(BiFunction)} method.
     *
     * @param order a byte order to use.
     */
    @MethodSource({"provideByteOrders"})
    @ParameterizedTest
    void testAddressAsPoint(final ByteOrder order) {
        log.debug("order: {}", order);
        final Address instance = entityInstance();
        final double x1 = current().nextDouble();
        final double y1 = current().nextDouble();
        log.debug("x1: {}, y1: {}", x1, y1);
        instance.setLocationAsPoint(x1, y1, order);
        log.debug("location: {}", instance.getLocation());
        instance.applyLocationAsPoint((x2, y2) -> {
            log.debug("x2: {}, y2: {}", x2, y2);
            assertEquals(x1, x2);
            assertEquals(y1, y2);
            return null;
        });
    }
}
