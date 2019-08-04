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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static com.github.jinahya.sakila.persistence.FilmCategory.ATTRIBUTE_NAME_CATEGORY;
import static com.github.jinahya.sakila.persistence.FilmCategory.ATTRIBUTE_NAME_FILM;
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
    Optional<FilmCategory> find(@NotNull final Film film, @NotNull final Category category) {
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
                        criteriaBuilder.equal(
//                                from.get(singularAttribute(ATTRIBUTE_NAME_FILM, Film.class)), film),
                                from.get(FilmCategory_.film), film),
                        criteriaBuilder.equal(
//                                from.get(singularAttribute(ATTRIBUTE_NAME_CATEGORY, Category.class)), category
                                from.get(FilmCategory_.category), category
                        )
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
    public @PositiveOrZero long countCategories(@NotNull final Film film) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
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
    public @NotNull List<@NotNull Film> listFilms(@NotNull final Category category,
                                                  @PositiveOrZero @Nullable final Integer firstResult,
                                                  @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-21 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
