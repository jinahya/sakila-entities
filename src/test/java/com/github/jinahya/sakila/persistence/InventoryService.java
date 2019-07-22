package com.github.jinahya.sakila.persistence;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * A service class for {@link Inventory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class InventoryService extends BaseEntityService<Inventory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    InventoryService() {
        super(Inventory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of stores that specified film is available.
     *
     * @param film the film to check.
     * @return the number of stores that {@code film} is available.
     */
    public @PositiveOrZero long countStores(@NotNull final Film film) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * List stores that specified film is available sorted by {@link Country#ATTRIBUTE_NAME_COUNTRY Country#country}
     * attribute, {@link City#ATTRIBUTE_NAME_CITY City#city} attribute, {@link Address#ATTRIBUTE_NAME_DISTRICT
     * Address#district} attribute, and {@link BaseEntity#ATTRIBUTE_NAME_ID Store#storeId} attribute in all ascending
     * order.
     *
     * @param film        the film to check.
     * @param firstResult first index of the result; {@code null} for an unspecified result.
     * @param maxResults  maximum results to retrieve; {@code null} for an unspecified result.
     * @return a list of stores.
     */
    public @NotNull List<@NotNull Store> listStores(@NotNull final Film film,
                                                    @PositiveOrZero @Nullable final Integer firstResult,
                                                    @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts films available on specified store.
     *
     * @param store the store whose films are counted.
     * @return the number of films available in {@code store}.
     */
    public @PositiveOrZero long countFilms(@NotNull final Film store) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * List films available on specified store sorted by {@link Film#ATTRIBUTE_NAME_TITLE title} in ascending order.
     *
     * @param store       the store whose films are listed.
     * @param firstResult first index of the result; {@code null} for an unspecified result.
     * @param maxResults  maximum results to retrieve; {@code null} for an unspecified result.
     * @return a list of films.
     */
    public @NotNull List<@NotNull Film> listFilm(@NotNull final Store store,
                                                 @PositiveOrZero @Nullable final Integer firstResult,
                                                 @Positive @Nullable final Integer maxResults) {
        // TODO: 2019-07-22 implement!!!
        throw new UnsupportedOperationException("not implemented yet");
    }
}
