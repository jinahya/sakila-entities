package com.github.jinahya.sakila.persistence;

public abstract class BaseEntityParameterResolver<T extends BaseEntity> extends EntityParameterResolver<T> {

    public BaseEntityParameterResolver(final Class<? extends T> entityClass) {
        super(entityClass);
    }
}
