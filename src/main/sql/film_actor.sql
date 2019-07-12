desc film_actor;

-- =====================================================================================================================

-- ------------------------------------------------------------------------------------------  count films of each actor
SELECT a.actor_id, COUNT(fa.film_id) AS film_count
FROM actor AS a
         LEFT JOIN film_actor AS fa ON a.actor_id = fa.actor_id
GROUP BY a.actor_id
ORDER BY film_count DESC
;

-- --------------------------------------------------------------------------------------------- count films of an actor
SELECT COUNT(film_id)
FROM film_actor
WHERE actor_id = 107
;
-- -------------------------------------------------------------------------------------------- select films of an actor
SELECT f.*
FROM film_actor AS fa
         JOIN film AS f ON fa.film_id = f.film_id
WHERE fa.actor_id = 107
ORDER BY f.release_year ASC
;

-- -------------------------------------------------------------------------------------- count films of a set of actors
SELECT COUNT(DISTINCT fa.film_id) AS film_count
FROM film_actor AS fa
WHERE fa.actor_id IN (107, 102)
;

-- ------------------------------------------------------------------------------------- select films of a set of actors
SELECT DISTINCT f.*
FROM film_actor AS fa
         JOIN film AS f ON fa.film_id = f.film_id
WHERE fa.actor_id IN (107, 102)
ORDER BY f.film_id ASC
;

-- =====================================================================================================================

-- ------------------------------------------------------------------------------------------  count actors of each film
SELECT f.film_id, COUNT(fa.actor_id) AS actor_count
FROM film AS f
         LEFT JOIN film_actor AS fa ON f.film_id = fa.film_id
GROUP BY f.film_id
ORDER BY actor_count DESC
;

-- ---------------------------------------------------------------------------------------------- count actors of a film
SELECT a.*
FROM film_actor AS fa
         JOIN actor AS a ON fa.actor_id = a.actor_id
WHERE fa.film_id = 508
;
-- --------------------------------------------------------------------------------------------- select actors of a film
SELECT a.*
FROM film_actor AS fa
         JOIN actor AS a ON fa.actor_id = a.actor_id
WHERE fa.film_id = 508
ORDER BY a.actor_id ASC
;

-- -------------------------------------------------------------------------------------- count actors of a set of films
SELECT COUNT(DISTINCT fa.actor_id) AS count
FROM film_actor AS fa
WHERE fa.film_id IN (508, 87)
;

-- ------------------------------------------------------------------------------------- select actors of a set of films
SELECT DISTINCT a.*
FROM film_actor AS fa
         JOIN actor AS a ON fa.actor_id = a.actor_id
WHERE fa.film_id IN (508, 87)
ORDER BY a.actor_id ASC
;
