DESC payment;

SELECT *
FROM payment;


SELECT SUM(p.amount) AS total, c.*
FROM payment AS p
         JOIN customer AS c ON p.customer_id = c.customer_id
GROUP BY p.customer_id
ORDER BY total DESC;
;