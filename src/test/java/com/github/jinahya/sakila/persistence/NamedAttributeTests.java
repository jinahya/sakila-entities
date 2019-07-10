package com.github.jinahya.sakila.persistence;

import org.slf4j.Logger;

import java.lang.reflect.Field;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

final class NamedAttributeTests {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Logger logger = getLogger(lookup().lookupClass());

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Verifies all fields of specified class and its ancestors a properly named when they are annotated with {@link
     * NamedAttribute}.
     *
     * @param clazz the class to be verified.
     */
    public static void verify(final Class<?> clazz) {
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
            assertEquals(expected, actual, "field name mismatches; " + annotation + "; " + field);
        }
        final Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            verify(superclass);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private NamedAttributeTests() {
        super();
    }
}
