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

import org.slf4j.Logger;

import java.lang.reflect.Field;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Constants and utilities for {@link NamedAttribute}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class NamedAttributeTests {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Verifies all fields of specified class and its ancestors are properly named when they are annotated with {@link
     * NamedAttribute}.
     *
     * @param clazz the class to be verified.
     */
    static void verify(final Class<?> clazz) {
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
            verify(superclass);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    private NamedAttributeTests() {
        super();
    }
}
