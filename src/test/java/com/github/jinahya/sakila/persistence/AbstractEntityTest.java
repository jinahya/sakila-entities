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

import java.lang.reflect.Constructor;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for testing an entity class.
 *
 * @param <T> entity type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class AbstractEntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance for specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    AbstractEntityTest(final Class<? extends T> entityClass) {
        super();
        // TODO: 2019-07-10 verify the entityClass is annotated with @Entity
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     */
    protected T entityInstance() {
        try {
            final Constructor<? extends T> constructor = entityClass.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class to test.
     */
    protected final Class<? extends T> entityClass;
}
