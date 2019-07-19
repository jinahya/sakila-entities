package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * A service interface for entities implement {@link FullNamed}.
 *
 * @param <U> entity type parameter.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNamedEntityService<T extends EntityService<U>, U extends FullNamed> {

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    default T entityServiceType() {
        return (T) this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    default List<U> listMatchedByFirstNameSortedByLastNameIn(@NotNull final String firstName,
                                                             final boolean ascendingOrder,
                                                             @PositiveOrZero @Nullable final Integer firstResult,
                                                             @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    default List<U> listMatchedByLastNameSortedByFirstNameIn(@NotNull final String lastName,
                                                             final boolean ascendingOrder,
                                                             @PositiveOrZero @Nullable final Integer firstResult,
                                                             @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
