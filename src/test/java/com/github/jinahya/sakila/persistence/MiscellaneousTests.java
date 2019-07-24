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
import org.slf4j.Logger;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Constants and utilities for miscellaneous tests.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
final class MiscellaneousTests {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Asserts specified class is annotated with {@code @Slf4j}.
     *
     * @param clazz the class to be asserted.
     */
    static void assertAnnotatedWithSlf4j(@NotNull final Class<?> clazz) {
        final String message = "Annotate " + clazz + " with " + Slf4j.class;
        try {
            final Field logField = clazz.getDeclaredField("log");
            assertEquals(Logger.class, logField.getType(), message);
        } catch (final NoSuchFieldException nsfe) {
            fail(message);
        }
    }

    /**
     * Verifies all fields of specified class and its ancestors are properly named when they are annotated with {@link
     * NamedAttribute}.
     *
     * @param clazz the class to be verified.
     */
    static void verifyNamedAttributes(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz is null");
        }
        for (final Field field : clazz.getDeclaredFields()) {
            final NamedAttribute annotation = field.getAnnotation(NamedAttribute.class);
            if (annotation == null) {
                continue;
            }
            final String expected = annotation.value();
            final String actual = field.getName();
            assertEquals(expected, actual, "attribute name(" + expected + ") doesn't match to field name: " + field);
        }
        final Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            verifyNamedAttributes(superclass);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private MiscellaneousTests() {
        super();
    }
}
