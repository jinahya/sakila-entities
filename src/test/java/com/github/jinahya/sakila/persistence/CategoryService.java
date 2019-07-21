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

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * A service for {@link Category} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CategoryService extends BaseEntityService<Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CategoryService() {
        super(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Lists categories whose {@link Category#ATTRIBUTE_NAME_NAME name} attribute matches to specified value sorted by
     * {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute in ascending order.
     *
     * @param name the value for {@link Category#ATTRIBUTE_NAME_NAME name} attribute to match.
     * @return a list of categories.
     */
    public List<Category> listByName(@NotNull final String name) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of categories sorted by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending order.
     *
     * @param firstResult the position of the first result to retrieve.
     * @param maxResults  the maximum number of results to retrieve.
     * @return a stream of, optionally paged, categorys.
     */
    public List<Category> listSortedByName(@PositiveOrZero @Nullable final Integer firstResult,
                                           @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
