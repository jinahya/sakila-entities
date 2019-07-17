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

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Language.comparingName;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * An integration test class for {@link LanguageService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;q
 */
class LanguageServiceIT extends BaseEntityServiceIT<LanguageService, Language> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The total number of {@link Language}.
     */
    static final int LANGUAGE_COUNT = entityCountAsInt(Language.class);

    /**
     * Returns a random instance of {@link Language}.
     *
     * @return a random instance of {@link Language}.
     */
    static Language randomLanguage() {
        return randomEntity(Language.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A sorted set of values of {@link Language#ATTRIBUTE_NAME_NAME} attribute.
     */
    static final SortedSet<String> NAMES;

    static {
        try {
            NAMES = unmodifiableSortedSet(applyResourceScanner("language_set_name.txt", s -> {
                final SortedSet<String> set = new TreeSet<>();
                while (s.hasNext()) {
                    set.add(s.next());
                }
                return set;
            }));
        } catch (final IOException ioe) {
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static final Condition<String> A_NAME_IN_DATABASE = new Condition<>(NAMES::contains, "a name is in database");

    static final Condition<Language> A_LANGUAGE_WHOSE_NAME_IS_IN_DATABASE = new Condition<>(
            l -> A_NAME_IN_DATABASE.matches(l.getName()), "a language whose name is in database");

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Provides arguments for {@link #testStreamOrderedByName(boolean, Integer, Integer)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestingStreamOrderedByName() {
        return range(8, 17).mapToObj(i -> arguments(
                current().nextBoolean(), firstResult(Language.class), maxResults(Language.class)));
    }

    /**
     * Provides arguments for {@link #testFindByName(String)} method.
     *
     * @return a stream of arguments.
     */
    private static Stream<Arguments> argumentsForTestingFindByName() {
        return range(8, 17).mapToObj(i -> arguments(
                NAMES.stream()
                        .skip(current().nextLong(NAMES.size()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("fix me if you see me"))));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    LanguageServiceIT() {
        super(LanguageService.class, Language.class);
    }

    // ------------------------------------------------------------------------------------------------------ findByName

    /**
     * Asserts {@link LanguageService#findByName(String)} returns empty for an unknown language.
     */
    // TODO: 2019-07-16 enable, assert fails, implement, assert passes.
    @Disabled
    @Test
    void assertFindByNameReturnsEmptyIfNameIsUnknown() {
        final String name = "Esperanto";
        assertThat(NAMES).doesNotContain(name);
        assertThat(serviceInstance().findByName(name)).isEmpty();
    }

    /**
     * Tests {@link LanguageService#findByName(String)}.
     *
     * @param name a value for {@code name} parameter.
     */
    // TODO: 2019-07-16 enable, assert fails, implement, assert passes.
    @Disabled
    @MethodSource({"argumentsForTestingFindByName"})
    @ParameterizedTest
    void testFindByName(@NotNull final String name) {
        assertThat(serviceInstance().findByName(name))
                .isPresent()
                .hasValueSatisfying(language -> assertThat(language.getName()).isEqualTo(name))
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link LanguageService#streamOrderedByName(boolean, Integer, Integer)} method.
     *
     * @param ascendingOrder a value for {@code ascendingOrder} parameter.
     * @param firstResult    a value for {@code firstResult} parameter.
     * @param maxResults     a value for {@code maxResults} parameter.
     */
    // TODO: 2019-07-16 enable, assert fails, implement, assert passes.
    @Disabled
    @MethodSource({"argumentsForTestingStreamOrderedByName"})
    @ParameterizedTest
    void testStreamOrderedByName(final boolean ascendingOrder, @PositiveOrZero final Integer firstResult,
                                 @Positive final Integer maxResults) {
        final Stream<Language> stream = serviceInstance().streamOrderedByName(ascendingOrder, firstResult, maxResults);
        final List<Language> list = stream.collect(toList());
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(comparingName(ascendingOrder))
                .hasSizeLessThan(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }
}
