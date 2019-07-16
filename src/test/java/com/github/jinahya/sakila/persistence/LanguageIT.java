package com.github.jinahya.sakila.persistence;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSortedSet;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A class for integration-testing {@link Language}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
class LanguageIT extends BaseEntityIT<Language> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The total number of {@link Language}.
     */
    static final int LANGUAGE_COUNT = entityCountAsInt(Language.class);

    /**
     * Returns a random instance of {@link Language}.
     *
     * @return a random instance of {@link Language}.
     */
    static Language randomLanguage() {
        return randomEntity(Language.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A sorted set of values of {@link Language#ATTRIBUTE_NAME_NAME} attribute.
     */
    private static final SortedSet<String> NAMES;

    static {
        try {
            NAMES = unmodifiableSortedSet(applyResourceScanner("language_set_name.txt", s -> {
                final SortedSet<String> set = new TreeSet<>();
                while (s.hasNext()) {
                    set.add(s.next());
                }
                return set;
            }));
        } catch (final IOException ioe) {
            throw new InstantiationError(ioe.getMessage());
        }
    }

    static String randomName() {
        return NAMES.stream()
                .skip(current().nextLong(NAMES.size()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("you should't see me"));
    }

    // -----------------------------------------------------------------------------------------------------------------
    LanguageIT() {
        super(Language.class);
    }
}
