package com.github.jinahya.sakila.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * A class for integration-testing {@link FilmService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmServiceIT extends BaseEntityServiceIT<FilmService, Film> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable map of film ids and category count.
     */
    static final Map<Integer, Integer> FILM_ID_CATEGORY_COUNT;

    static {
        try {
            FILM_ID_CATEGORY_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_map_film_id_category_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new HashMap<>();
                        while (s.hasNext()) {
                            final int filmId = s.nextInt();
                            final int categoryCount = s.nextInt();
                            final Integer previous = map.put(filmId, categoryCount);
                            assert previous == null : "duplicate film id: " + filmId;
                        }
                        return map;
                    }));
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmServiceIT() {
        super(FilmService.class, Film.class);
    }
}
