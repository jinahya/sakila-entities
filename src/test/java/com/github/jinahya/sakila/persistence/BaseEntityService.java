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

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * An abstract class for service classes of {@link BaseEntity}.
 *
 * @param <EntityType> entity type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class BaseEntityService<EntityType extends BaseEntity> extends EntityService<EntityType> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified entity class.
     *
     * @param entityClass the entity class to server for.
     */
    BaseEntityService(final Class<EntityType> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the managed type of {@link #entityClass}.
     *
     * @return the managed type of {@link #entityClass}.
     */
    final ManagedType<EntityType> managedType() {
        return entityManager()
                .getEntityManagerFactory()
                .getMetamodel()
                .managedType(entityClass);
    }

    final <AttributeType> SingularAttribute<? super EntityType, AttributeType> singularAttribute(
            final String name, final Class<AttributeType> type) {
        return managedType().getSingularAttribute(name, type);
    }

    final SingularAttribute<? super EntityType, Integer> idAttribute() {
        return singularAttribute(BaseEntity.ATTRIBUTE_NAME_ID, Integer.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public List<EntityType> listSortedById(final boolean ascendingOrder, final Integer firstResult,
                                           final Integer maxResults) {
        final String entityName = entityName();
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT e"
                    + " FROM " + entityName() + " AS e"
                    + " ORDER BY e." + BaseEntity.ATTRIBUTE_NAME_ID + " " + (ascendingOrder ? "ASC" : "DESC"));
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<EntityType> resultList = (List<EntityType>) query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final TypedQuery<EntityType> typedQuery = entityManager().createQuery(
                    "SELECT e"
                    + " FROM " + entityName + " AS e"
                    + " ORDER BY e." + BaseEntity.ATTRIBUTE_NAME_ID + " " + (ascendingOrder ? "ASC" : "DESC"),
                    entityClass);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<EntityType> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            final Root<EntityType> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(from);
            final Path<Integer> id = from.get(BaseEntity.ATTRIBUTE_NAME_ID); // attribute name
            criteriaQuery.orderBy(ascendingOrder ? criteriaBuilder.asc(id) : criteriaBuilder.desc(id));
            final TypedQuery<EntityType> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<EntityType> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<EntityType> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(from);
        //final Path<Integer> id = from.get(BaseEntity_.id); // metamodel attribute // doesn't work with EclipseLink
        final Path<Integer> id = from.get(idAttribute());
        criteriaQuery.orderBy(ascendingOrder ? criteriaBuilder.asc(id) : criteriaBuilder.desc(id));
        final TypedQuery<EntityType> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
