package com.github.jinahya.sakila.persistence;

import org.assertj.core.api.AbstractAssert;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An abstract for creating an {@link org.assertj.core.api.Assert} for subclasses of {@link FullNamed}.
 *
 * @param <T> assert type parameter
 * @param <U> full named type paramter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class FullNamedAssert<T extends FullNamedAssert<T, U>, U extends FullNamed> extends AbstractAssert<T, U> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with given actual value.
     *
     * @param actual the actual value.
     */
    FullNamedAssert(final U actual) {
        super(actual, FullNamedAssert.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Asserts that the current {@code actual} is not {@code null} and its current value of {@link
     * FullNamed#ATTRIBUTE_NAME_FIRST_NAME} attribute is equal to specified value.
     *
     * @param expectedFirstName an expected value for {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME} attribute.
     * @return this assert.
     */
    @SuppressWarnings({"unchecked"})
    public T firstNamedAs(@NotNull final String expectedFirstName) {
        isNotNull().satisfies(v -> assertThat(v.getFirstName()).isNotNull().isEqualTo(expectedFirstName));
        return (T) this;
    }

    public T hasSameFirstNameWith(@NotNull final FullNamed whoseFirstNameExpected) {
        return firstNamedAs(whoseFirstNameExpected.getFirstName());
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    public T lastNamedAs(@NotNull final String expectedLastName) {
        isNotNull().satisfies(v -> assertThat(v.getLastName()).isNotNull().isEqualTo(expectedLastName));
        return (T) this;
    }

    public T hasSameLastNameWith(@NotNull final FullNamed whoseLastNameExpected) {
        return lastNamedAs(whoseLastNameExpected.getLastName());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public T hasSameFullNameWith(@NotNull final FullNamed whoseNameExpected) {
        return hasSameFirstNameWith(whoseNameExpected).hasSameLastNameWith(whoseNameExpected);
    }
}
