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

import javax.persistence.EntityManager;
import java.util.Comparator;

import static java.lang.Math.toIntExact;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A class for testing {@link Actor}.
 */
class ActorIT extends BaseEntityIT<Actor> {

    // -----------------------------------------------------------------------------------------------------------------
    public static final Comparator<Actor> a = Comparator.comparing(Actor::getId);

    // -----------------------------------------------------------------------------------------------------------------
    static Long COUNT;

    static long count(final EntityManager entityManager) {
        if (COUNT == null) {
            COUNT = entityManager.createQuery("SELECT COUNT(a) FROM Actor AS a", Long.class).getSingleResult();
        }
        return COUNT;
    }

    static Actor selectRandom(final EntityManager entityManager) {
        final int firstResult = current().nextInt(0, toIntExact(count(entityManager)));
        return entityManager
                .createQuery("SELECT a FROM Actor AS a", Actor.class)
                .setFirstResult(firstResult)
                .setMaxResults(1)
                .getResultList()
                .get(0);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorIT() {
        super(Actor.class);
    }
}
