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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

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
     * Counts categories as which specified film is categorized.
     *
     * @param film the film whose categories are counted.
     * @return the number of categories of specified film.
     */
    public @PositiveOrZero long countCategories(@NotNull final Film film) {
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
        //final SingularAttribute<FilmCategory, Film> filmAttribute = FilmCategory_.film;
        final SingularAttribute<? super FilmCategory, Film> filmAttribute
                = singularAttribute(FilmCategory.class, FilmCategory.ATTRIBUTE_NAME_FILM, Film.class);
        final Path<Film> filmPath = from.get(filmAttribute);
        final Predicate predicate = criteriaBuilder.equal(filmPath, film);
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
    public @NotNull List<@NotNull Category> listCategories(@NotNull final Film film,
                                                           @PositiveOrZero @Nullable final Integer firstResult,
                                                           @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of films categorized as specified category.
     *
     * @param category the category as which films are categorized.
     * @return the number of films categorized as specified category.
     */
    public @PositiveOrZero long countFilms(@NotNull final Category category) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
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
    public @NotNull List<@NotNull Category> listFilms(@NotNull final Category category,
                                                      @PositiveOrZero @Nullable final Integer firstResult,
                                                      @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
