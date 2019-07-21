package com.github.jinahya.sakila.persistence;

/**
 * A service class for {@link Film}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmService extends BaseEntityService<Film> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmService() {
        super(Film.class);
    }
}
