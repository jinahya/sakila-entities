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

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
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
import java.util.stream.Stream;

import static java.util.Collections.synchronizedMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * An integration test class for {@link CategoryService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;q
 */
class CategoryServiceIT extends BaseEntityServiceIT<CategoryService, Category> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An unmodifiable synchronized map of category ids and film counts.
     */
    private static final Map<Integer, Integer> CATEGORY_ID_FILM_COUNT;

    static {
        try {
            CATEGORY_ID_FILM_COUNT = synchronizedMap(applyResourceScanner(
                    "category_map_category_id_film_count.txt", s -> {
                        final Map<Integer, Integer> map = new HashMap<>();
                        while (s.hasNext()) {
                            final int categoryId = s.nextInt();
                            final int filmCount = s.nextInt();
                            final Integer previous = map.put(categoryId, filmCount);
                            assert previous == null : "duplicate category id: " + categoryId;
                        }
                        return map;
                    }
            ));
        } catch (final IOException ioe) {
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testListByName(String)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListByName() {
        return range(0, 8).mapToObj(i -> randomEntity(Category.class)).map(Arguments::of);
    }

    /**
     * Provides arguments for {@link #testListSortedByName(Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestListSortedByName() {
        return range(0, 8).mapToObj(i -> arguments(firstResult(Category.class), maxResults(Category.class)));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    CategoryServiceIT() {
        super(CategoryService.class, Category.class);
    }

    // ------------------------------------------------------------------------------------------------------ findByName

    /**
     * Asserts {@link CategoryService#listByName(String)} method returns an empty for an unknown category.
     */
    @Test
    void assertListByNameReturnsEmptyForUnknown() {
        final String name = "周星馳";
        assertThat(serviceInstance().listByName(name)).isEmpty();
    }

    /**
     * Tests {@link CategoryService#listByName(String)} method.
     *
     * @param name a value for {@code name} parameter.
     */
    @MethodSource({"argumentsForTestListByName"})
    @ParameterizedTest
    void testListByName(@NotNull final String name) {
        assertThat(serviceInstance().listByName(name))
                .isNotEmpty()
                .allSatisfy(v -> assertThat(v.getName()).isEqualTo(name))
        ;
    }

    // ------------------------------------------------------------------------------------------------ listSortedByName

    /**
     * Tests {@link CategoryService#listSortedByName(Integer, Integer)} method.
     *
     * @param firstResult a value for {@code firstResult} parameter.
     * @param maxResults  a value for {@code maxResults} parameter.
     */
    @MethodSource({"argumentsForTestListSortedByName"})
    @ParameterizedTest
    void testListSortedByName(@PositiveOrZero @Nullable final Integer firstResult,
                              @Positive @Nullable final Integer maxResults) {
        final List<Category> list = serviceInstance().listSortedByName(firstResult, maxResults);
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(Category.COMPARING_NAME)
                .hasSizeLessThanOrEqualTo(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }
}
