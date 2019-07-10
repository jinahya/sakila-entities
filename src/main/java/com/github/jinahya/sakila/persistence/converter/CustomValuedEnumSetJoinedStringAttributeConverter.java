package com.github.jinahya.sakila.persistence.converter;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

public abstract class CustomValuedEnumSetJoinedStringAttributeConverter<E extends Enum<E> & CustomValuedEnum<E, String>>
        implements AttributeConverter<Set<E>, String> {

    // -----------------------------------------------------------------------------------------------------------------
    private static Map<String, Pattern> PATTERNS;

    private static Map<String, Pattern> patterns() {
        if (PATTERNS == null) {
            PATTERNS = new WeakHashMap<>();
        }
        return PATTERNS;
    }

    /**
     * Returns a pre-compiled pattern of specified regular expression.
     *
     * @param regex the regular expression to compile.
     * @return a compiled pattern of specified regular expression.
     */
    private static Pattern pattern(final String regex) {
        return patterns().computeIfAbsent(requireNonNull(regex, "regex is null"), Pattern::compile);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public CustomValuedEnumSetJoinedStringAttributeConverter(final Class<E> enumClass, final String columnDelimiter) {
        super();
        this.enumClass = requireNonNull(enumClass, "enumClass is null");
        this.columnDelimiter = requireNonNull(columnDelimiter, "columnDelimiter is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public String convertToDatabaseColumn(final Set<E> attribute) {
        return ofNullable(attribute)
                .map(a -> a.stream().map(CustomValuedEnum::getDatabaseColumn).collect(joining(columnDelimiter)))
                .orElse(null);
    }

    @Override
    public Set<E> convertToEntityAttribute(final String column) {
        return ofNullable(column)
                .map(j -> (current().nextBoolean() ? // TODO: 2019-07-10 Choose one of them!
                           pattern(columnDelimiter).splitAsStream(j) : stream(j.split(columnDelimiter)))
                        .map(s -> CustomValuedEnum.valueOfDatabaseColumn(enumClass, s))
                        .collect(toCollection(() -> EnumSet.noneOf(enumClass))))
                .orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------
    protected final Class<E> enumClass;

    /**
     * The delimiter for splitting or joining the database column.
     */
    protected final String columnDelimiter;
}
