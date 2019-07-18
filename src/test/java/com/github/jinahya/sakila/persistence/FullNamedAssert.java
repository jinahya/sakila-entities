package com.github.jinahya.sakila.persistence;

import org.assertj.core.api.AbstractAssert;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;

import static java.util.Optional.ofNullable;
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
     * Asserts that the current {@link #actual} is not {@code null} and its current value of {@link
     * FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute is equal to specified value.
     *
     * @param expectedFirstName an expected value of {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute.
     * @return this assert.
     */
    @SuppressWarnings({"unchecked"})
    public T firstNamedAs(@NotNull final String expectedFirstName) {
        isNotNull().satisfies(v -> assertThat(v.getFirstName()).isNotNull().isEqualTo(expectedFirstName));
        return (T) this;
    }

    @Deprecated
    public T firstNamedAsNullable(@Nullable final String expectedFirstNameNullable) {
        return firstNamedAs(ofNullable(expectedFirstNameNullable).orElseGet(actual::getFirstName));
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

    @Deprecated
    public T lastNamedAsNullable(@Nullable final String expectedLastNameNullable) {
        return lastNamedAs(ofNullable(expectedLastNameNullable).orElseGet(actual::getLastName));
    }

    public T hasSameLastNameWith(@NotNull final FullNamed whoseLastNameExpected) {
        return lastNamedAs(whoseLastNameExpected.getLastName());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public T hasSameFullNameWith(@NotNull final FullNamed whoseNameExpected) {
        return hasSameFirstNameWith(whoseNameExpected).hasSameLastNameWith(whoseNameExpected);
    }
}
