package com.github.jinahya.sakila.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for testing {@link com.github.jinahya.sakila.persistence.Film.SpecialFeature}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmSpecialFeatureTest {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Asserts that none of {@link com.github.jinahya.sakila.persistence.Film.SpecialFeature} contains comma(',') in
     * their {@link com.github.jinahya.sakila.persistence.Film.SpecialFeature#databaseColumn databaseColumn} field.
     *
     * @see <a href="https://dev.mysql.com/doc/refman/8.0/en/set.html">The SET Type (MySQL Reference Manual)</a>
     */
    @Test
    void assertNoneOfDatabaseColumnsContainComma() {
        assertThat(Film.SpecialFeature.values()).allSatisfy(v -> assertThat(v.getDatabaseColumn()).doesNotContain(","));
    }
}
