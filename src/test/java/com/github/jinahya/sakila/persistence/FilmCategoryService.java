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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static com.github.jinahya.sakila.persistence.FilmCategory.ATTRIBUTE_NAME_CATEGORY;
import static com.github.jinahya.sakila.persistence.FilmCategory.ATTRIBUTE_NAME_FILM;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link FilmCategory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmCategoryService extends EntityService<FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmCategoryService() {
        super(FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Finds the entity whose {@link FilmCategory#ATTRIBUTE_NAME_FILM film} attribute and {@link
     * FilmCategory#ATTRIBUTE_NAME_CATEGORY category} attribute match to specified values.
     *
     * @param film     the value of {@link FilmCategory#ATTRIBUTE_NAME_FILM film} attribute to match.
     * @param category the value of {@link FilmCategory#ATTRIBUTE_NAME_CATEGORY category} attribute to match.
     * @return an optional of matched entity; empty if not matched.
     */
    @NotNull Optional<FilmCategory> find(@NotNull final Film film, @NotNull final Category category) {
        if (current().nextBoolean()) {
            try {
                return Optional.of(
                        (FilmCategory) entityManager()
                                .createQuery("SELECT fe"
                                             + " FROM FilmCategory AS fe"
                                             + " WHERE fe.film = :film AND fe.category = :category")
                                .setParameter("film", film)
                                .setParameter("category", category)
                                .getSingleResult()
                );
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            try {
                return Optional.of(
                        (entityManager()
                                .createQuery("SELECT fe"
                                             + " FROM FilmCategory AS fe"
                                             + " WHERE fe.film = :film AND fe.category = :category",
                                             FilmCategory.class)
                                .setParameter("film", film)
                                .setParameter("category", category)
                                .getSingleResult())
                );
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<FilmCategory> criteriaQuery = criteriaBuilder.createQuery(FilmCategory.class);
            final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
            criteriaQuery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(from.get(ATTRIBUTE_NAME_FILM), film),
                    criteriaBuilder.equal(from.get(ATTRIBUTE_NAME_CATEGORY), category)));
            try {
                return Optional.of((entityManager().createQuery(criteriaQuery).getSingleResult()));
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<FilmCategory> criteriaQuery = criteriaBuilder.createQuery(FilmCategory.class);
        final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
        criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get(FilmCategory_.film), film),
                        criteriaBuilder.equal(from.get(FilmCategory_.category), category)
                )
        );
        try {
            return Optional.of((entityManager().createQuery(criteriaQuery).getSingleResult()));
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts categories as which specified film is categorized.
     *
     * @param film the film whose categories are counted.
     * @return the number of categories of specified film.
     */
    @PositiveOrZero long countCategories(@NotNull final Film film) {
        final EntityManager entityManager = entityManager();
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery(
                    "SELECT COUNT(fc) FROM FilmCategory AS fc WHERE fc.film = :film");
            query.setParameter("film", film);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager.createQuery(
                    "SELECT COUNT(fc) FROM FilmCategory AS fc WHERE fc.film = :film", Long.class);
            typedQuery.setParameter("film", film);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
            criteriaQuery.select(criteriaBuilder.count(from));
            final Path<Film> filmPath = from.get(FilmCategory.ATTRIBUTE_NAME_FILM);
            final Predicate predicate = criteriaBuilder.equal(filmPath, film);
            criteriaQuery.where(predicate);
            final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        final Predicate predicate = criteriaBuilder.equal(from.get(FilmCategory_.film), film);
        criteriaQuery.where(predicate);
        final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists categories of specified film ordered by {@link Category#ATTRIBUTE_NAME_NAME name} attribute in ascending
     * order.
     *
     * @param film        the film whose categories are listed.
     * @param firstResult position of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                    result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of categories of specified film.
     */
    @NotNull List<@NotNull Category> listCategories(@NotNull final Film film,
                                                    @PositiveOrZero @Nullable final Integer firstResult,
                                                    @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fc.category FROM FilmCategory AS fc WHERE fc.film = :film");
            query.setParameter("film", film);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Category> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Category> typedQuery = entityManager().createQuery(
                    "SELECT fc.category FROM FilmCategory AS fc WHERE fc.film = :film", Category.class);
            typedQuery.setParameter("film", film);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
            criteriaQuery.select(from.get(FilmCategory.ATTRIBUTE_NAME_CATEGORY));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FilmCategory.ATTRIBUTE_NAME_FILM), film));
            final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
        criteriaQuery.select(from.get(FilmCategory_.category));
        criteriaQuery.where(criteriaBuilder.equal(from.get(FilmCategory_.film), film));
        final TypedQuery<Category> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of films categorized as specified category.
     *
     * @param category the category as which films are categorized.
     * @return the number of films categorized as specified category.
     */
    @PositiveOrZero long countFilms(@NotNull final Category category) {
        final EntityManager entityManager = entityManager();
        if (current().nextBoolean()) {
            final Query query = entityManager.createQuery(
                    "SELECT COUNT(fc) FROM FilmCategory AS fc WHERE fc.category = :category");
            query.setParameter("category", category);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager.createQuery(
                    "SELECT COUNT(fc) FROM FilmCategory AS fc WHERE fc.category = :category", Long.class);
            typedQuery.setParameter("category", category);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
            criteriaQuery.select(criteriaBuilder.count(from));
            final Path<Category> categoryPath = from.get(FilmCategory.ATTRIBUTE_NAME_CATEGORY);
            final Predicate predicate = criteriaBuilder.equal(categoryPath, category);
            criteriaQuery.where(predicate);
            final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        final Path<Category> categoryPath = from.get(FilmCategory_.category);
        final Predicate predicate = criteriaBuilder.equal(categoryPath, category);
        criteriaQuery.where(predicate);
        final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists all films categorized as specified category ordered by {@link Film#ATTRIBUTE_NAME_TITLE title} attribute in
     * ascending order.
     *
     * @param category    the category as which films are categorized.
     * @param firstResult position of the first result, numbered from {@code 0}; {@code null} for an unspecified
     *                    result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of films categorized as specified category.
     */
    @NotNull List<@NotNull Film> listFilms(@NotNull final Category category,
                                           @PositiveOrZero @Nullable final Integer firstResult,
                                           @Positive @Nullable final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fc.film FROM FilmCategory AS fc WHERE fc.category = :category");
            query.setParameter("category", category);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Film> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Film> typedQuery = entityManager().createQuery(
                    "SELECT fc.film FROM FilmCategory AS fc WHERE fc.category = :category", Film.class);
            typedQuery.setParameter("category", category);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
            final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
            criteriaQuery.select(from.get(FilmCategory.ATTRIBUTE_NAME_FILM));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FilmCategory.ATTRIBUTE_NAME_CATEGORY), category));
            final TypedQuery<Film> typedQuery = entityManager().createQuery(criteriaQuery);
            ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
            ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
            return typedQuery.getResultList();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        final Root<FilmCategory> from = criteriaQuery.from(FilmCategory.class);
        criteriaQuery.select(from.get(FilmCategory_.film));
        criteriaQuery.where(criteriaBuilder.equal(from.get(FilmCategory_.category), category));
        final TypedQuery<Film> typedQuery = entityManager().createQuery(criteriaQuery);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultList();
    }
}
