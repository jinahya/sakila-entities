package com.github.jinahya.sakila.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static java.util.Objects.requireNonNull;

abstract class EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    EntityService(final Class<? extends T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<? extends T> entityClass;

    @Inject
    EntityManager entityManager;
}
