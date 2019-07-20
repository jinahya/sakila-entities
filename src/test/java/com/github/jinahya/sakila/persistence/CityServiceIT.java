package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

/**
 * A class for integration-testing {@link CityService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class CityServiceIT extends BaseEntityServiceIT<CityService, City> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable map of country ids and a unmodifiable list of cities.
     */
    private static final Map<Integer, List<String>> COUNTRY_ID_CITIES;

    static {
        try {

            COUNTRY_ID_CITIES = unmodifiableMap(applyResourceScanner(
                    "city_country_id_city.txt",
                    s -> {
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
                        for (Integer key : map.keySet().toArray(new Integer[0])) {
                            map.put(key, unmodifiableList(map.get(key)));
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
