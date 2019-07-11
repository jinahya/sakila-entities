package com.github.jinahya.sakila.persistence;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Constructor;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for testing an entity class.
 *
 * @param <T> entity type parameter
 */
@ExtendWith({WeldJunit5Extension.class})
public abstract class EntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance for specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public EntityTest(final Class<? extends T> entityClass) {
        super();
        // TODO: 2019-07-10 verify the entityClass is annotated with @Entity
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Validates all field names of {@link #entityClass} and its superclasses against {@link NamedAttribute#value()}.
     */
    @Test
    void verifyNamedAttributes() {
        NamedAttributeTests.verify(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     */
    protected T entityInstance() {
        try {
            final Constructor<? extends T> constructor = entityClass.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<? extends T> entityClass;
}
