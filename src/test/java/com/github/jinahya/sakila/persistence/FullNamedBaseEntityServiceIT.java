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
                .isSortedAccordingTo(
                        FullNamedBaseEntity.COMPARING_LAST_NAME_IGNORE_CASE.thenComparing(BaseEntity.COMPARING_ID))
        ;
    }

    // -----------------------------------------------------------------------------------------------------------------
    final String tableName;
}
