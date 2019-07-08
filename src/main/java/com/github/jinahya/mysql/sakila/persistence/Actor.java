package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.mysql.sakila.persistence.Actor.COLUMN_NAME_ACTOR_ID;
import static com.github.jinahya.mysql.sakila.persistence.Actor.TABLE_NAME;
import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;

/**
 * An entity class for binding {@value TABLE_NAME} table.
 * <blockquote>
 * The {@code actor} table lists information for all actors.
 * <p>
 * The {@code actor} table is joined to the {@link Film film} table by means of the {@link FilmActor film_actor} table.
 * </blockquote>
 *
 * @see <a href="https://dev.mysql.com/doc/sakila/en/sakila-structure-tables-actor.html">The actor table (Sakila Sample
 * Database)</a>
 */
@AttributeOverrides(value = {
        @AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_ACTOR_ID, nullable = false))
})
@Entity
@Table(name = TABLE_NAME)
public class Actor extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of {@link Actor} entity. The value is {@value}.
     */
    public static final String TABLE_NAME = "actor";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ACTOR_ID = "actor_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    public static final String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    public static final int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    public static final int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Actor() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + "{"
               + "firstName=" + firstName
               + ",lastName=" + lastName
               + "}";
    }

    // ------------------------------------------------------------------------------------------------------- firstName

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute with specified value.
     *
     * @param firstName new value for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    @Size(max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;
}
