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

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service for {@link Category} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
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
     * Finds the category whose {@link Category#ATTRIBUTE_NAME_NAME name} attribute matches to specified value. Note
     * that {@link Category#ATTRIBUTE_NAME_NAME name} attribute is not unique but all values in table are distinct to
     * each other.
     *
     * @param name the value for {@link Category#ATTRIBUTE_NAME_NAME name} attribute to match.
     * @return an optional of found; empty if not found.
     */
    @NotNull Optional<Category> findByName(@NotBlank final String name) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Category AS c WHERE c.name = :name");
            query.setParameter("name", name);
            try {
                return Optional.of((Category) query.getSingleResult()); // NonUniqueResultException
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typed = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name", Category.class);
            typed.setParameter("name", name);
            try {
                return Optional.of(typed.getSingleResult()); // NonUniqueResultException
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
            final Root<Category> from = criteria.from(Category.class);
            criteria.select(from);
            criteria.where(builder.equal(from.get(Category.ATTRIBUTE_NAME_NAME), name));
            final TypedQuery<Category> typed = entityManager().createQuery(criteria);
            try {
                return Optional.of(typed.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        final Root<Category> from = criteria.from(Category.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get(Category_.name), name));
        final TypedQuery<Category> typed = entityManager().createQuery(criteria);
        try {
            return Optional.of(typed.getSingleResult());
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    /**
     * Returns a list of categories sorted by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending order.
     *
     * @param firstResult the position of the first result to retrieve, numbered from {@code 0}; {@code null} for an
     *                    unspecified result.
     * @param maxResults  the maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of, optionally paged, category.
     */
    List<Category> listSortedByName(@PositiveOrZero @Nullable final Integer firstResult,
                                    @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Category AS c ORDER BY c.name ASC");
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Category> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typed = entityManager().createQuery(
                    "SELECT c FROM Category AS c ORDER BY c.name ASC", Category.class);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
            final Root<Category> from = criteria.from(Category.class);
            criteria.select(from);
            criteria.orderBy(builder.asc(from.get(Category.ATTRIBUTE_NAME_NAME)));
            final TypedQuery<Category> typed = entityManager().createQuery(criteria);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        final Root<Category> from = criteria.from(Category.class);
        criteria.select(from);
        final Order order = builder.asc(from.get(Category_.name));
        criteria.orderBy(order);
        final TypedQuery<Category> typed = entityManager().createQuery(criteria);
        ofNullable(firstResult).ifPresent(typed::setFirstResult);
        ofNullable(maxResults).ifPresent(typed::setMaxResults);
        return typed.getResultList();
    }
}
