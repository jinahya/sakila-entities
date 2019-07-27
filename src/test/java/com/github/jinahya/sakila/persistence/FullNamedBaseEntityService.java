package com.github.jinahya.sakila.persistence;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

interface FullNamedBaseEntityService<T extends FullNamedBaseEntity> extends FullNamedService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedBaseEntity> @PositiveOrZero long countByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamedBaseEntity> @NotNull List<@NotNull T> listByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedBaseEntity> @PositiveOrZero long countByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamedBaseEntity> @NotNull List<@NotNull T> listByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
