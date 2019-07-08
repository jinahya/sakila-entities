package com.github.jinahya.mysql.sakila.persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;

import static com.github.jinahya.mysql.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;
import static com.github.jinahya.mysql.sakila.persistence.Film.COLUMN_NAME_FILM_ID;
import static com.github.jinahya.mysql.sakila.persistence.Film.TABLE_NAME;
import static java.lang.Math.toIntExact;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = COLUMN_NAME_FILM_ID, nullable = false))
@Entity
@Table(name = TABLE_NAME)
public class Film extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity. The value is {@value}.
     */
    public static final String TABLE_NAME = "film";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The primary key column name of this entity. The value is {@value}.
     */
    // A surrogate primary key used to uniquely identify each film in the table.
    // film_id SMALLINT(5) PK NN UN AI
    public static final String COLUMN_NAME_FILM_ID = "film_id";

    // -----------------------------------------------------------------------------------------------------------------
    // The title of the film.
    // title VARCHAR(255) NN
    public static final String COLUMN_NAME_TITLE = "title";

    public static final String ATTRIBUTE_NAME_TITLE = "title";

    public static final int SIZE_MAX_TITLE = 255;

    // -----------------------------------------------------------------------------------------------------------------
    // A short description or plot summary of the film
    // description VARCHAR(255) NULL
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    public static final String ATTRIBUTE_NAME_DESCRIPTION = "description";

    public static final int SIZE_MAX_DESCRIPTION = 65535; // TEXT

    // -----------------------------------------------------------------------------------------------------------------
    // The year in which the movie was released.
    // release_year YEAR NULL
    // https://dev.mysql.com/doc/refman/8.0/en/year.html
    public static final String COLUMN_NAME_RELEASE_YEAR = "release_year";

    public static final String ATTRIBUTE_NAME_RELEASE_YEAR = "releaseYear";

    public static final int MIN_RELEASE_YEAR = 1901;

    public static final int MAX_RELEASE_YEAR = 2155;

    // -----------------------------------------------------------------------------------------------------------------
    // A foreign key pointing at the language table; identifies the language of the film.
    // language_id TINYINT(3) NN UN
    public static final String COLUMN_NAME_LANGUAGE_ID = "language_id";

    public static final String ATTRIBUTE_NAME_LANGUAGE = "language";

    // -----------------------------------------------------------------------------------------------------------------
    // A foreign key pointing at the language table; identifies the original language of the film.
    // Used when a film has been dubbed into a new language.
    // original_language_id TINYINT(3) UN NULL
    public static final String COLUMN_NAME_ORIGINAL_LANGUAGE_ID = "original_language_id";

    public static final String ATTRIBUTE_NAME_ORIGINAL_LANGUAGE = "originalLanguage";

    // -----------------------------------------------------------------------------------------------------------------
    // The length of the rental period, in days.
    // TINYINT(3) NN UN '3'
    public static final String COLUMN_NAME_RENTAL_DURATION = "rental_duration";

    public static final String ATTRIBUTE_NAME_RENTAL_DURATION = "rentalDuration";

    public static final int MIN_RENTAL_DURATION = 0; // 1?

    public static final int MAX_RENTAL_DURATION = 255;

    // -----------------------------------------------------------------------------------------------------------------
    // The cost to rent the film for the period specified in the rental_duration column.
    // DECIMAL(4,2) NN '4.99'
    public static final String COLUMN_NAME_RENTAL_RATE = "rental_rate";

    public static final String ATTRIBUTE_NAME_RENTAL_RATE = "rentalRate";

    public static final int DIGITS_INTEGER_RENTAL_RATE = 4;

    public static final int DIGITS_FRACTION_RENTAL_RATE = 2;

    public static final String DECIMAL_MIN_RENTAL_RATE = "0000.00"; // free?

    public static final String DECIMAL_MAX_RENTAL_RATE = "9999.99"; // really?

    // -----------------------------------------------------------------------------------------------------------------
    // The duration of the film, in minutes.
    // SMALLINT(5) UN NULL
    public static final String COLUMN_NAME_LENGTH = "length";

    public static final String ATTRIBUTE_NAME_LENGTH = "length";

    public static final int MIN_LENGTH = 0; // less than a minute?

    public static final int MAX_LENGTH = 65535; // 1092.25 hours?

    // -----------------------------------------------------------------------------------------------------------------
    // The amount charged to the customer if the film is not returned or is returned in a damaged state.
    // DECIMAL(5,2) NN '19.99'
    public static final String COLUMN_NAME_REPLACEMENT_COST = "replacement_cost";

    public static final String ATTRIBUTE_NAME_REPLACEMENT_COST = "replacementCost";

    public static final int DIGITS_INTEGER_REPLACEMENT_COST = 5;

    public static final int DIGITS_FRACTION_REPLACEMENT_COST = 2;

    public static final String DECIMAL_MIN_REPLACEMENT_COST = "00000.00"; // no charge at all?

    public static final String DECIMAL_MAX_REPLACEMENT_COST = "99999.99"; // seriously?

    // -----------------------------------------------------------------------------------------------------------------
    // The rating assigned to the film. Can be one of: G, PG, PG-13, R, or NC-17.
    // ENUM('G', 'PG', 'PG-13', 'R', 'NC-17') 'G'
    public static final String COLUMN_NAME_RATING = "rating";

    public static final String ATTRIBUTE_NAME_RATING = "rating";

    /**
     * Rating for {@value}(General Audiences). <i>All ages admitted</i>. Nothing that would offend parents for viewing
     * by children.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public static final String RATING_G = "G";

    /**
     * Rating for {@value}(Parental Guidance Suggested). <i>Some material may not be suitable for children</i>. Parents
     * urged to give "parental guidance". May contain some material parents might not like for their young children.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public static final String RATING_PG = "PG";

    /**
     * Rating for {@value}(Parents Strongly Cautioned). <i>Some material may be inappropriate for children under 13</i>.
     * Parents are urged to be cautious. Some material may be inappropriate for pre-teenagers.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public static final String RATING_PG_13 = "PG-13";

    /**
     * Rating for {@value}(Restricted). <i>Under 17 requires accompanying parent or adult guardian</i>. Contains some
     * adult material. Parents are urged to learn more about the film before taking their young children with them.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public static final String RATING_R = "R";

    /**
     * Rating for {@value}(Adults Only). <i>No One 17 and Under Admitted</i>. Clearly adult. Children are not admitted.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public static final String RATING_NC_17 = "NC-17";

    /**
     * Constants for {@link #ATTRIBUTE_NAME_RATING} attribute.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system#MPAA_film_ratings">MPAA
     * film ratings (Wikipedia)</a>
     */
    public enum Rating {
        /**
         * Constant for {@link #RATING_G}.
         */
        GENERAL_AUDIENCES("G"),
        /**
         * Constants for {@link #RATING_PG}.
         */
        PARENTAL_GUIDANCE_SUGGESTED("PG"),
        /**
         * Constants for {@link #RATING_PG_13}.
         */
        PARENTS_STRONGLY_CAUTIONED("PG-13"),
        /**
         * Constants for {@link #RATING_R}.
         */
        RESTRICTED("R"),
        /**
         * Constants for {@link #RATING_NC_17}.
         */
        ADULTS_ONLY("NC-17");

        // -------------------------------------------------------------------------------------------------------------

        /**
         * An attribute converter for converting constants to database values and vice versa.
         */
        public static class RatingAttributeConverter implements AttributeConverter<Rating, String> {

            @Override
            public String convertToDatabaseColumn(final Rating attribute) {
                return ofNullable(attribute).map(Rating::getDbData).orElse(null);
            }

            @Override
            public Rating convertToEntityAttribute(final String dbData) {
                return ofNullable(dbData).map(Rating::valueOfDbData).orElse(null);
            }
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Returns the value whose {@code dbData} value is equals to specified value.
         *
         * @param dbData the value for {@code dbData} to match.
         * @return The matched value.
         */
        public static Rating valueOfDbData(final String dbData) {
            if (dbData == null) {
                throw new NullPointerException("dbData is null");
            }
            for (final Rating value : values()) {
                if (value.dbData.equals(dbData)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("unknown dbData: " + dbData);
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Creates a new instance.
         *
         * @param dbData a value for {@code dbData}.
         */
        Rating(final String dbData) {
            this.dbData = requireNonNull(dbData);
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Returns the value of {@code dbData}.
         *
         * @return the value of {@code dbData}.
         */
        public String getDbData() {
            return dbData;
        }

        // -------------------------------------------------------------------------------------------------------------
        private final String dbData;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // Lists which common special features are included on the DVD.
    // Can be zero or more of: Trailers, Commentaries, Deleted Scenes, Behind the Scenes.
    // SET('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')
    public static final String COLUMN_NAME_SPECIAL_FEATURES = "special_features";

    public static final String ATTRIBUTE_NAME_SPECIAL_FEATURES = "specialFeatures";

    /**
     * A constant for special feature of <i>trailers</i>.
     */
    public static final String SPECIAL_FEATURE_TRAILERS = "Trailers";

    /**
     * A constant for special feature of <i>commentaries</i>.
     */
    public static final String SPECIAL_FEATURE_COMMENTARIES = "Commentaries";

    /**
     * A constant for special feature of <i>deleted scenes</i>.
     */
    public static final String SPECIAL_FEATURE_DELETED_SCENES = "Deleted Scenes";

    /**
     * A constant for special feature of <i>behind the scenes</i>.
     */
    public static final String SPECIAL_FEATURE_BEHIND_THE_SCENES = "Behind the Scenes";

    /**
     * Constants for {@link #ATTRIBUTE_NAME_SPECIAL_FEATURES} attribute.
     */
    public enum SpecialFeature {
        /**
         * Constant for {@link #SPECIAL_FEATURE_TRAILERS}.
         */
        TRAILERS(SPECIAL_FEATURE_TRAILERS),
        /**
         * Constant for {@link #SPECIAL_FEATURE_COMMENTARIES}.
         */
        COMMENTARIES(SPECIAL_FEATURE_COMMENTARIES),
        /**
         * Constant for {@link #SPECIAL_FEATURE_DELETED_SCENES}.
         */
        DELETED_SCENES(SPECIAL_FEATURE_DELETED_SCENES),
        /**
         * Constant for {@link #SPECIAL_FEATURE_BEHIND_THE_SCENES}.
         */
        BEHIND_THE_SCENES(SPECIAL_FEATURE_BEHIND_THE_SCENES);

        // -------------------------------------------------------------------------------------------------------------
        public static class SpecialFeatureAttributeConverter implements AttributeConverter<SpecialFeature, String> {

            @Override
            public String convertToDatabaseColumn(final SpecialFeature attribute) {
                return ofNullable(attribute).map(SpecialFeature::getDbData).orElse(null);
            }

            @Override
            public SpecialFeature convertToEntityAttribute(final String dbData) {
                return ofNullable(dbData).map(SpecialFeature::valueOfDbData).orElse(null);
            }
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Returns the value whose {@code dbData} value is equals to specified value.
         *
         * @param dbData the value for {@code dbData} to match.
         * @return The matched value.
         */
        public static SpecialFeature valueOfDbData(final String dbData) {
            if (dbData == null) {
                throw new NullPointerException("dbData is null");
            }
            for (final SpecialFeature value : values()) {
                if (value.dbData.equals(dbData)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("unknown dbData: " + dbData);
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Creates a new instance.
         *
         * @param dbData a value for {@code dbData}.
         */
        SpecialFeature(final String dbData) {
            this.dbData = requireNonNull(dbData);
        }

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Returns the value of {@code dbData}.
         *
         * @return the value of {@code dbData}.
         */
        public String getDbData() {
            return dbData;
        }

        // -------------------------------------------------------------------------------------------------------------
        private final String dbData;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Film() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(final Language language) {
        this.language = language;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(final Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public int getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(final int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    @Transient
    public Duration getRentalDurationAsJavaTime() {
        return Duration.of(getRentalDuration(), ChronoUnit.DAYS);
    }

    public void setRentalDurationAsJavaTime(final Duration rentalDurationAsJavaTime) {
        setRentalDuration(toIntExact(requireNonNull(rentalDurationAsJavaTime, "duration is null").toDays()));
    }

    // -----------------------------------------------------------------------------------------------------------------
    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(final BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Integer getLength() {
        return length;
    }

    public void setLength(final Integer length) {
        this.length = length;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(final BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Rating getRating() {
        return rating;
    }

    public void setRating(final Rating rating) {
        this.rating = rating;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public EnumSet<SpecialFeature> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(final EnumSet<SpecialFeature> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = SIZE_MAX_TITLE)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TITLE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_TITLE)
    private String title;

    @Size(max = SIZE_MAX_DESCRIPTION)
    @Basic
    @Column(name = COLUMN_NAME_DESCRIPTION)
    @NamedAttribute(ATTRIBUTE_NAME_DESCRIPTION)
    private String description;

    @Max(MAX_RELEASE_YEAR)
    @Min(MIN_RELEASE_YEAR)
    @Basic
    @Column(name = COLUMN_NAME_RELEASE_YEAR)
    @NamedAttribute(ATTRIBUTE_NAME_RELEASE_YEAR)
    private Integer releaseYear;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_LANGUAGE_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LANGUAGE)
    private Language language;

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME_ORIGINAL_LANGUAGE_ID)
    @NamedAttribute(ATTRIBUTE_NAME_ORIGINAL_LANGUAGE)
    private Language originalLanguage;

    @Max(MAX_RENTAL_DURATION)
    @Min(MIN_RENTAL_DURATION)
    @NotNull
    //@Convert(converter = RentalDurationAttributeConverter.class)
    @Column(name = COLUMN_NAME_RENTAL_DURATION, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_RENTAL_DURATION)
    private int rentalDuration;

    @DecimalMax(DECIMAL_MAX_RENTAL_RATE)
    @DecimalMin(DECIMAL_MIN_RENTAL_RATE)
    @Digits(integer = DIGITS_INTEGER_RENTAL_RATE, fraction = DIGITS_FRACTION_RENTAL_RATE)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_RENTAL_RATE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_RENTAL_RATE)
    private BigDecimal rentalRate;

    @Max(MAX_LENGTH)
    @Min(MIN_LENGTH)
    @Basic
    @Column(name = COLUMN_NAME_LENGTH)
    @NamedAttribute(ATTRIBUTE_NAME_LENGTH)
    private Integer length;

    @DecimalMax(DECIMAL_MAX_REPLACEMENT_COST)
    @DecimalMin(DECIMAL_MIN_REPLACEMENT_COST)
    @Digits(integer = DIGITS_INTEGER_REPLACEMENT_COST, fraction = DIGITS_FRACTION_REPLACEMENT_COST)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_REPLACEMENT_COST, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_REPLACEMENT_COST)
    private BigDecimal replacementCost;

    @Convert(converter = Rating.RatingAttributeConverter.class) //@Enumerated(EnumType.STRING)
    @Column(name = COLUMN_NAME_RATING)
    @NamedAttribute(ATTRIBUTE_NAME_RATING)
    private Rating rating;

    @Convert(converter = SpecialFeature.SpecialFeatureAttributeConverter.class) //@Enumerated(EnumType.STRING)
    @Column(name = COLUMN_NAME_SPECIAL_FEATURES)
    @NamedAttribute(ATTRIBUTE_NAME_SPECIAL_FEATURES)
    private EnumSet<SpecialFeature> specialFeatures;
}
