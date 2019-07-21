DESC film;

SELECT *
FROM film
ORDER BY film_id DESC
LIMIT 1;

-- --------------------------------------------------------------------------------------------- select special_features
SELECT special_features
FROM film;

<<<<<<< HEAD
SELECT f.film_id, fc.category_id
FROM film AS f
         LEFT OUTER JOIN film_category AS fc ON f.film_id = fc.film_id
WHERE fc.category_id IS NULL =======

SELECT f.film_id, COUNT(fc.category_id) AS category_count
FROM film AS f <<<<<<
   < HEAD
    LEFT OUTER JOIN film_category AS fc
ON f.film_id = fc.film_id OR fc.film_id IS NULL
-- WHERE fc.category_id IS NULL
ORDER BY f.film_id
    >>>>>> > sketch
    =======
    LEFT OUTER JOIN film_category AS fc
ON f.film_id = fc.film_id
GROUP BY f.film_id
ORDER BY category_count
DESC, f.film_id ASC
    >>>>>>> sketch
;