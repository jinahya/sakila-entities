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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class for testing subclasses of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 */
public abstract class BaseEntityIT<T extends BaseEntity> extends EntityIT<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends BaseEntity> T of(final Class<? extends T> clazz, final Integer id) {
        return of(
                () -> {
                    try {
                        final Constructor<? extends T> constructor = clazz.getDeclaredConstructor();
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }
                        final T instance = constructor.newInstance();
                        instance.setId(id);
                        return instance;
                    } catch (final ReflectiveOperationException roe) {
                        throw new RuntimeException(roe);
                    }
                },
                id
        );
    }

    static <T extends BaseEntity> T of(final Supplier<? extends T> supplier, final Integer id) {
        final T instance = supplier.get();
        instance.setId(id);
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected static <T extends BaseEntity> Predicate<? super T> distinctById() {
        final Map<Integer, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(t.getId(), Boolean.TRUE) == null;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public BaseEntityIT(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
