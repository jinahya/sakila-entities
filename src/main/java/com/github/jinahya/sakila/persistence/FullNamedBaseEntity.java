package com.github.jinahya.sakila.persistence;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.sakila.persistence.FullName.ATTRIBUTE_NAME_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.ATTRIBUTE_NAME_LAST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.COLUMN_NAME_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.COLUMN_NAME_LAST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.SIZE_MAX_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.SIZE_MAX_LAST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.SIZE_MIN_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullName.SIZE_MIN_LAST_NAME;

@Deprecated
@MappedSuperclass
abstract class FullNamedBaseEntity extends BaseEntity implements FullNamed {

    // ------------------------------------------------------------------------------------------------------- firstName
    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_FIRST_NAME, max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false, length = SIZE_MAX_FIRST_NAME)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    @Size(min = SIZE_MIN_LAST_NAME, max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, length = SIZE_MAX_LAST_NAME)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;
}
