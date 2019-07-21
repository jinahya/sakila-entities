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
import org.jetbrains.annotations.Nullable;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

/**
 * An abstract class for service classes of {@link BaseEntity}.
 *
 * @param <T> entity type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
abstract class BaseEntityService<T extends BaseEntity> extends EntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to server for.
     */
    BaseEntityService(final Class<T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the managed type of {@link #entityClass}.
     *
     * @return the managed type of {@link #entityClass}.
     */
    final ManagedType<T> managedType() {
        return entityManager()
                .getEntityManagerFactory()
                .getMetamodel()
                .managedType(entityClass);
    }

    /**
     * Returns the singular attribute of specified name and type.
     *
     * @param name the attribute name.
     * @param type the attribute type.
     * @param <A>  attribute type parameter
     * @return the singular attribute of specified name and type.
     * @see #managedType()
     * @see ManagedType#getSingularAttribute(String, Class)
     */
    final <A> SingularAttribute<? super T, A> singularAttribute(final String name, final Class<A> type) {
        return managedType().getSingularAttribute(name, type);
    }

    /**
     * Returns the singular attribute for {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute.
     *
     * @return the singular attribute for {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute.
     * @see #singularAttribute(String, Class)
     */
    final SingularAttribute<? super T, Integer> idAttribute() {
        return singularAttribute(BaseEntity.ATTRIBUTE_NAME_ID, Integer.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the entity whose {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute equals to specified value.
     *
     * @param id the value for {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute.
     * @return the entity identified by specified value; empty if not found.
     */
    public Optional<T> findById(final int id) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a list of entities of {@link #entityClass} sorted by {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute in
     * specified order.
     *
     * @param ascendingOrder the order to sort; {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    the position of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                       result.
     * @param maxResults     maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of entities.
     */
    public List<T> listSortedByIdIn(final boolean ascendingOrder,
                                    @PositiveOrZero @Nullable final Integer firstResult,
                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-19 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
