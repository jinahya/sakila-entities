package com.github.jinahya.sakila.persistence;

import static java.util.Objects.requireNonNull;

/**
 * An abstract class for testing an entity class.
 *
 * @param <T> entity type parameter
 */
abstract class AbstractEntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance for specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    AbstractEntityTest(final Class<? extends T> entityClass) {
        super();
        // TODO: 2019-07-10 verify the entityClass is annotated with @Entity
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<? extends T> entityClass;
}
