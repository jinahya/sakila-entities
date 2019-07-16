DESC rental;

SELECT *
FROM rental;

SELECT MIN(rental_date), MAX(rental_date)
FROM rental;
SELECT MIN(return_date), MAX(return_date)
FROM rental;

SELECT *
FROM rental
WHERE return_date IS NOT NULL
  and return_date = rental_date;