package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.extension.ParameterResolver;

import static java.util.Objects.requireNonNull;

public abstract class EntityParameterResolver<T> implements ParameterResolver {

    // -----------------------------------------------------------------------------------------------------------------
    public EntityParameterResolver(final Class<? extends T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<? extends T> entityClass;
}
