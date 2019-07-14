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

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for service classes.
 *
 * @param <T> entity type parameter
 */
abstract class EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the class of the entity to serve.
     */
    EntityService(final Class<? extends T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager for access persistence context.
     *
     * @return an entity manager.
     */
    EntityManager entityManager() {
        // TODO: 2019-07-14 proxy!!!
        return entityManager;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class of entity to serve.
     */
    final Class<? extends T> entityClass;

    @Inject
    private EntityManager entityManager;
}
