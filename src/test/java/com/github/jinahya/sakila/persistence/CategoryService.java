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
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;
import java.util.stream.Stream;

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
     * Returns a stream of categorys ordered by {@link Category#ATTRIBUTE_NAME_NAME} attribute in the order indicated by
     * specified flag.
     *
     * @param ascendingOrder the flag for ordering; {@code true} for ascending order; {@code false} for descending
     *                       order.
     * @param firstResult    an optional value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     an optional value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally paged, categorys.
     */
    public Stream<Category> streamOrderedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                                @Positive final Integer maxResults) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Finds the category whose {@link Category#ATTRIBUTE_NAME_NAME} attribute matches to specified value.
     *
     * @param name the value of {@link Category#ATTRIBUTE_NAME_NAME} attribute to match.
     * @return an optional of found entity.
     */
    public Optional<Category> findByName(@NotNull final String name) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Category AS c WHERE c.name = :name");
            query.setParameter("name", name);
            try {
                return Optional.of((Category) query.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Category AS c WHERE c.name = :name", Category.class);
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
}
