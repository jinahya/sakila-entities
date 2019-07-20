DESC city;

SELECT *
FROM city;

SELECT city, COUNT(1) AS count
FROM city
GROUP BY city
ORDER BY count DESC ;

SELECT country_id, city
FROM city
ORDER BY country_id ASC, city ASC
;
