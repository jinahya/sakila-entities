package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
