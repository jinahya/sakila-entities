package com.github.jinahya.sakila.persistence;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * A service class for {@link FilmCategory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class FilmCategoryService extends EntityService<FilmCategory> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmCategoryService() {
        super(FilmCategory.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts categories to which specified film is categorized.
     *
     * @param film the film whose categories are counted.
     * @return the number of categories of specified film.
     */
    public @PositiveOrZero long countCategories(@NotNull final Film film) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists categories of specified film ordered in {@link BaseEntity#ATTRIBUTE_NAME_ID} attribute in ascending order.
     *
     * @param film the film whose categories are listed.
     * @return a list of categories of specified film.
     */
    public @NotNull List<@NotNull Category> listCategories(@NotNull final Film film) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Counts distinct categories of specified films.
     *
     * @param films the films whose categories are counted.
     * @return the number of distinct categories of specified films.
     */
    public @PositiveOrZero long countCategories(@NotNull final Collection<@NotNull ? extends Film> films) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a stream of distinct categories of specified films ordered in {@link Category#ATTRIBUTE_NAME_NAME} in
     * ascending order.
     *
     * @param films the films whose distinct categories are streamed.
     * @return a stream of distinct categories of specified films.
     */
    public @NotNull Stream<? extends Category> streamCategories(
            @NotNull final Collection<@NotNull ? extends Film> films) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns the number of films categorized as specified category.
     *
     * @param category the category as which films are categorized.
     * @return the number of films categorized as specified category.
     */
    public @PositiveOrZero long countFilms(@NotNull final Category category) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Lists all films categorized as specified category ordered in {@link Film#ATTRIBUTE_NAME_TITLE} attribute in
     * ascending order.
     *
     * @param category the category as which films are categorized.
     * @return a list of films categorized as specified category.
     */
    public @NotNull List<@NotNull Category> listFilms(@NotNull final Category category) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Counts all distinct films categorized as any of specified categories.
     *
     * @param categories the categories.
     * @return the number of distinct films categorized as any of {@code categories}.
     */
    public @PositiveOrZero long countFilms(@NotNull final Collection<@NotNull ? extends Category> categories) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Returns a stream of distinct films categorized as any of specified categories ordered in {@link
     * Film#ATTRIBUTE_NAME_LENGTH} attribute in descending order.
     *
     * @param categories the categories.
     * @return a stream of distinct films which each is categorized any of {@code categories}.
     */
    public @NotNull Stream<? extends Category> streamFilms(
            @NotNull final Collection<@NotNull ? extends Category> categories) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
