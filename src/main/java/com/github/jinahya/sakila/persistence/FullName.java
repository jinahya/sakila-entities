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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An embeddable for those entities which each has two attributes for {@value #COLUMN_NAME_FIRST_NAME} column and
 * {@value #COLUMN_NAME_LAST_NAME} column.
 */
@Embeddable
public class FullName implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The column name for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute. The value is {@value}.
     */
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    /**
     * The attribute name for {@value #COLUMN_NAME_FIRST_NAME} column. The value is {@value}.
     */
    public static final String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    /**
     * The minimum size for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute. The value is {@value}.
     */
    public static final int SIZE_MIN_FIRST_NAME = 0;

    /**
     * The maximum size for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute. The value is {@value}.
     */
    public static final int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The table column name for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute. The value is {@value}.
     */
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    /**
     * The object attribute name for {@value #COLUMN_NAME_LAST_NAME} column. The value is {@value}.
     */
    public static final String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    /**
     * The minimum size for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute. The value is {@value}.
     */
    public static final int SIZE_MIN_LAST_NAME = 0;

    /**
     * The maximum size for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute. The value is {@value}.
     */
    public static final int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + "{"
               + "firstName=" + firstName
               + ",lastName=" + lastName
               + "}";
    }

    /**
     * Indicates whether some other object is "equal to" this one..
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FullName)) {
            return false;
        }
        final FullName that = (FullName) obj;
        return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName());
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string of {@value #ATTRIBUTE_NAME_FIRST_NAME} and {@value #ATTRIBUTE_NAME_LAST_NAME} joined specified
     * delimiter.
     *
     * @return a string of {@value #ATTRIBUTE_NAME_FIRST_NAME} and {@value #ATTRIBUTE_NAME_LAST_NAME} joined with
     * specified declimiter.
     */
    public String getAsFirstNameFirst(@NotNull final String delimiter) {
        return firstName + delimiter + lastName;
    }

    /**
     * Returns a full name as first name first delimited with a white space.
     *
     * @return a full name as first name first delimited with a white space.
     */
    public String getAsFirstNameFirst() {
        return getAsFirstNameFirst(" ");
    }

    /**
     * Returns a string of {@value #ATTRIBUTE_NAME_LAST_NAME} and {@value #ATTRIBUTE_NAME_FIRST_NAME} joined with
     * specified delimiter.
     *
     * @param delimiter the delimiter.
     * @return a string of {@value #ATTRIBUTE_NAME_LAST_NAME} and {@value #ATTRIBUTE_NAME_FIRST_NAME} joined with
     * specified delimiter.
     */
    public String getAsLastNameFirst(@NotNull final String delimiter) {
        return lastName + delimiter + firstName;
    }

    /**
     * Returns a full name as last name first delimited with a comma followed by a white space.
     *
     * @return a full name as last name first delimited with a comma followed by a white space.
     */
    public String getAsLastNameFirst() {
        return getAsLastNameFirst(", ");
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

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute with specified value.
     *
     * @param lastName new value for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_FIRST_NAME, max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    @Size(min = SIZE_MIN_LAST_NAME, max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;
}
