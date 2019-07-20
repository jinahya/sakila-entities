package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for asserting {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CityAssert extends BaseEntityAssert<CityAssert, City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     *
     * @param actual an actual.
     */
    CityAssert(final City actual) {
        super(actual);
    }

    // ------------------------------------------------------------------------------------------------------------ city
    CityAssert hasCity(@NotNull final String city) {
        isNotNull().satisfies(a -> assertThat(a.getCity()).satisfies(c -> assertThat(c).isNotNull().isEqualTo(c)));
        return this;
    }

    CityAssert hasName(@NotNull final String city) {
        return hasCity(city);
    }

    // --------------------------------------------------------------------------------------------------------- country
    CityAssert residesInCountryWhoseIdIs(@NotNull final int countryId) {
        isNotNull().satisfies(c -> assertThat(c.getId()).isNotNull().isEqualTo(countryId));
        return this;
    }

    CityAssert residesIn(@NotNull final Country country) {
        return residesInCountryWhoseIdIs(country.getId());
    }
}
