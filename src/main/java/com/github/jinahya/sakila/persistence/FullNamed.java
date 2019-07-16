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

import java.util.Comparator;

import static java.util.Comparator.comparing;

public interface FullNamed {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The attribute name for {@link FullNamedEntity#COLUMN_NAME_FIRST_NAME} column. The value is {@value}.
     */
    String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    /**
     * The minimum size for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute. The value is {@value}.
     */
    int SIZE_MIN_FIRST_NAME = 0; // TODO: 2019-07-14 empty???

    /**
     * The maximum size for {@value #ATTRIBUTE_NAME_FIRST_NAME} attribute. The value is {@value}.
     */
    int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The object attribute name for {@link FullNamedEntity#COLUMN_NAME_LAST_NAME} column. The value is {@value}.
     */
    String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    /**
     * The minimum size for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute. The value is {@value}.
     */
    int SIZE_MIN_LAST_NAME = 0; // TODO: 2019-07-14 empty???

    /**
     * The maximum size for {@value #ATTRIBUTE_NAME_LAST_NAME} attribute. The value is {@value}.
     */
    int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    Comparator<FullNamed> COMPARING_FIRST_NAME = comparing(FullNamed::getFirstName);

    static Comparator</*? super */FullNamed> comparingFirstName0(final boolean natural) {
        return natural ? COMPARING_FIRST_NAME : COMPARING_FIRST_NAME.reversed();
    }

    Comparator<FullNamed> COMPARING_LAST_NAME = comparing(FullNamed::getLastName);

    static Comparator</*? super */FullNamed> comparingLastName0(final boolean natural) {
        return natural ? COMPARING_LAST_NAME : COMPARING_LAST_NAME.reversed();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> Comparator</*? super */T> comparingFirstNameNatural() {
        return comparing(FullNamed::getFirstName);
    }

    static <T extends FullNamed> Comparator</*? super */T> comparingFirstNameReverse() {
        return FullNamed.<T>comparingFirstNameNatural().reversed();
    }

    static <T extends FullNamed> Comparator</*? super */T> comparingFirstName(final boolean natural) {
        return natural ? comparingFirstNameNatural() : comparingFirstNameReverse();
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamed> Comparator</*? super */T> comparingLastNameNatural() {
        return comparing(FullNamed::getLastName);
    }

    static <T extends FullNamed> Comparator</*? super */T> comparingLastNameReverse() {
        return FullNamed.<T>comparingLastNameNatural().reversed();
    }

    static <T extends FullNamed> Comparator</*? super */T> comparingLastName(final boolean natural) {
        return natural ? comparingLastNameNatural() : comparingLastNameReverse();
    }

    // -----------------------------------------------------------------------------------------------------------------
    String getFirstName();

    void setFirstName(String firstName);

    // -----------------------------------------------------------------------------------------------------------------
    String getLastName();

    void setLastName(String lastName);
}
