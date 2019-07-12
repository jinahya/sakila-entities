package com.github.jinahya.sakila.persistence;

abstract class BaseEntityServiceIT<T extends BaseEntityService<U>, U extends BaseEntity> extends EntityServiceIT {

    // -----------------------------------------------------------------------------------------------------------------
    BaseEntityServiceIT(final Class<? extends T> serviceClass, final Class<? extends U> entityClass) {
        super(serviceClass, entityClass);
    }
}
