package com.github.jinahya.sakila.persistence;

import java.util.Comparator;

import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

/**
 * An interface for entities to which an {@link FullName} is embedded.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNameEmbedded {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the {@link FullName} attribute. The value is {@value}.
     */
    String ATTRIBUTE_NAME_FULL_NAME = "fullName";

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> Comparator<T> comparingFirstNameNatural() {
        return comparing(FullNameEmbedded::getFirstName);
    }

    static <T extends FullNameEmbedded> Comparator<T> comparingFirstNameReverse() {
        return FullNameEmbedded.<T>comparingFirstNameNatural().reversed();
    }

    static <T extends FullNameEmbedded> Comparator<T> comparingFirstName(final boolean natural) {
        return natural ? comparingFirstNameNatural() : comparingFirstNameReverse();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> Comparator<T> comparingLastNameNatural() {
        return comparing(FullNameEmbedded::getLastName);
    }

    static <T extends FullNameEmbedded> Comparator<T> comparingLastNameReverse() {
        return FullNameEmbedded.<T>comparingLastNameNatural().reversed();
    }

    static <T extends FullNameEmbedded> Comparator<T> comparingLastName(final boolean natural) {
        return natural ? comparingLastNameNatural() : comparingLastNameReverse();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the current value of {@link #ATTRIBUTE_NAME_FULL_NAME} attribute.
     *
     * @return the current value of {@link #ATTRIBUTE_NAME_FULL_NAME} attribute.
     */
    FullName getFullName();

    /**
     * Replaces the current value of {@link #ATTRIBUTE_NAME_FULL_NAME} attribute with specified value.
     *
     * @param fullName new value for  {@link #ATTRIBUTE_NAME_FULL_NAME} attribute.
     */
    void setFullName(FullName fullName);

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the current value of {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute of {@link
     * #ATTRIBUTE_NAME_FULL_NAME} attribute.
     *
     * @return the current value of {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute of {@link
     * #ATTRIBUTE_NAME_FULL_NAME} attribute.
     */
    default String getFirstName() {
        return ofNullable(getFullName()).map(FullName::getFirstName).orElse(null);
    }

    default void setFirstName(final String firstName) {
        ofNullable(getFullName())
                .orElseGet(() -> {
                    setFullName(new FullName());
                    return getFullName();
                })
                .setFirstName(firstName);
    }

    // -----------------------------------------------------------------------------------------------------------------
    default String getLastName() {
        return ofNullable(getFullName()).map(FullName::getLastName).orElse(null);
    }

    default void setLastName(final String lastName) {
        ofNullable(getFullName())
                .orElseGet(() -> {
                    setFullName(new FullName());
                    return getFullName();
                })
                .setLastName(lastName);
    }

    // -----------------------------------------------------------------------------------------------------------------
    default String toString(final FullName.Order order, final String delimiter) {
        return ofNullable(getFullName())
                .map(v -> v.toString(order, delimiter))
                .orElseThrow(() -> new IllegalStateException(
                        "the current value of " + ATTRIBUTE_NAME_FULL_NAME + " attribute is null"));
    }
}
