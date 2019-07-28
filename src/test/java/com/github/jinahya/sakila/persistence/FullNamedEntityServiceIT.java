package com.github.jinahya.sakila.persistence;

abstract class FullNamedEntityServiceIT<T extends EntityService<U> & FullNamedEntityService<U>, U extends FullNamedEntity>
        extends EntityServiceIT<T, U> {

    // -----------------------------------------------------------------------------------------------------------------
    FullNamedEntityServiceIT(final Class<T> serviceClass, final Class<U> entityClass) {
        super(serviceClass, entityClass);
    }
}
