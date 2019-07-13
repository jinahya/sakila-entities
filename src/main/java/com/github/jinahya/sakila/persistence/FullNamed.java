package com.github.jinahya.sakila.persistence;

// TODO: 2019-07-13 remove!!!
@Deprecated
interface FullNamed {

    // -----------------------------------------------------------------------------------------------------------------
    static String firstNameFirst(final FullNamed object, final String delimiter) {
        return object.getFirstName() + delimiter + object.getLastName();
    }

    static String lastNameFirst(final FullNamed object, final String delimiter) {
        return object.getLastName() + delimiter + object.getFirstName();
    }

    // -----------------------------------------------------------------------------------------------------------------
    String getFirstName();

    void setFirstName(String firstName);

    // -----------------------------------------------------------------------------------------------------------------
    String getLastName();

    void setLastName(String lastName);
}
