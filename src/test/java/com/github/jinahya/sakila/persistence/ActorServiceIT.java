package com.github.jinahya.sakila.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static com.github.jinahya.sakila.persistence.FullNameEmbedded.comparingFirstName;
import static java.lang.StrictMath.toIntExact;
import static java.util.Collections.unmodifiableSet;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A class for testing {@link ActorService}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class ActorServiceIT extends BaseEntityServiceIT<ActorService, Actor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A set of first names.
     */
    static final Set<String> FIRST_NAMES;

    static {
        final Set<String> set = new LinkedHashSet<>();
        try {
            try (InputStream stream = ActorServiceIT.class.getResourceAsStream("actor_set_first_name.txt");
                 Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNext()) {
                    set.add(scanner.next());
                }
            }
            FIRST_NAMES = unmodifiableSet(set);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A set of last names.
     */
    static final Set<String> LAST_NAMES;

    static {
        final Set<String> set = new LinkedHashSet<>();
        try {
            try (InputStream stream = ActorServiceIT.class.getResourceAsStream("actor_set_last_name.txt");
                 Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNext()) {
                    set.add(scanner.next());
                }
            }
            LAST_NAMES = unmodifiableSet(set);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new InstantiationError(ioe.getMessage());
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorServiceIT() {
        super(ActorService.class, Actor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link ActorService#streamOrderedByFirstName(String, boolean, Integer, Integer)} method.
     */
    @RepeatedTest(8)
    void testStreamOrderByFirstName() {
        final String lastName = current().nextBoolean() ? null : randomEntity().getLastName();
        final boolean ascendingOrder = current().nextBoolean();
        final Integer firstResult = current().nextBoolean() ? null : current().nextInt(toIntExact(entityCount()));
        final Integer maxResult = current().nextBoolean() ? null : current().nextInt(1, toIntExact(entityCount() + 1));
        log.debug("lastName: {}, ascendingOrder: {}, firstResult: {}, maxResults: {}", lastName, ascendingOrder,
                  firstResult, maxResult);
        final List<Actor> stream = serviceInstance()
                .streamOrderedByFirstName(lastName, ascendingOrder, firstResult, maxResult)
                .collect(toList());
        assertThat(stream)
                .allMatch(a -> ofNullable(lastName).map(v -> v.equals(a.getLastName())).orElse(true))
                .isSortedAccordingTo(comparingFirstName(ascendingOrder))
                .size()
                .matches(s -> ofNullable(maxResult).map(v -> s <= v).orElse(true))
        ;
    }

    /**
     * Tests {@link ActorService#streamOrderedByLastName(String, boolean, Integer, Integer)} method.
     */
    @RepeatedTest(8)
    void testStreamOrderByLastName() {
        final String firstName = current().nextBoolean() ? null : randomEntity().getFirstName();
        final boolean ascendingOrder = current().nextBoolean();
        final Integer firstResult = current().nextBoolean() ? null : current().nextInt(toIntExact(entityCount()));
        final Integer maxResult = current().nextBoolean() ? null : current().nextInt(1, toIntExact(entityCount() + 1));
        log.debug("firstName: {}, ascendingOrder: {}, firstResult: {}, maxResults: {}", firstName, ascendingOrder,
                  firstResult, maxResult);
        final List<Actor> stream = serviceInstance()
                .streamOrderedByLastName(firstName, ascendingOrder, firstResult, maxResult)
                .collect(toList());
        assertThat(stream)
                .allMatch(a -> ofNullable(firstName).map(v -> v.equals(a.getFirstName())).orElse(true))
                .isSortedAccordingTo(comparingFirstName(ascendingOrder))
                .size()
                .matches(s -> ofNullable(maxResult).map(v -> s <= v).orElse(true))
        ;
    }
}
