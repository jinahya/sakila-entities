package com.github.jinahya.sakila.persistence;

abstract class BaseEntityService<T extends BaseEntity> extends EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    BaseEntityService(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
