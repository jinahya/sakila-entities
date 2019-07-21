desc film_category;

SELECT film_id, COUNT(category_id) AS category_count
FROM film_category
GROUP BY film_id
ORDER BY category_count DESC, film_id ASC
;

SELECT category_id, COUNT(film_id) AS film_count
FROM film_category
GROUP BY category_id
ORDER BY film_count DESC, category_id ASC
;



