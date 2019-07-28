package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestReporter;

import static com.github.jinahya.sakila.persistence.FullNamedTests.countByFirstName;
import static com.github.jinahya.sakila.persistence.FullNamedTests.randomFirstName;
import static com.github.jinahya.sakila.persistence.PersistenceProducer.applyEntityManager;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

abstract class FullNamedBaseEntityServiceIT<
        T extends BaseEntityService<U> & FullNamedBaseEntityService<U>, U extends FullNamedBaseEntity>
        extends BaseEntityServiceIT<T, U> {

    // -----------------------------------------------------------------------------------------------------------------
    FullNamedBaseEntityServiceIT(final Class<T> serviceClass, final Class<U> entityClass, final String tableName) {
        super(serviceClass, entityClass);
        this.tableName = requireNonNull(tableName, "tableName is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @RepeatedTest(16)
    void testCountByFirstName(final TestReporter testReporter) {
        final String firstName = randomFirstName(entityManager(), tableName);
        testReporter.publishEntry("firstName", firstName);
        final long expected = countByFirstName(entityManager(), tableName, firstName);
        final long actual = serviceInstance().countByFirstName(firstName);
        assertThat(actual).isEqualTo(expected);
    }

    // -----------------------------------------------------------------------------------------------------------------
    final String tableName;
}
