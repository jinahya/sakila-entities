package com.github.jinahya.sakila.persistence;

abstract class BaseEntityIT<T extends BaseEntity> extends EntityIT<T> {

    // -----------------------------------------------------------------------------------------------------------------
    BaseEntityIT(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
