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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
     * Lists actors whose {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute and {@link
     * Actor#ATTRIBUTE_NAME_LAST_NAME lastName} attribute match to specified values ordered by {@link
     * BaseEntity#ATTRIBUTE_NAME_ID} attribute in ascending order.
     *
     * @param firstName   a value for {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute to match; {@code null}
     *                    for skipping matching.
     * @param lastName    a value for {@link Actor#ATTRIBUTE_NAME_LAST_NAME lastName} attribute to match; {@code null}
     *                    for skipping matching.
     * @param firstResult position of the first result, numbered from 0.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByIdInAscendingOrder(@Nullable final String firstName, @Nullable final String lastName,
                                                      @Nullable final Integer firstResult,
                                                      @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM Actor AS a");
            if (firstName != null || lastName != null) {
                queryBuilder.append(" WHERE");
                if (firstName != null) {
                    queryBuilder.append(" a.firstName = :firstName");
                    if (lastName != null) {
                        queryBuilder.append(" AND");
                    }
                }
                if (lastName != null) {
                    queryBuilder.append(" a.lastName = :lastName");
                }
            }
            queryBuilder.append(" ORDER BY a.id ASC");
            final String queryString = queryBuilder.toString();
            final Query query = entityManager().createQuery(queryString);
            ofNullable(firstName).ifPresent(v -> query.setParameter("firstName", v));
            ofNullable(lastName).ifPresent(v -> query.setParameter("lastName", v));
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Actor> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM Actor AS a");
            if (firstName != null || lastName != null) {
                queryBuilder.append(" WHERE");
                if (firstName != null) {
                    queryBuilder.append(" a.firstName = :firstName");
                    if (lastName != null) {
                        queryBuilder.append(" AND");
                    }
                }
                if (lastName != null) {
                    queryBuilder.append(" a.lastName = :lastName");
                }
            }
            queryBuilder.append(" ORDER BY a.id ASC");
            final String queryString = queryBuilder.toString();
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryString, Actor.class);
            ofNullable(firstName).ifPresent(v -> typedQuery.setParameter("firstName", v));
            ofNullable(lastName).ifPresent(v -> typedQuery.setParameter("lastName", v));
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
            final Root<Actor> from = criteriaQuery.from(Actor.class);
            criteriaQuery.select(from);
            if (firstName != null || lastName != null) {
                final List<Predicate> predicates = new ArrayList<>();
                if (firstName != null) {
                    predicates.add(criteriaBuilder.equal(from.get(Actor.ATTRIBUTE_NAME_FIRST_NAME), firstName));
                }
                if (lastName != null) {
                    predicates.add(criteriaBuilder.equal(from.get(Actor.ATTRIBUTE_NAME_LAST_NAME), lastName));
                }
                if (predicates.size() == 2) {
                    predicates.add(criteriaBuilder.and(predicates.remove(0), predicates.remove(0)));
                }
                assert !predicates.isEmpty();
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
            }
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstName).ifPresent(v -> typedQuery.setParameter("firstName", v));
            ofNullable(lastName).ifPresent(v -> typedQuery.setParameter("lastName", v));
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        final Root<Actor> from = criteriaQuery.from(Actor.class);
        criteriaQuery.select(from);
        if (firstName != null || lastName != null) {
            final List<Predicate> predicates = new ArrayList<>();
            if (firstName != null) {
                //predicates.add(criteriaBuilder.equal(from.get(Actor_.firstName), firstName));
                predicates.add(criteriaBuilder.equal(
                        from.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class)), firstName));
            }
            if (lastName != null) {
                //predicates.add(criteriaBuilder.equal(from.get(Actor_.lastName), lastName));
                predicates.add(criteriaBuilder.equal(from.get(
                        singularAttribute(FullNamed.ATTRIBUTE_NAME_LAST_NAME, String.class)), lastName));
            }
            if (predicates.size() == 2) {
                predicates.add(criteriaBuilder.and(predicates.remove(0), predicates.remove(0)));
            }
            assert !predicates.isEmpty();
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstName).ifPresent(v -> typedQuery.setParameter("firstName", v));
        ofNullable(lastName).ifPresent(v -> typedQuery.setParameter("lastName", v));
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} attributes match to specified
     * value, ordered by {@link FullName#ATTRIBUTE_NAME_FIRST_NAME firstName} attribute in either ascending order or
     * descending order indicated by specified flag.
     *
     * @param lastName       a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} to match; {@code null} for
     *                       skipping the criterion.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    position of the first result, numbered from 0.
     * @param maxResults     maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByFirstName(@Nullable final String lastName, final boolean ascendingOrder,
                                             @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM ")
                    .append(Actor.ENTITY_NAME)
                    .append(" AS a");
            if (lastName != null) {
                queryBuilder.append(" WHERE a.lastName = :lastName");
            }
            queryBuilder.append(" ORDER BY a.firstName ");
            if (ascendingOrder) {
                queryBuilder.append("ASC");
            } else {
                queryBuilder.append("DESC");
            }
            final Query query = entityManager().createQuery(queryBuilder.toString());
            if (lastName != null) {
                query.setParameter("lastName", lastName);
            }
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Actor> resultList = query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM ")
                    .append(Actor.ENTITY_NAME)
                    .append(" AS a");
            if (lastName != null) {
                queryBuilder.append(" WHERE a.lastName = :lastName");
            }
            queryBuilder.append(" ORDER BY a.firstName ");
            if (ascendingOrder) {
                queryBuilder.append("ASC");
            } else {
                queryBuilder.append("DESC");
            }
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryBuilder.toString(), Actor.class);
            if (lastName != null) {
                typedQuery.setParameter("lastName", lastName);
            }
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
            final Root<Actor> actor = criteriaQuery.from(Actor.class);
            criteriaQuery.select(actor);
            if (lastName != null) {
                criteriaQuery.where(criteriaBuilder.equal(actor.get(FullNamed.ATTRIBUTE_NAME_LAST_NAME), lastName));
            }
            final Path<String> firstName = actor.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME);
            if (ascendingOrder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(firstName));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(firstName));
            }
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        final Root<Actor> actor = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actor);
        if (lastName != null) {
//            criteriaQuery.where(criteriaBuilder.equal(actor.get(Actor_.firstName), lastName));
            criteriaQuery.where(criteriaBuilder.equal(
                    actor.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_LAST_NAME, String.class)), lastName));
        }
//        final Path<String> firstName = actor.get(Actor_.firstName);
        final Path<String> firstName = actor.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class));
        if (ascendingOrder) {
            criteriaQuery.orderBy(criteriaBuilder.asc(firstName));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(firstName));
        }
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    /**
     * Returns a list of actors, whose {@link FullName#ATTRIBUTE_NAME_FIRST_NAME firstName} attributes match to
     * specified value, ordered by {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} in either ascending order or
     * descending order indicated by specified flag.
     *
     * @param firstName      a value for {@link FullName#ATTRIBUTE_NAME_LAST_NAME lastName} to match; {@code null} for
     *                       skipping the criterion.
     * @param ascendingOrder {@code true} for ascending order; {@code false} for descending order.
     * @param firstResult    position of the first result, numbered from 0.
     * @param maxResults     maximum number of results to retrieve.
     * @return a list of actors.
     */
    public List<Actor> listSortedByLastName(@Nullable final String firstName, final boolean ascendingOrder,
                                            @Nullable final Integer firstResult, @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM ")
                    .append(Actor.ENTITY_NAME)
                    .append(" AS a");
            if (firstName != null) {
                queryBuilder.append(" WHERE a.firstName = :firstName");
            }
            queryBuilder.append(" ORDER BY a.lastName ");
            if (ascendingOrder) {
                queryBuilder.append("ASC");
            } else {
                queryBuilder.append("DESC");
            }
            final Query query = entityManager().createQuery(queryBuilder.toString());
            if (firstName != null) {
                query.setParameter("firstName", firstName);
            }
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Actor> resultList = query.getResultList();
            return resultList;
        }
        if (current().nextBoolean()) {
            final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM ")
                    .append(Actor.ENTITY_NAME)
                    .append(" AS a");
            if (firstName != null) {
                queryBuilder.append(" WHERE a.firstName = :firstName");
            }
            queryBuilder.append(" ORDER BY a.lastName ");
            if (ascendingOrder) {
                queryBuilder.append("ASC");
            } else {
                queryBuilder.append("DESC");
            }
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(queryBuilder.toString(), Actor.class);
            if (firstName != null) {
                typedQuery.setParameter("firstName", firstName);
            }
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
            final Root<Actor> actor = criteriaQuery.from(Actor.class);
            criteriaQuery.select(actor);
            if (firstName != null) {
                criteriaQuery.where(criteriaBuilder.equal(actor.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME), firstName));
            }
            final Path<String> lastName = actor.get(FullNamed.ATTRIBUTE_NAME_LAST_NAME);
            if (ascendingOrder) {
                criteriaQuery.orderBy(criteriaBuilder.asc(lastName));
            } else {
                criteriaBuilder.desc(lastName);
            }
            final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);
        final Root<Actor> actor = criteriaQuery.from(Actor.class);
        criteriaQuery.select(actor);
        if (firstName != null) {
//            criteriaQuery.where(criteriaBuilder.equal(actor.get(Actor_.lastName), firstName));
            criteriaQuery.where(criteriaBuilder.equal(
                    actor.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_FIRST_NAME, String.class)), firstName));
        }
//        final Path<String> lastName = actor.get(Actor_.lastName);
        final Path<String> lastName = actor.get(singularAttribute(FullNamed.ATTRIBUTE_NAME_LAST_NAME, String.class));
        if (ascendingOrder) {
            criteriaQuery.orderBy(criteriaBuilder.asc(lastName));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(lastName));
        }
        final TypedQuery<Actor> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
