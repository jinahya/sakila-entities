package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestReporter;

import java.util.List;

import static com.github.jinahya.sakila.persistence.FullNamedTests.countByFirstName;
import static com.github.jinahya.sakila.persistence.FullNamedTests.randomFirstName;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
abstract class FullNamedBaseEntityServiceIT<
        T extends BaseEntityService<U> & FullNamedBaseEntityService<U>, U extends FullNamedBaseEntity>
        extends BaseEntityServiceIT<T, U> {

    // -----------------------------------------------------------------------------------------------------------------
    FullNamedBaseEntityServiceIT(final Class<T> serviceClass, final Class<U> entityClass, final String tableName) {
        super(serviceClass, entityClass);
        this.tableName = requireNonNull(tableName, "tableName is null");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link FullNamedBaseEntityService#countByFirstName(String)} method.
     *
     * @param testReporter a test reporter.
     */
    @RepeatedTest(16)
    void testCountByFirstName(final TestReporter testReporter) {
        final String firstName = randomFirstName(entityManager(), tableName);
        testReporter.publishEntry("firstName", firstName);
        final long expected = countByFirstName(entityManager(), tableName, firstName);
        log.debug("expected: {}", expected);
        final long actual = serviceInstance().countByFirstName(firstName);
        log.debug("actual: {}", actual);
        assertThat(actual).isEqualTo(expected);
    }

    @RepeatedTest(16)
    void testListByFirstName(final TestReporter testReporter) {
        final String firstName = randomFirstName(entityManager(), tableName);
        testReporter.publishEntry("firstName", firstName);
        final Integer firstResult = current().nextBoolean() ? null : 0;
        final Integer maxResults = current().nextBoolean() ? null : current().nextInt(3);
        final List<U> list = serviceInstance().listByFirstName(firstName, firstResult, maxResults);
        log.debug("listByFirstName({}): {}", firstName, list);
        assertThat(list)
                .isNotNull()
                .isNotEmpty()
                .isSortedAccordingTo(FullNamed.COMPARING_LAST_NAME_IGNORE_CASE)
    }

    // -----------------------------------------------------------------------------------------------------------------
    final String tableName;
}
