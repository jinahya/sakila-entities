package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * A service interface for base entities implement {@link FullNamed}.
 *
 * @param <U> entity type parameter.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNamedBaseEntityService<T extends BaseEntityService<U>, U extends BaseEntity & FullNamed>
        extends FullNamedEntityService<T, U> {

    // -----------------------------------------------------------------------------------------------------------------
    default List<U> listMatchedByFullNameSortedByIdIn(
            @NotNull final String firstName, @NotNull final String lastName, final boolean ascendingOrder,
            @PositiveOrZero @Nullable final Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
