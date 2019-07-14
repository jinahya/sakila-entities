desc actor;

SELECT DISTINCT first_name
FROM actor
ORDER BY first_name;

SELECT *
FROM actor
ORDER BY first_name ASC;

SELECT DISTINCT last_name
FROM actor
ORDER BY last_name;

SELECT *
FROM actor
ORDER BY last_name ASC;


DELIMITER //
CREATE FUNCTION RANDOM_FIRST_NAME()
    RETURNS STRING
BEGIN
    RETURN
(SELECT actor.first_name
 FROM actor
 ORDER BY RAND()
 LIMIT 1)
END//
DELIMITER ;

SELECT *
FROM actor
WHERE first_name = (SELECT first_name FROM actor ORDER BY RAND() LIMIT 1);

SELECT *
FROM actor
WHERE last_name = (SELECT last_name FROM actor ORDER BY RAND() LIMIT 1);

