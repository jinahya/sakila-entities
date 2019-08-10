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

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link Address} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressService extends BaseEntityService<Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressService() {
        super(Address.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts all addresses which each resides in specified city.
     * <blockquote><pre>{@code
     * SELECT COUNT(*) FROM city AS c WHERE c.city_id = ?
     * }</pre></blockquote>
     *
     * @param city the city whose addresses are counted.
     * @return the number of address reside in specified city.
     */
    @PositiveOrZero long count(@NotNull final City city) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT COUNT(a) FROM Address AS a WHERE a.city = :city");
            query.setParameter("city", city);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(a) FROM Address AS a WHERE a.city = :city", Long.class);
            typedQuery.setParameter("city", city);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<Address> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(criteriaBuilder.count(root));
            criteriaQuery.where(criteriaBuilder.equal(root.get(Address.ATTRIBUTE_NAME_CITY), city));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<Address> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(criteriaBuilder.equal(root.get(Address_.city), city));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists addresses which each resides in specified city sorted by {@link Address#ATTRIBUTE_NAME_DISTRICT district}
     * attribute and {@link Address#ATTRIBUTE_NAME_ADDRESS address} attribute in both ascending order.
     *
     * @param city        the city whose addresses are listed.
     * @param firstResult the first index of the result, numbered from {@code 0}; {@code null} for an unspecified
     *                    result.
     * @param maxResults  the maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of addresses.
     */
    @NotNull List<@NotNull Address> list(@NotNull final City city, @PositiveOrZero @Nullable Integer firstResult,
                                         @Positive @Nullable Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT a FROM Address AS a WHERE a.city = :city ORDER BY a.district ASC, a.address ASC");
            query.setParameter("city", city);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Address> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Address> typedQuery = entityManager().createQuery(
                    "SELECT a FROM Address AS a WHERE a.city = :city ORDER BY a.district ASC, a.address ASC",
                    Address.class);
            typedQuery.setParameter("city", city);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
            final Root<Address> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get(Address.ATTRIBUTE_NAME_CITY), city));
            final TypedQuery<Address> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        final Root<Address> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Address_.city), city));
        final TypedQuery<Address> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
