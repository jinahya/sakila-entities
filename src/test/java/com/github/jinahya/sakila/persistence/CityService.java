package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

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

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityService extends BaseEntityService<City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CityService() {
        super(City.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public @PositiveOrZero long countByCity(@NotNull final String city) {
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts cities resides in specified country.
     *
     * @param country the value of {@link City#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @return the number of cities in specified country.
     */
    public @PositiveOrZero long countByCountry(@NotNull final Country country) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(c) FROM City AS c WHERE c.country = :country");
            query.setParameter("country", country);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(c) FROM City AS c WHERE c.country = :country", Long.class);
            typedQuery.setParameter("country", country);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<City> from = criteriaQuery.from(City.class);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(City.ATTRIBUTE_NAME_COUNTRY), country));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<City> from = criteriaQuery.from(City.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        //final SingularAttribute<City, Country> countryAttribute = City_.country;
        final SingularAttribute<? super City, Country> countryAttribute
                = singularAttribute(City.class, City.ATTRIBUTE_NAME_COUNTRY, Country.class);
        final Path<Country> countryPath = from.get(countryAttribute);
        criteriaQuery.where(criteriaBuilder.equal(countryPath, country));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists cities resides in specified country sorted by {@link City#ATTRIBUTE_NAME_CITY city} attribute in ascending
     * order.
     *
     * @param country     the value of {@link City#ATTRIBUTE_NAME_COUNTRY country} attribute to match.
     * @param firstResult position of the first result, numbered from 0; {@code null} for an unspecified result.
     * @param maxResults  maximum number of results to retrieve; {@code null} for an unspecified result.
     * @return a list of cities.
     */
    @NotNull
    public List<@NotNull City> listByCountry(
            @NotNull final Country country, @PositiveOrZero @Nullable final Integer firstResult,
            @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-20 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
