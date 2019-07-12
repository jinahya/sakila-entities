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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class for testing {@link FullName}.
 */
class FullNameTest {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FullName#getAsFirstNameFirst()}.
     */
    @Test
    void testGetAsFirstNameFirst() {
        final FullName instance = new FullName();
        final String firstName = "firstName";
        final String lastName = "lastName";
        instance.setFirstName(firstName);
        instance.setLastName(lastName);
        final String expected = firstName + " " + lastName;
        final String actual = instance.getAsFirstNameFirst();
        assertEquals(expected, actual);
    }

    /**
     * Tests {@link FullName#getAsLastNameFirst()}.
     */
    @Test
    void testGetAsLastNameFirst() {
        final FullName instance = new FullName();
        final String firstName = "firstName";
        final String lastName = "lastName";
        instance.setFirstName(firstName);
        instance.setLastName(lastName);
        final String expected = lastName + ", " + firstName;
        final String actual = instance.getAsLastNameFirst();
        assertEquals(expected, actual);
    }
}
