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

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * A class for testing {@link FilmCategoryService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmCategoryServiceIT extends EntityServiceIT<FilmCategoryService, FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable map of category ids and film count.
     */
    private static final Map<Integer, Integer> CATEGORY_ID_FILM_COUNT;

    static {
        try {
            CATEGORY_ID_FILM_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_category_map_category_id_film_count.txt",
                    s -> {
                        final Map<Integer, Integer> map = new HashMap<>();
                        while (s.hasNext()) {
                            final Integer categoryId = s.nextInt();
                            final int filmCount = s.nextInt();
                            final Integer previous = map.put(categoryId, filmCount);
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
     * An unmodifiable map of film ids and category count.
     */
    private static final Map<Integer, Integer> FILM_ID_CATEGORY_COUNT;

    static {
        try {
            FILM_ID_CATEGORY_COUNT = unmodifiableMap(applyResourceScanner(
                    "film_category_map_film_id_category_count.txt",
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
     * Provides arguments for {@link #testCountCategories(Film)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestCountCategories() {
        return IntStream.range(0, 8).mapToObj(i -> randomEntity(Film.class)).map(Arguments::of);
    }

    /**
     * Provides arguments for {@link #testListCategories(Film, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListCategories() {
        return IntStream.range(0, 8).mapToObj(
                i -> arguments(randomEntity(Film.class), 0, entityCountAsInt(Category.class)));
    }

    /**
     * Provides arguments for {@link #testCountFilms(Category)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestCountFilms() {
        return IntStream.range(0, 8).mapToObj(i -> randomEntity(Category.class)).map(Arguments::of);
    }

    /**
     * Provides arguments for {@link #testListFilms(Category, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListFilms() {
        return IntStream.range(0, 8).mapToObj(
                i -> arguments(randomEntity(Category.class), firstResult(Film.class), maxResults(Film.class)));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmCategoryServiceIT() {
        super(FilmCategoryService.class, FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmCategoryService#countCategories(Film)}.
     *
     * @param film a value for {@code film} parameter.
     */
    // TODO: 2019-07-21 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"argumentsForTestCountCategories"})
    @ParameterizedTest
    void testCountCategories(@NotNull final Film film) {
        final long expected = FilmServiceIT.FILM_ID_CATEGORY_COUNT.get(film.getId());
        final long actual = serviceInstance().countCategories(film);
        assertThat(actual).isEqualTo(expected);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmCategoryService#listCategories(Film, Integer, Integer)} method with specified arguments.
     *
     * @param film        the film whose categories are listed as sorted by {@link Category#ATTRIBUTE_NAME_NAME name}
     *                    attribute in ascending order.
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-21 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"argumentsForTestListCategories"})
    @ParameterizedTest
    void testListCategories(@NotNull final Film film, @PositiveOrZero @Nullable final Integer firstResult,
                            @Positive @Nullable final Integer maxResults) {
        final List<Category> list = serviceInstance().listCategories(film, firstResult, maxResults);
        assertThat(list)
                .isSortedAccordingTo(Category.COMPARING_NAME)
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmCategoryService#countFilms(Category)} method with specified category.
     *
     * @param category the category whose films are counted.
     */
    // TODO: 2019-07-21 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"argumentsForTestCountFilms"})
    @ParameterizedTest
    void testCountFilms(@NotNull final Category category) {
        final long expected = CategoryServiceIT.CATEGORY_ID_FILM_COUNT.get(category.getId());
        final long actual = serviceInstance().countFilms(category);
        assertThat(actual).isEqualTo(expected);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FilmCategoryService#listFilms(Category, Integer, Integer)} method with specified arguments.
     *
     * @param category    a category whose films are listed as sorted by {@link Film#ATTRIBUTE_NAME_TITLE title}
     *                    attribute} in ascending order. attribute in ascending order.
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-21 enable, assert fails, implement, and assert passes.
    @Disabled
    @MethodSource({"argumentsForTestListCategories"})
    @ParameterizedTest
    void testListFilms(@NotNull final Category category, @PositiveOrZero @Nullable final Integer firstResult,
                       @Positive @Nullable final Integer maxResults) {
        final List<Film> list = serviceInstance().listFilms(category, firstResult, maxResults);
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(Film.COMPARING_TITLE)
        ;
    }
}
