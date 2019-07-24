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

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * An abstract service class for entities implement {@link FullNamed}.
 *
 * @param <T> entity type parameter.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class FullNamedEntityService2<T extends FullNamed> extends EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to serve.
     */
    FullNamedEntityService2(final Class<T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Lists entities sorted by {@link FullNamed#ATTRIBUTE_NAME_LAST_NAME lastName} attributes.
     *
     * @param firstName      a value for {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME} to match; {@code null} for an
     *                       unspecified result.
     * @param ascendingOrder a flag for ordering direction; {@code true} for ascending order, {@code false} for
     *                       descending order.
     * @param firstResult    index of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                       result.
     * @param maxResults     maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of entities
     */
    // TODO: 2019-07-24 implement!!!
    abstract List<T> listSortedByLastNameIn(@Nullable final String firstName, final boolean ascendingOrder,
                                            @PositiveOrZero @Nullable final Integer firstResult,
                                            @Positive @Nullable final Integer maxResults);

    // TODO: 2019-07-24 implement!!!
    abstract List<T> listSortedByFirstNameIn(@Nullable final String lastName, final boolean ascendingOrder,
                                             @PositiveOrZero @Nullable final Integer firstResult,
                                             @Positive @Nullable final Integer maxResults);
}
