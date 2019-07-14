package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.params.provider.Arguments;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Test constants and utilities for {@link FullNameEmbedded}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class FullNameEmbeddedTests {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides a stream of {@link FullName.Order} for testing.
     *
     * @return a stream of {@link FullName.Order}.
     */
    static Stream<Arguments> provideNameOrders() {
        return Stream.of(
                Arguments.of(FullName.Order.FIRST_NAME_FIRST),
                Arguments.of(FullName.Order.LAST_NAME_FIRST)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> void test(final Supplier<? extends T> supplier) {
        final T instance = supplier.get();
        {
            final FullName fullName = instance.getFullName();
            if (fullName != null) {
                final String firstNameFirst = fullName.toString(FullName.Order.FIRST_NAME_FIRST, " ");
                final String lastNameFirst = fullName.toString(FullName.Order.LAST_NAME_FIRST, ", ");
            }
        }
        instance.setFullName(FullName.of("John", "Doggett")); // https://en.wikipedia.org/wiki/John_Doggett
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private FullNameEmbeddedTests() {
        super();
    }
}
