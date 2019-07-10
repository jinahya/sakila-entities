SELECT *
FROM actor;

SELECT * FROM actor AS a LEFT JOIN film_actor AS fa ON a.actor_id = fa.actor_id;