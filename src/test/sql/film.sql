-- --------------------------------------------------------------------------------------------- select special_features
SELECT special_features
FROM film;


SELECT f.film_id, COUNT(fc.category_id) AS category_count
FROM film AS f
         LEFT OUTER JOIN film_category AS fc ON f.film_id = fc.film_id
GROUP BY f.film_id
ORDER BY category_count DESC, f.film_id ASC
;