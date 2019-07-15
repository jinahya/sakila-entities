desc film_category;

SELECT film_id, COUNT(category_id) AS category_count
FROM film_category
GROUP BY film_id
ORDER BY category_count DESC
;

SELECT fc.film_id, c.*
FROM film_category AS fc
         JOIN category AS c ON fc.category_id = c.category_id
ORDER BY fc.film_id ASC, c.category_id ASC
;

SELECT category_id, COUNT(film_id) AS film_count
FROM film_category
GROUP BY category_id
ORDER BY film_count DESC
;

