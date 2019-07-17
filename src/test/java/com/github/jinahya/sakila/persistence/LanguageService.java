package com.github.jinahya.sakila.persistence;

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
    public Stream<Language> streamOrderedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                                @Positive final Integer maxResults) {
        final TypedQuery<Language> typedQuery = entityManager().createQuery(
                "SELECT l FROM Language AS l ORDER BY l.name " + (ascendingOrder ? "ASC" : "DESC"),
                Language.class);
        ofNullable(firstResult).ifPresent(typedQuery::setFirstResult);
        ofNullable(maxResults).ifPresent(typedQuery::setMaxResults);
        return typedQuery.getResultStream();
    }
}
