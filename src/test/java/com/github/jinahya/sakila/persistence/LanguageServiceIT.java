package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.Language.comparingName;
import static com.github.jinahya.sakila.persistence.LanguageIT.A_LANGUAGE_WHOSE_NAME_IS_IN_DATABASE;
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
                LanguageIT.NAMES.stream()
                        .skip(current().nextLong(LanguageIT.NAMES.size()))
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
        final List<Language> list = serviceInstance()
                .streamOrderedByName(ascendingOrder, firstResult, maxResults)
                .collect(toList());
        assertThat(list)
                .isNotNull()
                .isSortedAccordingTo(comparingName(ascendingOrder))
                .hasSizeLessThan(ofNullable(maxResults).orElse(Integer.MAX_VALUE))
        ;
    }

    // ------------------------------------------------------------------------------------------------------ findByName

    /**
     * Asserts {@link LanguageService#findByName(String)} returns empty for an unknown language.
     */
    @Test
    void assertFindByNameReturnsEmptyIfNameIsUnknown() {
        final String name = "Esperanto";
        assertThat(LanguageIT.NAMES).doesNotContain(name);
        assertThat(serviceInstance().findByName(name)).isEmpty();
    }

    /**
     * Tests {@link LanguageService#findByName(String)}.
     *
     * @param name a value for {@code name} parameter.
     */
    @MethodSource({"argumentsForTestingFindByName"})
    @ParameterizedTest
    void testFindByName(@NotNull final String name) {
        assertThat(serviceInstance().findByName(name))
                .isPresent()
                .hasValueSatisfying(language -> assertThat(language.getName()).isEqualTo(name))
        ;
    }
}
