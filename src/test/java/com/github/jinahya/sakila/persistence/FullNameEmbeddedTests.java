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
     * Provides a stream of {@link FullNameFormatter} for testing.
     *
     * @return a stream of {@link FullNameFormatter}.
     */
    static Stream<Arguments> provideNameOrders() {
        return Stream.of(
                Arguments.of(FullNameFormatter.FIRST_NAME_FIRST),
                Arguments.of(FullNameFormatter.LAST_NAME_FIRST)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> void test(final Supplier<? extends T> supplier) {
        final T instance = supplier.get();
        {
            final FullName fullName = instance.getFullName();
            if (fullName != null) {
                final String firstNameFirst = fullName.toString(FullNameFormatter.FIRST_NAME_FIRST, " ");
                final String lastNameFirst = fullName.toString(FullNameFormatter.LAST_NAME_FIRST, ", ");
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
