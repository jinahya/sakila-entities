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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.ByteOrder;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A class for testing {@link Address} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressTest extends BaseEntityTest<Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides byte orders to test with.
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

    // -------------------------------------------------------------------------------------------------------- location

    /**
     * Asserts {@link Address#getLocation()} returns a copy of the current value.
     */
    // TODO: 2019-07-14 enable, assert fails, implement, assert passes.
    @Disabled
    @Test
    void assertGetLocationReturnsCopy() {
        final Address address = new Address();
        final byte[] location = new byte[0];
        setField(Address.class, address, Address.ATTRIBUTE_NAME_LOCATION, location);
        assertNotSame(location, address.getLocation());
    }

    /**
     * Asserts {@link Address#setLocation(byte[])} replaces the current value with a copy of specified value.
     */
    // TODO: 2019-07-14 enable, assert fails, implement, assert passes.
    @Disabled
    @Test
    void assertSetLocationReplacesCopy() {
        final Address address = new Address();
        final byte[] location = new byte[0];
        address.setLocation(location);
        assertNotSame(location, getField(Address.class, address, Address.ATTRIBUTE_NAME_LOCATION));
    }

    /**
     * Asserts the {@link Address#applyLocationAsPoint(BiFunction)} method throws an {@code IllegalStateException} when
     * the value of {@link Address#ATTRIBUTE_NAME_LOCATION} attribute is currently {@code null}.
     */
    @Test
    void assertApplyLocationAsPointThrowsIllegalStateExceptionWhenAddressIsNull() {
        assertThrows(IllegalStateException.class, () -> new Address().applyLocationAsPoint((x, y) -> null));
    }

    /**
     * Asserts the {@link Address#applyLocationAsPoint(BiFunction)} method throws an {@code IllegalStateException} when
     * {@link Address#ATTRIBUTE_NAME_LOCATION} attribute is {@code null}.
     */
    @Test
    void assertApplyLocationAsPointThrowsIllegalStateExceptionWhenAddressLengthIsNotForPoint() {
        final Address address = new Address();
        address.setLocation(new byte[0]);
        assertThrows(IllegalStateException.class, () -> new Address().applyLocationAsPoint((x, y) -> null));
    }

    /**
     * Tests {@link Address#setLocationAsPoint(double, double, ByteOrder)} method and {@link
     * Address#applyLocationAsPoint(BiFunction)} method.
     *
     * @param order a byte order to test with.
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
