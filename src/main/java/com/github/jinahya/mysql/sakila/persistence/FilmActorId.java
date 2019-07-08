package com.github.jinahya.mysql.sakila.persistence;

import java.io.Serializable;
import java.util.Objects;

public class FilmActorId implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmActorId() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "actorId=" + actorId
               + ",filmId=" + filmId
               + "}";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilmActorId)) {
            return false;
        }
        final FilmActorId that = (FilmActorId) obj;
        return getActorId() == that.getActorId() && getFilmId() == that.getFilmId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActorId(), getFilmId());
    }

    // -----------------------------------------------------------------------------------------------------------------

    public int getActorId() {
        return actorId;
    }

    public void setActorId(final int actorId) {
        this.actorId = actorId;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(final int filmId) {
        this.filmId = filmId;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NamedAttribute(FilmActor.ATTRIBUTE_NAME_ACTOR_ID)
    private int actorId;

    @NamedAttribute(FilmActor.ATTRIBUTE_NAME_FILM_ID)
    private int filmId;
}
