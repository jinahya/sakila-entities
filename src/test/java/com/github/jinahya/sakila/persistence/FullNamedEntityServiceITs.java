package com.github.jinahya.sakila.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Stream;

import static com.github.jinahya.sakila.persistence.EntityService.entityName;
import static com.github.jinahya.sakila.persistence.EntityServiceIT.firstResult;
import static com.github.jinahya.sakila.persistence.EntityServiceIT.maxResults;
import static java.util.Collections.synchronizedMap;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toSet;

final class FullNamedEntityServiceITs {

    // -----------------------------------------------------------------------------------------------------------------
    static String tableName(final Class<?> entityClass) {
        return entityClass.getAnnotation(Table.class).name();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Class<?>, Set<String>> NON_UNIQUE_FIRST_NAMES = synchronizedMap(new WeakHashMap<>());

    static <T extends FullNamed> Set<String> nonUniqueFirstNames(final EntityManager entityManager,
                                                                 final Class<T> entityClass) {
        synchronized (NON_UNIQUE_FIRST_NAMES) {
            return NON_UNIQUE_FIRST_NAMES.computeIfAbsent(entityClass, k -> {
                if (current().nextBoolean()) {
                    final Set<String> set = entityManager
                            .createQuery("SELECT e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME
                                         + " FROM " + entityName(entityManager, k) + " AS e"
                                         + " GROUP BY e." + FullNamed.ATTRIBUTE_NAME_FIRST_NAME
                                         + " HAVING COUNT(e) > 1", String.class)
                            .getResultStream()
                            .collect(toSet());
                    assert !set.isEmpty();
                    return set;
                }
                if (current().nextBoolean()) {
                    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    final CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
                    @SuppressWarnings({"unchecked"})
                    final Root<T> entityRoot = criteriaQuery.from((Class<T>) k);
                    final Path<String> firstNamePath = entityRoot.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME);
                    criteriaQuery.select(firstNamePath);
                    criteriaQuery.groupBy(firstNamePath);
                    criteriaQuery.having(criteriaBuilder.gt(criteriaBuilder.count(firstNamePath), 1));
                    final Set<String> set = entityManager.createQuery(criteriaQuery).getResultStream().collect(toSet());
                    assert !set.isEmpty();
                    return set;
                }
                @SuppressWarnings({"unchecked"})
                final Set<String> set = ((Stream<String>) entityManager
                        .createNativeQuery("SELECT " + FullNamedEntity.COLUMN_NAME_FIRST_NAME
                                           + " FROM " + tableName(entityClass)
                                           + " GROUP BY " + FullNamedEntity.COLUMN_NAME_FIRST_NAME
                                           + " HAVING COUNT(" + FullNamedEntity.COLUMN_NAME_FIRST_NAME + ") > 1",
                                           String.class)
                        .getResultStream())
                        .collect(toSet());
                assert !set.isEmpty();
                return set;
            });
        }
    }

    private static String randomNonUniqueFirstName(final Class<?> entityClass) {
        synchronized (NON_UNIQUE_FIRST_NAMES) {
            final Set<String> nonUniqueFirstNames = NON_UNIQUE_FIRST_NAMES.get(entityClass);
            assert !nonUniqueFirstNames.isEmpty();
            return nonUniqueFirstNames
                    .stream()
                    .skip(current().nextInt(nonUniqueFirstNames.size()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("something's wrong if you can see me!!!"));
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Class<?>, Set<String>> NON_UNIQUE_LAST_NAMES = synchronizedMap(new WeakHashMap<>());

    static <T extends FullNamed> Set<String> nonUniqueLastNames(final EntityManager entityManager,
                                                                final Class<T> entityClass) {
        synchronized (NON_UNIQUE_LAST_NAMES) {
            return NON_UNIQUE_LAST_NAMES.computeIfAbsent(entityClass, k -> {
                if (current().nextBoolean()) {
                    return entityManager
                            .createQuery("SELECT e." + FullNamed.ATTRIBUTE_NAME_LAST_NAME
                                         + " FROM " + entityName(entityManager, k) + " AS e"
                                         + " GROUP BY e." + FullNamed.ATTRIBUTE_NAME_LAST_NAME
                                         + " HAVING COUNT(e) > 1", String.class)
                            .getResultStream()
                            .collect(toSet());
                }
                if (current().nextBoolean()) {
                    final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                    final CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
                    @SuppressWarnings({"unchecked"})
                    final Root<T> entityRoot = criteriaQuery.from((Class<T>) k);
                    final Path<String> firstNamePath = entityRoot.get(FullNamed.ATTRIBUTE_NAME_FIRST_NAME);
                    criteriaQuery.select(firstNamePath);
                    criteriaQuery.groupBy(firstNamePath);
                    criteriaQuery.having(criteriaBuilder.gt(criteriaBuilder.count(firstNamePath), 1));
                    return entityManager.createQuery(criteriaQuery).getResultStream().collect(toSet());
                }
                @SuppressWarnings({"unchecked"})
                final Set<String> set = ((Stream<String>) entityManager
                        .createNativeQuery("SELECT " + FullNamedEntity.COLUMN_NAME_LAST_NAME
                                           + " FROM " + tableName(entityClass)
                                           + " GROUP BY " + FullNamedEntity.COLUMN_NAME_LAST_NAME
                                           + " HAVING COUNT(" + FullNamedEntity.COLUMN_NAME_LAST_NAME + ") > 1",
                                           String.class)
                        .getResultStream())
                        .collect(toSet());
                return set;
            });
        }
    }

    private static String randomNonUniqueLastName(final Class<?> entityClass) {
        synchronized (NON_UNIQUE_LAST_NAMES) {
            final Set<String> nonUniqueLastNames = NON_UNIQUE_LAST_NAMES.get(entityClass);
            assert !nonUniqueLastNames.isEmpty();
            return nonUniqueLastNames
                    .stream()
                    .skip(current().nextInt(nonUniqueLastNames.size()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("something's wrong if you can see me!!!"));
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    static <T extends FullNamedEntityService<U>, U extends FullNamed> void testListSortedByLastNameIn(
            final EntityManager entityManager, final T entityService, final Class<U> entityClass) {
        final String firstName = current().nextBoolean() ? null : randomNonUniqueFirstName(entityClass);
        final boolean ascendingOrder = current().nextBoolean();
        final Integer firstResult = firstResult(entityManager, entityClass);
        final Integer maxResults = maxResults(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    FullNamedEntityServiceITs() {
        super();
    }
}
