package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A service for {@link Language}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
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
     * Finds the language whose {@link Language#ATTRIBUTE_NAME_NAME} attribute matches to specified value.
     *
     * @param name the value of {@link Language#ATTRIBUTE_NAME_NAME} attribute to match.
     * @return an optional of found entity.
     */
    public Optional<Language> findByName(@NotNull final String name) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
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
    public Stream<Language> streamOrderedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                                @Positive final Integer maxResults) {
        // TODO: 2019-07-16 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
