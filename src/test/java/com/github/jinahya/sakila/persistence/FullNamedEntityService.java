package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

interface FullNamedEntityService<T extends FullNamedEntity> extends FullNamedService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedEntity> @PositiveOrZero long countByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamedEntity> @NotNull List<@NotNull T> listByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedEntity> @PositiveOrZero long countByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamedEntity> @NotNull List<@NotNull T> listByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    default @PositiveOrZero long countByFirstName(@NotNull final String firstName) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return countByFirstName(entityManager, entityClass, firstName);
    }

    @Override
    default @NotNull List<@NotNull T> listByFirstName(@NotNull final String firstName,
                                                      @PositiveOrZero @Nullable Integer firstResult,
                                                      @Positive @Nullable final Integer maxResults) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return listByFirstName(entityManager, entityClass, firstName, firstResult, maxResults);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    default @PositiveOrZero long countByLastName(@NotNull final String lastName) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return countByLastName(entityManager, entityClass, lastName);
    }

    @Override
    default @NotNull List<@NotNull T> listByLastName(@NotNull final String lastName,
                                                     @PositiveOrZero @Nullable Integer lastResult,
                                                     @Positive @Nullable final Integer maxResults) {
        final EntityManager entityManager = EntityService.entityManager((EntityService<?>) this);
        @SuppressWarnings({"unchecked"})
        final Class<T> entityClass = (Class<T>) EntityService.entityClass((EntityService<?>) this);
        return listByLastName(entityManager, entityClass, lastName, lastResult, maxResults);
    }
}
