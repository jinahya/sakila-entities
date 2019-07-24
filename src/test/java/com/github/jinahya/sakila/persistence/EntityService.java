package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static com.github.jinahya.sakila.persistence.PersistenceUtil.uncloseable;
import static java.util.Objects.requireNonNull;

/**
 * An abstract class for entity service classes.
 *
 * @param <T> entity type parameter
 */
@Slf4j
abstract class EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    static Metamodel metamodel(@NotNull final EntityManager entityManager) {
        return entityManager.getEntityManagerFactory().getMetamodel();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <X> ManagedType<X> managedType(@NotNull final EntityManager entityManager,
                                          @NotNull final Class<X> entityClass) {
        return metamodel(entityManager).managedType(entityClass);
    }

    static <X> ManagedType<X> managedType(@NotNull final Class<X> entityClass) {
        return applyEntityManager(v -> managedType(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <X> EntityType<X> entityType(@NotNull final EntityManager entityManager,
                                        @NotNull final Class<X> entityClass) {
        return metamodel(entityManager).entity(entityClass);
    }

    static <X> EntityType<X> entityType(@NotNull final Class<X> entityClass) {
        return applyEntityManager(v -> entityType(v, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the entity name of the specified entity class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class whose name is returned.
     * @return the entity name of {@code entityClass}.
     * @see EntityType#getName()
     * @see #entityName(Class)
     */
    static String entityName(@NotNull final EntityManager entityManager, @NotNull final Class<?> entityClass) {
        return entityType(entityManager, entityClass).getName();
    }

    /**
     * Returns the entity name of the specified entity class.
     *
     * @param entityClass the entity class whose entity name is returned.
     * @return the entity name of {@code entityClass}.
     * @see #entityName(EntityManager, Class)
     */
    static String entityName(@NotNull final Class<?> entityClass) {
        return applyEntityManager(m -> entityName(m, entityClass));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the class of the entity to serve.
     */
    EntityService(final Class<T> entityClass) {
        super();
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the total number of entities of {@link #entityClass} in database.
     *
     * @return the total number of entities.
     */
    public long count() {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

//    /**
//     * Returns the managed type of {@link #entityClass}.
//     *
//     * @return the managed type of {@link #entityClass}.
//     */
//    final @NotNull ManagedType<T> managedType() {
//        return managedType(entityManager(), entityClass);
//    }

//    final @NotNull EntityType<T> entityType() {
//        return entityType(entityManager(), entityClass);
//    }

//    /**
//     * Returns the entity name of the {@link #entityClass}.
//     *
//     * @return the name of the {@code #entityClass}.
//     * @see #entityName(Class)
//     */
//    final @NotNull String entityName() {
//        return entityName(entityManager(), entityClass);
//    }

//    /**
//     * Returns the singular attribute of specified name and type.
//     *
//     * @param name the attribute name.
//     * @param type the attribute type.
//     * @param <A>  attribute type parameter
//     * @return the singular attribute of specified name and type.
//     * @see #managedType()
//     * @see ManagedType#getSingularAttribute(String, Class)
//     */
//    final @NotNull <A> SingularAttribute<? super T, A> singularAttribute(@NotNull final String name,
//                                                                         @NotNull final Class<A> type) {
//        return managedType().getSingularAttribute(name, type);
//    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns an entity manager for accessing persistence context.
     *
     * @return an entity manager.
     */
    final @NotNull EntityManager entityManager() {
        if (entityManagerUncloseable == null) {
            entityManagerUncloseable = uncloseable(entityManager);
        }
        return entityManagerUncloseable;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The class of entity to serve.
     */
    final Class<T> entityClass;

    @Inject
    private EntityManager entityManager;

    private transient EntityManager entityManagerUncloseable;
}
