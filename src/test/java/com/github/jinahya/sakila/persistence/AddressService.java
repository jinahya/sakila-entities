package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * A service class for {@link Address} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class AddressService extends BaseEntityService<Address> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    AddressService() {
        super(Address.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long count(@NotNull final City city) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @NotNull List<@NotNull Address> list(@NotNull final City city) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @PositiveOrZero long count(@NotNull final City city, @NotNull final String district) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    @NotNull List<@NotNull Address> list(@NotNull final City city, @NotNull final String restrict) {
        // TODO: 2019-07-27 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
