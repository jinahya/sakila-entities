package com.github.jinahya.sakila.persistence;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSortedSet;

class CountryServiceIT extends BaseEntityServiceIT<CountryService, Country> {

    // -----------------------------------------------------------------------------------------------------------------
    static final int COUNTRY_COUNT = entityCountAsInt(Country.class);

    static Country randomCountry() {
        return randomEntity(Country.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable sorted set of {@link Country#ATTRIBUTE_NAME_COUNTRY} attributes.
     */
    static final SortedSet<String> COUNTRIES;

    static {
        try {
            COUNTRIES = unmodifiableSortedSet(applyResourceScanner(
                    "country_set_country.txt",
                    s -> {
                        final SortedSet<String> set = new TreeSet<>();
                        while (s.hasNext()) {
                            final String country = s.nextLine();
                            final boolean added = set.add(country);
                            assert added : "duplicate country: " + country;
                        }
                        return set;
                    })
            );
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CountryServiceIT() {
        super(CountryService.class, Country.class);
    }
}
