package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

interface FullNamedService<T extends FullNamed> {

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByFirstName(@NotNull String firstName);

    @NotNull List<@NotNull T> listByFirstName(@NotNull String firstName);

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByLastName(@NotNull String lastName);

    @NotNull List<@NotNull T> listByLastName(@NotNull String lastName);
}
