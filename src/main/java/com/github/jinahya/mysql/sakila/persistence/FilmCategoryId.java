package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmCategoryId implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public FilmCategoryId() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.toString() + "{"
               + "filmId=" + filmId
               + ",categoryId=" + categoryId
               + "}";
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilmCategoryId)) {
            return false;
        }
        final FilmCategoryId that = (FilmCategoryId) obj;
        return getFilmId() == that.getFilmId() && getCategoryId() == that.getCategoryId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilmId(), getCategoryId());
    }

    // ---------------------------------------------------------------------------------------------------------- filmId
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(final int filmId) {
        this.filmId = filmId;
    }

    // ------------------------------------------------------------------------------------------------------ categoryId
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final int categoryId) {
        this.categoryId = categoryId;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = false)
    @Column(name = FilmCategory.COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(FilmCategory.ATTRIBUTE_NAME_FILM_ID)
    private int filmId;

    @Basic(optional = false)
    @Column(name = FilmCategory.COLUMN_NAME_CATEGORY_ID, nullable = false)
    @NamedAttribute(FilmCategory.ATTRIBUTE_NAME_CATEGORY_ID)
    private int categoryId;
}
