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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

abstract class AbstractFullNamedEntityService<T extends FullNamedEntity>
        extends EntityService<T>
        implements FullNamedEntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    AbstractFullNamedEntityService(final Class<T> entityClass) {
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
