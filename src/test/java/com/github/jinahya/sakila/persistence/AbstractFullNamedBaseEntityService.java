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

import org.jetbrains.annotations.Nullable;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

abstract class AbstractFullNamedBaseEntityService<T extends FullNamedBaseEntity>
        extends BaseEntityService<T>
        implements FullNamedBaseEntityService<T> {

    // -----------------------------------------------------------------------------------------------------------------
    AbstractFullNamedBaseEntityService(final Class<T> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByFirstName(@NotNull final String firstName) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
            query.setParameter("firstName", firstName);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(e) " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName",
                    Long.class
            );
            typedQuery.setParameter("firstName", firstName);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<T> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(from));
        //final SingularAttribute<FullNamedBaseEntity, String> firstNameAttribute = FullNamedBaseEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(entityManager(), entityClass, FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(firstNameAttribute), firstName));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public @NotNull List<@NotNull T> listByFirstName(@NotNull final String firstName,
                                                     @PositiveOrZero @Nullable final Integer firstResult,
                                                     @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT e " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName"
                    + " ORDER BY e." + FullNamed.ATTRIBUTE_NAME_LAST_NAME + " ASC,"
                    + " e." + BaseEntity.ATTRIBUTE_NAME_ID + " ASC");
            query.setParameter("firstName", firstName);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<T> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<T> typedQuery = entityManager().createQuery(
                    "SELECT e " +
                    " FROM " + entityName(entityClass) + " AS e"
                    + " WHERE e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName"
                    + " ORDER BY e." + FullNamed.ATTRIBUTE_NAME_LAST_NAME + " ASC,"
                    + " e." + BaseEntity.ATTRIBUTE_NAME_ID + " ASC",
                    entityClass
            );
            typedQuery.setParameter("firstName", firstName);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            final Root<T> from = criteriaQuery.from(entityClass);
            criteriaQuery.select(from);
            criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            criteriaQuery.orderBy(
                    criteriaBuilder.asc(from.get(FullNamed.ATTRIBUTE_NAME_LAST_NAME)),
                    criteriaBuilder.asc(from.get(BaseEntity.ATTRIBUTE_NAME_ID))
            );
            final TypedQuery<T> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        final Root<T> from = criteriaQuery.from(entityClass);
        criteriaQuery.select(from);
        //final SingularAttribute<FullNamedBaseEntity, String> firstNameAttribute = FullNamedBaseEntity_.firstName;
        final SingularAttribute<? super T, String> firstNameAttribute
                = singularAttribute(FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class);
//        criteriaQuery.where(criteriaBuilder.equal(from.get(FullNamedBaseEntity_.firstName), firstName));
        criteriaQuery.where(criteriaBuilder.equal(
                from.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class)),
                firstName));
        criteriaQuery.orderBy(
                //criteriaBuilder.asc(from.get(FullNamedBaseEntity_.lastName)),
                criteriaBuilder.asc(from.get(singularAttribute(FullName.ATTRIBUTE_NAME_LAST_NAME, String.class))),
                //criteriaBuilder.asc(from.get(BaseEntity_.id)),
                criteriaBuilder.asc(from.get(idAttribute()))
        );
        final TypedQuery<T> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public @PositiveOrZero long countByLastName(@NotNull final String lastName) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public @NotNull List<@NotNull T> listByLastName(@NotNull final String lastName,
                                                    @PositiveOrZero @Nullable final Integer lastResult,
                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-28 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
