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
