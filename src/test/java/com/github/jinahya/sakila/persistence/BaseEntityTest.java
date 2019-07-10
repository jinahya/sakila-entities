package com.github.jinahya.sakila.persistence;

/**
 * A class for testing subclasses of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 */
public abstract class BaseEntityTest<T extends BaseEntity> extends EntityTest<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public BaseEntityTest(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
