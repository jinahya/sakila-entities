package com.github.jinahya.sakila.persistence;

/**
 * An assertion for {@link Address} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class AddressAssertion extends BaseEntityAssert<AddressAssertion, Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance with specified actual value.
     *
     * @param actual the actual value.
     */
    AddressAssertion(final Address actual) {
        super(actual);
    }
}
