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
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Collections.unmodifiableSortedMap;

/**
 * A class for testing {@link FilmCategoryService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmCategoryServiceIT extends EntityServiceIT<FilmCategoryService, FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable sorted map of category id and film count.
     */
    static final SortedMap<Integer, Integer> CATEGORY_ID_FILM_COUNT;

    static {
        try {
            CATEGORY_ID_FILM_COUNT = unmodifiableSortedMap(applyResourceScanner(
                    "film_category_category_id_film_count.txt",
                    s -> {
                        final SortedMap<Integer, Integer> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final Integer categoryId = s.nextInt();
                            final Integer previous = map.put(categoryId, s.nextInt());
                            assert previous == null : "duplicate film id: " + categoryId;
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
     * An unmodifiable sorted map of film id and category count.
     */
    static final SortedMap<Integer, Integer> FILM_ID_CATEGORY_COUNT;

    static {
        try {
            FILM_ID_CATEGORY_COUNT = unmodifiableSortedMap(applyResourceScanner(
                    "film_category_film_id_category_count.txt",
                    s -> {
                        final SortedMap<Integer, Integer> map = new TreeMap<>();
                        while (s.hasNext()) {
                            final Integer filmId = s.nextInt();
                            final Integer previous = map.put(filmId, s.nextInt());
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
    FilmCategoryServiceIT() {
        super(FilmCategoryService.class, FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
