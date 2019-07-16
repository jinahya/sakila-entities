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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Collections.unmodifiableMap;

/**
 * A class for testing {@link FilmCategoryService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmCategoryServiceIT extends EntityServiceIT<FilmCategoryService, FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A map of category id and film count.
     */
    static final Map<Integer, Integer> CATEGORY_ID_FILM_COUNT;

    static {
        try {
            CATEGORY_ID_FILM_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_category_category_id_film_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new HashMap<>();
                        while (s.hasNext()) {
                            map.put(s.nextInt(), s.nextInt());
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
     * A map of film id and category count.
     */
    static final Map<Integer, Integer> FILM_ID_CATEGORY_COUNT;

    static {
        final Map<Integer, Integer> map = new HashMap<>();
        try {
            try (InputStream stream
                         = FilmActorServiceIT.class.getResourceAsStream("film_actor_film_id_category_count.txt");
                 Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNext()) {
                    map.put(scanner.nextInt(), scanner.nextInt());
                }
            }
            FILM_ID_CATEGORY_COUNT = unmodifiableMap(map);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmCategoryServiceIT() {
        super(FilmCategoryService.class, FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
