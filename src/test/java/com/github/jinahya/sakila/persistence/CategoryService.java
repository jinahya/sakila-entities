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

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service for {@link Category}.
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
     * Finds the category whose {@link Category#ATTRIBUTE_NAME_NAME} attribute matches to specified value.
     *
     * @param name the value for {@link Category#ATTRIBUTE_NAME_NAME} attribute to match.
     * @return an optional of found entity.
     */
    public Optional<Category> findByName(@NotNull final String name) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name");
            query.setParameter("name", name);
            try {
                return Optional.of((Category) query.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name",
                    Category.class
            );
            typedQuery.setParameter("name", name);
            try {
                return Optional.of(typedQuery.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            final Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get(Category.ATTRIBUTE_NAME_NAME), name));
            final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
            try {
                return Optional.of(typedQuery.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        final Root<Category> root = criteriaQuery.from(Category.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Category_.name), name));
        final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    /**
     * Returns a list of categories sorted by {@link Category#ATTRIBUTE_NAME_NAME} attribute in the order indicated by
     * specified flag.
     *
     * @param ascendingOrder the flag for ordering; {@code true} for ascending order; {@code false} for descending
     *                       order.
     * @param firstResult    the position of the first result to retrieve.
     * @param maxResults     the maximum number of results to retrieve.
     * @return a list of categories.
     */
    public List<Category> listSortedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                           @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager()
                    .createQuery("SELECT c FROM Category AS c ORDER BY c.name " + (ascendingOrder ? "ASC" : "DESC"));
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Category> resultList = query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Category AS c ORDER BY c.name " + (ascendingOrder ? "ASC" : "DESC"), Category.class);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            final Root<Category> from = criteriaQuery.from(Category.class);
            criteriaQuery.select(from);
            final Path<String> name = from.get(Category.ATTRIBUTE_NAME_NAME);
            final Order order = ascendingOrder ? criteriaBuilder.asc(name) : criteriaBuilder.desc(name);
            criteriaQuery.orderBy(order);
            final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        final Root<Category> from = criteriaQuery.from(Category.class);
        criteriaQuery.select(from);
        //final Path<String> name = from.get(Category_.name); // TODO: 2019-07-18 check with EclipseLink
        final Path<String> name = from.get(singularAttribute(Category.ATTRIBUTE_NAME_NAME, String.class));
        final Order order = ascendingOrder ? criteriaBuilder.asc(name) : criteriaBuilder.desc(name);
        criteriaQuery.orderBy(order);
        final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
