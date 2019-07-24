desc actor;

-- ------------------------------------------------------------------------------------------------- distinct first_name
SELECT DISTINCT first_name
FROM actor
ORDER BY first_name;

-- ----------------------------------------------------------------------------------------------- first_name with count
SELECT first_name, COUNT(first_name) AS count
FROM actor
GROUP BY first_name
ORDER BY count DESC, first_name ASC;

-- -------------------------------------------------------------------------------------------------- distinct last_name
SELECT DISTINCT last_name
FROM actor
ORDER BY last_name;

-- ------------------------------------------------------------------------------------------------ last_name with count
SELECT last_name, COUNT(last_name) AS count
FROM actor
GROUP BY last_name
ORDER BY count DESC, last_name ASC;

SELECT *
FROM actor
WHERE first_name = (SELECT first_name FROM actor ORDER BY RAND() LIMIT 1);

SELECT *
FROM actor
WHERE last_name = (SELECT last_name FROM actor ORDER BY RAND() LIMIT 1);


SELECT first_name, last_name, COUNT(1) AS count
FROM actor
GROUP BY first_name, last_name
ORDER BY count DESC;

-- ----------------------------------------------------------------------------------- first_name, last_name, name_count
SELECT first_name, last_name, COUNT(1) AS name_count
FROM actor
GROUP BY first_name, last_name
ORDER BY name_count DESC
;


SELECT first_name, COUNT(1) AS count
FROM actor
GROUP BY first_name
HAVING count > 1
-- ORDER BY count DESC
;

SELECT first_name
FROM actor
GROUP BY first_name
HAVING COUNT(first_name) > 1
;


