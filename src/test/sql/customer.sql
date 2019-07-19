DESC customer;


SELECT first_name, COUNT(1) AS name_count
FROM customer
GROUP BY first_name
ORDER BY name_count DESC
;


SELECT last_name, COUNT(1) AS name_count
FROM customer
GROUP BY last_name
ORDER BY name_count DESC
;

SELECT first_name, last_name, COUNT(1) AS name_count
FROM customer
GROUP BY first_name, last_name
ORDER BY name_count DESC
;