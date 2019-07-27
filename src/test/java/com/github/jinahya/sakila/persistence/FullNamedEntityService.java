package com.github.jinahya.sakila.persistence;

import jdk.internal.jline.internal.Nullable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
            @NotNull final Class<T> entityClass, @NotNull String lastName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
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
        final EntityManager entityManager;
        {
            try {
                final Method entityManagerMethod = EntityService.class.getDeclaredMethod("entityManager");
                if (!entityManagerMethod.isAccessible()) {
                    entityManagerMethod.setAccessible(true);
                }
                entityManager = (EntityManager) entityManagerMethod.invoke(this);
            } catch (final ReflectiveOperationException roe) {
                throw new RuntimeException(roe);
            }
        }
        final Class<T> entityClass;
        {
            try {
                final Field entityClassField = EntityService.class.getDeclaredField("entityClass");
                if (!entityClassField.isAccessible()) {
                    entityClassField.setAccessible(true);
                }
                @SuppressWarnings({"unchecked"})
                final Class<T> entityClass_ = (Class<T>) entityClassField.get(this);
                entityClass = entityClass_;
            } catch (final ReflectiveOperationException roe) {
                throw new RuntimeException(roe);
            }
        }
        return countByFirstName(entityManager, entityClass, firstName);
    }
}
