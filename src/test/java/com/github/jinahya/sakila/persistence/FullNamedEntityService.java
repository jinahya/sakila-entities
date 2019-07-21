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

/**
 * A service interface for entities implement {@link FullNamed}.
 *
 * @param <U> entity type parameter.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNamedEntityService<T extends EntityService<U>, U extends FullNamed> {

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    default T entityServiceType() {
        return (T) this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    default List<U> listMatchedByFirstNameSortedByLastNameIn(@NotNull final String firstName,
                                                             final boolean ascendingOrder,
                                                             @PositiveOrZero @Nullable final Integer firstResult,
                                                             @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    default List<U> listMatchedByLastNameSortedByFirstNameIn(@NotNull final String lastName,
                                                             final boolean ascendingOrder,
                                                             @PositiveOrZero @Nullable final Integer firstResult,
                                                             @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
