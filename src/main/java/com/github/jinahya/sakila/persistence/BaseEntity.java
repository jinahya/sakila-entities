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

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * An abstract base class for entity classes.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
public abstract class BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The entity attribute name for {@code ..._id} columns. The value is {@value}.
     */
    public static final String ATTRIBUTE_NAME_ID = "id";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The database column name for {@link #ATTRIBUTE_NAME_LAST_UPDATE} attribute. The value is {@value}.
     */
    public static final String COLUMN_NAME_LAST_UPDATE = "last_update";

    /**
     * The entity attribute name for {@link #COLUMN_NAME_LAST_UPDATE} column.
     */
    public static final String ATTRIBUTE_NAME_LAST_UPDATE = "lastUpdate";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A comparator for comparing entities with {@link #ATTRIBUTE_NAME_ID} attribute.
     */
    public static final Comparator<BaseEntity> COMPARING_ID = comparing(BaseEntity::getId);

    public static Comparator<BaseEntity> comparingId(final boolean natural) {
        return natural ? COMPARING_ID : COMPARING_ID.reversed();
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends BaseEntity> Stream<T> select(
            final EntityManager entityManager, final Class<T> entityClass,
            final BiFunction<CriteriaBuilder, Root<T>, Collection<? extends Predicate>> predicatesFunction,
            final UnaryOperator<TypedQuery<T>> queryOperator) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<T> root = criteriaQuery.from(entityClass);
        final Collection<? extends Predicate> predicates = predicatesFunction.apply(criteriaBuilder, root);
        if (!predicates.isEmpty()) {
            criteriaQuery.where((Predicate[]) predicates.toArray());
        }
        final TypedQuery<T> typedQuery = queryOperator.apply(entityManager.createQuery(criteriaQuery));
        return typedQuery.getResultStream();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + "{"
               + "id=" + id
               + ",lastUpdate=" + lastUpdate
               + "}";
    }

    // TODO: 2019-07-12 remove!!!
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    // TODO: 2019-07-12 remove!!!
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // -------------------------------------------------------------------------------------------------------------- id

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_ID} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_ID} attribute.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_ID} attribute specified value.
     *
     * @param id new value for {@value #ATTRIBUTE_NAME_ID} attribute.
     */
    void setId(final Integer id) {
        this.id = id;
    }

    // ------------------------------------------------------------------------------------------------------ lastUpdate

    /**
     * Returns the current value of {@link #ATTRIBUTE_NAME_LAST_UPDATE} attribute.
     *
     * @return the current value of {@link #ATTRIBUTE_NAME_LAST_UPDATE} attribute.
     */
    public Date getLastUpdate() {
        // TODO: 2019-07-12 copy!!!
        return lastUpdate;
    }

    void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ..._id ...INT PK NN UN AI
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NamedAttribute(ATTRIBUTE_NAME_ID)
    private Integer id;

    // last_update TIMESTAMP NN CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    // !Payment !Customer
    //@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = COLUMN_NAME_LAST_UPDATE, nullable = /* ??? */ false, insertable = false, updatable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_UPDATE)
    private Date lastUpdate;
}
