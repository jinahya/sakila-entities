package com.github.jinahya.sakila.persistence.util;

import java.util.Comparator;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.fail;

public final class AssertionsUtil {

    // -----------------------------------------------------------------------------------------------------------------
    public static <T> void assertSorted(final Iterable<? extends T> iterable, final Comparator<? super T> comparator) {
        if (!IterableUtil.isSorted(iterable, comparator)) {
            fail(() -> format("%1$s is not sorted in terms of %2$s", iterable, comparator));
        }
    }

    public static <T extends Comparable<? super T>> void assertSortedInNaturalOrder(final Iterable<T> iterable) {
        if (!IterableUtil.isSortedInNaturalOrder(iterable)) {
            fail(() -> format("%1$s is not sorted in natural order", iterable));
        }
    }

    public static <T extends Comparable<? super T>> void assertSortedInReverseOrder(final Iterable<T> iterable) {
        if (!IterableUtil.isSortedInNaturalOrder(iterable)) {
            fail(() -> format("%1$s is not sorted in reverse order", iterable));
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AssertionsUtil() {
        super();
    }
}
