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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * A class for testing subclasses of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 */
abstract class BaseEntityTest<T extends BaseEntity> extends EntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    BaseEntityTest(final Class<T> entityClass) {
        super(entityClass);
    }

    // ------------------------------------------------------------------------------------------------------ lastUpdate

    /**
     * Asserts the default value of {@link BaseEntity#ATTRIBUTE_NAME_ID} attribute is {@code null}.
     */
    @Test
    void assertDefaultValueOfLastUpdateIsNull() {
        assertNull(entityInstance().getLastUpdate());
    }

    /**
     * Asserts {@link BaseEntity#getLastUpdate()} returns a copy of the current value of {@link
     * BaseEntity#ATTRIBUTE_NAME_LAST_UPDATE} attribute.
     */
    // TODO: 2019-07-12 enable, assert fails, fix it, and assert passes.
    @Disabled
    @Test
    void assertGetLastUpdateReturnsCopy() {
        final T entityInstance = entityInstance();
        final Date unexpected = new Date();
        entityInstance.setLastUpdate(unexpected);
        final Date actual = entityInstance.getLastUpdate();
        assertNotSame(unexpected, actual);
    }
}
