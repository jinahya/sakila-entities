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

/**
 * A service interface for base entities implement {@link FullNamed}.
 *
 * @param <T> entity type parameter.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNamedBaseEntityService<T extends BaseEntity & FullNamed> extends FullNamedEntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> List<T> listSortedByLastNameIn(
            @NotNull final Class<T> entityClass, @NotNull final EntityManager entityManager,
            @Nullable final String firstName, final boolean ascendingOrder,
            @PositiveOrZero @Nullable final Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-24 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    static <T extends FullNamed> List<T> listSortedByFirstNameIn(
            @NotNull final Class<T> entityClass, @NotNull final EntityManager entityManager,
            @Nullable final String lastName, final boolean ascendingOrder,
            @PositiveOrZero @Nullable final Integer firstResult, @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-24 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    default List<T> listSortedByLastNameIn(@Nullable final String firstName, final boolean ascendingOrder,
                                           @PositiveOrZero @Nullable final Integer firstResult,
                                           @Positive @Nullable final Integer maxResults) {
        if (!(this instanceof EntityService)) {
            throw new IllegalStateException("this instance is not an instance of " + EntityService.class);
        }
        final Class<T> entityClass = ((EntityService<T>) this).entityClass;
        final EntityManager entityManager = ((EntityService<?>) this).entityManager();
        return listSortedByLastNameIn(entityClass, entityManager, firstName, ascendingOrder, firstResult, maxResults);
    }

    @SuppressWarnings({"unchecked"})
    default List<T> listSortedByFirstNameIn(@Nullable final String lastName, final boolean ascendingOrder,
                                            @PositiveOrZero @Nullable final Integer firstResult,
                                            @Positive @Nullable final Integer maxResults) {
        if (!(this instanceof EntityService)) {
            throw new IllegalStateException("this instance is not an instance of " + EntityService.class);
        }
        final Class<T> entityClass = ((EntityService<T>) this).entityClass;
        final EntityManager entityManager = ((EntityService<?>) this).entityManager();
        return listSortedByFirstNameIn(entityClass, entityManager, lastName, ascendingOrder, firstResult, maxResults);
    }
}
