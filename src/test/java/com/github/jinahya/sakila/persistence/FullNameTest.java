package com.github.jinahya.sakila.persistence;

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
