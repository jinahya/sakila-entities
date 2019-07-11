package com.github.jinahya.sakila.persistence;

import javax.persistence.EntityManager;
import java.util.Comparator;

import static java.lang.Math.toIntExact;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A class for testing {@link Actor}.
 */
class ActorIT extends BaseEntityIT<Actor> {

    // -----------------------------------------------------------------------------------------------------------------
    public static final Comparator<Actor> a = Comparator.comparing(Actor::getId);

    // -----------------------------------------------------------------------------------------------------------------
    static Long COUNT;

    static long count(final EntityManager entityManager) {
        if (COUNT == null) {
            COUNT = entityManager.createQuery("SELECT COUNT(a) FROM Actor AS a", Long.class).getSingleResult();
        }
        return COUNT;
    }

    static Actor selectRandom(final EntityManager entityManager) {
        final int firstResult = current().nextInt(0, toIntExact(count(entityManager)));
        return entityManager
                .createQuery("SELECT a FROM Actor AS a", Actor.class)
                .setFirstResult(firstResult)
                .setMaxResults(1)
                .getResultList()
                .get(0);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    ActorIT() {
        super(Actor.class);
    }
}
