package com.github.jinahya.sakila.persistence;

import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.WeakHashMap;

import static java.lang.StrictMath.toIntExact;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.ThreadLocalRandom.current;

@ExtendWith({WeldJunit5Extension.class})
abstract class EntityServiceIT<T extends EntityService<U>, U> {

    // -----------------------------------------------------------------------------------------------------------------
    static final String TAG_JPQL = "jpql";

    static final String TAG_CRITERIA_API = "criteria-api";

    // -----------------------------------------------------------------------------------------------------------------
    private static Map<Class<?>, Long> COUNTS;

    private static Map<Class<?>, Long> counts() {
        if (COUNTS == null) {
            COUNTS = new WeakHashMap<>();
        }
        return COUNTS;
    }

    static long count(final EntityManager entityManager, final Class<?> entityClass) {
        return counts().computeIfAbsent(entityClass, k -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(entityClass)));
            final TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            final long count = typedQuery.getSingleResult();
            assert count > 0L;
            return count;
        });
    }

    static <T> T random(final EntityManager entityManager, final Class<T> entityClass) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        final TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(toIntExact(current().nextLong(count(entityManager, entityClass))));
        typedQuery.setMaxResults(1);
        return typedQuery.getSingleResult();
    }

    // -----------------------------------------------------------------------------------------------------------------
    EntityServiceIT(final Class<? extends T> serviceClass, final Class<? extends U> entityClass) {
        super();
        this.serviceClass = requireNonNull(serviceClass, "serviceClass is null");
        this.entityClass = requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    T serviceInstance() {
        if (true) {
            return serviceInstance.select(serviceClass).get();
        }
        try {
            final Constructor<? extends T> constructor = serviceClass.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException(roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    final long count() {
        return count(entityManager, entityClass);
    }

    final U random() {
        return random(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<? extends T> serviceClass;

    final Class<? extends U> entityClass;

    @Inject
    EntityManager entityManager;

    @Inject
    private transient Instance<EntityService> serviceInstance;
}
