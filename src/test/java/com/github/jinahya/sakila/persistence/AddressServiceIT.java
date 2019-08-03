package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * A class for integration-testing {@link AddressService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressServiceIT extends BaseEntityServiceIT<AddressService, Address> {

    // -----------------------------------------------------------------------------------------------------------------
    static final Map<Integer, Integer> CITY_ID_ADDRESS_CONT;

    static {
        try {
            CITY_ID_ADDRESS_CONT = unmodifiableMap(applyResourceScanner(
                    "address_map_city_id_address_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new LinkedHashMap<>();
                        while (s.hasNext()) {
                            final int cityId = s.nextInt();
                            final int addressCount = s.nextInt();
                            final Integer previous = map.put(cityId, addressCount);
                            assert previous == null : "duplicate city id: " + cityId;
                        }
                        return map;
                    })
            );
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressServiceIT() {
        super(AddressService.class, Address.class);
    }
}
