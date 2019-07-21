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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An interface for asserting {@link FullNamed}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
interface FullNamedAssert<T extends AbstractAssert<T, U> & FullNamedAssert<T, U>, U extends FullNamed> {

    // ------------------------------------------------------------------------------------------------------- firstName

    /**
     * Asserts the {@code actual}'s {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute equals to specified
     * value.
     *
     * @param expectedFirstName the value for {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute to match.
     * @return this assert.
     */
    @SuppressWarnings({"unchecked"})
    default T hasFirstName(@NotNull final String expectedFirstName) {
        assertThat(actual())
                .isNotNull()
                .satisfies(v -> assertThat(v.getFirstName()).isNotNull().isEqualTo(expectedFirstName));
        return (T) this;
    }

    /**
     * Asserts the {@code actual} has the same {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} with specified
     * entity.
     *
     * @param whoseFirstNameExpected the entity whose {@link FullNamed#ATTRIBUTE_NAME_FIRST_NAME firstName} is used to
     *                               match.
     * @return this assert.
     */
    default T hasSameFirstNameAs(@NotNull final FullNamed whoseFirstNameExpected) {
        return hasFirstName(whoseFirstNameExpected.getFirstName());
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    @SuppressWarnings({"unchecked"})
    default T hasLastName(@NotNull final String expectedLastName) {
        assertThat(actual())
                .isNotNull()
                .satisfies(v -> assertThat(v.getLastName()).isNotNull().isEqualTo(expectedLastName));
        return (T) this;
    }

    default T hasSameLastNameAs(@NotNull final FullNamed whoseLastNameExpected) {
        return hasLastName(whoseLastNameExpected.getLastName());
    }

    // -----------------------------------------------------------------------------------------------------------------
    default FullNamedAssert hasSameFullNameAs(@NotNull final FullNamed whoseFullNameExpected) {
        return hasSameFirstNameAs(whoseFullNameExpected).hasSameLastNameAs(whoseFullNameExpected);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({"unchecked"})
    default U actual() {
        final Class<?> clazz = getClass();
        if (!AbstractAssert.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("class(" + clazz + ") is not assignable to " + AbstractAssert.class);
        }
        try {
            final Field field = AbstractAssert.class.getDeclaredField("actual");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return (U) field.get(this);
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }
}
