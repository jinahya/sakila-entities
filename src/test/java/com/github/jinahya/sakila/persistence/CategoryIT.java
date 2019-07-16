package com.github.jinahya.sakila.persistence;

import org.assertj.core.api.Condition;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSortedSet;

class CategoryIT extends BaseEntityIT<Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The total number of {@link Category}.
     */
    static final int CATEGORY_COUNT = entityCountAsInt(Category.class);

    /**
     * Returns a random instance of {@link Category}.
     *
     * @return a random instance of {@link Category}.
     */
    static Category randomCategory() {
        return randomEntity(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A sorted set of values of {@link Category#ATTRIBUTE_NAME_NAME} attribute.
     */
    static final SortedSet<String> NAMES;

    static {
        try {
            NAMES = unmodifiableSortedSet(applyResourceScanner("category_set_name.txt", s -> {
                final SortedSet<String> set = new TreeSet<>();
                while (s.hasNext()) {
                    set.add(s.next());
                }
                return set;
            }));
        } catch (final IOException ioe) {
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static final Condition<String> A_NAME_IN_DATABASE = new Condition<>(NAMES::contains, "a name is in database");

    static final Condition<Category> A_CATEGORY_WHOSE_NAME_IS_IN_DATABASE = new Condition<>(
            l -> A_NAME_IN_DATABASE.matches(l.getName()), "a category whose name is in database");

    // -----------------------------------------------------------------------------------------------------------------
    CategoryIT() {
        super(Category.class);
    }
}
