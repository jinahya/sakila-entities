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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service for {@link Category} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CategoryService extends BaseEntityService<Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CategoryService() {
        super(Category.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Lists categories whose {@link Category#ATTRIBUTE_NAME_NAME name} attribute matches to specified value sorted by
     * {@link BaseEntity#ATTRIBUTE_NAME_ID id} attribute in ascending order.
     *
     * @param name the value for {@link Category#ATTRIBUTE_NAME_NAME name} attribute to match.
     * @return a list of categories.
     */
    public List<Category> listByName(@NotNull final String name) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name ORDER BY c.id ASC");
            query.setParameter("name", name);
            @SuppressWarnings({"unchecked"})
            final List<Category> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name ORDER BY c.id ASC",
                    Category.class
            );
            typedQuery.setParameter("name", name);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            final Root<Category> from = criteriaQuery.from(Category.class);
            criteriaQuery.select(from);
            final Path<String> namePath = from.get(Category.ATTRIBUTE_NAME_NAME);
            criteriaQuery.where(criteriaBuilder.equal(namePath, name));
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(BaseEntity.ATTRIBUTE_NAME_ID)));
            final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        final Root<Category> from = criteriaQuery.from(Category.class);
        criteriaQuery.select(from);
        //final SingularAttribute<Category, String> nameAttribute = Category_.name;
        final SingularAttribute<? super Category, String> nameAttribute
                = singularAttribute(Category.class, Category.ATTRIBUTE_NAME_NAME, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(nameAttribute), name));
        final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    /**
     * Returns a list of categories sorted by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending order.
     *
     * @param firstResult the position of the first result to retrieve.
     * @param maxResults  the maximum number of results to retrieve.
     * @return a stream of, optionally paged, categorys.
     */
    public List<Category> listSortedByName(@PositiveOrZero @Nullable final Integer firstResult,
                                           @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Category AS c ORDER BY c.name ASC");
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Category> resultList = query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Category AS c ORDER BY c.name ASC",
                    Category.class);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            final Root<Category> from = criteriaQuery.from(Category.class);
            criteriaQuery.select(from);
            final Path<String> namePath = from.get(Category.ATTRIBUTE_NAME_NAME);
            criteriaQuery.orderBy(criteriaBuilder.asc(namePath));
            final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        final Root<Category> from = criteriaQuery.from(Category.class);
        criteriaQuery.select(from);
        //final SingularAttribute<Category, String> nameAttribute = Category_.name;
        final SingularAttribute<? super Category, String> nameAttribute
                = singularAttribute(Category.class, Category.ATTRIBUTE_NAME_NAME, String.class);
        final Path<String> namePath = from.get(nameAttribute);
        final Order order = criteriaBuilder.asc(namePath);
        criteriaQuery.orderBy(order);
        final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
