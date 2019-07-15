package com.github.jinahya.sakila.persistence;

import java.util.Comparator;
import java.util.Optional;

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
     * The name of the attribute of {@link FullName}. The value is {@value}.
     */
    String ATTRIBUTE_NAME_FULL_NAME = "fullName";

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingFirstNameNatural() {
        if (true) {
            return (Comparator<T>) (Comparator<?>) FullName.COMPARING_FIRST_NAME_NATURAL;
        }
        return comparing(v -> v.getFirstName().orElse(null));
    }

    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingFirstNameReverse() {
        return FullNameEmbedded.<T>comparingFirstNameNatural().reversed();
    }

    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingFirstName(final boolean natural) {
        return natural ? comparingFirstNameNatural() : comparingFirstNameReverse();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingLastNameNatural() {
        return comparing(v -> v.getLastName().orElse(null));
    }

    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingLastNameReverse() {
        return FullNameEmbedded.<T>comparingLastNameNatural().reversed();
    }

    static <T extends FullNameEmbedded> Comparator</*? super */T> comparingLastName(final boolean natural) {
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
     * @return an optional of {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute of {@link #ATTRIBUTE_NAME_FULL_NAME}
     * attribute; {@code null} if either {@link #ATTRIBUTE_NAME_FULL_NAME} attribute or {@code null} or {@link
     * FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute is {@code null}.
     */
    default Optional<String> getFirstName() {
        return ofNullable(getFullName()).map(FullName::getFirstName);
    }

    // TODO: 2019-07-15 remove!!! state changes regardless of parameter!!!
    @Deprecated // forRemoval = true
    default void setFirstName(final String firstName) {
        ofNullable(getFullName())
                .orElseGet(() -> {
                    setFullName(new FullName()); // TODO: 2019-07-15 not good!!!
                    return getFullName();
                })
                .setFirstName(firstName);
    }

    // -----------------------------------------------------------------------------------------------------------------
    default Optional<String> getLastName() {
        return ofNullable(getFullName()).map(FullName::getLastName);
    }

    // TODO: 2019-07-15 remove!!! state changes regardless of parameter!!!
    @Deprecated // forRemoval = true
    default void setLastName(final String lastName) {
        ofNullable(getFullName())
                .orElseGet(() -> {
                    setFullName(new FullName()); // TODO: 2019-07-15 not good!!!
                    return getFullName();
                })
                .setLastName(lastName);
    }

    // -----------------------------------------------------------------------------------------------------------------
    default Optional<String> toString(final FullName.Order order, final String delimiter) {
        return ofNullable(getFullName()).map(v -> v.toString(order, delimiter));
    }
}
