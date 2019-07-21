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

import org.assertj.core.api.AbstractAssert;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An assert for  {@link BaseEntity}.
 *
 * @param <T> assert type parameter
 * @param <U> full named type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class BaseEntityAssert<T extends BaseEntityAssert<T, U>, U extends BaseEntity> extends AbstractAssert<T, U> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with given actual value.
     *
     * @param actual the actual value.
     */
    BaseEntityAssert(final U actual) {
        super(actual, BaseEntityAssert.class);
    }

    // -------------------------------------------------------------------------------------------------------------- id

    /**
     * Asserts that the {@link #actual} is not {@code null} and its current value of {@link BaseEntity#ATTRIBUTE_NAME_ID
     * id} attribute is equals to specified value.
     *
     * @param id the value for {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute to compare.
     * @return this assert.
     */
    @SuppressWarnings({"unchecked"})
    public T hasId(final int id) {
        isNotNull().satisfies(v -> assertThat(v.getId()).isNotNull().isEqualTo(id));
        return (T) this;
    }

    /**
     * Asserts that the {@link #actual} is not {@code null} and its current value of {@link BaseEntity#ATTRIBUTE_NAME_ID
     * id} attribute equals to that of specified entity.
     *
     * @param baseEntity the base entity whose {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute is compared to.
     * @return this assert.
     */
    public T hasSameIdAs(@NotNull final BaseEntity baseEntity) {
        return hasId(baseEntity.getId());
    }
}
