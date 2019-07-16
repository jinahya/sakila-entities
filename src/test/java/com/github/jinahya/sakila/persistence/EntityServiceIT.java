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

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

abstract class EntityServiceIT<T extends EntityService<U>, U> extends EntityIT<U> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A tag value for JPQL.
     */
    static final String TAG_JPQL = "jpql";

    /**
     * A tag value for Criteria-API.
     */
    static final String TAG_CRITERIA_API = "criteria-api";

    // -----------------------------------------------------------------------------------------------------------------
    static int firstResult(final Class<?> entityClass) {
        return current().nextInt(entityCountAsInt(entityClass));
    }

    static int maxResults(final Class<?> entityClass) {
        return current().nextInt(1, entityCountAsInt(entityClass)); // [1..entityCount())
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified service class and entity class.
     *
     * @param serviceClass the service class to test.
     * @param entityClass  the target entity class of the {@code serviceClass}.
     * @see #serviceClass
     * @see #entityClass
     */
    EntityServiceIT(final Class<? extends T> serviceClass, final Class<? extends U> entityClass) {
        super(entityClass);
        this.serviceClass = requireNonNull(serviceClass, "serviceClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an instance of {@link #serviceClass}.
     *
     * @return an instance of {@link #serviceClass}.
     */
    T serviceInstance() {
        return serviceInstance.select(serviceClass).get();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The service class to test.
     */
    final Class<? extends T> serviceClass;

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private transient Instance<EntityService> serviceInstance;
}
