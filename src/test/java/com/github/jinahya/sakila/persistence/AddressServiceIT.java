package com.github.jinahya.sakila.persistence;

/**
 * A class for integration-testing {@link AddressService} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class AddressServiceIT extends BaseEntityServiceIT<AddressService, Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressServiceIT() {
        super(AddressService.class, Address.class);
    }
}
