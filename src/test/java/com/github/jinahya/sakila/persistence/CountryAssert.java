package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for asserting {@link Country}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class CountryAssert extends BaseEntityAssert<CountryAssert, Country> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     *
     * @param actual an actual.
     */
    CountryAssert(final Country actual) {
        super(actual);
    }

    // --------------------------------------------------------------------------------------------------------- country
    CountryAssert hasCountry(@NotNull final String country) {
        isNotNull().satisfies(a -> assertThat(a.getCountry()).satisfies(c -> assertThat(c).isNotNull().isEqualTo(c)));
        return this;
    }

    CountryAssert hasName(@NotNull final String country) {
        return hasCountry(country);
    }
}
