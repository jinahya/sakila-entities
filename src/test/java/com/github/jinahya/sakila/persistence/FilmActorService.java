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

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
 * A service class for {@link FilmActor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmActorService extends EntityService<FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorService() {
        super(FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts all films that specified actor played.
     *
     * @param actor the actor whose films are counted.
     * @return the number of films {@code actor} played.
     */
    public @PositiveOrZero long countFilms(@NotNull final Actor actor) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.actor = :actor");
            query.setParameter("actor", actor);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.actor = :actor", Long.class);
            typedQuery.setParameter("actor", actor);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FilmActor.ATTRIBUTE_NAME_ACTOR), actor));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        //final SingularAttribute<FilmActor, Actor> actorAttribute = FilmActor_.actor;
        final SingularAttribute<? super FilmActor, Actor> actorAttribute
                = singularAttribute(entityClass, FilmActor.ATTRIBUTE_NAME_ACTOR, Actor.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(actorAttribute), actor));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists films that specified actor played, sorted by {@link Film#ATTRIBUTE_NAME_RELEASE_YEAR releaseYear} attribute
     * in descending order.
     *
     * @param actor       the actor whose films are listed.
     * @param firstResult position of the first result, numbered from 0.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of films of {@code actor}.
     */
    public @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                         @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fa.film FROM FilmActor AS fa WHERE fa.actor = :actor ORDER BY fa.film.releaseYear DESC");
            query.setParameter("actor", actor);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Film> films = query.getResultList();
            return films;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Film> typedQuery = entityManager().createQuery(
                    "SELECT fa.film FROM FilmActor AS fa WHERE fa.actor = :actor ORDER BY fa.film.releaseYear DESC",
                    Film.class);
            typedQuery.setParameter("actor", actor);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
            final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
            criteriaQuery.select(from.get(FilmActor.ATTRIBUTE_NAME_FILM));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FilmActor.ATTRIBUTE_NAME_ACTOR), actor));
            criteriaQuery.orderBy(criteriaBuilder.desc(
                    from.get(FilmActor.ATTRIBUTE_NAME_FILM).get(Film.ATTRIBUTE_NAME_RELEASE_YEAR)));
            final TypedQuery<Film> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
//        final SingularAttribute<FilmActor, Film> filmAttribute = FilmActor_.film;
        final SingularAttribute<? super FilmActor, Film> filmAttribute
                = singularAttribute(FilmActor.class, FilmActor.ATTRIBUTE_NAME_FILM, Film.class);
        criteriaQuery.select(from.get(filmAttribute));
//        final SingularAttribute<FilmActor, Actor> actorAttribute = FilmActor_.actor;
        final SingularAttribute<? super FilmActor, Actor> actorAttribute
                = singularAttribute(FilmActor.class, FilmActor.ATTRIBUTE_NAME_ACTOR, Actor.class);
        criteriaQuery.where(criteriaBuilder.equal(from.get(actorAttribute), actor));
//        final SingularAttribute<Film, Integer> releaseYearAttribute = Film_.releaseYear;
        final SingularAttribute<? super Film, Integer> releaseYearAttribute
                = singularAttribute(Film.class, Film.ATTRIBUTE_NAME_RELEASE_YEAR, Integer.class);
        final Path<Integer> releaseYearPath = from.get(filmAttribute).get(releaseYearAttribute);
        criteriaQuery.orderBy(criteriaBuilder.desc(releaseYearPath));
        final TypedQuery<Film> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count all actors played in specified film.
     *
     * @param film the film whose actors are counted.
     * @return the number of actors mapped to specified film.
     */
    public @PositiveOrZero long countActors(@NotNull final Film film) {
        return entityManager()
                .createQuery("SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.film = :film", Long.class)
                .setParameter("film", film)
                .getSingleResult();
    }

    /**
     * Lists actors played in specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} in ascending
     * order.
     *
     * @param film        the film whose actors are listed.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    public @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                           @Positive final Integer maxResults) {
        final TypedQuery<Actor> typedQuery = entityManager()
                .createQuery("SELECT fa.actor FROM FilmActor AS fa WHERE fa.film = :film ORDER BY fa.actor.id ASC",
                             Actor.class)
                .setParameter("film", film);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
