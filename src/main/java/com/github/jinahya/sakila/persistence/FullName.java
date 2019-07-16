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
import java.util.Comparator;
import java.util.Objects;

import static com.github.jinahya.sakila.persistence.FullNamedEntity.COLUMN_NAME_FIRST_NAME;
import static com.github.jinahya.sakila.persistence.FullNamedEntity.COLUMN_NAME_LAST_NAME;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

/**
 * An embeddable class for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute and {@value #ATTRIBUTE_NAME_LAST_NAME}
 * attribute.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Embeddable
public class FullName implements FullNamed, Serializable {

    // -----------------------------------------------------------------------------------------------------------------
    private static final long serialVersionUID = 672072114529715565L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final Comparator</*? super */FullName> COMPARING_FIRST_NAME_NATURAL
            = comparing(FullName::getFirstName);

    public static final Comparator</*? super */FullName> COMPARING_FIRST_NAME_REVERSE
            = COMPARING_FIRST_NAME_NATURAL.reversed();

    // -----------------------------------------------------------------------------------------------------------------
    public static final Comparator</*? super */FullName> COMPARING_LAST_NAME_NATURAL
            = comparing(FullName::getLastName);

    public static final Comparator</*? super */FullName> COMPARING_LAST_NAME_REVERSE
            = COMPARING_LAST_NAME_NATURAL.reversed();

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified first name and last name.
     *
     * @param firstName a value for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     * @param lastName  a value for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     * @return a new instance.
     */
    public static FullName of(final String firstName, final String lastName) {
        final FullName instance = new FullName();
        instance.setFirstName(firstName);
        instance.setLastName(lastName);
        return instance;
    }

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
     * @param delimiter the delimiter.
     * @return a string of {@value #ATTRIBUTE_NAME_FIRST_NAME} and {@value #ATTRIBUTE_NAME_LAST_NAME} joined with
     * specified delimiter.
     * @deprecated Use {@link #toString(FullNameFormatter, String)}
     */
    // TODO: 2019-07-13 remove!!!!
    @Deprecated // forRemoval = true
    public String getAsFirstNameFirst(@NotNull final String delimiter) {
        if (true) {
            return toString(FullNameFormatter.FIRST_NAME_FIRST, delimiter);
        }
        return firstName + delimiter + lastName;
    }

    /**
     * Returns a full name as first name first delimited with a white space.
     *
     * @return a full name as first name first delimited with a white space.
     * @deprecated Use {@link #toString(FullNameFormatter, String)}
     */
    // TODO: 2019-07-13 remove!!!
    @Deprecated // forRemoval = true
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
     * @deprecated Use {@link #toString(FullNameFormatter, String)}
     */
    // TODO: 2019-07-13 remove!!!
    @Deprecated // forRemoval = true
    public String getAsLastNameFirst(@NotNull final String delimiter) {
        if (true) {
            return toString(FullNameFormatter.LAST_NAME_FIRST, delimiter);
        }
        return lastName + delimiter + firstName;
    }

    /**
     * Returns a full name as last name first delimited with a comma followed by a white space.
     *
     * @return a full name as last name first delimited with a comma followed by a white space.
     * @deprecated Use {@link #toString(FullNameFormatter, String)}
     */
    // TODO: 2019-07-13 remove!!!
    @Deprecated // forRemoval = true
    public String getAsLastNameFirst() {
        return getAsLastNameFirst(", ");
    }

    /**
     * Returns a full name of this object using specified order and delimiter.
     *
     * @param order     the order of the name.
     * @param delimiter the delimiter.
     * @return a formatted full name of this object.
     */
    public String toString(final FullNameFormatter order, final String delimiter) {
        return requireNonNull(order, "order is null").format(this, requireNonNull(delimiter, "delimiter is null"));
    }

    // ------------------------------------------------------------------------------------------------------- firstName

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute with specified value.
     *
     * @param firstName new value for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute.
     */
    @Override
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public FullName firstName(final String firstName) {
        setFirstName(firstName);
        return this;
    }

    // -------------------------------------------------------------------------------------------------------- lastName

    /**
     * Returns the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     *
     * @return the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces the current value of {@value #ATTRIBUTE_NAME_LAST_NAME} attribute with specified value.
     *
     * @param lastName new value for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute.
     */
    @Override
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public FullName lastName(final String lastName) {
        setLastName(lastName);
        return this;
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
