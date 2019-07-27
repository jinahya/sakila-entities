package com.github.jinahya.sakila.persistence;

import jdk.internal.jline.internal.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

interface FullNamedService<T extends FullNamed> {

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByFirstName(@NotNull String firstName);

    @NotNull List<@NotNull T> listByFirstName(@NotNull String firstName, @PositiveOrZero @Nullable Integer firstResult,
                                              @Positive @Nullable final Integer maxResults);

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByLastName(@NotNull String lastName);

    @NotNull List<@NotNull T> listByLastName(@NotNull String lastName, @PositiveOrZero @Nullable Integer firstResult,
                                             @Positive @Nullable final Integer maxResults);
}
