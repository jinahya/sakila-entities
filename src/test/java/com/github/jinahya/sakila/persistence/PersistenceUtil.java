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

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.util.Objects.requireNonNull;

@Slf4j
final class PersistenceUtil {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Method ENTITY_MANAGER_CLOSE;

    static {
        try {
            ENTITY_MANAGER_CLOSE = EntityManager.class.getMethod("close");
        } catch (final NoSuchMethodException nsme) {
            throw new InstantiationError(nsme.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a proxy of specified entity manager whose {@link EntityManager#close()} method is prohibited.
     *
     * @param entityManager the entity manager.
     * @return an uncloseable proxy of {@code entityManager}.
     */
    static EntityManager uncloseable(final EntityManager entityManager) {
        if (false && entityManager.getClass().getName().startsWith("org.apache")) {
            return entityManager;
        }
        return (EntityManager) Proxy.newProxyInstance(
                requireNonNull(entityManager, "entityManager is null").getClass().getClassLoader(),
                new Class<?>[] {EntityManager.class},
                (proxy, method, args) -> {
                    if (ENTITY_MANAGER_CLOSE.equals(method)) {
                        throw new UnsupportedOperationException("not permitted");
                    }
                    return method.invoke(entityManager, args);
                }
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private PersistenceUtil() {
        super();
    }
}
