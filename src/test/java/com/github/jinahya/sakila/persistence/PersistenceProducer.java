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

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class PersistenceProducer {

    // -----------------------------------------------------------------------------------------------------------------
    private static final String PERSISTENCE_UNIT_NAME = "sakilaPU";

    private static final EntityManagerFactory PERSISTENCE_UNIT
            = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    @PersistenceContext
    public EntityManager produceEntityManager() {
        return PERSISTENCE_UNIT.createEntityManager();
    }

    public void disposeEntityManager(@Disposes final EntityManager entityManager) {
        entityManager.close();
    }
}
