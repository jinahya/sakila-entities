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

/**
 * An abstract class for service classes of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class BaseEntityService<T extends BaseEntity> extends EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to server for.
     */
    BaseEntityService(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}