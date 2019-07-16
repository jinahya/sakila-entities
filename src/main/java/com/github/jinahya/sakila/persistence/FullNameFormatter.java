package com.github.jinahya.sakila.persistence;

/**
 * Constants for ordering names.
 */
public enum FullNameFormatter {

    /**
     * A constant for formatting a full name as the {@link FullName#ATTRIBUTE_NAME_FIRST_NAME} attribute first.
     */
    FIRST_NAME_FIRST() {
        @Override
        String format(final FullNamed object, final String delimiter) {
            return object.getFirstName() + delimiter + object.getLastName();
        }
    },

    /**
     * A constant for formatting a full name as the {@link FullName#ATTRIBUTE_NAME_LAST_NAME} attribute first.
     */
    LAST_NAME_FIRST() {
        @Override
        String format(final FullNamed object, final String delimiter) {
            return object.getLastName() + delimiter + object.getFirstName();
        }
    };

    // -------------------------------------------------------------------------------------------------------------

    /**
     * Formats specified full name using specified delimiter.
     *
     * @param object    the full name to format.
     * @param delimiter a delimiter between names.
     * @return a formatted value for specified full name.
     */
    abstract String format(final FullNamed object, final String delimiter);
}
