package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A service for {@link Category}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CategoryService extends BaseEntityService<Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CategoryService() {
        super(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a stream of categorys ordered by {@link Category#ATTRIBUTE_NAME_NAME} attribute in the order indicated by
     * specified flag.
     *
     * @param ascendingOrder the flag for ordering; {@code true} for ascending order; {@code false} for descending
     *                       order.
     * @param firstResult    an optional value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     an optional value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally paged, categorys.
     */
    public Stream<Category> streamOrderedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                                @Positive final Integer maxResults) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Finds the category whose {@link Category#ATTRIBUTE_NAME_NAME} attribute matches to specified value.
     *
     * @param name the value of {@link Category#ATTRIBUTE_NAME_NAME} attribute to match.
     * @return an optional of found entity.
     */
    public Optional<Category> findByName(@NotNull final String name) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}