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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for testing an entity class.
 *
 * @param <T> entity type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
public abstract class EntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T> T setField(final Class<? extends T> clazz, final T object, final String name, final Object value) {
        try {
            final Field field = clazz.getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(object, value);
            return object;
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    static <T> Object getField(final Class<? extends T> clazz, final T object, final String name) {
        try {
            final Field field = clazz.getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance for specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public EntityTest(final Class<T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Validates all field names of {@link #entityClass} and its superclasses against {@link NamedAttribute#value()}.
     */
    @Test
    void verifyNamedAttributes() {
        NamedAttributeTests.verify(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     */
    final T entityInstance() {
        try {
            final Constructor<T> constructor = entityClass.getDeclaredConstructor();
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
     * The class of the entity to test.
     */
    final Class<T> entityClass;
}
