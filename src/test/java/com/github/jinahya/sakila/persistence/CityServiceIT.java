package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for integration-testing {@link CityService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityServiceIT extends BaseEntityServiceIT<CityService, City> {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Integer, List<String>> COUNTRY_ID_CITIES;

    static {
        try {
            COUNTRY_ID_CITIES = Collections.unmodifiableMap(
                    applyResourceScanner("city_country_id_city.txt", s -> {
                        final Map<Integer, List<String>> map = new HashMap<>();
                        while (s.hasNext()) {
                            final Integer countryId = s.nextInt();
                            final String city = s.nextLine();
                            map.compute(countryId, (k, v) -> {
                                if (v == null) {
                                    v = new ArrayList<>();
                                }
                                v.add(city);
                                return v;
                            });
                        }
                        return map;
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
    CityServiceIT() {
        super(CityService.class, City.class);
    }
}
