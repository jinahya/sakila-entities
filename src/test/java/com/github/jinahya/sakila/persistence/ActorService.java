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

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link Actor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class ActorService extends BaseEntityService<Actor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorService() {
        super(Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a stream of actors, whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attribute <i>optionally</i> matches
     * to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} in either ascending or descending
     * indicated by specified flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally matched, actors.
     */
    public Stream<Actor> streamOrderedByFirstName(final String lastName, final boolean ascendingOrder,
                                                  final Integer firstResult, final Integer maxResults) {
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
            final Root<Actor> root = criteriaQuery.from(Actor.class);
            criteriaQuery.select(root);
            if (lastName != null) {
                criteriaQuery.where(criteriaBuilder.equal(root.get(FullNamed.ATTRIBUTE_NAME_LAST_NAME), lastName));
            }
            final Expression<String> firstNamePath = root.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME);
            criteriaQuery.orderBy(
                    ascendingOrder ? criteriaBuilder.asc(firstNamePath) : criteriaBuilder.desc(firstNamePath));
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultStream();
        }
        if (current().nextBoolean()) {
            log.debug("entityManager.open: {}, {}", entityManager().isOpen(), entityManager());
            System.out.println(entityManager().isOpen());
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
            final Root<Actor> root = criteriaQuery.from(Actor.class);
            criteriaQuery.select(root);
            if (lastName != null) {
                log.debug("entityManager.open: {}, {}", entityManager().isOpen(), entityManager());
                System.out.println(entityManager().isOpen());
                criteriaQuery.where(criteriaBuilder.equal(root.get(Actor_.firstName), lastName));
            }
            log.debug("entityManager.open: {}, {}", entityManager().isOpen(), entityManager());
            System.out.println(entityManager().isOpen());
            final Expression<String> firstNamePath = root.get(Actor_.firstName);
            criteriaQuery.orderBy(
                    ascendingOrder ? criteriaBuilder.asc(firstNamePath) : criteriaBuilder.desc(firstNamePath));
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            log.debug("entityManager.open: {}", entityManager().isOpen());
            System.out.println(entityManager().isOpen());
            return typedQuery.getResultStream();
        }
        final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM " + Actor.ENTITY_NAME + " AS a");
        if (lastName != null) {
            queryBuilder.append(
                    " WHERE a." + FullNamed.ATTRIBUTE_NAME_LAST_NAME + " = :lastName");
        }
        queryBuilder
                .append(" ORDER BY a." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " ")
                .append(ascendingOrder ? "ASC" : "DESC");
        final String queryString = queryBuilder.toString();
        log.debug("queryString: {}", queryString);
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryString, Actor.class);
        if (lastName != null) {
            typedQuery.setParameter("lastName", lastName);
        }
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }

    /**
     * Returns a stream of actors, whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute <i>optionally</i> matches
     * to specified value, ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME} in either ascending or descending
     * indicated by specified flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME} to <i>optionally</i> match; may be
     *                       {@code null}.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    a value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     a value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally matched, actors.
     */
    public Stream<Actor> streamOrderedByLastName(final String firstName, final boolean ascendingOrder,
                                                 final Integer firstResult, final Integer maxResults) {
        final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM " + Actor.ENTITY_NAME + " AS a");
        if (firstName != null) {
            queryBuilder.append(
                    " WHERE a." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME + " = :firstName");
        }
        queryBuilder
                .append(" ORDER BY a." + FullNamed.ATTRIBUTE_NAME_LAST_NAME + " ")
                .append(ascendingOrder ? "ASC" : "DESC");
        final String queryString = queryBuilder.toString();
        log.debug("queryString: {}", queryString);
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryString, Actor.class);
        ofNullable(firstName).ifPresent(v -> typedQuery.setParameter("firstName", v));
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }
}
