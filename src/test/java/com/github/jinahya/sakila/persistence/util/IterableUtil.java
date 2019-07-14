package com.github.jinahya.sakila.persistence.util;

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
        boolean beforeFirst = true;
        T previous = null;
        for (final T current : iterable) {
            if (beforeFirst) {
                beforeFirst = false;
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
