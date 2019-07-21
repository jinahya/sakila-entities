DESC film;

SELECT *
FROM film
ORDER BY film_id DESC
LIMIT 1;

-- --------------------------------------------------------------------------------------------- select special_features
SELECT special_features
FROM film;

SELECT f.film_id, fc.category_id
FROM film AS f
         LEFT OUTER JOIN film_category AS fc ON f.film_id = fc.film_id
WHERE fc.category_id IS NULL
;