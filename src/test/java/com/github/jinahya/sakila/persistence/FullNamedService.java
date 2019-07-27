package com.github.jinahya.sakila.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static com.github.jinahya.sakila.persistence.EntityService.entityName;
import static java.util.concurrent.ThreadLocalRandom.current;

interface FullNamedService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> @PositiveOrZero long countByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery("SELECT e FROM " + entityName(entityClass) + " AS e ")
        }
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamed> @NotNull List<@NotNull T> listByFirstName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String firstName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> @PositiveOrZero long countByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamed> @NotNull List<@NotNull T> listByLastName(
            @NotNull final EntityManager entityManager,
            @NotNull final Class<T> entityClass, @NotNull String lastName) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByFirstName(@NotNull String firstName);

    @NotNull List<@NotNull T> listByFirstName(@NotNull String firstName);

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long countByLastName(@NotNull String lastName);

    @NotNull List<@NotNull T> listByLastName(@NotNull String lastName);
}
