package com.github.jinahya.sakila.persistence.util;

import java.util.Comparator;

public final class IterableUtil {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Checks whether specified iterable is sorted in terms of specified comparator.
     *
     * @param iterable   the iterable to be checked.
     * @param comparator the comparator to compare elements in {@code iterable}.
     * @param <T>        element type parameter
     * @return {@code true} if {@code iterable} is sorted; {@code false} otherwise.
     */
    public static <T> boolean isSorted(final Iterable<? extends T> iterable, final Comparator<? super T> comparator) {
        boolean beforeTheFirst = true;
        T previous = null;
        for (final T current : iterable) {
            if (beforeTheFirst) {
                beforeTheFirst = false;
                previous = current;
                continue;
            }
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    /**
     * Checks whether specified iterable of comparable elements is sorted in natural order.
     *
     * @param iterable the iterable to be checked.
     * @param <T>      element type parameter
     * @return {@code true} if {@code iterable} is sorted in natural order; {@code false} otherwise.
     * @see Comparator#naturalOrder()
     */
    public static <T extends Comparable<? super T>> boolean isSortedInNaturalOrder(
            final Iterable<? extends T> iterable) {
        return isSorted(iterable, Comparator.naturalOrder());
    }

    /**
     * Checks whether specified iterable of comparable elements is sorted in reverse order.
     *
     * @param iterable the iterable to be checked.
     * @param <T>      element type parameter
     * @return {@code true} if {@code iterable} is sorted in reverse order; {@code false} otherwise.
     * @see Comparator#reverseOrder()
     */
    public static <T extends Comparable<? super T>> boolean isSortedInReverseOrder(
            final Iterable<? extends T> iterable) {
        return isSorted(iterable, Comparator.reverseOrder());
    }

    // -----------------------------------------------------------------------------------------------------------------
    private IterableUtil() {
        super();
    }
}
