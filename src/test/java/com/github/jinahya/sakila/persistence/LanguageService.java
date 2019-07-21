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
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * A service for {@link Language}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class LanguageService extends BaseEntityService<Language> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    LanguageService() {
        super(Language.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Finds the language whose {@link Language#ATTRIBUTE_NAME_NAME name} attribute matches to specified value.
     *
     * @param name the value of {@link Language#ATTRIBUTE_NAME_NAME name} attribute to match.
     * @return an optional of found entity.
     */
    public Optional<Language> findByName(@NotNull final String name) {
        final TypedQuery<Language> typedQuery = entityManager().createQuery(
                "SELECT l FROM Language AS l WHERE l.name = :name", Language.class);
        typedQuery.setParameter("name", name);
        try {
            return Optional.of(typedQuery.getSingleResult()); // NonUniqueResultException
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    /**
     * Returns a stream of languages ordered by {@link Language#ATTRIBUTE_NAME_NAME} attribute in the order indicated by
     * specified flag.
     *
     * @param ascendingOrder the flag for ordering; {@code true} for ascending order; {@code false} for descending
     *                       order.
     * @param firstResult    an optional value for {@link javax.persistence.TypedQuery#setFirstResult(int)}
     * @param maxResults     an optional value for {@link javax.persistence.TypedQuery#setMaxResults(int)}
     * @return a stream of, optionally paged, languages.
     */
    public Stream<Language> streamOrderedByName(final boolean ascendingOrder,
                                                @PositiveOrZero @Nullable final Integer firstResult,
                                                @Positive @Nullable final Integer maxResults) {
        final TypedQuery<Language> typedQuery = entityManager().createQuery(
                "SELECT l FROM Language AS l ORDER BY l.name " + (ascendingOrder ? "ASC" : "DESC"),
                Language.class);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }
}
