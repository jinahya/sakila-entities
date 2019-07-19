package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

interface FullNamedService<T extends FullNamed> {

    // -----------------------------------------------------------------------------------------------------------------
    default List<T> listByFirstNameSortedByLastNameInAscendingOrder(@NotNull final String firstName,
                                                                    @PositiveOrZero @Nullable final Integer firstResult,
                                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
