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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.lang.reflect.Array;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

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
     * Lists countries sorted by {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute in either ascending or
     * descending indicated by specified flag.
     *
     * @param ascendingOrder the flag for sorting direction; {@code true} for ascending order; {@code false} for
     *                       descending order.
     * @param firstResult    the position of the first result to retrieve.
     * @param maxResults     the maximum number of results to retrieve.
     * @return a list of countries.
     */
    public List<Country> listSortedByCountryIn(final boolean ascendingOrder,
                                               @PositiveOrZero @Nullable final Integer firstResult,
                                               @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT c FROM Country AS c ORDER BY c.country " + (ascendingOrder ? "ASC" : "DESC"));
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Country> resultList = (List<Country>) query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Country> typedQuery = entityManager().createQuery(
                    "SELECT c FROM Country AS c ORDER BY c.country " + (ascendingOrder ? "ASC" : "DESC"),
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
            final Path<String> countryPath = from.get(Country.ATTRIBUTE_NAME_COUNTRY);
            final Order order = ascendingOrder ? criteriaBuilder.asc(countryPath) : criteriaBuilder.desc(countryPath);
            criteriaQuery.orderBy(order);
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
        final Path<String> countryPath = from.get(countryAttribute);
        final Order order = ascendingOrder ? criteriaBuilder.asc(countryPath) : criteriaBuilder.desc(countryPath);
        criteriaQuery.orderBy(order);
        final TypedQuery<Country> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    /**
     * Lists countries sorted by the number of cities reside in them in descending order. Countries with same number
     * number of cities should be sorted in their {@link Country#ATTRIBUTE_NAME_COUNTRY country} attribute in ascending
     * order.
     *
     * @param firstResult the position of the first result to retrieve.
     * @param maxResults  the maximum number of results to retrieve.
     * @return a list of countries.
     */
    public List<Country> listSortedByCityCount(@PositiveOrZero @Nullable final Integer firstResult,
                                               @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT country"
                    + " FROM Country AS country LEFT OUTER JOIN country.cities AS city ON country = city.country"
                    + " GROUP BY country"
                    + " ORDER BY COUNT(city) DESC, country.country ASC");
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Country> countries = query.getResultList();
            return countries;
        }
        if (true || current().nextBoolean()) {
            final TypedQuery<Country> typedQuery = entityManager().createQuery(
                    "SELECT country"
                    + " FROM Country AS country LEFT OUTER JOIN country.cities AS city ON country = city.country"
                    + " GROUP BY country"
                    + " ORDER BY COUNT(city) DESC, country.country ASC",
                    Country.class);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
            final Root<Country> country = criteriaQuery.from(Country.class);
            final Join<Country, City> cities = country.join(Country.ATTRIBUTE_NAME_CITIES, JoinType.LEFT);

            final TypedQuery<Object[]> typedQuery = entityManager().createQuery(
                    "SELECT country, COUNT(city)"
                    + " FROM Country AS country LEFT OUTER JOIN City AS city ON country = city.country"
                    + " GROUP BY country"
                    + " ORDER BY COUNT(city) DESC, country.country ASC",
                    Object[].class);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultStream().map(v -> (Country) Array.get(v, 0)).collect(toList());
        }
        throw new UnsupportedOperationException("not implemented yet");
    }
}
