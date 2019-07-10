package com.github.jinahya.sakila.persistence;

/**
 * A class for testing subclasses of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 */
public abstract class BaseEntityIT<T extends BaseEntity> extends EntityIT<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to test.
     */
    public BaseEntityIT(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
