package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

/**
 * A class for testing {@link Film}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmTest extends BaseEntityTest<Film> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmTest() {
        super(Film.class);
    }
}
