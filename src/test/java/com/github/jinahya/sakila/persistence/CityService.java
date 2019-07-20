package com.github.jinahya.sakila.persistence;

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
}
