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
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service for {@link Country}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CountryService extends BaseEntityService<Country> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CountryService() {
        super(Country.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Find the country whose {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute matches to specified value. Note
     * that the {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute is not unique but all values are distinct to
     * each other in the table.
     *
     * @param country the value for {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @return an optional of found country.
     */
    Optional<Country> find(@NotNull final String country) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Country AS c WHERE c.country = :country");
            query.setParameter("country", country);
            try {
                return Optional.of((Country) query.getSingleResult()); // NonUniqueResultException
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final TypedQuery<Country> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Country AS c WHERE c.country = :country", Country.class);
            typedQuery.setParameter("country", country);
            try {
                return Optional.of(typedQuery.getSingleResult()); // NonUniqueResultException
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
            final Root<Country> from = criteriaQuery.from(Country.class);
            criteriaQuery.select(from);
            criteriaQuery.where(criteriaBuilder.equal(from.get(Country.ATTRIBUTE_NAME_COUNTRY), country));
            final TypedQuery<Country> typedQuery = entityManager().createQuery(criteriaQuery);
            try {
                return Optional.of(typedQuery.getSingleResult()); // NonUniqueResultException
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        final Root<Country> from = criteriaQuery.from(Country.class);
        criteriaQuery.select(from);
        //final SingularAttribute<Country, String> countryAttribute = Country_.country;
        final SingularAttribute<? super Country, String> countryAttribute
                = singularAttribute(Country.ATTRIBUTE_NAME_COUNTRY, String.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(countryAttribute), country));
        final TypedQuery<Country> typedQuery = entityManager().createQuery(criteriaQuery);
        try {
            return Optional.of(typedQuery.getSingleResult()); // NonUniqueResultException
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    /**
     * Lists countries sorted by {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute in ascending order.
     *
     * @param firstResult the position of the first result to retrieve.
     * @param maxResults  the maximum number of results to retrieve.
     * @return a list of countries.
     */
    List<Country> list(@PositiveOrZero @Nullable final Integer firstResult,
                       @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery("SELECT c FROM Country AS c ORDER BY c.country ASC");
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Country> resultList = (List<Country>) query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Country> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Country AS c ORDER BY c.country ASC",
                    Country.class);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
            final Root<Country> from = criteriaQuery.from(Country.class);
            criteriaQuery.select(from);
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(Country.ATTRIBUTE_NAME_COUNTRY)));
            final TypedQuery<Country> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        final Root<Country> from = criteriaQuery.from(Country.class);
        criteriaQuery.select(from);
        //final SingularAttribute<? super Country, String> countryAttribute = Country_.country;
        final SingularAttribute<? super Country, String> countryAttribute
                = singularAttribute(Country.ATTRIBUTE_NAME_COUNTRY, String.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get(countryAttribute)));
        final TypedQuery<Country> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
