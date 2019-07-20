package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

/**
 * A class for testing {@link City}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityTest extends BaseEntityTest<City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CityTest() {
        super(City.class);
    }
}
