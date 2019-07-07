package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Film.COLUMN_NAME_FILM_ID;
import static com.github.jinahya.mysql.sakila.persistence.Film.TABLE_NAME;

@AttributeOverrides({
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_FILM_ID, nullable = false))
})
@Entity
@Table(name = TABLE_NAME)
public class FilmText {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "film_text";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FILM_ID = "film_id";

    public static final String ATTRIBUTE_NAME_FILM_ID = "filmId";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_TITLE = "title";

    public static final String ATTRIBUTE_NAME_TITLE = "title";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    public static final String ATTRIBUTE_NAME_DESCRIPTION = "description";

    // -----------------------------------------------------------------------------------------------------------------
    @Id
    @Column(name = Film.COLUMN_NAME_FILM_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FILM_ID)
    private int filmId;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TITLE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_TITLE)
    private String title;

    @Basic
    @Column(name = COLUMN_NAME_DESCRIPTION)
    @NamedAttribute(ATTRIBUTE_NAME_DESCRIPTION)
    private String description;
}
