package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CityService extends BaseEntityService<City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CityService() {
        super(City.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts cities whose {@link City#ATTRIBUTE_NAME_COUNTRY country} attribute equals to specified city.
     *
     * @param city the city to match.
     * @return the number of cities in specified country.
     */
    public @PositiveOrZero long countCitiesOfSpecifiedCountry(@NotNull final City city) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(c) FROM City AS c WHERE c.country = :country");
            query.setParameter("country", )
        }
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists cities whose {@link City#ATTRIBUTE_NAME_CITY city} attribute equals to specified country sorted by {@link
     * City#ATTRIBUTE_NAME_CITY city} attribute in ascending order.
     *
     * @param city        the city to match.
     * @param firstResult position of the first result, numbered from 0; {@code null} for an unspecified result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of cities.
     */
    @NotNull
    public List<@NotNull City> countCitiesOfSpecifiedCountrySortedByCityInAscendingOrder(
            @NotNull final City city, @PositiveOrZero @Nullable final Integer firstResult,
            @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
