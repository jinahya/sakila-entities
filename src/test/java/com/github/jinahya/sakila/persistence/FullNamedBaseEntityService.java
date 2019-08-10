package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
            @NotNull final Class<T> entityClass, @NotNull String firstName,
            @PositiveOrZero @Nullable Integer firstResult, @Positive @Nullable final Integer maxResults) {
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
