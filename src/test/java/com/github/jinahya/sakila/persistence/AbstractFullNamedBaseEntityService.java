package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

abstract class AbstractFullNamedBaseEntityService<T extends FullNamedBaseEntity>
        extends BaseEntityService<T>
        implements FullNamedBaseEntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    AbstractFullNamedBaseEntityService(final Class<T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByFirstName(@NotNull final String firstName) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public @NotNull List<@NotNull T> listByFirstName(@NotNull final String firstName,
                                                     @PositiveOrZero @Nullable final Integer firstResult,
                                                     @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByLastName(@NotNull final String lastName) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public @NotNull List<@NotNull T> listByLastName(@NotNull final String lastName,
                                                    @PositiveOrZero @Nullable final Integer lastResult,
                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
