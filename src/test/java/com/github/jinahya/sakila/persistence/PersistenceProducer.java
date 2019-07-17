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

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.util.Objects.requireNonNull;

@Slf4j
class PersistenceProducer {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the persistence unit. The value is {@value}.
     */
    private static final String PERSISTENCE_UNIT_NAME = "sakilaPU";

    /**
     * The entity manager factory.
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY
            = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Applies an entity manager to specified function and returns the result.
     *
     * @param function the function to be applied with an entity manager.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    static <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return requireNonNull(function, "function is null").apply(uncloseable(entityManager));
        } finally {
            entityManager.close();
        }
    }

    /**
     * Applies an entity manager, along with a second argument provided by specified supplier, to specified function and
     * returns the result.
     *
     * @param function the function to be applied.
     * @param supplier the supplier for the second argument.
     * @param <U>      second argument type parameter
     * @param <R>      result type parameter
     * @return the result of the function.
     */
    static <U, R> R applyEntityManager(final BiFunction<? super EntityManager, ? super U, ? extends R> function,
                                       final Supplier<? extends U> supplier) {
        return applyEntityManager(e -> function.apply(e, supplier.get()));
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    public EntityManager produceEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void disposeEntityManager(@Disposes final EntityManager entityManager) {
        entityManager.close();
    }
}
