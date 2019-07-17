package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * A service for {@link Country}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
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
     * Finds a country whose {@link Country#ATTRIBUTE_NAME_COUNTRY} attribute matches to specified value. Note that this
     * method may throw a {@link javax.persistence.NonUniqueResultException} because the {@link
     * Country#COLUMN_NAME_COUNTRY} column is not unique.
     *
     * @param country the value of {@link Country#ATTRIBUTE_NAME_COUNTRY} attribute to match.
     * @return an optional found entity; empty if not found.
     */
    public Optional<Country> findByCountry(@NotNull final String country) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a stream of countries ordered by {@link Country#ATTRIBUTE_NAME_COUNTRY} attribute in either ascending or
     * descending indicated by specified flag.
     *
     * @param ascendingOrder the flag for ordering direction; {@code true} for ascending order; {@code false} for
     *                       descending order.
     * @param firstResult    the position of the first result to retrieve.
     * @param maxResults     the maximum number of results to retrieve.
     * @return a stream of countries.
     */
    public List<Country> listOrderedByCountry(final boolean ascendingOrder, final Integer firstResult,
                                              final Integer maxResults) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
